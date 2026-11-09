package ru.netherdon.netheragriculture.blocks;

import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherFungusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;

public class GlowingFungusBlock extends NetherFungusBlock
{

    protected final ResourceKey<Feature> feature;

    protected GlowingFungusBlock(ResourceKey<Feature> resourceKey, Properties properties)
    {
        // 1.21.1 FungusBlock.mayPlaceOn = nylium, mycelium, soul soil, dirt, farmland: the vanilla crimson fungus support tag
        super(resourceKey, Blocks.CRIMSON_NYLIUM, BlockTags.SUPPORTS_CRIMSON_FUNGUS, properties);
        this.feature = resourceKey;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState, BonemealSource source)
    {
        BlockState blockState2 = levelReader.getBlockState(blockPos.below());
        return blockState2.is(BlockTags.NYLIUM);
    }
}
