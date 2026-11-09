package ru.netherdon.netheragriculture.blocks;

import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import ru.netherdon.netheragriculture.registries.NABlocks;

public class DeadVinesBlock extends Block implements BonemealableBlock
{

    public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
    public static final BooleanProperty SHOOTS = BooleanProperty.create("shoots");
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public static final int MAX_AGE = 3;
    public static final float GROWTH_PROBABILITY = 0.15f;

    public static final VoxelShape SHAPE = box(4, 0, 4, 12, 16, 12);
    public static final VoxelShape HEAD_SHAPE = box(4, 3, 4, 12, 16, 12);

    public DeadVinesBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ATTACHED, false).setValue(SHOOTS, false));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return state.getValue(ATTACHED) ? SHAPE : HEAD_SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos posAbove = context.getClickedPos().above();
        BlockState stateAbove = context.getLevel().getBlockState(posAbove);
        int age = stateAbove.is(this)
            ? this.nextAge(stateAbove.getValue(AGE))
            : context.getLevel().getRandom().nextInt(MAX_AGE + 1);

        boolean attached = this.checkAttachmentForPlace(context.getLevel(), context.getClickedPos());
        return this.defaultBlockState().setValue(AGE, age).setValue(ATTACHED, attached);
    }

    private boolean checkAttachmentForPlace(Level level, BlockPos pos)
    {
        BlockPos posBelow = pos.below();
        BlockState stateBelow = level.getBlockState(posBelow);
        return stateBelow.is(this) || stateBelow.is(NABlocks.MORTOFRUCT.value());
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random)
    {
        if (!this.canSurvive(state, level, pos))
        {
            return Blocks.AIR.defaultBlockState();
        }

        if (direction == Direction.DOWN)
        {
            boolean attached = neighborState.is(this) || neighborState.is(NABlocks.MORTOFRUCT.value());
            return state.setValue(ATTACHED, attached);
        }

        return super.updateShape(state, level, ticks, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockPos posAbove = pos.above();
        BlockState stateAbove = level.getBlockState(posAbove);
        return stateAbove.is(this) || stateAbove.isFaceSturdy(level, posAbove, Direction.DOWN);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        super.randomTick(state, level, pos, random);
        if (random.nextFloat() < GROWTH_PROBABILITY)
        {
            BlockPos posBelow = pos.below();
            BlockState stateBelow = level.getBlockState(posBelow);
            if (state.getValue(SHOOTS))
            {
                if (stateBelow.isAir())
                {
                    level.setBlockAndUpdate(posBelow, NABlocks.MORTOFRUCT.value().getWildState());
                }
            }
            else
            {
                int age = state.getValue(AGE);
                if (age == 3 || !stateBelow.isAir())
                {
                    level.setBlockAndUpdate(pos, state.setValue(SHOOTS, true));
                    return;
                }

                level.setBlockAndUpdate(posBelow,
                    state.setValue(AGE, this.nextAge(age))
                        .setValue(ATTACHED, this.checkAttachmentForPlace(level, posBelow))
                );
            }
        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state)
    {
        return super.isRandomlyTicking(state) && !state.getValue(ATTACHED);
    }

    // 26.x: onRemove(state, level, pos, newState, moved) is gone. Vanilla now calls affectNeighborsAfterRemoval
    // only when the block really changed, which matches the original "!newState.is(state.getBlock())" check.
    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean movedByPiston)
    {
        super.affectNeighborsAfterRemoval(state, level, pos, movedByPiston);

        BlockPos posBelow = pos.below();
        BlockState stateBelow = level.getBlockState(posBelow);
        if (stateBelow.is(NABlocks.MORTOFRUCT.value()))
        {
            NABlocks.MORTOFRUCT.value().onVineBroken(level, posBelow, stateBelow);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, BonemealSource source)
    {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        while (true)
        {
            BlockState state2 = level.getBlockState(mutablePos);
            if (!state2.is(this))
            {
                return false;
            }

            if (!state2.getValue(ATTACHED))
            {
                return !state2.getValue(SHOOTS)
                    || level.getBlockState(mutablePos.below()).isAir();
            }

            mutablePos.move(0, -1, 0);
        }
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source)
    {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        while (true)
        {
            BlockState state2 = level.getBlockState(mutablePos);
            if (!state2.is(this))
            {
                return;
            }

            if (!state2.getValue(ATTACHED))
            {
                if (state2.getValue(SHOOTS))
                {
                    level.setBlockAndUpdate(mutablePos.below(), NABlocks.MORTOFRUCT.value().getWildState());
                    return;
                }

                int age = state2.getValue(AGE);
                if (age >= MAX_AGE || !level.getBlockState(mutablePos.below()).isAir())
                {
                    level.setBlockAndUpdate(mutablePos, state2.setValue(SHOOTS, true));
                    return;
                }

                mutablePos.move(0, -1, 0);
                level.setBlockAndUpdate(mutablePos, this.defaultBlockState()
                    .setValue(AGE, this.nextAge(age))
                    .setValue(ATTACHED, this.checkAttachmentForPlace(level, mutablePos)));
                return;
            }

            mutablePos.move(0, -1, 0);
        }
    }

    private int nextAge(int age)
    {
        return Math.min(MAX_AGE, age + 1);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source)
    {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(ATTACHED, SHOOTS, AGE);
    }

}
