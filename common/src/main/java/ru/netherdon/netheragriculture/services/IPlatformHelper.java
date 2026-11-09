package ru.netherdon.netheragriculture.services;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.Nullable;
import ru.netherdon.netheragriculture.effects.BlazeFlightEffect;
import ru.netherdon.netheragriculture.misc.ModLoaderTypes;
import ru.netherdon.netheragriculture.registries.IRegistryProvider;

public interface IPlatformHelper
{
    boolean canGrow(ServerLevel level, BlockPos pos, BlockState state, boolean grown);

    void onGrowPost(ServerLevel level, BlockPos pos, BlockState state);

    @Nullable
    Boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction direction, BlockState plantState);

    boolean isLocalImmuneToFire(Entity entity);

    boolean canTrample(Entity entity, BlockState state, BlockPos pos, float fallDistance);

    void fireBlazeFlightTick(LivingEntity entity, int tick);

    MobEffect modifyBlazeFlight(BlazeFlightEffect effect);

    boolean isModLoaded(String modId);

    ModLoaderTypes getModLoaderType();

    RecipeBookType getBlackFurnaceRecipeBookType();

    <T> IRegistryProvider<T> createProvider(Registry<T> registry);

    WoodType registerWoodType(WoodType type);

    BlockSetType registerBlockSetType(BlockSetType type);
}
