package ru.netherdon.netheragriculture.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import ru.netherdon.netheragriculture.NetherAgriculture;
import ru.netherdon.netheragriculture.config.NACommonConfig;
import ru.netherdon.netheragriculture.config.NAServerConfig;
import ru.netherdon.netheragriculture.neoforge.registries.NADataLoadConditions;
import ru.netherdon.netheragriculture.neoforge.registries.NALootModifiers;
import ru.netherdon.netheragriculture.neoforge.registries.NAEntityAttachments;
import ru.netherdon.netheragriculture.services.neoforge.RegistryManagerImpl;

@Mod(NetherAgriculture.ID)
public class NetherAgricultureNeoForge
{
    public NetherAgricultureNeoForge(IEventBus modEventBus, ModContainer modContainer)
    {
        NAEntityAttachments.TYPES.register(modEventBus);
        NetherAgriculture.initialize();
        NALootModifiers.initialize();
        NADataLoadConditions.initialize();
        RegistryManagerImpl.registerDeferredRegistries(modEventBus);

        modContainer.registerConfig(ModConfig.Type.SERVER, NAServerConfig.getSpec(), NAServerConfig.FILE_NAME);
        modContainer.registerConfig(ModConfig.Type.COMMON, NACommonConfig.getSpec(), NACommonConfig.FILE_NAME);
    }
}
