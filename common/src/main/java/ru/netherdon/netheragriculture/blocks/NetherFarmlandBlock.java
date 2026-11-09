package ru.netherdon.netheragriculture.blocks;

import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import ru.netherdon.netheragriculture.services.EntityService;

public class NetherFarmlandBlock extends Block
{

    public static final VoxelShape SHAPE = box(0, 0, 0, 16, 15, 16);

    public NetherFarmlandBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random)
    {
        if (direction == Direction.UP && !state.canSurvive(level, pos))
        {
            ticks.scheduleTick(pos, this, 1);
        }

        return super.updateShape(state, level, ticks, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        super.tick(state, level, pos, random);
        if (!state.canSurvive(level, pos))
        {
            turnToNetherrack(null, state, level, pos);
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockPos posAbove = pos.above();
        BlockState stateAbove = level.getBlockState(posAbove);
        return !stateAbove.isFaceSturdy(level, posAbove, Direction.DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos())
            ? super.getStateForPlacement(context)
            : Blocks.NETHERRACK.defaultBlockState();
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance)
    {
        if (!level.isClientSide() && EntityService.canTrample(entity, state, pos, (float) fallDistance))
        {
            turnToNetherrack(entity, state, level, pos);
        }

        super.fallOn(level, state, pos, entity, fallDistance);
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state)
    {
        return true;
    }

    public static void turnToNetherrack(@Nullable Entity entity, BlockState state, Level level, BlockPos pos)
    {
        BlockState blockstate = pushEntitiesUp(state, Blocks.NETHERRACK.defaultBlockState(), level, pos);
        level.setBlockAndUpdate(pos, blockstate);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, blockstate));
    }
}
