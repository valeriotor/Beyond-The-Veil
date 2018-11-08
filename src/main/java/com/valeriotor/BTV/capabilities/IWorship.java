package com.valeriotor.BTV.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraftforge.common.util.INBTSerializable;

public interface IWorship {
	
	public void setLevel(int lv);
	
	public int getLevel();
	
	public void addLevel();
	
	public void removeLevel();
	
}
