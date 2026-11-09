package ru.netherdon.netheragriculture.registries;

import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.List;

public class NAPiglinBartering
{
    public static List<LootPoolEntryContainer> getAdditionalEntries()
    {
        return List.of(
            LootItem.lootTableItem(NAItems.CRIMSON_BERRY_SEEDS.value())
                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 8)))
                .setWeight(40)
                .build(),

            LootItem.lootTableItem(NAItems.CRIMSON_BERRY.value())
                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 10)))
                .setWeight(30)
                .build(),

            LootItem.lootTableItem(NAItems.SINFUL_EYES.value())
                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 8)))
                .setWeight(40)
                .build(),

            LootItem.lootTableItem(NAItems.MORTOFRUCT_HALF.value())
                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 9)))
                .setWeight(40)
                .build(),

            LootItem.lootTableItem(NAItems.DEAD_VINES.value())
                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5)))
                .setWeight(35)
                .build()
        );
    }
}
