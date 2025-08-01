package com.valeriotor.beyondtheveil.lib;

import com.valeriotor.beyondtheveil.entity.*;
import com.valeriotor.beyondtheveil.entity.ictya.AnglerEntity;
import com.valeriotor.beyondtheveil.entity.ictya.CephalopodianEntity;
import com.valeriotor.beyondtheveil.entity.ictya.SeaSnakeEntity;
import com.valeriotor.beyondtheveil.networking.GenericToClientPacket;
import com.valeriotor.beyondtheveil.networking.Messages;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class BTVEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, References.MODID);
    public static final RegistryObject<EntityType<ShoremanEntity>> SHOREMAN = ENTITIES.register("shoreman", () -> EntityType.Builder.of(ShoremanEntity::new, MobCategory.CREATURE).sized(0.7F, 1.9F).clientTrackingRange(32).build("shoreman"));
    public static final RegistryObject<EntityType<BloodCultistEntity>> BLOOD_CULTIST = ENTITIES.register("blood_cultist", () -> EntityType.Builder.of(BloodCultistEntity::new, MobCategory.CREATURE).sized(0.7F, 2F).clientTrackingRange(32).build("blood_cultist"));
    public static final RegistryObject<EntityType<Abomination1Entity>> ABOMINATION_1 = ENTITIES.register("abomination_1", () -> EntityType.Builder.of(Abomination1Entity::new, MobCategory.CREATURE).sized(0.7F, 1.8F).clientTrackingRange(32).build("abomination_1"));
    public static final RegistryObject<EntityType<Abomination0Entity>> ABOMINATION_0 = ENTITIES.register("abomination_0", () -> EntityType.Builder.of(Abomination0Entity::new, MobCategory.CREATURE).sized(0.7F, 1.8F).clientTrackingRange(32).build("abomination_0"));
    public static final RegistryObject<EntityType<FletumEntity>> FLETUM = ENTITIES.register("fletum", () -> EntityType.Builder.of(FletumEntity::new, MobCategory.CREATURE).sized(0.7F, 1.8F).clientTrackingRange(32).build("fletum"));
    public static final RegistryObject<EntityType<WeeperEntity>> WEEPER = ENTITIES.register("weeper", () -> EntityType.Builder.of(WeeperEntity::new, MobCategory.CREATURE).sized(0.7F, 1.8F).clientTrackingRange(32).build("weeper"));
    public static final RegistryObject<EntityType<CrawlerEntity>> CRAWLER = ENTITIES.register("crawler", () -> EntityType.Builder.of(CrawlerEntity::new, MobCategory.CREATURE).sized(1.2F, 0.7F).clientTrackingRange(32).build("crawler"));
    public static final RegistryObject<EntityType<NautilusEntity>> NAUTILUS = ENTITIES.register("nautilus", () -> EntityType.Builder.of(NautilusEntity::new, MobCategory.MISC).sized(5.6F, 5F).clientTrackingRange(32).build("nautilus"));
    public static final RegistryObject<EntityType<BloodWraithEntity>> BLOOD_WRAITH = ENTITIES.register("blood_wraith", () -> EntityType.Builder.of(BloodWraithEntity::new, MobCategory.MONSTER).sized(0.7F, 5F).clientTrackingRange(32).build("blood_wraith"));
    public static final RegistryObject<EntityType<BloodZombieEntity>> BLOOD_ZOMBIE = ENTITIES.register("blood_zombie", () -> EntityType.Builder.of(BloodZombieEntity::new, MobCategory.MONSTER).sized(0.7F, 5F).clientTrackingRange(32).build("blood_zombie"));
    public static final RegistryObject<EntityType<BloodSkeletonEntity>> BLOOD_SKELETON = ENTITIES.register("blood_skeleton", () -> EntityType.Builder.of(BloodSkeletonEntity::new, MobCategory.MONSTER).sized(0.7F, 5F).clientTrackingRange(32).build("blood_skeleton"));
    public static final RegistryObject<EntityType<DeepOneEntity>> DEEP_ONE = ENTITIES.register("deep_one", () -> EntityType.Builder.of(DeepOneEntity::new, MobCategory.MONSTER).sized(0.6F, 3F).clientTrackingRange(32).build("deep_one"));
    public static final RegistryObject<EntityType<CephalopodianEntity>> CEPHALOPODIAN = ENTITIES.register("cephalopodian", () -> EntityType.Builder.of(CephalopodianEntity::new, MobCategory.MONSTER).sized(5.6F, 3F).clientTrackingRange(32).build("cephalopodian"));
    public static final RegistryObject<EntityType<AnglerEntity>> ANGLER = ENTITIES.register("angler", () -> EntityType.Builder.of(AnglerEntity::new, MobCategory.MONSTER).sized(1.6F, 0.5F).clientTrackingRange(32).build("angler"));
    public static final RegistryObject<EntityType<SeaSnakeEntity>> SEA_SNAKE = ENTITIES.register("sea_snake", () -> EntityType.Builder.of(SeaSnakeEntity::new, MobCategory.MONSTER).sized(1.6F, 0.5F).clientTrackingRange(32).build("sea_snake"));
    public static final RegistryObject<EntityType<CanoeEntity>> CANOE = ENTITIES.register("canoe", () -> EntityType.Builder.of((EntityType<CanoeEntity> pEntityType, Level pLevel) -> new CanoeEntity(pLevel), MobCategory.MISC).sized(1.5F, 0.5F).clientTrackingRange(32).build("canoe"));

    public static void init(IEventBus bus) {
        ENTITIES.register(bus);
    }

    public static boolean isFearlessEntity(LivingEntity entity) {
        return entity instanceof DeepOneEntity; // TODO bosses too?
    }

    public static boolean isScaryEntity(Entity entity) {
        // Transformed players
        return entity instanceof DeepOneEntity;
    }

    public static void moveEntity(LivingEntity scared, Entity scary) {
        double xDist = scared.getX() - scary.getX();
        double zDist = scared.getZ() - scary.getZ();
        double dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(zDist, 2));
        if (dist != 0) {
            if (scared instanceof ServerPlayer player) {
                Messages.sendToPlayer(GenericToClientPacket.movePlayer(xDist / dist * 1.2, 0, zDist / dist * 1.2), player);
            } else {
                scared.setDeltaMovement(new Vec3(xDist / dist, 0, zDist / dist));
            }
        }
    }

    public enum BTVFlags {
        CAMOUFLAGED(0);

        private final int flag;

        BTVFlags(int flag) {
            this.flag = flag;
        }

        public int getFlag() {
            return flag;
        }
    }
}
