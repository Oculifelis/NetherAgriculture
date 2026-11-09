package ru.netherdon.netheragriculture.world;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.state.BlockState;

public class TallNetherVegetationConfig
{
    public static final MapCodec<TallNetherVegetationConfig> CODEC = RecordCodecBuilder.mapCodec((instance) ->
        instance.group(
            BlockState.CODEC.fieldOf("state_below").forGetter((config) -> config.stateBelow),
            BlockState.CODEC.fieldOf("state_above").forGetter((config) -> config.stateAbove),
            ExtraCodecs.POSITIVE_INT.fieldOf("spread_width").forGetter((config) -> config.spreadWidth),
            ExtraCodecs.POSITIVE_INT.fieldOf("spread_height").forGetter((config) -> config.spreadHeight),
            ExtraCodecs.POSITIVE_INT.fieldOf("chance").forGetter((config) -> config.chance)
        ).apply(instance, TallNetherVegetationConfig::new)
    );

    public final BlockState stateBelow;
    public final BlockState stateAbove;
    public final int spreadWidth;
    public final int spreadHeight;
    public final int chance;

    public TallNetherVegetationConfig(BlockState stateBelow, BlockState stateAbove, int spreadWidth, int spreadHeight, int chance)
    {
        this.stateBelow = stateBelow;
        this.stateAbove = stateAbove;
        this.spreadWidth = spreadWidth;
        this.spreadHeight = spreadHeight;
        this.chance = chance;
    }
}
