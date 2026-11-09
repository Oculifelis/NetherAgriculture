package ru.netherdon.netheragriculture.blocks;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import ru.netherdon.netheragriculture.registries.NATags;

public class BlazeFruitBlock extends NetherCropBlock
{
    public static final MapCodec<BlazeFruitBlock> CODEC = simpleCodec(BlazeFruitBlock::new);

    public static final BooleanProperty SOUL = BooleanProperty.create("soul");

    private static final VoxelShape SHAPE0 = box(5f, 0f, 5f, 11f, 2f,11f);
    private static final VoxelShape SHAPE1 = box(4f, 0f, 4f, 12f, 2f,12f);
    private static final VoxelShape SHAPE2 = box(3f, 0f, 3f, 13f, 3f,13f);
    private static final VoxelShape SHAPE3 = box(2f, 0f, 2f, 14f, 5f,14f);

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]
    {
        SHAPE0, SHAPE0, SHAPE1, SHAPE1, SHAPE2, SHAPE2, SHAPE2, SHAPE3,
    };

    public BlazeFruitBlock(Properties properties)
    {
        super(NATags.Blocks.BLAZE_FRUIT_FERTILE_SOILS, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(SOUL, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos posBelow = context.getClickedPos().below();
        BlockState stateBelow = context.getLevel().getBlockState(posBelow);
        return this.defaultBlockState()
            .setValue(SOUL, stateBelow.is(BlockTags.SOUL_FIRE_BASE_BLOCKS));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return this.isFertileSoil(state) || super.mayPlaceOn(state, level, pos);
    }

    @Override
    public void growCrops(Level level, BlockPos pos, BlockState state)
    {
        int age = Math.min(
            this.getAge(state) + this.getBonemealAgeIncrease(level),
            this.getMaxAge()
        );
        BlockState newState = this.withAge(state, age);
        level.setBlock(pos, newState, 2);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
        super.animateTick(state, level, pos, random);
        if (!this.isMaxAge(state))
        {
            return;
        }

        Direction[] faces = new Direction[] { Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST };
        Direction face = faces[random.nextInt(faces.length)];
        Direction.Axis axis = face.getAxis();
        double xOffset = 0d, yOffset = 0d, zOffset = 0d;
        switch (axis)
        {
            case X:
                xOffset = 0.5d + 0.3d * face.getUnitVec3i().getX();
                yOffset = 0.3625d * random.nextFloat();
                zOffset = 0.2d + 0.6d * random.nextFloat();
                break;
            case Y:
                xOffset = 0.2d + 0.6d * random.nextFloat();
                yOffset = 0.3625d;
                zOffset = 0.2d + 0.6d * random.nextFloat();
                break;
            case Z:
                xOffset = 0.2d + 0.6d * random.nextFloat();
                yOffset = 0.3625d * random.nextFloat();
                zOffset = 0.5d + 0.3d * face.getUnitVec3i().getZ();
                break;
        }

        ParticleOptions options = state.getValue(SOUL) ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME;
        level.addParticle(options, pos.getX() + xOffset, pos.getY() + yOffset, pos.getZ() + zOffset, 0d, 0d, 0d);
        level.addParticle(ParticleTypes.SMOKE, pos.getX() + xOffset, pos.getY() + yOffset, pos.getZ() + zOffset, 0d, 0d, 0d);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise)
    {
        if (this.isMaxAge(state))
        {
            if (!entity.fireImmune())
            {
                entity.setRemainingFireTicks(entity.getRemainingFireTicks() + 1);
                if (entity.getRemainingFireTicks() == 0)
                {
                    entity.igniteForSeconds(3.0f);
                }
            }

            // 26.x: hurt() is server-only (hurtServer); 1.21.1 hurt() did nothing on the client either
            if (level instanceof ServerLevel serverLevel)
            {
                entity.hurtServer(serverLevel, level.damageSources().inFire(), 1f);
            }
        }
        super.entityInside(state, level, pos, entity, effectApplier, isPrecise);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        int age = this.getAge(state);
        return SHAPE_BY_AGE[age];
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(SOUL);
    }

    @Override
    public MapCodec<? extends BlazeFruitBlock> codec()
    {
        return CODEC;
    }
}
