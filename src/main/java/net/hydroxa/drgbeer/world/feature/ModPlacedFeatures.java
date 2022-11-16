package net.hydroxa.drgbeer.world.feature;

import net.hydroxa.drgbeer.world.ModConfiguredFeatures;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

public class ModPlacedFeatures {
    public static final RegistryEntry<PlacedFeature> BOOLO_PLACED = PlacedFeatures.register("boolo_placed",
            ModConfiguredFeatures.BOOLO_CAP, CountPlacementModifier.of(UniformIntProvider.create(104, 157)), SquarePlacementModifier.of(),
            PlacedFeatures.BOTTOM_TO_120_RANGE, SurfaceThresholdFilterPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG, Integer.MIN_VALUE, 0), BiomePlacementModifier.of());
}
