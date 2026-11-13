package ru.netherdon.netheragriculture.neoforge.registries;

import java.util.function.Supplier;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import ru.netherdon.netheragriculture.NetherAgriculture;

public final class NAEntityAttachments {
    public static final DeferredRegister<AttachmentType<?>> TYPES = DeferredRegister.create(
            NeoForgeRegistries.ATTACHMENT_TYPES, NetherAgriculture.ID);

    // The old flag was transient too: saved mob effects restore it on the first server tick.
    public static final Supplier<AttachmentType<Boolean>> INTERNAL_HEAT = TYPES.register("internal_heat",
            () -> AttachmentType.builder(() -> false).sync(ByteBufCodecs.BOOL).build());

    private NAEntityAttachments() {}
}
