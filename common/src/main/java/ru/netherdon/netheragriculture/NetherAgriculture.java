package ru.netherdon.netheragriculture;

import net.minecraft.resources.Identifier;
import ru.netherdon.netheragriculture.registries.*;

public class NetherAgriculture
{
    public static final String ID = "netheragriculture";

    public static void initialize()
    {
        NABlocks.initialize();
        NAMobEffects.initialize();
        NAPotions.initialize();
        NAItems.initialize();
        NACreativeTabs.initialize();
        NARecipeSerializers.initialize();
        NABlockEntityTypes.initialize();
        NAMenuTypes.initialize();
        NARecipeTypes.initialize();
        NARecipeBookCategories.initialize();
        NAParticleTypes.initialize();
        NAFeatures.initialize();
        NAWoodTypes.initialize();
    }

    public static Identifier location(String path)
    {
        return Identifier.fromNamespaceAndPath(ID, path);
    }
}
