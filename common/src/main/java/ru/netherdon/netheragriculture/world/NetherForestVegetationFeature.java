package ru.netherdon.netheragriculture.world;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

// Minecraft 26.3 removed the vanilla nether_forest_vegetation feature type.
// This is the same placement logic as the vanilla type in earlier versions.
public record NetherForestVegetationFeature(Holder<BlockStateProvider> stateProvider, int spreadWidth, int spreadHeight) implements Feature
{
    public static final MapCodec<NetherForestVegetationFeature> CODEC = RecordCodecBuilder.mapCodec((instance) ->
        instance.group(
            BlockStateProvider.CODEC.fieldOf("state_provider").forGetter(NetherForestVegetationFeature::stateProvider),
            ExtraCodecs.POSITIVE_INT.fieldOf("spread_width").forGetter(NetherForestVegetationFeature::spreadWidth),
            ExtraCodecs.POSITIVE_INT.fieldOf("spread_height").forGetter(NetherForestVegetationFeature::spreadHeight)
        ).apply(instance, NetherForestVegetationFeature::new)
    );

    @Override
    public MapCodec<NetherForestVegetationFeature> codec()
    {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin)
    {
        BlockState belowState = level.getBlockState(origin.below());
        if (!belowState.is(BlockTags.NYLIUM))
        {
            return false;
        }

        int y = origin.getY();
        if (y >= level.getMinY() + 1 && y + 1 <= level.getMaxY())
        {
            int placed = 0;
            for (int i = 0; i < this.spreadWidth * this.spreadWidth; i++)
            {
                BlockPos finalPos = origin.offset(
                    random.nextInt(this.spreadWidth) - random.nextInt(this.spreadWidth),
                    random.nextInt(this.spreadHeight) - random.nextInt(this.spreadHeight),
                    random.nextInt(this.spreadWidth) - random.nextInt(this.spreadWidth)
                );
                BlockState state = this.stateProvider.value().getState(level, random, finalPos);
                if (level.isEmptyBlock(finalPos) && finalPos.getY() > level.getMinY() && state.canSurvive(level, finalPos))
                {
                    level.setBlock(finalPos, state, 2);
                    placed++;
                }
            }
            return placed > 0;
        }

        return false;
    }
}
