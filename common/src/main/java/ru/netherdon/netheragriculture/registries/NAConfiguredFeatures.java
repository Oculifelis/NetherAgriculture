package ru.netherdon.netheragriculture.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OverlayFeature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import ru.netherdon.netheragriculture.NetherAgriculture;
import ru.netherdon.netheragriculture.config.NACommonConfig;
import ru.netherdon.netheragriculture.config.settings.common.ConfiguredFeatureSettings;

import java.util.function.Function;

public final class NAConfiguredFeatures
{
    public static final ResourceKey<Feature> GLOWING_FUNGUS = key("glowing_fungus");
    public static final ResourceKey<Feature> NETHER_ROOTS_BONEMEAL = key("nether_roots_bonemeal");

    private static ResourceKey<Feature> key(String name)
    {
        return ResourceKey.create(Registries.FEATURE, NetherAgriculture.location(name));
    }

    // 26.3: features are immutable records and the four vanilla vegetation features of 1.21.1 are now
    // crimson/warped_forest_vegetation (simple_block) plus one nylium_bonemeal overlay holding inline copies
    // of both. The same weights are added to the same block lists; the modified feature is returned and
    // replaces the decoded one before it is registered.
    public static Feature modify(Identifier name, Feature feature)
    {
        if (name.equals(NetherFeatures.NYLIUM_BONEMEAL.identifier()))
        {
            boolean crimson = config().isCrimsonNyliumBonemealEnabled();
            boolean warped = config().isWarpedNyliumBonemealEnabled();
            return rewrite(feature, (simple) ->
            {
                if (!(simple.toPlace().value() instanceof WeightedStateProvider provider))
                {
                    return simple;
                }
                if (contains(provider, Blocks.WARPED_ROOTS))
                {
                    return warped ? addGlowingFungus(simple, provider, 4) : simple;
                }
                if (contains(provider, Blocks.CRIMSON_ROOTS))
                {
                    return crimson ? addGlowingFungus(simple, provider, 4) : simple;
                }
                return simple;
            });
        }
        else if (name.equals(NetherFeatures.CRIMSON_FOREST_VEGETATION.identifier()))
        {
            if (config().isCrimsonForestVegetationEnabled())
            {
                return rewrite(feature, (simple) -> simple.toPlace().value() instanceof WeightedStateProvider provider ? addGlowingFungus(simple, provider, 2) : simple);
            }
        }
        else if (name.equals(NetherFeatures.WARPED_FOREST_VEGETION.identifier()))
        {
            if (config().isWarpedForestVegetationEnabled())
            {
                return rewrite(feature, (simple) -> simple.toPlace().value() instanceof WeightedStateProvider provider ? addGlowingFungus(simple, provider, 2) : simple);
            }
        }
        return feature;
    }

    private static boolean contains(WeightedStateProvider provider, net.minecraft.world.level.block.Block block)
    {
        for (Weighted<BlockState> wrapper : provider.weightedList().unwrap())
        {
            if (wrapper.value().is(block))
            {
                return true;
            }
        }
        return false;
    }

    private static Feature rewrite(Feature feature, Function<SimpleBlockFeature, Feature> fn)
    {
        if (feature instanceof SimpleBlockFeature simple)
        {
            return fn.apply(simple);
        }
        if (feature instanceof OverlayFeature overlay)
        {
            boolean changed = false;
            java.util.List<Holder<PlacedFeature>> list = new java.util.ArrayList<>();
            for (Holder<PlacedFeature> holder : overlay.features())
            {
                Holder<PlacedFeature> out = holder;
                if (holder.kind() == Holder.Kind.DIRECT)
                {
                    PlacedFeature placed = holder.value();
                    Holder<Feature> inner = placed.feature();
                    if (inner.kind() == Holder.Kind.DIRECT)
                    {
                        Feature rewritten = rewrite(inner.value(), fn);
                        if (rewritten != inner.value())
                        {
                            out = Holder.direct(new PlacedFeature(Holder.direct(rewritten), placed.placement()));
                            changed = true;
                        }
                    }
                }
                list.add(out);
            }
            return changed ? new OverlayFeature(HolderSet.direct(list)) : feature;
        }
        return feature;
    }

    private static Feature addGlowingFungus(SimpleBlockFeature feature, WeightedStateProvider provider, int weight)
    {
        WeightedList.Builder<BlockState> builder = WeightedList.builder();
        for (Weighted<BlockState> wrapper : provider.weightedList().unwrap())
        {
            builder.add(wrapper.value(), wrapper.weight());
        }
        builder.add(NABlocks.GLOWING_FUNGUS.value().defaultBlockState(), weight);
        return new SimpleBlockFeature(Holder.direct(new WeightedStateProvider(builder.build())), feature.scheduleTick());
    }

    private static ConfiguredFeatureSettings config()
    {
        return NACommonConfig.get().overrides.configuredFeature();
    }
}
