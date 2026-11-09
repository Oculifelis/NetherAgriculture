package ru.netherdon.netheragriculture.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import ru.netherdon.netheragriculture.NetherAgriculture;
import ru.netherdon.netheragriculture.config.NACommonConfig;
import ru.netherdon.netheragriculture.config.NAServerConfig;
import ru.netherdon.netheragriculture.registries.NACompostables;
import ru.netherdon.netheragriculture.neoforge.registries.NADataLoadConditions;
import ru.netherdon.netheragriculture.neoforge.registries.NALootModifiers;
import ru.netherdon.netheragriculture.services.neoforge.RegistryManagerImpl;

@Mod(NetherAgriculture.ID)
public class NetherAgricultureNeoForge
{
    public NetherAgricultureNeoForge(IEventBus modEventBus, ModContainer modContainer)
    {
        NetherAgriculture.initialize();
        NALootModifiers.initialize();
        NADataLoadConditions.initialize();
        RegistryManagerImpl.registerDeferredRegistries(modEventBus);
        modEventBus.addListener(NetherAgricultureNeoForge::modifyDefaultComponents);

        modContainer.registerConfig(ModConfig.Type.SYNCED, NAServerConfig.getSpec(), NAServerConfig.FILE_NAME);
        modContainer.registerConfig(ModConfig.Type.LOCAL, NACommonConfig.getSpec(), NACommonConfig.FILE_NAME);
    }

    private static void modifyDefaultComponents(ModifyDefaultComponentsEvent event)
    {
        // Composter chances moved from the compostables data map to the item component in 26.3.
        NACompostables.forEach((item, compostable) ->
            event.modify(item, (builder, registries, it) -> NACompostables.apply(builder, compostable)));
    }
}
