package ru.netherdon.netheragriculture.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import ru.netherdon.netheragriculture.NetherAgriculture;

public final class NATags
{
    public static class Blocks
    {
        public static TagKey<Block> NETHERRACKS = c("netherracks");
        public static TagKey<Block> SINFUL_EYES_FERTILE_SOILS = fertileSoils("sinful_eyes");
        public static TagKey<Block> CRIMSON_BERRY_FERTILE_SOILS = fertileSoils("crimson_berry");
        public static TagKey<Block> WARPED_BERRY_FERTILE_SOILS = fertileSoils("warped_berry");
        public static TagKey<Block> AZURE_MELON_FERTILE_SOILS = fertileSoils("azure_melon");
        public static TagKey<Block> BLAZE_FRUIT_FERTILE_SOILS = fertileSoils("blaze_fruit");
        public static TagKey<Block> LOTHUN_FERTILE_SOILS = fertileSoils("lothun");

        private static TagKey<Block> fertileSoils(String postfix)
        {
            return na("fertile_soils/" + postfix);
        }

        private static TagKey<Block> na(String id)
        {
            return tag(NetherAgriculture.location(id));
        }

        private static TagKey<Block> c(String id)
        {
            return tag(Identifier.fromNamespaceAndPath("c", id));
        }

        private static TagKey<Block> tag(Identifier name)
        {
            return TagKey.create(Registries.BLOCK, name);
        }
    }

    public static class Enchantments
    {
        public static TagKey<Enchantment> INCREASE_ENTITY_DROPS = c("increase_entity_drops");

        private static TagKey<Enchantment> c(String id)
        {
            return TagKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath("c", id));
        }
    }
}
