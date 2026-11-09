package ru.netherdon.netheragriculture.mixin;

import com.google.common.collect.ImmutableSet;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.crafting.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
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

    // 26.x: recipes are parsed in prepare(); the same ids are dropped from its result instead of from the JSON map.
    @Inject(
        method = "prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Lnet/minecraft/world/item/crafting/RecipeMap;",
        at = @At("RETURN"),
        cancellable = true
    )
    private void na$removeRecipes(ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfoReturnable<RecipeMap> cir)
    {
        if (NACommonConfig.get().modCompatibility.nethersDelight().isOnlyBlackFurnaceUsed())
        {
            cir.setReturnValue(na$removeRecipesFor(OtherModNames.MY_NETHERS_DELIGHT, cir.getReturnValue(), NA$MYNETHERSDELIGHT_RECIPES));
        }
    }

    @Unique
    private static RecipeMap na$removeRecipesFor(String modId, RecipeMap map, Collection<String> recipeIds)
    {
        Set<Identifier> removed = new HashSet<>();
        for (String recipeId : recipeIds)
        {
            removed.add(Identifier.fromNamespaceAndPath(modId, recipeId));
        }

        List<RecipeHolder<?>> kept = new ArrayList<>();
        for (RecipeHolder<?> holder : map.values())
        {
            if (!removed.contains(holder.id().identifier()))
            {
                kept.add(holder);
            }
        }
        return kept.size() == map.values().size() ? map : RecipeMap.create(kept);
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
