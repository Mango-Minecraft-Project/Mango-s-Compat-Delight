package dev.mangojellypudding.mango_compat_delight.integration.ftbultimine;

import dev.ftb.mods.ftbultimine.api.crop.CropLikeHandler;
import dev.ftb.mods.ftbultimine.api.util.ItemCollector;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;

import java.util.List;

public enum FarmerDelightCropLikeHandler implements CropLikeHandler {
    INSTANCE;

    @Override
    public boolean isApplicable(Level level, BlockPos pos, BlockState state) {
        return state.getBlock() instanceof MushroomColonyBlock mushroomColonyBlock && state.getValue(MushroomColonyBlock.COLONY_AGE) >= mushroomColonyBlock.getMaxAge();
    }

    @Override
    public boolean doHarvesting(Player player, BlockPos pos, BlockState state, ItemCollector itemCollector) {
        BlockEntity blockEntity = state.hasBlockEntity() ? player.level().getBlockEntity(pos) : null;
        List<ItemStack> drops = Block.getDrops(state, (ServerLevel) player.level(), pos, blockEntity, player, ItemStack.EMPTY);

        for (ItemStack stack : drops) {
            // should work for most if not all modded crop blocks, hopefully
            if (Block.byItem(stack.getItem()) == state.getBlock() && consumesItemToReplant(state)) {
                stack.shrink(1);
            }
            itemCollector.add(stack);
        }

        resetAge(player.level(), pos, state);

        return true;
    }

    @Override
    public boolean isEquivalent(BlockState state1, BlockState state2) {
        return state1.getBlock().equals(state2.getBlock());
    }

    private boolean consumesItemToReplant(BlockState state) {
        return true;
    }

    private void resetAge(Level level, BlockPos pos, BlockState currentState) {
        if (currentState.getBlock() instanceof MushroomColonyBlock mushroomColonyBlock) {
            BlockState newState = currentState.setValue(mushroomColonyBlock.getAgeProperty(), 0);
            level.setBlock(pos, newState, Block.UPDATE_ALL);
        }
    }
}
