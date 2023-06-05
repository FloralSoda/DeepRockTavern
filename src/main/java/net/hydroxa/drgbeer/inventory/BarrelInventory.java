package net.hydroxa.drgbeer.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Contract;

public interface BarrelInventory extends MugProvidingInventory {
    DefaultedList<ItemStack> getItems();
    int HOP_A_SLOT = 0;
    int HOP_B_SLOT = 1;
    int HOP_C_SLOT = 2;
    int CATALYST_SLOT = 3;
    int SOLUTION_SLOT = 4;
    int OUTPUT_SLOT = 5;

    int INVENTORY_SIZE = 6;

    @Contract(pure = true)
    default ItemStack getHopA() {
        return getStack(HOP_A_SLOT);
    }
    @Contract(pure = true)
    default ItemStack getHopB() {
        return getStack(HOP_B_SLOT);
    }
    @Contract(pure = true)
    default ItemStack getHopC() {
        return getStack(HOP_C_SLOT);
    }
    @Contract(pure = true)
    default ItemStack getCatalyst() {
        return getStack(CATALYST_SLOT);
    }
    @Contract(pure = true)
    default ItemStack getSolution() {
        return getStack(SOLUTION_SLOT);
    }
    @Contract(pure = true)
    default ItemStack peekMug() {
        return getStack(OUTPUT_SLOT);
    }
    @Contract()
    default ItemStack popMug() {
        ItemStack peek = peekMug();
        ItemStack output = peek.copy();
        peek.decrement(1);

        return output;
    }
    @Contract(pure = true)
    default boolean hasMug() {
        return !peekMug().isEmpty();
    }

    default void setHopA(ItemStack stack) {
        setStack(HOP_A_SLOT, stack);
    }
    default void setHopB(ItemStack stack) {
        setStack(HOP_B_SLOT, stack);
    }
    default void setHopC(ItemStack stack) {
        setStack(HOP_C_SLOT, stack);
    }
    default void setCatalyst(ItemStack stack) {
        setStack(CATALYST_SLOT, stack);
    }
    default void setSolution(ItemStack stack) {
        setStack(SOLUTION_SLOT, stack);
    }
    default void setOutput(ItemStack stack) {
        setStack(OUTPUT_SLOT, stack);
    }

    @Override
    @Contract(pure = true)
    default int size() {
        return getItems().size();
    }

    @Override
    @Contract(pure = true)
    default boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            ItemStack stack = getStack(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Contract(pure = true)

    default ItemStack getStack(int slot) {
        return getItems().get(slot);
    }
    @Override
    default ItemStack removeStack(int slot, int count) {
        ItemStack result = Inventories.splitStack(getItems(), slot, count);
        if (!result.isEmpty()) {
            markDirty();
        }
        return result;
    }

    @Override
    default ItemStack removeStack(int slot) {
        return Inventories.removeStack(getItems(), slot);
    }

    @Override
    default void setStack(int slot, ItemStack stack) {
        getItems().set(slot, stack);
        if (stack.getCount() > stack.getMaxCount()) {
            stack.setCount(stack.getMaxCount());
        }
    }

    @Override
    default void clear() {
        getItems().clear();
    }

    @Override
    @Contract(pure = true)
    default boolean canPlayerUse(PlayerEntity player) {
        return false;
    }
}
