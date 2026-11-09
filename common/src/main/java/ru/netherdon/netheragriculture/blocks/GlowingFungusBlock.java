package ru.netherdon.netheragriculture.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherFungusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class GlowingFungusBlock extends NetherFungusBlock
{
    public static final MapCodec<GlowingFungusBlock> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
                ResourceKey.codec(Registries.CONFIGURED_FEATURE).fieldOf("feature").forGetter(fungusBlock -> fungusBlock.feature),
                propertiesCodec()
            )
            .apply(instance, GlowingFungusBlock::new)
    );

    protected final ResourceKey<ConfiguredFeature<?, ?>> feature;

    protected GlowingFungusBlock(ResourceKey<ConfiguredFeature<?, ?>> resourceKey, Properties properties)
    {
        // 1.21.1 FungusBlock.mayPlaceOn = nylium, mycelium, soul soil, dirt, farmland: the vanilla crimson fungus support tag
        super(resourceKey, Blocks.CRIMSON_NYLIUM, BlockTags.SUPPORTS_CRIMSON_FUNGUS, properties);
        this.feature = resourceKey;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState)
    {
        BlockState blockState2 = levelReader.getBlockState(blockPos.below());
        return blockState2.is(BlockTags.NYLIUM);
    }
}
