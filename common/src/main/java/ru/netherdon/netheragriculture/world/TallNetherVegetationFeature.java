package ru.netherdon.netheragriculture.world;

import net.minecraft.world.level.chunk.ChunkGenerator;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;

public class TallNetherVegetationFeature implements Feature
{
    public static final MapCodec<TallNetherVegetationFeature> CODEC = TallNetherVegetationConfig.CODEC.xmap(TallNetherVegetationFeature::new, (feature) -> feature.config);

    private final TallNetherVegetationConfig config;

    public TallNetherVegetationFeature(TallNetherVegetationConfig config)
    {
        this.config = config;
    }

    @Override
    public MapCodec<TallNetherVegetationFeature> codec()
    {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel levelIn, ChunkGenerator chunkGeneratorIn, RandomSource randomIn, BlockPos originIn)
    {
        WorldGenLevel level = levelIn;
        BlockPos pos = originIn;
        BlockState state = level.getBlockState(pos.below());
        TallNetherVegetationConfig config = this.config;
        RandomSource randomSource = randomIn;
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
