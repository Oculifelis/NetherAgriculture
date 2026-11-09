package ru.netherdon.netheragriculture.mixin;

import com.google.common.collect.ImmutableSet;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.crafting.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.netherdon.netheragriculture.compat.OtherModNames;
import ru.netherdon.netheragriculture.config.NACommonConfig;
import ru.netherdon.netheragriculture.recipes.furnace.NetherCookingRecipe;
import ru.netherdon.netheragriculture.registries.NARecipeBookCategories;
import ru.netherdon.netheragriculture.registries.NARecipeTypes;

import java.util.*;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin
{
    @Unique
    private static final Set<String> NA$MYNETHERSDELIGHT_RECIPES = ImmutableSet.of(
        "hoglin_loin_cooking", "hoglin_loin_from_campfire_cooking", "hoglin_loin_from_smoking",
        "hoglin_sausage_cooking", "hoglin_sausage_from_campfire_cooking", "hoglin_sausage_from_smoking",
        "boiled_egg_cooking", "boiled_egg_from_campfire_cooking", "boiled_egg_from_smoking"
    );

    @Shadow
    private RecipeMap recipes;

    @Shadow
    private Map<ResourceKey<RecipePropertySet>, RecipePropertySet> propertySets;

    // 26.3: recipes are a registry; the same ids are left out of the map the manager builds from it.
    @ModifyArg(
        method = "<init>",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/crafting/RecipeMap;create(Lnet/minecraft/core/HolderLookup;)Lnet/minecraft/world/item/crafting/RecipeMap;")
    )
    private static HolderLookup<Recipe<?>> na$removeRecipes(HolderLookup<Recipe<?>> recipes)
    {
        if (NACommonConfig.get().modCompatibility.nethersDelight().isOnlyBlackFurnaceUsed())
        {
            return na$removeRecipesFor(OtherModNames.MY_NETHERS_DELIGHT, (HolderLookup.RegistryLookup<Recipe<?>>) recipes, NA$MYNETHERSDELIGHT_RECIPES);
        }
        return recipes;
    }

    @Unique
    private static HolderLookup.RegistryLookup<Recipe<?>> na$removeRecipesFor(String modId, HolderLookup.RegistryLookup<Recipe<?>> lookup, Collection<String> recipeIds)
    {
        Set<Identifier> removed = new HashSet<>();
        for (String recipeId : recipeIds)
        {
            removed.add(Identifier.fromNamespaceAndPath(modId, recipeId));
        }

        return new HolderLookup.RegistryLookup.Delegate<>()
        {
            @Override
            public HolderLookup.RegistryLookup<Recipe<?>> parent()
            {
                return lookup;
            }

            @Override
            public java.util.stream.Stream<Holder.Reference<Recipe<?>>> listElements()
            {
                return lookup.listElements().filter((holder) -> !removed.contains(holder.key().identifier()));
            }
        };
    }

    // Black furnace input slot filter (the 1.21.1 menu asked the recipe manager directly).
    @Inject(method = "finalizeRecipeLoading", at = @At("TAIL"))
    private void na$addBlackFurnaceInputs(FeatureFlagSet enabledFlags, CallbackInfo ci)
    {
        List<Ingredient> ingredients = new ArrayList<>();
        for (RecipeHolder<NetherCookingRecipe> holder : this.recipes.byType(NARecipeTypes.NETHER_COOKING.value()))
        {
            Ingredient ingredient = holder.value().input();
            if (!holder.value().placementInfo().isImpossibleToPlace()
                && ingredient.items().allMatch((item) -> item.value().isEnabled(enabledFlags)))
            {
                ingredients.add(ingredient);
            }
        }

        Map<ResourceKey<RecipePropertySet>, RecipePropertySet> sets = new HashMap<>(this.propertySets);
        sets.put(NARecipeBookCategories.BLACK_FURNACE_INPUT, RecipePropertySetAccessor.na$create(ingredients));
        this.propertySets = Map.copyOf(sets);
    }
}
