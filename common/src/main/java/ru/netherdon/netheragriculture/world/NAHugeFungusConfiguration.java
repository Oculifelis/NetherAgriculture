package ru.netherdon.netheragriculture.world;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

public class NAHugeFungusConfiguration
{
    public static final MapCodec<NAHugeFungusConfiguration> CODEC = RecordCodecBuilder.mapCodec((instance) ->
        instance.group(
            BlockState.CODEC.fieldOf("stem_state").forGetter(hugeFungusConfiguration -> hugeFungusConfiguration.stemState),
            BlockState.CODEC.fieldOf("hat_state").forGetter(hugeFungusConfiguration -> hugeFungusConfiguration.hatState),
            BlockState.CODEC.fieldOf("decor_state").forGetter(hugeFungusConfiguration -> hugeFungusConfiguration.decorState),
            RuleTest.CODEC.fieldOf("valid_base").forGetter(hugeFungusConfiguration -> hugeFungusConfiguration.validBaseTest),
            BlockPredicate.CODEC.fieldOf("replaceable_blocks").forGetter(hugeFungusConfiguration -> hugeFungusConfiguration.replaceableBlocks),
            Codec.BOOL.fieldOf("planted").orElse(false).forGetter(hugeFungusConfiguration -> hugeFungusConfiguration.planted)
        )
        .apply(instance, NAHugeFungusConfiguration::new)
    );

    public final BlockState stemState;
    public final BlockState hatState;
    public final BlockState decorState;
    public final RuleTest validBaseTest;
    public final BlockPredicate replaceableBlocks;
    public final boolean planted;

    public NAHugeFungusConfiguration(BlockState stemState, BlockState hatState, BlockState decorState, RuleTest validBaseTest, BlockPredicate replaceableBlocks, boolean planted)
    {
        this.stemState = stemState;
        this.hatState = hatState;
        this.decorState = decorState;
        this.validBaseTest = validBaseTest;
        this.replaceableBlocks = replaceableBlocks;
        this.planted = planted;
    }
}
