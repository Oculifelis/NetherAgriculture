package ru.netherdon.netheragriculture.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import ru.netherdon.netheragriculture.registries.NAItems;
import ru.netherdon.netheragriculture.registries.NATags;

public class CrimsonBerryRootsBlock extends NetherCropBlock
{

    private static final VoxelShape SHAPE0 = box(0f, 0f, 0f, 16f, 2f,16f);
    private static final VoxelShape SHAPE1 = box(0f, 0f, 0f, 16f, 5f,16f);
    private static final VoxelShape SHAPE2 = box(0f, 0f, 0f, 16f, 9f,16f);
    private static final VoxelShape SHAPE3 = box(0f, 0f, 0f, 16f, 12f,16f);

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]
    {
        SHAPE0, SHAPE0, SHAPE1, SHAPE1, SHAPE2, SHAPE2, SHAPE2, SHAPE3,
    };

    public CrimsonBerryRootsBlock(Properties properties)
    {
        super(NATags.Blocks.CRIMSON_BERRY_FERTILE_SOILS, properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult)
    {
        int age = this.getAge(state);
        if (age >= this.getMaxAge())
        {
            int count = 1 + level.getRandom().nextInt(2);
            popResource(level, pos, new ItemStack(NAItems.CRIMSON_BERRY.value(), count));
            level.playSound(
                null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.getRandom().nextFloat() * 0.4F
            );
            BlockState newState = state.setValue(this.getAgeProperty(), age-1);
            level.setBlock(pos, newState, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));
            return InteractionResult.SUCCESS;
        }

        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        int age = this.getAge(state);
        return SHAPE_BY_AGE[age];
    }

}
