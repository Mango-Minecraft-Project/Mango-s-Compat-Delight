package dev.mangojellypudding.mango_compat_delight.data;

import dev.mangojellypudding.mango_compat_delight.MangoCompatDelight;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class MCDBlockTags extends BlockTagsProvider {
    public static class Tags {
        static String FTBULTIMINE = "ftbultimine";
        public static TagKey<Block> TILLABLE_TAG = externalBlockTag(FTBULTIMINE, "farmland_tillable");

        static TagKey<Block> externalBlockTag(String modId, String path) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(modId, path));
        }
    }

    public MCDBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MangoCompatDelight.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        registriesIntegrationTags();
    }

    protected void registriesIntegrationTags() {
        tag(Tags.TILLABLE_TAG).add(
                ModBlocks.RICH_SOIL.get()
        );
    }
}
