package net.hydroxa.drgbeer;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> GIVES_FORTUNE = createLocalTag("gives_fortune");
        public static TagKey<Block> createLocalTag(String name) {
            return TagKey.of(Registry.BLOCK_KEY, new Identifier(DRGBeerMod.MOD_ID, name));
        }
        public static TagKey<Block> getCommonTag(String name) {
            return TagKey.of(Registry.BLOCK_KEY, new Identifier("c", name));
        }
    }

    public static class Items {
        public static final TagKey<Item> HOPS = createLocalTag("hops");
        public static final TagKey<Item> CATALYSTS = createLocalTag("catalysts");
        public static final TagKey<Item> SOLUTIONS = createLocalTag("solutions");
        public static TagKey<Item> createLocalTag(String name) {
            return TagKey.of(Registry.ITEM_KEY, new Identifier(DRGBeerMod.MOD_ID, name));
        }
        public static TagKey<Item> getCommonTag(String name) {
            return TagKey.of(Registry.ITEM_KEY, new Identifier("c", name));
        }
    }
}
