package com.mawuote.client.modules.client;

import com.mawuote.api.manager.module.*;
import com.mawuote.*;
import net.minecraft.client.gui.*;

public class ModuleHUDEditor extends Module
{
    public static ModuleHUDEditor INSTANCE;
    
    public ModuleHUDEditor() {
        super("HUDEditor", "HUD Editor", "The client's HUD Editor.", ModuleCategory.CLIENT);
        ModuleHUDEditor.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (ModuleHUDEditor.mc.field_71439_g == null || ModuleHUDEditor.mc.field_71441_e == null) {
            this.disable();
            return;
        }
        ModuleHUDEditor.mc.func_147108_a((GuiScreen)Kaotik.HUD_EDITOR);
    }
}
