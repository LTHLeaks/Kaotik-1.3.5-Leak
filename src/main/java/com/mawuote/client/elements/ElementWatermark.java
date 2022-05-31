package com.mawuote.client.elements;

import com.mawuote.api.manager.element.*;
import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.event.impl.render.*;
import com.mawuote.*;
import com.mawuote.client.modules.client.*;
import com.mojang.realmsclient.gui.*;

public class ElementWatermark extends Element
{
    public static ValueEnum mode;
    public static ValueString customValue;
    public static ValueEnum version;
    public static ValueEnum versionColor;
    
    public ElementWatermark() {
        super("Watermark", "The client's watermark.");
    }
    
    @Override
    public void onRender2D(final EventRender2D event) {
        super.onRender2D(event);
        this.frame.setWidth(Kaotik.FONT_MANAGER.getStringWidth(this.getText()));
        this.frame.setHeight(Kaotik.FONT_MANAGER.getHeight());
        Kaotik.FONT_MANAGER.drawString(this.getText(), this.frame.getX(), this.frame.getY(), ModuleColor.getActualColor());
    }
    
    private String getText() {
        return (ElementWatermark.mode.getValue().equals(Modes.Custom) ? ElementWatermark.customValue.getValue() : "Kaotik") + (ElementWatermark.version.getValue().equals(Versions.None) ? "" : (" " + this.getVersionColor() + (ElementWatermark.version.getValue().equals(Versions.Normal) ? "v" : "") + "1.3.5"));
    }
    
    private ChatFormatting getVersionColor() {
        if (ElementWatermark.versionColor.getValue().equals(VersionColors.White)) {
            return ChatFormatting.WHITE;
        }
        if (ElementWatermark.versionColor.getValue().equals(VersionColors.Gray)) {
            return ChatFormatting.GRAY;
        }
        return ChatFormatting.RESET;
    }
    
    static {
        ElementWatermark.mode = new ValueEnum("Mode", "Mode", "The mode for the watermark.", Modes.Normal);
        ElementWatermark.customValue = new ValueString("CustomValue", "CustomValue", "The value for the Custom Watermark.", "Kaotik");
        ElementWatermark.version = new ValueEnum("Version", "Version", "Renders the Version on the watermark.", Versions.Normal);
        ElementWatermark.versionColor = new ValueEnum("VersionColor", "VersionColor", "The color for the version.", VersionColors.Normal);
    }
    
    public enum Modes
    {
        Normal, 
        Custom;
    }
    
    public enum Versions
    {
        None, 
        Simple, 
        Normal;
    }
    
    public enum VersionColors
    {
        Normal, 
        White, 
        Gray;
    }
}
