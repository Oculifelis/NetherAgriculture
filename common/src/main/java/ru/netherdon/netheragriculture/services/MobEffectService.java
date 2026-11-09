package ru.netherdon.netheragriculture.services;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Contract;
import ru.netherdon.netheragriculture.effects.BlazeFlightEffect;

public final class MobEffectService
{
    @Contract(pure = true)
    public static void fireBlazeFlightTick(LivingEntity entity, int tick)
    {
        Services.PLATFORM.fireBlazeFlightTick(entity, tick);
    }

    @Contract(pure = true)
    public static MobEffect modifyBlazeFlight(BlazeFlightEffect effect)
    {
        return Services.PLATFORM.modifyBlazeFlight(effect);
    }
}
