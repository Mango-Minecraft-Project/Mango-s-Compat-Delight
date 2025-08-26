package dev.mangojellypudding.mango_compat_delight.integration.ftbultimine;

import dev.ftb.mods.ftbultimine.api.FTBUltimineAPI;
import dev.ftb.mods.ftbultimine.api.rightclick.RightClickHandler;
import dev.ftb.mods.ftbultimine.api.shape.ShapeContext;
import dev.ftb.mods.ftbultimine.api.util.ItemCollector;
import dev.ftb.mods.ftbultimine.config.FTBUltimineServerConfig;
import dev.ftb.mods.ftbultimine.shape.BlockMatchers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.Tags;
import org.apache.commons.lang3.mutable.MutableInt;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.Collection;

public enum FDCropHarvesting implements RightClickHandler {
    INSTANCE;

    @Override
    public int handleRightClickBlock(ShapeContext shapeContext, InteractionHand hand, Collection<BlockPos> positions) {
        ServerPlayer player = shapeContext.player();

        if (!FTBUltimineServerConfig.RIGHT_CLICK_HARVESTING.get() || shapeContext.matcher() != BlockMatchers.MATCH_BY_CROP_TYPE || !player.getItemInHand(hand).is(Tags.Items.TOOLS_SHEAR)) {
            return 0;
        }

        int clicked = 0;
        ItemCollector itemCollector = new ItemCollector();

        for (BlockPos pos : positions) {
            BlockState state = player.level().getBlockState(pos);

            if (FDMushroomColonyLikeHandler.isApplicable(player.level(), pos, state)) {
                FDMushroomColonyLikeHandler.doHarvesting(player, pos, state, itemCollector);
                player.level().gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
                clicked++;

                player.causeFoodExhaustion((float) (FTBUltimineServerConfig.getExhaustionPerBlock(player) * 0.005D));
                if (hurtItemAndCheckIfBroken(player, hand) || FTBUltimineAPI.isTooExhausted(player)) {
                    break;
                }
            }
        }

        if (clicked > 0) {
            player.level().playSound(null, shapeContext.origPos(), SoundEvents.MOOSHROOM_SHEAR, SoundSource.BLOCKS, 1F, 1F);
        }

        itemCollector.drop(player.level(), shapeContext.face() == null ? shapeContext.origPos() : shapeContext.origPos().relative(shapeContext.face()));

        return clicked;
    }
}
