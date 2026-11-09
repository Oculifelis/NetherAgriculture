package ru.netherdon.netheragriculture.world;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

public class NAHugeFungusFeature implements Feature
{

    public static final MapCodec<NAHugeFungusFeature> CODEC = NAHugeFungusConfiguration.CODEC.xmap(NAHugeFungusFeature::new, (feature) -> feature.config);

    private final NAHugeFungusConfiguration config;

    public NAHugeFungusFeature(NAHugeFungusConfiguration config)
    {
        this.config = config;
    }

    @Override
    public MapCodec<NAHugeFungusFeature> codec()
    {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel levelIn, ChunkGenerator chunkGeneratorIn, RandomSource randomIn, BlockPos originIn)
    {
        WorldGenLevel worldGenLevel = levelIn;
        BlockPos blockPos = originIn;
        RandomSource randomSource = randomIn;
        ChunkGenerator chunkGenerator = chunkGeneratorIn;
        NAHugeFungusConfiguration hugeFungusConfiguration = this.config;

        BlockPos blockPos2 = null;
        BlockState blockState = worldGenLevel.getBlockState(blockPos.below());
        if (hugeFungusConfiguration.validBaseTest.test(blockState, blockPos, randomIn))
        {
            blockPos2 = blockPos;
        }

        if (blockPos2 == null)
        {
            return false;
        }

        int i = Mth.nextInt(randomSource, 4, 13);
        if (randomSource.nextInt(12) == 0) {
            i *= 2;
        }

        if (!hugeFungusConfiguration.planted)
        {
            int j = chunkGenerator.getGenDepth();
            if (blockPos2.getY() + i + 1 >= j)
            {
                return false;
            }
        }

        boolean bl = !hugeFungusConfiguration.planted && randomSource.nextFloat() < 0.06F;
        worldGenLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 4);
        this.placeStem(worldGenLevel, randomSource, hugeFungusConfiguration, blockPos2, i, bl);
        this.placeHat(worldGenLevel, randomSource, hugeFungusConfiguration, blockPos2, i, bl);
        return true;
    }

    private static boolean isReplaceable(WorldGenLevel level, BlockPos pos, NAHugeFungusConfiguration config, boolean planted)
    {
        if (level.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::canBeReplaced))
        {
            return true;
        }

        return planted && config.replaceableBlocks.test(level, pos);
    }

    private void placeStem(WorldGenLevel level, RandomSource random, NAHugeFungusConfiguration config, BlockPos pos, int i, boolean planted)
    {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        BlockState blockState = config.stemState;
        int j = planted ? 1 : 0;

        for (int k = -j; k <= j; k++)
        {
            for (int l = -j; l <= j; l++)
            {
                boolean bl2 = planted && Mth.abs(k) == j && Mth.abs(l) == j;

                for (int m = 0; m < i; m++)
                {
                    mutableBlockPos.setWithOffset(pos, k, m, l);
                    if (isReplaceable(level, mutableBlockPos, config, true))
                    {
                        if (config.planted)
                        {
                            if (!level.getBlockState(mutableBlockPos.below()).isAir())
                            {
                                level.destroyBlock(mutableBlockPos, true);
                            }

                            level.setBlock(mutableBlockPos, blockState, 3);
                        }
                        else if (bl2)
                        {
                            if (random.nextFloat() < 0.1F)
                            {
                                this.setBlock(level, mutableBlockPos, blockState);
                            }
                        }
                        else
                        {
                            this.setBlock(level, mutableBlockPos, blockState);
                        }
                    }
                }
            }
        }
    }

    private void placeHat(WorldGenLevel level, RandomSource random, NAHugeFungusConfiguration config, BlockPos pos, int i, boolean planted)
    {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int j = Math.min(random.nextInt(1 + i / 3) + 5, i);
        int k = i - j;

        for (int l = k; l <= i; l++)
        {
            int m = l < i - random.nextInt(3) ? 2 : 1;
            if (j > 8 && l < k + 4)
            {
                m = 3;
            }

            if (planted)
            {
                m++;
            }

            for (int n = -m; n <= m; n++)
            {
                for (int o = -m; o <= m; o++)
                {
                    boolean bl3 = n == -m || n == m;
                    boolean bl4 = o == -m || o == m;
                    boolean bl5 = !bl3 && !bl4 && l != i;
                    boolean bl6 = bl3 && bl4;
                    boolean bl7 = l < k + 3;
                    mutableBlockPos.setWithOffset(pos, n, l, o);
                    if (isReplaceable(level, mutableBlockPos, config, false))
                    {
                        if (config.planted && !level.getBlockState(mutableBlockPos.below()).isAir())
                        {
                            level.destroyBlock(mutableBlockPos, true);
                        }

                        if (bl7)
                        {
                            if (!bl5)
                            {
                                this.placeHatDropBlock(level, random, mutableBlockPos, config.hatState);
                            }
                        }
                        else if (bl5)
                        {
                            this.placeHatBlock(level, random, config, mutableBlockPos, 0.1F, 0.2F);
                        }
                        else if (bl6)
                        {
                            this.placeHatBlock(level, random, config, mutableBlockPos, 0.01F, 0.7F);
                        }
                        else
                        {
                            this.placeHatBlock(level, random, config, mutableBlockPos, 5.0E-4F, 0.98F);
                        }
                    }
                }
            }
        }
    }

    private void placeHatBlock(LevelAccessor level, RandomSource random, NAHugeFungusConfiguration config, BlockPos.MutableBlockPos pos, float f, float g)
    {
        if (random.nextFloat() < f)
        {
            this.setBlock(level, pos, config.decorState);
        }
        else if (random.nextFloat() < g)
        {
            this.setBlock(level, pos, config.hatState);
        }
    }

    private void placeHatDropBlock(LevelAccessor level, RandomSource random, BlockPos pos, BlockState state)
    {
        if (level.getBlockState(pos.below()).is(state.getBlock()))
        {
            this.setBlock(level, pos, state);
        }
        else if (random.nextFloat() < 0.15f)
        {
            this.setBlock(level, pos, state);
        }
    }
}
