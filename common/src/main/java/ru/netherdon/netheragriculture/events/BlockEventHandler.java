package ru.netherdon.netheragriculture.events;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.util.Lazy;
import org.jetbrains.annotations.Nullable;
import ru.netherdon.netheragriculture.blocks.NetherFarmlandBlock;
import ru.netherdon.netheragriculture.registries.NABlocks;

import java.util.Map;

public final class BlockEventHandler
{
    private static final Lazy<Map<Block, NetherFarmlandBlock>> FARMLANDS = Lazy.lazy(() -> ImmutableMap.of(
        Blocks.CRIMSON_NYLIUM, NABlocks.CRIMSON_FARMLAND.value(),
        Blocks.WARPED_NYLIUM, NABlocks.WARPED_FARMLAND.value()
    ));

    private static final Lazy<Map<Block, DoublePlantBlock>> DOUBLE_PLANTS = Lazy.lazy(() -> ImmutableMap.of(
        Blocks.CRIMSON_ROOTS, NABlocks.TALL_CRIMSON_ROOTS.value(),
        Blocks.WARPED_ROOTS, NABlocks.TALL_WARPED_ROOTS.value()
    ));

    @Nullable
    public static BlockState hoeTill(BlockState state)
    {
        if (FARMLANDS.get().containsKey(state.getBlock()))
        {
            return FARMLANDS.get().get(state.getBlock()).defaultBlockState();
        }

        return null;
    }

    public static boolean applyBoneMeal(BlockState state, Level level, BlockPos pos, ItemStack boneMealStack)
    {
        Block originalBlock = state.getBlock();
        if (DOUBLE_PLANTS.get().containsKey(originalBlock))
        {
            if (level.isClientSide())
            {
                ParticleUtils.spawnParticleInBlock(level, pos, 15, ParticleTypes.HAPPY_VILLAGER);
            }
            else
            {
                DoublePlantBlock doublePlant = DOUBLE_PLANTS.get().get(originalBlock);
                BlockState doublePlantState = doublePlant.defaultBlockState();
                if (doublePlantState.canSurvive(level, pos) && level.isEmptyBlock(pos.above()))
                {
                    DoublePlantBlock.placeAt(level, doublePlantState, pos, 2);
                }
                boneMealStack.shrink(1);
            }

            return true;
        }

        return false;
    }
}
