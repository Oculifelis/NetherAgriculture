package ru.netherdon.netheragriculture.services;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Contract;

public final class EntityService
{
    public static boolean isImmuneToFire(Entity entity)
    {
        return entity instanceof Player player
            && player.getAbilities().invulnerable
            || entity.fireImmune()
            || entity.isInWaterOrRain()
            || entity.isInPowderSnow
            || EntityService.isLocalImmuneToFire(entity);
    }

    @Contract(pure = true)
    private static boolean isLocalImmuneToFire(Entity entity)
    {
        return Services.PLATFORM.isLocalImmuneToFire(entity);
    }

    @Contract(pure = true)
    public static boolean canTrample(Entity entity, BlockState state, BlockPos pos, float fallDistance)
    {
        return Services.PLATFORM.canTrample(entity, state, pos, fallDistance);
    }
}
