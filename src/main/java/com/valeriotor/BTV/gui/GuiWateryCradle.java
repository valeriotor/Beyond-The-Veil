package com.valeriotor.BTV.gui;

import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageWateryCradle;
import com.valeriotor.BTV.tileEntities.TileWateryCradle;
import com.valeriotor.BTV.tileEntities.TileWateryCradle.PatientTypes;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiWateryCradle extends GuiOptionWheel{

	private final BlockPos pos;
	private boolean isAlreadySpineless = false;
	private boolean isAlreadyFilledBrain = false;
	private boolean isAlreadyHeartless = false;
	
	public GuiWateryCradle(BlockPos pos) {
		this.pos = pos;
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		TileWateryCradle te = (TileWateryCradle) this.mc.player.world.getTileEntity(this.pos);
		if(te != null) {
			if(te.getPatientType() != PatientTypes.NONE) {
				this.isAlreadySpineless = te.isPatientSpineless();
				this.isAlreadyFilledBrain = te.doesPatientHaveFilledBrain();
				this.isAlreadyHeartless = te.isPatientHeartless();
			} else this.mc.displayGuiScreen((GuiScreen)null);
		} else this.mc.displayGuiScreen((GuiScreen)null);
	}
	
	@Override
	public String getGuiLangName() {
		return "guiWateryCradle";
	}
	
	@Override
	protected boolean isNorthOptionAvailable() {
		return true;
	}
	
	@Override
	protected boolean isNorthOptionGreyedOut() {
		return this.isAlreadySpineless;
	}
	
	private static final ResourceLocation spine = new ResourceLocation(References.MODID + ":textures/items/spine.png");
	@Override
	public ResourceLocation getNorthOptionTexture() {
		return spine;
	}
	
	@Override
	public void doNorthAction() {
		BTVPacketHandler.INSTANCE.sendToServer(new MessageWateryCradle((byte)0, pos.getX(), pos.getY(), pos.getZ()));
		this.mc.displayGuiScreen((GuiScreen)null);
	}

}
