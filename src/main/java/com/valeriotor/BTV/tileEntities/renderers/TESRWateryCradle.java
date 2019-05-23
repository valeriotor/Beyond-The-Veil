package com.valeriotor.BTV.tileEntities.renderers;

import org.lwjgl.opengl.GL11;

import com.valeriotor.BTV.blocks.BlockWateryCradle;
import com.valeriotor.BTV.entities.EntityCrawlingVillager;
import com.valeriotor.BTV.tileEntities.TileWateryCradle;
import com.valeriotor.BTV.tileEntities.TileWateryCradle.PatientTypes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;

public class TESRWateryCradle extends TileEntitySpecialRenderer<TileWateryCradle>{
	
	public void render(TileWateryCradle te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if(te.getPatientType() == PatientTypes.NONE) return;
		EntityCrawlingVillager patient = (EntityCrawlingVillager) te.getDummyEntity();
		EnumFacing facing = te.getState().getValue(BlockWateryCradle.FACING);
		float xOffset = facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH ? 0.5F : (facing == EnumFacing.WEST ? -0.5F : 1.5F);
		float zOffset = facing == EnumFacing.WEST || facing == EnumFacing.EAST ? 0.5F : (facing == EnumFacing.NORTH ? -0.5F : 1.5F);
		GL11.glPushMatrix();
		GL11.glScalef(0.99F, 0.89F, 0.99F);
		GlStateManager.translate((x+xOffset)/0.99, (y+0.04)/0.89, (z+zOffset)/0.99);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glRotatef((float) (-90*(facing.getHorizontalIndex()-2)), 0, 1, 0);
		//patient.setRotationYawHead(90*facing.getHorizontalIndex());
		patient.setLocationAndAngles((x+xOffset)/0.99, (y+0.04)/0.89, (z+zOffset), 0, 0);
		Minecraft.getMinecraft().getRenderManager().renderEntity(te.getDummyEntity(), 0,0,0, 0, partialTicks, false);
		GL11.glPopMatrix();
	}

}
