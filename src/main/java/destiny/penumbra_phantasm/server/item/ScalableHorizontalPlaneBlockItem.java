package destiny.penumbra_phantasm.server.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ScalableHorizontalPlaneBlockItem extends BlockItem {
    public ScalableHorizontalPlaneBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    protected boolean canPlace(BlockPlaceContext pContext, BlockState pState) {
        Level level = pContext.getLevel();
        BlockPos clickedPos = pContext.getClickedPos();

        return level.getBlockState(clickedPos.below()).isSolidRender(level, clickedPos);
    }
}
