package ru.netherdon.netheragriculture.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import ru.netherdon.netheragriculture.registries.NADamageSources;

public class MortofructBlock extends Block implements Fallable
{

    public static final BooleanProperty WILD = BooleanProperty.create("wild");

    public MortofructBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WILD, false));
    }

    @Override
    protected void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile)
    {
        super.onProjectileHit(level, state, hit, projectile);
        level.scheduleTick(hit.getBlockPos(), this, 2);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        super.tick(state, level, pos, random);
        if (state.getValue(WILD) && FallingBlock.isFree(level.getBlockState(pos.below())))
        {
            FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(level, pos, state);
            fallingblockentity.disableDrop();
            fallingblockentity.setHurtsEntities(1f, 30);
        }
    }

    @Override
    public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity fallingBlock)
    {
        BlockState state = fallingBlock.getBlockState();
        level.levelEvent(2001, pos, Block.getId(state));
        Block.dropResources(state, level, pos);
    }

    @Override
    public DamageSource getFallDamageSource(Entity entity)
    {
        return NADamageSources.mortofruct(entity);
    }

    public void onVineBroken(Level level, BlockPos pos, BlockState state)
    {
        level.scheduleTick(pos, this, 2);
    }

    public BlockState getWildState()
    {
        return this.defaultBlockState().setValue(WILD, true);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(WILD);
    }

}
