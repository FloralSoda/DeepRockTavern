package net.hydroxa.drgbeer.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.hydroxa.drgbeer.DRGBeerItemGroups;
import net.hydroxa.drgbeer.DRGBeerMod;
import net.hydroxa.drgbeer.block.ModBlocks;
import net.hydroxa.drgbeer.effect.ModEffects;
import net.hydroxa.drgbeer.item.custom.MilkMugItem;
import net.hydroxa.drgbeer.item.custom.MugItem;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public enum ModItems {
    MUG("mug", new MugItem(
            new FabricItemSettings().
                    group(DRGBeerItemGroups.DRINKS).
                    maxCount(4),
            ModBlocks.MUG_BLOCK.asBlock(),
            null,
            0,
            0,
            0
    )),
    WATER_MUG("water_mug", new MugItem(
            new FabricItemSettings().
                group(DRGBeerItemGroups.DRINKS).
                maxCount(4),
            ModBlocks.WATER_MUG.asBlock(),
            ModEffects.WATER,
            0,
            0,
            0
    )),
    HASTE_MUG("haste_mug", new MugItem(
            new FabricItemSettings().
                    group(DRGBeerItemGroups.DRINKS).
                    maxCount(4),
            ModBlocks.HASTE_MUG.asBlock(),
            StatusEffects.HASTE,
            36000,
            0,
            15
    )),
    STRENGTH_MUG("strength_mug", new MugItem(
            new FabricItemSettings().
                    group(DRGBeerItemGroups.DRINKS).
                    maxCount(4),
            ModBlocks.STRENGTH_MUG.asBlock(),
            StatusEffects.STRENGTH,
            36000,
            1,
            20
    )),
    HEALTH_BOOST_MUG("health_boost_mug", new MugItem(
            new FabricItemSettings().
                    group(DRGBeerItemGroups.DRINKS).
                    maxCount(4),
            ModBlocks.HEALTH_BOOST_MUG.asBlock(),
            StatusEffects.HEALTH_BOOST,
            36000,
            3,
            12
    )),
    MILK_MUG("milk_mug", new MilkMugItem(
            new FabricItemSettings().
                group(DRGBeerItemGroups.DRINKS).
                maxCount(4)
    )),
    FREEZE_MUG("freeze_mug", new MugItem(
            new FabricItemSettings().
                    group(DRGBeerItemGroups.DRINKS).
                    maxCount(4),
            ModBlocks.FREEZE_MUG.asBlock(),
            ModEffects.FREEZE,
            600,
            0,
            10
    )),
    NO_RAIN_MUG("no_rain_mug", new MugItem(
            new FabricItemSettings().
                    group(DRGBeerItemGroups.DRINKS).
                    maxCount(4),
            ModBlocks.NO_RAIN_MUG.asBlock(),
            ModEffects.NO_RAIN,
            200,
            0,
            41
    )),
    BLACKOUT_MUG("blackout_mug", new MugItem(
            new FabricItemSettings().
                    group(DRGBeerItemGroups.DRINKS).
                    maxCount(4),
            ModBlocks.BLACKOUT_MUG.asBlock(),
            ModEffects.WATER,
            0,
            0,
            100
    )),
    STRONG_TIPSY_MUG("strong_tipsy_mug", new MugItem(
            new FabricItemSettings().
                    group(DRGBeerItemGroups.DRINKS).
                    maxCount(4),
            ModBlocks.STRONG_TIPSY_MUG.asBlock(),
            ModEffects.WATER,
            0,
            0,
            20
    )),
    SFW_TIPSY_MUG("sfw_tipsy_mug", new MugItem(
            new FabricItemSettings().
                    group(DRGBeerItemGroups.DRINKS).
                    maxCount(4),
            ModBlocks.SFW_TIPSY_MUG.asBlock(),
            ModEffects.WATER,
            0,
            0,
            12
    )),
    EPIC_TIPSY_MUG("epic_tipsy_mug", new MugItem(
            new FabricItemSettings().
                    group(DRGBeerItemGroups.DRINKS).
                    maxCount(4),
            ModBlocks.EPIC_TIPSY_MUG.asBlock(),
            ModEffects.WATER,
            0,
            0,
            41
    )),
    FARTY_MUG("farty_mug", new MugItem(
            new FabricItemSettings().
                    group(DRGBeerItemGroups.DRINKS).
                    maxCount(4),
            ModBlocks.FARTY_MUG.asBlock(),
            ModEffects.FARTY,
            600,
            0,
            20
    )),
    LUCK_MUG("luck_mug", new MugItem(
            new FabricItemSettings().
                    group(DRGBeerItemGroups.DRINKS).
                    maxCount(4),
            ModBlocks.LUCK_MUG.asBlock(),
            StatusEffects.LUCK,
            200,
            5,
            20
    )),
    POISON_MUG("poison_mug", new MugItem(
            new FabricItemSettings().
                    group(DRGBeerItemGroups.DRINKS).
                    maxCount(4),
            ModBlocks.POISON_MUG.asBlock(),
            StatusEffects.POISON,
            100,
            5,
            20
    )),
    FORTUNE_MUG("fortune_mug", new MugItem(
            new FabricItemSettings().
                    group(DRGBeerItemGroups.DRINKS).
                    maxCount(4),
            ModBlocks.FORTUNE_MUG.asBlock(),
            ModEffects.FORTUNE,
            36000,
            0,
            16
    )),
    CHORUS_MUG("chorus_mug", new MugItem(
            new FabricItemSettings().
                    group(DRGBeerItemGroups.DRINKS).
                    maxCount(4),
            ModBlocks.CHORUS_MUG.asBlock(),
            ModEffects.WORMHOLE,
            10,
            0,
            25
    ));

    public static void registerItems() {
        DRGBeerMod.LOGGER.info("Loading items");
    }

    public final Item Item;
    ModItems(String name) {
        Item = registerItem(name, DRGBeerItemGroups.GENERIC);
    }
    ModItems(String name, ItemGroup group) {
        DRGBeerMod.LOGGER.info("generating " + name);
        Item = registerItem(name, new FabricItemSettings().group(group));
    }
    ModItems(String name, FabricItemSettings settings) {
        Item = registerItem(name, new Item(settings));
    }
    ModItems(String name, Item item) {
        Item = registerItem(name, item);
    }

    public static Item registerItem(String name, ItemGroup group) {
        return registerItem(name, new FabricItemSettings().group(group));
    }
    public static Item registerItem(String name, FabricItemSettings settings) {
        return registerItem(name, new Item(settings));
    }
    public static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(DRGBeerMod.MOD_ID, name), item);
    }
}
