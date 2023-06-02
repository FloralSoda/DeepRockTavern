package net.hydroxa.drgbeer;

import net.fabricmc.api.ModInitializer;
import net.hydroxa.drgbeer.block.ModBlocks;
import net.hydroxa.drgbeer.block.entity.ModBlockEntities;
import net.hydroxa.drgbeer.effect.ModEffects;
import net.hydroxa.drgbeer.item.ModItems;
import net.hydroxa.drgbeer.recipe.DrinkRecipe;
import net.hydroxa.drgbeer.recipe.DrinkRecipeSerializer;
import net.hydroxa.drgbeer.sound.ModSounds;
import net.hydroxa.drgbeer.world.ModConfiguredFeatures;
import net.hydroxa.drgbeer.world.gen.ModWorldGen;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DRGBeerMod implements ModInitializer {
	public static final String MOD_ID = "drgbeer";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitialize() {
		ModEffects.registerEffects();
		ModSounds.registerSoundEvents();
		ModItems.registerItems();
		ModBlocks.registerBlocks();
		ModBlockEntities.registerBlockEntities();
		DRGBeerItemGroups.registerGroups();
		ModConfiguredFeatures.registerConfiguredFeatures();
		ModWorldGen.generateModWorldGen();
		Registry.register(Registry.RECIPE_SERIALIZER, DrinkRecipeSerializer.ID, DrinkRecipeSerializer.INSTANCE);
		Registry.register(Registry.RECIPE_TYPE, new Identifier(MOD_ID, DrinkRecipe.Type.ID), DrinkRecipe.Type.INSTANCE);
	}
}
