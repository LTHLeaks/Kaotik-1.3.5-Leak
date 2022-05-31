package com.mawuote.client.mixins.impl;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mawuote.api.manager.event.*;
import com.mawuote.api.manager.event.impl.network.*;
import net.minecraftforge.common.*;
import org.spongepowered.asm.mixin.injection.*;
import io.netty.channel.*;

@Mixin({ NetworkManager.class })
public class MixinNetworkManager
{
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onSendPacket(final Packet<?> packet, final CallbackInfo callbackInfo) {
        final EventPacket event = new EventPacket.Send(Event.Stage.PRE, packet);
        MinecraftForge.EVENT_BUS.post((net.minecraftforge.fml.common.eventhandler.Event)event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    private void onReceivePacket(final ChannelHandlerContext channelHandlerContext, final Packet<?> packet, final CallbackInfo callbackInfo) {
        final EventPacket event = new EventPacket.Receive(Event.Stage.POST, packet);
        MinecraftForge.EVENT_BUS.post((net.minecraftforge.fml.common.eventhandler.Event)event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
