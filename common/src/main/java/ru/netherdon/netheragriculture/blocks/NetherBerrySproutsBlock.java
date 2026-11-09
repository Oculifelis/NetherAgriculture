package ru.netherdon.netheragriculture.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NetherBerrySproutsBlock extends VegetationBlock
{

    protected static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 4, 14);

    public NetherBerrySproutsBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return state.is(BlockTags.NYLIUM) || state.is(Blocks.SOUL_SOIL) || super.mayPlaceOn(state, level, pos);
    }

}
