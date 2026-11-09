package ru.netherdon.netheragriculture.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import ru.netherdon.netheragriculture.items.*;
import ru.netherdon.netheragriculture.services.RegistryManager;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class NAItems
{
    public static final IRegistryProvider<Item> REGISTER = RegistryManager.getOrCreate(BuiltInRegistries.ITEM);

    public static final Holder<Item> CRUSHED_NETHER_ROOTS = registerSimpleItem("crushed_nether_roots");
    public static final Holder<Item> CRUSHED_CRIMSON_ROOTS = registerSimpleItem("crushed_crimson_roots");
    public static final Holder<Item> CRUSHED_WARPED_ROOTS = registerSimpleItem("crushed_warped_roots");

    public static final Holder<Item> BLAZING_GOLD_INGOT = REGISTER.register("blazing_gold_ingot", (id) -> new BurningItem(
        60, props(id).fireResistant()
    ));
    public static final Holder<Item> BLAZING_GOLD_NUGGET = REGISTER.register("blazing_gold_nugget", (id) -> new BurningItem(
        20, props(id).fireResistant()
    ));
    public static final Holder<Item> BLAZING_GOLDEN_LOTHUN = REGISTER.register("blazing_golden_lothun", (id) -> new BurningFoodItem(
        60, NAFoods.BLAZING_GOLDEN_LOTHUN.apply(props(id)).fireResistant()
    ));

    public static final Holder<Item> CRIMSON_BERRY = registerSimpleItem("crimson_berry", NAFoods.CRIMSON_BERRY.apply(new Item.Properties()));
    public static final Holder<Item> WARPED_BERRY = registerSimpleItem("warped_berry", NAFoods.WARPED_BERRY.apply(new Item.Properties()));
    public static final Holder<Item> LOTHUN = REGISTER.register("lothun", (id) -> new BlockItem(NABlocks.LOTHUNS.value(), NAFoods.LOTHUN.apply(props(id))));
    public static final Holder<Item> SINFUL_EYES = REGISTER.register("sinful_eyes", (id) -> new BlockItem(NABlocks.SINFUL_EYES.value(), NAFoods.SINFUL_EYES.apply(props(id))));
    public static final Holder<Item> MORTOFRUCT_HALF = REGISTER.register("mortofruct_half", (id) -> new FoodItem(NAFoods.MORTOFRUCT.apply(props(id))));
    public static final Holder<BlockItem> AZURE_MELON = registerSimpleBlockItem(NABlocks.AZURE_MELON);
    public static final Holder<Item> AZURE_MELON_SLICE = registerSimpleItem("azure_melon_slice", NAFoods.AZURE_MELON_SLICE.apply(new Item.Properties()));

    public static final Holder<Item> BLAZE_FRUIT = REGISTER.register("blaze_fruit", (id) -> new BurningItem(
        60, NAFoods.BLAZE_FRUIT.apply(props(id)).fireResistant()
    ));

    public static final Holder<Item> HOGLIN_MEAT = registerItem("hoglin_meat", FoodItem::new, NAFoods.HOGLIN_MEAT.apply(new Item.Properties()));
    public static final Holder<Item> COOKED_HOGLIN_MEAT = registerItem("cooked_hoglin_meat", FoodItem::new, NAFoods.COOKED_HOGLIN_MEAT.apply(new Item.Properties()));
    public static final Holder<Item> STRIDER_LEG = registerItem("strider_leg", FoodItem::new, NAFoods.STRIDER_LEG.apply(new Item.Properties()).fireResistant());
    public static final Holder<Item> COOKED_STRIDER_LEG = registerItem("cooked_strider_leg", FoodItem::new, NAFoods.COOKED_STRIDER_LEG.apply(new Item.Properties()).fireResistant());

    public static final Holder<Item> NETHER_BARBECUE_ON_A_STICK = REGISTER.register("nether_barbecue_on_a_stick", (id) -> new FoodItem(
        NAFoods.NETHER_BARBECUE_ON_A_STICK.apply(props(id).stacksTo(16))
    ));

    public static final Holder<Item> BLAZE_CREAM_SOUP = REGISTER.register("blaze_cream_soup", (id) -> new BurningFoodItem(
        100,
        NAFoods.BLAZE_CREAM_SOUP.apply(props(id).stacksTo(16)).fireResistant()
    ));
    public static final Holder<Item> NETHER_MUSHROOM_STEW = REGISTER.register("nether_mushroom_stew", (id) -> new FoodItem(
        NAFoods.NETHER_MUSHROOM_STEW.apply(props(id).stacksTo(16))
    ));
    public static final Holder<Item> GLOWING_MUSHROOM_STEW = REGISTER.register("glowing_mushroom_stew", (id) -> new FoodItem(
        NAFoods.GLOWING_MUSHROOM_STEW.apply(props(id).stacksTo(16))
    ));
    public static final Holder<Item> NETHER_FRUIT_SALAD = REGISTER.register("nether_fruit_salad", (id) -> new FoodItem(
        NAFoods.NETHER_FRUIT_SALAD.apply(props(id).stacksTo(16))
    ));
    public static final Holder<Item> GLAZED_HOGLIN_MEAT = REGISTER.register("glazed_hoglin_meat", (id) -> new FoodItem(
        NAFoods.GLAZED_HOGLIN_MEAT.apply(props(id).stacksTo(16))
    ));
    public static final Holder<Item> NETHER_PORK_ROAST = REGISTER.register("nether_pork_roast", (id) -> new FoodItem(
        NAFoods.NETHER_PORK_ROAST.apply(props(id).stacksTo(16))
    ));
    public static final Holder<Item> STRIDER_WITH_VEGETABLES = REGISTER.register("strider_with_vegetables", (id) -> new FoodItem(
        NAFoods.STRIDER_WITH_VEGETABLES.apply(props(id).stacksTo(16))
    ));
    public static final Holder<Item> AZURE_MELON_PUREE = REGISTER.register("azure_melon_puree", (id) -> new FoodItem(
        NAFoods.AZURE_MELON_PUREE.apply(props(id).stacksTo(16))
    ));
    public static final Holder<Item> AZURE_MELON_JELLY = REGISTER.register("azure_melon_jelly", (id) -> new FoodItem(
        NAFoods.AZURE_MELON_JELLY.apply(props(id).stacksTo(16))
    ));
    public static final Holder<Item> NETHER_BERRY_JELLY = REGISTER.register("nether_berry_jelly", (id) -> new FoodItem(
        NAFoods.NETHER_BERRY_JELLY.apply(props(id).stacksTo(16))
    ));

    public static final Holder<Item> CRIMSON_BERRY_JAM = REGISTER.register("crimson_berry_jam", (id) -> new JamItem(
        NAFoods.CRIMSON_BERRY_JAM.apply(props(id).stacksTo(16)).craftRemainder(Items.GLASS_BOTTLE)
    ));
    public static final Holder<Item> WARPED_BERRY_JAM = REGISTER.register("warped_berry_jam", (id) -> new JamItem(
        NAFoods.WARPED_BERRY_JAM.apply(props(id).stacksTo(16)).craftRemainder(Items.GLASS_BOTTLE)
    ));
    public static final Holder<Item> BLAZING_BLEND = REGISTER.register("blazing_blend", (id) -> new JamItem(
        NAFoods.BLAZING_BLEND.apply(props(id).stacksTo(16)).craftRemainder(Items.GLASS_BOTTLE)
    ));
    public static final Holder<StriderTreatItem> STRIDER_TREAT = REGISTER.register("strider_treat", (id) -> new StriderTreatItem(
        props(id).stacksTo(16)
    ));
    public static final Holder<BlockItem> GLOWING_SPORES_IN_A_BOTTLE = registerItem("glowing_spores_in_a_bottle",
        (properties) -> new GlowingSporesInBottleItem(NABlocks.GLOWING_SPORES.value(), Items.GLASS_BOTTLE, properties),
        new Item.Properties()
    );

    public static final Holder<BlockItem> CRIMSON_FARMLAND = registerSimpleBlockItem(NABlocks.CRIMSON_FARMLAND);
    public static final Holder<BlockItem> WARPED_FARMLAND = registerSimpleBlockItem(NABlocks.WARPED_FARMLAND);
    public static final Holder<BlockItem> BLACK_FURNACE = registerSimpleBlockItem(NABlocks.BLACK_FURNACE);
    public static final Holder<BlockItem> DEAD_VINES = registerSimpleBlockItem(NABlocks.DEAD_VINES);
    public static final Holder<BlockItem> MORTOFRUCT = registerSimpleBlockItem(NABlocks.MORTOFRUCT);
    public static final Holder<BlockItem> CRIMSON_BERRY_SPROUTS = registerSimpleBlockItem(NABlocks.CRIMSON_BERRY_SPROUTS);
    public static final Holder<BlockItem> WARPED_BERRY_SPROUTS = registerSimpleBlockItem(NABlocks.WARPED_BERRY_SPROUTS);
    public static final Holder<BlockItem> WILD_LOTHUN = registerSimpleBlockItem(NABlocks.WILD_LOTHUN);
    public static final Holder<BlockItem> WILD_SINFUL_EYES = registerSimpleBlockItem(NABlocks.WILD_SINFUL_EYES);

    public static final Holder<BlockItem> CRIMSON_BERRY_SEEDS = REGISTER.register("crimson_berry_seeds", (id) -> new BlockItem(NABlocks.CRIMSON_BERRY_ROOTS.value(), props(id)));
    public static final Holder<BlockItem> WARPED_BERRY_SEEDS = REGISTER.register("warped_berry_seeds", (id) -> new BlockItem(NABlocks.WARPED_BERRY_ROOTS.value(), props(id)));
    public static final Holder<BlockItem> BLAZE_FRUIT_SEED = REGISTER.register("blaze_fruit_seed", (id) -> new BlockItem(NABlocks.BLAZE_FRUIT.value(), props(id).fireResistant()));
    public static final Holder<BlockItem> AZURE_MELON_SEEDS = REGISTER.register("azure_melon_seeds", (id) -> new BlockItem(NABlocks.AZURE_MELON_STEM.value(), props(id)));

    public static final Holder<BlockItem> NETHER_ROOTS = registerSimpleBlockItem(NABlocks.NETHER_ROOTS);
    public static final Holder<BlockItem> TALL_CRIMSON_ROOTS = registerBlockItem(NABlocks.TALL_CRIMSON_ROOTS, DoubleHighBlockItem::new);
    public static final Holder<BlockItem> TALL_WARPED_ROOTS = registerBlockItem(NABlocks.TALL_WARPED_ROOTS, DoubleHighBlockItem::new);

    public static final Holder<BlockItem> GLOWING_FUNGUS = registerSimpleBlockItem(NABlocks.GLOWING_FUNGUS);
    public static final Holder<BlockItem> GLOWING_WART_BLOCK = registerSimpleBlockItem(NABlocks.GLOWING_WART_BLOCK);
    public static final Holder<BlockItem> GLOWING_STEM = registerSimpleBlockItem(NABlocks.GLOWING_STEM);
    public static final Holder<BlockItem> GLOWING_HYPHAE = registerSimpleBlockItem(NABlocks.GLOWING_HYPHAE);
    public static final Holder<BlockItem> STRIPPED_GLOWING_STEM = registerSimpleBlockItem(NABlocks.STRIPPED_GLOWING_STEM);
    public static final Holder<BlockItem> STRIPPED_GLOWING_HYPHAE = registerSimpleBlockItem(NABlocks.STRIPPED_GLOWING_HYPHAE);
    public static final Holder<BlockItem> GLOWING_PLANKS = registerSimpleBlockItem(NABlocks.GLOWING_PLANKS);
    public static final Holder<BlockItem> GLOWING_SLAB = registerSimpleBlockItem(NABlocks.GLOWING_SLAB);
    public static final Holder<BlockItem> GLOWING_STAIRS = registerSimpleBlockItem(NABlocks.GLOWING_STAIRS);
    public static final Holder<BlockItem> GLOWING_FENCE = registerSimpleBlockItem(NABlocks.GLOWING_FENCE);
    public static final Holder<BlockItem> GLOWING_FENCE_GATE = registerSimpleBlockItem(NABlocks.GLOWING_FENCE_GATE);
    public static final Holder<BlockItem> GLOWING_DOOR = registerSimpleBlockItem(NABlocks.GLOWING_DOOR);
    public static final Holder<BlockItem> GLOWING_TRAPDOOR = registerBlockItem(NABlocks.GLOWING_TRAPDOOR, DoubleHighBlockItem::new);
    public static final Holder<BlockItem> GLOWING_PRESSURE_PLATE = registerSimpleBlockItem(NABlocks.GLOWING_PRESSURE_PLATE);
    public static final Holder<BlockItem> GLOWING_BUTTON = registerSimpleBlockItem(NABlocks.GLOWING_BUTTON);
    public static final Holder<BlockItem> GLOWING_SIGN = registerItem("glowing_sign", (properties) ->
        new StandingAndWallBlockItem(NABlocks.GLOWING_SIGN.value(), NABlocks.GLOWING_WALL_SIGN.value(), net.minecraft.core.Direction.DOWN, properties)
    , new Item.Properties().useBlockDescriptionPrefix().signText());
    public static final Holder<BlockItem> GLOWING_HANGING_SIGN = registerItem("glowing_hanging_sign", (properties) ->
        new HangingSignItem(NABlocks.GLOWING_HANGING_SIGN.value(), NABlocks.GLOWING_WALL_HANGING_SIGN.value(), properties)
    , new Item.Properties().useBlockDescriptionPrefix().signText());

    public static final Holder<BlockItem> CRIMSON_CRATE = registerSimpleBlockItem(NABlocks.CRIMSON_CRATE);
    public static final Holder<BlockItem> SMALL_CRIMSON_CRATE = registerSimpleBlockItem(NABlocks.SMALL_CRIMSON_CRATE);
    public static final Holder<BlockItem> WARPED_CRATE = registerSimpleBlockItem(NABlocks.WARPED_CRATE);
    public static final Holder<BlockItem> SMALL_WARPED_CRATE = registerSimpleBlockItem(NABlocks.SMALL_WARPED_CRATE);
    public static final Holder<BlockItem> GLOWING_CRATE = registerSimpleBlockItem(NABlocks.GLOWING_CRATE);
    public static final Holder<BlockItem> SMALL_GLOWING_CRATE = registerSimpleBlockItem(NABlocks.SMALL_GLOWING_CRATE);

    public static final Holder<BlockItem> CRIMSON_CRATE_OF_CRIMSON_BERRIES = registerSimpleBlockItem(NABlocks.CRIMSON_CRATE_OF_CRIMSON_BERRIES);
    public static final Holder<BlockItem> SMALL_CRIMSON_CRATE_OF_CRIMSON_BERRIES = registerSimpleBlockItem(NABlocks.SMALL_CRIMSON_CRATE_OF_CRIMSON_BERRIES);
    public static final Holder<BlockItem> WARPED_CRATE_OF_CRIMSON_BERRIES = registerSimpleBlockItem(NABlocks.WARPED_CRATE_OF_CRIMSON_BERRIES);
    public static final Holder<BlockItem> SMALL_WARPED_CRATE_OF_CRIMSON_BERRIES = registerSimpleBlockItem(NABlocks.SMALL_WARPED_CRATE_OF_CRIMSON_BERRIES);
    public static final Holder<BlockItem> GLOWING_CRATE_OF_CRIMSON_BERRIES = registerSimpleBlockItem(NABlocks.GLOWING_CRATE_OF_CRIMSON_BERRIES);
    public static final Holder<BlockItem> SMALL_GLOWING_CRATE_OF_CRIMSON_BERRIES = registerSimpleBlockItem(NABlocks.SMALL_GLOWING_CRATE_OF_CRIMSON_BERRIES);

    public static final Holder<BlockItem> CRIMSON_CRATE_OF_WARPED_BERRIES = registerSimpleBlockItem(NABlocks.CRIMSON_CRATE_OF_WARPED_BERRIES);
    public static final Holder<BlockItem> SMALL_CRIMSON_CRATE_OF_WARPED_BERRIES = registerSimpleBlockItem(NABlocks.SMALL_CRIMSON_CRATE_OF_WARPED_BERRIES);
    public static final Holder<BlockItem> WARPED_CRATE_OF_WARPED_BERRIES = registerSimpleBlockItem(NABlocks.WARPED_CRATE_OF_WARPED_BERRIES);
    public static final Holder<BlockItem> SMALL_WARPED_CRATE_OF_WARPED_BERRIES = registerSimpleBlockItem(NABlocks.SMALL_WARPED_CRATE_OF_WARPED_BERRIES);
    public static final Holder<BlockItem> GLOWING_CRATE_OF_WARPED_BERRIES = registerSimpleBlockItem(NABlocks.GLOWING_CRATE_OF_WARPED_BERRIES);
    public static final Holder<BlockItem> SMALL_GLOWING_CRATE_OF_WARPED_BERRIES = registerSimpleBlockItem(NABlocks.SMALL_GLOWING_CRATE_OF_WARPED_BERRIES);

    public static final Holder<BlockItem> CRIMSON_CRATE_OF_LOTHUN = registerSimpleBlockItem(NABlocks.CRIMSON_CRATE_OF_LOTHUN);
    public static final Holder<BlockItem> SMALL_CRIMSON_CRATE_OF_LOTHUN = registerSimpleBlockItem(NABlocks.SMALL_CRIMSON_CRATE_OF_LOTHUN);
    public static final Holder<BlockItem> WARPED_CRATE_OF_LOTHUN = registerSimpleBlockItem(NABlocks.WARPED_CRATE_OF_LOTHUN);
    public static final Holder<BlockItem> SMALL_WARPED_CRATE_OF_LOTHUN = registerSimpleBlockItem(NABlocks.SMALL_WARPED_CRATE_OF_LOTHUN);
    public static final Holder<BlockItem> GLOWING_CRATE_OF_LOTHUN = registerSimpleBlockItem(NABlocks.GLOWING_CRATE_OF_LOTHUN);
    public static final Holder<BlockItem> SMALL_GLOWING_CRATE_OF_LOTHUN = registerSimpleBlockItem(NABlocks.SMALL_GLOWING_CRATE_OF_LOTHUN);

    public static final Holder<BlockItem> CRIMSON_CRATE_OF_BLAZING_GOLDEN_LOTHUN = registerSimpleBlockItem(NABlocks.CRIMSON_CRATE_OF_BLAZING_GOLDEN_LOTHUN, new Item.Properties().fireResistant());
    public static final Holder<BlockItem> SMALL_CRIMSON_CRATE_OF_BLAZING_GOLDEN_LOTHUN = registerSimpleBlockItem(NABlocks.SMALL_CRIMSON_CRATE_OF_BLAZING_GOLDEN_LOTHUN, new Item.Properties().fireResistant());
    public static final Holder<BlockItem> WARPED_CRATE_OF_BLAZING_GOLDEN_LOTHUN = registerSimpleBlockItem(NABlocks.WARPED_CRATE_OF_BLAZING_GOLDEN_LOTHUN, new Item.Properties().fireResistant());
    public static final Holder<BlockItem> SMALL_WARPED_CRATE_OF_BLAZING_GOLDEN_LOTHUN = registerSimpleBlockItem(NABlocks.SMALL_WARPED_CRATE_OF_BLAZING_GOLDEN_LOTHUN, new Item.Properties().fireResistant());
    public static final Holder<BlockItem> GLOWING_CRATE_OF_BLAZING_GOLDEN_LOTHUN = registerSimpleBlockItem(NABlocks.GLOWING_CRATE_OF_BLAZING_GOLDEN_LOTHUN, new Item.Properties().fireResistant());
    public static final Holder<BlockItem> SMALL_GLOWING_CRATE_OF_BLAZING_GOLDEN_LOTHUN = registerSimpleBlockItem(NABlocks.SMALL_GLOWING_CRATE_OF_BLAZING_GOLDEN_LOTHUN, new Item.Properties().fireResistant());

    public static final Holder<BlockItem> CRIMSON_CRATE_OF_BLAZE_FRUIT = registerSimpleBlockItem(NABlocks.CRIMSON_CRATE_OF_BLAZE_FRUIT, new Item.Properties().fireResistant());
    public static final Holder<BlockItem> SMALL_CRIMSON_CRATE_OF_BLAZE_FRUIT = registerSimpleBlockItem(NABlocks.SMALL_CRIMSON_CRATE_OF_BLAZE_FRUIT, new Item.Properties().fireResistant());
    public static final Holder<BlockItem> WARPED_CRATE_OF_BLAZE_FRUIT = registerSimpleBlockItem(NABlocks.WARPED_CRATE_OF_BLAZE_FRUIT, new Item.Properties().fireResistant());
    public static final Holder<BlockItem> SMALL_WARPED_CRATE_OF_BLAZE_FRUIT = registerSimpleBlockItem(NABlocks.SMALL_WARPED_CRATE_OF_BLAZE_FRUIT, new Item.Properties().fireResistant());
    public static final Holder<BlockItem> GLOWING_CRATE_OF_BLAZE_FRUIT = registerSimpleBlockItem(NABlocks.GLOWING_CRATE_OF_BLAZE_FRUIT, new Item.Properties().fireResistant());
    public static final Holder<BlockItem> SMALL_GLOWING_CRATE_OF_BLAZE_FRUIT = registerSimpleBlockItem(NABlocks.SMALL_GLOWING_CRATE_OF_BLAZE_FRUIT, new Item.Properties().fireResistant());

    public static final Holder<BlockItem> CRIMSON_CRATE_OF_SINFUL_EYES = registerSimpleBlockItem(NABlocks.CRIMSON_CRATE_OF_SINFUL_EYES);
    public static final Holder<BlockItem> SMALL_CRIMSON_CRATE_OF_SINFUL_EYES = registerSimpleBlockItem(NABlocks.SMALL_CRIMSON_CRATE_OF_SINFUL_EYES);
    public static final Holder<BlockItem> WARPED_CRATE_OF_SINFUL_EYES = registerSimpleBlockItem(NABlocks.WARPED_CRATE_OF_SINFUL_EYES);
    public static final Holder<BlockItem> SMALL_WARPED_CRATE_OF_SINFUL_EYES = registerSimpleBlockItem(NABlocks.SMALL_WARPED_CRATE_OF_SINFUL_EYES);
    public static final Holder<BlockItem> GLOWING_CRATE_OF_SINFUL_EYES = registerSimpleBlockItem(NABlocks.GLOWING_CRATE_OF_SINFUL_EYES);
    public static final Holder<BlockItem> SMALL_GLOWING_CRATE_OF_SINFUL_EYES = registerSimpleBlockItem(NABlocks.SMALL_GLOWING_CRATE_OF_SINFUL_EYES);



    public static Holder<Item> registerSimpleItem(String name, Item.Properties properties)
    {
        return registerItem(name, Item::new, properties);
    }
    
    public static Holder<Item> registerSimpleItem(String name)
    {
        return registerItem(name, Item::new, new Item.Properties());
    }

    public static Holder<BlockItem> registerSimpleBlockItem(Holder<? extends Block> block)
    {
        return registerSimpleBlockItem(block, new Item.Properties());
    }

    public static Holder<BlockItem> registerSimpleBlockItem(Holder<? extends Block> block, Item.Properties properties)
    {
        return registerBlockItem(block, BlockItem::new, properties);
    }

    public static <T extends BlockItem, B extends Block> Holder<T> registerBlockItem(Holder<B> block, BiFunction<B, Item.Properties, T> constructor)
    {
        return registerBlockItem(block, constructor, new Item.Properties());
    }

    public static <T extends BlockItem, B extends Block> Holder<T> registerBlockItem(Holder<B> block, BiFunction<B, Item.Properties, T> constructor, Item.Properties properties)
    {
        // 1.21.1 BlockItem used the block's translation key
        return registerItem(blockName(block), (propertiesIn) -> constructor.apply(block.value(), propertiesIn), properties.useBlockDescriptionPrefix());
    }

    public static String blockName(Holder<? extends Block> block)
    {
        return block.unwrapKey().orElseThrow().identifier().getPath();
    }

    public static <T extends Item> Holder<T> registerItem(String name, Function<Item.Properties, T> constructor)
    {
        return registerItem(name, constructor, new Item.Properties());
    }

    public static <T extends Item> Holder<T> registerItem(String name, Function<Item.Properties, T> constructor, Item.Properties properties)
    {
        return REGISTER.register(name, (id) -> constructor.apply(properties.setId(ResourceKey.create(Registries.ITEM, id))));
    }

    private static Item.Properties props(Identifier id)
    {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id));
    }

    public static void initialize() {}
}
