package com.valeriotor.beyondtheveil.client.event;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.valeriotor.beyondtheveil.Registration;
import com.valeriotor.beyondtheveil.block.DreamFocusBlock;
import com.valeriotor.beyondtheveil.block.FlaskBlock;
import com.valeriotor.beyondtheveil.block.FlaskShelfBlock;
import com.valeriotor.beyondtheveil.block.SurgeryBedBlock;
import com.valeriotor.beyondtheveil.capability.arsenal.TriggerData;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSync;
import com.valeriotor.beyondtheveil.capability.crossync.CrossSyncDataProvider;
import com.valeriotor.beyondtheveil.capability.crossync.PlayerTransformation;
import com.valeriotor.beyondtheveil.client.ClientData;
import com.valeriotor.beyondtheveil.client.ClientSetup;
import com.valeriotor.beyondtheveil.client.gui.SurgeryBedGui;
import com.valeriotor.beyondtheveil.client.model.entity.AnimatedModel;
import com.valeriotor.beyondtheveil.client.model.entity.layer.ChestWoundModel;
import com.valeriotor.beyondtheveil.client.model.entity.layer.WoundModel;
import com.valeriotor.beyondtheveil.client.reminiscence.ReminiscenceClient;
import com.valeriotor.beyondtheveil.client.render.entity.layer.PatientWoundLayer;
import com.valeriotor.beyondtheveil.client.render.entity.player.RenderPlayerUtils;
import com.valeriotor.beyondtheveil.client.render.entity.player.ScaledPlayerRenderer;
import com.valeriotor.beyondtheveil.client.util.CameraRotator;
import com.valeriotor.beyondtheveil.client.util.CrossSyncHolder;
import com.valeriotor.beyondtheveil.entity.CrawlerEntity;
import com.valeriotor.beyondtheveil.entity.NautilusEntity;
import com.valeriotor.beyondtheveil.lib.BTVEffects;
import com.valeriotor.beyondtheveil.lib.BTVEntities;
import com.valeriotor.beyondtheveil.lib.References;
import com.valeriotor.beyondtheveil.rituals.bindings.Binding;
import com.valeriotor.beyondtheveil.rituals.bindings.BindingData;
import com.valeriotor.beyondtheveil.surgery.OperationRegistry;
import com.valeriotor.beyondtheveil.surgery.PatientStatus;
import com.valeriotor.beyondtheveil.surgery.SurgicalLocation;
import com.valeriotor.beyondtheveil.surgery.arsenal.ArsenalEffect;
import com.valeriotor.beyondtheveil.tile.FlaskBE;
import com.valeriotor.beyondtheveil.tile.FlaskShelfBE;
import com.valeriotor.beyondtheveil.tile.SurgeryBedBE;
import com.valeriotor.beyondtheveil.tile.SurgicalBE;
import com.valeriotor.beyondtheveil.util.DataUtil;
import com.valeriotor.beyondtheveil.world.dimension.BTVDimensions;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.valeriotor.beyondtheveil.world.dimension.ArcheCycleData.CURRENT_DURATION;
import static com.valeriotor.beyondtheveil.world.dimension.ArcheCycleData.CURRENT_PEAK;
import static net.minecraft.client.renderer.LevelRenderer.getLightColor;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class RenderEvents {

    private static final ResourceLocation RAIN_LOCATION = new ResourceLocation(References.MODID, "textures/environment/current.png");
    private static final float[] rainSizeY = new float[1024];
    private static final float[] rainSizeZ = new float[1024];
    private static CameraRotator rotator;
    private static int shakeDuration;
    private static int blackScreenDuration = -1;
    private static double partialTick;

    static {
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                float f = (float) (j - 16);
                float f1 = (float) (i - 16);
                float f2 = Mth.sqrt(f * f + f1 * f1);
                rainSizeY[i << 5 | j] = -f1 / f2;
                rainSizeZ[i << 5 | j] = f / f2;
            }
        }
    }

    public static void setBlackScreenDuration(int duration) {
        blackScreenDuration = duration;
    }

    public static void startCameraRotation(CameraRotator newRotator) {
        if (newRotator.getTime() == 0) {
            LocalPlayer p = Minecraft.getInstance().player;
            if (p != null) {
                p.setYHeadRot(newRotator.getYaw());
                p.setYRot(newRotator.getYaw());
                p.setXRot(newRotator.getPitch());
            }
        } else {
            rotator = newRotator;
        }
    }

    public static void shakeCamera(int duration) {
        shakeDuration = duration;
    }

    @SubscribeEvent
    public static void renderTickEvent(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                if (rotator != null) {
                    boolean done = rotator.update();
                    player.setYHeadRot((float) rotator.computeYaw(0));
                    player.setYRot((float) rotator.computeYaw(0));
                    player.setXRot((float) rotator.computePitch(0));
                    if (done) {
                        rotator = null;
                    }
                } else if (shakeDuration > 0) {
                    shakeDuration--;
                    boolean phase = (shakeDuration / 10) % 2 == 0;
                    player.setYHeadRot(player.getYHeadRot() + 4 * (phase ? 1 : -1));
                }

            }
        }
    }

    @SubscribeEvent
    public static void tickEvent(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (blackScreenDuration >= 0) {
                blackScreenDuration--;
            }
        }
    }

    @SubscribeEvent
    public static void renderArmEvent(RenderArmEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            CrossSync crossSync = CrossSyncHolder.getCrossSync(player);
            if (crossSync != null && (crossSync.getTransformation() != null || crossSync.isDreamFocus())) {
                event.setCanceled(true);
                if (crossSync.getTransformation() == PlayerTransformation.SCALED) {
                    if (ClientSetup.moreRenderers.get(PlayerTransformation.SCALED) instanceof ScaledPlayerRenderer spc) {
                        spc.renderHand(event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), player, event.getArm() == HumanoidArm.RIGHT ? spc.getModel().rightArm : spc.getModel().leftArm);
                    }
                }
            }
            if (!ClientData.getInstance().getFirstPersonAnimations().isEmpty()) {
                event.setCanceled(true);
            }
            //event.getPoseStack().translate(0, 0, 0);
            ////event.getPoseStack().mulPose(Axis.ZP.rotation((float) ((player.tickCount + partialTick) / 20D)));
            //PlayerRenderer playerrenderer = (PlayerRenderer)Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player);
            //if (event.getArm() == HumanoidArm.RIGHT) {
            //    playerrenderer.renderHand(event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), player, playerrenderer.getModel().leftArm, playerrenderer.getModel().leftSleeve);
            //} else {
            //    playerrenderer.renderHand(event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), player, playerrenderer.getModel().rightArm, playerrenderer.getModel().rightSleeve);
            //}
        }
    }

    @SubscribeEvent
    public static void renderHandEvent(RenderHandEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            CrossSync crossSync = CrossSyncHolder.getCrossSync(player);
            if (crossSync != null && (crossSync.getTransformation() != null || crossSync.isDreamFocus())) {
                if (crossSync.getTransformation() != PlayerTransformation.SCALED) {
                    event.setCanceled(true);
                }
            }
            if (!ClientData.getInstance().getFirstPersonAnimations().isEmpty()) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void computeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        partialTick = event.getPartialTick();
        LocalPlayer player = Minecraft.getInstance().player;
        Camera camera = event.getCamera();
        if (player != null) {
            if (player.getSleepingPos().isPresent()) {
                BlockState state = player.level().getBlockState(player.getSleepingPos().get());
                if (state.getBlock() instanceof SurgeryBedBlock b) {
                    Direction direction = state.getValue(SurgeryBedBlock.FACING);
                    event.setYaw(direction.toYRot() + 90.0F);
                }
            }
            if (player.getVehicle() instanceof NautilusEntity && camera.isDetached()) {
                camera.move(-camera.getMaxZoom(4), 0, 0);
            }
        }
    }


    //private static volatile double fov = 0;
