package ru.netherdon.netheragriculture.mixin;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.monster.Strider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.netherdon.netheragriculture.registries.NAMobEffects;

@Mixin(Strider.class)
public abstract class StriderMixin
{
    @Unique
    private static final EntityDataAccessor<Boolean> NA$DATA_INTERNAL_HEAT = SynchedEntityData.defineId(Strider.class, EntityDataSerializers.BOOLEAN);

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    public void defineInternalHeatData(SynchedEntityData.Builder builder, CallbackInfo ci)
    {
        builder.define(NA$DATA_INTERNAL_HEAT, false);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Strider;setSuffocating(Z)V"))
    public void updateInternalHeatTick(CallbackInfo ci)
    {
        this.na$updateInternalHeat();
    }

    @ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Strider;setSuffocating(Z)V"))
    public boolean checkInternalHeat(boolean bl)
    {
        return bl && !this.na$asStrider().getEntityData().get(NA$DATA_INTERNAL_HEAT);
    }

    @Unique
    private void na$updateInternalHeat()
    {
        Strider na$strider = this.na$asStrider();
        if (!na$strider.level().isClientSide())
        {
            na$strider.getEntityData().set(NA$DATA_INTERNAL_HEAT, na$strider.hasEffect(NAMobEffects.INTERNAL_HEAT));
        }
    }

    @Unique
    private Strider na$asStrider()
    {
        return (Strider)(Object)this;
    }
}
