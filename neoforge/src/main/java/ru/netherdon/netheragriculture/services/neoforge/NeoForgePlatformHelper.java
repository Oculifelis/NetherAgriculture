package ru.netherdon.netheragriculture.services.neoforge;

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
import ru.netherdon.netheragriculture.services.IPlatformHelper;

public final class NeoForgePlatformHelper implements IPlatformHelper
{
    @Override
    public boolean canGrow(ServerLevel level, BlockPos pos, BlockState state, boolean grown)
    {
        return CropServiceImpl.canGrow(level, pos, state, grown);
    }

    @Override
    public void onGrowPost(ServerLevel level, BlockPos pos, BlockState state)
    {
        CropServiceImpl.onGrowPost(level, pos, state);
    }

    @Override
    @Nullable
    public Boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction direction, BlockState plantState)
    {
        return CropServiceImpl.canSustainPlant(state, level, pos, direction, plantState);
    }

    @Override
    public boolean isLocalImmuneToFire(Entity entity)
    {
        return EntityServiceImpl.isLocalImmuneToFire(entity);
    }

    @Override
    public boolean canTrample(Entity entity, BlockState state, BlockPos pos, float fallDistance)
    {
        return EntityServiceImpl.canTrample(entity, state, pos, fallDistance);
    }

    @Override
    public void fireBlazeFlightTick(LivingEntity entity, int tick)
    {
        MobEffectServiceImpl.fireBlazeFlightTick(entity, tick);
    }

    @Override
    public MobEffect modifyBlazeFlight(BlazeFlightEffect effect)
    {
        return MobEffectServiceImpl.modifyBlazeFlight(effect);
    }

    @Override
    public boolean isModLoaded(String modId)
    {
        return ModLoaderServiceImpl.isModLoaded(modId);
    }

    @Override
    public ModLoaderTypes getModLoaderType()
    {
        return ModLoaderServiceImpl.getModLoaderType();
    }

    @Override
    public RecipeBookType getBlackFurnaceRecipeBookType()
    {
        return RecipeServiceImpl.getBlackFurnaceRecipeBookType();
    }

    @Override
    public <T> IRegistryProvider<T> createProvider(Registry<T> registry)
    {
        return RegistryManagerImpl.createProvider(registry);
    }

    @Override
    public WoodType registerWoodType(WoodType type)
    {
        return WoodTypeServiceImpl.register(type);
    }

    @Override
    public BlockSetType registerBlockSetType(BlockSetType type)
    {
        return WoodTypeServiceImpl.registerSetType(type);
    }
}
