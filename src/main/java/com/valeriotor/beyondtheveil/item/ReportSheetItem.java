package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.client.gui.GuiHelper;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.surgery.notes.InjectionStep;
import com.valeriotor.beyondtheveil.surgery.notes.InsertionStep;
import com.valeriotor.beyondtheveil.surgery.notes.Report;
import com.valeriotor.beyondtheveil.surgery.notes.ReportStep;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.util.GuiType;
import com.valeriotor.beyondtheveil.util.VanillaUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReportSheetItem extends Item {
    public ReportSheetItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pUsedHand == InteractionHand.MAIN_HAND) {
            ItemStack stack = pPlayer.getItemInHand(pUsedHand);
            if (stack.hasTag() && stack.getTag().contains("report")) {
                Report report = Report.loadFromNBT(stack.getTag().getCompound("report"));
                for (ReportStep step : report.getSteps()) {
                    if (step instanceof InsertionStep insertionStep && pPlayer instanceof ServerPlayer sp && sp.getServer() != null) {
                        SurgeryIngredient ingredient = insertionStep.getIngredient();
                        ResourceLocation resourceLocation = new ResourceLocation(References.MODID, "ingredients/" + ingredient.toString().split(":")[1]);
                        VanillaUtils.awardAdvancement(sp, resourceLocation);
                    } else if (step instanceof InjectionStep injectionStep) {
                        DataUtil.discoverFluid(pPlayer, injectionStep.getFluid(), false);
                    }
                }
                if (pLevel.isClientSide()) {
                    pPlayer.playSound(SoundEvents.BOOK_PAGE_TURN, 1, 1);
                    GuiHelper.openClientSideGui(GuiType.REPORT_SHEET);
                }
            }
            /*else {
            DataUtil.setBooleanOnServerAndSync(pPlayer, "thebeginning", true, false);
            ResearchUtil.getResearch(pPlayer, "FIRSTDREAMS");
        }*/
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getOrCreateTag();
        if (tag.contains("report")) {
            Report report = Report.loadFromNBT(tag.getCompound("report"));
            String name = report.getName();
            pTooltipComponents.add(Component.literal(""));
            pTooltipComponents.add(Component.literal("§6§o" + name));
        }
    }
}
