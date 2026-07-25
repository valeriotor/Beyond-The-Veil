package com.valeriotor.beyondtheveil.rituals;

import com.google.common.collect.Streams;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.capability.PlayerDataProvider;
import com.valeriotor.beyondtheveil.dreaming.Memory;
import com.valeriotor.beyondtheveil.entity.LivingPortalEntity;
import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.rituals.bindings.Binding;
import com.valeriotor.beyondtheveil.rituals.bindings.BindingData;
import com.valeriotor.beyondtheveil.surgery.PatientType;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.util.ItemSet;
import com.valeriotor.beyondtheveil.world.saved.blood_pool.ColorTriplet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

public class RitualRegistry {

    private static final List<RitualTemplate> TEMPLATES = new ArrayList<>();
    private static final Map<String, RitualTemplate> BY_NAME = new HashMap<>();
    public static final Set<Item> MULTIPLE_ALLOWED = Set.of(Items.STONE_BRICKS);

    public static final RitualTemplate BLOOD_BRICKS = new RitualTemplate.RitualTemplateBuilder("blood_bricks", 100, 1, 0, 0, 0) // negligible
            .setMatch(input -> oneOrMore(input, List.of(), ItemSet.of(Items.STONE_BRICKS), 4))
            .setOutputs(((itemStacks, player) -> byNumber(itemStacks, Registration.BLOOD_BRICK_ITEM.get(), 3)))
            .toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate CORAL_STAFF = new RitualTemplate.RitualTemplateBuilder("coral_staff", 0, 20, 10, 0.03, 0) // low
            .setMatch(input -> exact(input, Items.FIRE_CORAL, Items.HEART_OF_THE_SEA, Registration.HEART_ITEM.get()))
            .setOutputs(List.of(new ItemStack(Registration.CORAL_STAFF.get())))
            .toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate BLEEDING_BELT = new RitualTemplate.RitualTemplateBuilder("bleeding_belt", 5, 20, 40, 0.03, 1.1) // medium
            .setMatch(input -> exact(input, Items.CHAIN, Registration.GREAT_HEART.get(), Registration.GREAT_HEART.get(), Items.CACTUS))
            .setOutputs(List.of(new ItemStack(Registration.BLEEDING_BELT.get())))
            .toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate LIVING_IRON = new RitualTemplate.RitualTemplateBuilder("living_iron", 10, 1, 1, 0, 0) // negligible
            .setMatch(input -> oneOrMore(input, List.of(Registration.HEART_ITEM.get()), ItemSet.of(Items.RAW_IRON), 3))
            .setOutputs((itemStacks, player) -> byNumberScaled(itemStacks, Items.RAW_IRON, Registration.LIVING_IRON.get(), 3, 4))
            .toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate VESSEL_STONE = new RitualTemplate.RitualTemplateBuilder("vessel_stone", 5, 20, 10, 0.03, 0) // low
            .setMatch(input -> exact(input, Registration.HEART_ITEM.get(), Items.EMERALD_BLOCK, Registration.EMPTY_BLADDER.get(), Items.CHORUS_FRUIT))
            .setOutputs(List.of(new ItemStack(Registration.VESSEL_STONE.get())))
            .toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate BLOOD_ORB = new RitualTemplate.RitualTemplateBuilder("blood_orb", 5, 20, 10, 0.03, 0) // low
            .setMatch(input -> exact(input, Registration.HEART_ITEM.get(), Items.HEART_OF_THE_SEA, Items.EGG))
            .setOutputs(List.of(new ItemStack(Registration.BLOOD_ORB.get())))
            .toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate BLOOD_GEM = new RitualTemplate.RitualTemplateBuilder("blood_gem", 5, 20, 10, 0.03, 0) // low
            .setMatch(input -> exact(input, Registration.HEART_ITEM.get(), Items.DIAMOND, Items.QUARTZ))
            .setOutputs(List.of(new ItemStack(Registration.BLOOD_GEM.get())))
            .toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate VEIN_MINER = new RitualTemplate.RitualTemplateBuilder("vein_miner", 5, 20, 40, 0.03, 1.1) // medium
            .setMatch(input -> exact(input, Registration.HEART_ITEM.get(), Items.DIAMOND_PICKAXE, Registration.EMPTY_BLADDER.get()))
            .setOutputs(List.of(new ItemStack(Registration.VEIN_MINER.get())))
            .toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate BIND_ITEM_DAMAGE = new RitualTemplate.RitualTemplateBuilder("bind_item_damage", 50, 20, 30, 0.03, 0) // medium
            .setMatch(input -> exactWithWildcard(input, 1, Registration.HEART_ITEM.get(), null, Items.DIAMOND_SWORD, Registration.SCALPEL.get()))
            .setOutputs((stacks, player) -> {
                if (stacks.size() == 4) {
                    ItemStack stack = stacks.get(1);
                    stack.getOrCreateTag().putUUID("bind_item_damage", player);
                    return List.of(stack);
                }
                return List.of();
            }).toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate BIND_ITEM_WEAKNESS = new RitualTemplate.RitualTemplateBuilder("bind_item_weakness", 50, 20, 30, 0.03, 0) // medium
            .setMatch(input -> exactWithWildcard(input, 1, Registration.HEART_ITEM.get(), null, Items.ANVIL, Registration.SPINE.get()))
            .setOutputs((stacks, player) -> {
                if (stacks.size() == 4) {
                    ItemStack stack = stacks.get(1);
                    stack.getOrCreateTag().putUUID("bind_item_weakness", player);
                    return List.of(stack);
                }
                return List.of();
            }).toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate BIND_PILLAR = new RitualTemplate.RitualTemplateBuilder("bind_pillar", 50, 40, 10, 0, 0) // low
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

