package me.inf32768.ultimatescaler.mixins.offset;

import me.inf32768.ultimatescaler.UltimateScaler;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Chunk.class)
public abstract class MixinChunk {
    @ModifyVariable(method = "populateBiomes", at = @At("STORE"))
    private ChunkPos applyOffset(ChunkPos chunkPos) {
        int offset = UltimateScaler.config.genOffset;
        return new ChunkPos(chunkPos.x + offset, chunkPos.z + offset);
    }
}
