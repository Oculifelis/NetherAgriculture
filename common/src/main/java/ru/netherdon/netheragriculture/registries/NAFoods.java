package ru.netherdon.netheragriculture.registries;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import ru.netherdon.netheragriculture.items.properties.NAFood;

// 26.x: FoodProperties no longer carries effects or a remainder. Each NAFood keeps the
// 1.21.1 nutrition, saturation, eat speed and effect chances, and builds the matching
// FoodProperties + Consumable (one ApplyStatusEffects entry per effect, rolled separately)
// and the use remainder when the item is constructed.
public final class NAFoods
{
    public static final NAFood CRIMSON_BERRY = NAFood.builder()
        .nutrition(2).saturationModifier(0.2f).fast()
        .build();

    public static final NAFood WARPED_BERRY = NAFood.builder()
        .nutrition(3).saturationModifier(0.2f).fast()
        .effect(() -> new MobEffectInstance(NAMobEffects.WARP, 400), 0.15f)
        .build();

    public static final NAFood LOTHUN = NAFood.builder()
        .nutrition(4).saturationModifier(0.7F)
        .effect(() -> new MobEffectInstance(MobEffects.NAUSEA, 300), 0.2f)
        .build();

    public static final NAFood SINFUL_EYES = NAFood.builder()
        .nutrition(2).saturationModifier(0.4F)
        .build();

    public static final NAFood MORTOFRUCT = NAFood.builder()
        .nutrition(3).saturationModifier(0.5f)
        .build();

    public static final NAFood AZURE_MELON_SLICE = NAFood.builder()
        .nutrition(3).saturationModifier(0.2f)
        .build();

    public static final NAFood BLAZE_FRUIT = NAFood.builder()
        .nutrition(1).saturationModifier(0.05f)
        .build();

    public static final NAFood HOGLIN_MEAT = NAFood.builder()
        .nutrition(4).saturationModifier(0.3f)
        .build();

    public static final NAFood COOKED_HOGLIN_MEAT = NAFood.builder()
        .nutrition(9).saturationModifier(0.7f)
        .build();

    public static final NAFood STRIDER_LEG = NAFood.builder()
        .nutrition(2).saturationModifier(0.2f)
        .build();

    public static final NAFood COOKED_STRIDER_LEG = NAFood.builder()
        .nutrition(7).saturationModifier(0.6f)
        .build();

    public static final NAFood BLAZE_CREAM_SOUP = NAFood.builder()
        .nutrition(11).saturationModifier(0.7f).alwaysEdible()
        .effect(() -> new MobEffectInstance(NAMobEffects.BLAZE_FLIGHT, 200), 1f)
        .usingConvertsTo(Items.BOWL)
        .build();

    public static final NAFood GLAZED_HOGLIN_MEAT = NAFood.builder()
        .nutrition(12).saturationModifier(1.05f).alwaysEdible()
        .effect(() -> new MobEffectInstance(MobEffects.STRENGTH, 300), 0.9f)
        .usingConvertsTo(Items.BOWL)
        .build();

    public static final NAFood AZURE_MELON_PUREE = NAFood.builder()
        .nutrition(9).saturationModifier(0.35f).alwaysEdible()
        .effect(() -> new MobEffectInstance(MobEffects.LUCK, 200), 1f)
        .build();

    public static final NAFood NETHER_MUSHROOM_STEW = NAFood.builder()
        .nutrition(6).saturationModifier(0.6f)
        .usingConvertsTo(Items.BOWL)
        .build();

    public static final NAFood GLOWING_MUSHROOM_STEW = NAFood.builder()
        .nutrition(6).saturationModifier(0.6f)
        .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 100), 1f)
        .usingConvertsTo(Items.BOWL)
        .build();

    public static final NAFood NETHER_FRUIT_SALAD = NAFood.builder()
        .nutrition(8).saturationModifier(0.55f).alwaysEdible()
        .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200), 1f)
        .usingConvertsTo(Items.BOWL)
        .build();

    public static final NAFood AZURE_MELON_JELLY = NAFood.builder()
        .nutrition(8).saturationModifier(0.4f).alwaysEdible()
        .usingConvertsTo(Items.BOWL)
        .build();

    public static final NAFood NETHER_BERRY_JELLY = NAFood.builder()
        .nutrition(8).saturationModifier(0.3f).alwaysEdible()
        .usingConvertsTo(Items.BOWL)
        .build();

    public static final NAFood CRIMSON_BERRY_JAM = NAFood.builder()
        .nutrition(5).saturationModifier(0.5f).alwaysEdible()
        .drink(SoundEvents.HONEY_DRINK, 40)
        .build();

    public static final NAFood WARPED_BERRY_JAM = NAFood.builder()
        .nutrition(8).saturationModifier(0.10f).alwaysEdible()
        .drink(SoundEvents.HONEY_DRINK, 40)
        .build();

    public static final NAFood NETHER_PORK_ROAST = NAFood.builder()
        .nutrition(15).saturationModifier(0.7f).alwaysEdible()
        .effect(() -> new MobEffectInstance(MobEffects.RESISTANCE, 100), 0.7f)
        .usingConvertsTo(Items.BOWL)
        .build();

    public static final NAFood STRIDER_WITH_VEGETABLES = NAFood.builder()
        .nutrition(10).saturationModifier(1f).alwaysEdible()
        .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300), 0.85f)
        .usingConvertsTo(Items.BOWL)
        .build();

    public static final NAFood NETHER_BARBECUE_ON_A_STICK = NAFood.builder()
        .nutrition(9).saturationModifier(0.9f)
        .usingConvertsTo(Items.STICK)
        .build();

    public static final NAFood BLAZING_BLEND = NAFood.builder()
        .nutrition(2).saturationModifier(0.2f).alwaysEdible()
        .drink(SoundEvents.HONEY_DRINK, 40)
        .build();

    public static final NAFood BLAZING_GOLDEN_LOTHUN = NAFood.builder()
        .nutrition(7).saturationModifier(0.7f).alwaysEdible()
        .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600), 1f)
        .effect(() -> new MobEffectInstance(MobEffects.SPEED, 300), 1f)
        .build();
}
