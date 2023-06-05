package net.hydroxa.drgbeer.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public interface MugProvidingInventory extends Inventory {
    ItemStack peekMug();
    ItemStack popMug();
    boolean addMug(ItemStack mug);
    boolean hasMug();
}
