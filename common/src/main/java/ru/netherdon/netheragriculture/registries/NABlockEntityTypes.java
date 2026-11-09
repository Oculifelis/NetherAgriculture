package ru.netherdon.netheragriculture.registries;

import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import ru.netherdon.netheragriculture.blocks.entities.BlackFurnaceBlockEntity;
import ru.netherdon.netheragriculture.blocks.entities.CustomHangingSignBlockEntity;
import ru.netherdon.netheragriculture.blocks.entities.CustomSignBlockEntity;
import ru.netherdon.netheragriculture.services.RegistryManager;

public final class NABlockEntityTypes
{
    public static final IRegistryProvider<BlockEntityType<?>> REGISTER = RegistryManager.getOrCreate(BuiltInRegistries.BLOCK_ENTITY_TYPE);

    public static final Holder<BlockEntityType<BlackFurnaceBlockEntity>> BLACK_FURNACE =
        REGISTER.register("black_furnace", () -> new BlockEntityType<>(BlackFurnaceBlockEntity::new, Set.of(NABlocks.BLACK_FURNACE.value())));

    public static final Holder<BlockEntityType<CustomSignBlockEntity>> CUSTOM_SIGN =
        REGISTER.register("sign", () ->
            new BlockEntityType<>(CustomSignBlockEntity::new, Set.of(NABlocks.GLOWING_SIGN.value(), NABlocks.GLOWING_WALL_SIGN.value()))
        );

    public static final Holder<BlockEntityType<CustomHangingSignBlockEntity>> CUSTOM_HANGING_SIGN =
        REGISTER.register("hanging_sign", () ->
            new BlockEntityType<>(CustomHangingSignBlockEntity::new, Set.of(NABlocks.GLOWING_HANGING_SIGN.value(), NABlocks.GLOWING_WALL_HANGING_SIGN.value()))
        );



    public static void initialize() {}
}
