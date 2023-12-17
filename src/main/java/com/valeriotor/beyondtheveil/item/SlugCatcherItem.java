package com.valeriotor.beyondtheveil.item;

import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.research.ResearchProvider;
import com.valeriotor.beyondtheveil.client.gui.GuiHelper;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.research.ResearchStatus;
import com.valeriotor.beyondtheveil.research.ResearchUtil;
import com.valeriotor.beyondtheveil.tile.SlugBaitBE;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SlugCatcherItem extends Item {

    public SlugCatcherItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        double x = pPlayer.position().x;
        double y = pPlayer.position().y + pPlayer.getEyeHeight();
        double z = pPlayer.position().z;
        float xRot = pPlayer.getXRot();
        float yHeadRot = pPlayer.getYHeadRot();

        final double BLOCK_DISTANCE = 20.0D;
        final double ANGLE_CONVERSION = Math.PI / 180;

        double ySin = -Math.sin(xRot * ANGLE_CONVERSION);
        double xSin = -Math.sin(yHeadRot * ANGLE_CONVERSION) * Math.cos(xRot * ANGLE_CONVERSION);
        double zCos = Math.cos(yHeadRot * ANGLE_CONVERSION) * Math.cos(xRot * ANGLE_CONVERSION);

        if (pLevel.isClientSide) {
            //DataUtil.setBoolean(pPlayer, "thebeginning", true, false);
            //DataUtil.setBoolean(pPlayer, "didDream", true, false);
            //ResearchStatus firstdreams = pPlayer.getCapability(ResearchProvider.RESEARCH).resolve().get().getResearch("FIRSTDREAMS");
            //firstdreams.complete(pPlayer);
            //GuiHelper.openClientSideGui(GuiHelper.GuiType.NECRONOMICON);
            final int PARTICLE_START = 25;
            final int PARTICLE_COUNT = 100;
            ClientLevel level = (ClientLevel) pLevel;
            for (int i = 25; i < PARTICLE_COUNT+PARTICLE_START; i++) {
                double yOffset = ySin * i * BLOCK_DISTANCE / (PARTICLE_COUNT+PARTICLE_START);
                double xOffset = xSin * i * BLOCK_DISTANCE / (PARTICLE_COUNT+PARTICLE_START);
                double zOffset = zCos * i * BLOCK_DISTANCE / (PARTICLE_COUNT+PARTICLE_START);
                level.addParticle(ParticleTypes.WHITE_ASH, x + xOffset, y + yOffset, z + zOffset, 0, 0, 0);
            }
        } else {
            ServerLevel level = (ServerLevel) pLevel;

            double yOffset = ySin * BLOCK_DISTANCE;
            double xOffset = xSin * BLOCK_DISTANCE;
            double zOffset = zCos * BLOCK_DISTANCE;

            Vec3 from = new Vec3(x, y, z);
            Vec3 to = new Vec3(x + xOffset, y + yOffset, z + zOffset);
            ClipContext context = new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, null);
            HitResult r = level.clip(context);
            Vec3 hit = r.getLocation();
            BlockPos hitPos = BlockPos.containing(hit);
            if (level.getBlockState(hitPos) == Blocks.WATER.defaultBlockState()) {
                for (int i = -6; i <= 6; i++) {
                    for (int j = -6; j <= 6; j++) {
                        BlockEntity blockEntity = level.getBlockEntity(hitPos.offset(i, 0, j));
                        if (blockEntity instanceof SlugBaitBE be) {
                            if (be.isSlugCloseOnXZPlane(hit, 1)) {
                                be.catchSlug();
                                ItemEntity e = new ItemEntity(level, x + xSin, y + ySin, z + zCos, new ItemStack(Registration.SLUG.get()));
                                level.addFreshEntity(e);
                            }
                        }
                    }
                }
                //level.sendParticles(ParticleTypes.SMOKE, hit.x, hit.y, hit.z, 100, 0,0,0,0.1);
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

}
