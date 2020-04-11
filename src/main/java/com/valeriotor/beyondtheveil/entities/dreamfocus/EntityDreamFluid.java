package com.valeriotor.beyondtheveil.entities.dreamfocus;

import java.util.List;

import javax.vecmath.Point3d;

import com.valeriotor.beyondtheveil.tileEntities.TileDreamFocus;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class EntityDreamFluid extends EntityLiving implements IDreamEntity{
	private int pointCounter = 0;
	private BlockPos focus = null;
	private static final DataParameter<String> FLUIDNAME = EntityDataManager.<String>createKey(EntityDreamFluid.class, DataSerializers.STRING);
	private static final DataParameter<Byte> FLUIDAMOUNT = EntityDataManager.<Byte>createKey(EntityDreamFluid.class, DataSerializers.BYTE);
	private FluidStack fluid = null;
	private boolean loadEntity = false;
	private boolean removeEntity = false;
	
	public EntityDreamFluid(World worldIn) {
		super(worldIn);
		this.setSize(0.2F, 0.2F);
	}
	
	public EntityDreamFluid(World worldIn, FluidStack fluid, BlockPos focusPos) {
		super(worldIn);
		this.fluid = fluid;
		this.focus = focusPos;
		this.setSize(0.2F, 0.2F);
	}

	@Override
	public Point3d getNextPoint(List<Point3d> ps) {
		if(this.pointCounter < ps.size()) {
			Point3d p = ps.get(pointCounter);
			this.pointCounter++;
			return p;
		}
		this.removeEntity = true;
		return null;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		String s = this.fluid == null ? "null" : this.fluid.getFluid().getName();
		byte amount = this.fluid == null ? 0 : (byte)(this.fluid.amount / 100);
		this.dataManager.register(FLUIDNAME, s);
		this.dataManager.register(FLUIDAMOUNT, amount);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("fluid")) {
			this.fluid = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("fluid"));
			if(this.fluid != null) {
				this.dataManager.set(FLUIDNAME, fluid.getFluid().getName());
				this.dataManager.set(FLUIDAMOUNT, (byte)(this.fluid.amount/1000));
			}
		}

		this.pointCounter = compound.getInteger("point");
		if(compound.hasKey("focus")) {
			this.focus = BlockPos.fromLong(compound.getLong("focus"));
			this.loadEntity = true;
			
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("point", pointCounter);
		if(focus != null) compound.setLong("focus", focus.toLong());
		if(this.fluid != null) {
			compound.setTag("fluid", this.fluid.writeToNBT(new NBTTagCompound()));
		}
	}
	
	public String getFluidName() {
		return this.dataManager.get(FLUIDNAME);
	}
	
	public byte getTenths() {
		return this.dataManager.get(FLUIDAMOUNT);
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		/*this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        double d0 = this.motionX;
        double d1 = this.motionY;
        double d2 = this.motionZ;

        if (!this.hasNoGravity())
        {
            this.motionY -= 0.03999999910593033D;
        }

        if (this.world.isRemote)
        {
            this.noClip = false;
        }
        else
        {
            this.noClip = this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0D, this.posZ);
        }

        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        boolean flag = (int)this.prevPosX != (int)this.posX || (int)this.prevPosY != (int)this.posY || (int)this.prevPosZ != (int)this.posZ;
        float f = 0.98F;
        this.motionX *= (double)f;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= (double)f;

        if (this.onGround)
        {
            this.motionY *= -0.5D;
        }
        */
        /* ***************** */
		if(world.isRemote) return;
		/* ***************** */
		
		/*double d3 = this.motionX - d0;
        double d4 = this.motionY - d1;
        double d5 = this.motionZ - d2;
        double d6 = d3 * d3 + d4 * d4 + d5 * d5;

        if (d6 > 0.01D)
        {
            this.isAirBorne = true;
        }
		*/
		if(this.fluid != null) {
			this.dataManager.set(FLUIDNAME, fluid.getFluid().getName());
			this.dataManager.set(FLUIDAMOUNT, (byte)(this.fluid.amount/100));
		}
		if(loadEntity) {
			TileEntity te = this.world.getTileEntity(focus);
			if(te instanceof TileDreamFocus) {
				TileDreamFocus tdf = (TileDreamFocus)te;
				tdf.addDreamEntity(this);
			}
		}
		if(removeEntity) {
			this.world.removeEntity(this);
		}
	}
	
	@Override
	public boolean getIsInvulnerable() {
		return true;
	}

}
