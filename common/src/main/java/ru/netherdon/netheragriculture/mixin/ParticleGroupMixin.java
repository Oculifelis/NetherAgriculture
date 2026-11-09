package ru.netherdon.netheragriculture.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.netherdon.netheragriculture.client.particles.ISourcedParticle;
import ru.netherdon.netheragriculture.client.particles.event.SourcedParticleManager;

@Mixin(ParticleGroup.class)
public class ParticleGroupMixin
{
    @Inject(method = "tickParticles", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;remove()V"))
    public void removeSourcedParticle(CallbackInfo ci, @Local Particle particle)
    {
        if (particle instanceof ISourcedParticle sourcedParticle)
        {
            SourcedParticleManager.remove(sourcedParticle);
        }
    }
}
