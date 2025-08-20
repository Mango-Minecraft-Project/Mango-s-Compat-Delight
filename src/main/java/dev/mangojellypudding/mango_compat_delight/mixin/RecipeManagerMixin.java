package dev.mangojellypudding.mango_compat_delight.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.mangojellypudding.mango_compat_delight.integration.all.LogStripped;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
    private static List<RecipeHolder<?>> getCustomRecipes(){
        List<RecipeHolder<?>> recipes = new ArrayList<>();

        recipes.addAll(LogStripped.get());

        return recipes;
    }

    @WrapOperation(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at= @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMultimap$Builder;build()Lcom/google/common/collect/ImmutableMultimap;"))
    public ImmutableMultimap<RecipeType<?>, RecipeHolder<?>> applyCustomType(
            ImmutableMultimap.Builder<RecipeType<?>, RecipeHolder<?>> instance,
            Operation<ImmutableMultimap<RecipeType<?>, RecipeHolder<?>>> original) {

        List<RecipeHolder<?>> holder = getCustomRecipes();
        holder.forEach(recipeHolder -> instance.put(recipeHolder.value().getType(), recipeHolder));

        return original.call(instance);
    }

    @WrapOperation(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at= @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;"))
    public ImmutableMap<ResourceLocation, RecipeHolder<?>> applyCustomId(
            ImmutableMap.Builder<ResourceLocation, RecipeHolder<?>> instance, Operation<ImmutableMap<ResourceLocation, RecipeHolder<?>>> original) {

        List<RecipeHolder<?>> holder = getCustomRecipes();
        holder.forEach(recipeHolder -> instance.put(recipeHolder.id(), recipeHolder));

        return original.call(instance);
    }
}
