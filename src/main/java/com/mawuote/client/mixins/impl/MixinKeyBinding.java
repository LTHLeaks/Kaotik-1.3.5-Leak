package com.mawuote.client.mixins.impl;

import net.minecraft.client.settings.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mawuote.api.manager.event.impl.player.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ KeyBinding.class })
public class MixinKeyBinding
{
    @Shadow
    private boolean field_74513_e;
    
    @Inject(method = { "isKeyDown" }, at = { @At("RETURN") }, cancellable = true)
    private void isKeyDown(final CallbackInfoReturnable<Boolean> info) {
        final EventKey event = new EventKey(info.getReturnValue(), this.field_74513_e);
        MinecraftForge.EVENT_BUS.post((Event)event);
        info.setReturnValue(event.info);
    }
}
