package ru.netherdon.netheragriculture.services;

import org.jetbrains.annotations.Contract;
import ru.netherdon.netheragriculture.misc.ModLoaderTypes;

import java.util.Arrays;

public final class ModLoaderService
{
    @Contract(pure = true)
    public static boolean isModLoaded(String modId)
    {
        return Services.PLATFORM.isModLoaded(modId);
    }

    public static boolean anyModLoaded(String... modIds)
    {
        return Arrays.stream(modIds).anyMatch(ModLoaderService::isModLoaded);
    }

    @Contract(pure = true)
    public static ModLoaderTypes getModLoaderType()
    {
        return Services.PLATFORM.getModLoaderType();
    }
}
