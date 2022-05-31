package com.mawuote.client.mixins.impl;

import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mawuote.*;
import com.mawuote.client.modules.render.*;
import net.minecraft.init.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { EntityRenderer.class }, priority = Integer.MIN_VALUE)
public class MixinEntityRenderer
{
    @Shadow
    public ItemStack field_190566_ab;
    
    @Inject(method = { "renderItemActivation" }, at = { @At("HEAD") }, cancellable = true)
    public void renderItemActivationHook(final CallbackInfo info) {
        if (this.field_190566_ab != null && Kaotik.getModuleManager().isModuleEnabled("NoRender") && ModuleNoRender.totemPop.getValue() && this.field_190566_ab.func_77973_b() == Items.field_190929_cY) {
            info.cancel();
        }
    }
    
    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void hurtCameraEffectHook(final float ticks, final CallbackInfo info) {
        if (Kaotik.getModuleManager().isModuleEnabled("NoRender") && ModuleNoRender.hurtCam.getValue()) {
            info.cancel();
        }
    }
}
