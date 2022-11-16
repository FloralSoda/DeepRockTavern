package net.hydroxa.drgbeer;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.hydroxa.drgbeer.block.ModBlocks;
import net.hydroxa.drgbeer.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class DRGBeerItemGroups {
    public static final ItemGroup GENERIC =
            registerItemGroup("generic", () -> new ItemStack(ModBlocks.BOOLO_CAP.asItem()));
    public static final ItemGroup DRINKS =
            registerItemGroup("drinks", () -> new ItemStack(ModItems.MUG.Item));

    public static ItemGroup registerItemGroup(String name) {
        return registerItemGroup(name, Items.STONE);
    }
    public static ItemGroup registerItemGroup(String name, Item icon) {
        return registerItemGroup(name, () -> new ItemStack(icon));
    }
    public static ItemGroup registerItemGroup(String name, Supplier<ItemStack> icon) {
        return FabricItemGroupBuilder.build(new Identifier(DRGBeerMod.MOD_ID, name), icon);
    }

    public static void registerGroups() {
        DRGBeerMod.LOGGER.info("Loading item groups");
    }
}
