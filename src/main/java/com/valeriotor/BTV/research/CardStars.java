package com.valeriotor.BTV.research;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.theorycraft.ResearchTableData;
import thaumcraft.api.research.theorycraft.TheorycraftCard;

public class CardStars extends TheorycraftCard{
	
	int md1;
	int md2;
	//boolean knowsStars = false;                    // As you start to truly study stars this will change. That part of BTV is far off though... :(
	
	@Override
	public NBTTagCompound serialize() {
		NBTTagCompound nbt = super.serialize();
		nbt.setInteger("md1", this.md1);
		nbt.setInteger("md2", this.md2);
		//nbt.setBoolean("knowsStars", this.knowsStars);
		return nbt;
	}
	
	@Override
	public void deserialize(NBTTagCompound nbt) {
		super.deserialize(nbt);
		this.md1 = nbt.getInteger("md1");
		this.md2 = nbt.getInteger("md2");
		//this.knowsStars = nbt.getBoolean("knowsStars");
	}
	
	@Override
	public boolean initialize(EntityPlayer player, ResearchTableData data) {
		if(!ThaumcraftCapabilities.knowsResearch(player, new String[] {"FIRSTDREAMS"})) {
			return false;
		}
		Random r = new Random(getSeed());
		this.md1 = MathHelper.getInt(r, 1, 4);
		this.md2 = MathHelper.getInt(r, 1, 3);
		if(this.md1 == this.md2) this.md2++;
		return super.initialize(player, data);
	}
	
	
	
	@Override
	public int getInspirationCost() {
		return 1;
	}

	@Override
	public String getLocalizedName() {
		return new TextComponentTranslation("card.stars.name", new Object[0]).getFormattedText();
	}

	@Override
	public String getLocalizedText() {
		// if(this.knowsStars)
		return 
				new TextComponentTranslation("card.stars.text", new Object[] { TextFormatting.BOLD + new TextComponentTranslation(new StringBuilder().append("tc.research_category.BEYOND_THE_VEIL").toString(), new Object[0]).getFormattedText() + TextFormatting.RESET }).getUnformattedText();
		  
	}
	
	public ItemStack[] getRequiredItems()
	  {
	    return new ItemStack[] { new ItemStack(ItemsTC.celestialNotes, 1, this.md1), new ItemStack(ItemsTC.celestialNotes, 1, this.md2) };
	  }
	
	public boolean[] getRequiredItemsConsumed()
	  {
	    return new boolean[] { true, true };
	  }

	@Override
	public boolean activate(EntityPlayer player, ResearchTableData data) {
		data.addTotal("BEYOND_THE_VEIL", MathHelper.getInt(player.getRNG(), 35, 70));
		
		return true;
	}

}
