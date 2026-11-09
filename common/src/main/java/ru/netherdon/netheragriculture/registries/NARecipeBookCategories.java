package ru.netherdon.netheragriculture.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.ExtendedRecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipePropertySet;
import ru.netherdon.netheragriculture.NetherAgriculture;
import ru.netherdon.netheragriculture.services.RegistryManager;

// 26.x: recipe book categories are registry entries and the furnace slot filter is a synced property set.
public final class NARecipeBookCategories
{
    public static final IRegistryProvider<RecipeBookCategory> REGISTER = RegistryManager.getOrCreate(BuiltInRegistries.RECIPE_BOOK_CATEGORY);

    public static final Holder<RecipeBookCategory> BLACK_FURNACE_FOOD = REGISTER.register("black_furnace_food", RecipeBookCategory::new);
    public static final Holder<RecipeBookCategory> BLACK_FURNACE_MISC = REGISTER.register("black_furnace_misc", RecipeBookCategory::new);

    // Search tab: food + misc, filled on the client by ClientRecipeBookMixin.
    public static final ExtendedRecipeBookCategory BLACK_FURNACE_SEARCH = new ExtendedRecipeBookCategory() {
        @Override
        public String toString()
        {
            return NetherAgriculture.ID + ":black_furnace_search";
        }
    };

    public static final ResourceKey<RecipePropertySet> BLACK_FURNACE_INPUT =
        ResourceKey.create(RecipePropertySet.TYPE_KEY, NetherAgriculture.location("black_furnace_input"));

    public static void initialize() {}
}
