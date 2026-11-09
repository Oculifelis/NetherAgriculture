package ru.netherdon.netheragriculture.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.world.item.crafting.ExtendedRecipeBookCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import ru.netherdon.netheragriculture.registries.NARecipeBookCategories;

import java.util.List;
import java.util.Map;

// Black furnace search tab = food + misc, like the 1.21.1 aggregate category.
@Mixin(ClientRecipeBook.class)
public abstract class ClientRecipeBookMixin
{
    @SuppressWarnings("unchecked")
    @ModifyArg(
        method = "rebuildCollections",
        at = @At(value = "INVOKE", target = "Ljava/util/Map;copyOf(Ljava/util/Map;)Ljava/util/Map;")
    )
    private Map<?, ?> na$addBlackFurnaceSearch(Map<?, ?> map)
    {
        Map<ExtendedRecipeBookCategory, List<RecipeCollection>> byCategory = (Map<ExtendedRecipeBookCategory, List<RecipeCollection>>) map;
        ImmutableList.Builder<RecipeCollection> search = ImmutableList.builder();
        search.addAll(byCategory.getOrDefault(NARecipeBookCategories.BLACK_FURNACE_FOOD.value(), List.of()));
        search.addAll(byCategory.getOrDefault(NARecipeBookCategories.BLACK_FURNACE_MISC.value(), List.of()));
        byCategory.put(NARecipeBookCategories.BLACK_FURNACE_SEARCH, search.build());
        return map;
    }
}
