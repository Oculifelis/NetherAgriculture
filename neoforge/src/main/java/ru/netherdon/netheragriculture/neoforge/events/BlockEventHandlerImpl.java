package ru.netherdon.netheragriculture.neoforge.events;

import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import ru.netherdon.netheragriculture.NetherAgriculture;
import ru.netherdon.netheragriculture.events.BlockEventHandler;
import ru.netherdon.netheragriculture.registries.NABlocks;

import java.util.Map;

@EventBusSubscriber(modid = NetherAgriculture.ID)
public final class BlockEventHandlerImpl
{
    private static final Lazy<Map<Block, Block>> MY_STRIPPABLES = Lazy.of(() -> Map.of(
        NABlocks.GLOWING_STEM.value(), NABlocks.STRIPPED_GLOWING_STEM.value(),
        NABlocks.GLOWING_HYPHAE.value(), NABlocks.STRIPPED_GLOWING_HYPHAE.value()
    ));

    @SubscribeEvent
    private static void hoeTillModification(BlockEvent.BlockToolModificationEvent event)
    {
        if (event.getItemAbility() == ItemAbilities.HOE_TILL)
        {
            BlockState state = BlockEventHandler.hoeTill(event.getState());
            if (state != null)
            {
                event.setFinalState(state);
            }
        }
        else if (event.getItemAbility() == ItemAbilities.AXE_STRIP)
        {
            BlockState originalState = event.getState();
            if (MY_STRIPPABLES.get().containsKey(originalState.getBlock()))
            {
                Block strippedBlock = MY_STRIPPABLES.get().get(originalState.getBlock());
                BlockState newState = strippedBlock
                    .defaultBlockState()
                    .setValue(
                        RotatedPillarBlock.AXIS,
                        event.getState().getValue(RotatedPillarBlock.AXIS)
                    );
                event.setFinalState(newState);
            }
        }
    }

    @SubscribeEvent
    private static void applyBoneMeal(BonemealEvent event)
    {
        if (BlockEventHandler.applyBoneMeal(event.getState(), event.getLevel(), event.getPos(), event.getStack()))
        {
            event.setSuccessful(true);
        }
    }
}
