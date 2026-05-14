package destiny.penumbra_phantasm.server.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import javax.annotation.Nullable;

public class GreatDoorSpawnerBlock extends HorizontalDirectionalBlock {
    public GreatDoorSpawnerBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING);
        super.createBlockStateDefinition(stateBuilder);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
}
