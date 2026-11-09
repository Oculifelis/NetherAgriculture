package ru.netherdon.netheragriculture.events;

import net.minecraft.core.Holder;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.component.BlockTransformers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.CopyPropertiesProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider;
import org.apache.logging.log4j.util.Lazy;
import ru.netherdon.netheragriculture.registries.NABlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Hoe tilling of nylium and axe stripping of glowing stems, expressed as block transforms.
 * Tilling works on any face with no check of the block above, like the original tool hook.
 */
public final class NABlockTransforms
{
    private static final Lazy<List<BlockTransformer.BlockTransformData>> HOE = Lazy.lazy(() ->
    {
        RuleBasedStateProvider.Builder rules = RuleBasedStateProvider.builder();
        for (Map.Entry<Block, ? extends Block> entry : BlockEventHandler.farmlands().entrySet())
        {
            rules.ifTrueThenProvide(BlockPredicate.matchesBlocks(entry.getKey()), entry.getValue());
        }
        return List.of(BlockTransformer.BlockTransformData.builder(rules.build())
            .sound(SoundEvents.HOE_TILL)
            .build());
    });

    private static final Lazy<List<BlockTransformer.BlockTransformData>> AXE = Lazy.lazy(() -> List.of(
        BlockTransformer.BlockTransformData.builder(RuleBasedStateProvider.builder()
                .ifTrueThenProvide(BlockPredicate.matchesBlocks(NABlocks.GLOWING_STEM.value()), new CopyPropertiesProvider(NABlocks.STRIPPED_GLOWING_STEM.value()))
                .ifTrueThenProvide(BlockPredicate.matchesBlocks(NABlocks.GLOWING_HYPHAE.value()), new CopyPropertiesProvider(NABlocks.STRIPPED_GLOWING_HYPHAE.value()))
                .build())
            .sound(SoundEvents.AXE_STRIP)
            .build()
    ));

    private NABlockTransforms()
    {
    }

    public static List<BlockTransformer.BlockTransformData> withModTransforms(Holder<BlockTransformer> transformer, List<BlockTransformer.BlockTransformData> transforms)
    {
        List<BlockTransformer.BlockTransformData> extra;
        if (transformer == null)
        {
            return transforms;
        }
        else if (transformer.is(BlockTransformers.HOE))
        {
            extra = HOE.get();
        }
        else if (transformer.is(BlockTransformers.AXE))
        {
            extra = AXE.get();
        }
        else
        {
            return transforms;
        }

        List<BlockTransformer.BlockTransformData> result = new ArrayList<>(extra.size() + transforms.size());
        result.addAll(extra);
        result.addAll(transforms);
        return result;
    }
}