//
    //@SubscribeEvent
    //public static void fieldOfViewEvent(EntityViewRenderEvent.FieldOfView event) {
    //    fov = event.getFOV();
    //}

    //@SubscribeEvent
    //public static void computeCameraAnglesEvent(ViewportEvent.ComputeCameraAngles event) {
    //    LocalPlayer p = Minecraft.getInstance().player;
    //    if (p != null && p.isPassenger() && p.getVehicle() instanceof NautilusEntity && event.getCamera().isDetached()) {
    //        event.getCamera().move(-event.getCamera().getMaxZoom(4F), 0, 0);
    //    }
    //}

    //private static RenderTransformedPlayer deepOnePlayerRenderer = new RenderTransformedPlayer();

    @SubscribeEvent
    public static void renderLivingEvent(RenderLivingEvent event) {
        LivingEntity entity = event.getEntity();
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null && entity.hasEffect(BTVEffects.CAMOUFLAGE.get())) {
            BlockPos entityPos = entity.blockPosition();
            BlockState blockState = level.getBlockState(entityPos.below());
            if (!blockState.canBeReplaced()) {
                event.setCanceled(true);

                BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(entityPos.getX(), entityPos.getY(), entityPos.getZ());
                int width = Mth.floor(entity.getDimensions(entity.getPose()).width);
                int height = Mth.floor(entity.getDimensions(entity.getPose()).height);
                for (int x = -width / 2; x <= width / 2; x++) {
                    for (int z = -width / 2; z <= width / 2; z++) {
                        for (int y = 0; y <= height; y++) {
                            pos.set(entityPos.getX() + x, entityPos.getY() + y, entityPos.getZ() + z);
                            event.getPoseStack().pushPose();
                            event.getPoseStack().translate(x - 0.5, y, z - 0.5);
                            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(blockState, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), OverlayTexture.NO_OVERLAY);
                            event.getPoseStack().popPose();
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void renderPlayer(RenderPlayerEvent event) {
        Player p = event.getEntity();
        if (event.isCanceled()) {
            return;
        }
        AnimatedModel.playerModel.resetParts();
        AnimatedModel.playerSlimModel.resetParts();
        boolean shouldRenderAsPlayer = true;
        CrossSync crossSync = CrossSyncHolder.getCrossSync(p);
        if (crossSync != null) { // in theory this should never be null
            if (crossSync.isDreamFocus()) {
                p.level().addParticle(ParticleTypes.CRIT, p.getX(), p.getY(), p.getZ(), 0, 0, 0);
                event.setCanceled(true);
                return;
            }
            PlayerTransformation transformation = crossSync.getTransformation();
            if (transformation != null) {
                shouldRenderAsPlayer = false;
                LivingEntityRenderer<LivingEntity, ?> entityRenderer = ClientSetup.moreRenderers.get(transformation);
                float f = Mth.lerp(event.getPartialTick(), p.yRotO, p.getYRot());
                event.setCanceled(true);
                entityRenderer.render(p, f, event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
            }
            if (shouldRenderAsPlayer) {
                Mob heldPatientEntity = crossSync.getHeldPatientEntity(p.level());
                if (heldPatientEntity != null) {
                    PoseStack poseStack = event.getPoseStack();
                    poseStack.pushPose();
                    float scaleFactor = 1;
                    poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
                    poseStack.mulPose(Axis.YP.rotation((float) (Math.PI - Math.toRadians(Mth.rotLerp(event.getPartialTick(), p.yBodyRotO, p.yBodyRot)))));
                    Minecraft.getInstance().getEntityRenderDispatcher().render(heldPatientEntity, -0.4, 1.3, 0.1, 0, event.getPartialTick(), poseStack, event.getMultiBufferSource(), event.getPackedLight());
                    poseStack.popPose();
                }
            }
        }
        if (false) {
            //Entity entity = Minecraft.getInstance().getCameraEntity();
            //double d0 = p.xOld + (p.position().x - p.xOld) * (double)event.getPartialTick();
            //double d1 = p.yOld + (p.position().y - p.yOld) * (double)event.getPartialTick();
            //double d2 = p.zOld + (p.position().z - p.zOld) * (double)event.getPartialTick();
            //double d3 = entity.xOld + (entity.position().x - entity.xOld) * (double)event.getPartialTick();
            //double d4 = entity.yOld + (entity.position().y - entity.yOld) * (double)event.getPartialTick();
            //double d5 = entity.zOld + (entity.position().z - entity.zOld) * (double)event.getPartialTick();
            float f = Mth.lerp(event.getPartialTick(), p.yRotO, p.getYRot());
            event.setCanceled(true);
            EntityRenderer<LivingEntity> deepOneRenderer = (EntityRenderer<LivingEntity>) Minecraft.getInstance().getEntityRenderDispatcher().renderers.get(BTVEntities.DEEP_ONE.get());
            deepOneRenderer.render(p, f, event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
        }
        if (shouldRenderAsPlayer) {
            if (event instanceof RenderPlayerEvent.Pre) {
                if (p.getSleepingPos().isPresent()) {
                    BlockState state = p.level().getBlockState(p.getSleepingPos().get());
                    if (state.getBlock() instanceof SurgeryBedBlock b) {
                        PoseStack pose = event.getPoseStack();
                        pose.pushPose();
                        rotatePlayerPatientXZPlane(pose, state);
                        //pose.mulPose(Axis.YP.rotationDegrees(90.0F));
                        if (p.level().getBlockEntity(b.findCenter(p.getSleepingPos().get(), state)) instanceof SurgeryBedBE be) {
                            PatientStatus status = be.getPatientStatus();
                            if (status != null && status.getExposedLocation() == SurgicalLocation.BACK) {
                                pose.mulPose(Axis.XP.rotationDegrees(180));
                            }
                            if (status != null) {
                                if (status.isIncised()) {
                                    if (status.getExposedLocation() == SurgicalLocation.CHEST) {
                                        ChestWoundModel<CrawlerEntity> model = PatientWoundLayer.chestWoundModel;
                                        pose.pushPose();
                                        pose.mulPose(Axis.ZP.rotationDegrees(180));
                                        pose.scale(-1.0F, -1.0F, 1.0F);
                                        pose.translate(-1.23F, -1.393F, 0.095F);

                                        model.setupAnim(p.tickCount + event.getPartialTick(), status);
                                        model.renderToBuffer(pose, event.getMultiBufferSource().getBuffer(model.renderType(PatientWoundLayer.CHEST_WOUND_TEXTURE)), event.getPackedLight(), OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F, !status.getFlags().containsKey("extract_heart"));
                                        pose.popPose();
                                    } else {
                                        WoundModel<CrawlerEntity> model = PatientWoundLayer.woundModel;
                                        pose.pushPose();
                                        pose.mulPose(Axis.ZP.rotationDegrees(180));
                                        pose.scale(-1.0F, -1.0F, 1.0F);
                                        //pose.translate(0, -0.15, 2);
                                        pose.translate(-1.033F, -1.133F, 0.0F);
                                        pose.mulPose(Axis.YP.rotationDegrees(90));
                                        pose.scale(0.95F, 0.95F, 0.95F);
                                        //pose.translate(0.0F, -1.501F, 0.0F);

                                        model.setupAnim(p.tickCount + event.getPartialTick(), status);
                                        model.renderToBuffer(pose, event.getMultiBufferSource().getBuffer(model.renderType(PatientWoundLayer.WOUND_TEXTURE)), event.getPackedLight(), OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F, !status.getFlags().containsKey(OperationRegistry.SPINELESS));
                                        pose.popPose();
                                    }
                                }
                            }
                        }
                        //Minecraft.getInstance().getEntityRenderDispatcher().setRenderShadow(false);
                        event.getPoseStack().popPose();
                    }
                }
            }
        }
    }

    public static void rotatePlayerPatient(AbstractClientPlayer p, PoseStack pose) {
        if (p.getSleepingPos().isPresent()) {
            BlockState state = p.level().getBlockState(p.getSleepingPos().get());
            if (state.getBlock() instanceof SurgeryBedBlock b) {
                rotatePlayerPatientXZPlane(pose, state);

                if (p.level().getBlockEntity(b.findCenter(p.getSleepingPos().get(), state)) instanceof SurgeryBedBE be) {
                    PatientStatus status = be.getPatientStatus();
                    if (status != null && status.getExposedLocation() == SurgicalLocation.BACK) {
                        pose.mulPose(Axis.XP.rotationDegrees(180));
                    }
                }
            }
        }
    }

    private static void rotatePlayerPatientXZPlane(PoseStack pose, BlockState state) {
        Direction direction = state.getValue(SurgeryBedBlock.FACING);
        float f1 = switch (direction) {
            case SOUTH:
                yield 90.0F;
            case WEST:
                yield 0.0F;
            case NORTH:
                yield 270.0F;
            case EAST:
                yield 180.0F;
            default:
                yield 0.0F;
        };
        pose.mulPose(Axis.YP.rotationDegrees(f1));
        pose.mulPose(Axis.YP.rotationDegrees(90.0F));
        pose.translate(1.75, 0, 0);
    }

    public static void rotatePlayerCrawling(AbstractClientPlayer p, PoseStack pose, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        CrossSync crossSync = CrossSyncHolder.getCrossSync(p);
        if (crossSync != null && crossSync.isCrawling()) {
            pose.translate(0, 0.11, 0);
            pose.mulPose(Axis.YN.rotation((float) Math.toRadians(pRotationYaw)));
            pose.mulPose(Axis.XP.rotation((float) Math.toRadians(90)));
            pose.mulPose(Axis.YP.rotation((float) Math.toRadians(pRotationYaw)));
            pose.translate(0, -1, 0);
        }
    }

    public static void rotatePlayerCrawlingPost(AbstractClientPlayer p, PoseStack pose, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        CrossSync crossSync = CrossSyncHolder.getCrossSync(p);
        if (crossSync != null && crossSync.isCrawling()) {
            float f = p.getSwimAmount(pPartialTicks);
            if (f > 0.0F) {
                if (p.isVisuallySwimming()) {
                    pose.translate(0.0F, 1.0F, -0.3F);
                }
                float f3 = p.isInWater() || p.isInFluidType((fluidType, height) -> p.canSwimInFluidType(fluidType)) ? -90.0F - p.getXRot() : -90.0F;
                float f4 = Mth.lerp(f, 0.0F, f3);
                pose.mulPose(Axis.XP.rotationDegrees(-f4));
            }
        }
    }

    public static void animatePlayerCrawling(Player player, PlayerModel<?> model, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        CrossSync crossSync = CrossSyncHolder.getCrossSync(player);
        if (crossSync != null && crossSync.isCrawling()) {
            limbSwingAmount *= 2;
            limbSwingAmount = Math.min(limbSwingAmount, 1);
            float yRot = -Mth.cos(limbSwing * 0.6662F) * limbSwingAmount * 0.3F;

            // NO CROUCHING
            model.body.xRot = 0.0F;
            model.rightLeg.z = 0.0F;
            model.leftLeg.z = 0.0F;
            model.rightLeg.y = 12.0F;
            model.leftLeg.y = 12.0F;
            model.head.y = 0.0F;
            model.body.y = 0.0F;
            model.leftArm.y = 2.0F;
            model.rightArm.y = 2.0F;

            // CRAWL ANIM
            model.head.xRot = headPitch * ((float) Math.PI / 180F);
            model.head.yRot = yRot;
            model.body.yRot = yRot;
            model.leftLeg.yRot = yRot;
            model.rightLeg.yRot = yRot;
            model.leftLeg.xRot = 0;
            model.rightLeg.xRot = 0;
            model.leftLeg.zRot = -0.1F + Mth.cos(limbSwing * 0.6662F + Mth.PI / 2) * limbSwingAmount * 0.1F;
            model.rightLeg.zRot = 0.1F + Mth.cos(limbSwing * 0.6662F + Mth.PI / 2) * limbSwingAmount * 0.1F;
            model.leftArm.xRot = Mth.PI;
            model.rightArm.xRot = Mth.PI;
            float v = limbSwing * 0.6662F % (2 * Mth.PI);
            if (v < Mth.PI) {
                model.leftArm.xRot += limbSwingAmount * Mth.cos(limbSwing * 0.6662F + Mth.PI / 2);
            } else {
                model.rightArm.xRot -= limbSwingAmount * Mth.cos(limbSwing * 0.6662F + Mth.PI / 2);
            }
            model.leftArm.zRot = Mth.PI / 6 + Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
            model.rightArm.zRot = -Mth.PI / 6 - Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
            model.leftPants.copyFrom(model.leftLeg);
            model.rightPants.copyFrom(model.rightLeg);
            model.leftSleeve.copyFrom(model.leftArm);
            model.rightSleeve.copyFrom(model.rightArm);
            model.head.xRot -= 1.4;
        }
    }

    @SubscribeEvent
    public static void renderCurrent(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.level.dimension() != BTVDimensions.ARCHE_LEVEL) {
            return;
        }
        long ticks = ClientData.getInstance().archeCycleData.ticksInCycle();
        if (ticks < 0) {
            return;
        }

        float pPartialTick = event.getPartialTick();
        Camera camera = event.getCamera();
        double pCamX = camera.getPosition().x();
        double pCamY = camera.getPosition().y();
        double pCamZ = camera.getPosition().z();
        float intensity = ticks <= CURRENT_PEAK - 1 ? (ticks % (CURRENT_PEAK)) / (float) (CURRENT_PEAK) : (CURRENT_DURATION - ticks) / (float) (CURRENT_DURATION - CURRENT_PEAK);
        float speedIncrement = intensity <= 0.5F ? 0.3F : 0.3F + (intensity - 0.5F) * 4;
        if (!(intensity <= 0.0F)) {
            mc.gameRenderer.lightTexture().turnOnLightLayer();
//            pLightTexture.turnOnLightLayer();
            Level level = mc.level;
            int x = Mth.floor(pCamX);
            int y = Mth.floor(pCamY);
            int z = Mth.floor(pCamZ);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            int l = 5;
            if (Minecraft.useFancyGraphics()) {
                l = 10;
            }

            RenderSystem.depthMask(Minecraft.useShaderTransparency());
            int i1 = -1;
            float f1 = (float) ticks + pPartialTick;
            RenderSystem.setShader(GameRenderer::getParticleShader);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (int z1 = z - l; z1 <= z + l; ++z1) {
                for (int y1 = y - l; y1 <= y + l; ++y1) {
                    //if (z1 - z != 1 || y1 - x != 1) {
                    //    continue;
                    //}
                    int l1 = (z1 - z + 16) * 32 + y1 - y + 16;
                    double d0 = (double) rainSizeY[l1] * 0.5D;
                    double d1 = (double) rainSizeZ[l1] * 0.5D;
                    int xStart = x - l * 2;
                    int xEnd = xStart;
                    blockpos$mutableblockpos.set(xStart, y1, z1);
                    boolean waterPhase = level.getBlockState(blockpos$mutableblockpos).is(Blocks.WATER);
                    boolean doubleRender = Math.abs(z1 - z) <= 1 && Math.abs(y1 - y) <= 1;
                    while (xEnd <= x + l * 2) {
                        xEnd++;
                        blockpos$mutableblockpos.set(xEnd, y1, z1);
                        Block b = level.getBlockState(blockpos$mutableblockpos).getBlock();
                        if (!waterPhase && b == Blocks.WATER && (Math.abs(xEnd - x) > 2 || !doubleRender)) {
                            waterPhase = true;
                            xStart = xEnd;
                        } else if (waterPhase && (b != Blocks.WATER || xEnd == x + l * 2 || (doubleRender && xEnd == x - 2))) {
                            waterPhase = false;
                            int i2 = 1;
                            int x1 = x - l * 4 / 2;
                            int x2 = x + l * 4 / 2;
                            blockpos$mutableblockpos.set(x, y1, z1);
                            if (level.getBlockState(blockpos$mutableblockpos).getBlock() != Blocks.WATER) {
                                continue;
                            }

                            for (int i = 0; i < 2; i++) {
                                if (i == 0 && true) {//!doubleRender) {
                                    continue;
                                }
                                if (doubleRender) {
                                    if (i == 0) {
                                        x1 = x + 2;
                                    } else {
                                        x1 = x - l * 2;
                                        x2 = x - 2;
                                    }
                                }
                                if (x1 != x2) {
                                    RandomSource randomsource = RandomSource.create((long) (y1 * y1 * 3121 + y1 * 45238971 ^ z1 * z1 * 418711 + z1 * 13761));
                                    if (i1 != 0) {
                                        if (i1 >= 0) {
                                            tesselator.end();
                                        }

                                        i1 = 0;
                                        RenderSystem.setShaderTexture(0, RAIN_LOCATION);
                                        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
                                    }

                                    long i3 = (ticks + (y1 * y1 * 3121) + (y1 * 45238971) + (z1 * z1 * 418711) + (z1 * 13761)) & 31;
                                    float f2 = -((float) i3 + pPartialTick) / 32.0F * (3.0F + randomsource.nextFloat());
                                    f2 *= 3 * speedIncrement;
                                    double d2 = (double) y1 + 0.5D - pCamY;
                                    double d4 = (double) z1 + 0.5D - pCamZ;
                                    float f3 = (float) Math.sqrt(d2 * d2 + d4 * d4) / (float) l;
                                    float f4 = ((1.0F - f3 * f3) * 0.5F + 0.5F) * intensity;
                                    int j3 = getLightColor(level, blockpos$mutableblockpos);
                                    //bufferbuilder.vertex((double)y1 - pCamX - d0 + 0.5D, (double)x2 - pCamY, (double)z1 - pCamZ - d1 + 0.5D).uv(0.0F, (float)x1 * 0.25F + f2).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();
                                    //bufferbuilder.vertex((double)y1 - pCamX + d0 + 0.5D, (double)x2 - pCamY, (double)z1 - pCamZ + d1 + 0.5D).uv(1.0F, (float)x1 * 0.25F + f2).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();
                                    //bufferbuilder.vertex((double)y1 - pCamX + d0 + 0.5D, (double)x1 - pCamY, (double)z1 - pCamZ + d1 + 0.5D).uv(1.0F, (float)x2 * 0.25F + f2).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();
                                    //bufferbuilder.vertex((double)y1 - pCamX - d0 + 0.5D, (double)x1 - pCamY, (double)z1 - pCamZ - d1 + 0.5D).uv(0.0F, (float)x2 * 0.25F + f2).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();

                                    bufferbuilder.vertex((double) xEnd - pCamX, (double) y1 - pCamY - d0 + 0.5D, (double) z1 - pCamZ - d1 + 0.5D).uv((float) xStart * 0.25F + f2, 0.0F).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();
                                    bufferbuilder.vertex((double) xEnd - pCamX, (double) y1 - pCamY + d0 + 0.5D, (double) z1 - pCamZ + d1 + 0.5D).uv((float) xStart * 0.25F + f2, 1.0F).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();
                                    bufferbuilder.vertex((double) xStart - pCamX, (double) y1 - pCamY + d0 + 0.5D, (double) z1 - pCamZ + d1 + 0.5D).uv((float) xEnd * 0.25F + f2, 1.0F).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();
                                    bufferbuilder.vertex((double) xStart - pCamX, (double) y1 - pCamY - d0 + 0.5D, (double) z1 - pCamZ - d1 + 0.5D).uv((float) xEnd * 0.25F + f2, 0.0F).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();

                                    //bufferbuilder.vertex((double)y1 - pCamX - d0 + 0.5D, (double)x1 - pCamY, (double)z1 - pCamZ - d1 + 0.5D).uv(0.0F, (float)x1 * 0.25F + f2).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();
                                    //bufferbuilder.vertex((double)y1 - pCamX - d0 + 0.5D, (double)x2 - pCamY, (double)z1 - pCamZ - d1 + 0.5D).uv(1.0F, (float)x1 * 0.25F + f2).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();
                                    //bufferbuilder.vertex((double)y1 - pCamX + d0 + 0.5D, (double)x2 - pCamY, (double)z1 - pCamZ + d1 + 0.5D).uv(1.0F, (float)x2 * 0.25F + f2).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();
                                    //bufferbuilder.vertex((double)y1 - pCamX + d0 + 0.5D, (double)x1 - pCamY, (double)z1 - pCamZ + d1 + 0.5D).uv(0.0F, (float)x2 * 0.25F + f2).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();

                                    //bufferbuilder.vertex((double)y1 - pCamX - d0 + 0.5D, (double)x2 - pCamY, (double)z1 - pCamZ /*- d1*/ + 0.5D).uv(0.0F + f2, (float)x1 * 1F).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();
                                    //bufferbuilder.vertex((double)y1 - pCamX + d0 + 0.5D, (double)x2 - pCamY, (double)z1 - pCamZ /*+ d1*/ + 0.5D).uv(0.25F + f2, (float)x1 * 1F).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();
                                    //bufferbuilder.vertex((double)y1 - pCamX + d0 + 0.5D, (double)x1 - pCamY, (double)z1 - pCamZ /*+ d1*/ + 0.5D).uv(0.25F + f2, (float)x2 * 1F).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();
                                    //bufferbuilder.vertex((double)y1 - pCamX - d0 + 0.5D, (double)x1 - pCamY, (double)z1 - pCamZ /*- d1*/ + 0.5D).uv(0.0F + f2, (float)x2 * 1F).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();
                                }
                            }
                        }
                    }
                }
            }

            if (i1 >= 0) {
                tesselator.end();
            }
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            //pLightTexture.turnOffLightLayer();
            mc.gameRenderer.lightTexture().turnOffLightLayer();
        }

    }

    @SubscribeEvent
    public static void fogEvent(ViewportEvent.RenderFog event) {
        LocalPlayer p = Minecraft.getInstance().player;
        if (ClientData.getInstance().isBlinded()) {
            event.setFarPlaneDistance(0);
            event.setNearPlaneDistance(0);
            event.setCanceled(true);
        } else if (p.isUnderWater() && p.level().dimension() == BTVDimensions.ARCHE_LEVEL) {
            event.setFarPlaneDistance(80);
            event.setNearPlaneDistance(40);
            event.setCanceled(true);
        } else if (p.isUnderWater()) { // TODO eventually remove this...
            event.setFarPlaneDistance(300);
            //event.setNearPlaneDistance(-100);
            event.setCanceled(true);
        } else if (ClientData.getInstance().getContactFogLevel() > 0) {
            event.setFarPlaneDistance(256 - ClientData.getInstance().getContactFogLevel());
            event.setNearPlaneDistance(0);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void fogColorEvent(ViewportEvent.ComputeFogColor event) {
        LocalPlayer p = Minecraft.getInstance().player;
        if (ClientData.getInstance().isBlinded()) {
            event.setRed(0);
            event.setGreen(0);
            event.setBlue(0);
        } else if (p != null && p.isUnderWater() && p.level().dimension() == BTVDimensions.ARCHE_LEVEL) {
            event.setRed(0);
            event.setGreen(0);
            event.setBlue(0);
        } else if (ClientData.getInstance().getContactFogLevel() > 0) {
            event.setRed(0);
            event.setGreen(0);
            event.setBlue(0);
        }
    }

    @SubscribeEvent
    public static void renderFlaskShelfOutline(RenderHighlightEvent.Block event) {
        BlockPos blockPos = event.getTarget().getBlockPos();
        Level l = Minecraft.getInstance().level;
        if (l != null) {
            BlockState blockState = l.getBlockState(blockPos);
            if (blockState.getBlock() == Registration.FLASK_SHELF.get()) {
                event.setCanceled(true);
                VertexConsumer vertexconsumer2 = event.getMultiBufferSource().getBuffer(RenderType.lines());

                Vec3 vec3 = event.getCamera().getPosition();
                double d0 = vec3.x();
                double d1 = vec3.y();
                double d2 = vec3.z();
                renderHitOutline(event.getPoseStack(), vertexconsumer2, event.getCamera().getEntity(), d0, d1, d2, event.getTarget().getBlockPos(), blockState, l, FlaskShelfBlock.getBaseShape(blockState));
            }
        }
    }

    private static void renderHitOutline(PoseStack pPoseStack, VertexConsumer pConsumer, Entity pEntity, double pCamX, double pCamY, double pCamZ, BlockPos pPos, BlockState pState, Level l, VoxelShape shape) {
        renderShape(pPoseStack, pConsumer, shape, (double) pPos.getX() - pCamX, (double) pPos.getY() - pCamY, (double) pPos.getZ() - pCamZ, 0.0F, 0.0F, 0.0F, 0.4F);
    }

    private static void renderShape(PoseStack pPoseStack, VertexConsumer pConsumer, VoxelShape pShape, double pX, double pY, double pZ, float pRed, float pGreen, float pBlue, float pAlpha) {
        PoseStack.Pose posestack$pose = pPoseStack.last();
        pShape.forAllEdges((p_234280_, p_234281_, p_234282_, p_234283_, p_234284_, p_234285_) -> {
            float f = (float) (p_234283_ - p_234280_);
            float f1 = (float) (p_234284_ - p_234281_);
            float f2 = (float) (p_234285_ - p_234282_);
            float f3 = Mth.sqrt(f * f + f1 * f1 + f2 * f2);
            f /= f3;
            f1 /= f3;
            f2 /= f3;
            pConsumer.vertex(posestack$pose.pose(), (float) (p_234280_ + pX), (float) (p_234281_ + pY), (float) (p_234282_ + pZ)).color(pRed, pGreen, pBlue, pAlpha).normal(posestack$pose.normal(), f, f1, f2).endVertex();
            pConsumer.vertex(posestack$pose.pose(), (float) (p_234283_ + pX), (float) (p_234284_ + pY), (float) (p_234285_ + pZ)).color(pRed, pGreen, pBlue, pAlpha).normal(posestack$pose.normal(), f, f1, f2).endVertex();
        });
    }

    @SubscribeEvent
    public static void renderGameOverlay(RenderGuiOverlayEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            if (event instanceof RenderGuiOverlayEvent.Pre) {
                if (Minecraft.getInstance().screen instanceof SurgeryBedGui) {
                    if (event.getOverlay() == VanillaGuiOverlay.SLEEP_FADE.type()) {
                        event.setCanceled(true);
                    }
                }
                ReminiscenceClient.renderReminiscence(event);
                renderSyringeContents(event);
                renderBindingEnergy(event);
                renderSurgeryOverlays(event);
                renderBlackScreen(event);
                renderRepairHammerOverlay(event);
                renderExplosionRedScreen(event);
                renderDreamFocusBar(event);
            }
        }
    }

    private static final ResourceLocation DREAM_FOCUS_OVERLAY = new ResourceLocation(References.MODID, "textures/gui/overlay/focus_overlay.png");

    private static void renderDreamFocusBar(RenderGuiOverlayEvent event) {
        int dreamFocusTime = ClientData.getInstance().getDreamFocusTime();
        if (dreamFocusTime > 0) {
            GuiGraphics gg = event.getGuiGraphics();
            PoseStack pose = gg.pose();
            pose.pushPose();
            int width = gg.guiWidth();
            int height = gg.guiHeight();
            final int BAR_WIDTH = 128;
            final int BAR_HEIGHT = BAR_WIDTH / 4;
            final int TOP_Y = height - 52;
            final int LEFT_X = width / 2 - BAR_WIDTH / 2;
            final float SIZE_MULTIPLIER = 1.5F;
            float percentage = (dreamFocusTime - event.getPartialTick()) / ((float) DreamFocusBlock.DREAM_FOCUS_TIME);
            gg.blit(DREAM_FOCUS_OVERLAY, LEFT_X, TOP_Y, (int) (BAR_WIDTH * percentage), BAR_HEIGHT, 0, 0, Mth.floor(128 * percentage), 32, 128, 32);
            pose.popPose();
        }
    }
    private static void renderExplosionRedScreen(RenderGuiOverlayEvent event) {
        int explosionTicks = InputEvents.getExplosionTicks();
        if (explosionTicks > 0 && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            PoseStack poseStack = event.getGuiGraphics().pose();
            poseStack.pushPose();
            poseStack.translate(0, 0, 10);
            Matrix4f matrix4f = poseStack.last().pose();
            Window window = Minecraft.getInstance().getWindow();
            int alpha = -(explosionTicks - 30) * 100 / 300;
            event.getGuiGraphics().fill(0, 0, window.getGuiScaledWidth(), window.getGuiScaledWidth(), alpha << 24 | 0xFF0000);
            poseStack.popPose();
        }
    }

    private static void renderBlackScreen(RenderGuiOverlayEvent event) {
        if (blackScreenDuration >= 0) {
            PoseStack poseStack = event.getGuiGraphics().pose();
            poseStack.pushPose();
            poseStack.translate(0, 0, 10);
            Matrix4f matrix4f = poseStack.last().pose();
            Window window = Minecraft.getInstance().getWindow();
            RenderEvents.innerFill(matrix4f, 0, 0, window.getGuiScaledWidth(), window.getGuiScaledHeight(), 0xFF000000);
            poseStack.popPose();
        }

    }

    private static final VoxelShape OVERWORLD_BINDING_OUTLINE = Shapes.box(0, 0, 0, 1, 1, 1);

    @SubscribeEvent
    public static void renderWorldLastEvent(RenderLevelStageEvent event) {
        LocalPlayer p = Minecraft.getInstance().player;
        if (p != null && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
                RenderPlayerUtils.renderArms(event.getPoseStack(), event.getCamera(), event.getPartialTick(), Minecraft.getInstance().renderBuffers().bufferSource());
            }
            float partialTick = Minecraft.getInstance().getPartialTick();
            CrossSync crossSync = CrossSyncHolder.getCrossSync(p);
            if (crossSync != null && event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
                Mob heldPatientEntity = crossSync.getHeldPatientEntity(p.level());
                if (heldPatientEntity != null) {
                    PoseStack poseStack = event.getPoseStack();
                    poseStack.pushPose();
                    float scaleFactor = 1;
                    poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
                    poseStack.mulPose(Axis.YP.rotation((float) (Math.PI - Math.toRadians(Mth.rotLerp(partialTick, p.yBodyRotO, p.yBodyRot)))));
                    MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
                    EntityRenderDispatcher erd = Minecraft.getInstance().getEntityRenderDispatcher();
                    erd.render(heldPatientEntity, -0.4, 0, 0.1, 0, partialTick, poseStack, bufferSource, erd.getPackedLightCoords(p, partialTick));
                    poseStack.popPose();
                }
            }
        }
        if (p != null) {
            BindingData data = DataUtil.getBindingData(p);
            if (data != null) {
                if (data.getBinding() == Binding.OVERWORLD) {
                    BlockPos overworldPos1 = data.getOverworldPos1();
                    BlockPos overworldPos2 = data.getOverworldPos2();
                    VertexConsumer vertexconsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.lines());
                    Camera camera = event.getCamera();
                    Vec3 position = camera.getPosition();
                    if (overworldPos1 != null) {
                        LevelRenderer.renderLineBox(event.getPoseStack(), vertexconsumer, overworldPos1.getX() - position.x, overworldPos1.getY() - position.y, overworldPos1.getZ() - position.z, overworldPos1.getX() - position.x + 1, overworldPos1.getY() - position.y + 1, overworldPos1.getZ() - position.z + 1, 1, 0, 0, 1, 1, 0, 0);
                    }
                    if (overworldPos2 != null) {
                        LevelRenderer.renderLineBox(event.getPoseStack(), vertexconsumer, overworldPos2.getX() - position.x, overworldPos2.getY() - position.y, overworldPos2.getZ() - position.z, overworldPos2.getX() - position.x + 1, overworldPos2.getY() - position.y + 1, overworldPos2.getZ() - position.z + 1, 0, 1, 0, 1, 0, 1, 0);
                    }
                }
            }
        }
    }


    public static void innerFill(Matrix4f pMatrix, int pMinX, int pMinY, int pMaxX, int pMaxY, int pColor) {
        if (pMinX < pMaxX) {
            int i = pMinX;
            pMinX = pMaxX;
            pMaxX = i;
        }

        if (pMinY < pMaxY) {
            int j = pMinY;
            pMinY = pMaxY;
            pMaxY = j;
        }

        float f3 = (float) (pColor >> 24 & 255) / 255.0F;
        float f = (float) (pColor >> 16 & 255) / 255.0F;
        float f1 = (float) (pColor >> 8 & 255) / 255.0F;
        float f2 = (float) (pColor & 255) / 255.0F;
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        RenderSystem.enableBlend();
        //RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferbuilder.vertex(pMatrix, (float) pMinX, (float) pMaxY, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(pMatrix, (float) pMaxX, (float) pMaxY, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(pMatrix, (float) pMaxX, (float) pMinY, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(pMatrix, (float) pMinX, (float) pMinY, 0.0F).color(f, f1, f2, f3).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end()); // TODO with shader or just draw()?
        //RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    private static final ResourceLocation SYRINGE_TANK_TEXTURE = new ResourceLocation(References.MODID, "textures/gui/overlay/syringe_tank.png");
    private static final ResourceLocation BINDING_ENERGY_TEXTURE = new ResourceLocation(References.MODID, "textures/gui/overlay/binding_energy.png");
    private static final ResourceLocation BLOOD_TEXTURE = new ResourceLocation(References.MODID, "textures/block/blood.png");

    private static void renderSyringeContents(RenderGuiOverlayEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !(event instanceof RenderGuiOverlayEvent.Pre)) {
            return;
        }
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemStack.getItem() != Registration.SYRINGE.get() || !itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {
            return;
        }
        GuiGraphics guiGraphics = event.getGuiGraphics();
        int width = guiGraphics.guiWidth();
        int height = guiGraphics.guiHeight();
        final int TOP_Y = height / 20;
        final int LEFT_X = width / 40;
        final float SIZE_MULTIPLIER = 1.25F;
        guiGraphics.blit(SYRINGE_TANK_TEXTURE, LEFT_X, TOP_Y, (int) (44 * SIZE_MULTIPLIER), (int) (142 * SIZE_MULTIPLIER), (float) 0, (float) 0, (int) (44 * SIZE_MULTIPLIER), (int) (142 * SIZE_MULTIPLIER), (int) (44 * SIZE_MULTIPLIER), (int) (142 * SIZE_MULTIPLIER));
        IFluidHandlerItem syringe = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().orElseThrow();
        FluidStack fluidInTank = syringe.getFluidInTank(0);
        if (!fluidInTank.isEmpty()) {
            FluidState defaultFluidState = fluidInTank.getFluid().defaultFluidState();
            IClientFluidTypeExtensions props = IClientFluidTypeExtensions.of(defaultFluidState);
            BlockPos pos = player.getOnPos();
            ClientLevel level = player.clientLevel;
            ResourceLocation stillTexture = props.getStillTexture();
            TextureAtlasSprite stillSprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(stillTexture);
            //TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(props.getStillTexture(defaultFluidState, level, pos));
            float percentFilled = fluidInTank.getAmount() / (float) syringe.getTankCapacity(0);
            final int X_BASE_OFFSET = 6; // WITHOUT MULTIPLIER
            final int Y_BASE_OFFSET = 133;
            for (int i = 0; i < 4; i++) {
                if (i * 0.25F >= percentFilled) {
                    break;
                }
                int heightOfQuartile = (int) (SIZE_MULTIPLIER * 128 * (percentFilled - 0.25F * i));
                float currentBaseX = LEFT_X + X_BASE_OFFSET * SIZE_MULTIPLIER;
                float currentBaseY = TOP_Y + (Y_BASE_OFFSET - 32 * i) * SIZE_MULTIPLIER;
                float currentTopY = currentBaseY - heightOfQuartile;
                if (fluidInTank.getFluid() == Fluids.WATER) {
                    int averageWaterColor = BiomeColors.getAverageWaterColor(level, pos);
                    float r = ((averageWaterColor >> 16) & 255) / 255F;
                    float g = ((averageWaterColor >> 8) & 255) / 255F;
                    float b = ((averageWaterColor) & 255) / 255F;
                    guiGraphics.blit((int) currentBaseX, (int) (currentBaseY - 32 * SIZE_MULTIPLIER), 0, (int) (32 * SIZE_MULTIPLIER), (int) (32 * SIZE_MULTIPLIER), stillSprite, r, g, b, 1);
                } else {
                    guiGraphics.blit((int) currentBaseX, (int) (currentBaseY - 32 * SIZE_MULTIPLIER), 0, (int) (32 * SIZE_MULTIPLIER), (int) (32 * SIZE_MULTIPLIER), stillSprite);
                }
                if (heightOfQuartile < SIZE_MULTIPLIER * 32)
                    guiGraphics.blit(SYRINGE_TANK_TEXTURE, (int) currentBaseX, (int) (currentBaseY - 32 * SIZE_MULTIPLIER), (int) (32 * SIZE_MULTIPLIER), (int) (32 * SIZE_MULTIPLIER - heightOfQuartile), X_BASE_OFFSET * SIZE_MULTIPLIER, Y_BASE_OFFSET * SIZE_MULTIPLIER - 32 * SIZE_MULTIPLIER, (int) (32 * SIZE_MULTIPLIER), (int) (32 * SIZE_MULTIPLIER - heightOfQuartile), (int) (44 * SIZE_MULTIPLIER), (int) (142 * SIZE_MULTIPLIER));
//            guiGraphics.blit(stillTexture, currentBaseX, currentTopY, 32*SIZE_MULTIPLIER, heightOfQuartile, 0, 0, 32*SIZE_MULTIPLIER, heightOfQuartile, 32*SIZE_MULTIPLIER, 32*SIZE_MULTIPLIER);

            }
            guiGraphics.drawString(Minecraft.getInstance().font, String.format("%d mB", fluidInTank.getAmount()), (int) (LEFT_X + 48 * SIZE_MULTIPLIER), (int) (TOP_Y + (142 / 2) * SIZE_MULTIPLIER), 0xFFFFFF00);
            guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable(fluidInTank.getTranslationKey()), (int) (LEFT_X + 48 * SIZE_MULTIPLIER), (int) (TOP_Y + (142 / 2) * SIZE_MULTIPLIER + 7), 0xFFFFFF00);
        }
        for (int i = 0; i < 4; i++) {
            float currentBaseX = LEFT_X + 35 * SIZE_MULTIPLIER;
            float currentBaseY = TOP_Y + (133 - 32 * (i + 1)) * SIZE_MULTIPLIER;
            guiGraphics.fill((int) currentBaseX, (int) currentBaseY, (int) (currentBaseX + 3 * SIZE_MULTIPLIER), (int) (currentBaseY + 1 * SIZE_MULTIPLIER), 0xFF000000);
        }
    }

    private static void renderBindingEnergy(RenderGuiOverlayEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !(event instanceof RenderGuiOverlayEvent.Pre)) {
            return;
        }
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemStack.getItem() != Registration.BLOOD_FIST.get()) {
            return;
        }
        BindingData data = DataUtil.getBindingData(player);
        if (data != null) {
            GuiGraphics guiGraphics = event.getGuiGraphics();
            PoseStack pose = guiGraphics.pose();
            int width = guiGraphics.guiWidth();
            int height = guiGraphics.guiHeight();
            final float SIZE_MULTIPLIER = 0.65F;
            final float TOP_Y = (int) (height / 2 - 172 * SIZE_MULTIPLIER / 2);
            final float LEFT_X = width / 60F;
            pose.pushPose();
            pose.translate(LEFT_X, TOP_Y, 0);
            guiGraphics.blit(BINDING_ENERGY_TEXTURE, 0, 0, (int) (50 * SIZE_MULTIPLIER), (int) (172 * SIZE_MULTIPLIER), (float) 0, (float) 0, (int) (50 * SIZE_MULTIPLIER), (int) (172 * SIZE_MULTIPLIER), (int) (50 * SIZE_MULTIPLIER), (int) (172 * SIZE_MULTIPLIER));
            pose.popPose();
            float amountFilled = Math.min(1, data.getEnergy() / 10000F);
            final float X_BASE_OFFSET = 5.5F; // WITHOUT MULTIPLIER
            final int Y_BASE_OFFSET = 162;
            final float TANK_HEIGHT = 153.8F;
            float currentBaseX = LEFT_X + X_BASE_OFFSET * SIZE_MULTIPLIER;
            float currentBaseY = TOP_Y + Y_BASE_OFFSET * SIZE_MULTIPLIER;
            float bloodHeight = TANK_HEIGHT * amountFilled * SIZE_MULTIPLIER;
            pose.pushPose();
            pose.translate(currentBaseX - 0.4, currentBaseY - bloodHeight + 2, 0);
            float pVOffset = 48 * (1 - amountFilled);
            float pVHeight = 48 * amountFilled;
            //guiGraphics.blit(BLOOD_TEXTURE, 0, 0, (int) (40 * SIZE_MULTIPLIER), (int) bloodHeight, 0, pVOffset, 15, (int) pVHeight, 48, 48);
            float pMinU = 0;
            float pMaxU = (15F) / (float)48;
            float pMinV = (pVOffset + 0.0F) / (float)48;
            float pMaxV = (pVOffset + (float)pVHeight) / (float)48;
            RenderSystem.setShaderTexture(0, BLOOD_TEXTURE);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            Matrix4f matrix4f = pose.last().pose();
            BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(matrix4f, (float)0, (float)0, (float)0).uv(pMinU, pMinV).endVertex();
            bufferbuilder.vertex(matrix4f, (float)0, (float)bloodHeight, (float)0).uv(pMinU, pMaxV).endVertex();
            final float TANK_WIDTH = 39.4F;
            bufferbuilder.vertex(matrix4f, (float)(TANK_WIDTH * SIZE_MULTIPLIER), (float)bloodHeight, (float)0).uv(pMaxU, pMaxV).endVertex();
            bufferbuilder.vertex(matrix4f, (float)(TANK_WIDTH * SIZE_MULTIPLIER), (float)0, (float)0).uv(pMaxU, pMinV).endVertex();
            BufferUploader.drawWithShader(bufferbuilder.end());
            pose.popPose();
        }
    }

    private static void renderSurgeryOverlays(RenderGuiOverlayEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        GuiGraphics gg = event.getGuiGraphics();
        if (player == null || !(event instanceof RenderGuiOverlayEvent.Pre)) {
            return;
        }
        Item mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhandItem = player.getItemInHand(InteractionHand.OFF_HAND).getItem();
        final int X_OFFSET = gg.guiWidth() / 2 + 10;
        HitResult hitResult = Minecraft.getInstance().hitResult;
        if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
            if (hitResult instanceof BlockHitResult bhr) {
                BlockPos lookedAtPos = bhr.getBlockPos();
                BlockState blockState = player.level().getBlockState(lookedAtPos);
                BlockEntity be = player.level().getBlockEntity(lookedAtPos);
                if (blockState.getBlock() instanceof FlaskBlock && be instanceof FlaskBE flaskBE) {
                    FluidStack fluid = flaskBE.getTank().getFluid();
                    if (!fluid.isEmpty() && (mainHandItem == Registration.SYRINGE.get())) {
                        gg.drawString(Minecraft.getInstance().font, fluid.getDisplayName(), X_OFFSET, gg.guiHeight() / 2, 0xFF000000 | Color.YELLOW.getRGB());
                        gg.drawString(Minecraft.getInstance().font, fluid.getAmount() + " mB/" + flaskBE.getTank().getTankCapacity(0) + " mB", X_OFFSET, gg.guiHeight() / 2 + 15, 0xFF000000 | Color.YELLOW.getRGB());
                    }
                } else if (blockState.getBlock() == Registration.FLASK_SHELF.get()) {
                    BlockPos centerPos = Registration.FLASK_SHELF.get().findCenter(lookedAtPos, blockState);
                    if (player.level().getBlockEntity(centerPos) instanceof FlaskShelfBE flaskShelfBE) {
                        FlaskShelfBE.Flask lookedAtFlask = flaskShelfBE.getLookedAtFlask(player.level(), lookedAtPos, bhr.getLocation());
                        if (lookedAtFlask != null) {
                            FluidStack fluid = lookedAtFlask.getTank().getFluid();
                            if (!fluid.isEmpty() && (mainHandItem == Registration.SYRINGE.get())) {
                                gg.drawString(Minecraft.getInstance().font, fluid.getDisplayName(), X_OFFSET, gg.guiHeight() / 2, 0xFF000000 | Color.YELLOW.getRGB());
                                gg.drawString(Minecraft.getInstance().font, fluid.getAmount() + " mB/" + lookedAtFlask.getTank().getTankCapacity(0) + " mB", X_OFFSET, gg.guiHeight() / 2 + 15, 0xFF000000 | Color.YELLOW.getRGB());
                            }
                        }
                    }
                } else if (blockState.getBlock() == Registration.SURGERY_BED.get() || blockState.getBlock() == Registration.WATERY_CRADLE.get()) {
                    // replace with findCenter from thinmultiblock when the time comes
                    BlockPos center;
                    if (blockState.getBlock() == Registration.SURGERY_BED.get()) {
                        center = Registration.SURGERY_BED.get().findCenter(lookedAtPos, blockState);
                    } else if (blockState.getBlock() == Registration.WATERY_CRADLE.get()) {
                        center = Registration.WATERY_CRADLE.get().findCenter(lookedAtPos, blockState);
                    } else {
                        center = lookedAtPos;
                    }
                    if (player.level().getBlockEntity(center) instanceof SurgicalBE surgicalBE && (isSurgicalItem(mainHandItem) || isSurgicalItem(offhandItem))) {
                        PatientStatus patientStatus = surgicalBE.getPatientStatus();
                        if (surgicalBE.getEntity() != null && patientStatus != null) {
                            int pY = gg.guiHeight() / 2;
                            gg.drawString(Minecraft.getInstance().font, I18n.get("surgery.status.status") + patientStatus.getCondition().toString(), X_OFFSET, pY, 0xFF000000 | Color.YELLOW.getRGB());
                            pY += 15;
                            if (patientStatus.getLeftoverCapacity() > 0) {
                                gg.drawString(Minecraft.getInstance().font, I18n.get("surgery.status.capacity") + patientStatus.getLeftoverCapacity(), X_OFFSET, pY, 0xFF000000 | Color.YELLOW.getRGB());
                                pY += 15;
                            }
                            for (String s : triggerDataDescription(patientStatus.getTriggerData())) {
                                gg.drawString(Minecraft.getInstance().font, s, X_OFFSET, pY, 0xFF000000 | Color.YELLOW.getRGB());
                                pY += 15;
                            }

                        }
                    }
                }
            }
        }
    }

    public static List<String> triggerDataDescription(@NotNull TriggerData data) {
        List<String> lines = new ArrayList<>();
        for (ArsenalEffect arsenalEffect : data.getEffects()) {
            lines.add(arsenalEffect.getEffectType().getDisplayName().getString());
            lines.add("-   " + I18n.get("surgery.status.arsenal_amplifier") + arsenalEffect.getAmplifier());
            lines.add("-   " + I18n.get("surgery.status.arsenal_duration") + arsenalEffect.getAmplifier());
        }
        if (data.getBurst() != null && data.getBurst().getExtension() > 0) {
            lines.add(I18n.get("surgery.status.burst_extension") + data.getBurst().getExtension());
        }
        if (data.getMutex() != null) {
            lines.add(I18n.get("surgery.status.mutex") + data.getMutex().getName());
        }
        if (data.getTriggerType() != null) {
            lines.add(I18n.get("surgery.status.trigger_type") + I18n.get("target_type." + data.getTriggerType().name()));
        }
        if (data.getTargetType() != null) {
            lines.add(I18n.get("surgery.status.target_type") + I18n.get("target_type." + data.getTargetType().name()));
        }
        return lines;
    }

    private static boolean isSurgicalItem(Item item) {
        return item == Registration.SYRINGE.get()
                || item == Registration.SCALPEL.get()
                || item == Registration.SEWING_NEEDLE.get()
                || item == Registration.TONGS.get()
                || item == Registration.FORCEPS.get();
    }

    private static void renderRepairHammerOverlay(RenderGuiOverlayEvent event) {

        LocalPlayer player = Minecraft.getInstance().player;
        GuiGraphics gg = event.getGuiGraphics();
        if (player == null || !(event instanceof RenderGuiOverlayEvent.Pre)) {
            return;
        }
        Item mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        final int X_OFFSET = gg.guiWidth() / 2 + 10;
        HitResult hitResult = Minecraft.getInstance().hitResult;
        if (mainHandItem == Registration.REPAIR_HAMMER.get() && hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
            if (hitResult instanceof EntityHitResult ehr && ehr.getEntity() instanceof NautilusEntity nautilus) {
                int pY = gg.guiHeight() / 2;
                //gg.drawString(Minecraft.getInstance().font, Component.literal(String.format("Integrity: %.2f%%", 100 - 100 * nautilus.getDamage() / NautilusEntity.TOTAL_HEALTH)), X_OFFSET, pY, 0xFF000000 | Color.YELLOW.getRGB());
                gg.drawString(Minecraft.getInstance().font, Component.translatable("overlay.repair_hammer.submarine", 100 - 100 * nautilus.getDamage() / NautilusEntity.TOTAL_HEALTH), X_OFFSET, pY, 0xFF000000 | Color.YELLOW.getRGB());
            }
        }
    }

}
