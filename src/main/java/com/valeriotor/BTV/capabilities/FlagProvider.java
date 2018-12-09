package com.valeriotor.BTV.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class FlagProvider implements ICapabilitySerializable<NBTTagCompound>{
	
	@CapabilityInject(IFlags.class)
	public static final Capability<IFlags> FLAG_CAP = null;
	
	public IFlags instance = FLAG_CAP.getDefaultInstance();
	
	public FlagProvider() {
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == FLAG_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == FLAG_CAP ? FLAG_CAP.<T> cast(this.instance) : null;
	}

	@Override
    public NBTTagCompound serializeNBT()
    {
        return (NBTTagCompound) FLAG_CAP.getStorage().writeNBT(FLAG_CAP, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
    	FLAG_CAP.getStorage().readNBT(FLAG_CAP, this.instance, null, nbt);
}

}
