package com.mawuote.client.modules.client;

import com.mawuote.api.manager.value.impl.*;
import com.mawuote.client.gui.special.particles.*;
import com.mawuote.api.manager.module.*;
import com.mawuote.api.manager.event.impl.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mawuote.api.manager.event.impl.render.*;
import com.mawuote.*;
import net.minecraft.client.gui.inventory.*;
import java.awt.*;

public class ModuleParticles extends Module
{
    public static ValueColor daColor;
    public static ValueNumber lineWidth;
    public static ValueNumber amount;
    public static ValueNumber radius;
    public static ValueNumber speed;
    public static ValueNumber delta;
    boolean changeAmount;
    private ParticleSystem ps;
    
    public ModuleParticles() {
        super("Particles", "Particles", "Renders fancy particles on the screen.", ModuleCategory.CLIENT);
        this.changeAmount = false;
    }
    
    @Override
    public void onEnable() {
        this.ps = new ParticleSystem(ModuleParticles.amount.getValue().intValue(), ModuleParticles.radius.getValue().intValue());
    }
    
    @SubscribeEvent
    public void onSetting(final EventClient event) {
        if (ModuleParticles.mc.field_71439_g == null || ModuleParticles.mc.field_71441_e == null) {
            return;
        }
        if (event.getSetting() == ModuleParticles.amount) {
            this.changeAmount = true;
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.changeAmount) {
            this.ps.changeParticles(ModuleParticles.amount.getValue().intValue());
            this.changeAmount = false;
        }
        this.ps.tick(ModuleParticles.delta.getValue().intValue());
        this.ps.dist = ModuleParticles.radius.getValue().intValue();
        final ParticleSystem ps = this.ps;
        ParticleSystem.SPEED = (float)ModuleParticles.speed.getValue().doubleValue();
    }
    
    @Override
    public void onRender2D(final EventRender2D event) {
        if (ModuleParticles.mc.field_71456_v.func_146158_b().func_146241_e() || ModuleParticles.mc.field_71462_r == Kaotik.CLICK_GUI || ModuleParticles.mc.field_71462_r instanceof GuiContainer) {
            this.ps.render();
        }
    }
    
    static {
        ModuleParticles.daColor = new ValueColor("Color", "Color", "", new Color(255, 255, 255));
        ModuleParticles.lineWidth = new ValueNumber("LineWidth", "LineWidth", "", 2.0, 1.0, 3.0);
        ModuleParticles.amount = new ValueNumber("Population", "Amounts", "", 100, 50, 400);
        ModuleParticles.radius = new ValueNumber("Radius", "Radius", "", 100, 50, 300);
        ModuleParticles.speed = new ValueNumber("Speed", "Speed", "", 0.1f, 0.1f, 10.0f);
        ModuleParticles.delta = new ValueNumber("Delta", "Delta", "", 1, 1, 10);
    }
}
