package ru.netherdon.netheragriculture.client.particles;

import net.minecraft.util.RandomSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleLimit;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import ru.netherdon.netheragriculture.client.particles.options.GlowingSporeAirParticleOptions;

import java.util.List;
import java.util.Optional;

public class GlowingSporeParticle extends SuspendedParticle implements ISourcedParticle
{
    private static final float SPEED = 0.03f;
    private static final int FADE_TIME = 10;

    private final List<LifetimeAlpha> faders;
    private final BlockPos sourcePos;
    private final Block sourceBlock;

    public GlowingSporeParticle(ClientLevel level, BlockPos sourcePos, Block sourceBlock, TextureAtlasSprite sprite, List<LifetimeAlpha> faders, double x, double y, double z, double dx, double dy, double dz)
    {
        super(level, x, y, z, dx, dy, dz, sprite);
        this.sourcePos = sourcePos;
        this.sourceBlock = sourceBlock;
        this.faders = faders;
        this.setColor(1f, 1f, 1f);
        this.setAlpha(0f);
    }

    @Override
    public Optional<ParticleLimit> getParticleLimit()
    {
        return Optional.of(NAParticleGroups.GLOWING_SPORES);
    }

    @Override
    public BlockPos getSourcePos()
    {
        return this.sourcePos;
    }

    @Override
    public void sourceChanged(BlockState newState)
    {
        if (!newState.is(this.sourceBlock))
        {
            int newAge = this.lifetime - Math.min(this.age, FADE_TIME);
            if (this.age < newAge)
            {
                this.age = newAge;
            }
        }
    }

    @Override
    public void extract(QuadParticleRenderState renderState, Camera camera, float partialTick)
    {
        this.setAlpha(
            this.faders.stream()
                .map((fader) -> fader.currentAlphaForAge(this.age, this.lifetime, partialTick))
                .filter((alpha) -> alpha != 1f)
                .findFirst()
                .orElse(1f)
        );

        super.extract(renderState, camera, partialTick);
    }

    @Override
    protected int getLightCoords(float partialTick)
    {
        int oldLight = super.getLightCoords(partialTick);
        int j = oldLight & 0xff;
        int k = oldLight >> 16 & 0xff;
        j += (int)(this.alpha * 15f * 16f);
        if (j > 240)
        {
            j = 240;
        }

        return j | k << 16;
    }

    @Override
    public SingleQuadParticle.Layer getLayer()
    {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    public static class GlowingSporeAirProvider implements ParticleProvider<GlowingSporeAirParticleOptions>
    {
        private final SpriteSet sprites;

        public GlowingSporeAirProvider(SpriteSet sprites)
        {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(GlowingSporeAirParticleOptions options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random)
        {
            int lifetime = Mth.randomBetweenInclusive(random, 60, 100);
            LifetimeAlpha faderIn = new LifetimeAlpha(0f, 1f, 0f, (float)FADE_TIME / lifetime);
            LifetimeAlpha faderOut = new LifetimeAlpha(1f, 0f, (float)(lifetime - FADE_TIME) / lifetime, 1f);

            var particle = new GlowingSporeParticle(level, options.pos(), options.block(), this.sprites.get(random), List.of(faderIn, faderOut), x, y, z, xSpeed, ySpeed, zSpeed);
            particle.setLifetime(lifetime);
            particle.xd *= SPEED;
            particle.yd *= SPEED;
            particle.zd *= SPEED;
            return particle;
        }
    }
}
