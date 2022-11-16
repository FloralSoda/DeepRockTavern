package net.hydroxa.drgbeer.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.hydroxa.drgbeer.DRGBeerMod;
import net.hydroxa.drgbeer.block.ModBlocks;
import net.hydroxa.drgbeer.block.custom.MugBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {
    public static final BlockEntityType<MugBlockEntity> MUG_BLOCK_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(DRGBeerMod.MOD_ID, "mug_block_entity"),
            FabricBlockEntityTypeBuilder.create(MugBlockEntity::new, ModBlocks.MUG_BLOCK.asBlock()).build());

    public static void registerBlockEntities() {
        DRGBeerMod.LOGGER.info("Registering block entities");
    }
}
