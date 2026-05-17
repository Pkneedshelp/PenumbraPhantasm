package destiny.penumbra_phantasm.server.worldgen;

import com.mojang.serialization.Codec;
import destiny.penumbra_phantasm.server.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import static destiny.penumbra_phantasm.server.block.DustBlock.ANIMATION_OFFSET;
import static destiny.penumbra_phantasm.server.block.DustBlock.SPAWN_PARTICLES;
import static destiny.penumbra_phantasm.server.block.GenericHorizontalOrientableBlock.HORIZONTAL_FACING;

public class DustFeature extends Feature<NoneFeatureConfiguration> {
    public DustFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = level.getRandom();

        //Lower layer
        BlockPos[] lowerPositions = {
                pos.offset(1, 0, 1),
                pos.offset(-1, 0, -1),
                pos.offset(1, 0, -1),
                pos.offset(-1, 0, 1),
                pos.offset(0, 0, 0),
                pos.offset(1, 0, 0),
                pos.offset(0, 0, 1),
                pos.offset(-1, 0, 0),
                pos.offset(0, 0, -1)
        };

        //Upper layer
        BlockPos[] upperPositions = {
                pos.offset(0, 1, 0),
                pos.offset(1, 1, 0),
                pos.offset(0, 1, 1),
                pos.offset(-1, 1, 0),
                pos.offset(0, 1, -1)
        };


        //Check lower layer
        for (BlockPos target : lowerPositions) {
            if (!level.getBlockState(target).isAir()) {
                return false;
            }
            if (!level.getBlockState(target.below()).isFaceSturdy(level, target.below(), Direction.UP)) {
                return false;
            }
        }

        //Check upper layer
        for (BlockPos target : upperPositions) {
            if (!level.getBlockState(target).isAir()) {
                return false;
            }
        }

        //Corner blocks
        level.setBlock(pos.offset(1, 0, 1), BlockRegistry.DUST_BLOCK.get().defaultBlockState()
                .setValue(ANIMATION_OFFSET, random.nextInt(1, 4)).setValue(HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)), 2);
        level.setBlock(pos.offset(-1, 0, -1), BlockRegistry.DUST_BLOCK.get().defaultBlockState()
                .setValue(ANIMATION_OFFSET, random.nextInt(1, 4)).setValue(HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)), 2);
        level.setBlock(pos.offset(1, 0, -1), BlockRegistry.DUST_BLOCK.get().defaultBlockState()
                .setValue(ANIMATION_OFFSET, random.nextInt(1, 4)).setValue(HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)), 2);
        level.setBlock(pos.offset(-1, 0, 1), BlockRegistry.DUST_BLOCK.get().defaultBlockState()
                .setValue(ANIMATION_OFFSET, random.nextInt(1, 4)).setValue(HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)), 2);

        //Lower cross
        level.setBlock(pos.offset(0, 0, 0), BlockRegistry.DUST_BLOCK.get().defaultBlockState()
                .setValue(ANIMATION_OFFSET, 0).setValue(HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)), 2);
        level.setBlock(pos.offset(1, 0, 0), BlockRegistry.DUST_BLOCK.get().defaultBlockState()
                .setValue(ANIMATION_OFFSET, 0).setValue(HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)), 2);
        level.setBlock(pos.offset(0, 0, 1), BlockRegistry.DUST_BLOCK.get().defaultBlockState()
                .setValue(ANIMATION_OFFSET, 0).setValue(HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)), 2);
        level.setBlock(pos.offset(-1, 0, 0), BlockRegistry.DUST_BLOCK.get().defaultBlockState()
                .setValue(ANIMATION_OFFSET, 0).setValue(HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)), 2);
        level.setBlock(pos.offset(0, 0, -1), BlockRegistry.DUST_BLOCK.get().defaultBlockState()
                .setValue(ANIMATION_OFFSET, 0).setValue(HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)), 2);

        //Upper cross
        level.setBlock(pos.offset(0, 1, 0), BlockRegistry.DUST_BLOCK.get().defaultBlockState()
                .setValue(ANIMATION_OFFSET, 0).setValue(HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random))
                .setValue(SPAWN_PARTICLES, true), 2);
        level.setBlock(pos.offset(1, 1, 0), BlockRegistry.DUST_BLOCK.get().defaultBlockState()
                .setValue(ANIMATION_OFFSET, random.nextInt(1, 4)).setValue(HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)), 2);
        level.setBlock(pos.offset(0, 1, 1), BlockRegistry.DUST_BLOCK.get().defaultBlockState()
                .setValue(ANIMATION_OFFSET, random.nextInt(1, 4)).setValue(HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)), 2);
        level.setBlock(pos.offset(-1, 1, 0), BlockRegistry.DUST_BLOCK.get().defaultBlockState()
                .setValue(ANIMATION_OFFSET, random.nextInt(1, 4)).setValue(HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)), 2);
        level.setBlock(pos.offset(0, 1, -1), BlockRegistry.DUST_BLOCK.get().defaultBlockState()
                .setValue(ANIMATION_OFFSET, random.nextInt(1, 4)).setValue(HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)), 2);

        return true;
    }
}