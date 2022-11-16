package net.hydroxa.drgbeer.world.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.hydroxa.drgbeer.block.custom.HopPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class HopPlantFeature extends Feature<HopPlantFeatureConfig> {
    public HopPlantFeature(Codec<HopPlantFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<HopPlantFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();
        HopPlantFeatureConfig hopPlantFeatureConfig = context.getConfig();
        if (!HopPlantFeature.isAirOrWater(structureWorldAccess.getBlockState(blockPos))) {
            return false;
        }
        List<Direction> directions = HopPlantFeature.shuffleDirections(hopPlantFeatureConfig, random);
        if (HopPlantFeature.generate(structureWorldAccess, blockPos, structureWorldAccess.getBlockState(blockPos), hopPlantFeatureConfig, random, directions)) {
            return true;
        }
        BlockPos.Mutable mutable = blockPos.mutableCopy();
        for (Direction direction : directions) {
            mutable.set(blockPos);
            List<Direction> validDirections = HopPlantFeature.shuffleDirections(hopPlantFeatureConfig, random, direction.getOpposite());
            for (int i = 0; i < hopPlantFeatureConfig.searchRange; ++i) {
                mutable.set(blockPos, direction);
                BlockState blockState = structureWorldAccess.getBlockState(mutable);
                if (!HopPlantFeature.isAirOrWater(blockState))
                    continue;
                if (!HopPlantFeature.generate(structureWorldAccess, mutable, blockState, hopPlantFeatureConfig, random, validDirections))
                    continue;
                return true;
            }
        }
        return false;
    }

    public static boolean generate(StructureWorldAccess world, BlockPos pos, BlockState state, HopPlantFeatureConfig config, Random random, List<Direction> directions) {
        BlockPos.Mutable mutable = pos.mutableCopy();
        for (Direction direction : directions) {
            BlockState blockState = world.getBlockState(mutable.set(pos, direction));
            if (!blockState.isIn(config.canPlaceOn)) continue;
            HopPlantBlock hopPlantBlock = (HopPlantBlock)config.blocks.getRandom(random).orElse(config.blocks.get(0)).value();
            BlockState blockState2 = hopPlantBlock.withDirection(state, world, pos, direction);
            if (blockState2 == null) {
                return false;
            }
            world.setBlockState(pos, blockState2, Block.NOTIFY_ALL);
            world.getChunk(pos).markBlockForPostProcessing(pos);
            return true;
        }
        return false;
    }

    public static List<Direction> shuffleDirections(HopPlantFeatureConfig config, Random random) {
        ArrayList<Direction> list = Lists.newArrayList(config.directions);
        Collections.shuffle(list, random);
        return list;
    }

    public static List<Direction> shuffleDirections(HopPlantFeatureConfig config, Random random, Direction excluded) {
        List<Direction> list = config.directions.stream().filter(direction -> direction != excluded).collect(Collectors.toList());
        Collections.shuffle(list, random);
        return list;
    }

    private static boolean isAirOrWater(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER);
    }
}
