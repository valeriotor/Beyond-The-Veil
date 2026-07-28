package com.valeriotor.beyondtheveil.block;

import com.valeriotor.beyondtheveil.lib.BTVBlockEntities;
import com.valeriotor.beyondtheveil.tile.FlaskBE;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class FlaskBlock extends Block implements EntityBlock {

    public static final Map<FlaskShape, FlaskBlock> sizeToBlock = new EnumMap<>(FlaskShape.class);
    private static final double a = 0.0625;

    private static final VoxelShape SHAPE = Shapes.box(0.0625 * 4, 0, 0.0625 * 4, 0.0625 * 12, 0.0625 * 11, 0.0625 * 12);
    private static final VoxelShape SHAPE_JAR_LARGE;
    private static final VoxelShape SHAPE_JAR_MEDIUM;
    private static final VoxelShape SHAPE_JAR_SMALL;
    private static final VoxelShape SHAPE_FLASK_LARGE;
    private static final VoxelShape SHAPE_FLASK_MEDIUM;
    private static final VoxelShape SHAPE_FLASK_SMALL;
    private static final VoxelShape SHAPE_ITEM;
    public static final double[] JAR_LARGE1 = {5 * a, 0, 5 * a, 11 * a, a / 2, 11 * a};
    public static final double[] JAR_LARGE2 = {5 * a, a / 2, 4.5 * a, 11 * a, 8.5 * a, 11.5 * a};
    public static final double[] JAR_LARGE3 = {4.5 * a, a / 2, 5 * a, 11.5 * a, 8.5 * a, 11 * a};
    public static final double[] JAR_LARGE4 = {5 * a, 8.5 * a, 5 * a, 11 * a, 9 * a, 11 * a};
    public static final double[] JAR_LARGE5 = {5.5 * a, 9 * a, 5.5 * a, 10.5 * a, 9.5 * a, 10.5 * a};
    public static final double[] JAR_LARGE6 = {6.5 * a, 9.5 * a, 6.5 * a, 9.5 * a, 10.5 * a, 9.5 * a};
    public static final double[] JAR_MEDIUM1 = {6 * a, 0, 6 * a, 10 * a, a / 2, 10 * a};
    public static final double[] JAR_MEDIUM2 = {6 * a, a / 2, 5.5 * a, 10 * a, 6.5 * a, 10.5 * a};
    public static final double[] JAR_MEDIUM3 = {5.5 * a, a / 2, 6 * a, 10.5 * a, 6.5 * a, 10 * a};
    public static final double[] JAR_MEDIUM4 = {6 * a, 6.5 * a, 6 * a, 10 * a, 7 * a, 10 * a};
    public static final double[] JAR_MEDIUM5 = {6.5 * a, 7 * a, 6.5 * a, 9.5 * a, 7.5 * a, 9.5 * a};
    public static final double[] JAR_MEDIUM6 = {7 * a, 7.5 * a, 7 * a, 9 * a, 8 * a, 9 * a};
    public static final double[] JAR_SMALL1 = {7 * a, 0, 7 * a, 9 * a, a / 4, 9 * a};
    public static final double[] JAR_SMALL2 = {7 * a, a / 4, 6.75 * a, 9 * a, 4.5 * a, 9.25 * a};
    public static final double[] JAR_SMALL3 = {6.75 * a, a / 4, 7 * a, 9.25 * a, 4.5 * a, 9 * a};
    public static final double[] JAR_SMALL4 = {7 * a, 4.5 * a, 7 * a, 9 * a, 4.75 * a, 9 * a};
    public static final double[] JAR_SMALL5 = {7.25 * a, 4.75 * a, 7.25 * a, 8.75 * a, 5 * a, 8.75 * a};
    public static final double[] JAR_SMALL6 = {7.5 * a, 5 * a, 7.5 * a, 8.5 * a, 5.5 * a, 8.5 * a};

    public static final double[] FLASK_LARGE1 = {6 * a, 0, 6 * a, 10 * a, a / 2, 10 * a};
    public static final double[] FLASK_LARGE2 = {6 * a, a / 2, 5.5 * a, 10 * a, 8.25 * a, 10.5 * a};
    public static final double[] FLASK_LARGE3 = {5.5 * a, a / 2, 6 * a, 10.5 * a, 8.25 * a, 10 * a};
    public static final double[] FLASK_LARGE4 = {6 * a, 8.25 * a, 6 * a, 10 * a, 8.75 * a, 10 * a};
    public static final double[] FLASK_LARGE5 = {6.5 * a, 8.75 * a, 6.5 * a, 9.5 * a, 9.25 * a, 9.5 * a};
    public static final double[] FLASK_LARGE6 = {7 * a, 9.25 * a, 7 * a, 9 * a, 13.25 * a, 9 * a};
    public static final double[] FLASK_LARGE7 = {6.5 * a, 11 * a, 6.5 * a, 9.5 * a, 11.75 * a, 9.5 * a};

    public static final double[] FLASK_MEDIUM1 = {6.5 * a, 0, 6.5 * a, 9.5 * a, a / 2, 9.5 * a};
    public static final double[] FLASK_MEDIUM2 = {6.5 * a, a / 2, 6 * a, 9.5 * a, 6.5 * a, 10 * a};
    public static final double[] FLASK_MEDIUM3 = {6 * a, a / 2, 6.5 * a, 10 * a, 6.5 * a, 9.5 * a};
    public static final double[] FLASK_MEDIUM4 = {6.5 * a, 6.5 * a, 6.5 * a, 9.5 * a, 7 * a, 9.5 * a};
    public static final double[] FLASK_MEDIUM5 = {7 * a, 7 * a, 7 * a, 9 * a, 7.5 * a, 9 * a};
    public static final double[] FLASK_MEDIUM6 = {7.25 * a, 7.5 * a, 7.25 * a, 8.75 * a, 10 * a, 8.75 * a};
    public static final double[] FLASK_MEDIUM7 = {7 * a, 8.5 * a, 7 * a, 9 * a, 9 * a, 9 * a};

    public static final double[] FLASK_SMALL1 = {7 * a, 0, 7 * a, 9 * a, a / 4, 9 * a};
    public static final double[] FLASK_SMALL2 = {7 * a, a / 4, 6.75 * a, 9 * a, 4.5 * a, 9.25 * a};
    public static final double[] FLASK_SMALL3 = {6.75 * a, a / 4, 7 * a, 9.25 * a, 4.5 * a, 9 * a};
    public static final double[] FLASK_SMALL4 = {7 * a, 4.5 * a, 7 * a, 9 * a, 4.75 * a, 9 * a};
    public static final double[] FLASK_SMALL5 = {7.25 * a, 4.75 * a, 7.25 * a, 8.75 * a, 5 * a, 8.75 * a};
    public static final double[] FLASK_SMALL6 = {7.5 * a, 5 * a, 7.5 * a, 8.5 * a, 6.75 * a, 8.5 * a};
    public static final double[] FLASK_SMALL7 = {7.25 * a, 5.75 * a, 7.25 * a, 8.75 * a, 6 * a, 8.75 * a};
    public static final double[] ITEM_SIMPLE = FlaskShape.ITEM.simpleShape;
    public static final double[] LARGE_SIMPLE = FlaskShape.FLASK_LARGE.simpleShape;
    public static final double[] MEDIUM_SIMPLE = FlaskShape.FLASK_MEDIUM.simpleShape;
    public static final double[] SMALL_SIMPLE = FlaskShape.FLASK_SMALL.simpleShape;
    public static final double[] JAR_LARGE_SIMPLE = FlaskShape.JAR_LARGE.simpleShape;
    public static final double[] JAR_MEDIUM_SIMPLE = FlaskShape.JAR_MEDIUM.simpleShape;
    public static final double[] JAR_SMALL_SIMPLE = FlaskShape.JAR_SMALL.simpleShape;

    static {
        VoxelShape shapeJarLarge = Shapes.box(JAR_LARGE1[0], JAR_LARGE1[1], JAR_LARGE1[2], JAR_LARGE1[3], JAR_LARGE1[4], JAR_LARGE1[5]);
        shapeJarLarge = Shapes.or(shapeJarLarge, Shapes.box(JAR_LARGE2[0], JAR_LARGE2[1], JAR_LARGE2[2], JAR_LARGE2[3], JAR_LARGE2[4], JAR_LARGE2[5]));
        shapeJarLarge = Shapes.or(shapeJarLarge, Shapes.box(JAR_LARGE3[0], JAR_LARGE3[1], JAR_LARGE3[2], JAR_LARGE3[3], JAR_LARGE3[4], JAR_LARGE3[5]));
        shapeJarLarge = Shapes.or(shapeJarLarge, Shapes.box(JAR_LARGE4[0], JAR_LARGE4[1], JAR_LARGE4[2], JAR_LARGE4[3], JAR_LARGE4[4], JAR_LARGE4[5]));
        shapeJarLarge = Shapes.or(shapeJarLarge, Shapes.box(JAR_LARGE5[0], JAR_LARGE5[1], JAR_LARGE5[2], JAR_LARGE5[3], JAR_LARGE5[4], JAR_LARGE5[5]));
        shapeJarLarge = Shapes.or(shapeJarLarge, Shapes.box(JAR_LARGE6[0], JAR_LARGE6[1], JAR_LARGE6[2], JAR_LARGE6[3], JAR_LARGE6[4], JAR_LARGE6[5]));

        VoxelShape shapeJarMedium = Shapes.box(JAR_MEDIUM1[0], JAR_MEDIUM1[1], JAR_MEDIUM1[2], JAR_MEDIUM1[3], JAR_MEDIUM1[4], JAR_MEDIUM1[5]);
        shapeJarMedium = Shapes.or(shapeJarMedium, Shapes.box(JAR_MEDIUM2[0], JAR_MEDIUM2[1], JAR_MEDIUM2[2], JAR_MEDIUM2[3], JAR_MEDIUM2[4], JAR_MEDIUM2[5]));
        shapeJarMedium = Shapes.or(shapeJarMedium, Shapes.box(JAR_MEDIUM3[0], JAR_MEDIUM3[1], JAR_MEDIUM3[2], JAR_MEDIUM3[3], JAR_MEDIUM3[4], JAR_MEDIUM3[5]));
        shapeJarMedium = Shapes.or(shapeJarMedium, Shapes.box(JAR_MEDIUM4[0], JAR_MEDIUM4[1], JAR_MEDIUM4[2], JAR_MEDIUM4[3], JAR_MEDIUM4[4], JAR_MEDIUM4[5]));
        shapeJarMedium = Shapes.or(shapeJarMedium, Shapes.box(JAR_MEDIUM5[0], JAR_MEDIUM5[1], JAR_MEDIUM5[2], JAR_MEDIUM5[3], JAR_MEDIUM5[4], JAR_MEDIUM5[5]));
        shapeJarMedium = Shapes.or(shapeJarMedium, Shapes.box(JAR_MEDIUM6[0], JAR_MEDIUM6[1], JAR_MEDIUM6[2], JAR_MEDIUM6[3], JAR_MEDIUM6[4], JAR_MEDIUM6[5]));

        VoxelShape shapeJarSmall = Shapes.box(JAR_SMALL1[0], JAR_SMALL1[1], JAR_SMALL1[2], JAR_SMALL1[3], JAR_SMALL1[4], JAR_SMALL1[5]);
        shapeJarSmall = Shapes.or(shapeJarSmall, Shapes.box(JAR_SMALL2[0], JAR_SMALL2[1], JAR_SMALL2[2], JAR_SMALL2[3], JAR_SMALL2[4], JAR_SMALL2[5]));
        shapeJarSmall = Shapes.or(shapeJarSmall, Shapes.box(JAR_SMALL3[0], JAR_SMALL3[1], JAR_SMALL3[2], JAR_SMALL3[3], JAR_SMALL3[4], JAR_SMALL3[5]));
        shapeJarSmall = Shapes.or(shapeJarSmall, Shapes.box(JAR_SMALL4[0], JAR_SMALL4[1], JAR_SMALL4[2], JAR_SMALL4[3], JAR_SMALL4[4], JAR_SMALL4[5]));
        shapeJarSmall = Shapes.or(shapeJarSmall, Shapes.box(JAR_SMALL5[0], JAR_SMALL5[1], JAR_SMALL5[2], JAR_SMALL5[3], JAR_SMALL5[4], JAR_SMALL5[5]));
        shapeJarSmall = Shapes.or(shapeJarSmall, Shapes.box(JAR_SMALL6[0], JAR_SMALL6[1], JAR_SMALL6[2], JAR_SMALL6[3], JAR_SMALL6[4], JAR_SMALL6[5]));

        VoxelShape shapeFlaskLarge = Shapes.box(FLASK_LARGE1[0], FLASK_LARGE1[1], FLASK_LARGE1[2], FLASK_LARGE1[3], FLASK_LARGE1[4], FLASK_LARGE1[5]);
        shapeFlaskLarge = Shapes.or(shapeFlaskLarge, Shapes.box(FLASK_LARGE2[0], FLASK_LARGE2[1], FLASK_LARGE2[2], FLASK_LARGE2[3], FLASK_LARGE2[4], FLASK_LARGE2[5]));
        shapeFlaskLarge = Shapes.or(shapeFlaskLarge, Shapes.box(FLASK_LARGE3[0], FLASK_LARGE3[1], FLASK_LARGE3[2], FLASK_LARGE3[3], FLASK_LARGE3[4], FLASK_LARGE3[5]));
        shapeFlaskLarge = Shapes.or(shapeFlaskLarge, Shapes.box(FLASK_LARGE4[0], FLASK_LARGE4[1], FLASK_LARGE4[2], FLASK_LARGE4[3], FLASK_LARGE4[4], FLASK_LARGE4[5]));
        shapeFlaskLarge = Shapes.or(shapeFlaskLarge, Shapes.box(FLASK_LARGE5[0], FLASK_LARGE5[1], FLASK_LARGE5[2], FLASK_LARGE5[3], FLASK_LARGE5[4], FLASK_LARGE5[5]));
        shapeFlaskLarge = Shapes.or(shapeFlaskLarge, Shapes.box(FLASK_LARGE6[0], FLASK_LARGE6[1], FLASK_LARGE6[2], FLASK_LARGE6[3], FLASK_LARGE6[4], FLASK_LARGE6[5]));
        shapeFlaskLarge = Shapes.or(shapeFlaskLarge, Shapes.box(FLASK_LARGE7[0], FLASK_LARGE7[1], FLASK_LARGE7[2], FLASK_LARGE7[3], FLASK_LARGE7[4], FLASK_LARGE7[5]));

        VoxelShape shapeFlaskMedium = Shapes.box(FLASK_MEDIUM1[0], FLASK_MEDIUM1[1], FLASK_MEDIUM1[2], FLASK_MEDIUM1[3], FLASK_MEDIUM1[4], FLASK_MEDIUM1[5]);
        shapeFlaskMedium = Shapes.or(shapeFlaskMedium, Shapes.box(FLASK_MEDIUM2[0], FLASK_MEDIUM2[1], FLASK_MEDIUM2[2], FLASK_MEDIUM2[3], FLASK_MEDIUM2[4], FLASK_MEDIUM2[5]));
        shapeFlaskMedium = Shapes.or(shapeFlaskMedium, Shapes.box(FLASK_MEDIUM3[0], FLASK_MEDIUM3[1], FLASK_MEDIUM3[2], FLASK_MEDIUM3[3], FLASK_MEDIUM3[4], FLASK_MEDIUM3[5]));
        shapeFlaskMedium = Shapes.or(shapeFlaskMedium, Shapes.box(FLASK_MEDIUM4[0], FLASK_MEDIUM4[1], FLASK_MEDIUM4[2], FLASK_MEDIUM4[3], FLASK_MEDIUM4[4], FLASK_MEDIUM4[5]));
        shapeFlaskMedium = Shapes.or(shapeFlaskMedium, Shapes.box(FLASK_MEDIUM5[0], FLASK_MEDIUM5[1], FLASK_MEDIUM5[2], FLASK_MEDIUM5[3], FLASK_MEDIUM5[4], FLASK_MEDIUM5[5]));
        shapeFlaskMedium = Shapes.or(shapeFlaskMedium, Shapes.box(FLASK_MEDIUM6[0], FLASK_MEDIUM6[1], FLASK_MEDIUM6[2], FLASK_MEDIUM6[3], FLASK_MEDIUM6[4], FLASK_MEDIUM6[5]));
        shapeFlaskMedium = Shapes.or(shapeFlaskMedium, Shapes.box(FLASK_MEDIUM7[0], FLASK_MEDIUM7[1], FLASK_MEDIUM7[2], FLASK_MEDIUM7[3], FLASK_MEDIUM7[4], FLASK_MEDIUM7[5]));

        VoxelShape shapeFlaskSmall = Shapes.box(FLASK_SMALL1[0], FLASK_SMALL1[1], FLASK_SMALL1[2], FLASK_SMALL1[3], FLASK_SMALL1[4], FLASK_SMALL1[5]);
        shapeFlaskSmall = Shapes.or(shapeFlaskSmall, Shapes.box(FLASK_SMALL2[0], FLASK_SMALL2[1], FLASK_SMALL2[2], FLASK_SMALL2[3], FLASK_SMALL2[4], FLASK_SMALL2[5]));
        shapeFlaskSmall = Shapes.or(shapeFlaskSmall, Shapes.box(FLASK_SMALL3[0], FLASK_SMALL3[1], FLASK_SMALL3[2], FLASK_SMALL3[3], FLASK_SMALL3[4], FLASK_SMALL3[5]));
        shapeFlaskSmall = Shapes.or(shapeFlaskSmall, Shapes.box(FLASK_SMALL4[0], FLASK_SMALL4[1], FLASK_SMALL4[2], FLASK_SMALL4[3], FLASK_SMALL4[4], FLASK_SMALL4[5]));
        shapeFlaskSmall = Shapes.or(shapeFlaskSmall, Shapes.box(FLASK_SMALL5[0], FLASK_SMALL5[1], FLASK_SMALL5[2], FLASK_SMALL5[3], FLASK_SMALL5[4], FLASK_SMALL5[5]));
        shapeFlaskSmall = Shapes.or(shapeFlaskSmall, Shapes.box(FLASK_SMALL6[0], FLASK_SMALL6[1], FLASK_SMALL6[2], FLASK_SMALL6[3], FLASK_SMALL6[4], FLASK_SMALL6[5]));
        shapeFlaskSmall = Shapes.or(shapeFlaskSmall, Shapes.box(FLASK_SMALL7[0], FLASK_SMALL7[1], FLASK_SMALL7[2], FLASK_SMALL7[3], FLASK_SMALL7[4], FLASK_SMALL7[5]));

        SHAPE_JAR_LARGE = shapeJarLarge;
        SHAPE_JAR_MEDIUM = shapeJarMedium;
        SHAPE_JAR_SMALL = shapeJarSmall;
        SHAPE_FLASK_LARGE = shapeFlaskLarge;
        SHAPE_FLASK_MEDIUM = shapeFlaskMedium;
        SHAPE_FLASK_SMALL = shapeFlaskSmall;
        SHAPE_ITEM = Shapes.box(5.75 * a, 0, 5.75 * a, 10.25 * a, 7.25 * a, 10.25 * a);
    }

    public static final IntegerProperty COLOR = IntegerProperty.create("color", 0, 2); // 0 base, 1 wrong, 2 selected


    public final FlaskShape shape;

    public FlaskBlock(Properties properties, FlaskShape shape) {
        super(properties);
        this.shape = shape;
        sizeToBlock.put(shape, this);
        this.registerDefaultState(this.stateDefinition.any().setValue(COLOR, 0));
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return switch (shape) {
            case FLASK_SMALL -> SHAPE_FLASK_SMALL;
            case FLASK_MEDIUM -> SHAPE_FLASK_MEDIUM;
            case FLASK_LARGE -> SHAPE_FLASK_LARGE;
            case JAR_SMALL -> SHAPE_JAR_SMALL;
            case JAR_MEDIUM -> SHAPE_JAR_MEDIUM;
            case JAR_LARGE -> SHAPE_JAR_LARGE;
            case ITEM -> SHAPE_ITEM;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COLOR);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.getBlockEntity(pPos) instanceof FlaskBE be) {
            return be.tryFillFromItem(pLevel, pPos, pPlayer, pHand, pHit);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }


    @Override
    public @NotNull ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        if (true) return super.getCloneItemStack(pLevel, pPos, pState);
        ItemStack itemstack = super.getCloneItemStack(pLevel, pPos, pState);
        pLevel.getBlockEntity(pPos, BTVBlockEntities.FLASK_BE.get()).ifPresent((p_187446_) -> {
            p_187446_.saveToItem(itemstack);
        });
        return itemstack;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FlaskBE(pPos, pState);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        if (shape != FlaskShape.ITEM) {
            CompoundTag tag = BlockItem.getBlockEntityData(pStack);
            int contained = 0;
            Component displayName = null;
            if (tag != null) {
                contained = tag.getInt("Amount");
                if (tag.contains("FluidName", Tag.TAG_STRING)) {
                    ResourceLocation fluidName = new ResourceLocation(tag.getString("FluidName"));
                    Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidName);
                    if (fluid != null) {
                        displayName = new FluidStack(fluid, contained).getDisplayName();
                    }
                }
            }
            if (contained == 0) {
                pTooltip.add(Component.translatable("tooltip.flask.contained_empty", shape.capacity));
            } else if (displayName != null) {
                pTooltip.add(Component.translatable("tooltip.flask.contained", contained, shape.capacity, displayName));
            }
        }
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
        if (!pLevel.isClientSide && shape == FlaskShape.ITEM) {
            if (pLevel.getBlockEntity(pPos) instanceof FlaskBE be) {
                ItemStack stackInSlot = be.getStackHandler().getStackInSlot(0);
                if (!stackInSlot.isEmpty()) {
                    ItemEntity itementity = new ItemEntity(pLevel, (double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, stackInSlot);
                    itementity.setDefaultPickUpDelay();
                    pLevel.addFreshEntity(itementity);
                }
            }
        }
    }

    public enum FlaskShape {
        FLASK_SMALL(250, new double[]{6.75 * a, 0, 6.75 * a, 9.25 * a, 5.5 * a, 9.25 * a}, a / 4, 4.5 * a, 7 * a, 9 * a, false),
        FLASK_MEDIUM(700, new double[]{6 * a, 0, 6 * a, 10 * a, 10 * a, 10 * a}, a / 2, 6.5 * a, 6.5 * a, 9.5 * a, false),
        FLASK_LARGE(1500, new double[]{5.5 * a, 0, 5.5 * a, 13.25 * a, 10.5 * a, 13.25 * a}, a / 2, 8.5 * a, 6 * a, 10 * a, false),
        JAR_SMALL(300, new double[]{6.75 * a, 0, 6.75 * a, 9.25 * a, 5.5 * a, 9.25 * a}, a / 4, 4.5 * a, 7 * a, 9 * a, false),
        JAR_MEDIUM(750, new double[]{5.5 * a, 0, 5.5 * a, 10.5 * a, 8 * a, 10.5 * a}, a / 2, 6.5 * a, 6 * a, 10 * a, false),
        JAR_LARGE(2000, new double[]{4.5 * a, 0, 4.5 * a, 11.5 * a, 10.5 * a, 11.5 * a}, a / 2, 8.5 * a, 5 * a, 11 * a, false),
        ITEM(0, new double[]{5.5 * a, 0, 5.5 * a, 10.5 * a, 8 * a, 10.5 * a}, a / 2, 8.5 * a, 5 * a, 11 * a, true);

        private final int capacity;
        private final double[] simpleShape;
        private final double minRenderHeight;
        private final double maxRenderHeight;
        private final double minRenderHorizontal;
        private final double maxRenderHorizontal;
        private final boolean allowsItems;

        FlaskShape(int capacity, double[] simpleShape, double minRenderHeight, double maxRenderHeight, double minRenderHorizontal, double maxRenderHorizontal, boolean allowsItems) {
            this.capacity = capacity;
            this.simpleShape = simpleShape;
            this.minRenderHeight = minRenderHeight;
            this.maxRenderHeight = maxRenderHeight;
            this.minRenderHorizontal = minRenderHorizontal;
            this.maxRenderHorizontal = maxRenderHorizontal;
            this.allowsItems = allowsItems;
        }

        public double[] getSimpleShape() {
            return simpleShape;
        }

        public int getCapacity() {
            return capacity;
        }

        public double getMinRenderHeight() {
            return minRenderHeight;
        }

        public double getMaxRenderHeight() {
            return maxRenderHeight;
        }

        public double getMinRenderHorizontal() {
            return minRenderHorizontal;
        }

        public double getMaxRenderHorizontal() {
            return maxRenderHorizontal;
        }

        public boolean allowsItems() {
            return allowsItems;
        }
    }


}
