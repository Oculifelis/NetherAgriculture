package ru.netherdon.netheragriculture.blocks;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ScheduledTickAccess;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import ru.netherdon.netheragriculture.registries.NABlocks;
import ru.netherdon.netheragriculture.registries.NAItems;

import java.util.EnumMap;
import java.util.Map;

public class AzureMelonCropBlock extends Block
{
    public static final MapCodec<AzureMelonCropBlock> CODEC = simpleCodec(AzureMelonCropBlock::new);

    public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final VoxelShape SHAPE_EAST = box(10, 0, 5, 16, 9, 11);
    public static final VoxelShape SHAPE_WEST = box(0, 0, 5, 6, 9, 11);
    public static final VoxelShape SHAPE_SOUTH = box(5, 0, 10, 11, 9, 16);
    public static final VoxelShape SHAPE_NORTH = box(5, 0, 0, 11, 9, 6);

    public static final Map<Direction, VoxelShape> SHAPE_BY_FACING = new EnumMap<>(Map.of(
        Direction.NORTH, SHAPE_NORTH,
        Direction.SOUTH, SHAPE_SOUTH,
        Direction.WEST, SHAPE_WEST,
        Direction.EAST, SHAPE_EAST
    ));

    public static final VoxelShape ATTACHED_SHAPE_EAST = box(10, 0, 5, 16, 13, 11);
    public static final VoxelShape ATTACHED_SHAPE_WEST = box(0, 0, 5, 6, 13, 11);
    public static final VoxelShape ATTACHED_SHAPE_SOUTH = box(5, 0, 10, 11, 13, 16);
    public static final VoxelShape ATTACHED_SHAPE_NORTH = box(5, 0, 0, 11, 13, 6);

    public static final Map<Direction, VoxelShape> ATTACHED_SHAPE_BY_FACING = new EnumMap<>(Map.of(
        Direction.NORTH, ATTACHED_SHAPE_NORTH,
        Direction.SOUTH, ATTACHED_SHAPE_SOUTH,
        Direction.WEST, ATTACHED_SHAPE_WEST,
        Direction.EAST, ATTACHED_SHAPE_EAST
    ));

    public AzureMelonCropBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource random)
    {
        if (!facingState.is(NABlocks.ATTACHED_AZURE_MELON_STEM.value()) && facing == state.getValue(FACING))
        {
            return state.setValue(ATTACHED, false);
        }

        return super.updateShape(state, level, ticks, currentPos, facing, facingPos, facingState, random);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        Direction facing = state.getValue(FACING);
        boolean attached = state.getValue(ATTACHED);
        return (attached ? ATTACHED_SHAPE_BY_FACING : SHAPE_BY_FACING).get(facing);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockPos posBelow = pos.below();
        return level.getBlockState(posBelow).isFaceSturdy(level, posBelow, Direction.UP);
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean includeData)
    {
        return NAItems.AZURE_MELON.value().getDefaultInstance();
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation)
    {
        Direction facing = state.getValue(FACING);
        return state.setValue(FACING, rotation.rotate(facing));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror)
    {
        Direction facing = state.getValue(FACING);
        return state.setValue(FACING, mirror.mirror(facing));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(ATTACHED, FACING);
    }

    @Override
    protected MapCodec<? extends AzureMelonCropBlock> codec()
    {
        return CODEC;
    }
}
