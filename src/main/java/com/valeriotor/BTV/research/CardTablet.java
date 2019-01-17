package com.valeriotor.BTV.research;

import com.valeriotor.BTV.items.ItemRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import thaumcraft.api.capabilities.IPlayerKnowledge;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.research.theorycraft.ResearchTableData;
import thaumcraft.api.research.theorycraft.TheorycraftCard;

public class CardTablet extends TheorycraftCard{
	
	
	
	@Override
	public int getInspirationCost() {
		return 1;
	}

	@Override
	public String getLocalizedName() {
		return new TextComponentTranslation("card.tablet.name", new Object[0]).getFormattedText();
	}

	@Override
	public String getLocalizedText() {
		return 
			new TextComponentTranslation("card.tablet.text", new Object[] { TextFormatting.BOLD + new TextComponentTranslation(new StringBuilder().append("tc.research_category.BEYOND_THE_VEIL").toString(), new Object[0]).getFormattedText() + TextFormatting.RESET }).getUnformattedText();
	}

	@Override
	public boolean activate(EntityPlayer player, ResearchTableData data) {
		data.addTotal("BEYOND_THE_VEIL", MathHelper.getInt(player.getRNG(), 45, 90));
		return true;
	}
	
	@Override
	public boolean initialize(EntityPlayer player, ResearchTableData data) {
		IPlayerKnowledge k = ThaumcraftCapabilities.getKnowledge(player);
		if(!k.isResearchKnown("inscription_complete")) {
			return false;
		}
		return super.initialize(player, data);
	}
	@Override
	public ItemStack[] getRequiredItems() {
		ItemStack stack = new ItemStack(ItemRegistry.tablet, 1, 1);
		return new ItemStack[] {stack};
	}
	
	public boolean[] getRequiredItemsConsumed()
	 {
	    return new boolean[] { true };
	 }

}
