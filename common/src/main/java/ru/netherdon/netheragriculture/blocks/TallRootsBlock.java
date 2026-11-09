package ru.netherdon.netheragriculture.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TallRootsBlock extends DoublePlantBlock
{

    public TallRootsBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return state.is(BlockTags.NYLIUM) || state.is(Blocks.SOUL_SOIL) || super.mayPlaceOn(state, level, pos);
    }

}
