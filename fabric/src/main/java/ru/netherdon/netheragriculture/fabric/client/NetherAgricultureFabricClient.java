package ru.netherdon.netheragriculture.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.impl.client.rendering.BlockEntityRendererRegistryImpl;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import ru.netherdon.netheragriculture.blocks.entities.IBlockEntityRendererRegister;
import ru.netherdon.netheragriculture.client.NetherAgricultureClient;
import ru.netherdon.netheragriculture.client.particles.IParticleProviderRegister;
import ru.netherdon.netheragriculture.fabric.registries.NABlockRenderTypes;

import java.util.function.Function;

public final class NetherAgricultureFabricClient implements ClientModInitializer
{
    private static final IParticleProviderRegister PARTICLE_PROVIDER_REGISTER = new IParticleProviderRegister()
    {
        @Override
        public <T extends ParticleOptions> void register(ParticleType<T> type, Function<SpriteSet, ParticleProvider<T>> factory)
        {
            ParticleProviderRegistry.getInstance().register(type, factory::apply);
        }
    };

    @Override
    public void onInitializeClient()
    {
        NetherAgricultureClient.initialize();
        NABlockRenderTypes.initialize();
        NetherAgricultureClient.registerScreens(MenuScreens::register);
        NetherAgricultureClient.registerParticleProviders(PARTICLE_PROVIDER_REGISTER);
        NetherAgricultureClient.registerBlockEntityRenderers(BlockEntityRenderers::register);
        // 26.2: sign boards are block models now (assets/.../models/block/glowing_*sign*), no sprite registration
    }
}
