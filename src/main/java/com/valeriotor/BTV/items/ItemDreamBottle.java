package com.valeriotor.BTV.items;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import com.valeriotor.BTV.BeyondTheVeil;
import com.valeriotor.BTV.dreaming.DreamRegistry;
import com.valeriotor.BTV.dreaming.Memory;
import com.valeriotor.BTV.dreaming.dreams.Dream;
import com.valeriotor.BTV.fluids.ModFluids;
import com.valeriotor.BTV.gui.container.GuiContainerHandler;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.block.BlockDispenser;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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
				dream(p, stack);
				return stack;
			}
		}
			
		return stack;
	}
	
	public void dream(EntityPlayer playerIn, ItemStack stack) {
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
			int amount = fluid.getFluid().amount;
			int amountHolder = amount;
			for(Entry<Integer, Dream> entry : set) {
				if(amount < 1000) break;
				if(entry.getValue().activate(playerIn, playerIn.world)) {
					nbt.removeTag(String.format("slot%d", entry.getKey().intValue()));
					amount -= 1000;
				}
			}
			fluid.drain(amountHolder - amount,  true);
			
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
	
}
