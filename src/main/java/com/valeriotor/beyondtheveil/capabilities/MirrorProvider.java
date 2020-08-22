package com.valeriotor.beyondtheveil.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class MirrorProvider implements ICapabilitySerializable<NBTTagCompound>{

	@CapabilityInject(MirrorCapInstance.class)
	public static final Capability<MirrorCapInstance> MIRROR = null;
	
	public MirrorCapInstance instance = MIRROR.getDefaultInstance();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == MIRROR;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == MIRROR ? MIRROR.<T> cast(this.instance) : null;
	}
	
	@Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) MIRROR.getStorage().writeNBT(MIRROR, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
    	MIRROR.getStorage().readNBT(MIRROR, this.instance, null, nbt);
	}

}
