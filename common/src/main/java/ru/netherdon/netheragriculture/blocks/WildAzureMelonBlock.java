package ru.netherdon.netheragriculture.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import ru.netherdon.netheragriculture.registries.NAItems;

public class WildAzureMelonBlock extends Block
{
    public static final MapCodec<WildAzureMelonBlock> CODEC = simpleCodec(WildAzureMelonBlock::new);

    public static final VoxelShape SHAPE = box(5, 0, 5, 11, 9, 11);

    public WildAzureMelonBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        Vec3 offset = state.getOffset(pos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean includeData)
    {
        return NAItems.AZURE_MELON.value().getDefaultInstance();
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockPos posBelow = pos.below();
        BlockState stateBelow = level.getBlockState(posBelow);
        return stateBelow.is(BlockTags.NYLIUM);
    }

    @Override
    protected MapCodec<? extends WildAzureMelonBlock> codec()
    {
        return CODEC;
    }
}
