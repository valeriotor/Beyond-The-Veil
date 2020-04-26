package com.valeriotor.beyondtheveil.entities;

import java.util.UUID;

import com.valeriotor.beyondtheveil.animations.Animation;
import com.valeriotor.beyondtheveil.animations.AnimationRegistry;
import com.valeriotor.beyondtheveil.blocks.BlockRegistry;
import com.valeriotor.beyondtheveil.entities.AI.AIRevenge;
import com.valeriotor.beyondtheveil.entities.AI.AISurgeon;
import com.valeriotor.beyondtheveil.items.ItemRegistry;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessageSurgeonToClient;
import com.valeriotor.beyondtheveil.tileEntities.TileWateryCradle;
import com.valeriotor.beyondtheveil.tileEntities.TileWateryCradle.PatientStatus;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class EntitySurgeon extends EntityMob implements IPlayerMinion{
	
	private UUID master;
	private static final DataParameter<Integer> OPCODE = EntityDataManager.<Integer>createKey(EntitySurgeon.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> ANIMATION = EntityDataManager.<Integer>createKey(EntitySurgeon.class, DataSerializers.VARINT);
	private BlockPos container;
	private BlockPos cradle;
	public ItemStack patient;
	public int hearts = 0;
	public int spines = 0;
	public Animation surgeryAnimation;
	
	public EntitySurgeon(World worldIn) {
		super(worldIn);
	}
	
	@Override
	protected void initEntityAI() {
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(0, new AISurgeon(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.5D, true));
        this.targetTasks.addTask(2, new AIRevenge(this));
	}

	protected void applyEntityAttributes() {
	    super.applyEntityAttributes(); 

	   getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
	   getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	   getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
	   getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
	   
	   getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(OPCODE, -1);
		this.dataManager.register(ANIMATION, 0);
	}
	
	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
		if(hand == EnumHand.MAIN_HAND && !player.world.isRemote) {
			if(player.getPersistentID().equals(this.getMasterID())) {
				if(player.getHeldItemMainhand().getItem() == ItemRegistry.surgery_tools) {
					BTVPacketHandler.INSTANCE.sendTo(new MessageSurgeonToClient(this.getEntityId()), (EntityPlayerMP)player);
					return EnumActionResult.SUCCESS;
				} else if(player.getHeldItemMainhand().isEmpty()) {
					ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ItemRegistry.spine, this.spines));
					ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(BlockRegistry.BlockHeart, this.hearts));
					this.spines = 0;
					this.hearts = 0;
				}
			}
		}
		return super.applyPlayerInteraction(player, vec, hand);
	}

	@Override
	public EntityPlayer getMaster() {
		return world.getMinecraftServer().getPlayerList().getPlayerByUUID(this.master);
	}

	@Override
	public UUID getMasterID() {
		return this.master;
	}

	@Override
	public void setMaster(EntityPlayer p) {
		this.master = p.getPersistentID();
	}
	
	public void setOperation(int id) {
		this.dataManager.set(OPCODE, id);
	}
	
	public int getOperation() {
		return this.dataManager.get(OPCODE);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("operation", this.dataManager.get(OPCODE));
		if(this.master != null) compound.setString("master", this.master.toString());
		if(this.container != null) compound.setLong("container", container.toLong());
		if(this.cradle != null) compound.setLong("cradle", cradle.toLong());
		if(this.patient != null) compound.setTag("patient", this.patient.writeToNBT(new NBTTagCompound()));
		compound.setInteger("hearts", hearts);
		compound.setInteger("spines", spines);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.dataManager.set(OPCODE, compound.getInteger("operation"));
		if(compound.hasKey("master")) this.master = UUID.fromString(compound.getString("master"));
		if(compound.hasKey("container")) this.container = BlockPos.fromLong(compound.getLong("container"));
		if(compound.hasKey("cradle")) this.cradle = BlockPos.fromLong(compound.getLong("cradle"));
		if(compound.hasKey("patient")) this.patient = new ItemStack(compound.getCompoundTag("patient"));
		this.hearts = compound.getInteger("hearts");
		this.spines = compound.getInteger("spines");
	}
	
	public void setContainer(BlockPos pos) {
		this.container = pos;
	}
	
	public BlockPos getContainer() {
		return this.container;
	}
	
	public BlockPos getCradle() {
		return this.cradle;
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if(this.world.isRemote) {
			if(this.surgeryAnimation == null) {
				if(this.dataManager.get(ANIMATION) == 1) {
					this.surgeryAnimation = new Animation(AnimationRegistry.surgeon_surgery);
				}
			} else {
				this.surgeryAnimation.update();
				if(this.surgeryAnimation.isDone()) {
					this.surgeryAnimation = null;
				}
			}
			
			return;
		}
		if((this.ticksExisted & 63) == 0) {
			if(this.cradle == null || this.getDistanceSq(cradle) > 256 || !(this.world.getTileEntity(cradle) instanceof TileWateryCradle)) {
				for(int x = -5; x <= 5; x++) {
					for(int y = 0; y <= 1; y++) {
						for(int z = -5; z <= 5; z++) {
							BlockPos pos = this.getPosition().add(x, y, z);
							if(world.getBlockState(pos).getBlock() == BlockRegistry.BlockWateryCradle) {
								if(world.getTileEntity(pos) instanceof TileWateryCradle) {
									this.cradle = pos;
									break;
								}
							}
						}
					}	
				}
			}
		}
		if(this.patient != null && !this.patient.isEmpty()) {
			if(this.container != null && this.getDistanceSq(container) < 256) {
				Vec3d vec = new Vec3d(container.add(posX-container.getX(), posY-container.getY(), posZ-container.getZ())).normalize().scale(0.2);
				BlockPos otherPos = container.add(vec.x,vec.y,vec.z);
				double dist = this.getDistanceSq(otherPos);
				if(dist > 4) {
					this.getNavigator().setPath(this.getNavigator().getPathToPos(otherPos), 1.2);
				} else {
					TileEntity te = world.getTileEntity(container);
					ItemStack hearts = new ItemStack(BlockRegistry.BlockHeart, this.hearts);
					ItemStack spines = new ItemStack(ItemRegistry.spine, this.spines);
					if(te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
						IItemHandler cap = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
						for(int i = 0; i < cap.getSlots() && !(this.patient.isEmpty() && spines.isEmpty() && hearts.isEmpty()); i++) {
							this.patient = cap.insertItem(i, this.patient, false);
							hearts = cap.insertItem(i, hearts, false);
							spines = cap.insertItem(i, spines, false);
						}
					}
					this.hearts = hearts.getCount();
					this.spines = spines.getCount();
					if(!this.patient.isEmpty()) {
						EntityLiving e = PatientStatus.getEntityFromStack(world, patient);
						if(e != null) {
							e.setPosition(posX, posY, posZ);
							this.world.spawnEntity(e);
						}
						this.patient = ItemStack.EMPTY;
					}
					
				}
			}
			
		}
	}
	
	public void setSurgeryAnimation(boolean set) {
		this.dataManager.set(ANIMATION, set ? 1 : 0);
	}
	
	public Animation getSurgeryAnimation() {
		return this.surgeryAnimation;
	}
	
}
