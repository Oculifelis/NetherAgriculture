package ru.netherdon.netheragriculture.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;

import java.util.function.Function;
import java.util.function.Supplier;

public interface IRegistryProvider<T>
{
    public <V extends T> Holder<V> register(String name, Function<Identifier, V> registryObject);
    public ResourceKey<? extends Registry<T>> getKey();

    public default  <V extends T> Holder<V> register(String name, Supplier<V> registryObject)
    {
        return this.register(name, (nameIn) -> registryObject.get());
    }
}
