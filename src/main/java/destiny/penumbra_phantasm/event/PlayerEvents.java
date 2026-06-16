package destiny.penumbra_phantasm.event;

import destiny.penumbra_phantasm.capability.PlayerInventoryData;
import destiny.penumbra_phantasm.capability.PlayerInventoryProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerEvents {

    private static final String CARD_KINGDOM = "penumbra_phantasm:card_kingdom";

    public PlayerEvents() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    // Attach capability to players
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Object> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(
                    new net.minecraft.resources.ResourceLocation("penumbra_phantasm", "player_inventory"),
                    new PlayerInventoryProvider()
            );
        }
    }

    // Clone capability on respawn
    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        event.getOriginal().getCapability(PlayerInventoryProvider.PLAYER_INVENTORY).ifPresent(oldStore -> {
            event.getEntity().getCapability(PlayerInventoryProvider.PLAYER_INVENTORY).ifPresent(newStore -> {
                CompoundTag tag = new CompoundTag();
                oldStore.saveNBT(tag);
                newStore.loadNBT(tag);
            });
        });
    }

    // Main tick logic for dimension-based inventory swapping
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;
        Level level = player.level();
        if (level.isClientSide) return;

        player.getCapability(PlayerInventoryProvider.PLAYER_INVENTORY).ifPresent(data -> {

            String currentDim = level.dimension().location().toString();
            String lastDim = data.getLastDimension();

            // First tick or no change
            if (lastDim.equals(currentDim)) return;

            boolean enteringCard = currentDim.equals(CARD_KINGDOM);
            boolean leavingCard = lastDim.equals(CARD_KINGDOM);

            // Save current inventory into correct slot
            CompoundTag invNBT = new CompoundTag();
            player.getInventory().save(invNBT);

            if (leavingCard) {
                data.setCardKingdomInv(invNBT);
            } else {
                data.setOverworldInv(invNBT);
            }

            // Load the new inventory
            CompoundTag loadNBT = enteringCard ? data.getCardKingdomInv() : data.getOverworldInv();
            player.getInventory().load(loadNBT);

            // Update last dimension
            data.setLastDimension(currentDim);
        });
    }
}

