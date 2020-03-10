package com.valeriotor.beyondtheveil.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ResearchProvider implements ICapabilitySerializable<NBTTagCompound>{

	
	@CapabilityInject(IResearch.class)
	public static final Capability<IResearch> RESEARCH = null;
	
	public IResearch instance = RESEARCH.getDefaultInstance();
	
	public ResearchProvider() {}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == RESEARCH;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == RESEARCH ? RESEARCH.<T> cast(this.instance) : null;
	}
	
	@Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) RESEARCH.getStorage().writeNBT(RESEARCH, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
    	RESEARCH.getStorage().readNBT(RESEARCH, this.instance, null, nbt);
	}
}
