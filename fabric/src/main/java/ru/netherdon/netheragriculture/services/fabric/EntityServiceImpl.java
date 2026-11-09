package ru.netherdon.netheragriculture.services.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public final class EntityServiceImpl
{
    public static boolean isLocalImmuneToFire(Entity entity)
    {
        return false;
    }

    public static boolean canTrample(Entity entity, BlockState state, BlockPos pos, float fallDistance)
    {
        // 26.x: game rules live on the server level only; the only caller runs on the server
        if (!(entity.level() instanceof ServerLevel level))
        {
            return false;
        }
        return level.getRandom().nextFloat() < fallDistance - 0.5F
            && entity instanceof LivingEntity
            && (entity instanceof Player || level.getGameRules().get(GameRules.MOB_GRIEFING))
            && entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512F;
    }
}
