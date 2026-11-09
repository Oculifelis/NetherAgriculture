package ru.netherdon.netheragriculture.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import ru.netherdon.netheragriculture.services.RegistryManager;

public final class NAPotions
{
    private static final IRegistryProvider<Potion> REGISTER = RegistryManager.getOrCreate(BuiltInRegistries.POTION);

    public static final Holder<Potion> BLAZE_FLIGHT = REGISTER.register("blaze_flight",
        () -> new Potion("blaze_flight", new MobEffectInstance(NAMobEffects.BLAZE_FLIGHT, 900))
    );

    public static final Holder<Potion> WARP = REGISTER.register("warp",
        () -> new Potion("warp", new MobEffectInstance(NAMobEffects.WARP, 3600))
    );

    public static void registerRecipes(PotionBrewing.Builder builder)
    {
        builder.addMix(Potions.SLOW_FALLING, NAItems.BLAZING_GOLDEN_LOTHUN.value(), BLAZE_FLIGHT);
        builder.addMix(Potions.AWKWARD, NAItems.WARPED_BERRY.value(), WARP);
    }

    public static void initialize() {}
}
