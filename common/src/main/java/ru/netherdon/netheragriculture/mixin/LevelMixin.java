package ru.netherdon.netheragriculture.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.netherdon.netheragriculture.client.particles.event.SourcedParticleManager;

@Mixin(Level.class)
public class LevelMixin
{
    @Inject(method = "updatePOIOnBlockStateChange", at = @At("HEAD"))
    public void applyBlockChangeListeners(BlockPos pos, BlockState state, BlockState state2, CallbackInfo ci)
    {
        if ((Object)this instanceof ClientLevel)
        {
            SourcedParticleManager.fireBlockChange(pos, state2);
        }
    }
}
