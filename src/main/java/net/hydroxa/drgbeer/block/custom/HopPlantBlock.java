package net.hydroxa.drgbeer.block.custom;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class HopPlantBlock extends Block implements Waterloggable {
    private static final DirectionProperty FACING = Properties.FACING;
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public HopPlantBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.UP));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED, FACING);
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
        return Objects.requireNonNull(super.getPlacementState(ctx)).with(WATERLOGGED, bl).with(FACING, ctx.getSide());
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(FACING);
        BlockPos blockPos = pos.offset(direction.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, direction);
    }

    public BlockState withDirection(BlockState state, StructureWorldAccess world, BlockPos pos, Direction direction) {
        BlockState blockState = isWaterlogged() && state.getFluidState().isEqualAndStill(Fluids.WATER) ? getDefaultState().with(Properties.WATERLOGGED, true) : getDefaultState();
        BlockPos blockPos = pos.offset(direction);
        if (canPlaceAt(world, direction, blockPos, world.getBlockState(blockPos))) {
            return blockState.with(FACING, direction.getOpposite());
        }
        return null;
    }

    private boolean canPlaceAt(StructureWorldAccess world, Direction direction, BlockPos blockPos, BlockState blockState) {
        return Block.isFaceFullSquare(blockState.getCollisionShape(world, blockPos), direction.getOpposite());
    }

    private boolean isWaterlogged() {
        return this.stateManager.getProperties().contains(Properties.WATERLOGGED);
    }
}
