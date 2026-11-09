package ru.netherdon.netheragriculture.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import ru.netherdon.netheragriculture.recipes.furnace.NetherCookingRecipe;
import ru.netherdon.netheragriculture.services.RegistryManager;

public final class NARecipeTypes
{
    public static final IRegistryProvider<RecipeType<?>> REGISTER = RegistryManager.getOrCreate(BuiltInRegistries.RECIPE_TYPE);

    public static final Holder<RecipeType<NetherCookingRecipe>> NETHER_COOKING =
        REGISTER.register("nether_cooking", NARecipeTypes::simple);

    static <T extends Recipe<?>> RecipeType<T> simple(Identifier name)
    {
        final String nameStr = name.toString();
        return new RecipeType<T>()
        {
            public String toString()
            {
                return nameStr;
            }
        };
    }

    public static void initialize() {}
}
