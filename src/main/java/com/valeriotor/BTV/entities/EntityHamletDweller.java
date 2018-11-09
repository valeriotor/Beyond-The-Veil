package com.valeriotor.BTV.entities;

import com.valeriotor.BTV.entities.AI.AIWanderHamlet;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.BTVSounds;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class EntityHamletDweller extends EntityCreature{
	
	private EntityHamletDweller.ProfessionsEnum profession;
	private BlockPos villageCenter = this.getPosition().add(10, 0, 10);
	private BlockPos home = this.getPosition();
	private BlockPos destination;
	private int goWorshipTime;
	private int goHomeTime;
	private int talkCount = 0;
	private static final DataParameter<Integer> PROFESSION = EntityDataManager.<Integer>createKey(EntityHamletDweller.class, DataSerializers.VARINT);
	
	public EntityHamletDweller(World worldIn) {
		this(worldIn, getProfessionByID(worldIn.rand.nextInt(EntityHamletDweller.ProfessionsEnum.values().length)), null, null);
	}
	
	public EntityHamletDweller(World worldIn, EntityHamletDweller.ProfessionsEnum prof, BlockPos villageCenter, BlockPos home) {
		super(worldIn);
		this.setProfession(prof);
		if(villageCenter != null) this.setVillageCenter(villageCenter);
		if(home != null) this.setHome(home);
		this.goWorshipTime = this.world.rand.nextInt(12000)+9000;
		this.goHomeTime = Math.min(this.goWorshipTime+5000, 23000);
		if(home!= null) this.setPosition(home.getX(), home.getY(), home.getZ());
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(PROFESSION, 0);
	}
	
	
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64);
	}
	
	protected void initEntityAI()
    {	
	 	
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(2, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(2, new EntityAISwimming(this));
    }
	
	public EntityHamletDweller.ProfessionsEnum getProfession(){
		
		return this.getProfessionByID(this.dataManager.get(PROFESSION));
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
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if(this.world.isRemote) return;
		if(this.home == null) return;
		if(this.profession == EntityHamletDweller.ProfessionsEnum.FISHERMAN) {
			if(this.world.getWorldTime() == 100) {
				this.goWorshipTime = this.world.rand.nextInt(12000)+9000;
				this.goHomeTime = Math.min(this.goWorshipTime+5000, 23000);
				int r = this.world.rand.nextInt(11)-5;
				int s = r < 0 ? -5-r : 5-r;
				this.destination = this.villageCenter.add(r, 0, s);
				this.getNavigator().tryMoveToXYZ(this.home.getX(), this.home.getY(), this.home.getZ(), 1.0);
			}
			if(Math.abs(this.world.getWorldTime()-this.goWorshipTime) < 500) {
				if(destination == null) destination = this.villageCenter.offset(EnumFacing.getHorizontal(this.world.rand.nextInt(3)),3);
				this.getNavigator().tryMoveToXYZ(this.destination.getX(), this.villageCenter.getY(), this.destination.getZ(), 1.0);
			
			}
			else if(Math.abs(this.world.getWorldTime() - this.goHomeTime) < 1000 || Math.abs(this.world.getWorldTime() - 23500) < 1000) {
				this.getNavigator().tryMoveToXYZ(this.home.getX(), this.home.getY(), this.home.getZ(), 1.0);
			}
		}else if(this.profession == EntityHamletDweller.ProfessionsEnum.LHKEEPER || this.profession == EntityHamletDweller.ProfessionsEnum.DRUNK ||
				this.profession == EntityHamletDweller.ProfessionsEnum.STOCKPILER || this.profession == EntityHamletDweller.ProfessionsEnum.CARPENTER) {
			if((this.world.getWorldTime()&1023) == 0) {
				this.getNavigator().tryMoveToXYZ(this.home.getX(), this.home.getY(), this.home.getZ(), 1.0);
			}
		}
	}
	
	public void setProfession(EntityHamletDweller.ProfessionsEnum prof) {
		this.profession = prof;
		this.dataManager.set(PROFESSION, prof.getID());
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("profession", this.dataManager.get(PROFESSION));
		compound.setLong("villageCenter", this.getVillageCenter().toLong());
		compound.setLong("home", this.getHome().toLong());
		compound.setLong("destination", this.destination.toLong());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		int id = compound.getInteger("profession");
		this.setProfession(getProfessionByID(id));
		this.setVillageCenter(BlockPos.fromLong(compound.getLong("villageCenter")));
		this.setHome(BlockPos.fromLong(compound.getLong("home")));
		this.destination = BlockPos.fromLong(compound.getLong("destination"));
	}
	
	public void setVillageCenter(BlockPos pos) {
		if(pos == null) return;
		this.villageCenter = pos;
		int r = this.world.rand.nextInt(11)-5;
		int s = r < 0 ? -5-r : 5-r;
		this.destination = this.villageCenter.add(r, 0, s);
	}
	
	public BlockPos getVillageCenter() {
		return this.villageCenter;
	}
	
	public void setHome(BlockPos pos) {
		if(pos == null) return;
		this.home = pos;
	}
	
	public BlockPos getHome() {
		return this.home;
	}
	
	private static EntityHamletDweller.ProfessionsEnum getProfessionByID(int id){
		EntityHamletDweller.ProfessionsEnum temp = EntityHamletDweller.ProfessionsEnum.values()[id];
		return temp;
	}
	
	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
		if(!world.isRemote) {		//DEBUG
		//player.sendMessage(new TextComponentString(this.villageCenter.toString() + "\n" + this.home.toString() + "\n" + this.profession + "\n" + this.profession.getName() + "\n" + this.profession.getID()));
			//if(this.profession == EntityHamletDweller.ProfessionsEnum.FISHERMAN)
			//	player.sendMessage(new TextComponentString(I18n.format("dweller.fisherman.greeting")));
		
			if(this.profession != EntityHamletDweller.ProfessionsEnum.BARTENDER) {
				player.sendMessage(new TextComponentString("§5§o" + I18n.format(String.format("dweller.%s.greeting%d", this.profession.getName().toLowerCase(), this.talkCount%this.profession.getTalkCount()))));
				this.talkCount++;
			}
		}
		return EnumActionResult.SUCCESS;
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return BTVSounds.dwellerIdle;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return BTVSounds.dwellerHurt;
	}
	
	
	
	public static enum ProfessionsEnum{
		FISHERMAN(0, 4),
		BARTENDER(1, 2),
		MINER(2, 0),
		LHKEEPER(3, 3),
		STOCKPILER(4, 4),
		DRUNK(5, 4),
		CARPENTER(6, 3);
		
		private final int id;
		private final int talkCount;
		
		private ProfessionsEnum(int id, int talkCount) {
			this.id = id;
			this.talkCount = talkCount;
		}
		
		public String getName() {
			return this.name();
		}
		
		public int getID() {
			return this.id;
		}
		
		public int getTalkCount() {
			return this.talkCount;
		}
		
	}

}
