package net.hydroxa.drgbeer.block.custom;

import net.hydroxa.drgbeer.ModTags;
import net.hydroxa.drgbeer.block.entity.BarrelBlockEntity;
import net.hydroxa.drgbeer.inventory.BarrelInventory;
import net.hydroxa.drgbeer.mixin.BucketItemAccessor;
import net.hydroxa.drgbeer.state.property.ModProperties;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Random;

public class BarrelBlock extends BlockWithEntity implements Waterloggable, BlockEntityProvider {
    private static final DirectionProperty FACING = Properties.FACING;
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final BooleanProperty READY = ModProperties.READY;
    private static final BooleanProperty BREWING = ModProperties.BREWING;
    public BarrelBlock(Settings settings) {
        super(settings);
        setDefaultState(stateManager.getDefaultState().with(FACING, Direction.UP).with(READY, false).with(WATERLOGGED, false).with(BREWING, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED, FACING, READY, BREWING);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean bl = fluidState.getFluid() == Fluids.WATER;
        return Objects.requireNonNull(super.getPlacementState(ctx)).with(WATERLOGGED, bl).with(FACING, ctx.getSide()).with(READY, false);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BarrelBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        BarrelBlockEntity.tick(world, pos, state, (BarrelBlockEntity)world.getBlockEntity(pos));
    }

//    @Override
//    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
//        if (state.get(READY)) {
//            double x = (double) pos.getX() + 0.5;
//            double y = (double) pos.getY() + 1;
//            double z = (double) pos.getZ() + 0.5;
//            world.addParticle(ParticleTypes.ENTITY_EFFECT, x, y, z, world.random.nextFloat(-0.1f, 0.1f), 0.0, world.random.nextFloat(-0.1f, 0.1f));
//        } else if (state.get(BREWING)) {
//            double x = (double) pos.getX() + 0.5;
//            double y = (double) pos.getY() + 1;
//            double z = (double) pos.getZ() + 0.5;
//            BarrelBlockEntity be = (BarrelBlockEntity)world.getBlockEntity(pos);
//
//            world.addParticle(be.getEffect(), x, y, z, world.random.nextFloat(-0.1f, 0.1f), 0.0, world.random.nextFloat(-0.1f, 0.1f));
//        }
//    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(ModProperties.BREWING) ? 8 : (state.get(ModProperties.READY) ? 15 : 0);
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        if (world.isClient)
            if (!(player.getStackInHand(hand).isOf(Items.BUCKET) || player.getStackInHand(hand).isIn(ModTags.Items.SOLUTIONS))) //This stops liquids from being placed by the client
                return ActionResult.PASS;

        BarrelBlockEntity blockEntity = (BarrelBlockEntity) world.getBlockEntity(blockPos);

        if (!player.getStackInHand(hand).isEmpty()) { //Add new item
            ItemStack itemInHand = player.getStackInHand(hand);

            if (itemInHand.isIn(ModTags.Items.HOPS)) {
                for (int i = BarrelInventory.HOP_A_SLOT; i <= BarrelInventory.HOP_C_SLOT; i++) {
                    if (blockEntity.getStack(i).isEmpty()) {
                        ItemStack toAdd = itemInHand.copy();
                        toAdd.setCount(1);
                        blockEntity.setStack(i, toAdd);
                        blockEntity.checkValidRecipe(world, blockPos, blockState);

                        itemInHand.decrement(1);
                        return ActionResult.SUCCESS;
                    }
                }
            }

            if (itemInHand.isIn(ModTags.Items.CATALYSTS) && blockEntity.getCatalyst().isEmpty()) {
                ItemStack toAdd = itemInHand.copy();
                toAdd.setCount(1);
                blockEntity.setCatalyst(toAdd);
                blockEntity.checkValidRecipe(world, blockPos, blockState);

                itemInHand.decrement(1);

                return ActionResult.SUCCESS;
            } else if (itemInHand.isIn(ModTags.Items.SOLUTIONS) && blockEntity.getSolution().isEmpty()) {
                ItemStack toAdd = itemInHand.copy();
                if (!world.isClient) {
                    blockEntity.setStack(BarrelInventory.SOLUTION_SLOT, toAdd);
                    blockEntity.checkValidRecipe(world, blockPos, blockState);

                    itemInHand.decrement(1);

                    player.getInventory().offerOrDrop(new ItemStack(Items.BUCKET));
                }

                SoundEvent sound = SoundEvents.ITEM_BUCKET_EMPTY;
                if (toAdd.getItem() instanceof BucketItem bucketItem) {
                    ((BucketItemAccessor)bucketItem).invokePlayEmptyingSound(player, world, blockPos);

                    return ActionResult.SUCCESS; //Return early so to not play the default noise as well
                }  else if(toAdd.isOf(Items.POWDER_SNOW_BUCKET)) {
                    sound = SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW;
                }

                world.playSound(player, blockPos, sound, SoundCategory.BLOCKS,1, 1);

                return ActionResult.SUCCESS;
            } else if (itemInHand.isOf(Items.BUCKET) && !blockEntity.getSolution().isEmpty()) { //Remove solution with bucket
                ItemStack toGive = blockEntity.getSolution();

                if (world.isClient) {
                    SoundEvent sound = SoundEvents.ITEM_BUCKET_FILL;

                    if (toGive.getItem() instanceof BucketItem bucketItem)
                        sound = ((BucketItemAccessor) bucketItem).getFluid().getBucketFillSound().orElse(SoundEvents.ITEM_BUCKET_FILL);
                    else if (toGive.isOf(Items.POWDER_SNOW_BUCKET))
                        sound = SoundEvents.ITEM_BUCKET_FILL_POWDER_SNOW;

                    player.playSound(sound, SoundCategory.BLOCKS, 1, 1);
                } else {
                    itemInHand.decrement(1);
                    player.getInventory().offerOrDrop(toGive);
                    blockEntity.setSolution(ItemStack.EMPTY);
                    blockEntity.checkValidRecipe(world, blockPos, blockState);
                }

                return ActionResult.SUCCESS;
            }
        } else if (!world.isClient) {
            //TEMP CODE FOR TESTING PURPOSES : RIGHT CLICK TO REMOVE OUTPUT
            if (!blockEntity.getOutput().isEmpty())
            {
                world.playSound(player, blockPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS,1, 1);

                player.getInventory().offerOrDrop(blockEntity.getOutput());
                blockEntity.setOutput(ItemStack.EMPTY);
                blockEntity.checkValidRecipe(world, blockPos, blockState);

                world.setBlockState(blockPos, blockState.with(ModProperties.READY, false));

                return ActionResult.SUCCESS;
            }


            //This goes backwards so the catalyst and hops are extracted in order of insertion (though catalyst will be extracted first)
            for (int i = BarrelInventory.CATALYST_SLOT; i >= BarrelInventory.HOP_A_SLOT; i--) {
                if (!blockEntity.getStack(i).isEmpty()) {
                    player.getInventory().offerOrDrop(blockEntity.getStack(i));
                    blockEntity.removeStack(i);
                    blockEntity.checkValidRecipe(world, blockPos, blockState);

                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.PASS;
    }
}
