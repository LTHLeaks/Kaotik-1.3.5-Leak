package com.mawuote.api.manager.module;

import net.minecraft.client.*;
import com.mawuote.api.manager.value.*;
import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.event.impl.render.*;
import net.minecraftforge.common.*;
import com.mojang.realmsclient.gui.*;
import com.mawuote.api.manager.misc.*;
import com.mawuote.*;
import com.mawuote.client.modules.client.*;
import java.util.*;
import java.awt.*;

public class Module
{
    protected static final Minecraft mc;
    private final ArrayList<Value> values;
    private final String name;
    private final String description;
    private final ModuleCategory category;
    private boolean toggled;
    private final boolean persistent;
    public final ValueString tag;
    public final ValueBoolean chatNotify;
    public final ValueBoolean drawn;
    public final ValueBind bind;
    
    public Module(final String name, final String tag, final String description, final ModuleCategory category) {
        this.tag = new ValueString("Tag", "Tag", "Let's you customize the module's name.", "Placeholder");
        this.chatNotify = new ValueBoolean("ChatNotify", "ChatNotify", "Sends a message when the module is toggled.", true);
        this.drawn = new ValueBoolean("Drawn", "Drawn", "Puts the module on the array list hud component.", true);
        this.bind = new ValueBind("Bind", "Bind", "The bind for the module.", 0);
        this.name = name;
        this.tag.setValue(tag);
        this.description = description;
        this.category = category;
        this.persistent = false;
        this.values = new ArrayList<Value>();
    }
    
    public Module(final String name, final String tag, final String description, final ModuleCategory category, final boolean persistent) {
        this.tag = new ValueString("Tag", "Tag", "Let's you customize the module's name.", "Placeholder");
        this.chatNotify = new ValueBoolean("ChatNotify", "ChatNotify", "Sends a message when the module is toggled.", true);
        this.drawn = new ValueBoolean("Drawn", "Drawn", "Puts the module on the array list hud component.", true);
        this.bind = new ValueBind("Bind", "Bind", "The bind for the module.", 0);
        this.name = name;
        this.tag.setValue(tag);
        this.description = description;
        this.category = category;
        this.persistent = persistent;
        this.values = new ArrayList<Value>();
        this.persist();
    }
    
    public void onUpdate() {
    }
    
    public void onMotionUpdate() {
    }
    
    public void onRender2D(final EventRender2D event) {
    }
    
    public void onRender3D(final EventRender3D event) {
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    public void onLogin() {
    }
    
    public void onLogout() {
    }
    
    public void onDeath() {
    }
    
    public static boolean fullNullCheck() {
        return Module.mc.field_71439_g == null || Module.mc.field_71441_e == null;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public ModuleCategory getCategory() {
        return this.category;
    }
    
    public boolean isPersistent() {
        return this.persistent;
    }
    
    public boolean isToggled() {
        return this.toggled;
    }
    
    public void setToggled(final boolean toggled) {
        this.toggled = toggled;
    }
    
    public String getTag() {
        return this.tag.getValue();
    }
    
    public void setTag(final String tag) {
        this.tag.setValue(tag);
    }
    
    public boolean isChatNotify() {
        return this.chatNotify.getValue();
    }
    
    public void setChatNotify(final boolean chatNotify) {
        this.chatNotify.setValue(chatNotify);
    }
    
    public boolean isDrawn() {
        return this.drawn.getValue();
    }
    
    public void setDrawn(final boolean drawn) {
        this.drawn.setValue(drawn);
    }
    
    public int getBind() {
        return this.bind.getValue();
    }
    
    public void setBind(final int bind) {
        this.bind.setValue(bind);
    }
    
    public String getHudInfo() {
        return "";
    }
    
    public void persist() {
        if (this.persistent) {
            this.setToggled(true);
            MinecraftForge.EVENT_BUS.register((Object)this);
        }
    }
    
    public void toggle() {
        if (this.toggled) {
            this.disable();
        }
        else {
            this.enable();
        }
    }
    
    public void enable() {
        if (!this.persistent) {
            this.setToggled(true);
            this.onEnable();
            int moduleNumber = 0;
            for (final char character : this.name.toCharArray()) {
                moduleNumber += character;
                moduleNumber *= 10;
            }
            if (this.isChatNotify()) {
                if (ModuleColor.prefixMode.getValue().equals(ModuleColor.prefixModes.Rainbow) || ModuleColor.prefixMode.getValue().equals(ModuleColor.prefixModes.Gradient)) {
                    ChatManager.sendClientMessage(this.name + "§r" + ChatFormatting.GREEN + ChatFormatting.BOLD + " Enabled!", moduleNumber);
                }
                else {
                    ChatManager.sendClientMessage(this.name + ChatFormatting.GREEN + ChatFormatting.BOLD + " Enabled!", moduleNumber);
                }
            }
            if (Kaotik.getModuleManager().isModuleEnabled("Notifications") && ModuleNotifications.chatNotify.getValue() && this.isChatNotify()) {
                Kaotik.NOTIFICATION_PROCESSOR.addNotification(this.name + ChatFormatting.GREEN + ChatFormatting.BOLD + " Enabled!", ModuleNotifications.lifetime.getValue().intValue(), ModuleNotifications.inOutTime.getValue().intValue());
            }
            MinecraftForge.EVENT_BUS.register((Object)this);
        }
    }
    
    public void disable() {
        if (!this.persistent) {
            this.setToggled(false);
            this.onDisable();
            int moduleNumber = 0;
            for (final char character : this.name.toCharArray()) {
                moduleNumber += character;
                moduleNumber *= 10;
            }
            if (this.isChatNotify()) {
                if (ModuleColor.prefixMode.getValue().equals(ModuleColor.prefixModes.Rainbow) || ModuleColor.prefixMode.getValue().equals(ModuleColor.prefixModes.Gradient)) {
                    ChatManager.sendClientMessage(this.name + "§r" + ChatFormatting.RED + ChatFormatting.BOLD + " Disabled!", moduleNumber);
                }
                else {
                    ChatManager.sendClientMessage(this.name + ChatFormatting.RED + ChatFormatting.BOLD + " Disabled!", moduleNumber);
                }
            }
            if (Kaotik.getModuleManager().isModuleEnabled("Notifications") && ModuleNotifications.chatNotify.getValue() && this.isChatNotify()) {
                Kaotik.NOTIFICATION_PROCESSOR.addNotification(this.name + ChatFormatting.RED + ChatFormatting.BOLD + " Disabled!", ModuleNotifications.lifetime.getValue().intValue(), ModuleNotifications.inOutTime.getValue().intValue());
            }
            MinecraftForge.EVENT_BUS.unregister((Object)this);
        }
    }
    
    public Value getValue(final String name) {
        for (final Value value : this.values) {
            if (value.getName().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }
    
    public void addValue(final Value value) {
        this.values.add(value);
    }
    
    public ArrayList<Value> getValues() {
        return this.values;
    }
    
    public static Color globalColor(final int alpha) {
        return new Color(ModuleColor.daColor.getValue().getRed(), ModuleColor.daColor.getValue().getGreen(), ModuleColor.daColor.getValue().getBlue(), alpha);
    }
    
    static {
        mc = Minecraft.func_71410_x();
    }
}
