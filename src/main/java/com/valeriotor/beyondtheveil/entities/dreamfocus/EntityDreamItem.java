package com.valeriotor.beyondtheveil.entities.dreamfocus;

import java.util.List;

import javax.vecmath.Point3d;

import com.valeriotor.beyondtheveil.items.ItemBlackjack;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.tileEntities.TileDreamFocus;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityDreamItem extends EntityItem implements IDreamEntity{
	private int pointCounter = 0;
	private BlockPos focus = null;
	private boolean loadEntity = false;
	private boolean removeEntity = false;
	public EntityDreamItem(World w) {
		super(w);
	}
	public EntityDreamItem(World worldIn, double x, double y, double z, ItemStack stack, BlockPos focusPos) {
		super(worldIn, x, y, z, stack);
		this.focus = focusPos;
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		this.travel();
		if(world.isRemote) {
			this.world.spawnParticle(EnumParticleTypes.REDSTONE, posX, posY, posZ, 255, 0, 0);
			return;
		}
		if(loadEntity) {
			TileEntity te = this.world.getTileEntity(focus);
			if(te instanceof TileDreamFocus) {
				TileDreamFocus tdf = (TileDreamFocus)te;
				tdf.addDreamEntity(this);
			}
			loadEntity = false;
		}
		if(removeEntity) {
			EntityItem e = new EntityItem(this.world, this.posX, this.posY, this.posZ, this.getItem());
			this.world.spawnEntity(e);
			e.motionX = 0;
			e.motionZ = 0;
			this.world.removeEntity(this);	
		}
	}
	
	@Override
	public void onUpdate() {
		this.onEntityUpdate();
	}
	
	@Override
	public Point3d getNextPoint(List<Point3d> ps) {
		if(this.pointCounter < ps.size()) {
			Point3d p = ps.get(pointCounter);
			this.pointCounter++;
			ItemStack stack = this.getItem();
			if(stack.getItem() instanceof ItemSword) {
				this.world.getEntities(EntityLiving.class, e -> e.getDistanceSq(this) < 0.5)
				.forEach(e -> e.attackEntityFrom(DamageSource.GENERIC, ((ItemSword)stack.getItem()).getAttackDamage()));
			} else if(stack.getItem() == ItemRegistry.blackjack) {
				this.world.getEntities(EntityLiving.class, e -> e.getDistanceSq(this) < 1.5)
				.forEach(e -> ItemBlackjack.processInteract(stack, e, 0.2, false));
			}
			return p;
		}
		this.removeEntity = true;
		return null;
	}
	
	public void removeEntity() {
		this.removeEntity = true;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("point", pointCounter);
		if(focus != null) compound.setLong("focus", focus.toLong());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.pointCounter = compound.getInteger("point");
		if(compound.hasKey("focus")) {
			this.focus = BlockPos.fromLong(compound.getLong("focus"));
			this.loadEntity = true;
			
		}
		super.readFromNBT(compound);
	}
	
	public void travel()
    {
        if (!this.isInWater())
        {
                
                
                    float f6 = 0.91F;
                    BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(this.posX, this.getEntityBoundingBox().minY - 1.0D, this.posZ);

                    if (this.onGround)
                    {
                        IBlockState underState = this.world.getBlockState(blockpos$pooledmutableblockpos);
                        f6 = underState.getBlock().getSlipperiness(underState, this.world, blockpos$pooledmutableblockpos, this) * 0.91F;
                    }

                    float f7 = 0.16277136F / (f6 * f6 * f6);
                    f6 = 0.91F;

                    if (this.onGround)
                    {
                        IBlockState underState = this.world.getBlockState(blockpos$pooledmutableblockpos.setPos(this.posX, this.getEntityBoundingBox().minY - 1.0D, this.posZ));
                        f6 = underState.getBlock().getSlipperiness(underState, this.world, blockpos$pooledmutableblockpos, this) * 0.91F;
                    }


                    this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

                    

                   
                    blockpos$pooledmutableblockpos.setPos(this.posX, 0.0D, this.posZ);


                    this.motionX *= (double)f6;
                    this.motionZ *= (double)f6;
                    blockpos$pooledmutableblockpos.release();
                
        }
        else
        {
            double d0 = this.posY;
            float f2 = 0.02F;


            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionY *= 0.800000011920929D;

            if (!this.hasNoGravity())
            {
                this.motionY -= 0.02D;
            }

            if (this.collidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + d0, this.motionZ))
            {
                this.motionY = 0.30000001192092896D;
            }
        }
        

    }

}
