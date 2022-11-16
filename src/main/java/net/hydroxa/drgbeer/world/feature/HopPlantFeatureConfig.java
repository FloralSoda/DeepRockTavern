package net.hydroxa.drgbeer.world.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryCodecs;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HopPlantFeatureConfig implements FeatureConfig {
    public final int searchRange;
    public final boolean placeOnFloor;
    public final boolean placeOnCeiling;
    public final boolean placeOnWalls;
    public final RegistryEntryList<Block> canPlaceOn;
    public final List<Direction> directions;
    public final RegistryEntryList<Block> blocks;

    public static Codec<HopPlantFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance ->
                    instance.group(
                                    Codec.intRange(1,64).fieldOf("searchRange").forGetter(config -> config.searchRange),
                                    Codec.BOOL.fieldOf("placeOnFloor").forGetter(config -> config.placeOnFloor),
                                    Codec.BOOL.fieldOf("placeOnCeiling").forGetter(config -> config.placeOnCeiling),
                                    Codec.BOOL.fieldOf("placeOnWalls").forGetter(config -> config.placeOnWalls),
                                    RegistryCodecs.entryList(Registry.BLOCK_KEY).fieldOf("canPlaceOn").forGetter(config -> config.canPlaceOn),
                                    RegistryCodecs.entryList(Registry.BLOCK_KEY).fieldOf("block").forGetter(config -> config.blocks))
                            .apply(instance, HopPlantFeatureConfig::new));

    public HopPlantFeatureConfig(int searchRange, boolean placeOnFloor, boolean placeOnCeiling, boolean placeOnWalls, RegistryEntryList<Block> canPlaceOn, RegistryEntryList<Block> block) {
        this.searchRange = searchRange;
        this.placeOnFloor = placeOnFloor;
        this.placeOnCeiling = placeOnCeiling;
        this.placeOnWalls = placeOnWalls;
        this.canPlaceOn = canPlaceOn;
        this.blocks = block;
        ArrayList<Direction> list = Lists.newArrayList();
        if (placeOnCeiling) {
            list.add(Direction.UP);
        }
        if (placeOnFloor) {
            list.add(Direction.DOWN);
        }
        if (placeOnWalls) {
            Direction.Type.HORIZONTAL.forEach(list::add);
        }
        this.directions = Collections.unmodifiableList(list);
    }
}
