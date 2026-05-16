package destiny.penumbra_phantasm.server.block.entity;

import destiny.penumbra_phantasm.server.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DarkMarbleDiceBlockEntity extends BlockEntity {
    public static final String ROTATION_X = "rotationX";
    public static final String ROTATION_Y = "rotationY";
    public static final String ROTATION_Z = "rotationZ";

    public int rotationX = 0;
    public int rotationY = 0;
    public int rotationZ = 0;

    public DarkMarbleDiceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.DARK_MARBLE_DICE_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.rotationX = tag.getInt(ROTATION_X);
        this.rotationY = tag.getInt(ROTATION_Y);
        this.rotationZ = tag.getInt(ROTATION_Z);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(ROTATION_X, rotationX);
        tag.putInt(ROTATION_Y, rotationY);
        tag.putInt(ROTATION_Z, rotationZ);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
    }

    public void markUpdated() {
        super.setChanged();
        if (level != null)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }
}
