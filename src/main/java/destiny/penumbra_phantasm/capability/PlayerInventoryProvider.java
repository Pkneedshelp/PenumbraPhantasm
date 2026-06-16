package destiny.penumbra_phantasm.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerInventoryProvider implements ICapabilityProvider {

    public static final Capability<PlayerInventoryData> PLAYER_INVENTORY =
            CapabilityManager.get(new CapabilityToken<>() {});

    private final PlayerInventoryData backend = new PlayerInventoryData();
    private final LazyOptional<PlayerInventoryData> optional = LazyOptional.of(() -> backend);

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == PLAYER_INVENTORY ? optional.cast() : LazyOptional.empty();
    }

    public CompoundTag saveNBT() {
        CompoundTag tag = new CompoundTag();
        backend.saveNBT(tag);
        return tag;
    }

    public void loadNBT(CompoundTag tag) {
        backend.loadNBT(tag);
    }
}

