package ru.netherdon.netheragriculture.services;

import net.minecraft.world.inventory.RecipeBookType;
import org.jetbrains.annotations.Contract;

public final class RecipeService
{
    @Contract(pure = true)
    public static RecipeBookType getBlackFurnaceRecipeBookType()
    {
        return Services.PLATFORM.getBlackFurnaceRecipeBookType();
    }
}
