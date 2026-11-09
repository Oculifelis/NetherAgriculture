package ru.netherdon.netheragriculture.fabric.registries;

import net.fabricmc.fabric.api.registry.CompostableRegistry;
import ru.netherdon.netheragriculture.registries.NAItems;

public final class NACompostables
{
    public static void initialize()
    {
        CompostableRegistry registry = CompostableRegistry.INSTANCE;

        registry.add(NAItems.NETHER_ROOTS.value(), 0.65f);
        registry.add(NAItems.TALL_CRIMSON_ROOTS.value(), 0.75f);
        registry.add(NAItems.TALL_WARPED_ROOTS.value(), 0.75f);

        registry.add(NAItems.CRIMSON_BERRY_SEEDS.value(), 0.35f);
        registry.add(NAItems.WARPED_BERRY_SEEDS.value(), 0.35f);
        registry.add(NAItems.AZURE_MELON_SEEDS.value(), 0.35f);
        registry.add(NAItems.BLAZE_FRUIT_SEED.value(), 0.25f);

        registry.add(NAItems.CRIMSON_BERRY.value(), 0.4f);
        registry.add(NAItems.WARPED_BERRY.value(), 0.4f);

        registry.add(NAItems.LOTHUN.value(), 0.7f);
        registry.add(NAItems.SINFUL_EYES.value(), 0.7f);
        registry.add(NAItems.AZURE_MELON.value(), 0.7f);
        registry.add(NAItems.BLAZE_FRUIT.value(), 0.7f);
        registry.add(NAItems.AZURE_MELON_SLICE.value(), 0.55f);
        registry.add(NAItems.MORTOFRUCT.value(), 0.7f);
        registry.add(NAItems.MORTOFRUCT_HALF.value(), 0.6f);

        registry.add(NAItems.DEAD_VINES.value(), 0.55f);
        registry.add(NAItems.WILD_LOTHUN.value(), 0.55f);
        registry.add(NAItems.WILD_SINFUL_EYES.value(), 0.55f);
        registry.add(NAItems.CRIMSON_BERRY_SPROUTS.value(), 0.55f);
        registry.add(NAItems.WARPED_BERRY_SPROUTS.value(), 0.55f);
    }
}
