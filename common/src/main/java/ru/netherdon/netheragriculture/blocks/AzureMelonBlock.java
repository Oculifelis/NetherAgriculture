package ru.netherdon.netheragriculture.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AzureMelonBlock extends Block
{

    public static final VoxelShape SHAPE_1 = box(5, 0, 5, 11, 9, 11);
    private static final VoxelShape SHAPE_2 = Shapes.or(
        box(1, 0, 4, 7, 9, 10),
        box(8, 0, 7, 14, 9, 13)
    );
    private static final VoxelShape SHAPE_3 = Shapes.or(
        box(1, 0, 5, 7, 9, 11),
        box(8, 0, 1, 14, 9, 7),
        box(9, 0, 8, 15, 9, 14)
    );
    private static final VoxelShape SHAPE_4 = Shapes.or(
        box(1, 0, 2, 7, 9, 8),
        box(2, 0, 9, 8, 9, 15),
        box(8, 0, 1, 14, 9, 7),
        box(9, 0, 8, 15, 9, 14)
    );
    private static final VoxelShape[] SHAPE_BY_COUNT = new VoxelShape[] {
        SHAPE_1, SHAPE_2, SHAPE_3, SHAPE_4
    };

    public static final int MIN_MELONS = 1;
    public static final int MAX_MELONS = 4;

    public static final IntegerProperty MELONS = IntegerProperty.create("melons", MIN_MELONS, MAX_MELONS);

    public AzureMelonBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(MELONS, 1));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        int count = state.getValue(MELONS);
        return SHAPE_BY_COUNT[count - 1];
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockPos posBelow = pos.below();
        return level.getBlockState(posBelow).isFaceSturdy(level, posBelow, Direction.UP);
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext useContext)
    {
        return useContext.getItemInHand().is(this.asItem()) && state.getValue(MELONS) < MAX_MELONS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        return blockstate.is(this)
            ? blockstate.setValue(MELONS, Math.min(MAX_MELONS, blockstate.getValue(MELONS) + 1))
            : super.getStateForPlacement(context);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(MELONS);
    }

}
