package com.valeriotor.BTV.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class DGProvider implements ICapabilitySerializable<NBTTagCompound>{

	
	
	@CapabilityInject(IWorship.class)
	public static final Capability<IWorship> LEVEL_CAP = null;
	
	public IWorship instance = LEVEL_CAP.getDefaultInstance();
	
	public DGProvider() {
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == LEVEL_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == LEVEL_CAP ? LEVEL_CAP.<T> cast(this.instance) : null;
	}
	
	 @Override
	    public NBTTagCompound serializeNBT()
	    {
	        return (NBTTagCompound) LEVEL_CAP.getStorage().writeNBT(LEVEL_CAP, this.instance, null);
	    }

	    @Override
	    public void deserializeNBT(NBTTagCompound nbt)
	    {
	        LEVEL_CAP.getStorage().readNBT(LEVEL_CAP, this.instance, null, nbt);
	}

}
