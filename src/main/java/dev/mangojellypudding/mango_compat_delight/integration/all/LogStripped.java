package dev.mangojellypudding.mango_compat_delight.integration.all;

import dev.mangojellypudding.mango_compat_delight.MangoCompatDelight;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.Tags;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.crafting.ingredient.ChanceResult;
import vectorwing.farmersdelight.common.crafting.ingredient.ItemAbilityIngredient;
import vectorwing.farmersdelight.common.registry.ModItems;
import static dev.mangojellypudding.mango_compat_delight.MangoCompatDelight.LOGGER;

import java.util.*;

public class LogStripped {
    public static List<RecipeHolder<?>> get() {
        List<RecipeHolder<?>> recipes = new ArrayList<>();

        LOGGER.debug("[LogStripped] Adding recipes for stripping logs with the cutting board.\n" +
                String.join(
                        "\n",
                        Arrays.stream(Ingredient.of(ItemTags.LOGS).getItems()).map(ItemStack::getDescriptionId).toList()
                )
        );

        for (ItemStack log : Ingredient.of(ItemTags.LOGS).getItems()) {
            if (log.is(Tags.Items.STRIPPED_LOGS) || log.is(Tags.Items.STRIPPED_WOODS)) {
                continue; // Skip if the log is already stripped
            }

            ResourceLocation logId = BuiltInRegistries.ITEM.getKey(log.getItem());
            ResourceLocation strippedId = logId.withPath("stripped_" + logId.getPath());
            Item stripped = BuiltInRegistries.ITEM.get(strippedId);
            if (Objects.equals(stripped, Items.AIR)) {
                continue; // Skip if the stripped item does not exist
            }

            recipes.add(new RecipeHolder<CuttingBoardRecipe>(
                    ResourceLocation.fromNamespaceAndPath(MangoCompatDelight.MODID, "cutting/" + strippedId.getPath()),
                    new CuttingBoardRecipe(
                            "",
                            Ingredient.of(log),
                            new ItemAbilityIngredient(ItemAbilities.AXE_STRIP).toVanilla(),
                            NonNullList.of(
                                    new ChanceResult(stripped.getDefaultInstance(), 1),
                                    new ChanceResult(ModItems.TREE_BARK.get().getDefaultInstance(), 1)),
                            Optional.of(SoundEvents.AXE_STRIP)
                    )
            ));
        }

        return recipes;
    }
}
