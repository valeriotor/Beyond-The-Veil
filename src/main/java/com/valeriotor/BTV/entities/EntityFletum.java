package com.valeriotor.BTV.entities;

import com.valeriotor.BTV.fluids.ModFluids;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.tileEntities.TileLacrymatory;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFletum extends EntityCreature implements IWeepingEntity{

	private int animationTicks = 0;
	private int tearTicks;
	private BlockPos lacrymatory;
	
	public EntityFletum(World worldIn) {
		super(worldIn);
		this.tearTicks = 500;
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(this.world.isRemote) {
			this.animationTicks++;
			this.animationTicks%=200;
		}else {
			this.tearTicks--;
			if(this.tearTicks <= 0) {
				TileLacrymatory.fillWithTears(this);
				this.tearTicks = 500;
			}
		}
	}
	
	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
		if(player.getHeldItem(hand).getItem() == Items.AIR) {
			if(this.lacrymatory != null) {
				TileEntity te = this.world.getTileEntity(lacrymatory);
				if(te instanceof TileLacrymatory) {
					TileLacrymatory tl = (TileLacrymatory) te;
					tl.setWeeper(null);
				}
			}
			if(!this.world.isRemote) {
				ItemStack item = new ItemStack(ItemRegistry.held_fletum);
				player.addItemStackToInventory(item);
				this.world.removeEntity(this);
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
	
	@SideOnly(Side.CLIENT)
	public float getAnimationTicks() {
		return this.animationTicks;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.tearTicks = compound.getInteger("tearTicks");
		if(compound.hasKey("lacrymatory"))
			this.lacrymatory = BlockPos.fromLong(compound.getLong("lacrymatory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("tearTicks", this.tearTicks);
		if(this.lacrymatory != null)
			compound.setLong("lacrymatory", this.lacrymatory.toLong());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void onDeath(DamageSource cause) {
		if(this.lacrymatory != null) {
			TileEntity te = this.world.getTileEntity(lacrymatory);
			if(te instanceof TileLacrymatory) {
				TileLacrymatory tl = (TileLacrymatory) te;
				tl.setWeeper(null);
			}
		}
		super.onDeath(cause);
	}
	
	@Override
	protected boolean canDespawn() {
		return false;
	}
	
	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}
	
	@Override
	public void setLacrymatory(BlockPos pos) {
		this.lacrymatory = pos;
	}

	@Override
	public BlockPos getLacrymatory() {
		return this.lacrymatory;
	}
	
}
