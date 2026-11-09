package ru.netherdon.netheragriculture.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public final class NAItemKeys
{
    public static final ResourceKey<Item> GLASS_BOTTLE = minecraft("glass_bottle");

    private static ResourceKey<Item> minecraft(String name)
    {
        return key(Identifier.withDefaultNamespace(name));
    }

    private static ResourceKey<Item> key(Identifier name)
    {
        return ResourceKey.create(Registries.ITEM, name);
    }
}
