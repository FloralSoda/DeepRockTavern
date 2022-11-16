package net.hydroxa.drgbeer.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.hydroxa.drgbeer.world.feature.ModPlacedFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;

public class ModFlowerGeneration {
    public static void generateFlowers() {
        BiomeModifications.addFeature(BiomeSelectors.categories(Biome.Category.UNDERGROUND),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.BOOLO_PLACED.getKey().get());
    }
}
