package ru.netherdon.netheragriculture.services;

import java.util.ServiceLoader;

public final class Services
{
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    private static <T> T load(Class<T> clazz)
    {
        return ServiceLoader.load(clazz, Services.class.getClassLoader())
            .findFirst()
            .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }
}
