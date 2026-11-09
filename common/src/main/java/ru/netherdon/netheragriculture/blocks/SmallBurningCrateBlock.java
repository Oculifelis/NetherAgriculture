package ru.netherdon.netheragriculture.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;

public class SmallBurningCrateBlock extends SlabBlock
{

    public SmallBurningCrateBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity)
    {
        BurningCrateBlock.damageWhileWalking(level, entity);
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource)
    {
        super.animateTick(blockState, level, blockPos, randomSource);
        boolean bottom = blockState.getValue(TYPE) == SlabType.BOTTOM;
        if (!blockState.getValue(WATERLOGGED) || !bottom)
        {
            BurningCrateBlock.spawnFlameParticle(level, blockPos, randomSource, bottom);
        }
    }

}
