package ru.netherdon.netheragriculture.services.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

public final class EntityServiceImpl
{
    public static boolean isLocalImmuneToFire(Entity entity)
    {
        return entity.isInFluidType((e, fluidType, height) -> entity.canFluidExtinguish(fluidType));
    }

    public static boolean canTrample(Entity entity, BlockState state, BlockPos pos, float fallDistance)
    {
        // 26.x: canTrample needs the server level; the only caller runs on the server
        return entity.level() instanceof ServerLevel serverLevel && entity.canTrample(serverLevel, state, pos, fallDistance);
    }
}
