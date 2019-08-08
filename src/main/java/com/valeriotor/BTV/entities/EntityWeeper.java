package com.valeriotor.BTV.entities;

import com.valeriotor.BTV.fluids.ModFluids;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.tileEntities.TileLacrymatory;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWeeper extends EntityCreature implements IWeepingEntity{
	
	private int animationTicks = 0;
	private int fletumTicks = 10;
	private int tearTicks;
	private boolean increase = true;
	private boolean heartless = false;
	private BlockPos lacrymatory;
	private static final DataParameter<Boolean> SPINELESS = EntityDataManager.<Boolean>createKey(EntityWeeper.class, DataSerializers.BOOLEAN);
	
	public EntityWeeper(World worldIn) {
		this(worldIn, false);
	}
	
	public EntityWeeper(World worldIn, boolean spineless) {
		super(worldIn);
		this.tearTicks = 300;
		this.getDataManager().set(SPINELESS, spineless);
	}
	
	public void setHeartless(boolean heartless) {
		this.heartless = heartless;
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(SPINELESS, false);
	}
	 
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(this.world.isRemote) {
			if(this.getDataManager().get(SPINELESS)) {
				this.fletumTicks--;
				if(this.fletumTicks == 1) {
					for(int i = 0; i < 9; i++)
						for(int j = 0; j < 10; j++)
						this.world.spawnParticle(EnumParticleTypes.REDSTONE, posX+(((double)i%3)-1)/9-1/9, posY+j*0.16+0.3, posZ+(((double)i/3)-1)/9-1/9, 255, 0, 0);
					this.world.playSound(posX, posY, posZ, SoundEvents.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, SoundCategory.NEUTRAL, 1, 1, false);
				}
			}
			this.animationTicks++;
			this.animationTicks%=200;
		}else {
			if(this.getDataManager().get(SPINELESS)) {
				this.fletumTicks--;
				if(this.fletumTicks == 0) {
					EntityFletum fletum = new EntityFletum(this.world);
					fletum.setPosition(posX, posY+1.8, posZ);
					this.world.spawnEntity(fletum);
					if(this.lacrymatory != null) {
						TileEntity te = this.world.getTileEntity(lacrymatory);
						if(te instanceof TileLacrymatory) {
							TileLacrymatory tl = (TileLacrymatory) te;
							tl.setWeeper(null);
							tl.setWeeper(fletum);
							fletum.setLacrymatory(lacrymatory);
						}
					}
					this.world.removeEntity(this);
					return;
				}
			}
			this.tearTicks--;
			if(this.tearTicks <= 0) {
				TileLacrymatory.fillWithTears(this);
				this.tearTicks = 300;
			}
		}
		
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
	
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64);
	}
	
	protected void initEntityAI()
    {	
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(2, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(2, new EntityAISwimming(this));
    }
	
	@Override
	protected boolean canDespawn() {
		return false;
	}
	
	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}
	
	
	@SideOnly(Side.CLIENT)
	public float getAnimationTicks() {
		return this.animationTicks;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("heartless", this.heartless);
		compound.setInteger("tearTicks", this.tearTicks);
		if(this.lacrymatory != null)
			compound.setLong("lacrymatory", this.lacrymatory.toLong());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.heartless = compound.getBoolean("heartless");
		this.tearTicks = compound.getInteger("tearTicks");
		if(compound.hasKey("lacrymatory"))
			this.lacrymatory = BlockPos.fromLong(compound.getLong("lacrymatory"));
		super.readFromNBT(compound);
	}
	
	public ItemStack getItemForm() {
		ItemStack stack = new ItemStack(ItemRegistry.held_weeper);
		ItemHelper.checkTagCompound(stack).setBoolean("heartless", this.heartless);
		ItemHelper.checkTagCompound(stack).setBoolean("spineless", this.getDataManager().get(SPINELESS));
		return stack;
	}
	
	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
		if(player.getHeldItem(hand).getItem() == ItemRegistry.blackjack) {
			if(this.lacrymatory != null) {
				TileEntity te = this.world.getTileEntity(lacrymatory);
				if(te instanceof TileLacrymatory) {
					TileLacrymatory tl = (TileLacrymatory) te;
					tl.setWeeper(null);
				}
			}
			if(!this.world.isRemote) {
				ItemStack item = this.getItemForm();
				player.addItemStackToInventory(item);
				this.world.removeEntity(this);
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
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
