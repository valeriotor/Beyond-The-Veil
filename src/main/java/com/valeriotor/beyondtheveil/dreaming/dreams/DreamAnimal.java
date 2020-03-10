package com.valeriotor.beyondtheveil.dreaming.dreams;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;

import com.valeriotor.beyondtheveil.dreaming.DreamHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class DreamAnimal extends Dream{

	public DreamAnimal(String name, int priority) {
		super(name, priority);
	}
	
	@Override
	public boolean activatePos(EntityPlayer p, World w, BlockPos pos){
		if(!DreamHandler.youDontHaveLevel(p, 2)) return false;
		AxisAlignedBB bb = new AxisAlignedBB(p.getPosition().add(-5, -5, -5), p.getPosition().add(5, 5, 5));
		
		List<Entity> ents = w.getEntitiesInAABBexcluding(p, bb, e -> e instanceof EntityItem);
		Function<World, EntityAnimal> func = null;
		for(Entity e : ents) {
			func = getAnimalFromItem(((EntityItem)e).getItem().getItem(), w);
			if(func != null) {
				((EntityItem)e).getItem().shrink(1);
				break;
			}
		}
		if(func == null) return false;
		pos = getEmptyArea(p, w, pos);
		if(pos == null) return false;	
		EntityAnimal animal = func.apply(w);
		animal.setPosition(pos.getX()+0.5, pos.getY(), pos.getZ()+0.5);
		w.spawnEntity(animal);
		
		return true;
	}
	
	private Function<World, EntityAnimal> getAnimalFromItem(Item item, World w) {
		if(item == Items.BONE) return EntityWolf::new;
		else if(item == Items.PORKCHOP) return EntityPig::new;
		else if(item == Items.RABBIT) return EntityRabbit::new;
		else if(item == Items.BEEF) return EntityCow::new;
		else if(item == Items.MUTTON || item == Item.getItemFromBlock(Blocks.WOOL)) return EntitySheep::new;
		else if(item == Items.CHICKEN || item == Items.EGG) return EntityChicken::new;
		else if(item == Items.FISH) return EntityOcelot::new;
		else if(item == Item.getItemFromBlock(Blocks.RED_MUSHROOM) || item == Item.getItemFromBlock(Blocks.BROWN_MUSHROOM)) return EntityMooshroom::new;
		else if(item == Items.FEATHER) return w.rand.nextBoolean() ? EntityChicken::new : EntityParrot::new;
		else if(item == Items.LEATHER) {
			int a = w.rand.nextInt(3);
			switch(a) {
			case 0: return EntityCow::new;
			case 1: return EntityHorse::new;
			case 2: return EntityLlama::new;
			}
		}
		return null;
	}
	// Behold the horror of five nested 'for's
	private BlockPos getEmptyArea(EntityPlayer p, World w, BlockPos pos) {
		for(int x = -9; x <= 9; x+=3) {
			for(int z = -9; z <= 9; z+=3) {
				boolean flag = true;
				for(int x2 = -1; x2 <=1 && flag; x2++) {
					for(int y = 0; y <= 2 && flag; y++) {
						for(int z2 = -1; z2 <= 1 && flag; z2++)
							if(w.getBlockState(pos.add(x, 0, z).add(x2, y, z2)).getBlock() != Blocks.AIR) flag = false;
							else return pos.add(x, 0, z).add(x2, y, z2);
					}
				}
			}
		}
		p.sendMessage(new TextComponentTranslation("dreams.animalsearch.toomanyblocks"));
		return null;
	}

}
