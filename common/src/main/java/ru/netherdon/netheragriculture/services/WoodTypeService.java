package ru.netherdon.netheragriculture.services;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.Contract;

public final class WoodTypeService
{
    @Contract(pure = true)
    public static WoodType register(WoodType type)
    {
        return Services.PLATFORM.registerWoodType(type);
    }

    @Contract(pure = true)
    public static BlockSetType registerSetType(BlockSetType type)
    {
        return Services.PLATFORM.registerBlockSetType(type);
    }
}
