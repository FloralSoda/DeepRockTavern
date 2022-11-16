package net.hydroxa.drgbeer.mixin;

import net.hydroxa.drgbeer.effect.FortuneEffect;
import net.hydroxa.drgbeer.effect.ModEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;

@Mixin(Block.class)
public class BlockMixin {
//    @Inject(method="onBreak(Lnet.minecraft.world.World;Lnet.minecraft.util.math.BlockPos;Lnet.minecraft.block.BlockState;Lnet.minecraft.entity.player.PlayerEntity;)", at = @At("TAIL"))
//    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo cbi) {
//        if (player.hasStatusEffect(ModEffects.FORTUNE) && !world.isClient()) {
//            java.util.List<ItemStack> dropList =  Collections.emptyList();
//
//            LootContext.Builder builder = new LootContext.Builder((ServerWorld) world).random(world.random).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos)).parameter(LootContextParameters.TOOL, ItemStack.EMPTY);
//            Identifier identifier = FortuneEffect.LOOT_TABLE;
//
//            if (identifier != LootTables.EMPTY) {
//                LootContext lootContext = builder.parameter(LootContextParameters.BLOCK_STATE, state).build(LootContextTypes.BLOCK);
//                ServerWorld serverWorld = lootContext.getWorld();
//                LootTable lootTable = serverWorld.getServer().getLootManager().getTable(identifier);
//                dropList = lootTable.generateLoot(lootContext);
//            }
//
//            Block.dropStacks(world, pos, dropList);
//        }
//    }
}
