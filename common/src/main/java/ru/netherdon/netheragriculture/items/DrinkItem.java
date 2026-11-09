package ru.netherdon.netheragriculture.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

// 26.x: use duration (40 ticks), DRINK animation and drink sound live in the item's Consumable (see NAFoods/NAFood)
public class DrinkItem extends Item
{
    public DrinkItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        super.finishUsingItem(stack, level, entityLiving);
        if (entityLiving instanceof ServerPlayer serverplayer)
        {
            // Quirk kept: 1.21.1 also awarded these on top of the food handling
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, stack);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        }

        this.onDrank(stack, level, entityLiving);

        if (stack.isEmpty())
        {
            return new ItemStack(Items.GLASS_BOTTLE);
        }
        else
        {
            if (entityLiving instanceof Player player && !player.hasInfiniteMaterials())
            {
                ItemStack itemstack = new ItemStack(Items.GLASS_BOTTLE);
                if (!player.getInventory().add(itemstack))
                {
                    player.drop(itemstack, false);
                }
            }
            return stack;
        }
    }

    protected void onDrank(ItemStack stack, Level level, LivingEntity entity)
    {

    }
}
