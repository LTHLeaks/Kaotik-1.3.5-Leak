package com.mawuote.client.mixins.impl;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mawuote.*;
import com.mawuote.client.modules.render.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { EntityLivingBase.class }, priority = Integer.MIN_VALUE)
public class MixinEntityLivingBase
{
    @Inject(method = { "getArmSwingAnimationEnd" }, at = { @At("HEAD") }, cancellable = true)
    private void getArmSwingAnimationEnd(final CallbackInfoReturnable<Integer> info) {
        if (Kaotik.MODULE_MANAGER.isModuleEnabled("Animations") && ModuleAnimations.changeSwing.getValue()) {
            info.setReturnValue(ModuleAnimations.swingDelay.getValue().intValue());
        }
    }
}
