package ru.netherdon.netheragriculture.fabric.registries;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.UniformContainerBase;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SequenceFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.core.HolderSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ru.netherdon.netheragriculture.NetherAgriculture;
import ru.netherdon.netheragriculture.compat.OtherModNames;
import ru.netherdon.netheragriculture.config.NACommonConfig;
import ru.netherdon.netheragriculture.config.settings.common.LootModifierSettings;
import ru.netherdon.netheragriculture.misc.LootTableHelper;
import ru.netherdon.netheragriculture.registries.NAItems;
import ru.netherdon.netheragriculture.registries.NAPiglinBartering;

import java.util.Map;

import static ru.netherdon.netheragriculture.registries.LootTableNames.*;

public final class NALootTableModifiers
{
    public static void initialize()
    {
        LootTableEvents.MODIFY.register(NALootTableModifiers::modify);
    }

    private static void modify(ResourceKey<LootTable> key, LootTable.Builder builder, LootTableSource source, HolderLookup.Provider registries)
    {
        if (!source.isBuiltin())
        {
            return;
        }

        Identifier tableId = key.identifier();
        if (tableId.equals(NETHER_BRIDGE))
        {
            if (config().fabric().isNetherBridgeEnabled())
            {
                builder.withPool(LootPool.lootPool().add(reference(registries, "modifiers/chests/nether_bridge")));
            }
        }
        else if (tableId.equals(STRIDER))
        {
            if (config().fabric().isStriderEnabled())
            {
                builder.withPool(LootPool.lootPool().add(reference(registries, "modifiers/strider_leg")));
            }
        }
        else if (tableId.equals(PIGLIN_BARTERING))
        {
            if (config().isPiglinBarteringEnabled())
            {
                LootTableHelper.extendLootPool(builder.pools.build().getFirst(), NAPiglinBartering.getAdditionalEntries());
            }
        }
        else if (!FabricLoader.getInstance().isModLoaded(OtherModNames.MY_NETHERS_DELIGHT))
        {
            if (tableId.equals(BASTION_HOGLIN_STABLE))
            {
                if (config().fabric().isBastionHoglinStableEnabled())
                {
                    replaceItem(builder, Map.of(
                        Items.PORKCHOP, toItem(NAItems.HOGLIN_MEAT),
                        Items.COOKED_PORKCHOP, toItem(NAItems.COOKED_HOGLIN_MEAT)
                    ));
                }
            }
            else if (tableId.equals(HOGLIN))
            {
                if (config().fabric().isHoglinEnabled())
                {
                    replaceItem(builder, Map.of(
                        Items.PORKCHOP, all(toItem(NAItems.HOGLIN_MEAT), removeSmelting())
                    ));
                }
            }
        }
    }

    private static Modifier all(Modifier... modifiers)
    {
        return (loot) ->
        {
            for (Modifier modifier : modifiers)
            {
                loot = modifier.modify(loot);
            }
            return loot;
        };
    }

    // 26.3: loot entries are immutable records of holders, so the modifiers build a new LootItem
    // with the same weight, quality, condition and functions instead of mutating fields.
    private static Modifier toItem(Holder<Item> item)
    {
        return (loot) -> new LootItem(item, loot.weight, loot.quality, loot.condition, loot.modifier);
    }

    private static Modifier removeSmelting()
    {
        return (loot) -> new LootItem(loot.item, loot.weight, loot.quality, loot.condition, withoutSmelting(loot.modifier));
    }

    private static Optional<Holder<LootItemFunction>> withoutSmelting(Optional<Holder<LootItemFunction>> modifier)
    {
        if (modifier.isEmpty())
        {
            return modifier;
        }
        LootItemFunction function = modifier.get().value();
        if (function instanceof SmeltItemFunction)
        {
            return Optional.empty();
        }
        if (function instanceof SequenceFunction sequence)
        {
            List<Holder<LootItemFunction>> kept = new ArrayList<>();
            for (Holder<LootItemFunction> holder : sequence.functions)
            {
                if (!(holder.value() instanceof SmeltItemFunction))
                {
                    kept.add(holder);
                }
            }
            if (kept.size() == sequence.functions.size())
            {
                return modifier;
            }
            if (sequence.condition.isEmpty())
            {
                if (kept.isEmpty())
                {
                    return Optional.empty();
                }
                if (kept.size() == 1)
                {
                    return Optional.of(kept.getFirst());
                }
            }
            return Optional.of(Holder.direct(new SequenceFunction(sequence.condition, HolderSet.direct(kept))));
        }
        return modifier;
    }

    private static void replaceItem(LootTable.Builder tableBuilder, Map<Item, Modifier> modifiers)
    {
        tableBuilder.modifyPools((poolBuilder) ->
        {
            ImmutableList<LootPoolEntryContainer> oldEntries = poolBuilder.entries.build();
            poolBuilder.entries = ImmutableList.builder();

            // Quirk kept: upstream added every entry twice (the first copy was the same LootItem it then
            // mutated), so matching entries become two copies of the replacement and the others two copies.
            for (LootPoolEntryContainer container : oldEntries)
            {
                if (container instanceof LootItem lootItem && modifiers.containsKey(lootItem.item.value()))
                {
                    LootItem replaced = modifiers.get(lootItem.item.value()).modify(lootItem);
                    poolBuilder.add(replaced);
                    poolBuilder.add(replaced);
                }
                else
                {
                    poolBuilder.add(container);
                    poolBuilder.add(container);
                }
            }
        });
    }

    private static ResourceKey<LootTable> key(String name)
    {
        return ResourceKey.create(Registries.LOOT_TABLE, NetherAgriculture.location(name));
    }

    private static LootModifierSettings config()
    {
        return NACommonConfig.get().overrides.lootModifier();
    }

    private static UniformContainerBase.Builder<?> reference(HolderLookup.Provider registries, String name)
    {
        return NestedLootTable.lootTableReference(registries.lookupOrThrow(Registries.LOOT_TABLE).getOrThrow(key(name)));
    }

    private interface Modifier
    {
        public LootItem modify(LootItem loot);
    }
}
