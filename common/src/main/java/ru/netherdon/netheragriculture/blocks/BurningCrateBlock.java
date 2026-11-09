package ru.netherdon.netheragriculture.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BurningCrateBlock extends Block
{

    private static final float FLAME_OFFSET = 3.5f;
    private static final float FLAME_SPREAD = 9f;
    private static final float FLAME_Y_OFFSET = 1.1f;
    private static final float FLAME_Y_OFFSET_BOTTOM = 0.6f;

    public BurningCrateBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity)
    {
        damageWhileWalking(level, entity);
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource)
    {
        super.animateTick(blockState, level, blockPos, randomSource);
        spawnFlameParticle(level, blockPos, randomSource, false);
    }

    public static void damageWhileWalking(Level level, Entity entity)
    {
        if (entity instanceof LivingEntity)
        {
            entity.hurt(level.damageSources().hotFloor(), 1.0F);
        }
    }

    public static void spawnFlameParticle(Level level, BlockPos pos, RandomSource random, boolean bottom)
    {
        if (random.nextInt(2) != 0)
        {
            return;
        }

        float yOffset = FLAME_Y_OFFSET;
        if (bottom)
        {
            yOffset = FLAME_Y_OFFSET_BOTTOM;
        }
        else if (!canBurnAt(level, pos))
        {
            return;
        }

        float x = pos.getX() + (FLAME_OFFSET + FLAME_SPREAD * random.nextFloat()) / 16f;
        float y = pos.getY() + yOffset;
        float z = pos.getZ() + (FLAME_OFFSET + FLAME_SPREAD * random.nextFloat()) / 16f;

        level.addParticle(ParticleTypes.FLAME, x, y, z, 0d, 0d, 0d);
        level.addParticle(ParticleTypes.SMOKE, x, y, z, 0d, 0d, 0d);
    }

    private static boolean canBurnAt(Level level, BlockPos pos)
    {
        BlockState stateAbove = level.getBlockState(pos.above());
        return !stateAbove.isFaceSturdy(level, pos, Direction.DOWN)
            && stateAbove.getFluidState().isEmpty();
    }
}
