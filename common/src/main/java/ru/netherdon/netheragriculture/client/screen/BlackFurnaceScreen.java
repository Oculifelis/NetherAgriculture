package ru.netherdon.netheragriculture.client.screen;

import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import ru.netherdon.netheragriculture.NetherAgriculture;
import ru.netherdon.netheragriculture.blocks.entities.containers.BlackFurnaceMenu;
import ru.netherdon.netheragriculture.registries.NAItems;
import ru.netherdon.netheragriculture.registries.NARecipeBookCategories;

import java.util.List;
import java.util.Optional;

public class BlackFurnaceScreen extends AbstractFurnaceScreen<BlackFurnaceMenu>
{
    private static final Identifier LIT_PROGRESS_SPRITE = NetherAgriculture.location("container/black_furnace/lit_progress");
    private static final Identifier BURN_PROGRESS_SPRITE = NetherAgriculture.location("container/black_furnace/burn_progress");
    private static final Identifier TEXTURE = NetherAgriculture.location("textures/gui/container/black_furnace.png");
    private static final Component FILTER_NAME = Component.translatable("gui.recipebook.toggleRecipes.smeltable");

    public BlackFurnaceScreen(BlackFurnaceMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title, FILTER_NAME, TEXTURE, LIT_PROGRESS_SPRITE, BURN_PROGRESS_SPRITE, tabs());
    }

    // Same tabs and icons as the 1.21.1 categories: search (compass), food (hoglin meat), misc (lava bucket).
    private static List<RecipeBookComponent.TabInfo> tabs()
    {
        return List.of(
            new RecipeBookComponent.TabInfo(new ItemStack(Items.COMPASS), Optional.empty(), NARecipeBookCategories.BLACK_FURNACE_SEARCH),
            new RecipeBookComponent.TabInfo(NAItems.HOGLIN_MEAT.value(), NARecipeBookCategories.BLACK_FURNACE_FOOD.value()),
            new RecipeBookComponent.TabInfo(Items.LAVA_BUCKET, NARecipeBookCategories.BLACK_FURNACE_MISC.value())
        );
    }
}
