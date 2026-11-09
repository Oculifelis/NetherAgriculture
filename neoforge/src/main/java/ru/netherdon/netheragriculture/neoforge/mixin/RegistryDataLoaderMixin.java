package ru.netherdon.netheragriculture.neoforge.mixin;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Decoder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.netherdon.netheragriculture.registries.NAConfiguredFeatures;

// 26.x: datapack registry elements are decoded in RegistryLoadTask.PendingRegistration.loadFromResource
// and registered later; the decoded configured feature is modified here, before it is registered.
@Mixin(targets = "net.minecraft.resources.RegistryLoadTask$PendingRegistration")
public class RegistryDataLoaderMixin
{
    @Inject(method = "loadFromResource", at = @At("RETURN"))
    private static <T> void b(
        Decoder<T> decoder,
        RegistryOps<JsonElement> registryOps,
        ResourceKey<T> resourceKey,
        Resource resource,
        CallbackInfoReturnable<Either<T, Exception>> cir
    )
    {
        if (resourceKey.registry().equals(Registries.CONFIGURED_FEATURE.identifier()))
        {
            cir.getReturnValue().ifLeft(object -> NAConfiguredFeatures.modify(resourceKey.identifier(), (ConfiguredFeature<?, ?>)object));
        }
    }
}
