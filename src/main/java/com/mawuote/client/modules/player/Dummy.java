package com.mawuote.client.modules.player;

import net.minecraft.client.entity.*;
import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.module.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;                                   // im not gonna fix these fields bc i dont want to
import net.minecraftforge.event.entity.living.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mawuote.api.manager.event.impl.network.*;
import net.minecraft.network.play.server.*;
import com.mawuote.api.utilities.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;                                         
import net.minecraft.init.*;

public class Dummy extends Module
{
    private static Dummy INSTANCE;
    public EntityOtherPlayerMP fakePlayer;
    public static ValueBoolean popParticle;
    public static ValueBoolean popSound;
    public static ValueBoolean pops;
    
    public Dummy() {
        super("Dummy", "Dummy", "spawns a dummy to test stuff on", ModuleCategory.PLAYER);
    }
    
    public static Dummy getInstance() {
        if (Dummy.INSTANCE == null) {
            Dummy.INSTANCE = new Dummy();
        }
        return Dummy.INSTANCE;
    }
    
    @Override
    public void onEnable() {
        if (Dummy.mc.field_71441_e == null || Dummy.mc.field_71439_g == null) {
            this.disable();
        }
        else {
            final UUID playerUUID = Dummy.mc.field_71439_g.func_110124_au();
            (this.fakePlayer = new EntityOtherPlayerMP((World)Dummy.mc.field_71441_e, new GameProfile(UUID.fromString(playerUUID.toString()), Dummy.mc.field_71439_g.getDisplayNameString()))).func_82149_j((Entity)Dummy.mc.field_71439_g);
            this.fakePlayer.field_71071_by.func_70455_b(Dummy.mc.field_71439_g.field_71071_by);
            Dummy.mc.field_71441_e.func_73027_a(-7777, (Entity)this.fakePlayer);
            this.fakePlayer.func_190631_cK();
        }
    }
    
    @SubscribeEvent
    public void onTick(final LivingEvent.LivingUpdateEvent event) {
        if (Dummy.pops.getValue() && this.fakePlayer != null) {
            this.fakePlayer.field_71071_by.field_184439_c.set(0, (Object)new ItemStack(Items.field_190929_cY));
            if (this.fakePlayer.func_110143_aJ() <= 0.0f) {
                this.fakePop((Entity)this.fakePlayer);
                this.fakePlayer.func_70606_j(20.0f);
            }
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final EventPacket.Receive event) {
        if (this.fakePlayer == null) {
            return;
        }
        if (event.getPacket() instanceof SPacketExplosion) {
            final SPacketExplosion explosion = (SPacketExplosion)event.getPacket();
            if (this.fakePlayer.func_70011_f(explosion.func_149148_f(), explosion.func_149143_g(), explosion.func_149145_h()) <= 15.0) {
                final double damage = DamageUtils.calculateDamage(explosion.func_149148_f(), explosion.func_149143_g(), explosion.func_149145_h(), (Entity)this.fakePlayer);
                if (damage > 0.0 && Dummy.pops.getValue()) {
                    this.fakePlayer.func_70606_j((float)(this.fakePlayer.func_110143_aJ() - MathHelper.func_151237_a(damage, 0.0, 999.0)));
                }
            }
        }
    }
    
    @Override
    public void onDisable() {
        if (this.fakePlayer != null && Dummy.mc.field_71441_e != null) {
            Dummy.mc.field_71441_e.func_73028_b(-7777);
        }
        this.fakePlayer = null;
    }
    
    private void fakePop(final Entity entity) {
        if (Dummy.popParticle.getValue()) {
            Dummy.mc.field_71452_i.func_191271_a(entity, EnumParticleTypes.TOTEM, 30);
        }
        if (Dummy.popSound.getValue()) {
            Dummy.mc.field_71441_e.func_184134_a(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, SoundEvents.field_191263_gW, entity.func_184176_by(), 1.0f, 1.0f, false);
        }
    }
    
    static {
        Dummy.popParticle = new ValueBoolean("PopParticle", "popparticle", "shows pop particles", false);
        Dummy.popSound = new ValueBoolean("PopSound", "popsound", "plays totem sound", false);
        Dummy.pops = new ValueBoolean("Pops", "pops", "shows totem pops", false);
    }
}
