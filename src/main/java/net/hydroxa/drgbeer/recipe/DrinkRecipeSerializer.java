package net.hydroxa.drgbeer.recipe;

import com.google.gson.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

public class DrinkRecipeSerializer implements RecipeSerializer<DrinkRecipe> {
    private DrinkRecipeSerializer() {}

    public static final DrinkRecipeSerializer INSTANCE = new DrinkRecipeSerializer();
    public static final Identifier ID = new Identifier("drgbeer:drink_recipe");

    @Override
    public DrinkRecipe read(Identifier id, JsonObject json) {
        DrinkRecipe.DrinkRecipeJsonFormat recipeJson = new Gson().fromJson(json, DrinkRecipe.DrinkRecipeJsonFormat.class);
        DefaultedList<Ingredient> defaultedList = getHops(JsonHelper.getArray(json, "hops"));
        if (defaultedList.isEmpty())
            throw new JsonParseException("No hops for Drink Recipe");
        else if (defaultedList.size() > 3)
            throw new JsonParseException("Too many hops for Drink Recipe");

        Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.outputItem)).orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.outputItem));
        ItemStack output = new ItemStack(outputItem, 1);

        Ingredient HopA = defaultedList.get(0);
        Ingredient HopB = defaultedList.size() > 1 ? defaultedList.get(1) : null;
        Ingredient HopC = defaultedList.size() > 2 ? defaultedList.get(2) : null;

        Ingredient Catalyst = recipeJson.catalyst != null ? Ingredient.fromJson(recipeJson.catalyst) : null;
        Ingredient Solution = Ingredient.fromJson(recipeJson.solution);
        return new DrinkRecipe(id, output, HopA, HopB, HopC, Catalyst, Solution, recipeJson.time == 0 ? 400 : recipeJson.time);
    }

    private static DefaultedList<Ingredient> getHops(JsonArray json) {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        for (int i = 0; i < json.size(); ++i) {
            Ingredient ingredient = Ingredient.fromJson(json.get(i));
            if (ingredient.isEmpty()) continue;
            defaultedList.add(ingredient);
        }
        return defaultedList;
    }
    @Override
    public DrinkRecipe read(Identifier id, PacketByteBuf buf) {
        Ingredient HopA = Ingredient.fromPacket(buf);
        Ingredient Solution = Ingredient.fromPacket(buf);
        int Time = buf.readInt();
        ItemStack output = buf.readItemStack();
        boolean readHopB = buf.readBoolean();
        boolean readHopC = buf.readBoolean();
        boolean readCatalyst = buf.readBoolean();
        Ingredient HopB = readHopB ? Ingredient.fromPacket(buf) : null;
        Ingredient HopC = readHopC ? Ingredient.fromPacket(buf) : null;
        Ingredient HopCatalyst = readCatalyst ? Ingredient.fromPacket(buf) : null;

        return new DrinkRecipe(id, output, HopA, HopB, HopC, HopCatalyst, Solution, Time);
    }

    @Override
    public void write(PacketByteBuf buf, DrinkRecipe recipe) {
        boolean hasCatalyst = recipe.getCatalyst() != null;
        boolean hasHopC = recipe.getHopC() != null;
        boolean hasHopB = recipe.getHopB() != null;
        recipe.getHopA().write(buf);
        recipe.getSolution().write(buf);
        buf.writeInt(recipe.getTime());
        buf.writeItemStack(recipe.getOutput());
        buf.writeBoolean(hasHopB);
        buf.writeBoolean(hasHopC);
        buf.writeBoolean(hasCatalyst);
        if (hasHopB)
            recipe.getHopB().write(buf);
        if (hasHopC)
            recipe.getHopC().write(buf);
        if (hasCatalyst)
            recipe.getCatalyst().write(buf);
    }
}