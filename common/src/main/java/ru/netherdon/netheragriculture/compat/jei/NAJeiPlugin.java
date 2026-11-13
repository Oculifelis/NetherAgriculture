package ru.netherdon.netheragriculture.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.common.Internal;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
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
        registration.addIngredientInfo(new ItemStack(NAItems.HOGLIN_MEAT.value()), VanillaTypes.ITEM_STACK,
            Component.translatable("jei.netheragriculture.info.hoglin_meat"));
        registration.addIngredientInfo(new ItemStack(NAItems.STRIDER_LEG.value()), VanillaTypes.ITEM_STACK,
            Component.translatable("jei.netheragriculture.info.strider_leg"));
        registration.addIngredientInfo(new ItemStack(NAItems.BLAZE_FRUIT_SEED.value()), VanillaTypes.ITEM_STACK,
            Component.translatable("jei.netheragriculture.info.blaze_fruit_seed"));
        registration.addIngredientInfo(List.of(new ItemStack(NAItems.CRIMSON_BERRY_SEEDS.value()),
            new ItemStack(NAItems.CRIMSON_BERRY.value())), VanillaTypes.ITEM_STACK,
            Component.translatable("jei.netheragriculture.info.crimson_berries"));
        registration.addIngredientInfo(List.of(new ItemStack(NAItems.WARPED_BERRY_SEEDS.value()),
            new ItemStack(NAItems.WARPED_BERRY.value())), VanillaTypes.ITEM_STACK,
            Component.translatable("jei.netheragriculture.info.warped_berries"));
    }
}
