package ru.netherdon.netheragriculture.effects;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import ru.netherdon.netheragriculture.config.NAServerConfig;
import ru.netherdon.netheragriculture.services.EntityService;
import ru.netherdon.netheragriculture.services.MobEffectService;

public class BlazeFlightEffect extends MobEffect
{
    public BlazeFlightEffect(MobEffectCategory mobEffectCategory, int color)
    {
        super(mobEffectCategory, color);
    }

    @Override
    // 26.x: effect ticks run on the server only
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int tick)
    {
        MobEffectService.fireBlazeFlightTick(entity, tick);

        if (
            entity instanceof Player player
            && player.getAbilities().flying
            && !EntityService.isImmuneToFire(player)
            && entity.getRemainingFireTicks() <= 0
            && NAServerConfig.get().entity.isBurningFromBlazeFlightEnabled()
        )
        {
            player.igniteForTicks(20);
        }

        return super.applyEffectTick(level, entity, tick);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int i, int j)
    {
        return true;
    }
}
