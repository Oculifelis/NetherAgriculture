package ru.netherdon.netheragriculture.services.fabric;

import net.minecraft.world.inventory.RecipeBookType;

public final class RecipeServiceImpl
{
    // 26.x: RecipeBookType can no longer be extended; the black furnace shares the furnace book state
    public static RecipeBookType getBlackFurnaceRecipeBookType()
    {
        return RecipeBookType.FURNACE;
    }
}
