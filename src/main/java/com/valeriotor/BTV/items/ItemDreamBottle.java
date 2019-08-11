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
import com.valeriotor.BTV.dreaming.dreams.AbstractDream;
import com.valeriotor.BTV.fluids.ModFluids;
import com.valeriotor.BTV.gui.container.GuiContainerHandler;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.util.FluidHelper;
import com.valeriotor.BTV.util.ItemHelper;

import net.minecraft.block.BlockDispenser;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.DispenseFluidContainer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import thaumcraft.api.aspects.AspectHelper;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.items.ItemsTC;

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
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(worldIn.isRemote) return super.onItemRightClick(worldIn, playerIn, handIn);
		if(handIn == EnumHand.MAIN_HAND) {
			if(!playerIn.isSneaking()) {
				BlockPos pos = playerIn.getPosition();
				playerIn.openGui(BeyondTheVeil.instance, GuiContainerHandler.DREAM_BOTTLE, worldIn, pos.getX(), pos.getY(), pos.getZ());
			} else {
				dream(playerIn);
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	public void dream(EntityPlayer playerIn) {
		ItemStack stack = playerIn.getHeldItemMainhand();
		Map<Integer, AbstractDream> dreams = new HashMap<>();
		NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
		for(int i = 0; i < (this == ItemRegistry.dream_bottle ? 4 : 1); i++) {
			if(nbt.hasKey(String.format("slot%d", i))) {
				ItemStack stack2 = new ItemStack(nbt.getCompoundTag(String.format("slot%d", i)));
				if(stack2.getItem() == ItemsTC.crystalEssence)
					dreams.put(i, DreamRegistry.dreams.get(AspectHelper.getObjectAspects(stack2).getAspects()[0].getName()));
			}
		}
		Comparator<Entry<Integer, AbstractDream>> comparator = 
				(e1, e2) -> (e1.getValue().priority == e2.getValue().priority) ? 1 : Integer.compare(e1.getValue().priority, e2.getValue().priority);
		SortedSet<Map.Entry<Integer, AbstractDream>> set = new TreeSet<Map.Entry<Integer, AbstractDream>>(comparator);
		set.addAll(dreams.entrySet());
		System.out.println("Dream Entry Set: " + dreams.entrySet());
		System.out.println("Sorted Entry Set: " + set);
		IFluidHandler fh = FluidUtil.getFluidHandler(stack);
		if(fh instanceof FluidHandlerItemStack) {
			FluidHandlerItemStack fluid = (FluidHandlerItemStack) fh;
			if(fluid.getFluid() == null) return;
			int amount = fluid.getFluid().amount;
			int amountHolder = amount;
			for(Entry<Integer, AbstractDream> entry : set) {
				if(amount < 1000) break;
				if(entry.getValue().activate(playerIn, playerIn.world, ThaumcraftCapabilities.getKnowledge(playerIn))) {
					nbt.removeTag(String.format("slot%d", entry.getKey().intValue()));
					amount -= 1000;
				}
			}
			fluid.drain(amountHolder - amount,  true);
			
		}
		
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		
		String aspects = I18n.format("tooltip.dream_bottle.containing");
		NBTTagCompound nbt = ItemHelper.checkTagCompound(stack);
		boolean none = true;
		for(int i = 0; i < (this == ItemRegistry.dream_bottle ? 4 : 1); i++) {
			if(nbt.hasKey(String.format("slot%d", i))) {
				ItemStack stack2 = new ItemStack(nbt.getCompoundTag(String.format("slot%d", i)));
				if(stack2.getItem() == ItemsTC.crystalEssence) {
					aspects = aspects.concat(AspectHelper.getObjectAspects(stack2).getAspects()[0].getName().toUpperCase() + " ");
					none = false;
				}
			}
		}
		if(none) aspects = aspects.concat(I18n.format("tooltip.dream_bottle.none"));
		tooltip.add(aspects);
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