    public static final RitualTemplate POOL_FLEBO = new RitualTemplate.RitualTemplateBuilder("pool_flebo", 50, 20, 10, 0, 0) // low
            .setMatch(input -> {
                input = skipModifiers(input);
                if (input.size() != 4) {
                    return false;
                }
                List<Item> allowed = Streams.concat(Arrays.stream(DyeColor.values()).map(pColor -> (Item) DyeItem.byColor(pColor)), Stream.of(Items.CLAY_BALL)).toList();
                for (int i = 0; i < 3; i++) {
                    if (!allowed.contains(input.get(i).getItem())) {
                        return false;
                    }
                }
                return input.get(3).getItem() == Registration.FLEBO_ITEM.get();
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

    public static final RitualTemplate SUMMON_WITHER = new RitualTemplate.RitualTemplateBuilder("summon_wither", 300, 20, 25, 0.04, 0) // medium
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

    public static final RitualTemplate SUMMON_LIVING_PORTAL = new RitualTemplate.RitualTemplateBuilder("summon_living_portal", 300, 40, 65, 0.04, 0.4) // high
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

    public static final RitualTemplate BIND_OVERWORLD = new RitualTemplate.RitualTemplateBuilder("bind_overworld", 300, 20, 25, 0.04, 0)
            .setMatch(input -> exactWithMemory(input, Memory.SENTIENCE, Registration.HEART_ITEM.get(), Items.PRISMARINE, Registration.MEMORY_PHIAL.get(), Items.TOTEM_OF_UNDYING))
            .setOtherEffects(bindingRitualEffect(Binding.OVERWORLD))
            .toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate BIND_NETHER = new RitualTemplate.RitualTemplateBuilder("bind_nether", 300, 20, 25, 0.04, 0)
            .setMatch(input -> exactWithMemory(input, Memory.POWER, Registration.HEART_ITEM.get(), Items.BLAZE_ROD, Registration.MEMORY_PHIAL.get(), Items.TOTEM_OF_UNDYING))
            .setOtherEffects(bindingRitualEffect(Binding.NETHER))
            .toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate BIND_END = new RitualTemplate.RitualTemplateBuilder("bind_end", 300, 20, 25, 0.04, 0)
            .setMatch(input -> exactWithMemory(input, Memory.VOID, Registration.HEART_ITEM.get(), Items.CHORUS_FRUIT, Registration.MEMORY_PHIAL.get(), Items.TOTEM_OF_UNDYING))
            .setOtherEffects(bindingRitualEffect(Binding.END))
            .toTemplate(TEMPLATES, BY_NAME);

    public static final RitualTemplate BIND_ARCHE = new RitualTemplate.RitualTemplateBuilder("bind_arche", 300, 20, 25, 0.04, 0)
            .setMatch(input -> exactWithMemory(input, Memory.SENTIENCE, Registration.HEART_ITEM.get(), Items.PRISMARINE, Registration.MEMORY_PHIAL.get(), Items.TOTEM_OF_UNDYING))
            .setOtherEffects(bindingRitualEffect(Binding.ARCHE))
            .weeper()
            .toTemplate(TEMPLATES, BY_NAME);


    private static RitualTemplate.AdditionalRitualEffect bindingRitualEffect(Binding binding) {
        return ((player, level, altarPos) -> {
            if (level != null) {
                Player p = level.getPlayerByUUID(player);
                if (p != null) {
                    p.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(data -> data.setBindingData(new BindingData(binding)));
                }
            }
        });
    }

    private static boolean oneOrMore(List<ItemStack> input, List<Item> prefix, ItemSet repeatable, int maxLength) { // TODO add startIndex to match when some are already burned... or maybe just feed again the burned items...?
        input = skipModifiers(input);
        if (input.isEmpty() || input.size() > maxLength) {
            return false;
        }
        int reached = 0;
        for (Item item : prefix) {
            if (!Objects.equals(item, input.get(reached).getItem())) {
                return false;
            }
            reached++;
            if (input.size() <= reached) {
                return false;
            }
        }

        for (int i = reached; i < input.size(); i++) {
            if (!repeatable.match(input.get(i).getItem())) {
                return false;
            }
        }
        return true;
    }

    private static boolean exactWithMemory(List<ItemStack> input, Memory memory, Item... ingredients) {
        boolean exact = exact(input, ingredients);
        if (exact) {
            for (ItemStack stack : input) {
                if (stack.getItem() == Registration.MEMORY_PHIAL.get()) {
                    CompoundTag tag = stack.getOrCreateTag();
                    Memory m = Memory.getMemoryFromDataName(tag.getString("memory"));
                    if (m != memory) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    private static boolean exact(List<ItemStack> input, Item... ingredients) {
        input = skipModifiers(input);
        if (input.size() != ingredients.length) {
            return false;
        }
        for (int i = 0; i < input.size(); i++) {
            Item item = input.get(i).getItem();
            Item ingredient = ingredients[i];
            if (item != ingredient) {
                return false;
            }
        }
        return true;
    }

    private static boolean exactWithWildcard(List<ItemStack> input, int wildcardIndex, Item... ingredients) {
        input = skipModifiers(input);
        if (input.size() != ingredients.length) {
            return false;
        }
        for (int i = 0; i < input.size(); i++) {
            if (i == wildcardIndex && input.get(i).getItem() != Items.AIR) {
                continue;
            }
            Item item = input.get(i).getItem();
            Item ingredient = ingredients[i];
            if (item != ingredient) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    private static List<ItemStack> skipModifiers(List<ItemStack> input) {
        for (int i = 0; i < input.size(); i++) {
            if (!RitualModifierRegistry.isModifier(input.get(i).getItem())) {
                input = input.subList(i, input.size());
                break;
            } else if (i == input.size() - 1) {
                return List.of();
            }
        }
        return input;
    }

    private static List<ItemStack> byNumber(List<ItemStack> input, Item outputItem, int max) {
        return byNumber(input, null, outputItem, max);
    }
    private static List<ItemStack> byNumber(List<ItemStack> input, Item matchItem, Item outputItem, int max) {
        List<ItemStack> output = new ArrayList<>();
        for (int i = 0; i < input.size() && i < max; i++) {
            ItemStack s = input.get(i);
            if (matchItem == null || s.getItem() == matchItem) {
                output.add(new ItemStack(outputItem, s.getCount()));
            } else {
                max++;
            }
        }
        return output;
    }

    private static List<ItemStack> byNumberScaled(List<ItemStack> input, Item matchItem, Item outputItem, int max, int divisor) {
        List<ItemStack> output = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < input.size() && i < max; i++) {
            ItemStack s = input.get(i);
            if (matchItem == null || s.getItem() == matchItem) {
                count += s.getCount();
            } else {
                max++;
            }
        }
        count /= divisor;
        while (count > 0) {
            int takeAway = Math.min(64, count);
            count -= takeAway;
            output.add(new ItemStack(outputItem, takeAway));
        }
        return output;
    }

    public static RitualTemplate findMatch(List<ItemStack> nonModifierItems, PatientType patientType) {
        for (RitualTemplate template : TEMPLATES) {
            if (template.matches(nonModifierItems, patientType)) {
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
