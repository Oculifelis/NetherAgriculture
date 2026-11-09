package ru.netherdon.netheragriculture.fabric.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import ru.netherdon.netheragriculture.NetherAgriculture;
import ru.netherdon.netheragriculture.registries.IRegistryProvider;

import java.util.function.Function;

public record RegistryProvider<T>(Registry<T> registry) implements IRegistryProvider<T>
{
    @SuppressWarnings("unchecked")
    @Override
    public <V extends T> Holder<V> register(String name, Function<Identifier, V> registryObject)
    {
        Identifier id = NetherAgriculture.location(name);
        return (Holder<V>) Registry.registerForHolder(this.registry, id, registryObject.apply(id));
    }

    @Override
    public ResourceKey<? extends Registry<T>> getKey()
    {
        return this.registry.key();
    }
}
