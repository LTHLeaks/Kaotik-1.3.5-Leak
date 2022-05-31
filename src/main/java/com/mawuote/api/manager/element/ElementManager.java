package com.mawuote.api.manager.element;

import net.minecraft.client.*;
import net.minecraftforge.common.*;
import com.mawuote.client.elements.*;
import com.mawuote.api.manager.module.*;
import java.util.function.*;
import com.mawuote.api.manager.value.*;
import java.lang.reflect.*;
import java.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mawuote.api.manager.event.impl.player.*;
import net.minecraftforge.client.event.*;
import com.mawuote.api.manager.event.impl.render.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.fml.common.network.*;

public class ElementManager
{
    protected static final Minecraft mc;
    private final ArrayList<Element> elements;
    
    public ElementManager() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.elements = new ArrayList<Element>();
        this.addElement(new ElementWatermark());
        this.addElement(new ElementWelcomer());
        this.addElement(new ElementCoordinates());
        this.addElement(new ElementStickyNotes());
        this.addElement(new ElementFriends());
        this.addElement(new ElementArmor());
        this.addElement(new ElementPlayerViewer());
        this.elements.sort(Comparator.comparing((Function<? super Element, ? extends Comparable>)Module::getName));
    }
    
    public void addElement(final Element element) {
        try {
            for (final Field field : element.getClass().getDeclaredFields()) {
                if (Value.class.isAssignableFrom(field.getType())) {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    element.addValue((Value)field.get(element));
                }
            }
            this.elements.add(element);
        }
        catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Element> getElements() {
        return this.elements;
    }
    
    public Element getElement(final String name) {
        for (final Element module : this.elements) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }
    
    public boolean isElementEnabled(final String name) {
        final Element module = this.elements.stream().filter(m -> m.getName().equals(name)).findFirst().orElse(null);
        return module != null && module.isToggled();
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (ElementManager.mc.field_71439_g != null && ElementManager.mc.field_71441_e != null) {
            this.elements.stream().filter(Module::isToggled).forEach(Element::onUpdate);
        }
    }
    
    @SubscribeEvent
    public void onUpdate(final EventMotionUpdate event) {
        if (ElementManager.mc.field_71439_g != null && ElementManager.mc.field_71441_e != null) {
            this.elements.stream().filter(Module::isToggled).forEach(Element::onMotionUpdate);
        }
    }
    
    @SubscribeEvent
    public void onRender2D(final RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            this.elements.stream().filter(Module::isToggled).forEach(m -> m.onRender2D(new EventRender2D(event.getPartialTicks())));
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @SubscribeEvent
    public void onLogin(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        this.elements.stream().filter(Module::isToggled).forEach(Element::onLogin);
    }
    
    @SubscribeEvent
    public void onLogout(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.elements.stream().filter(Module::isToggled).forEach(Element::onLogout);
    }
    
    static {
        mc = Minecraft.func_71410_x();
    }
}
