package ru.netherdon.netheragriculture.items;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import ru.netherdon.netheragriculture.config.NAServerConfig;
import ru.netherdon.netheragriculture.services.EntityService;

public class BurningItem extends Item
{
    private final int fireTicks;

    public BurningItem(int fireTicks, Properties properties)
    {
        super(properties);
        this.fireTicks = fireTicks;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot)
    {
        if (
            isSelectedLegacy(entity, slot)
            && !EntityService.isImmuneToFire(entity)
            && entity.getRemainingFireTicks() <= 0
            && NAServerConfig.get().entity.isBurningFromItemEnabled()
        )
        {
            entity.igniteForTicks(this.getFireTicks());
        }
    }

    // 1.21.1 ticked only player inventories, passing (list index, index == selected hotbar slot), and checked
    // "isSelected || slotId == SLOT_OFFHAND (40)". The offhand and armour lists passed their own index (0..3),
    // so 40 never matched, and an offhand or armour item counted as selected when its list index equalled the
    // selected hotbar slot. Quirk kept: the same rule, mapped from 26.x equipment slots.
    private static boolean isSelectedLegacy(Entity entity, @Nullable EquipmentSlot slot)
    {
        if (!(entity instanceof Player player) || slot == null)
        {
            return false;
        }
        if (slot == EquipmentSlot.MAINHAND)
        {
            return true;
        }
        int legacyIndex;
        if (slot == EquipmentSlot.OFFHAND)
        {
            legacyIndex = 0;
        }
        else if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR)
        {
            legacyIndex = slot.getIndex();
        }
        else
        {
            return false;
        }
        return legacyIndex == player.getInventory().getSelectedSlot();
    }

    public int getFireTicks()
    {
        return this.fireTicks;
    }
}
