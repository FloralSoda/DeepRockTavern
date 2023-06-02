package net.hydroxa.drgbeer.block.entity;

import net.hydroxa.drgbeer.DRGBeerMod;
import net.hydroxa.drgbeer.inventory.BarrelInventory;
import net.hydroxa.drgbeer.recipe.DrinkRecipe;
import net.hydroxa.drgbeer.state.property.ModProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BarrelBlockEntity extends BlockEntity implements BarrelInventory, SidedInventory {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(BarrelInventory.INVENTORY_SIZE, ItemStack.EMPTY);
    public int timeLeft = 0;
    private ItemStack output = ItemStack.EMPTY;
    private boolean hasRecipe = false;

    public BarrelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BARREL_BLOCK_ENTITY, pos, state);
    }
    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        Inventories.readNbt(nbt, items);
        timeLeft = nbt.getInt("timeLeft");
        hasRecipe = nbt.getBoolean("hasRecipe");
        output = ItemStack.fromNbt(nbt);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, items);
        nbt.putInt("timeLeft", timeLeft);
        nbt.putBoolean("hasRecipe", hasRecipe);
        output.writeNbt(nbt);

        super.writeNbt(nbt);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] result = new int[getItems().size() - 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }

        return result;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        switch (dir == null ? Direction.UP : dir ) { //Change logic - Top is Solution, North is HopA, East is HopB, South is HopC, West is Catalyst
            case UP -> {
                ItemStack s = getItems().get(slot);
                return s.isEmpty() || s.isOf(stack.getItem());
            }
            default -> {
                return false;
            }
        }
    }

    public boolean checkValidRecipe(World world, BlockPos pos, BlockState state) {
        if (!getHopA().isEmpty() && !getSolution().isEmpty()) {
            Optional<DrinkRecipe> match = world.getRecipeManager()
                    .getFirstMatch(DrinkRecipe.Type.INSTANCE, this, world);

            if (match.isPresent()) {
                //Recipe matched, load the output and the time
                timeLeft = match.get().getTime();
                output = match.get().getOutput();
                output.setCount(4);

                world.setBlockState(pos, state.with(ModProperties.BREWING, true));

                return true;
            } else {
                output = ItemStack.EMPTY;
                timeLeft = 0;
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    public static void tick(World world, BlockPos pos, BlockState state, BarrelBlockEntity be) {
        be.timeLeft--;

        if (be.timeLeft == 0 && !state.get(ModProperties.READY)) {
            world.setBlockState(pos, state.with(ModProperties.READY, true).with(ModProperties.BREWING, false));

            for (int i = BarrelInventory.HOP_A_SLOT; i <= BarrelInventory.OUTPUT_SLOT; i++)
                be.setStack(i, ItemStack.EMPTY);
            be.setOutput(be.output);

            DRGBeerMod.LOGGER.info("{}",be.getOutput().isEmpty());
        }
    }
}
