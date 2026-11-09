package ru.netherdon.netheragriculture.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Compostable;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import ru.netherdon.netheragriculture.NetherAgriculture;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public final class NACompostables
{
    public static Map<Holder<? extends Item>, Integer> entries()
    {
        Map<Holder<? extends Item>, Integer> map = new LinkedHashMap<>();

        map.put(NAItems.NETHER_ROOTS, 65);
        map.put(NAItems.TALL_CRIMSON_ROOTS, 75);
        map.put(NAItems.TALL_WARPED_ROOTS, 75);

        map.put(NAItems.CRIMSON_BERRY_SEEDS, 35);
        map.put(NAItems.WARPED_BERRY_SEEDS, 35);
        map.put(NAItems.AZURE_MELON_SEEDS, 35);
        map.put(NAItems.BLAZE_FRUIT_SEED, 25);

        map.put(NAItems.CRIMSON_BERRY, 40);
        map.put(NAItems.WARPED_BERRY, 40);

        map.put(NAItems.LOTHUN, 70);
        map.put(NAItems.SINFUL_EYES, 70);
        map.put(NAItems.AZURE_MELON, 70);
        map.put(NAItems.BLAZE_FRUIT, 70);
        map.put(NAItems.AZURE_MELON_SLICE, 55);
        map.put(NAItems.MORTOFRUCT, 70);
        map.put(NAItems.MORTOFRUCT_HALF, 60);

        map.put(NAItems.DEAD_VINES, 55);
        map.put(NAItems.WILD_LOTHUN, 55);
        map.put(NAItems.WILD_SINFUL_EYES, 55);
        map.put(NAItems.CRIMSON_BERRY_SPROUTS, 55);
        map.put(NAItems.WARPED_BERRY_SPROUTS, 55);

        return map;
    }

    public static void forEach(BiConsumer<Item, Compostable> consumer)
    {
        entries().forEach((item, percent) -> consumer.accept(item.value(), new Compostable(key(percent))));
    }

    public static void apply(DataComponentMap.Builder builder, Compostable compostable)
    {
        builder.set(DataComponents.COMPOSTABLE, compostable);
    }

    private static ResourceKey<ContextIntProvider> key(int percent)
    {
        return ResourceKey.create(Registries.CONTEXT_INT_PROVIDER, Identifier.fromNamespaceAndPath(NetherAgriculture.ID, "compostable/chance_" + percent));
    }
}
