package ru.netherdon.netheragriculture.events;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enderman;
import ru.netherdon.netheragriculture.registries.NAMobEffects;

public final class EntityEventHandler
{
    public static void spawn(Entity entity)
    {
        if (entity instanceof Enderman enderMan)
        {
            enderMan.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(enderMan, LivingEntity.class, 10, true, false, (target, serverLevel) ->
                target instanceof LivingEntity livingTarget && livingTarget.hasEffect(NAMobEffects.WARP))
            );
        }
    }
}
