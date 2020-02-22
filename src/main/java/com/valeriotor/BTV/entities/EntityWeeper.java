package com.valeriotor.BTV.entities;

import java.util.UUID;

import com.valeriotor.BTV.events.ServerTickEvents;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.BTVSounds;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.research.ResearchUtil;
import com.valeriotor.BTV.tileEntities.TileLacrymatory;
import com.valeriotor.BTV.util.ItemHelper;
import com.valeriotor.BTV.util.PlayerTimer;
import com.valeriotor.BTV.util.SyncUtil;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

public class EntityWeeper extends EntityCreature implements IWeepingEntity, IPlayerMinion{
	
	private int animationTicks = 0;
	private int fletumTicks = 10;
	private int transformTicks = 300;
	private int tearTicks;
	private int dialogue = -1;
	private int specialDialogue = -1;
	private int specialDialogueCounter = 0;
	private boolean increase = true;
	private boolean heartless = false;
	private BlockPos lacrymatory;
	private UUID master;
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
					fletum.setMaster(this.getMaster());
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
			this.specialDialogueCounter++;
			if(this.specialDialogueCounter > 300) {
				this.specialDialogueCounter = 0;
				EntityPlayer p = this.getMaster();
				if(this.specialDialogue < 0) {
					if(p != null && ResearchUtil.isResearchComplete(p, "DREAMBOTTLE") && !ResearchUtil.isResearchKnown(p, "SHOGGOTH")) {
						p.sendMessage(new TextComponentString(References.PURPLE + new TextComponentTranslation("weeper.shoggoth.call").getFormattedText()));
						this.specialDialogue = 0;
					}
				} else if(p != null && ResearchUtil.isResearchKnown(p, "SHOGGOTH") && this.specialDialogue < 7)
					this.specialDialogue = -1;
			}
			if(this.specialDialogue >= 7) {
				this.transformTicks--;
				EntityPlayer p = this.getMaster();
				if(p != null) {
					if(this.transformTicks == 225)
						p.sendMessage(new TextComponentString(References.PURPLE + new TextComponentTranslation("weeper.shoggoth.please").getFormattedText()));
					if(this.transformTicks == 150)
						p.sendMessage(new TextComponentString(References.PURPLE + new TextComponentTranslation("weeper.shoggoth.please2").getFormattedText()));
					if(this.transformTicks == 75)
						p.sendMessage(new TextComponentString(References.PURPLE + new TextComponentTranslation("weeper.shoggoth.promised").getFormattedText()));
				}
				if(this.transformTicks <= 0) {
					this.goCrazed();
				}
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
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64);
	}
	
	protected void initEntityAI()
    {	
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(2, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
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
		compound.setInteger("dialogue", this.dialogue);
		compound.setInteger("sdialogue", this.specialDialogue);
		if(this.master != null)
			compound.setString("master", this.master.toString());
		if(this.lacrymatory != null)
			compound.setLong("lacrymatory", this.lacrymatory.toLong());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.heartless = compound.getBoolean("heartless");
		this.tearTicks = compound.getInteger("tearTicks");
		this.dialogue = compound.getInteger("dialogue");
		this.specialDialogue = compound.getInteger("sdialogue");
		if(compound.hasKey("master"))
			this.master = UUID.fromString(compound.getString("master"));
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
		if(hand == EnumHand.OFF_HAND) return EnumActionResult.PASS;
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
				ItemHandlerHelper.giveItemToPlayer(player, item);
				this.world.removeEntity(this);
			}
			return EnumActionResult.SUCCESS;
		} else {
			if(player.getHeldItem(hand).getItem() == Items.AIR && !this.world.isRemote) {
				this.faceEntity(player, 3, 3);
				this.motionX = 0.01;
				if(this.specialDialogue >= 0) {
					if(this.specialDialogue < 7) {
						player.sendMessage(new TextComponentString(References.PURPLE + new TextComponentTranslation("weeper.shoggoth." + specialDialogue).getFormattedText()));
						this.specialDialogue++;
						if(this.specialDialogue == 7) {
							SyncUtil.addStringDataOnServer(player, false, "shoggothsecret");
							PlayerTimer pt = new PlayerTimer(player, p -> 
							player.sendMessage(new TextComponentString(References.PURPLE + new TextComponentTranslation("weeper.shoggoth.kill").getFormattedText())), 10);
							ServerTickEvents.addPlayerTimer(pt);
						}
					} else {
						player.sendMessage(new TextComponentString(References.PURPLE + new TextComponentTranslation("weeper.shoggoth.kill").getFormattedText()));
					}
				} else {
					if(this.dialogue == -1) this.dialogue = rand.nextInt(9);
					player.sendMessage(new TextComponentString(References.PURPLE + new TextComponentTranslation("weeper.dialogue." + dialogue).getFormattedText()));
					return EnumActionResult.SUCCESS;
				}
			}
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
	

	@Override
	public EntityPlayer getMaster() {
		if(master == null) return null;
		return world.getMinecraftServer().getPlayerList().getPlayerByUUID(master);
	}

	@Override
	public UUID getMasterID() {
		return master;
	}

	@Override
	public void setMaster(EntityPlayer p) {
		if(p != null)
			this.master = p.getPersistentID();
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return BTVSounds.weeper_idle;
	}
	
	public void goCrazed() {
		if(this.lacrymatory != null) {
			TileEntity te = this.world.getTileEntity(lacrymatory);
			if(te instanceof TileLacrymatory) {
				TileLacrymatory tl = (TileLacrymatory) te;
				tl.setWeeper(null);
			}
		}
		EntityCrazedWeeper craze = new EntityCrazedWeeper(world);
		craze.setPosition(posX, posY, posZ);
		craze.setRotationYawHead(this.getRotationYawHead());
		world.spawnEntity(craze);
		world.removeEntity(this);
	}

}
