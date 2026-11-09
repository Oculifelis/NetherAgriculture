package ru.netherdon.netheragriculture.world;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

public class MortofructFeatureConfiguration
{
    public static final MapCodec<MortofructFeatureConfiguration> CODEC = RecordCodecBuilder.mapCodec((instance) ->
        instance.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("spread_height").forGetter((config) -> config.spreadHeight),
            ExtraCodecs.POSITIVE_INT.fieldOf("spread_width").forGetter((config) -> config.spreadWidth),
            ExtraCodecs.POSITIVE_INT.fieldOf("tries").forGetter((config) -> config.tries),
            ExtraCodecs.POSITIVE_INT.fieldOf("crop_chance").orElse(1).forGetter((config) -> config.cropChance)
        ).apply(instance, MortofructFeatureConfiguration::new)
    );

    public final int spreadHeight;
    public final int spreadWidth;
    public final int tries;
    public final int cropChance;

    public MortofructFeatureConfiguration(int spreadHeight, int spreadWidth, int tries, int cropChance)
    {
        this.spreadHeight = spreadHeight;
        this.spreadWidth = spreadWidth;
        this.tries = tries;
        this.cropChance = cropChance;
    }
}
