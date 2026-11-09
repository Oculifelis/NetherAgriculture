package ru.netherdon.netheragriculture.registries;

import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import ru.netherdon.netheragriculture.NetherAgriculture;
import ru.netherdon.netheragriculture.services.WoodTypeService;

public final class NAWoodTypes
{
    public static final BlockSetType GLOWING_BLOCK_SET = WoodTypeService.registerSetType(
        new BlockSetType(
            name("glowing"),
            true,
            true,
            true,
            BlockSetType.PressurePlateSensitivity.EVERYTHING,
            SoundType.NETHER_WOOD,
            SoundEvents.NETHER_WOOD_DOOR_CLOSE,
            SoundEvents.NETHER_WOOD_DOOR_OPEN,
            SoundEvents.NETHER_WOOD_TRAPDOOR_CLOSE,
            SoundEvents.NETHER_WOOD_TRAPDOOR_OPEN,
            SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_OFF,
            SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_ON,
            SoundEvents.NETHER_WOOD_BUTTON_CLICK_OFF,
            SoundEvents.NETHER_WOOD_BUTTON_CLICK_ON
        )
    );

    public static final WoodType GLOWING = WoodTypeService.register(
        new WoodType(
            name("glowing"),
            GLOWING_BLOCK_SET,
            SoundType.NETHER_WOOD,
            SoundType.NETHER_WOOD_HANGING_SIGN,
            SoundEvents.NETHER_WOOD_FENCE_GATE_CLOSE,
            SoundEvents.NETHER_WOOD_FENCE_GATE_OPEN
        )
    );



    private static String name(String name)
    {
        return NetherAgriculture.location(name).toString();
    }

    public static void initialize() {}
}
