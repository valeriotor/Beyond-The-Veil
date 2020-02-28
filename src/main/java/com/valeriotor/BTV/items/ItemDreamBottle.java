package com.valeriotor.BTV.items;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.blocks.BlockRegistry;
import com.valeriotor.BTV.dreaming.DreamRegistry;
import com.valeriotor.BTV.dreaming.Memory;
import com.valeriotor.BTV.dreaming.dreams.Dream;
import com.valeriotor.BTV.entities.EntityFletum;
import com.valeriotor.BTV.events.special.CrawlerWorshipEvents;
import com.valeriotor.BTV.fluids.ModFluids;
import com.valeriotor.BTV.gui.container.GuiContainerHandler;
import com.valeriotor.BTV.lib.PlayerDataLib;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.multiblock.MultiblockRegistry;
import com.valeriotor.BTV.research.ResearchUtil;
import com.valeriotor.BTV.util.ItemHelper;
import com.valeriotor.BTV.util.SyncUtil;
import com.valeriotor.BTV.worship.CrawlerWorship;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.DispenseFluidContainer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

public class ItemDreamBottle extends Item{
	
	private static final int CAPACITY = 4000;
	
	public ItemDreamBottle(String name) {
		this.setMaxStackSize(1);
		this.setCreativeTab(References.BTV_TAB);
		setRegistryName(References.MODID, name);
		setUnlocalizedName(name);
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, DispenseFluidContainer.getInstance());
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new FluidHandlerDreamBottle(stack, CAPACITY);
	}
	
	public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }
	
	public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(handIn == EnumHand.MAIN_HAND) {
			if(!playerIn.isSneaking()) {
				BlockPos pos = playerIn.getPosition();
				playerIn.openGui(BeyondTheVeil.instance, GuiContainerHandler.DREAM_BOTTLE, worldIn, pos.getX(), pos.getY(), pos.getZ());
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
			}
			playerIn.setActiveHand(handIn);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase e) {
		if(e instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer)e;
			if(p.isSneaking() && !worldIn.isRemote) {
				IFluidHandler fh = FluidUtil.getFluidHandler(stack);
				if(!(fh instanceof FluidHandlerItemStack)) return stack;
				FluidStack fluid = ((FluidHandlerItemStack)fh).getFluid();
				int amount = 0;
				int required = 1000;
				NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
				CrawlerWorship cw = CrawlerWorshipEvents.getWorship(p);
				if(fluid != null) amount = fluid.amount;
				else return stack;
				if(cw != null && cw.improvesDreamBottle()) 
					required = 800;
				
				if(checkCombo(p, worldIn, stack, amount, required)) {
					if(!checkShoggothVat(p, worldIn, stack, amount, required) && !checkEndBath(p, worldIn, stack, amount, required))
						dream(p, stack, amount, required);
				} else
					dream(p, stack, amount, required);
				return stack;
			}
		}
			
		return stack;
	}
	
	public void dream(EntityPlayer playerIn, ItemStack stack, int amount, int required) {
		Map<Integer, Dream> dreams = new HashMap<>();
		NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
		for(int i = 0; i < (this == ItemRegistry.dream_bottle ? 4 : 1); i++) {
			if(nbt.hasKey(String.format("slot%d", i))) {
				ItemStack stack2 = new ItemStack(nbt.getCompoundTag(String.format("slot%d", i)));
				if(stack2.getItem() == ItemRegistry.memory_phial) {
					Memory m = Memory.getMemoryFromDataName(ItemHelper.checkStringTag(stack2, "memory", "none"));
					if(m != null)
						dreams.put(i, DreamRegistry.dreams.get(m));
				}
			}
		}
		Comparator<Entry<Integer, Dream>> comparator = 
				(e1, e2) -> (e1.getValue().priority == e2.getValue().priority) ? 1 : Integer.compare(e1.getValue().priority, e2.getValue().priority);
		SortedSet<Map.Entry<Integer, Dream>> set = new TreeSet<Map.Entry<Integer, Dream>>(comparator);
		set.addAll(dreams.entrySet());
		IFluidHandler fh = FluidUtil.getFluidHandler(stack);
		if(fh instanceof FluidHandlerItemStack) {
			FluidHandlerItemStack fluid = (FluidHandlerItemStack) fh;
			if(fluid.getFluid() == null) return;
			int amountHolder = amount;
			boolean dreamShrine = MultiblockRegistry.multiblocks.get("dream_shrine").checksOutBottomCenter(playerIn.world, playerIn.getPosition().offset(EnumFacing.DOWN,2));
			EntityPlayer target = null;
			BlockPos posTarget = null;
			EnumHand otherHand = null;
			boolean playerTag = false;
			if(dreamShrine) {
				for(EnumHand hand : EnumHand.values()) {
					ItemStack sigil = playerIn.getHeldItem(hand); 
					if(sigil.getItem() == ItemRegistry.sigil_pathway) {
						NBTTagCompound tag = ItemHelper.checkTagCompound(sigil);
						if(tag.hasKey("path")) {
							posTarget = BlockPos.fromLong(tag.getLong("path"));
							otherHand = hand;
							break;
						}
					} else if(sigil.getItem() == ItemRegistry.sigil_player) {
						NBTTagCompound tag = ItemHelper.checkTagCompound(sigil);
						if(tag.hasKey("player")) {
							playerTag = true;
							target = playerIn.world.getPlayerEntityByUUID(UUID.fromString(tag.getString("player")));
							otherHand = hand;
							break;
						}
					}
				}
			}
			if(playerTag && target == null) {
				playerIn.sendMessage(new TextComponentTranslation("use.dream_bottle.noplayer"));
				return;
			}
			for(Entry<Integer, Dream> entry : set) {
				if(amount < required) break;
				boolean activated = false;
				if(posTarget != null) activated = entry.getValue().activatePos(playerIn, playerIn.world, posTarget);
				else if(target != null) activated = entry.getValue().activatePlayer(playerIn, target, target.world);
				else activated = entry.getValue().activate(playerIn, playerIn.world);
				if(activated) {
					nbt.removeTag(String.format("slot%d", entry.getKey().intValue()));
					amount -= required;
				}
			}
			fluid.drain(amountHolder - amount,  true);
			if(otherHand != null && amount < amountHolder) playerIn.getHeldItem(otherHand).shrink(1); 
		}
		
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		
		String memories = I18n.format("tooltip.dream_bottle.containing");
		NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
		boolean none = true;
		for(int i = 0; i < (this == ItemRegistry.dream_bottle ? 4 : 1); i++) {
			if(nbt.hasKey(String.format("slot%d", i))) {
				ItemStack stack2 = new ItemStack(nbt.getCompoundTag(String.format("slot%d", i)));
				if(stack2.getItem() == ItemRegistry.memory_phial) {
					String s = ItemHelper.checkStringTag(stack2, "memory", "none");
					if(!s.equals("none"))
						memories = memories.concat(Memory.getMemoryFromDataName(s).getLocalizedName() + " ");
					none = false;
				}
			}
		}
		if(none) memories = memories.concat(I18n.format("tooltip.dream_bottle.none"));
		tooltip.add(memories);
		IFluidHandler fh = FluidUtil.getFluidHandler(stack);
		if(fh instanceof FluidHandlerItemStack) {
			FluidStack fluid = ((FluidHandlerItemStack)fh).getFluid();
			int amount = 0;
			if(fluid != null) amount = fluid.amount;
			tooltip.add(I18n.format("tooltip.dream_bottle.charges", amount));
		}
		
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	public static class FluidHandlerDreamBottle extends FluidHandlerItemStack{
		
		protected static final FluidStack EMPTY = new FluidStack(ModFluids.tears, 0); 
		
		public FluidHandlerDreamBottle(ItemStack container, int capacity) {
			super(container, capacity);
			 if (this.getFluid() == null){
				 setContainerToEmpty(); 
		     }
			
		}
		
		@Override
	    protected void setContainerToEmpty()
	    {
	        setFluid(EMPTY.copy()); 
	        container.getTagCompound().removeTag(FLUID_NBT_KEY);
	    }
		
		@Override
		public boolean canFillFluidType(FluidStack fluid) {
			return fluid.getFluid() == ModFluids.tears;
		}
		
		@Override
		public boolean canDrainFluidType(FluidStack fluid) {
			return fluid.getFluid() == ModFluids.tears;
		}
		
		
	}
	
	public boolean checkCombo(EntityPlayer p, World w, ItemStack stack, int amount, int required) {
		if(amount < 4*required) return false;
		EnumSet<Memory> mems = EnumSet.noneOf(Memory.class);
		NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
		for(int i = 0; i < (this == ItemRegistry.dream_bottle ? 4 : 1); i++) {
			if(nbt.hasKey(String.format("slot%d", i))) {
				ItemStack stack2 = new ItemStack(nbt.getCompoundTag(String.format("slot%d", i)));
				if(stack2.getItem() == ItemRegistry.memory_phial) {
					String s = ItemHelper.checkStringTag(stack2, "memory", "none");
					if(!s.equals("none"))
						mems.add(Memory.getMemoryFromDataName(s));
				}
			}
		}
		if(mems.contains(Memory.DARKNESS) && mems.contains(Memory.ELDRITCH) && mems.contains(Memory.LEARNING) && mems.contains(Memory.VOID))
			return true;
		return false;
	}
	
	public boolean checkShoggothVat(EntityPlayer p, World w, ItemStack stack, int amount, int required) {
		IFluidHandler fh = FluidUtil.getFluidHandler(stack);
		if(!(fh instanceof FluidHandlerItemStack)) return false;
		FluidStack fluid = ((FluidHandlerItemStack)fh).getFluid();
		NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
		
		int i = 0;
		BlockPos pos = p.getPosition();
		if(w.getBlockState(pos).getBlock() != BlockRegistry.BlockFluidTears)  return false;
		while(w.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() == BlockRegistry.BlockFluidTears && (i++) < 3) 
			pos = pos.offset(EnumFacing.NORTH);
		i = 0;
		while(w.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() == BlockRegistry.BlockFluidTears && (i++) < 3) 
			pos = pos.offset(EnumFacing.WEST);
		i = 0;
		while(w.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock() == BlockRegistry.BlockFluidTears && (i++) < 3) 
			pos = pos.offset(EnumFacing.DOWN);
		
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				for(int z = 0; z < 3; z++) {
					IBlockState state = w.getBlockState(pos.add(x, y, z));
					
					if(state.getBlock() != BlockRegistry.BlockFluidTears)
						return false;
					if(state.getBlock().getMetaFromState(state) != 0)
						return false;
				}
			}
		}
		AxisAlignedBB bbox = new AxisAlignedBB(pos, pos.add(3, 3, 3));
		List<EntityFletum> fleti = w.getEntitiesWithinAABB(EntityFletum.class, bbox);
		if(fleti.size() >= 3) {
			for(int x = 0; x < 3; x++) {
				for(int y = 0; y < 3; y++) {
					for(int z = 0; z < 3; z++) {
						w.setBlockToAir(pos.add(x, y, z));
					}
				}
			}
			for(int j = 0; j < 3; j++) {
				w.removeEntity(fleti.get(j));
			}
			for(int j = 0; j < 4; j++) {
				nbt.removeTag(String.format("slot%d", j));
			}
			fh.drain(4*required, true);
			EntityItem item = new EntityItem(w, pos.getX()+1, pos.getY()+1, pos.getZ()+1, new ItemStack(ItemRegistry.held_shoggoth));
			w.spawnEntity(item);
			return true;
		}
		
		return false;
	}
	
	public boolean checkEndBath(EntityPlayer p, World w, ItemStack stack, int amount, int required) {
		if(!p.isInWater()) return false;
		if(p.dimension != DimensionType.THE_END.getId()) return false;
		if(ResearchUtil.getResearchStage(p, "WATERWALKING") != 1) return false;
		NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
		for(int j = 0; j < 4; j++) {
			nbt.removeTag(String.format("slot%d", j));
		}
		IFluidHandler fh = FluidUtil.getFluidHandler(stack);
		fh.drain(4*required, true);
		SyncUtil.addStringDataOnServer(p, false, PlayerDataLib.ENDBATH);
		return false;
	}
	
}
