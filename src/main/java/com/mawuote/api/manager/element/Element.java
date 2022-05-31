package com.mawuote.api.manager.element;

import net.minecraft.client.*;
import java.util.*;
import com.mawuote.api.manager.value.*;
import com.mawuote.client.gui.hud.*;
import com.mawuote.api.manager.module.*;
import com.mawuote.api.manager.event.impl.render.*;
import net.minecraftforge.common.*;

public class Element extends Module
{
    protected static final Minecraft mc;
    private final ArrayList<Value> values;
    public ElementFrame frame;
    
    public Element(final String name, final String description) {
        super(name, name, description, ModuleCategory.HUD);
        this.values = new ArrayList<Value>();
    }
    
    public Element(final String name, final String tag, final String description) {
        super(name, tag, description, ModuleCategory.HUD);
        this.values = new ArrayList<Value>();
    }
    
    @Override
    public void onUpdate() {
    }
    
    @Override
    public void onMotionUpdate() {
    }
    
    @Override
    public void onRender2D(final EventRender2D event) {
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onLogin() {
    }
    
    @Override
    public void onLogout() {
    }
    
    @Override
    public void onDeath() {
    }
    
    public void setFrame(final ElementFrame frame) {
        this.frame = frame;
    }
    
    @Override
    public String getHudInfo() {
        return "";
    }
    
    @Override
    public void toggle() {
        if (this.isToggled()) {
            this.disable();
        }
        else {
            this.enable();
        }
    }
    
    @Override
    public void enable() {
        this.setToggled(true);
        this.onEnable();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public void disable() {
        this.setToggled(false);
        this.onDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @Override
    public void addValue(final Value value) {
        this.values.add(value);
    }
    
    @Override
    public ArrayList<Value> getValues() {
        return this.values;
    }
    
    static {
        mc = Minecraft.func_71410_x();
    }
}
