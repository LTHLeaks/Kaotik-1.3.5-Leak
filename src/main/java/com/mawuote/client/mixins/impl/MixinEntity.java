package com.mawuote.client.mixins.impl;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;
import com.mawuote.api.manager.event.impl.player.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ Entity.class })
public abstract class MixinEntity
{
    @Shadow
    public double field_70159_w;
    @Shadow
    public double field_70181_x;
    @Shadow
    public double field_70179_y;
    
    @Shadow
    public void func_70091_d(final MoverType type, final double x, final double y, final double z) {
    }
    
    @Redirect(method = { "applyEntityCollision" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
    public void addVelocityHook(final Entity entity, final double x, final double y, final double z) {
        final EventPush event = new EventPush(entity, x, y, z, true);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCancelled()) {
            entity.field_70159_w += event.x;
            entity.field_70181_x += event.y;
            entity.field_70179_y += event.z;
            entity.field_70160_al = event.airbone;
        }
    }
}
