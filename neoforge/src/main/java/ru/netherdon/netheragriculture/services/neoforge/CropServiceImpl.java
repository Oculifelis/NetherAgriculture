package ru.netherdon.netheragriculture.services.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;
import net.minecraft.util.TriState;
import org.jetbrains.annotations.Nullable;

public final class CropServiceImpl
{
    public static boolean canGrow(ServerLevel level, BlockPos pos, BlockState state, boolean grown)
    {
        return CommonHooks.canCropGrow(level, pos, state, grown);
    }

    public static void onGrowPost(ServerLevel level, BlockPos pos, BlockState state)
    {
        CommonHooks.fireCropGrowPost(level, pos, state);
    }

    @Nullable
    public static Boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction direction, BlockState plantState)
    {
        TriState triState = state.canSustainPlant(level, pos, direction, plantState);
        return triState.isDefault() ? null : triState.isTrue();
    }
}
