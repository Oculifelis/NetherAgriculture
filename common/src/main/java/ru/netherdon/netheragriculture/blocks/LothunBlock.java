package ru.netherdon.netheragriculture.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import ru.netherdon.netheragriculture.registries.NATags;

public class LothunBlock extends NetherCropBlock
{

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]
    {
        box(0f, 0f, 0f, 16f, 2f,16f),
        box(0f, 0f, 0f, 16f, 3f,16f),
        box(0f, 0f, 0f, 16f, 4f,16f),
        box(0f, 0f, 0f, 16f, 5f,16f),
        box(0f, 0f, 0f, 16f, 6f,16f),
        box(0f, 0f, 0f, 16f, 7f,16f),
        box(0f, 0f, 0f, 16f, 8f,16f),
        box(0f, 0f, 0f, 16f, 9f,16f)
    };

    public LothunBlock(Properties properties)
    {
        super(NATags.Blocks.LOTHUN_FERTILE_SOILS, properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        int age = this.getAge(state);
        return SHAPE_BY_AGE[age];
    }

}
