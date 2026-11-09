package ru.netherdon.netheragriculture.client.particles;

import net.minecraft.util.RandomSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import ru.netherdon.netheragriculture.blocks.GlowingSporesBlock;
import ru.netherdon.netheragriculture.registries.NABlocks;

public class GlowingSporesBlockMarker extends SingleQuadParticle implements ISourcedParticle
{
    private final BlockPos sourcePos;
    private final GlowingSporesBlock sourceBlock;

    protected GlowingSporesBlockMarker(ClientLevel level, GlowingSporesBlock sourceBlock, TextureAtlasSprite sprite, double x, double y, double z)
    {
        super(level, x, y, z, sprite);
        this.sourceBlock = sourceBlock;
        this.gravity = 0f;
        this.lifetime = 80;
        this.hasPhysics = false;
        this.sourcePos = BlockPos.containing(x, y, z);
    }

    @Override
    public BlockPos getSourcePos()
    {
        return this.sourcePos;
    }

    @Override
    public void sourceChanged(BlockState newState)
    {
        if (!newState.is(NABlocks.GLOWING_SPORES.value()))
        {
            this.remove();
        }
    }

    @Override
    public void tick()
    {
        if (this.isAlive())
        {
            ItemStack stack = Minecraft.getInstance().player.getMainHandItem();
            if (!this.sourceBlock.canInteractWith(stack))
            {
                this.remove();
            }
        }
        super.tick();
    }

    @Override
    public SingleQuadParticle.Layer getLayer()
    {
        return SingleQuadParticle.Layer.OPAQUE;
    }

    @Override
    public float getQuadSize(float f)
    {
        return 0.5f;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType>
    {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites)
        {
            this.sprites = sprites;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double dx, double dy, double dz, RandomSource random)
        {
            return new GlowingSporesBlockMarker(level, NABlocks.GLOWING_SPORES.value(), this.sprites.get(random), x, y, z);
        }
    }
}
