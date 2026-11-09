package ru.netherdon.netheragriculture.neoforge.client;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import ru.netherdon.netheragriculture.NetherAgriculture;
import ru.netherdon.netheragriculture.client.NetherAgricultureClient;
import ru.netherdon.netheragriculture.client.particles.IParticleProviderRegister;
import ru.netherdon.netheragriculture.compat.clothconfig.ConfigScreenLoader;

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
@Mod(value = NetherAgriculture.ID, dist = Dist.CLIENT)
public class NetherAgricultureNeoForgeClient
{
    public NetherAgricultureNeoForgeClient(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.register(this);
        NetherAgricultureClient.initialize();

        final var configScreenLoader = ConfigScreenLoader.get();
        if (configScreenLoader != null)
        {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, (mod, parent) -> configScreenLoader.apply(parent));
        }
    }

    @SubscribeEvent
    public void registerScreens(RegisterMenuScreensEvent event)
    {
        NetherAgricultureClient.registerScreens(event::register);
    }

    @SubscribeEvent
    public void registerScreens(RegisterParticleProvidersEvent event)
    {
        NetherAgricultureClient.registerParticleProviders(new IParticleProviderRegister()
        {
            @Override
            public <T extends ParticleOptions> void register(ParticleType<T> type, Function<SpriteSet, ParticleProvider<T>> factory)
            {
                event.registerSpriteSet(type, factory::apply);
            }
        });
    }

    @SubscribeEvent
    public void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        NetherAgricultureClient.registerBlockEntityRenderers(event::registerBlockEntityRenderer);
    }
}
