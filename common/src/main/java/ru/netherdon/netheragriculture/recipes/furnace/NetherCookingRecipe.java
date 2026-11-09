package ru.netherdon.netheragriculture.recipes.furnace;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import ru.netherdon.netheragriculture.registries.NAItems;
import ru.netherdon.netheragriculture.registries.NARecipeBookCategories;
import ru.netherdon.netheragriculture.registries.NARecipeTypes;

public class NetherCookingRecipe extends AbstractCookingRecipe
{
    public static final MapCodec<NetherCookingRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
        (instance) -> instance.group(
                Recipe.CommonInfo.MAP_CODEC.forGetter((NetherCookingRecipe recipe) -> recipe.commonInfo),
                AbstractCookingRecipe.CookingBookInfo.MAP_CODEC.forGetter((NetherCookingRecipe recipe) -> recipe.bookInfo),
                Ingredient.CODEC.fieldOf("ingredient").forGetter(NetherCookingRecipe::input),
                ItemStackTemplate.CODEC.fieldOf("result").forGetter(NetherCookingRecipe::result),
                Codec.FLOAT.optionalFieldOf("experience", 0.0F).forGetter(NetherCookingRecipe::experience),
                // 26.3 made cookingtime required in vanilla; the black furnace keeps its default of 100.
                Codec.INT.optionalFieldOf("cookingtime", 100).forGetter(NetherCookingRecipe::cookingTime)
            )
            .apply(instance, NetherCookingRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, NetherCookingRecipe> STREAM_CODEC = cookingStreamCodec(NetherCookingRecipe::new);
    public static final RecipeSerializer<NetherCookingRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public NetherCookingRecipe(Recipe.CommonInfo commonInfo, AbstractCookingRecipe.CookingBookInfo bookInfo, Ingredient ingredient, ItemStackTemplate result, float experience, int cookingTime)
    {
        super(commonInfo, bookInfo, ingredient, result, experience, cookingTime);
    }

    @Override
    protected Item furnaceIcon()
    {
        return NAItems.BLACK_FURNACE.value();
    }

    @Override
    public RecipeSerializer<NetherCookingRecipe> getSerializer()
    {
        return SERIALIZER;
    }

    @Override
    public RecipeType<NetherCookingRecipe> getType()
    {
        return NARecipeTypes.NETHER_COOKING.value();
    }

    // Same split as the 1.21.1 recipe category finder: FOOD goes to the food tab, everything else to misc.
    @Override
    public RecipeBookCategory recipeBookCategory()
    {
        return this.category() == CookingBookCategory.FOOD
            ? NARecipeBookCategories.BLACK_FURNACE_FOOD.value()
            : NARecipeBookCategories.BLACK_FURNACE_MISC.value();
    }
}
