package com.valeriotor.beyondtheveil.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PlayerDataProvider implements ICapabilitySerializable<NBTTagCompound>{
	
	@CapabilityInject(IPlayerData.class)
	public static final Capability<IPlayerData> PLAYERDATA = null;
	
	public IPlayerData instance = PLAYERDATA.getDefaultInstance();
	
	public PlayerDataProvider() {
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == PLAYERDATA;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == PLAYERDATA ? PLAYERDATA.<T> cast(this.instance) : null;
	}

	@Override
    public NBTTagCompound serializeNBT()
    {
        return (NBTTagCompound) PLAYERDATA.getStorage().writeNBT(PLAYERDATA, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
    	PLAYERDATA.getStorage().readNBT(PLAYERDATA, this.instance, null, nbt);
}

}
