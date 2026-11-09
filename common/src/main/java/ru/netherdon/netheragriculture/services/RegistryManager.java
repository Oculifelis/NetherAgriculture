package ru.netherdon.netheragriculture.services;

import net.minecraft.core.Registry;
import org.jetbrains.annotations.Contract;
import ru.netherdon.netheragriculture.registries.IRegistryProvider;

import java.util.HashMap;
import java.util.Map;

public final class RegistryManager
{
    private static final Map<Registry<?>, IRegistryProvider<?>> PROVIDERS = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> IRegistryProvider<T> getOrCreate(Registry<T> registry)
    {
        if (!PROVIDERS.containsKey(registry))
        {
            IRegistryProvider<T> provider = createProvider(registry);
            PROVIDERS.put(registry, provider);
            return provider;
        }

        return (IRegistryProvider<T>) PROVIDERS.get(registry);
    }

    @Contract(pure = true)
    private static <T> IRegistryProvider<T> createProvider(Registry<T> registry)
    {
        return Services.PLATFORM.createProvider(registry);
    }
}
