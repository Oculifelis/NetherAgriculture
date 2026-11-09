package ru.netherdon.netheragriculture.compat.jei;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import mezz.jei.library.plugins.vanilla.cooking.AbstractCookingCategory;
import ru.netherdon.netheragriculture.NetherAgriculture;
import ru.netherdon.netheragriculture.recipes.furnace.NetherCookingRecipe;
import ru.netherdon.netheragriculture.registries.NABlocks;
import ru.netherdon.netheragriculture.registries.NARecipeTypes;

public class NetherCookingCategory extends AbstractCookingCategory<NetherCookingRecipe>
{
    public static final IRecipeHolderType<NetherCookingRecipe> TYPE = IRecipeHolderType.create(NARecipeTypes.NETHER_COOKING.value());
    private static final String TRANSLATION_KEY = "emi.category." + NetherAgriculture.ID + ".nether_cooking";

    public NetherCookingCategory(IGuiHelper guiHelper)
    {
        super(guiHelper, TYPE, NABlocks.BLACK_FURNACE.value(), TRANSLATION_KEY, 100, 116, 54);
    }
}
