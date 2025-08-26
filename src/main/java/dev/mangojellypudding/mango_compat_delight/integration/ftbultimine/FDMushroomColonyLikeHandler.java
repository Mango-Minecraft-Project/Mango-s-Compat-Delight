package dev.mangojellypudding.mango_compat_delight.integration.ftbultimine;

import dev.ftb.mods.ftbultimine.api.util.ItemCollector;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;

import java.util.List;

public interface FDMushroomColonyLikeHandler {
    static boolean isApplicable(Level level, BlockPos pos, BlockState state) {
        return state.getBlock() instanceof MushroomColonyBlock mushroomColonyBlock && state.getValue(MushroomColonyBlock.COLONY_AGE) >= mushroomColonyBlock.getMaxAge();
    }

    static void doHarvesting(Player player, BlockPos pos, BlockState state, ItemCollector itemCollector) {
        List<ItemStack> drops = Block.getDrops(state, (ServerLevel) player.level(), pos, null, player, ItemStack.EMPTY);

        for (ItemStack stack : drops) {
            stack.shrink(1);
            itemCollector.add(stack);
        }

        resetAge(player.level(), pos, state);
    }

    static private void resetAge(Level level, BlockPos pos, BlockState currentState) {
        if (currentState.getBlock() instanceof MushroomColonyBlock mushroomColonyBlock) {
            BlockState newState = currentState.setValue(mushroomColonyBlock.getAgeProperty(), 0);
            level.setBlock(pos, newState, Block.UPDATE_ALL);
        }
    }
}
