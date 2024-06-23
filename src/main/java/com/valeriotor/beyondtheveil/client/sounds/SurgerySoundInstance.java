package com.valeriotor.beyondtheveil.client.sounds;

import com.valeriotor.beyondtheveil.item.SurgeryItem;
import com.valeriotor.beyondtheveil.lib.References;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SurgerySoundInstance extends AbstractTickableSoundInstance {

    private static Set<BlockPos> toRemovePositions = new HashSet<>();

    private final BlockPos bePos;

    public SurgerySoundInstance(SurgeryItem.SurgeryItemType type, BlockPos bePos) {
        super(type.getSound().get(), SoundSource.BLOCKS, SoundInstance.createUnseededRandom());
        this.bePos = bePos;
    }

    @Override
    public void tick() {
        if (toRemovePositions.contains(bePos)) {
            toRemovePositions.remove(bePos);
            stop();
        }
    }

    public static void stopSound(CompoundTag tag) {
        toRemovePositions.add(BlockPos.of(tag.getLong("pos")));
    }

    @SubscribeEvent
    public static void clientTickEvent(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            toRemovePositions.clear();
        }
    }
}
