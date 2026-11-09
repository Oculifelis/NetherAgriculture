package ru.netherdon.netheragriculture.fabric.data.resource_condition;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.RegistryOps;
import org.jetbrains.annotations.Nullable;
import ru.netherdon.netheragriculture.NetherAgriculture;
import ru.netherdon.netheragriculture.compat.OtherModNames;
import ru.netherdon.netheragriculture.config.NACommonConfig;

public class FarmersDelightIntegrationResourceCondition implements ResourceCondition
{
    public static final ResourceConditionType<FarmersDelightIntegrationResourceCondition> TYPE = ResourceConditionType.create(
        NetherAgriculture.location("farmers_delight_integration"),
        MapCodec.unit(new FarmersDelightIntegrationResourceCondition())
    );

    @Override
    public boolean test(@Nullable RegistryOps.RegistryInfoLookup registryInfo)
    {
        return !FabricLoader.getInstance().isModLoaded(OtherModNames.FARMERS_DELIGHT)
            || !NACommonConfig.get().modCompatibility.farmersDelight().isFullRecipeIntegrationEnabled();
    }

    @Override
    public ResourceConditionType<?> getType()
    {
        return TYPE;
    }
}
