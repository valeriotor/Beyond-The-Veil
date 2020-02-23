package com.valeriotor.BTV.dreaming.dreams;

import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.dreaming.DreamHandler;
import com.valeriotor.BTV.gui.Guis;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageOpenGuiToClient;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class DreamEldritch extends Dream{

	public DreamEldritch(String name, int priority) {
		super(name, priority);
	}

	@Override
	public boolean activatePos(EntityPlayer p, World w, BlockPos pos) {
		if(!DreamHandler.hasDreamtOfVoid(p)) {
				return searchStronghold(p, w, pos);
		}
		BTVPacketHandler.INSTANCE.sendTo(new MessageOpenGuiToClient(Guis.GuiAlienisDream), (EntityPlayerMP)p);
		return true;
	}
	
	private boolean searchStronghold(EntityPlayer p, World w, BlockPos pos) {
		if(!p.getCapability(PlayerDataProvider.PLAYERDATA, null).getString("eldritchDream"))
			return false;
		pos = w.findNearestStructure("Stronghold", pos, false);
		if(pos != null) p.sendMessage(new TextComponentTranslation("dreams.alienissearch.success", new Object[] {pos.getX(), pos.getZ()}));
		else p.sendMessage(new TextComponentTranslation("dreams.alienissearch.fail"));
		
		return true;
	}

}
