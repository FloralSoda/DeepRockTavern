package net.hydroxa.drgbeer.block.entity;

import net.hydroxa.drgbeer.inventory.MugProvidingInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.NotImplementedException;

public class StorageBarrelBlockEntity extends BlockEntity implements MugProvidingInventory {
    public int DrinksRemaining = 0;
    public int DrinkCapacity;
    public Item ItemType;

    public StorageBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE_BARREL_BLOCK_ENTITY, pos, state);
        DrinkCapacity = 16;
    }
    public StorageBarrelBlockEntity(BlockPos pos, BlockState state, int drinkCapacity) {
        super(ModBlockEntities.STORAGE_BARREL_BLOCK_ENTITY, pos, state);
        DrinkCapacity = drinkCapacity;
    }

    @Override
    public ItemStack peekMug() {
        if (DrinksRemaining > 0)
            return new ItemStack(ItemType);
        else
            return ItemStack.EMPTY;
    }

    @Override
    public ItemStack popMug() {
        if (DrinksRemaining > 0) {
            DrinksRemaining--;
            return new ItemStack(ItemType);
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public boolean addMug(ItemStack mug) {
        if (ItemType == mug.getItem() && DrinksRemaining < DrinkCapacity) {
            DrinksRemaining++;
            return true;
        } else if (DrinksRemaining == 0) {
            DrinksRemaining = 1;
            ItemType = mug.getItem();
        }
        return false;
    }

    @Override
    public boolean hasMug() {
        return DrinksRemaining > 0;
    }



    @Override
    public int size() {
        return DrinkCapacity;
    }

    @Override
    public boolean isEmpty() {
        return DrinksRemaining == 0;
    }

    @Override
    @Deprecated
    public ItemStack getStack(int slot) {
        if (slot > DrinkCapacity)
            throw new IndexOutOfBoundsException();
        return peekMug();
    }

    @Override
    @Deprecated
    public ItemStack removeStack(int slot, int amount) {
        if (slot > DrinkCapacity)
            throw new IndexOutOfBoundsException();
        else if (amount > DrinksRemaining) {
            ItemStack output = new ItemStack(ItemType, DrinksRemaining);
            DrinksRemaining = 0;
            return output;
        }
        DrinksRemaining -= amount;
        return new ItemStack(ItemType, amount);
    }

    @Override
    @Deprecated
    public ItemStack removeStack(int slot) {
        if (slot > DrinkCapacity)
            throw new IndexOutOfBoundsException();
        if (slot > DrinksRemaining)
            return ItemStack.EMPTY;

        DrinksRemaining--;
        return new ItemStack(ItemType);
    }

    @Override
    @Deprecated
    public void setStack(int slot, ItemStack stack) {
        throw new NotImplementedException();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {
        DrinksRemaining = 0;
        ItemType = null;
    }
}
