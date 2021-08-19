package com.valeriotor.beyondtheveil.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemInkMask extends ItemArmor implements IDeepOneItem{

    public ItemInkMask(String name, ArmorMaterial material) {
        super(material, 0, EntityEquipmentSlot.HEAD);
        this.setRegistryName(References.MODID, name);
        this.setUnlocalizedName("beyondtheveil:" + name);
        this.setCreativeTab(References.BTV_TAB);
    }

    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        return HashMultimap.create();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        String info = I18n.format(String.format("lore.%s",  this.getUnlocalizedName().substring(19)));
        if(!info.startsWith("lore")) tooltip.add(info);
    }
}
