package ru.netherdon.netheragriculture.items.properties;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class NAFood
{
    private final FoodProperties food;
    private final float consumeSeconds;
    private final List<Supplier<MobEffectInstance>> effects;
    private final List<Float> probabilities;
    @Nullable
    private final Item convertsTo;
    @Nullable
    private final Holder<SoundEvent> drinkSound;

    private NAFood(Builder builder)
    {
        FoodProperties.Builder food = new FoodProperties.Builder()
            .nutrition(builder.nutrition)
            .saturationModifier(builder.saturationModifier);
        if (builder.alwaysEdible)
        {
            food.alwaysEdible();
        }

        this.food = food.build();
        this.consumeSeconds = builder.consumeTicks / 20f;
        this.effects = List.copyOf(builder.effects);
        this.probabilities = List.copyOf(builder.probabilities);
        this.convertsTo = builder.convertsTo;
        this.drinkSound = builder.drinkSound;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public FoodProperties food()
    {
        return this.food;
    }

    public Consumable consumable()
    {
        Consumable.Builder builder = this.drinkSound != null
            ? Consumables.defaultDrink().sound(this.drinkSound)
            : Consumables.defaultFood();
        builder.consumeSeconds(this.consumeSeconds);
        for (int i = 0; i < this.effects.size(); i++)
        {
            builder.onConsume(new ApplyStatusEffectsConsumeEffect(this.effects.get(i).get(), this.probabilities.get(i)));
        }

        return builder.build();
    }

    public Item.Properties apply(Item.Properties properties)
    {
        properties.food(this.food, this.consumable());
        if (this.convertsTo != null)
        {
            properties.usingConvertsTo(this.convertsTo);
        }

        return properties;
    }

    public static final class Builder
    {
        private int nutrition;
        private float saturationModifier;
        private boolean alwaysEdible;
        private int consumeTicks = 32;
        private final List<Supplier<MobEffectInstance>> effects = new ArrayList<>();
        private final List<Float> probabilities = new ArrayList<>();
        @Nullable
        private Item convertsTo;
        @Nullable
        private Holder<SoundEvent> drinkSound;

        public Builder nutrition(int nutrition)
        {
            this.nutrition = nutrition;
            return this;
        }

        public Builder saturationModifier(float saturationModifier)
        {
            this.saturationModifier = saturationModifier;
            return this;
        }

        public Builder alwaysEdible()
        {
            this.alwaysEdible = true;
            return this;
        }

        // 1.21.1 FoodProperties.Builder.fast(): 16 ticks instead of 32
        public Builder fast()
        {
            this.consumeTicks = 16;
            return this;
        }

        public Builder effect(Supplier<MobEffectInstance> effect, float probability)
        {
            this.effects.add(effect);
            this.probabilities.add(probability);
            return this;
        }

        public Builder usingConvertsTo(Item item)
        {
            this.convertsTo = item;
            return this;
        }

        // 1.21.1 DrinkItem: drink animation and sound, fixed use duration
        public Builder drink(Holder<SoundEvent> sound, int useTicks)
        {
            this.drinkSound = sound;
            this.consumeTicks = useTicks;
            return this;
        }

        public NAFood build()
        {
            return new NAFood(this);
        }
    }
}
