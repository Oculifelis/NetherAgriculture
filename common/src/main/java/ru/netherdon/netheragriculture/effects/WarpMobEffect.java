package ru.netherdon.netheragriculture.effects;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.level.Level;

public class WarpMobEffect extends MobEffect
{
    public WarpMobEffect(MobEffectCategory category, int color)
    {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity livingEntity, int amplifier)
    {
        Level level = livingEntity.level();
        if (!level.isClientSide())
        {
            RandomSource rand = RandomSource.create();
            if (rand.nextInt(10) == 0)
            {
                Endermite endermite = new Endermite(EntityType.ENDERMITE, level);
                endermite.setPos(livingEntity.position());
                endermite.setTarget(livingEntity);
                level.addFreshEntity(endermite);
            }
        }

        return super.applyEffectTick(serverLevel, livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier)
    {
        int i = 50 >> amplifier;
        return i == 0 || duration % i == 0;
    }
}
