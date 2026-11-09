package ru.netherdon.netheragriculture.neoforge.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;
import ru.netherdon.netheragriculture.NetherAgriculture;
import ru.netherdon.netheragriculture.events.BlockEventHandler;

@EventBusSubscriber(modid = NetherAgriculture.ID)
public final class BlockEventHandlerImpl
{
    @SubscribeEvent
    private static void applyBoneMeal(BonemealEvent event)
    {
        if (BlockEventHandler.applyBoneMeal(event.getState(), event.getLevel(), event.getPos(), event.getStack()))
        {
            event.setSuccessful(true);
        }
    }
}
