package net.hydroxa.drgbeer.block.custom;

import net.hydroxa.drgbeer.block.entity.StorageBarrelBlockEntity;
import net.hydroxa.drgbeer.item.ModItems;
import net.hydroxa.drgbeer.item.custom.MugItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StorageBarrelBlock extends BarrelBlock {
    public int MugStorageCap;

    public StorageBarrelBlock(Settings settings, int mugStorageCap) {
        super(settings);
        MugStorageCap = mugStorageCap;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StorageBarrelBlockEntity(pos, state, MugStorageCap);
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        if (world.isClient)
            return ActionResult.PASS;

        ItemStack toAdd = player.getStackInHand(hand);
        if (toAdd.getItem() instanceof MugItem)
        {
            StorageBarrelBlockEntity inventory = (StorageBarrelBlockEntity)world.getBlockEntity(blockPos);
            if (!inventory.hasMug() || inventory.peekMug().isItemEqual(toAdd)) {
                ItemStack inserted = toAdd.copy();
                inserted.setCount(1);
                if (inventory.addMug(inserted)) {
                    toAdd.decrement(1);
                    player.getInventory().offerOrDrop(new ItemStack(ModItems.MUG.Item));

                    world.playSound(player, blockPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1, 1);
                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.PASS;
    }
}
