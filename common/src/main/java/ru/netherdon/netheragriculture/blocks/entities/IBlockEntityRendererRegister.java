package ru.netherdon.netheragriculture.blocks.entities;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface IBlockEntityRendererRegister
{
    public <T extends BlockEntity, S extends BlockEntityRenderState> void register(BlockEntityType<? extends T> type, BlockEntityRendererProvider<T, S> renderer);
}
