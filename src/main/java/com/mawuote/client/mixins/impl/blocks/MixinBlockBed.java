package com.mawuote.client.mixins.impl.blocks;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import com.mawuote.api.manager.event.impl.render.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ BlockBed.class })
public class MixinBlockBed
{
    @Inject(method = { "getRenderLayer" }, at = { @At("HEAD") }, cancellable = true)
    public void getRenderLayer(final CallbackInfoReturnable<BlockRenderLayer> callback) {
        final EventBlockGetRenderLayer event = new EventBlockGetRenderLayer((Block)this);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCancelled()) {
            callback.cancel();
            callback.setReturnValue(event.getBlockRenderLayer());
        }
    }
}
