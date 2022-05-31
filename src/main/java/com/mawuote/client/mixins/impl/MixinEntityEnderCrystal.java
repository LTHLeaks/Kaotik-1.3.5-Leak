package com.mawuote.client.mixins.impl;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mawuote.api.manager.event.impl.world.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EntityEnderCrystal.class })
public class MixinEntityEnderCrystal
{
    @Inject(method = { "attackEntityFrom" }, at = { @At("RETURN") }, cancellable = true)
    public void attackEntityFrom(final DamageSource source, final float amount, final CallbackInfoReturnable<Boolean> info) {
        if (source.func_76346_g() != null) {
            final EventCrystalAttack event = new EventCrystalAttack(source.func_76346_g().field_145783_c);
            MinecraftForge.EVENT_BUS.post((Event)event);
            if (event.isCanceled()) {
                info.cancel();
            }
        }
    }
}
