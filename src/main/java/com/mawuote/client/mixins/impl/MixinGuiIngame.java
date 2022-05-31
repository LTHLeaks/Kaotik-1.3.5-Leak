package com.mawuote.client.mixins.impl;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mawuote.*;
import com.mawuote.client.modules.render.*;
import org.spongepowered.asm.mixin.injection.*;
import com.mawuote.client.modules.client.*;

@Mixin({ GuiIngame.class })
public class MixinGuiIngame
{
    @Inject(method = { "renderPumpkinOverlay" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderPumpkinOverlayHook(final ScaledResolution scaledRes, final CallbackInfo info) {
        if (Kaotik.getModuleManager().isModuleEnabled("NoRender") && ModuleNoRender.pumpkin.getValue()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "renderPotionEffects" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderPotionEffectsHook(final ScaledResolution scaledRes, final CallbackInfo info) {
        if (ModuleHud.effectHud.getValue().equals(ModuleHud.effectHudModes.Hide)) {
            info.cancel();
        }
    }
}
