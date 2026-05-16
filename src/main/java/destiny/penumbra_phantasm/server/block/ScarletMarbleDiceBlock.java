package destiny.penumbra_phantasm.server.block;

import destiny.penumbra_phantasm.server.block.entity.DarkMarbleDiceBlockEntity;
import destiny.penumbra_phantasm.server.block.entity.ScarletMarbleDiceBlockEntity;
import destiny.penumbra_phantasm.server.registry.BlockEntityRegistry;
import destiny.penumbra_phantasm.server.registry.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ScarletMarbleDiceBlock extends BaseEntityBlock {
    public ScarletMarbleDiceBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            if (pLevel.getBlockEntity(pPos) instanceof ScarletMarbleDiceBlockEntity scarletMarbleDiceBlockEntity) {
                scarletMarbleDiceBlockEntity.rotationX = pLevel.getRandom().nextInt(0, 4) * 90;
                scarletMarbleDiceBlockEntity.rotationY = pLevel.getRandom().nextInt(0, 4) * 90;
                scarletMarbleDiceBlockEntity.rotationZ = pLevel.getRandom().nextInt(0, 4) * 90;

                scarletMarbleDiceBlockEntity.markUpdated();
            }
        }

        pLevel.playSound(null, pPos, SoundRegistry.DICE_THROW.get(), SoundSource.BLOCKS, 1f, 1f);
        spawnDestroyParticles(pLevel, pPlayer, pPos, pState);

        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return BlockEntityRegistry.SCARLET_MARBLE_DICE_BLOCK_ENTITY.get().create(blockPos, blockState);
    }
}
