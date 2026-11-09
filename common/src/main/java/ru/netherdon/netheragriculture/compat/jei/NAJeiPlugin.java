package ru.netherdon.netheragriculture.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.common.Internal;
import net.minecraft.resources.Identifier;
import ru.netherdon.netheragriculture.NetherAgriculture;
import ru.netherdon.netheragriculture.client.screen.BlackFurnaceScreen;
import ru.netherdon.netheragriculture.registries.NAItems;
import ru.netherdon.netheragriculture.registries.NARecipeTypes;

import java.util.List;

@JeiPlugin
public class NAJeiPlugin implements IModPlugin
{
    private static final Identifier UID = NetherAgriculture.location("jei_plugin");

    @Override
    public Identifier getPluginUid()
    {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        IModPlugin.super.registerCategories(registration);
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new NetherCookingCategory(guiHelper));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        IModPlugin.super.registerRecipeCatalysts(registration);
        registration.addCraftingStation(NetherCookingCategory.TYPE, NAItems.BLACK_FURNACE.value());
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration)
    {
        IModPlugin.super.registerGuiHandlers(registration);
        registration.addRecipeClickArea(BlackFurnaceScreen.class, 78, 32, 28, 23, NetherCookingCategory.TYPE, RecipeTypes.SMELTING_FUEL);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        IModPlugin.super.registerRecipes(registration);
        registration.addRecipes(NetherCookingCategory.TYPE,
            // 26.x: the client has no recipe manager; JEI keeps the recipes the server synced
            List.copyOf(Internal.getClientSyncedRecipes().byType(NARecipeTypes.NETHER_COOKING.value()))
        );
    }
}
