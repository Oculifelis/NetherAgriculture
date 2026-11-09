package ru.netherdon.netheragriculture.compat.clothconfig;

import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ConfigPermission
{
    public static final ConfigPermission ALL = new ConfigPermission(null);

    @Nullable
    private final Minecraft minecraft;

    private ConfigPermission(@Nullable Minecraft minecraft)
    {
        this.minecraft = minecraft;
    }

    public boolean levelLoaded()
    {
        return minecraft == null || minecraft.level != null;
    }

    public boolean isSingleplayer()
    {
        return minecraft == null || (minecraft.getSingleplayerServer() != null && !minecraft.getSingleplayerServer().isPublished()); // 26.2 removed Minecraft.isSingleplayer(); same check inlined
    }

    public boolean entryAvailable()
    {
        return this.levelLoaded() && this.isSingleplayer();
    }

    public static ConfigPermission server(@NotNull Minecraft minecraft)
    {
        return new ConfigPermission(Objects.requireNonNull(minecraft));
    }
}
