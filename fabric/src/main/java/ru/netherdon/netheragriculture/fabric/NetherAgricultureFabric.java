package ru.netherdon.netheragriculture.fabric;

import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.registry.FabricPotionBrewingBuilder;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.config.ModConfig;
import ru.netherdon.netheragriculture.NetherAgriculture;
import ru.netherdon.netheragriculture.config.NACommonConfig;
import ru.netherdon.netheragriculture.config.NAServerConfig;
import ru.netherdon.netheragriculture.fabric.registries.NABiomeModifiers;
import ru.netherdon.netheragriculture.fabric.registries.NACompostables;
import ru.netherdon.netheragriculture.fabric.registries.NALootTableModifiers;
import ru.netherdon.netheragriculture.fabric.registries.NAResourceConditions;
import ru.netherdon.netheragriculture.items.StriderTreatItem;
import ru.netherdon.netheragriculture.registries.NABlocks;
import ru.netherdon.netheragriculture.registries.NAPotions;

public final class NetherAgricultureFabric implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        NetherAgriculture.initialize();
        NABiomeModifiers.initialize();
        NALootTableModifiers.initialize();
        NACompostables.initialize();
        NAResourceConditions.initialize();

        ConfigRegistry.INSTANCE.register(NetherAgriculture.ID, ModConfig.Type.SERVER, NAServerConfig.getSpec(), NAServerConfig.FILE_NAME);
        ConfigRegistry.INSTANCE.register(NetherAgriculture.ID, ModConfig.Type.COMMON, NACommonConfig.getSpec(), NACommonConfig.FILE_NAME);

        FabricPotionBrewingBuilder.BUILD.register(NAPotions::registerRecipes);

        UseEntityCallback.EVENT.register((player, level, hand, entity, hit) ->
        {
            ItemStack stack = player.getItemInHand(hand);
            return StriderTreatItem.mobInteract(stack, level, entity, player);
        });

        StrippableBlockRegistry.register(NABlocks.GLOWING_STEM.value(), NABlocks.STRIPPED_GLOWING_STEM.value());
        StrippableBlockRegistry.register(NABlocks.GLOWING_HYPHAE.value(), NABlocks.STRIPPED_GLOWING_HYPHAE.value());
    }
}
