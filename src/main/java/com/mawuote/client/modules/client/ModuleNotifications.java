package com.mawuote.client.modules.client;

import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.module.*;
import net.minecraft.client.gui.*;
import com.mawuote.api.manager.event.impl.render.*;
import com.mawuote.*;

public class ModuleNotifications extends Module
{
    public static ValueNumber lifetime;
    public static ValueNumber inOutTime;
    ValueNumber height;
    ValueNumber max;
    public static ValueBoolean addType;
    public static ValueBoolean pops;
    public static ValueBoolean chatNotify;
    
    public ModuleNotifications() {
        super("Notifications", "Notifications", "Renders notifications on your screen.", ModuleCategory.CLIENT);
        this.height = new ValueNumber("Height", "Height", "", 50, 0, new ScaledResolution(ModuleNotifications.mc).func_78328_b());
        this.max = new ValueNumber("Max", "Max", "", 7, 1, 20);
    }
    
    @Override
    public void onRender2D(final EventRender2D event) {
        if (ModuleNotifications.mc.field_71439_g == null || ModuleNotifications.mc.field_71441_e == null) {
            return;
        }
        if (Kaotik.NOTIFICATION_PROCESSOR.notifications.size() > this.max.getValue().intValue()) {
            Kaotik.NOTIFICATION_PROCESSOR.notifications.remove(0);
        }
        Kaotik.NOTIFICATION_PROCESSOR.handleNotifications(this.height.getValue().intValue());
    }
    
    static {
        ModuleNotifications.lifetime = new ValueNumber("Lifetime", "Lifetime", "", 500, 500, 5000);
        ModuleNotifications.inOutTime = new ValueNumber("InOutTime", "InOutTime", "", 200, 50, 500);
        ModuleNotifications.addType = new ValueBoolean("AddDecrease", "AddDecrease", "", false);
        ModuleNotifications.pops = new ValueBoolean("Pops", "Pops", "", true);
        ModuleNotifications.chatNotify = new ValueBoolean("ChatNotify", "ChatNotify", "", true);
    }
}
