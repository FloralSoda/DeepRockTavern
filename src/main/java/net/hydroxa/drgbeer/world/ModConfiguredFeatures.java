package net.hydroxa.drgbeer.world;

import net.hydroxa.drgbeer.DRGBeerMod;
import net.hydroxa.drgbeer.block.ModBlocks;
import net.hydroxa.drgbeer.world.feature.HopPlantFeature;
import net.hydroxa.drgbeer.world.feature.HopPlantFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.feature.*;

public class ModConfiguredFeatures {

    public static final Feature<HopPlantFeatureConfig> HOP_PLANT_FEATURE = Registry.register(Registry.FEATURE, "hop_plant", new HopPlantFeature(HopPlantFeatureConfig.CODEC));
    public static final RegistryEntry<ConfiguredFeature<HopPlantFeatureConfig, ?>> HOP_PLANT =
            ConfiguredFeatures.register("hop_plant", HOP_PLANT_FEATURE,
                new HopPlantFeatureConfig(20, true, true, true, RegistryEntryList.of(Block::getRegistryEntry, Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.DRIPSTONE_BLOCK, Blocks.CALCITE, Blocks.TUFF, Blocks.DEEPSLATE, Blocks.MOSS_BLOCK, Blocks.OAK_LOG), RegistryEntryList.of(Block::getRegistryEntry, ModBlocks.BOOLO_CAP.asBlock(), ModBlocks.BARLEY_BULB.asBlock(), ModBlocks.MALT_STAR.asBlock(), ModBlocks.YEAST_CONE.asBlock(), ModBlocks.STARCH_NUT.asBlock())));

    public static void registerConfiguredFeatures() {
        DRGBeerMod.LOGGER.info("Registering Configured Features");
    }
}
