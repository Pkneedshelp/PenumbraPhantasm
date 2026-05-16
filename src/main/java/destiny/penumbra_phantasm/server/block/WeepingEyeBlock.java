package destiny.penumbra_phantasm.server.block;

import destiny.penumbra_phantasm.server.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class WeepingEyeBlock extends Block {
    public static final int MAX_TOTAL_LENGTH = 5;

    public static final IntegerProperty LEAKING = IntegerProperty.create("leaking", 0, 2);

    public WeepingEyeBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(LEAKING, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HORIZONTAL_FACING);
        pBuilder.add(LEAKING);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        Direction facing = pState.getValue(HORIZONTAL_FACING);
        int leaking = pState.getValue(LEAKING);

        if (!pLevel.getBlockState(pPos.relative(facing)).isSolidRender(pLevel, pPos.relative(facing))) {
            if (leaking < 1) {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(LEAKING, leaking + 1));
            } else if (leaking < 2) {
                BlockPos futurePos = pPos.below();

                if (!pLevel.getBlockState(futurePos.relative(facing)).isSolidRender(pLevel, futurePos.relative(facing)) && pLevel.getBlockState(futurePos).is(BlockRegistry.CLIFFROCK.get())) {
                    pLevel.setBlockAndUpdate(futurePos, BlockRegistry.LEAKING_ICHOR.get().defaultBlockState().setValue(HORIZONTAL_FACING, facing));
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(LEAKING, leaking + 1));
                }
            } else {
                int leakingBlocks = 1;

                for (int i = 0; i < MAX_TOTAL_LENGTH; i++) {
                    if (pLevel.getBlockState(pPos.offset(0, -i, 0)).is(BlockRegistry.LEAKING_ICHOR.get())) {
                        leakingBlocks++;
                    }
                }

                BlockPos futurePos = pPos.offset(0, -leakingBlocks, 0);
                BlockPos puddlePos = futurePos.relative(facing).above();

                if (pLevel.getBlockState(puddlePos.below()).isSolidRender(pLevel, puddlePos.below())
                        && !pLevel.getBlockState(puddlePos).is(BlockRegistry.ICHOR_PUDDLE.get())) {
                    pLevel.setBlockAndUpdate(puddlePos, BlockRegistry.ICHOR_PUDDLE.get().defaultBlockState());

                    return;
                }

                if (leakingBlocks < MAX_TOTAL_LENGTH) {
                    if (!pLevel.getBlockState(futurePos.relative(facing)).isSolidRender(pLevel, futurePos.relative(facing)) && pLevel.getBlockState(futurePos).is(BlockRegistry.CLIFFROCK.get())) {
                        pLevel.setBlockAndUpdate(futurePos, BlockRegistry.LEAKING_ICHOR.get().defaultBlockState().setValue(HORIZONTAL_FACING, facing));
                    }
                }
            }
        }

        super.randomTick(pState, pLevel, pPos, pRandom);
    }
}
