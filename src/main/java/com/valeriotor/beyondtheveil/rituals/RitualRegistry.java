package com.valeriotor.beyondtheveil.rituals;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.entity.LivingPortalEntity;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.tile.FleboBE;
import com.valeriotor.beyondtheveil.util.ItemSet;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.ColorTriplet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

public class RitualRegistry {

    private static final List<RitualTemplate> TEMPLATES = new ArrayList<>();
    private static final Map<String, RitualTemplate> BY_NAME = new HashMap<>();
    public static final Set<Item> MULTIPLE_ALLOWED = Set.of(Items.STONE_BRICKS);

    public static final RitualTemplate BLOOD_BRICKS = new RitualTemplate.RitualTemplateBuilder("blood_bricks", 0, 0, 0)
            .setMatch(input -> oneOrMore(input, List.of(), ItemSet.of(Items.STONE_BRICKS), 4))
            .setOutputs(((itemStacks, player) -> byNumber(itemStacks, Registration.BLOOD_BRICK_ITEM.get(), 3)))
            .toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate CORAL_STAFF = new RitualTemplate.RitualTemplateBuilder("coral_staff", 0, 7, 5)
            .setMatch(input -> exact(input, Items.FIRE_CORAL, Items.HEART_OF_THE_SEA, Registration.HEART_ITEM.get()))
            .setOutputs(List.of(new ItemStack(Registration.CORAL_STAFF.get())))
            .toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate BLEEDING_BELT = new RitualTemplate.RitualTemplateBuilder("bleeding_belt", 10, 10, 9)
            .setMatch(input -> exact(input, Items.CHAIN, Registration.GREAT_HEART.get(), Registration.GREAT_HEART.get(), Items.CACTUS))
            .setOutputs(List.of(new ItemStack(Registration.BLEEDING_BELT.get())))
            .toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate BIND_ITEM_DAMAGE = new RitualTemplate.RitualTemplateBuilder("bind_item_damage", 100, 12, 9)
            .setMatch(input -> exactWithWildcard(input, 1, Registration.HEART_ITEM.get(), null, Items.DIAMOND_SWORD, Registration.SCALPEL.get()))
            .setOutputs((stacks, player) -> {
                if (stacks.size() == 4) {
                    ItemStack stack = stacks.get(1);
                    stack.getOrCreateTag().putUUID("bind_item_damage", player);
                    return List.of(stack);
                }
                return List.of();
            }).toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate BIND_ITEM_WEAKNESS = new RitualTemplate.RitualTemplateBuilder("bind_item_weakness", 100, 12, 9)
            .setMatch(input -> exactWithWildcard(input, 1, Registration.HEART_ITEM.get(), null, Items.ANVIL, Registration.SPINE.get()))
            .setOutputs((stacks, player) -> {
                if (stacks.size() == 4) {
                    ItemStack stack = stacks.get(1);
                    stack.getOrCreateTag().putUUID("bind_item_weakness", player);
                    return List.of(stack);
                }
                return List.of();
            }).toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate BIND_PILLAR = new RitualTemplate.RitualTemplateBuilder("bind_pillar", 0, 1, 1)
            .setMatch(input -> exact(input, Registration.HEART_ITEM.get(), Registration.OFFER_PILLAR_ITEM.get(), Registration.BLOOD_SHARD.get()))
            .setOutputs((stacks, player) -> {
                ItemStack stack = new ItemStack(Registration.OFFER_PILLAR.get());
                if (stacks.size() == 3) {
                    stack.setTag(stacks.get(1).getOrCreateTag());
                    ItemStack knife = stacks.get(2);
                    if (knife.getItem() == Registration.BLOOD_SHARD.get()) {
                        CompoundTag tag = knife.getTag();
                        if (tag != null) {
                            String type = tag.getString("type");
                            if (type.length() > 0) {
                                EntityType<?> value = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(type));
                                if (value != null) {
                                    stack.getOrCreateTag().putString("boundEntity", type);
                                }
                            }
                        }
                    }
                }
                return List.of(stack);
            }).toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate POOL_FLEBO = new RitualTemplate.RitualTemplateBuilder("pool_flebo", 0, 3, 0)
            .setMatch(input -> {
                input = skipModifiers(input);
                if (input.size() != 4) {
                    return false;
                }
                List<Item> allowed = Streams.concat(Arrays.stream(DyeColor.values()).map(pColor -> (Item) DyeItem.byColor(pColor)), Stream.of(Items.CLAY_BALL)).toList();
                for (int i = 0; i < 3; i++) {
                    if (!allowed.contains(input.get(i))) {
                        return false;
                    }
                }
                return input.get(3) == Registration.FLEBO_ITEM.get();
            })
            .setOutputs((stacks, player) -> {
                if (stacks.size() == 4) {
                    ItemStack flebo = new ItemStack(Registration.FLEBO_ITEM.get());
                    ArrayList<DyeColor> dyes = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        Item burnedItem = stacks.get(i).getItem();
                        if (burnedItem instanceof DyeItem dye) {
                            dyes.add(dye.getDyeColor());
                        } else if (burnedItem == Items.CLAY_BALL) {
                            dyes.add(null);
                        } else {
                            return List.of();
                        }
                    }
                    ColorTriplet colorTriplet = new ColorTriplet(dyes.get(0), dyes.get(1), dyes.get(2));
                    CompoundTag tag = new CompoundTag();
                    tag.putBoolean("pool", true);
                    tag.putUUID("owner", player);
                    BlockItem.setBlockEntityData(flebo, BTVBlockEntities.FLEBO_BE.get(), colorTriplet.saveToTag(tag));
                    return List.of(flebo);
                }
                return List.of();
            })
            .toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate SUMMON_WITHER = new RitualTemplate.RitualTemplateBuilder("summon_wither", 100, 11, 5)
            .setMatch(input -> exact(input, Registration.HEART_ITEM.get(), Items.SOUL_SAND, Items.WITHER_SKELETON_SKULL))
            .setOtherEffects((player, level, vec3) -> {
                if (level != null) {
                    WitherBoss witherboss = EntityType.WITHER.create(level);
                    if (witherboss != null) {
                        witherboss.moveTo(vec3.add(0, 3, 0));
                        witherboss.makeInvulnerable();
                        level.addFreshEntity(witherboss);
                    }
                }
            }).toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate SUMMON_LIVING_PORTAL = new RitualTemplate.RitualTemplateBuilder("summon_living_portal", 100, 11, 5)
            .setMatch(input -> exact(input, Registration.HEART_ITEM.get(), Items.NETHER_STAR, Items.BLAZE_ROD, Items.GHAST_TEAR))
            .setOtherEffects((player, level, vec3) -> {
                if (level != null) {
                    LivingPortalEntity livingPortal = BTVEntities.LIVING_PORTAL.get().create(level);
                    if (livingPortal != null) {
                        livingPortal.moveTo(vec3.add(0, 2, 0));
                        level.addFreshEntity(livingPortal);
                        level.playSound(null, new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z), SoundEvents.WITHER_SPAWN, SoundSource.NEUTRAL);
                    }
                }
            }).toTemplate(TEMPLATES, BY_NAME);


    private static boolean oneOrMore(List<Item> input, List<Item> prefix, ItemSet repeatable, int maxLength) { // TODO add startIndex to match when some are already burned... or maybe just feed again the burned items...?
        input = skipModifiers(input);
        if (input.isEmpty() || input.size() > maxLength) {
            return false;
        }
        int reached = 0;
        for (Item item : prefix) {
            if (!Objects.equals(item, input.get(reached))) {
                return false;
            }
            reached++;
            if (input.size() <= reached) {
                return false;
            }
        }

        for (int i = reached; i < input.size(); i++) {
            if (!repeatable.match(input.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean exact(List<Item> input, Item... ingredients) {
        input = skipModifiers(input);
        if (input.size() != ingredients.length) {
            return false;
        }
        for (int i = 0; i < input.size(); i++) {
            Item item = input.get(i);
            Item ingredient = ingredients[i];
            if (item != ingredient) {
                return false;
            }
        }
        return true;
    }

    private static boolean exactWithWildcard(List<Item> input, int wildcardIndex, Item... ingredients) {
        input = skipModifiers(input);
        if (input.size() != ingredients.length) {
            return false;
        }
        for (int i = 0; i < input.size(); i++) {
            if (i == wildcardIndex && input.get(i) != Items.AIR) {
                continue;
            }
            Item item = input.get(i);
            Item ingredient = ingredients[i];
            if (item != ingredient) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    private static List<Item> skipModifiers(List<Item> input) {
        for (int i = 0; i < input.size(); i++) {
            if (!RitualModifierRegistry.isModifier(input.get(i))) {
                input = input.subList(i, input.size());
                break;
            } else if (i == input.size() - 1) {
                return List.of();
            }
        }
        return input;
    }

    private static List<ItemStack> byNumber(List<ItemStack> input, Item outputItem, int max) {
        List<ItemStack> output = new ArrayList<>();
        for (int i = 0; i < input.size() && i < max; i++) {
            ItemStack s = input.get(i);
            output.add(new ItemStack(outputItem, s.getCount()));
        }
        return output;
    }

    public static RitualTemplate findMatch(List<Item> nonModifierItems) {
        for (RitualTemplate template : TEMPLATES) {
            if (template.matches(nonModifierItems)) {
                return template;
            }
        }
        return null;
    }

    /*public static RitualTemplate longestMatch(List<Item> items) {
        if (items.isEmpty()) {
            return null;
        }
        items = Lists.reverse(items);
        Map<List<Item>, RitualTemplate> templatesByInverseIngredients = DICTIONARY.get(items.get(0));
        if (templatesByInverseIngredients == null) {
            return null;
        }
        int maxLength = 0;
        RitualTemplate bestMatch = null;
        for (Map.Entry<List<Item>, RitualTemplate> entry : templatesByInverseIngredients.entrySet()) {
            if (entry.getKey().size() > maxLength && matches(entry.getKey(), items)) {
                maxLength = entry.getKey().size();
                bestMatch = entry.getValue();
            }
        }
        return bestMatch;
    }*/

    private static boolean matches(List<Item> template, List<Item> provided) {
        if (provided.size() < template.size()) {
            return false;
        }
        for (int i = 0; i < template.size(); i++) {
            if (template.get(i) != provided.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static RitualTemplate byName(String name) {
        return BY_NAME.get(name);
    }

}
