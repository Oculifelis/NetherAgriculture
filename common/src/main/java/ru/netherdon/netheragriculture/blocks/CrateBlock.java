package ru.netherdon.netheragriculture.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class CrateBlock extends Block
{

    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public CrateBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(OPEN, true));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult)
    {
        if (tryToggleState(level, player, state, pos))
        {
            return InteractionResult.SUCCESS;
        }

        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN);
    }

    public static boolean tryToggleState(Level level, Player player, BlockState state, BlockPos pos)
    {
        if (player.isShiftKeyDown() && player.getAbilities().mayBuild)
        {
            level.setBlockAndUpdate(pos, state.cycle(OPEN));
            RandomSource random = level.getRandom();
            float pitch = random.nextFloat() * 0.1F + 0.9F;
            double x = (double)pos.getX() + 0.5d;
            double y = (double)pos.getY() + 1d;
            double z = (double)pos.getZ() + 0.5d;
            SoundEvent sound = state.getValue(OPEN)
                ? SoundEvents.BARREL_CLOSE
                : SoundEvents.BARREL_OPEN;
            level.playSound(null, x, y, z, sound, SoundSource.BLOCKS, 0.5F, pitch);

            return true;
        }

        return false;
    }

}
