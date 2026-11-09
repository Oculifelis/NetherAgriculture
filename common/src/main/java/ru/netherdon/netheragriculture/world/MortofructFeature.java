package ru.netherdon.netheragriculture.world;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import ru.netherdon.netheragriculture.blocks.DeadVinesBlock;
import ru.netherdon.netheragriculture.blocks.MortofructBlock;
import ru.netherdon.netheragriculture.registries.NABlocks;
import ru.netherdon.netheragriculture.registries.NATags;

public class MortofructFeature extends Feature<MortofructFeatureConfiguration>
{
    public MortofructFeature(Codec<MortofructFeatureConfiguration> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<MortofructFeatureConfiguration> featurePlaceContext)
    {
        WorldGenLevel level = featurePlaceContext.level();
        BlockPos pos = featurePlaceContext.origin();
        MortofructFeatureConfiguration config = featurePlaceContext.config();
        RandomSource random = featurePlaceContext.random();

        if (!this.canPlace(level, pos))
        {
            return false;
        }

        int i = pos.getY();
        if (i >= level.getMinY() + 1 && i + 1 < level.getMaxY() + 1)
        {
            int placed = 0;

            for(int k = 0; k < config.tries; k++)
            {
                BlockPos pos2 = pos.offset(
                    random.nextInt(config.spreadWidth) - random.nextInt(config.spreadWidth),
                    random.nextInt(config.spreadHeight) - random.nextInt(config.spreadHeight),
                    random.nextInt(config.spreadWidth) - random.nextInt(config.spreadWidth)
                );

                if (!this.canPlace(level, pos2))
                {
                    continue;
                }

                HeadType headType = HeadType.CROP;
                if (random.nextInt(config.cropChance) != 0)
                {
                    headType = random.nextBoolean() ? HeadType.VINE : HeadType.SHOOTS;
                }

                float r = random.nextFloat();
                int startAge = Math.round(DeadVinesBlock.MAX_AGE * (1f - r * r));
                if (this.placeMortofruct(level, pos2, startAge, headType))
                {
                    placed++;
                }
            }

            return placed > 0;
        }

        return false;
    }

    private boolean canPlace(WorldGenLevel level, BlockPos pos)
    {
        return level.getBlockState(pos.above()).is(NATags.Blocks.NETHERRACKS);
    }

    private boolean placeMortofruct(WorldGenLevel level, BlockPos pos, int startAge, HeadType headType)
    {
        int height = DeadVinesBlock.MAX_AGE - startAge + 2;
        for (int i = 0; i < height; i++)
        {
            if (!level.isEmptyBlock(pos.below(i)))
            {
                return false;
            }
        }

        int i = 0;
        for (; i < height-2; i++)
        {
            BlockState state = NABlocks.DEAD_VINES.value().defaultBlockState()
                .setValue(DeadVinesBlock.ATTACHED, true)
                .setValue(DeadVinesBlock.SHOOTS, false)
                .setValue(DeadVinesBlock.AGE, Math.min(DeadVinesBlock.MAX_AGE, startAge + i));
            level.setBlock(pos.below(i), state, 2);
        }

        if (headType == HeadType.CROP)
        {
            BlockState vineState = NABlocks.DEAD_VINES.value().defaultBlockState()
                .setValue(DeadVinesBlock.ATTACHED, true)
                .setValue(DeadVinesBlock.SHOOTS, true)
                .setValue(DeadVinesBlock.AGE, DeadVinesBlock.MAX_AGE);
            BlockState cropState = NABlocks.MORTOFRUCT.value().defaultBlockState()
                .setValue(MortofructBlock.WILD, true);

            level.setBlock(pos.below(i), vineState, 2);
            level.setBlock(pos.below(i+1), cropState, 2);
        }
        else
        {
            BlockState vineState = NABlocks.DEAD_VINES.value().defaultBlockState()
                .setValue(DeadVinesBlock.ATTACHED, false)
                .setValue(DeadVinesBlock.SHOOTS, headType == HeadType.SHOOTS)
                .setValue(DeadVinesBlock.AGE, DeadVinesBlock.MAX_AGE);

            level.setBlock(pos.below(i), vineState, 2);
        }


        return true;
    }

    private enum HeadType
    {
        CROP,
        SHOOTS,
        VINE
    }
}
