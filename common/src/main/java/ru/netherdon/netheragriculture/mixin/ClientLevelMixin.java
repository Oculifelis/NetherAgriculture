package ru.netherdon.netheragriculture.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.netherdon.netheragriculture.blocks.GlowingSporesBlock;
import ru.netherdon.netheragriculture.registries.NAParticleTypes;

@Mixin(ClientLevel.class)
public class ClientLevelMixin
{
    @Inject(method = "doAnimateTick", at = @At(value = "TAIL"))
    public void spawnGlowingSporesMarker(
        int i, int j, int k, int l,
        RandomSource random,
        Block block,
        BlockPos.MutableBlockPos pos,
        CallbackInfo ci,
        @Local BlockState blockState,
        @Local(ordinal = 4) int m,
        @Local(ordinal = 5) int n,
        @Local(ordinal = 6) int o
    )
    {
        if (blockState.getBlock() instanceof GlowingSporesBlock spores)
        {
            ItemStack stack = Minecraft.getInstance().player.getMainHandItem();
            if (spores.canInteractWith(stack))
            {
                ((ClientLevel) (Object) this).addParticle(NAParticleTypes.GLOWING_SPORES_MARKER.value(), m + 0.5d, n + 0.5d, o + 0.5d, 0d, 0d, 0d);
            }
        }
    }
}
