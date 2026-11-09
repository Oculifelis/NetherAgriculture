package ru.netherdon.netheragriculture.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import ru.netherdon.netheragriculture.registries.NAConfiguredFeatures;
import ru.netherdon.netheragriculture.registries.NATags;

public class NetherRootsBlock extends VegetationBlock implements BonemealableBlock
{
    public static final MapCodec<NetherRootsBlock> CODEC = Block.simpleCodec(NetherRootsBlock::new);

    public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 13, 14);

    public NetherRootsBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        return state.is(NATags.Blocks.NETHERRACKS)
            || state.is(BlockTags.NYLIUM)
            || state.is(Blocks.SOUL_SOIL)
            || super.mayPlaceOn(state, level, pos);
    }

    @Override
    protected MapCodec<? extends NetherRootsBlock> codec()
    {
        return CODEC;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState)
    {
        BlockState stateBelow = levelReader.getBlockState(blockPos.below());
        return stateBelow.is(NATags.Blocks.NETHERRACKS);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState)
    {
        return true;
    }

    @Override
    public Type getType()
    {
        return Type.NEIGHBOR_SPREADER;
    }

    @Override
    public BlockPos getParticlePos(BlockPos pos)
    {
        return pos;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState)
    {
        ChunkGenerator chunkGenerator = serverLevel.getChunkSource().getGenerator();
        var registry = serverLevel.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE);

        registry.get(NAConfiguredFeatures.NETHER_ROOTS_BONEMEAL)
            .ifPresent((reference) -> reference.value().place(serverLevel, chunkGenerator, randomSource, blockPos));
    }
}
