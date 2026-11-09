package ru.netherdon.netheragriculture.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import ru.netherdon.netheragriculture.recipes.crafting.EmptyingStorageRecipe;
import ru.netherdon.netheragriculture.recipes.furnace.NetherCookingRecipe;
import ru.netherdon.netheragriculture.services.RegistryManager;

public final class NARecipeSerializers
{
    public static final IRegistryProvider<RecipeSerializer<?>> REGISTER = RegistryManager.getOrCreate(BuiltInRegistries.RECIPE_SERIALIZER);

    public static final Holder<RecipeSerializer<?>> EMPTYING_STORAGE =
        REGISTER.register("emptying_storage", () -> EmptyingStorageRecipe.SERIALIZER);

    public static final Holder<RecipeSerializer<?>> NETHER_COOKING =
        REGISTER.register("nether_cooking", () -> NetherCookingRecipe.SERIALIZER);



    public static void initialize() {}
}

