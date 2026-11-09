package ru.netherdon.netheragriculture.client.particles;

import net.minecraft.util.RandomSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public record FlameEffectParticleProvider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType>
{
    @Override
    public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double x, double y, double z, double g, double h, double i, RandomSource random)
    {
        return new SpellParticle(clientLevel, x, y, z, g, h, i, this.sprites());
    }
}
