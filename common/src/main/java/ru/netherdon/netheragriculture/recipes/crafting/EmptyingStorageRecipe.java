package ru.netherdon.netheragriculture.recipes.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class EmptyingStorageRecipe implements CraftingRecipe
{
    public static final MapCodec<EmptyingStorageRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
        instance.group(
            Ingredient.CODEC.fieldOf("input").forGetter((recipe) -> recipe.input),
            ItemStackTemplate.CODEC.fieldOf("storage").forGetter((recipe) -> recipe.storage),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter((recipe) -> recipe.result)
        ).apply(instance, EmptyingStorageRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, EmptyingStorageRecipe> STREAM_CODEC = StreamCodec.composite(
        Ingredient.CONTENTS_STREAM_CODEC,
        (recipe) -> recipe.input,
        ItemStackTemplate.STREAM_CODEC,
        (recipe) -> recipe.storage,
        ItemStackTemplate.STREAM_CODEC,
        (recipe) -> recipe.result,
        EmptyingStorageRecipe::new
    );

    public static final RecipeSerializer<EmptyingStorageRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

    private final Ingredient input;
    private final ItemStackTemplate storage;
    private final ItemStackTemplate result;
    private @Nullable PlacementInfo placementInfo;

    public EmptyingStorageRecipe(Ingredient input, ItemStackTemplate storage, ItemStackTemplate result)
    {
        this.input = input;
        this.storage = storage;
        this.result = result;
    }

    @Override
    public boolean matches(CraftingInput input, Level level)
    {
        int inputCount = 0;
        for (int i = 0; i < input.size(); i++)
        {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty())
            {
                continue;
            }

            if (this.input.test(stack))
            {
                inputCount++;
                continue;
            }

            return false;
        }

        return inputCount == 1;
    }

    @Override
    public ItemStack assemble(CraftingInput input)
    {
        return this.result.create();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input)
    {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++)
        {
            ItemStack stack = input.getItem(i);
            if (this.input.test(stack))
            {
                ItemStack remainingStack = this.storage.create();
                remaining.set(i, remainingStack);
            }
        }

        return remaining;
    }

    @Override
    public boolean showNotification()
    {
        return true;
    }

    @Override
    public String group()
    {
        return "";
    }

    @Override
    public PlacementInfo placementInfo()
    {
        if (this.placementInfo == null)
        {
            this.placementInfo = PlacementInfo.create(List.of(this.input));
        }
        return this.placementInfo;
    }

    @Override
    public List<RecipeDisplay> display()
    {
        return List.of(new ShapelessCraftingRecipeDisplay(
            List.of(this.input.display()),
            new SlotDisplay.ItemStackSlotDisplay(this.result),
            new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
        ));
    }

    @Override
    public CraftingBookCategory category()
    {
        return CraftingBookCategory.MISC;
    }

    @Override
    public RecipeSerializer<EmptyingStorageRecipe> getSerializer()
    {
        return SERIALIZER;
    }
}
