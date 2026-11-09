package ru.netherdon.netheragriculture.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.random.Weighted;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import ru.netherdon.netheragriculture.NetherAgriculture;
import ru.netherdon.netheragriculture.config.NACommonConfig;
import ru.netherdon.netheragriculture.config.settings.common.ConfiguredFeatureSettings;

public final class NAConfiguredFeatures
{
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWING_FUNGUS = key("glowing_fungus");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_ROOTS_BONEMEAL = key("nether_roots_bonemeal");

    private static ResourceKey<ConfiguredFeature<?, ?>> key(String name)
    {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, NetherAgriculture.location(name));
    }

    public static void modify(Identifier name, ConfiguredFeature<?, ?> feature)
    {
        if (name.equals(NetherFeatures.CRIMSON_FOREST_VEGETATION_BONEMEAL.identifier()))
        {
            if (config().isCrimsonNyliumBonemealEnabled())
            {
                addGlowingFungus(feature, 4);
            }
        }
        else if (name.equals(NetherFeatures.WARPED_FOREST_VEGETATION_BONEMEAL.identifier()))
        {
            if (config().isWarpedNyliumBonemealEnabled())
            {
                addGlowingFungus(feature, 4);
            }
        }
        else if (name.equals(NetherFeatures.CRIMSON_FOREST_VEGETATION.identifier()))
        {
            if (config().isCrimsonForestVegetationEnabled())
            {
                addGlowingFungus(feature, 2);
            }
        }
        else if (name.equals(NetherFeatures.WARPED_FOREST_VEGETION.identifier()))
        {
            if (config().isWarpedForestVegetationEnabled())
            {
                addGlowingFungus(feature, 2);
            }
        }
    }

    private static void addGlowingFungus(ConfiguredFeature<?, ?> feature, int weight)
    {
        if (
            feature.config() instanceof NetherForestVegetationConfig config
            && config.stateProvider instanceof WeightedStateProvider provider
        )
        {
            WeightedList.Builder<BlockState> builder = WeightedList.builder();
            for (Weighted<BlockState> wrapper : provider.weightedList.unwrap())
            {
                builder.add(wrapper.value(), wrapper.weight());
            }
            builder.add(NABlocks.GLOWING_FUNGUS.value().defaultBlockState(), weight);
            provider.weightedList = builder.build();
        }
    }

    private static ConfiguredFeatureSettings config()
    {
        return NACommonConfig.get().overrides.configuredFeature();
    }
}
