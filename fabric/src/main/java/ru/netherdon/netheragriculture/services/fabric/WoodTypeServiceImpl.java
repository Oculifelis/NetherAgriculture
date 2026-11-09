package ru.netherdon.netheragriculture.services.fabric;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public final class WoodTypeServiceImpl
{
    public static WoodType register(WoodType type)
    {
        return WoodTypeBuilder.copyOf(type)
            .register(Identifier.parse(type.name()), type.setType());
    }

    public static BlockSetType registerSetType(BlockSetType type)
    {
        return BlockSetTypeBuilder.copyOf(type)
            .register(Identifier.parse(type.name()));
    }
}
