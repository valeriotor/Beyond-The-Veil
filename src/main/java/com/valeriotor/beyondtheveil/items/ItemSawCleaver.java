package com.valeriotor.beyondtheveil.items;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;
import com.valeriotor.beyondtheveil.events.ServerTickEvents;
import com.valeriotor.beyondtheveil.lib.BTVSounds;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.network.BTVPacketHandler;
import com.valeriotor.beyondtheveil.network.MessagePlaySound;
import com.valeriotor.beyondtheveil.util.ItemHelper;
import com.valeriotor.beyondtheveil.util.PlayerTimer.PlayerTimerBuilder;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSawCleaver extends ItemSword implements IArtifactItem{

	public ItemSawCleaver(ToolMaterial material, String name) {
		super(material);
		this.setRegistryName(References.MODID, name);
		this.setUnlocalizedName("beyondtheveil:" + name);
		this.addPropertyOverride(new ResourceLocation("Extended"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
            	if(!stack.hasTagCompound()) return 0;
            	if(!stack.getTagCompound().hasKey("Extended")) return 0;
                return (entityIn != null && stack.getTagCompound().getBoolean("Extended")) ? 1 : 0;
            }
        });
		this.setMaxDamage(900);
		this.setCreativeTab(References.BTV_TAB);
	}
	
	@Override
	public float getAttackDamage() {
		return 16;
	}
	// THIS IS SO MESSY I AM SORRYYY
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
		if(hand == EnumHand.OFF_HAND) return super.onItemRightClick(worldIn, player, hand);
		if(!player.getHeldItem(hand).hasTagCompound()) {
			player.getHeldItem(hand).setTagCompound(new NBTTagCompound());
			
		}
		if(!player.getHeldItem(hand).getTagCompound().hasKey("Extended")) {
			player.getHeldItem(hand).getTagCompound().setBoolean("Extended", true);
		}else {
			boolean toExtend = !player.getHeldItem(hand).getTagCompound().getBoolean("Extended");
			int timeSinceLastAttack = player.ticksExisted - player.getLastAttackedEntityTime();
			BlockPos p = player.getPosition();
			TargetPoint t = new TargetPoint(player.dimension, p.getX(), p.getY(), p.getZ(), 15);
			if(worldIn.isRemote) return super.onItemRightClick(worldIn, player, hand);
			boolean timer = ServerTickEvents.removePlayerTimer("CleaverAttack", player);
			if(player.getCooledAttackStrength(0) == 1 && timer) {
				if(toExtend) {
					this.extendedAttack(player, player, true);
					BTVPacketHandler.INSTANCE.sendToAllAround(new MessagePlaySound(BTVSounds.getIdBySound(BTVSounds.sawcleavertransformattack), p.toLong()), t);
				}
				else BTVPacketHandler.INSTANCE.sendToAllAround(new MessagePlaySound(BTVSounds.getIdBySound(BTVSounds.sawcleavertransform), p.toLong()), t);
			}else BTVPacketHandler.INSTANCE.sendToAllAround(new MessagePlaySound(BTVSounds.getIdBySound(BTVSounds.sawcleavertransform), p.toLong()), t);
			
			player.getHeldItem(hand).getTagCompound().setBoolean("Extended", !player.getHeldItem(hand).getTagCompound().getBoolean("Extended"));
		}
		
		
		return super.onItemRightClick(worldIn, player, hand);
	}
	
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if(player.world.isRemote) return false;
		if(!ItemHelper.checkBooleanTag(stack, "Extended", false)) {
			PlayerTimerBuilder ptb = new PlayerTimerBuilder(player).setName("CleaverAttack").setTimer(20);
			ServerTickEvents.removePlayerTimer("CleaverAttack", player);
			ServerTickEvents.addPlayerTimer(ptb.toPlayerTimer());
			return false;
		}
		this.extendedAttack(player, entity, false);
		stack.damageItem(2, player);
		return true;
	}	
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
		boolean extended = true;
		if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Extended") || !stack.getTagCompound().getBoolean("Extended")) extended = false;
		if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
        	multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
        	multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)(extended ? 9 : 11), 0));
        	multimap.removeAll(SharedMonsterAttributes.ATTACK_SPEED.getName());
        	// TODO: Understand why the modifier comes out negative on the in-game display, despite ItemSword applying same modifier but coming out positive
        	multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)(extended ? -3.1 : -2.4), 0));
        }
		return multimap;
	        
	}
	
	private void extendedAttack(EntityPlayer player, Entity entity, boolean normalAttack) {
		AxisAlignedBB bb = new AxisAlignedBB(entity.getPosition().add(-5, 0, -5), entity.getPosition().add(5, 2, 5));
		List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, bb);
		
		entities.forEach(e -> {
			if(e instanceof EntityLivingBase && !e.isDead) {
				if(normalAttack && entities.indexOf(e) == entities.size()-1) player.attackTargetEntityWithCurrentItem(e);
				else e.attackEntityFrom(DamageSource.causePlayerDamage(player), (float) (10*Math.pow(player.getCooledAttackStrength(0), 2)));
			}
		});
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("lore." + this.getUnlocalizedName().substring(5)));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	

}
