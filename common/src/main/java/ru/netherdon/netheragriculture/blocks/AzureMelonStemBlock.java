package ru.netherdon.netheragriculture.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import ru.netherdon.netheragriculture.registries.NABlocks;
import ru.netherdon.netheragriculture.registries.NATags;

public class AzureMelonStemBlock extends NetherCropBlock
{

    public static final VoxelShape SHAPE0 = box(5, 0, 5, 11, 2, 11);
    public static final VoxelShape SHAPE1 = box(5, 0, 5, 11, 5, 11);
    public static final VoxelShape SHAPE2 = box(5, 0, 5, 11, 10, 11);
    public static final VoxelShape SHAPE3 = box(5, 0, 5, 11, 13, 11);

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]
    {
        SHAPE0, SHAPE0, SHAPE1, SHAPE1, SHAPE2, SHAPE2, SHAPE2, SHAPE3
    };

    public AzureMelonStemBlock(Properties properties)
    {
        super(NATags.Blocks.AZURE_MELON_FERTILE_SOILS, properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        int age = this.getAge(state);
        return SHAPE_BY_AGE[age];
    }

    @Override
    protected boolean canGrow(BlockState state, ServerLevel level, BlockPos pos)
    {
        return true;
    }

    @Override
    protected void grow(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (!this.isMaxAge(state))
        {
            super.grow(state, level, pos, random);
            return;
        }

        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        BlockPos cropPos = pos.relative(direction);
        BlockPos cropPosBelow = cropPos.below();
        BlockState cropStateBelow = level.getBlockState(cropPosBelow);

        if (level.isEmptyBlock(cropPos) && (
            cropStateBelow.getBlock() instanceof NetherFarmlandBlock
            || cropStateBelow.isFaceSturdy(level, cropPosBelow, Direction.UP)
        ))
        {
            BlockState cropState = NABlocks.AZURE_MELON_CROP.value().defaultBlockState()
                .setValue(AzureMelonCropBlock.FACING, direction.getOpposite())
                .setValue(AzureMelonCropBlock.ATTACHED, true);

            BlockState attachedStemState = NABlocks.ATTACHED_AZURE_MELON_STEM.value().defaultBlockState()
                .setValue(AttachedAzureMelonStemBlock.FACING, direction);

            level.setBlockAndUpdate(cropPos, cropState);
            level.setBlockAndUpdate(pos, attachedStemState);
        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state)
    {
        return true;
    }

}
