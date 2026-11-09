package ru.netherdon.netheragriculture.neoforge.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import ru.netherdon.netheragriculture.NetherAgriculture;

@EventBusSubscriber(modid = NetherAgriculture.ID)
public class FeatureEvents
{
    @SubscribeEvent
    public static void modify(AddServerReloadListenersEvent event)
    {
    }
}
