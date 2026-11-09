package ru.netherdon.netheragriculture.registries;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import ru.netherdon.netheragriculture.services.RegistryManager;
import ru.netherdon.netheragriculture.world.*;

public final class NAFeatures
{
    private static final IRegistryProvider<MapCodec<? extends Feature>> REGISTER = RegistryManager.getOrCreate(BuiltInRegistries.FEATURE_TYPE);

    public static final Holder<MapCodec<? extends Feature>> TALL_NETHER_VEGETATION = REGISTER.register("tall_nether_vegetation", () -> TallNetherVegetationFeature.CODEC);
    public static final Holder<MapCodec<? extends Feature>> MORTOFRUCT = REGISTER.register("mortofruct", () -> MortofructFeature.CODEC);
    public static final Holder<MapCodec<? extends Feature>> HUGE_FUNGUS = REGISTER.register("huge_fungus", () -> NAHugeFungusFeature.CODEC);
    public static final Holder<MapCodec<? extends Feature>> NETHER_FOREST_VEGETATION = REGISTER.register("nether_forest_vegetation", () -> NetherForestVegetationFeature.CODEC);

    public static void initialize() {}
}
