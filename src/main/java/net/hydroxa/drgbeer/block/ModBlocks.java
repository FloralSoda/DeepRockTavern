package net.hydroxa.drgbeer.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.hydroxa.drgbeer.DRGBeerItemGroups;
import net.hydroxa.drgbeer.DRGBeerMod;
import net.hydroxa.drgbeer.block.custom.HopPlantBlock;
import net.hydroxa.drgbeer.block.custom.MugBlock;
import net.hydroxa.drgbeer.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public enum ModBlocks {
    BOOLO_CAP("boolo_cap", DRGBeerItemGroups.GENERIC, new HopPlantBlock(
            FabricBlockSettings.
                    of(Material.PLANT).
                    luminance(3).
                    breakInstantly().
                    noCollision().
                    nonOpaque().
                    sounds(BlockSoundGroup.SLIME))),
    BARLEY_BULB("barley_bulb", DRGBeerItemGroups.GENERIC, new HopPlantBlock(
            FabricBlockSettings.
                    of(Material.PLANT).
                    luminance(3).
                    breakInstantly().
                    noCollision().
                    nonOpaque().
                    sounds(BlockSoundGroup.SLIME))),
    MALT_STAR("malt_star", DRGBeerItemGroups.GENERIC, new HopPlantBlock(
            FabricBlockSettings.
                    of(Material.PLANT).
                    luminance(3).
                    breakInstantly().
                    noCollision().
                    nonOpaque().
                    sounds(BlockSoundGroup.SLIME))),
    STARCH_NUT("starch_nut", DRGBeerItemGroups.GENERIC, new HopPlantBlock(
            FabricBlockSettings.
                    of(Material.PLANT).
                    luminance(3).
                    breakInstantly().
                    noCollision().
                    nonOpaque().
                    sounds(BlockSoundGroup.SLIME))),
    YEAST_CONE("yeast_cone", DRGBeerItemGroups.GENERIC, new HopPlantBlock(
            FabricBlockSettings.
                    of(Material.PLANT).
                    luminance(3).
                    breakInstantly().
                    noCollision().
                    nonOpaque().
                    sounds(BlockSoundGroup.SLIME))),
    MUG_BLOCK("mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            false
    )),
    BLACKOUT_MUG("blackout_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    CHORUS_MUG("chorus_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    DANCE_MUG("dance_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    ENFLAME_MUG("enflame_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    EPIC_TIPSY_MUG("epic_tipsy_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    FARTY_MUG("farty_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    FEATHER_FALL_MUG("feather_fall_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    FORTUNE_MUG("fortune_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    FREEZE_MUG("freeze_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    GROWING_MUG("growing_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    HASTE_MUG("haste_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    HEALTH_BOOST_MUG("health_boost_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    LOW_GRAVITY_MUG("low_gravity_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    LUCK_MUG("luck_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    MILK_MUG("milk_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    NO_RAIN_MUG("no_rain_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    EXPLOSIVE_MUG("explosive_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    PSYCHOSIS_MUG("psychosis_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    SFW_TIPSY_MUG("sfw_tipsy_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    SHRINKING_MUG("shrinking_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    SMART_MUG("smart_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    STRENGTH_MUG("strength_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    STRONG_TIPSY_MUG("strong_tipsy_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    WATER_MUG("water_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    )),
    WURST_MUG("wurst_mug_block", new MugBlock(
            FabricBlockSettings.
                    of(Material.METAL).
                    breakInstantly().
                    nonOpaque().
                    sounds(BlockSoundGroup.METAL),
            true
    ));


    public static void registerBlocks() {
        DRGBeerMod.LOGGER.info("Registering Blocks");
    }
    public final Block Block;
    public final Item Item;
    ModBlocks(String name, Block block) {
        Block = registerBlock(name, block);
        Item = null;
    }
    ModBlocks(String name, ItemGroup group, Block block) {
        Block = registerBlock(name, block);
        Item = registerBlockItem(name, block, group);
    }
    ModBlocks(String name, ItemGroup group, FabricBlockSettings settings) {
        this(name, group, new Block(settings));
    }
    ModBlocks(String name, Block block, Item customItem) {
        Block = registerBlock(name, block);
        Item = ModItems.registerItem(name, customItem);
    }

    public static Block registerBlock(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(DRGBeerMod.MOD_ID, name), block);
    }
    public static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return ModItems.registerItem(name, new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public Item asItem() {
        return Item;
    }

    public Block asBlock() {
        return Block;
    }
}
