package com.valeriotor.beyondtheveil.dreaming.dreams;

import com.valeriotor.beyondtheveil.dreaming.DreamHandler;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.entity.PlayerMinion;
import com.valeriotor.beyondtheveil.util.DataUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class DreamAnimal extends Dream {

    public DreamAnimal() {
        super(Memory.ANIMAL, 1, () -> new ReminiscenceAnimal(""));
    }

    @Override
    public boolean activate(Player p, Level l) {
        return activatePos(p, l, p.blockPosition());
    }

    @Override
    public boolean activatePlayer(Player caster, Player target, Level l) {
        return activatePos(caster, l, target.blockPosition());
    }

    @Override
    public boolean activatePos(Player p, Level l, BlockPos pos) {
        boolean consumeVoid = DreamHandler.consumeVoid(p);
        EntityType<?> type = getType(p.getItemInHand(InteractionHand.MAIN_HAND), p.getRandom(), consumeVoid);
        if (type == null) {
            type = getType(p.getItemInHand(InteractionHand.OFF_HAND), p.getRandom(), consumeVoid);
        }
        if (type != null) {
            double angle = p.getRandom().nextDouble() * 2 * Math.PI;
            int dist = 2;
            double distX = Math.cos(angle) * dist;
            double distZ = Math.sin(angle) * dist;
            Entity e = type.create(l);
            if (e != null) {
                e.setPos(p.getX() + distX, p.getY(), p.getZ() + distZ);
                l.addFreshEntity(e);
                ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(type);
                DataUtil.addReminiscence(p, memory.getDataName(consumeVoid), new ReminiscenceAnimal(key == null ? "" : key.getPath()));
                return true;
            }
        }
        return false;
    }

    private EntityType<?> getType(ItemStack stack, RandomSource random, boolean hasVoid) {
        Item held = stack.getItem();
        if (held == Items.AXOLOTL_BUCKET) {
            return EntityType.AXOLOTL;
        } else if (held == Items.TROPICAL_FISH_BUCKET || held == Items.TROPICAL_FISH) {
            return random.nextInt(3) < 1 ? EntityType.AXOLOTL : EntityType.TROPICAL_FISH;
        } else if (held == Items.CACTUS) {
            return EntityType.CAMEL;
        } else if (held == Items.COD) {
            return random.nextInt(9) < 7 ? EntityType.OCELOT : (random.nextBoolean() ? EntityType.DOLPHIN : EntityType.COD);
        } else if (held == Items.SALMON) {
            return random.nextInt(9) < 7 ? EntityType.OCELOT : (random.nextBoolean() ? EntityType.DOLPHIN : EntityType.SALMON);
        } else if (held == Items.CHICKEN || held == Items.EGG) {
            return EntityType.CHICKEN;
        } else if (held == Items.FEATHER) {
            return random.nextBoolean() ? EntityType.CHICKEN : EntityType.PARROT;
        } else if (held == Items.BEETROOT_SEEDS || held == Items.WHEAT_SEEDS || held == Items.MELON_SEEDS || held == Items.PUMPKIN_SEEDS) {
            return random.nextInt(10) < 9 ? EntityType.CHICKEN : EntityType.PARROT;
        } else if (held == Items.TORCHFLOWER_SEEDS || held == Items.PITCHER_POD) {
            return random.nextInt(10) < 9 ? EntityType.SNIFFER : EntityType.CHICKEN;
        } else if (held == Items.BEEF) {
            int a = random.nextInt(20);
            if (a < 16) return EntityType.COW;
            if (a < 18) return EntityType.WOLF;
            return EntityType.MOOSHROOM;
        } else if (held == Items.PORKCHOP) {
            int a = random.nextInt(19);
            if (a < 16) return EntityType.PIG;
            return EntityType.WOLF;
        } else if (held == Items.LEATHER) {
            int a = random.nextInt(20);
            if (a < 6) return EntityType.COW;
            if (a < 7) return EntityType.MOOSHROOM;
            if (a < 11) return EntityType.HORSE;
            if (a < 14) return EntityType.LLAMA;
            if (a < 16) return EntityType.MULE;
            if (a < 17) return EntityType.DONKEY;
            if (a < 18) return EntityType.TRADER_LLAMA;
            return !hasVoid ? EntityType.COW : EntityType.HOGLIN;
        } else if (held == Items.WHEAT) {
            int a = random.nextInt(16);
            if (a < 6) return EntityType.COW;
            if (a < 10) return EntityType.SHEEP;
            if (a < 14) return EntityType.GOAT;
            return EntityType.MOOSHROOM;
        } else if (held == Items.SUGAR || held == Items.HAY_BLOCK || held == Items.GOLDEN_APPLE || held == Items.ENCHANTED_GOLDEN_APPLE) {
            int a = random.nextInt(16);
            if (a < 10) return EntityType.HORSE;
            if (a < 13) return EntityType.DONKEY;
            return EntityType.MULE;
        } else if (held == Items.SLIME_BALL) {
            return EntityType.FROG;
        } else if (held == Items.GLOW_INK_SAC) {
            return EntityType.GLOW_SQUID;
        } else if (held == Items.RED_MUSHROOM || held == Items.BROWN_MUSHROOM || held == Items.MUSHROOM_STEW) {
            return EntityType.MOOSHROOM;
        } else if (held == Items.CARROT_ON_A_STICK || held == Items.BEETROOT) {
            return EntityType.PIG;
        } else if (held == Items.CARROT) {
            return random.nextInt(5) < 3 ? EntityType.RABBIT : EntityType.PIG;
        } else if (held == Items.GOLDEN_CARROT) {
            return random.nextInt(6) < 3 ? EntityType.RABBIT : EntityType.HORSE;
        } else if (held == Items.DANDELION || held == Items.RABBIT || held == Items.RABBIT_FOOT) {
            return random.nextInt(6) < 3 ? EntityType.RABBIT : EntityType.HORSE;
        } else if (held instanceof BlockItem i && i.getBlock().defaultBlockState().is(BlockTags.WOOL)) {
            return EntityType.SHEEP;
        } else if (held == Items.BONE) {
            return random.nextInt(10) < 9 ? EntityType.WOLF : EntityType.SKELETON_HORSE;
        } else if (held == Items.INK_SAC) {
            return EntityType.SQUID;
        } else if ((held == Items.WARPED_FUNGUS || held == Items.WARPED_FUNGUS_ON_A_STICK) && hasVoid) {
            return EntityType.STRIDER;
        } else if (held == Items.TADPOLE_BUCKET) {
            return EntityType.TADPOLE;
        } else if (held == Items.SCUTE || held == Items.SEAGRASS) {
            return EntityType.TURTLE;
        } else if (held == Items.HONEYCOMB || held == Items.HONEYCOMB_BLOCK || held == Items.HONEY_BLOCK || held == Items.HONEY_BOTTLE || Ingredient.of(ItemTags.FLOWERS).test(stack)) {
            return EntityType.BEE;
        } else if (held == Items.SPIDER_EYE || held == Items.FERMENTED_SPIDER_EYE || held == Items.STRING) {
            return random.nextInt(3) < 2 ? EntityType.SPIDER : EntityType.CAVE_SPIDER;
        } else if (held == Items.GLOW_BERRIES || held == Items.SWEET_BERRIES) {
            return EntityType.FOX;
        } else if (held == Items.MILK_BUCKET) {
            return random.nextBoolean() ? EntityType.COW : EntityType.GOAT;
        } else if (held == Items.GOAT_HORN) {
            return EntityType.GOAT;
        } else if (Ingredient.of(ItemTags.WOOL_CARPETS).test(stack)) {
            return EntityType.LLAMA;
        } else if (held == Items.BAMBOO || Ingredient.of(ItemTags.BAMBOO_BLOCKS).test(stack) || held == Items.CAKE) {
            return EntityType.PANDA;
        } else if (held instanceof BlockItem i && i.getBlock().defaultBlockState().is(BlockTags.SNOW)) {
            return EntityType.POLAR_BEAR;
        } else if (held == Items.PUFFERFISH || held == Items.PUFFERFISH_BUCKET) {
            return EntityType.PUFFERFISH;
        }
        return null;
    }


}
