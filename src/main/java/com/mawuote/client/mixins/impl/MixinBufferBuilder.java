package com.mawuote.client.mixins.impl;

import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mawuote.api.manager.event.impl.render.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.nio.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ BufferBuilder.class })
public abstract class MixinBufferBuilder
{
    @Shadow
    private boolean field_78939_q;
    @Shadow
    private IntBuffer field_178999_b;
    
    @Shadow
    public abstract int func_78909_a(final int p0);
    
    @Inject(method = { "putColorMultiplier" }, at = { @At("HEAD") }, cancellable = true)
    public void putColorMultiplier(final float red, final float green, final float blue, final int vertexIndex, final CallbackInfo info) {
        final EventRenderPutColorMultiplier event = new EventRenderPutColorMultiplier();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCancelled()) {
            info.cancel();
            final int i = this.func_78909_a(vertexIndex);
            int j = -1;
            final float newAlpha = event.getOpacity();
            if (!this.field_78939_q) {
                j = this.field_178999_b.get(i);
                if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                    final int k = (int)((j & 0xFF) * red);
                    final int l = (int)((j >> 8 & 0xFF) * green);
                    final int i2 = (int)((j >> 16 & 0xFF) * blue);
                    final int alpha = (int)((j >> 24 & 0xFF) * newAlpha);
                    j = (alpha << 24 | i2 << 16 | l << 8 | k);
                }
                else {
                    final int j2 = (int)((j >> 24 & 0xFF) * red);
                    final int k2 = (int)((j >> 16 & 0xFF) * green);
                    final int l2 = (int)((j >> 8 & 0xFF) * blue);
                    final int alpha = (int)((j & 0xFF) * newAlpha);
                    j = (j2 << 24 | k2 << 16 | l2 << 8 | alpha);
                }
            }
            this.field_178999_b.put(i, j);
        }
    }
}
