package ru.netherdon.netheragriculture.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import ru.netherdon.netheragriculture.client.particles.options.GlowingSporeAirParticleOptions;

import java.util.List;

public class GlowingSporesBlock extends Block
{
    public static final MapCodec<GlowingSporesBlock> CODEC = RecordCodecBuilder.mapCodec((instance) ->
        instance.group(
            ResourceKey.codec(Registries.ITEM).fieldOf("container").forGetter((block) -> block.containerKey),
            propertiesCodec()
        ).apply(instance, GlowingSporesBlock::new)
    );

    private final ResourceKey<Item> containerKey;
    private Item container;

    public GlowingSporesBlock(ResourceKey<Item> container, Properties properties)
    {
        super(properties);
        this.containerKey = container;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
    {
        if (itemStack.is(this.getContainer()))
        {
            level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 11);
            ItemStack glowingSporesStack = new ItemStack(this.asItem());
            player.setItemInHand(interactionHand, ItemUtils.createFilledResult(itemStack, player, glowingSporesStack));
            return InteractionResult.SUCCESS;
        }

        return super.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource)
    {
        if (randomSource.nextInt(7) != 0)
        {
            return;
        }

        double sporePosX = blockPos.getX() + randomSource.nextDouble();
        double sporePosY = blockPos.getY() + randomSource.nextDouble();
        double sporePosZ = blockPos.getZ() + randomSource.nextDouble();
        var particleOptions = new GlowingSporeAirParticleOptions(blockState.getBlock(), blockPos.immutable());
        level.addParticle(particleOptions, sporePosX, sporePosY, sporePosZ, 0d, 0d, 0d);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        for (Item item : this.getInteractableItems())
        {
            if (context.isHoldingItem(item))
            {
                return super.getShape(state, level, pos, context);
            }
        }

        return Shapes.empty();
    }

    @Override
    protected void spawnDestroyParticles(Level level, Player player, BlockPos blockPos, BlockState blockState) {}

    @Override
    protected RenderShape getRenderShape(BlockState blockState)
    {
        return RenderShape.INVISIBLE;
    }

    public List<Item> getInteractableItems()
    {
        return List.of(this.asItem(), Items.GLASS_BOTTLE);
    }

    @Override
    protected MapCodec<? extends GlowingSporesBlock> codec()
    {
        return CODEC;
    }

    protected Item getContainer()
    {
        if (this.container == null)
        {
            this.container = BuiltInRegistries.ITEM.getOrThrow(this.containerKey).value();
        }

        return this.container;
    }

    public boolean canInteractWith(ItemStack stack)
    {
        return stack.is(this.asItem()) || stack.is(this.getContainer());
    }
}
