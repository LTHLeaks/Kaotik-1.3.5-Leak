package com.mawuote.client.mixins.impl;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mawuote.*;
import com.mawuote.client.modules.client.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiPlayerTabOverlay.class })
public class MixinGuiPlayerTabOverlay
{
    @Inject(method = { "getPlayerName" }, at = { @At("HEAD") }, cancellable = true)
    public void getPlayerName(final NetworkPlayerInfo networkPlayerInfoIn, final CallbackInfoReturnable returnable) {
        if (Kaotik.getModuleManager().isModuleEnabled("StreamerMode") && ModuleStreamerMode.hideYou.getValue()) {
            returnable.cancel();
            returnable.setReturnValue(ModuleStreamerMode.getPlayerName(networkPlayerInfoIn));
        }
    }
}
