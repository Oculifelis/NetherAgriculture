package ru.netherdon.netheragriculture.compat.clothconfig;

import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;
import ru.netherdon.netheragriculture.compat.OtherModNames;
import ru.netherdon.netheragriculture.services.ModLoaderService;

import java.util.function.Function;

public class ConfigScreenLoader
{
    @Nullable
    public static Function<Screen, Screen> get()
    {
        if (ModLoaderService.anyModLoaded(OtherModNames.CLOTH_CONFIG_FABRIC, OtherModNames.CLOTH_CONFIG_NEOFORGE))
        {
            return ConfigScreenBuilder::build;
        }

        return null;
    }
}
