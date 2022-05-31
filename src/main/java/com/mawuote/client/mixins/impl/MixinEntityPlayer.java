package com.mawuote.client.mixins.impl;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import com.mawuote.api.manager.event.impl.player.*;
import net.minecraft.entity.*;

@Mixin({ EntityPlayer.class })
public abstract class MixinEntityPlayer extends EntityLivingBase
{
    public MixinEntityPlayer(final World worldIn) {
        super(worldIn);
    }
    
    @Shadow
    public abstract String func_70005_c_();
    
    @Inject(method = { "jump" }, at = { @At("HEAD") }, cancellable = true)
    public void onJump(final CallbackInfo ci) {
        if (Minecraft.func_71410_x().field_71439_g.func_70005_c_() == this.func_70005_c_()) {
            MinecraftForge.EVENT_BUS.post((Event)new EventPlayerJump());
        }
    }
    
    @Inject(method = { "travel" }, at = { @At("HEAD") }, cancellable = true)
    public void travel(final float strafe, final float vertical, final float forward, final CallbackInfo info) {
        final EventPlayerTravel event = new EventPlayerTravel(strafe, vertical, forward);
        MinecraftForge.EVENT_BUS.post((Event)new EventPlayerTravel(strafe, vertical, forward));
        if (event.isCancelled()) {
            this.func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
            info.cancel();
        }
    }
}
