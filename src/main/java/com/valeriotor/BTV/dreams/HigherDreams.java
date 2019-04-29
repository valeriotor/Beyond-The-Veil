package com.valeriotor.BTV.dreams;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.valeriotor.BTV.capabilities.IPlayerData;
import com.valeriotor.BTV.capabilities.PlayerDataProvider;
import com.valeriotor.BTV.lib.PlayerDataLib;

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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class HigherDreams {
	
	// ***************************************** MORTUUS ***************************************** \\
	
	public static boolean playerDeath(EntityPlayer p, World w) {
		if(!DreamHandler.youDontHaveLevel(p, 1)) return false;
		List<EntityPlayerMP> list = w.getPlayers(EntityPlayerMP.class, player -> !player.equals(p)); // TODO: Change this and the Humanus and Instrumentum ones to get players from server rather than world
		int lvl = DreamHandler.getDreamingGodLevel(p);
		if(list.isEmpty()) p.sendMessage(new TextComponentTranslation("dreams.playersearch.fail"));
		for(int i = 0; i < lvl && !list.isEmpty(); i++) {
			int index = w.rand.nextInt(list.size());
			EntityPlayer target = list.get(index);
			if(DreamHandler.getDreamAttack(p, target) >= 0) {
				IPlayerData cap = target.getCapability(PlayerDataProvider.PLAYERDATA, null);
				Integer x = cap.getInteger(PlayerDataLib.DEATH_X);
				Integer y = cap.getInteger(PlayerDataLib.DEATH_Y);
				Integer z = cap.getInteger(PlayerDataLib.DEATH_Z);
				if(x != null && y != null && z != null) {
					p.sendMessage(new TextComponentTranslation("dreams.deathsearch.found", new Object[] {target.getName(), x, y, z}));
				} else {
					p.sendMessage(new TextComponentTranslation("dreams.deathsearch.notrecently", new Object[] {target.getName()}));
				}
			} else {
				p.sendMessage(new TextComponentTranslation("dreams.deathsearch.toostrong", new Object[] {target.getName()}));
			}
			list.remove(index);
		}
		return true;
	}
	
	
	// ***************************************** BESTIA ***************************************** \\
	
		public static boolean findAnimal(EntityPlayer p, World w){
			if(!DreamHandler.youDontHaveLevel(p, 2)) return false;
			AxisAlignedBB bb = new AxisAlignedBB(p.getPosition().add(-5, -5, -5), p.getPosition().add(5, 5, 5));
			
			List<Entity> ents = w.getEntitiesInAABBexcluding(p, bb, e -> e instanceof EntityItem);
			Class<? extends EntityAnimal> animal = null;
			for(Entity e : ents) {
				animal = getAnimalFromItem(((EntityItem)e).getItem().getItem(), w);
				if(animal != null) {
					((EntityItem)e).getItem().shrink(1);
					break;
				}
			}
			if(animal == null) return false;
			if(DreamHandler.getDreamingGodLevel(p) == 2) {
				List<Entity> ans = w.getEntities(animal, e -> e.getDistance(p) < 250);
				if(!ans.isEmpty()) {
					BlockPos pos = getEmptyArea(p, w);
					if(pos == null) return false;								// If there's no free area
					ans.get(0).setPosition(pos.getX(), pos.getY(), pos.getZ());
				} else {
					p.sendMessage(new TextComponentTranslation("dreams.animalsearch.nonefound"));
					return false;
				}
			} else {
				BlockPos pos = getEmptyArea(p, w);
				if(pos == null) return false;	
				EntityAnimal an = null;;
				try {
					an = animal.getConstructor(World.class).newInstance(w);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e1) {
					e1.printStackTrace();
				}
				if(an == null) return false;
				an.setPosition(pos.getX(), pos.getY(), pos.getZ());
				w.spawnEntity(an);
			}
			return true;
		}
		
		private static Class<? extends EntityAnimal> getAnimalFromItem(Item item, World w) {
			if(item == Items.BONE) return EntityWolf.class;
			else if(item == Items.PORKCHOP) return EntityPig.class;
			else if(item == Items.RABBIT) return EntityRabbit.class;
			else if(item == Items.BEEF) return EntityCow.class;
			else if(item == Items.MUTTON || item == Item.getItemFromBlock(Blocks.WOOL)) return EntitySheep.class;
			else if(item == Items.CHICKEN || item == Items.EGG) return EntityChicken.class;
			else if(item == Items.FISH) return EntityOcelot.class;
			else if(item == Item.getItemFromBlock(Blocks.RED_MUSHROOM) || item == Item.getItemFromBlock(Blocks.BROWN_MUSHROOM)) return EntityMooshroom.class;
			else if(item == Items.FEATHER) return w.rand.nextBoolean() ? EntityChicken.class : EntityParrot.class;
			else if(item == Items.LEATHER) {
				int a = w.rand.nextInt(3);
				switch(a) {
				case 0: return EntityCow.class;
				case 1: return EntityHorse.class;
				case 2: return EntityLlama.class;
				}
			}
			return null;
		}
		// Behold the horror of five nested 'for's
		private static BlockPos getEmptyArea(EntityPlayer p, World w) {
			for(int x = -9; x <= 9; x+=3) {
				for(int z = -9; z <= 9; z+=3) {
					boolean flag = true;
					for(int x2 = -1; x2 <=1 && flag; x2++) {
						for(int y = 0; y <= 2 && flag; y++) {
							for(int z2 = -1; z2 <= 1 && flag; z2++)
								if(w.getBlockState(p.getPosition().add(x, 0, z).add(x2, y, z2)).getBlock() != Blocks.AIR) flag = false;
								else return p.getPosition().add(x, 0, z).add(x2, y, z2);
						}
					}
				}
			}
			p.sendMessage(new TextComponentTranslation("dreams.animalsearch.toomanyblocks"));
			return null;
		}
	
}
