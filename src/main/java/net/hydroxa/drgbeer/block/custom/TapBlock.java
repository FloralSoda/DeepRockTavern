package net.hydroxa.drgbeer.block.custom;

import net.hydroxa.drgbeer.block.ModBlocks;
import net.hydroxa.drgbeer.inventory.MugProvidingInventory;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TapBlock extends WallMountedBlock implements Waterloggable {
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    protected static final VoxelShape NORTH_WALL_SHAPE = Block.createCuboidShape(7.0, 4.5, 8.5, 9.0, 9.0, 16.0);
    protected static final VoxelShape SOUTH_WALL_SHAPE = Block.createCuboidShape(7.0, 4.5, 0.0, 9.0, 9.0, 7.5);
    protected static final VoxelShape WEST_WALL_SHAPE = Block.createCuboidShape(8.5, 4.5, 7.0, 16.0, 9.0, 9.0);
    protected static final VoxelShape EAST_WALL_SHAPE = Block.createCuboidShape(0.0, 4.5, 7.0, 7.5, 9.0, 9.0);
    protected static final VoxelShape FLOOR_NORTH_SHAPE = Block.createCuboidShape(7.0, 0.0, 0.0, 9.0, 14.5, 3.0);
    protected static final VoxelShape FLOOR_SOUTH_SHAPE = Block.createCuboidShape(7.0, 0.0, 13.0, 9.0, 14.5, 16.0);
    protected static final VoxelShape FLOOR_WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 7.0, 3.0, 14.5, 9.0);
    protected static final VoxelShape FLOOR_EAST_SHAPE = Block.createCuboidShape(13.0, 0.0, 7.0, 16.0, 14.5, 9.0);
    protected static final VoxelShape CEILING_NORTH_SHAPE = Block.createCuboidShape(7.0, 1.5, 0.0, 9.0, 16, 3.0);
    protected static final VoxelShape CEILING_SOUTH_SHAPE = Block.createCuboidShape(7.0, 1.5, 13.0, 9.0, 16, 16.0);
    protected static final VoxelShape CEILING_WEST_SHAPE = Block.createCuboidShape(0.0, 1.5, 7.0, 3.0, 16, 9.0);
    protected static final VoxelShape CEILING_EAST_SHAPE = Block.createCuboidShape(13.0, 1.5, 7.0, 16.0, 16, 9.0);

    public TapBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false).with(FACE, WallMountLocation.WALL));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACE)) {
            case FLOOR -> {
                switch (state.get(FACING)) {
                    case EAST -> {
                        return FLOOR_EAST_SHAPE;
                    }
                    case WEST -> {
                        return FLOOR_WEST_SHAPE;
                    }
                    case SOUTH -> {
                        return FLOOR_SOUTH_SHAPE;
                    }
                }
                return FLOOR_NORTH_SHAPE;
            }
            case WALL -> {
                switch (state.get(FACING)) {
                    case EAST -> {
                        return EAST_WALL_SHAPE;
                    }
                    case WEST -> {
                        return WEST_WALL_SHAPE;
                    }
                    case SOUTH -> {
                        return SOUTH_WALL_SHAPE;
                    }
                }
                return NORTH_WALL_SHAPE;
            }
        }
        switch (state.get(FACING)) {
            case EAST -> {
                return CEILING_EAST_SHAPE;
            }
            case WEST -> {
                return CEILING_WEST_SHAPE;
            }
            case SOUTH -> {
                return CEILING_SOUTH_SHAPE;
            }
        }
        return CEILING_NORTH_SHAPE;
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
        return Objects.requireNonNull(super.getPlacementState(ctx)).with(WATERLOGGED, bl);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACE, FACING, WATERLOGGED);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient || state.get(FACE) == WallMountLocation.CEILING)
            return ActionResult.PASS;

        BlockEntity be = world.getBlockEntity(pos.offset(getDirection(state).getOpposite()));
        if (!(be instanceof MugProvidingInventory inv))
            return ActionResult.PASS;

        if (inv.peekMug().isEmpty())
            return ActionResult.FAIL;

        BlockPos mugBelow = null;
        switch (state.get(FACE)) {
            case FLOOR -> mugBelow = pos.offset(state.get(FACING));
            case WALL -> mugBelow = pos.down();
        }
        if (mugBelow == null)
            return ActionResult.FAIL;

        BlockState mug = world.getBlockState(mugBelow);

        if (!(mug.isOf(ModBlocks.MUG_BLOCK.asBlock())))
            return ActionResult.FAIL;

        Direction mugDirection = mug.get(MugBlock.FACING);
        boolean mugHasWater = mug.get(Properties.WATERLOGGED);

        if (inv.hasMug())
            world.setBlockState(mugBelow, Block.getBlockFromItem(inv.popMug().getItem()).getDefaultState()
                    .with(MugBlock.FACING, mugDirection)
                    .with(Properties.WATERLOGGED, mugHasWater));

        return ActionResult.SUCCESS;
    }
}
