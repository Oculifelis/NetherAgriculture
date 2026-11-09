package ru.netherdon.netheragriculture.fabric.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Collection;
import ru.netherdon.netheragriculture.registries.NAMobEffects;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player
{
    public ServerPlayerMixin(Level level, GameProfile gameProfile)
    {
        super(level, gameProfile);
    }

    // 26.x: onEffectRemoved(MobEffectInstance) became onEffectsRemoved(Collection)
    @Inject(method = "onEffectsRemoved", at = @At("HEAD"))
    public void blazeFlightRemoved(Collection<MobEffectInstance> effects, CallbackInfo ci)
    {
        for (MobEffectInstance mobEffectInstance : effects)
        {
            if (!this.isSpectator() && !this.isCreative() && mobEffectInstance.is(NAMobEffects.BLAZE_FLIGHT))
            {
                Abilities abilities = this.getAbilities();
                abilities.mayfly = false;
                abilities.flying = false;
                this.onUpdateAbilities();
            }
        }
    }
}
