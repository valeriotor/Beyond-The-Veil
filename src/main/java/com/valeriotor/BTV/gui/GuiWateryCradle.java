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
				this.isAlreadyFilledBrain = te.getPatientType() == PatientTypes.WEEPER;
				this.isAlreadyHeartless = te.isPatientHeartless();
			} else this.mc.displayGuiScreen((GuiScreen)null);
		} else this.mc.displayGuiScreen((GuiScreen)null);
	}
	
	@Override
	public String getGuiLangName() {
		return "waterycradle";
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
	protected boolean isEastOptionAvailable() {
		return true;
	}
	
	@Override
	protected boolean isEastOptionGreyedOut() {
		return this.isAlreadyHeartless;
	}
	
	private static final ResourceLocation heart = new ResourceLocation(References.MODID + ":textures/items/heart.png");
	@Override
	public ResourceLocation getEastOptionTexture() {
		return heart;
	}
	
	@Override
	protected boolean isWestOptionAvailable() {
		return true;
	}
	
	@Override
	protected boolean isWestOptionGreyedOut() {
		return this.isAlreadyFilledBrain;
	}
	
	private static final ResourceLocation tears = new ResourceLocation(References.MODID + ":textures/items/held_weeper.png");
	@Override
	public ResourceLocation getWestOptionTexture() {
		return tears;
	}
	
	@Override
	public void doNorthAction() {
		BTVPacketHandler.INSTANCE.sendToServer(new MessageWateryCradle((byte)0, pos.getX(), pos.getY(), pos.getZ()));
		this.mc.displayGuiScreen((GuiScreen)null);
	}
	
	@Override
	public void doEastAction() {
		BTVPacketHandler.INSTANCE.sendToServer(new MessageWateryCradle((byte)3, pos.getX(), pos.getY(), pos.getZ()));
		this.mc.displayGuiScreen((GuiScreen)null);
	}
	
	@Override
	public void doWestAction() {
		BTVPacketHandler.INSTANCE.sendToServer(new MessageWateryCradle((byte)1, pos.getX(), pos.getY(), pos.getZ()));
		this.mc.displayGuiScreen((GuiScreen)null);
	}

}
