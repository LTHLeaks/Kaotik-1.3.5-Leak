package com.mawuote.client.mixins.impl;

import net.minecraft.client.multiplayer.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import com.mawuote.api.manager.event.impl.world.*;
import com.mawuote.api.manager.event.impl.player.*;

@Mixin({ PlayerControllerMP.class })
public class MixinPlayerControllerMP
{
    @Shadow
    public GameType field_78779_k;
    public Minecraft mc;
    
    @Inject(method = { "clickBlock" }, at = { @At("HEAD") }, cancellable = true)
    private void clickBlockHook(final BlockPos pos, final EnumFacing face, final CallbackInfoReturnable<Boolean> info) {
        final EventClickBlock event = new EventClickBlock(pos, face);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
    
    @Inject(method = { "onPlayerDamageBlock" }, at = { @At("HEAD") }, cancellable = true)
    private void onPlayerDamageBlockHook(final BlockPos pos, final EnumFacing face, final CallbackInfoReturnable<Boolean> info) {
        final EventBlock event = new EventBlock(pos, face);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "onPlayerDestroyBlock" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playEvent(ILnet/minecraft/util/math/BlockPos;I)V") }, cancellable = true)
    private void onPlayerDestroyBlock(final BlockPos pos, final CallbackInfoReturnable<Boolean> info) {
        MinecraftForge.EVENT_BUS.post((Event)new EventPlayerDestroyBlock(pos));
    }
}
