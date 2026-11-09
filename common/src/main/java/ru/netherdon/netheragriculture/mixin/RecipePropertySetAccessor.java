package ru.netherdon.netheragriculture.mixin;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipePropertySet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Collection;

@Mixin(RecipePropertySet.class)
public interface RecipePropertySetAccessor
{
    @Invoker("create")
    static RecipePropertySet na$create(Collection<Ingredient> ingredients)
    {
        throw new AssertionError();
    }
}
