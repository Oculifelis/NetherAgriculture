package ru.netherdon.netheragriculture.services;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public final class CropService
{
    @Contract(pure = true)
    public static boolean canGrow(ServerLevel level, BlockPos pos, BlockState state, boolean grown)
    {
        return Services.PLATFORM.canGrow(level, pos, state, grown);
    }

    @Contract(pure = true)
    public static void onGrowPost(ServerLevel level, BlockPos pos, BlockState state)
    {
        Services.PLATFORM.onGrowPost(level, pos, state);
    }

    @Contract(pure = true)
    @Nullable
    public static Boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction direction, BlockState plantState)
    {
        return Services.PLATFORM.canSustainPlant(state, level, pos, direction, plantState);
    }
}
