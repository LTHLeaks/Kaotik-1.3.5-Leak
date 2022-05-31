package com.mawuote.client.modules.client;

import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.module.*;
import com.mawuote.*;
import net.minecraft.client.gui.*;

public class ModuleGUI extends Module
{
    public static ModuleGUI INSTANCE;
    public static ValueBoolean customMenu;
    public ValueNumber scrollSpeed;
    
    public ModuleGUI() {
        super("GUI", "GUI", "The client's Click GUI.", ModuleCategory.CLIENT);
        this.scrollSpeed = new ValueNumber("ScrollSpeed", "ScrollSpeed", "The speed for scrolling through the GUI.", 10, 1, 20);
        this.setBind(54);
        ModuleGUI.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        ModuleGUI.mc.func_147108_a((GuiScreen)Kaotik.CLICK_GUI);
    }
    
    static {
        ModuleGUI.customMenu = new ValueBoolean("CustomMainMenu", "CustomMainMenu", "", true);
    }
}
