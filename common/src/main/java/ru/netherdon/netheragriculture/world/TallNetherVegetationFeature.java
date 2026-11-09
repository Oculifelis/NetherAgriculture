package ru.netherdon.netheragriculture.world;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class TallNetherVegetationFeature extends Feature<TallNetherVegetationConfig>
{
    public TallNetherVegetationFeature(Codec<TallNetherVegetationConfig> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<TallNetherVegetationConfig> featurePlaceContext)
    {
        WorldGenLevel level = featurePlaceContext.level();
        BlockPos pos = featurePlaceContext.origin();
        BlockState state = level.getBlockState(pos.below());
        TallNetherVegetationConfig config = featurePlaceContext.config();
        RandomSource randomSource = featurePlaceContext.random();
        if (!state.is(BlockTags.NYLIUM))
        {
            return false;
        }

        int i = pos.getY();
        if (i >= level.getMinY() + 1 && i + 1 < level.getMaxY() + 1)
        {
            int j = 0;

            for(int k = 0; k < config.spreadWidth * config.spreadWidth; ++k)
            {
                if (randomSource.nextInt(config.chance) != 0)
                {
                    continue;
                }

                BlockPos pos2 = pos.offset(
                    randomSource.nextInt(config.spreadWidth) - randomSource.nextInt(config.spreadWidth),
                    randomSource.nextInt(config.spreadHeight) - randomSource.nextInt(config.spreadHeight),
                    randomSource.nextInt(config.spreadWidth) - randomSource.nextInt(config.spreadWidth)
                );
                BlockPos pos2Above = pos2.above();

                if (
                    level.isEmptyBlock(pos2)
                    && level.isEmptyBlock(pos2Above)
                    && pos2.getY() > level.getMinY()
                    && config.stateBelow.canSurvive(level, pos2))
                {
                    level.setBlock(pos2, config.stateBelow, 2);
                    level.setBlock(pos2Above, config.stateAbove, 2);
                    ++j;
                }
            }

            return j > 0;
        }

        return false;
    }
}
