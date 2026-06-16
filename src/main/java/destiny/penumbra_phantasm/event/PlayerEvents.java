package destiny.penumbra_phantasm.event;

import destiny.penumbra_phantasm.capability.PlayerInventoryProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerEvents {

    public PlayerEvents() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Object> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(
                    new ResourceLocation("penumbra_phantasm", "player_inventory"),
                    new PlayerInventoryProvider()
            );
        }
    }

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

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;
        Level level = player.level();
        if (level.isClientSide) return;

        player.getCapability(PlayerInventoryProvider.PLAYER_INVENTORY).ifPresent(data -> {

            String currentDim = level.dimension().location().toString();
            String lastDim = data.getLastDimension();

            // ⭐ First tick fix
            if (lastDim.isEmpty()) {
                data.setLastDimension(currentDim);
                return;
            }

            // No dimension change
            if (lastDim.equals(currentDim)) return;

            // ⭐ Dynamic dimension detection
            boolean enteringDark = currentDim.contains("dark_world_type_");
            boolean leavingDark = lastDim.contains("dark_world_type_");

            // Save current inventory
            ListTag invNBT = new ListTag();
            player.getInventory().save(invNBT);

            if (leavingDark) {
                data.setDarkWorldInv(invNBT);
            } else {
                data.setOverworldInv(invNBT);
            }

            // Load correct inventory
            ListTag loadNBT = enteringDark ? data.getDarkWorldInv() : data.getOverworldInv();
            player.getInventory().load(loadNBT);

            // Update last dimension
            data.setLastDimension(currentDim);
        });
    }
}

