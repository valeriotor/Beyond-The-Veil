package com.valeriotor.BTV.entities;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.entities.AI.AIDwellerFish;
import com.valeriotor.BTV.entities.AI.AIDwellerFollowPlayer;
import com.valeriotor.BTV.entities.AI.AIDwellerWanderHamlet;
import com.valeriotor.BTV.events.ServerTickEvents;
import com.valeriotor.BTV.gui.DialogueHandler;
import com.valeriotor.BTV.gui.DialogueHandler.Dialogues;
import com.valeriotor.BTV.gui.Guis;
import com.valeriotor.BTV.items.ItemDrink;
import com.valeriotor.BTV.items.ItemRegistry;
import com.valeriotor.BTV.lib.BTVSounds;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageOpenGuiToClient;
import com.valeriotor.BTV.research.ResearchUtil;
import com.valeriotor.BTV.util.PlayerTimer;
import com.valeriotor.BTV.util.SyncUtil;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityHamletDweller extends EntityCreature implements IMerchant{
	
	private EntityHamletDweller.ProfessionsEnum profession;
	public BlockPos villageCenter;
	public BlockPos home;
	public BlockPos destination;
	private EntityPlayer customer;
	private EntityPlayer talkingPlayer;
	private MerchantRecipeList buyingList;
	public int goWorshipTime;
	public int goHomeTime;
	private int drunkStatus = 0;
	private boolean thirsty = false;
	private boolean talking = false;
	private boolean receivedGold = false;
	private int talkTime = 0;
	private int followTime = 0;
	private int fishTime = 0;
	private UUID followPlayer = null;
	private static final DataParameter<Integer> PROFESSION = EntityDataManager.<Integer>createKey(EntityHamletDweller.class, DataSerializers.VARINT);
	
	public EntityHamletDweller(World worldIn) {
		this(worldIn, getProfessionByID(worldIn.rand.nextInt(EntityHamletDweller.ProfessionsEnum.values().length)), null, null);
	}
	
	public EntityHamletDweller(World worldIn, EntityHamletDweller.ProfessionsEnum prof, BlockPos villageCenter, BlockPos home) {
		super(worldIn);
		this.setProfession(prof);
		if(villageCenter != null) this.setVillageCenter(villageCenter);
		else this.setVillageCenter(this.getPosition().add(10,0,10));
		if(home != null) this.setHome(home);
		this.goWorshipTime = this.world.rand.nextInt(12000)+9000;
		this.goHomeTime = Math.min(this.goWorshipTime+5000, 23000);
		this.home = this.getPosition();
		if(home!= null) this.setPosition(home.getX(), home.getY(), home.getZ());
		//this.populateBuyingList();
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
        this.tasks.addTask(5, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(2, new EntityAISwimming(this));
        this.tasks.addTask(1, new AIDwellerFollowPlayer(this));
        this.tasks.addTask(0, new AIDwellerFish(this));
        this.tasks.addTask(3, new AIDwellerWanderHamlet(this));
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
		
		if(this.talkingPlayer != null) {
			this.faceEntity(this.talkingPlayer, 180, 180);
			int n = (int) (this.world.getWorldTime() % 20);
			if(n == 0) this.motionX = 0.002;
			else if(n == 10) this.motionX = -0.002;
			this.getNavigator().clearPath();
		}
		
		if(this.customer != null) {
			this.faceEntity(this.customer, 180, 180);
			int n = (int) (this.world.getWorldTime() % 20);
			if(n == 0) this.motionX = 0.005;
			else if(n == 10) this.motionX = -0.005; 
			this.getNavigator().clearPath();
		}
		
		if(this.talking) {
			this.talkTime++;
			if(this.getProfession() != ProfessionsEnum.FISHERMAN)
				this.getNavigator().clearPath();
			if(this.talkTime>20) {
				this.talking = false;
				this.talkTime = 0;
			}
			return;
		}
		
		if(this.world.isRemote) return;
		if(this.profession == EntityHamletDweller.ProfessionsEnum.FISHERMAN) {
			if(this.followPlayer == null) this.followTime--;
			else if(this.world.getPlayerEntityByUUID(followPlayer).getDistance(this) > 32) this.followPlayer = null;
			if(this.world.getBlockState(this.getPosition().down()).getBlock() == BlockRegistry.BlockSlugBait && this.fishTime == 0) {
				List<EntityItem> items = world.getEntities(EntityItem.class, e -> e.getDistance(this) < 3);
				for(EntityItem item : items) {
					if(item.getItem().getItem() == Items.GOLD_INGOT) {
						item.getItem().shrink(1);
						this.fishTime = 60*20;
						break;
					}
				}
			}
			
		}else if(this.getProfession() == EntityHamletDweller.ProfessionsEnum.LHKEEPER || this.getProfession() == EntityHamletDweller.ProfessionsEnum.DRUNK ||
				this.getProfession() == EntityHamletDweller.ProfessionsEnum.STOCKPILER || this.getProfession() == EntityHamletDweller.ProfessionsEnum.CARPENTER ||
						this.getProfession() == EntityHamletDweller.ProfessionsEnum.SCHOLAR || this.getProfession() == EntityHamletDweller.ProfessionsEnum.BARTENDER) {
			if((this.world.getWorldTime()&1023) == 0) {
				this.getNavigator().tryMoveToXYZ(this.home.getX(), this.home.getY(), this.home.getZ(), 1.0);
			}
		}
		
		if(this.isInWater() && this.world.getBlockState(this.getPosition().offset(this.getHorizontalFacing()).down()) != Blocks.WATER) {
			this.motionY += 0.1;
			this.setMoveForward((float) 0.3);
		}
		
		
	}
	
	public void setProfession(EntityHamletDweller.ProfessionsEnum prof) {
		this.profession = prof;
		this.dataManager.set(PROFESSION, prof.getID());
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("profession", this.dataManager.get(PROFESSION));
		if(this.getVillageCenter() != null && this.getDwellerHome() != null) {
			compound.setLong("villageCenter", this.getVillageCenter().toLong());
			compound.setLong("home", this.getDwellerHome().toLong());
			
		}
		if(this.destination != null) compound.setLong("destination", this.destination.toLong());
		compound.setInteger("drunkStatus", this.drunkStatus);
		compound.setInteger("followTime", this.followTime);
		compound.setInteger("fishTime", this.fishTime);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		int id = compound.getInteger("profession");
		this.setProfession(getProfessionByID(id));
		if(compound.hasKey("villageCenter") && compound.hasKey("home")) {
			this.setVillageCenter(BlockPos.fromLong(compound.getLong("villageCenter")));
			this.setHome(BlockPos.fromLong(compound.getLong("home")));
			
		}
		if(compound.hasKey("destination")) 
			this.destination = BlockPos.fromLong(compound.getLong("destination"));
		this.drunkStatus = compound.getInteger("drunkStatus");
		this.followTime = compound.getInteger("followTime");
		this.fishTime = compound.getInteger("fishTime");
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
	
	public BlockPos getDwellerHome() {
		return this.home;
	}
	
	public static EntityHamletDweller.ProfessionsEnum getProfessionByID(int id){
		EntityHamletDweller.ProfessionsEnum values[] = EntityHamletDweller.ProfessionsEnum.values();
		if(id < 0 || id >= values.length) {
			System.out.println("ERROR: getProfessionByID has received a parameter out of range.");
			return values[0];
		}
		return values[id];
	}
	
	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {

		this.faceEntity(player, 180F, 180F);
		this.motionX = 0.01;
		this.talking = true;
		ItemStack stack = player.getHeldItem(hand);
		if(!world.isRemote) {
			IPlayerData data = player.getCapability(PlayerDataProvider.PLAYERDATA, null);
			if(this.profession == ProfessionsEnum.FISHERMAN) {
				if(data.getString(PlayerDataLib.DAGON_DIALOGUE.apply(0)) && !data.getString(PlayerDataLib.DAGONQUEST)) {
					if(Block.getBlockFromItem(player.getHeldItem(hand).getItem()) == Blocks.GOLD_BLOCK && !this.receivedGold) {
						player.getHeldItem(hand).shrink(1);
						this.receivedGold = true;
						player.sendMessage(new TextComponentString("�5�o" + new TextComponentTranslation("dweller.fisherman.dagon").getFormattedText()));
						data.incrementOrSetInteger(PlayerDataLib.DAGON_GOLD, 1, 1, false);
						if(data.getInteger(PlayerDataLib.DAGON_GOLD) == 3) {
							SyncUtil.addStringDataOnServer(player, false, PlayerDataLib.DAGONQUEST);
							data.removeInteger(PlayerDataLib.DAGON_GOLD);
						}
						return EnumActionResult.SUCCESS;
					}
				}
				if(stack.getItem() == Items.GOLD_INGOT && ResearchUtil.isResearchComplete(player, "SLUGS")) {
					this.followTime = 60*20;
					this.followPlayer = player.getPersistentID();
				}
			}
			String key = String.format(PlayerDataLib.TALK_COUNT, this.profession.name());
			if(player.getHeldItem(hand).getItem() instanceof ItemDrink && player.getHeldItem(hand).getItem() != ItemRegistry.cup && this.getProfession() == EntityHamletDweller.ProfessionsEnum.DRUNK && this.thirsty) {
				player.getHeldItem(hand).shrink(1);
				this.drunkStatus++;
				this.thirsty = false;
				if(this.drunkStatus == 7) data.setInteger(key, 0, true);
				return EnumActionResult.SUCCESS;
			}else if(!this.doesOpenGui()) {
				int tc = data.getOrSetInteger(key, 0, true);
				int x = this.drunkStatus < 7 ? tc%this.profession.getTalkCount() + 4*this.drunkStatus : Math.min(tc, 7) + 28;
				String y = this.profession == EntityHamletDweller.ProfessionsEnum.DRUNK ? "" : "§5§o";
				if(this.drunkStatus > 2) y = y.concat("§o");
				if(this.drunkStatus > 5) y = "§5§o";
				if(this.drunkStatus == 7 && x == 35) this.drunkStatus++;
				if(this.drunkStatus == 8) x = 35;
				if(x > 33) {
					y = "";
					if(!data.getString(PlayerDataLib.OLDTRUTH) && data.getString("dialogue" + Dialogues.IMPRESSED.getName())) {
						data.addString(PlayerDataLib.OLDTRUTH, false);
						data.addString("dialogue" + Dialogues.IKNOW.getName(), false);
					}
				}
				player.sendMessage(new TextComponentString(y+ new TextComponentTranslation(String.format("dweller.%s%s.greeting%d", this.profession == ProfessionsEnum.DRUNK ? "" : DialogueHandler.getFriendlyhood(player), this.profession.getName().toLowerCase(), x)).getFormattedText()));
				if(tc % 4 == 3 && this.drunkStatus < 7) this.thirsty = true;
				else this.thirsty = false;
				
				data.incrementOrSetInteger(key, 1, 0, true);
				
			}
			
			if(this.getProfession() == EntityHamletDweller.ProfessionsEnum.BARTENDER || this.getProfession() == EntityHamletDweller.ProfessionsEnum.CARPENTER) {
				if(this.buyingList == null) this.populateBuyingList();
			}
		}
		if(this.doesOpenGui()) {
			this.talkingPlayer = player;
			DialogueHandler.newDialogue(player, this);
			if(world.isRemote) BeyondTheVeil.proxy.openGui(Guis.GuiDialogueDweller);
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
		MINER(2, 1),
		LHKEEPER(3, 3),
		STOCKPILER(4, 3),
		DRUNK(5, 4),
		CARPENTER(6, 2),
		SCHOLAR(7, 2);
		
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



	@Override
	public void setCustomer(@Nullable EntityPlayer player) {
		this.customer = player;
		
	}

	@Override
	@Nullable
	public EntityPlayer getCustomer() {
		return this.customer;
	}

	@Override
	public MerchantRecipeList getRecipes(EntityPlayer player) {
		if(this.buyingList == null) this.buyingList = new MerchantRecipeList();
		return this.buyingList;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setRecipes(@Nullable MerchantRecipeList recipeList) {
		
	}

	@Override
	public void useRecipe(MerchantRecipe recipe) {}

	@Override
	public void verifySellingItem(ItemStack stack) {}

	@Override
	public World getWorld() {
		return this.world;
	}

	@Override
	public BlockPos getPos() {
		return this.getPosition();
	}
	
	private void populateBuyingList() {
		if(this.buyingList == null) this.buyingList = new MerchantRecipeList();
		if(this.getProfession() == EntityHamletDweller.ProfessionsEnum.BARTENDER) {
			this.buyingList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 1), new ItemStack(Items.AIR), new ItemStack(ItemRegistry.ale, 3)));
			this.buyingList.add(new MerchantRecipe(new ItemStack(Items.IRON_PICKAXE, 1), new ItemStack(Items.AIR), new ItemStack(ItemRegistry.mead, 1)));
			this.buyingList.add(new MerchantRecipe(new ItemStack(Blocks.STONE, 32), new ItemStack(Blocks.STONE, 32), new ItemStack(ItemRegistry.rum, 1)));
			this.buyingList.add(new MerchantRecipe(new ItemStack(Items.CLAY_BALL, 8), new ItemStack(Items.AIR), new ItemStack(ItemRegistry.vodka, 1)));
			this.buyingList.add(new MerchantRecipe(new ItemStack(Items.IRON_INGOT, 3), new ItemStack(Items.AIR), new ItemStack(ItemRegistry.wine, 1)));
		}else if(this.getProfession() == EntityHamletDweller.ProfessionsEnum.CARPENTER) {
			this.buyingList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 1), new ItemStack(Items.AIR), new ItemStack(ItemRegistry.canoe, 1)));
			this.buyingList.add(new MerchantRecipe(new ItemStack(this.getRandomSweetie(), 1), new ItemStack(Items.AIR), new ItemStack(BlockRegistry.DampWood, 6)));
			this.buyingList.add(new MerchantRecipe(new ItemStack(this.getRandomSweetie(), 1), new ItemStack(Items.AIR), new ItemStack(BlockRegistry.DampCanopy, 8)));
		}
		
	}
	
	private Item getRandomSweetie() {
		Random r = world.rand;
		int i = r.nextInt(6);
		switch(i) {
		case 0:
			return Items.APPLE;
		case 1:
			return Items.BEETROOT;
		case 2: 
			return Items.CARROT;
		case 3:
			return Items.POTATO;
		case 4:
			return Items.MELON;
		default:
			return Items.PUMPKIN_PIE;
		}
	}
	
	public EntityPlayerMP getTalkingPlayer() {
		return (EntityPlayerMP)this.talkingPlayer;
	}
	
	public void resetTalkingPlayer() {
		this.talkingPlayer = null;
	}
	
	public boolean doesOpenGui() {
		if(this.getProfession() == EntityHamletDweller.ProfessionsEnum.BARTENDER || this.getProfession() == EntityHamletDweller.ProfessionsEnum.CARPENTER ||
		   this.getProfession() == EntityHamletDweller.ProfessionsEnum.LHKEEPER || this.getProfession() == EntityHamletDweller.ProfessionsEnum.SCHOLAR) {
			return true;
		}
		return false;
	}
	
	@Override
	public void onDeath(DamageSource cause) {
		EntityPlayer p = DialogueHandler.getPlayer(this);
		if(p != null) {
			BeyondTheVeil.proxy.closeGui(p);
		}
		if(cause.getTrueSource() instanceof EntityPlayer) {
			EntityPlayer killer = (EntityPlayer) cause.getTrueSource();
			IPlayerData data = killer.getCapability(PlayerDataProvider.PLAYERDATA, null);
			if(!this.world.isRemote) {
				if(!ResearchUtil.isResearchComplete(killer, "IDOL")) {
					int newVal = SyncUtil.incrementIntDataOnServer(killer, false, PlayerDataLib.CURSE, 1, 1);
				}
				
			}
			if(this.profession == ProfessionsEnum.LHKEEPER && data.getString("hearing4") && !data.getString("killedkeeper")) {
				if(!this.world.isRemote) {
					SyncUtil.addStringDataOnServer(killer, false, "killedkeeper");
					SyncUtil.removeStringDataOnServer(killer, "dialogueend");
					SyncUtil.removeStringDataOnServer(killer, "dialogueend2");
					PlayerTimer pt = new PlayerTimer(killer, pl -> BTVPacketHandler.INSTANCE.sendTo(new MessageOpenGuiToClient(Guis.GuiDagon), (EntityPlayerMP)pl), 50);
					ServerTickEvents.addPlayerTimer(pt);
				}
			}
		}
		super.onDeath(cause);
	}
	
	public int getFollowTime() {
		return this.followTime;
	}
	
	public EntityPlayer getFollowPlayer() {
		if(followPlayer != null)
			return world.getPlayerEntityByUUID(followPlayer);
		return null;
	}
	
	public void setFollowPlayer(EntityPlayer p) {
		if(p == null) this.followPlayer = null;
		else this.followPlayer = p.getPersistentID();
	}
	
	public int getFishTime() {
		return this.fishTime;
	}
	
	public void decreaseFishTime() {
		this.fishTime--;
	}

}
