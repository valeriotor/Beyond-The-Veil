package com.valeriotor.beyondtheveil.client.reminiscence;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.valeriotor.beyondtheveil.dreaming.dreams.ReminiscenceAnimal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import javax.annotation.Nullable;

public class ReminiscenceClientAnimal extends ReminiscenceClient {

    private final Entity entity;

    public ReminiscenceClientAnimal(ReminiscenceAnimal ra) {
        EntityType<?> value = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(ra.getEntityKey()));
        if (value != null && Minecraft.getInstance().level != null) {
            this.entity = value.create(Minecraft.getInstance().level);
        } else {
            this.entity = null;
        }
    }

    @Override
    protected void render(RenderGuiOverlayEvent event) {
        if (entity != null) {
            Window window = event.getWindow();
            renderEntityInInventory(event.getGuiGraphics(), window.getGuiScaledWidth() / 2, window.getGuiScaledHeight() * 3 / 5, 30, entity);
        }
    }

    public static void renderEntityInInventory(GuiGraphics pGuiGraphics, int pX, int pY, int pScale, Entity pEntity) {
        // Copied from InventoryScreen
        Quaternionf pPose = (new Quaternionf()).rotateZ((float)Math.PI);
        Quaternionf pCameraOrientation = (new Quaternionf()).rotateY(90);
        pPose.mul(pCameraOrientation);
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate((double)pX, (double)pY, 50.0D);
        pGuiGraphics.pose().mulPoseMatrix((new Matrix4f()).scaling((float)pScale, (float)pScale, (float)(-pScale)));
        pGuiGraphics.pose().mulPose(pPose);
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();

        entityrenderdispatcher.setRenderShadow(false);
        RenderSystem.runAsFancy(() -> {
            entityrenderdispatcher.render(pEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, pGuiGraphics.pose(), pGuiGraphics.bufferSource(), 15728880);
        });
        pGuiGraphics.flush();
        entityrenderdispatcher.setRenderShadow(true);
        pGuiGraphics.pose().popPose();
        Lighting.setupFor3DItems();
    }
}
