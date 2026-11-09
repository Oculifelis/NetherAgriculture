package ru.netherdon.netheragriculture.fabric.registries;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;

public final class NACompostables
{
    public static void initialize()
    {
        DefaultItemComponentEvents.MODIFY.register(context ->
            ru.netherdon.netheragriculture.registries.NACompostables.forEach((item, compostable) ->
                context.modify(item, builder -> ru.netherdon.netheragriculture.registries.NACompostables.apply(builder, compostable))
            )
        );
    }
}
