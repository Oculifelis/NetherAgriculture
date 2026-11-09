package ru.netherdon.netheragriculture.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import ru.netherdon.netheragriculture.events.NABlockTransforms;

import java.util.List;

@Mixin(BlockTransformer.class)
public class BlockTransformerMixin
{
    // 26.3: NeoForge adds transformBlock(UseOnContext, Holder) and the one-argument form delegates to it;
    // vanilla (Fabric) only has the one-argument form. Exactly one of the two reads the transforms field.
    @ModifyExpressionValue(method = {
        "transformBlock(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;",
        "transformBlock(Lnet/minecraft/world/item/context/UseOnContext;Lnet/minecraft/core/Holder;)Lnet/minecraft/world/InteractionResult;"
    }, at = @At(value = "FIELD", target = "Lnet/minecraft/core/component/BlockTransformer;transforms:Ljava/util/List;"))
    private List<BlockTransformer.BlockTransformData> netheragriculture$addTransforms(List<BlockTransformer.BlockTransformData> transforms, UseOnContext context)
    {
        return NABlockTransforms.withModTransforms(context.getItemInHand().get(DataComponents.BLOCK_TRANSFORMER), transforms);
    }
}
