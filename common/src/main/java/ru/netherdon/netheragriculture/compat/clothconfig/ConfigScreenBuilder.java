package ru.netherdon.netheragriculture.compat.clothconfig;

import com.google.common.collect.Lists;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.netherdon.netheragriculture.config.NACommonConfig;
import ru.netherdon.netheragriculture.config.NAServerConfig;
import ru.netherdon.netheragriculture.config.settings.common.*;
import ru.netherdon.netheragriculture.config.settings.server.EntitySettings;

import java.util.List;

import static ru.netherdon.netheragriculture.misc.TranslationHelper.text;
import static ru.netherdon.netheragriculture.compat.clothconfig.ConfigScreenHelper.*;

public class ConfigScreenBuilder
{
    private static final String CATEGORY_NAMESPACE = "config.category";
    private static final Component OVERRIDES_CATEGORY = text(CATEGORY_NAMESPACE,"overrides");
    private static final Component ENTITY_CATEGORY = text(CATEGORY_NAMESPACE,"entity");
    private static final Component MOD_COMPATIBILITY_CATEGORY = text(CATEGORY_NAMESPACE,"mod_compatibility");
    private static final Component TITLE = Component.literal("Nether Agriculture");

    public static Screen build(Screen lastScreen)
    {
        NAServerConfig server = NAServerConfig.get();
        NACommonConfig common = NACommonConfig.get();
        Minecraft minecraft = Minecraft.getInstance();

        ConfigBuilder builder = ConfigBuilder.create();
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        builder.setParentScreen(lastScreen);
        builder.setTitle(TITLE);
        builder.alwaysShowTabs();

        ConfigPermission serverPermission = ConfigPermission.server(minecraft);
        buildEntityCategory(server.entity, builder, entryBuilder, serverPermission);
        buildOverridesCategory(common.overrides, builder, entryBuilder, ConfigPermission.ALL);
        buildModCompatibilityCategory(common.modCompatibility, builder, entryBuilder, ConfigPermission.ALL);

        return builder.build();
    }

    @SuppressWarnings("rawtypes")
    private static void buildOverridesCategory(
        OverrideSettings overrides,
        ConfigBuilder builder,
        ConfigEntryBuilder entryBuilder,
        ConfigPermission permission
    )
    {
        ConfigCategory category = builder.getOrCreateCategory(OVERRIDES_CATEGORY);

        LootModifierSettings lootModifier = overrides.lootModifier();
        List<AbstractConfigListEntry> subEntries = Lists.newArrayList();
        subEntries.add(booleanToggle(lootModifier.piglinBarteringEnabled(), entryBuilder, permission).build());

        if (lootModifier.fabric() != null)
        {
            FabricLootModifierSettings fabricModifiers = lootModifier.fabric();
            subEntries.add(booleanToggle(fabricModifiers.hoglinEnabled(), entryBuilder, permission).build());
            subEntries.add(booleanToggle(fabricModifiers.striderEnabled(), entryBuilder, permission).build());
            subEntries.add(booleanToggle(fabricModifiers.netherBridgeEnabled(), entryBuilder, permission).build());
            subEntries.add(booleanToggle(fabricModifiers.bastionHoglinStableEnabled(), entryBuilder, permission).build());
        }

        category.addEntry(
            entryBuilder.startSubCategory(text(CATEGORY_NAMESPACE, "overrides", "loot"), subEntries)
                .build()
        );

        ConfiguredFeatureSettings configuredFeature = overrides.configuredFeature();
        List<AbstractConfigListEntry> cfEntries = Lists.newArrayList();
        cfEntries.add(booleanToggle(configuredFeature.crimsonForestVegetationEnabled(), entryBuilder, permission).build());
        cfEntries.add(booleanToggle(configuredFeature.crimsonNyliumBonemealEnabled(), entryBuilder, permission).build());
        cfEntries.add(booleanToggle(configuredFeature.warpedForestVegetationEnabled(), entryBuilder, permission).build());
        cfEntries.add(booleanToggle(configuredFeature.warpedNyliumBonemealEnabled(), entryBuilder, permission).build());

        category.addEntry(
            entryBuilder.startSubCategory(text(CATEGORY_NAMESPACE, "overrides", "configured_feature"), cfEntries)
                .build()
        );
    }

    private static void buildEntityCategory(
        EntitySettings worldSettings,
        ConfigBuilder builder,
        ConfigEntryBuilder entryBuilder,
        ConfigPermission permission
    )
    {
        ConfigCategory category = builder.getOrCreateCategory(ENTITY_CATEGORY);

        category.addEntry(
            booleanToggle(worldSettings.burningFromItemEnabled(), entryBuilder, permission).build()
        );

        category.addEntry(
            booleanToggle(worldSettings.burningFromBlazeFlightEnabled(), entryBuilder, permission).build()
        );
    }

    private static void buildModCompatibilityCategory(
        ModCompatibilitySettings settings,
        ConfigBuilder builder,
        ConfigEntryBuilder entryBuilder,
        ConfigPermission permission
    )
    {
        ConfigCategory category = builder.getOrCreateCategory(MOD_COMPATIBILITY_CATEGORY);

        FarmersDelightSettings farmersDelight = settings.farmersDelight();
        category.addEntry(
            entryBuilder.startSubCategory(
                text(CATEGORY_NAMESPACE, "mod_compatibility", "farmers_delight"),
                List.of(
                    booleanToggle(farmersDelight.fullRecipeIntegrationEnabled(), entryBuilder, permission).build()
                )
            ).setExpanded(true)
                .build()
        );

        NethersDelightSettings nethersDelightSettings = settings.nethersDelight();
        category.addEntry(
            entryBuilder.startSubCategory(
                text(CATEGORY_NAMESPACE, "mod_compatibility", "nethers_delight"),
                List.of(
                    booleanToggle(
                        nethersDelightSettings.useOnlyBlackFurnace(),
                        entryBuilder,
                        permission
                    ).build()
                )
            ).setExpanded(true)
                .build()
        );
    }
}
