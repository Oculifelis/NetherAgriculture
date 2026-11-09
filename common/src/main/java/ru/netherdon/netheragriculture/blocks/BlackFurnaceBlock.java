package ru.netherdon.netheragriculture.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import ru.netherdon.netheragriculture.blocks.entities.BlackFurnaceBlockEntity;
import ru.netherdon.netheragriculture.registries.NABlockEntityTypes;

public class BlackFurnaceBlock extends AbstractFurnaceBlock
{

    public BlackFurnaceBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player)
    {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof BlackFurnaceBlockEntity blackFurnace)
        {
            player.openMenu(blackFurnace);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random)
    {
        if (state.getValue(LIT))
        {
            double x = (double)pos.getX() + 0.5;
            double y = (double)pos.getY();
            double z = (double)pos.getZ() + 0.5;
            if (random.nextDouble() < 0.1)
            {
                level.playLocalSound(x, y, z, SoundEvents.SMOKER_SMOKE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            level.addParticle(ParticleTypes.SMOKE, x, y + 1.1, z, 0.0, 0.0, 0.0);

            Direction direction = state.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double step = 0.52d;
            double defaultStep = random.nextDouble() * 0.6d - 0.3d;
            double xOffset = direction$axis == Direction.Axis.X
                ? (double)direction.getStepX() * step
                : defaultStep;
            double yOffset = (5d + random.nextDouble() * 6d) / 16d;
            double zOffset = direction$axis == Direction.Axis.Z
                ? (double)direction.getStepZ() * step
                : defaultStep;
            level.addParticle(ParticleTypes.SMOKE, x + xOffset, y + yOffset, z + zOffset, 0d, 0d, 0d);
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x + xOffset, y + yOffset, z + zOffset, 0d, 0d, 0d);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new BlackFurnaceBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType)
    {
        return createFurnaceTicker(level, blockEntityType, NABlockEntityTypes.BLACK_FURNACE.value());
    }

}
