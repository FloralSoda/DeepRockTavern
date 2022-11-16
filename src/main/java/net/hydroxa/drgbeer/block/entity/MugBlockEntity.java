package net.hydroxa.drgbeer.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class MugBlockEntity extends BlockEntity {
    public static final String POTION_EFFECTS_KEY = "PotionEffects";
    public Collection<StatusEffectInstance> potions;
    public MugBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MUG_BLOCK_ENTITY, pos, state);
        potions = new ArrayList<>();
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        NbtList nbtList = nbt.getList(POTION_EFFECTS_KEY, 9);
        for (StatusEffectInstance statusEffectInstance : potions) {
            nbtList.add(statusEffectInstance.writeNbt(new NbtCompound()));
        }
        nbt.put(POTION_EFFECTS_KEY, nbtList);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        if (nbt != null && nbt.contains(POTION_EFFECTS_KEY, 9)) {
            NbtList nbtList = nbt.getList(POTION_EFFECTS_KEY, 10);
            for (int i = 0; i < nbtList.size(); ++i) {
                NbtCompound nbtCompound = nbtList.getCompound(i);
                StatusEffectInstance statusEffectInstance = StatusEffectInstance.fromNbt(nbtCompound);
                if (statusEffectInstance == null) continue;
                potions.add(statusEffectInstance);
            }
        }
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
