package ru.netherdon.netheragriculture.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.netherdon.netheragriculture.client.particles.ISourcedParticle;
import ru.netherdon.netheragriculture.client.particles.event.SourcedParticleManager;

import java.util.Collection;

@Mixin(ParticleEngine.class)
public class ParticleEngineMixin
{
    @Inject(method = "add", at = @At(value = "INVOKE", target = "Ljava/util/Queue;add(Ljava/lang/Object;)Z"))
    public void addSourcedParticle(Particle particle, CallbackInfo ci)
    {
        if (particle instanceof ISourcedParticle sourcedParticle)
        {
            SourcedParticleManager.add(sourcedParticle);
        }
    }

}
