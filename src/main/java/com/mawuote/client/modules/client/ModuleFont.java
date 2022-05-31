package com.mawuote.client.modules.client;

import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.module.*;

public class ModuleFont extends Module
{
    public static ValueString name;
    public static ValueEnum style;
    public static ValueNumber size;
    public static ValueBoolean antiAlias;
    public static ValueBoolean metrics;
    public static ValueEnum shadow;
    
    public ModuleFont() {
        super("Font", "Font", "Allows you to customize the client's font.", ModuleCategory.CLIENT);
    }
    
    static {
        ModuleFont.name = new ValueString("Name", "Name", "The name for the Font.", "Arial");
        ModuleFont.style = new ValueEnum("Style", "Style", "The style for the font.", Styles.Plain);
        ModuleFont.size = new ValueNumber("Size", "Size", "The size for the Font.", 18, 10, 50);
        ModuleFont.antiAlias = new ValueBoolean("AntiAlias", "AntiAlias", "Makes the font smoother.", true);
        ModuleFont.metrics = new ValueBoolean("Metrics", "Metrics", "Makes the font more clumped up and better looking.", true);
        ModuleFont.shadow = new ValueEnum("Shadow", "Shadow", "The shadow for the Font.", Shadows.Normal);
    }
    
    public enum Styles
    {
        Plain, 
        Italic, 
        Bold, 
        Both;
    }
    
    public enum Shadows
    {
        None, 
        Small, 
        Normal;
    }
}
