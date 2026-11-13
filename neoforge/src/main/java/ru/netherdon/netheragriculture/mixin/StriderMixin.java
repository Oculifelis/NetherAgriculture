package ru.netherdon.netheragriculture.mixin;

import net.minecraft.world.entity.monster.Strider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.netherdon.netheragriculture.neoforge.registries.NAEntityAttachments;
import ru.netherdon.netheragriculture.registries.NAMobEffects;

@Mixin(Strider.class)
public abstract class StriderMixin {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Strider;setSuffocating(Z)V"))
    private void na$updateInternalHeat(CallbackInfo ci) {
        Strider strider = na$asStrider();
        if (!strider.level().isClientSide()) {
            boolean heated = strider.hasEffect(NAMobEffects.INTERNAL_HEAT);
            if (strider.getData(NAEntityAttachments.INTERNAL_HEAT) != heated) {
                strider.setData(NAEntityAttachments.INTERNAL_HEAT, heated);
            }
        }
    }

    @ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Strider;setSuffocating(Z)V"))
    private boolean na$checkInternalHeat(boolean suffocating) {
        return suffocating && !na$asStrider().getData(NAEntityAttachments.INTERNAL_HEAT);
    }

    @Unique
    private Strider na$asStrider() {
        return (Strider) (Object) this;
    }
}
