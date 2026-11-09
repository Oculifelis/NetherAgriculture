package ru.netherdon.netheragriculture.client;

import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.StandingSignRenderer;
import ru.netherdon.netheragriculture.blocks.entities.IBlockEntityRendererRegister;
import ru.netherdon.netheragriculture.client.particles.FlameEffectParticleProvider;
import ru.netherdon.netheragriculture.client.particles.GlowingSporeParticle;
import ru.netherdon.netheragriculture.client.particles.GlowingSporesBlockMarker;
import ru.netherdon.netheragriculture.client.particles.IParticleProviderRegister;
import ru.netherdon.netheragriculture.client.screen.BlackFurnaceScreen;
import ru.netherdon.netheragriculture.client.screen.IMenuScreenRegister;
import ru.netherdon.netheragriculture.registries.NABlockEntityTypes;
import ru.netherdon.netheragriculture.registries.NAMenuTypes;
import ru.netherdon.netheragriculture.registries.NAParticleTypes;

public class NetherAgricultureClient
{
    public static void initialize()
    {

    }

    public static void registerScreens(IMenuScreenRegister register)
    {
        register.register(NAMenuTypes.BLACK_FURNACE.value(), BlackFurnaceScreen::new);
    }

    public static void registerParticleProviders(IParticleProviderRegister register)
    {
        register.register(NAParticleTypes.FLAME_EFFECT.value(), FlameEffectParticleProvider::new);
        register.register(NAParticleTypes.GLOWING_SPORE_AIR.value(), GlowingSporeParticle.GlowingSporeAirProvider::new);
        register.register(NAParticleTypes.GLOWING_SPORES_MARKER.value(), GlowingSporesBlockMarker.Provider::new);
    }

    public static void registerBlockEntityRenderers(IBlockEntityRendererRegister register)
    {
        register.register(NABlockEntityTypes.CUSTOM_SIGN.value(), StandingSignRenderer::new);
        register.register(NABlockEntityTypes.CUSTOM_HANGING_SIGN.value(), HangingSignRenderer::new);
    }
}
