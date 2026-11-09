package ru.netherdon.netheragriculture.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import ru.netherdon.netheragriculture.NetherAgriculture;

public final class NAFeatureKeys
{
    public static final ResourceKey<PlacedFeature> WILD_LOTHUN = placed("wild_lothun");
    public static final ResourceKey<PlacedFeature> WARPED_BERRY_SPROUTS = placed("warped_berry_sprouts");
    public static final ResourceKey<PlacedFeature> TALL_WARPED_ROOTS = placed("tall_warped_roots");
    public static final ResourceKey<PlacedFeature> WILD_SINFUL_EYES = placed("wild_sinful_eyes");
    public static final ResourceKey<PlacedFeature> CRIMSON_BERRY_SPROUTS = placed("crimson_berry_sprouts");
    public static final ResourceKey<PlacedFeature> TALL_CRIMSON_ROOTS = placed("tall_crimson_roots");
    public static final ResourceKey<PlacedFeature> MORTOFRUCT = placed("mortofruct");
    public static final ResourceKey<PlacedFeature> NETHER_ROOTS = placed("nether_roots");
    public static final ResourceKey<PlacedFeature> AZURE_MELON = placed("azure_melon");

    private static ResourceKey<PlacedFeature> placed(String name)
    {
        return ResourceKey.create(Registries.PLACED_FEATURE, NetherAgriculture.location(name));
    }
}
