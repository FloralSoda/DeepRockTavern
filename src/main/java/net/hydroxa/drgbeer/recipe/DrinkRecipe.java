package net.hydroxa.drgbeer.recipe;

import com.google.gson.*;
import net.hydroxa.drgbeer.inventory.BarrelInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

//Crafting Inventory is temporary until I made the barrel and faucet
public class DrinkRecipe implements Recipe<BarrelInventory> {
    private final Ingredient HopA;
    private final @Nullable Ingredient HopB;
    private final @Nullable Ingredient HopC;
    private final @Nullable Ingredient Catalyst;
    private final Ingredient Solution;
    private final int Time;
    private final ItemStack result;
    private final Identifier id;

    public DrinkRecipe(Identifier id, ItemStack result, Ingredient HopA, @Nullable Ingredient HopB, @Nullable Ingredient HopC, @Nullable Ingredient Catalyst, Ingredient Solution, int Time) {
        this.HopA = HopA;
        this.HopB = HopB;
        this.HopC = HopC;
        this.Catalyst = Catalyst;
        this.Solution = Solution;
        this.result = result;
        this.id = id;
        this.Time = Time;
    }

    public int getTime() {
        return Time;
    }
    public Ingredient getHopA() {
        return HopA;
    }
    public @Nullable Ingredient getHopB() {
        return HopB;
    }
    public @Nullable Ingredient getHopC() {
        return HopC;
    }
    public @Nullable Ingredient getCatalyst() {
        return Catalyst;
    }
    public Ingredient getSolution() {
        return Solution;
    }
    @Override
    public ItemStack getOutput() {
        return result;
    }
    @Override
    public Identifier getId() {
        return id;
    }

    private int checkSlots(BarrelInventory inventory, boolean[] toCheck, Ingredient checker) {
        for (int i = 0; i < toCheck.length; i++) {
            if (toCheck[i] && checker.test(inventory.getStack(i)))
                return i;
        }
        return -1;
    }

    @Override
    public boolean matches(BarrelInventory inventory, World world) {
        if (!Solution.test(inventory.getSolution()))
            return false;

        boolean[] hopsToCheck = {true, true, true};

        int hop = checkSlots(inventory, hopsToCheck, HopA);
        if (hop >= 0)
            hopsToCheck[hop] = false;
        else
            return false;
        if (HopB != null) {
            hop = checkSlots(inventory, hopsToCheck, HopB);
            if (hop >= 0)
                hopsToCheck[hop] = false;
            else
                return false;
        }
        if (HopC != null) {
            hop = checkSlots(inventory, hopsToCheck, HopC);
            if (hop >= 0)
                hopsToCheck[hop] = false;
            else
                return false;
        }

        return Catalyst == null || !Catalyst.test(inventory.getCatalyst());
    }

    @Override
    public ItemStack craft(BarrelInventory inventory) {
        return getOutput().copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DrinkRecipeSerializer.INSTANCE;
    }
    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
    public static class Type implements RecipeType<DrinkRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "drink_recipe";
    }
    class DrinkRecipeJsonFormat {
        JsonArray hops;
        @Nullable JsonObject catalyst;
        JsonObject solution;
        String outputItem;
        int time;
    }
}
