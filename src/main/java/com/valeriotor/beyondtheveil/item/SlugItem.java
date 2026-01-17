package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.capability.util.PlayerTimerDataProvider;
import com.valeriotor.beyondtheveil.entity.CanoeEntity;
import com.valeriotor.beyondtheveil.lib.PlayerDataLib;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.util.PlayerTimer;
import com.valeriotor.beyondtheveil.util.timers.BaptismTimer;
import com.valeriotor.beyondtheveil.util.timers.ContactTimer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.Tags;

import java.util.Iterator;

public class SlugItem extends Item {

    public SlugItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof ServerPlayer sp) {
            if (ResearchUtil.getResearchStage(sp, "CUSTOMS") == 0) {
                DataUtil.setBooleanOnServerAndSync(sp, PlayerDataLib.ate_slug.name(), true, false);
            }
            boolean baptized = DataUtil.getBoolean(sp, PlayerDataLib.baptized.name());
            if (!baptized) {
                sp.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 10));
                sp.addEffect(new MobEffectInstance(MobEffects.HUNGER, 20 * 10, 1));
            }
            if (checkBaptism(sp)) {
                pLevel.setBlock(sp.blockPosition().above(2), Blocks.ICE.defaultBlockState(), 3);
                sp.getCapability(PlayerTimerDataProvider.PLAYER_TIMER_DATA).ifPresent(c -> {
                    if (!c.hasTimer("baptism")) {
                        c.addTimer(new BaptismTimer(sp.getHealth()));
                    }
                });
            } else if (checkContact(sp)) {
                sp.getCapability(PlayerTimerDataProvider.PLAYER_TIMER_DATA).ifPresent(c -> {
                    if (!c.hasTimer("contact")) {
                        c.addTimer(new ContactTimer());
                    }
                });
            }
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    private static boolean checkBaptism(ServerPlayer sp) {
        if (ResearchUtil.getResearchStage(sp, "BAPTISM") < 1 || DataUtil.getBoolean(sp, PlayerDataLib.baptized.name())) {
            return false;
        }
        if (sp.level().getBlockState(sp.getOnPos()).canBeReplaced()) {
            return false;
        }
        for (int i = 0; i < 3; i++) {
            FluidState fluidState = sp.level().getFluidState(sp.blockPosition().above(i));
            if (fluidState != Fluids.WATER.getSource(false)) {
                return false;
            }
            Iterator<Direction> iterator = Direction.Plane.HORIZONTAL.iterator();
            while (iterator.hasNext()) {
                Direction next = iterator.next();
                if (sp.level().getBlockState(sp.blockPosition().above(i).relative(next)).canBeReplaced()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkContact(ServerPlayer sp) {
        if (!hasResearchForContact(sp)) {
            return false;
        }
        if (sp.getVehicle() instanceof CanoeEntity canoe) {
            Level l = sp.level();
            if (!l.getBiome(sp.getOnPos()).is(BiomeTags.IS_OCEAN)) {
                return false;
            }
            if (l.getDayTime() < 16000 || l.getDayTime() > 20000) {
                sp.sendSystemMessage(Component.translatable("contact.error.night"));
                return false;
            }
            if (!l.getEntities(sp, AABB.ofSize(sp.position(), 30, 30, 30), e -> e instanceof Player).isEmpty()) {
                sp.sendSystemMessage(Component.translatable("contact.error.players"));
                return false;
            }
            for (int x = -8; x <= 8; x++) {
                for (int z = -8; z <= 8; z++) {
                    BlockPos offset = canoe.getOnPos().offset(x, 0, z);
                    for (int i = 0; i < 2; i++) {
                        BlockState state = l.getBlockState(offset.below(i));
                        if (state.getBlock() != Blocks.WATER && !(state.getBlock() instanceof LiquidBlockContainer) && !(state.getBlock() instanceof BubbleColumnBlock)) {
                            return false;
                        }
                    }
                }
            }
            for (int x = -3; x <= 3; x++) {
                for (int z = -3; z <= 3; z++) {
                    for (int y = -3; y < 0; y++) {
                        BlockState state = l.getBlockState(canoe.getOnPos().offset(x, y, z));
                        if (state.getBlock() != Blocks.WATER && !(state.getBlock() instanceof LiquidBlockContainer) && !(state.getBlock() instanceof BubbleColumnBlock)) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static boolean hasResearchForContact(ServerPlayer sp) {
        return ResearchUtil.getResearchStage(sp, "CUSTOMS") >= 1 && !DataUtil.getBoolean(sp, PlayerDataLib.had_contact.name()); // TODO change customs to first contact
    }

}
