package com.mawuote.client.mixins.impl;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.network.*;
import net.minecraft.network.play.server.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mawuote.api.utilities.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.common.*;
import com.mawuote.api.manager.event.impl.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ NetHandlerPlayClient.class })
public class MixinNetHandlerPlayClient
{
    @Inject(method = { "handleEntityMetadata" }, at = { @At("RETURN") }, cancellable = true)
    private void handleEntityMetadataHook(final SPacketEntityMetadata packetIn, final CallbackInfo info) {
        final Entity entity;
        final EntityPlayer player;
        if (IMinecraft.mc.field_71441_e != null && (entity = IMinecraft.mc.field_71441_e.func_73045_a(packetIn.func_149375_d())) instanceof EntityPlayer && (player = (EntityPlayer)entity).func_110143_aJ() <= 0.0f) {
            MinecraftForge.EVENT_BUS.post((Event)new EventDeath(player));
        }
    }
}
