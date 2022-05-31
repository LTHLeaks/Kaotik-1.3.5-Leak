package com.mawuote.client.modules.client;

import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.module.*;
import java.awt.*;
import com.mojang.realmsclient.gui.*;

public class ModuleColor extends Module
{
    public static ValueBoolean prefix;
    public static ValueEnum textColour;
    public static ValueEnum bracketColour;
    public static ValueEnum prefixMode;
    public static ValueColor prefixStart;
    public static ValueColor prefixEnd;
    public static ValueColor daColor;
    
    public ModuleColor() {
        super("Color", "Color", "Allows you to customize the client's main colors.", ModuleCategory.CLIENT, true);
    }
    
    public static Color getActualColor() {
        return new Color(ModuleColor.daColor.getValue().getRed(), ModuleColor.daColor.getValue().getGreen(), ModuleColor.daColor.getValue().getBlue());
    }
    
    public static int getColor() {
        return new Color(ModuleColor.daColor.getValue().getRed(), ModuleColor.daColor.getValue().getGreen(), ModuleColor.daColor.getValue().getBlue()).getRGB();
    }
    
    public static ChatFormatting getTextColor() {
        switch (ModuleColor.textColour.getValue()) {
            case Red: {
                return ChatFormatting.RED;
            }
            case Aqua: {
                return ChatFormatting.AQUA;
            }
            case Blue: {
                return ChatFormatting.BLUE;
            }
            case Gold: {
                return ChatFormatting.GOLD;
            }
            case Gray: {
                return ChatFormatting.GRAY;
            }
            case Black: {
                return ChatFormatting.BLACK;
            }
            case Green: {
                return ChatFormatting.GREEN;
            }
            case White: {
                return ChatFormatting.WHITE;
            }
            case Yellow: {
                return ChatFormatting.YELLOW;
            }
            case DarkRed: {
                return ChatFormatting.DARK_RED;
            }
            case DarkAqua: {
                return ChatFormatting.DARK_AQUA;
            }
            case DarkBlue: {
                return ChatFormatting.DARK_BLUE;
            }
            case DarkGray: {
                return ChatFormatting.DARK_GRAY;
            }
            case DarkGreen: {
                return ChatFormatting.DARK_GREEN;
            }
            case DarkPurple: {
                return ChatFormatting.DARK_PURPLE;
            }
            case LightPurple: {
                return ChatFormatting.LIGHT_PURPLE;
            }
            default: {
                return null;
            }
        }
    }
    
    public static ChatFormatting getBracketColour() {
        switch (ModuleColor.bracketColour.getValue()) {
            case Red: {
                return ChatFormatting.RED;
            }
            case Aqua: {
                return ChatFormatting.AQUA;
            }
            case Blue: {
                return ChatFormatting.BLUE;
            }
            case Gold: {
                return ChatFormatting.GOLD;
            }
            case Gray: {
                return ChatFormatting.GRAY;
            }
            case Black: {
                return ChatFormatting.BLACK;
            }
            case Green: {
                return ChatFormatting.GREEN;
            }
            case White: {
                return ChatFormatting.WHITE;
            }
            case Yellow: {
                return ChatFormatting.YELLOW;
            }
            case DarkRed: {
                return ChatFormatting.DARK_RED;
            }
            case DarkAqua: {
                return ChatFormatting.DARK_AQUA;
            }
            case DarkBlue: {
                return ChatFormatting.DARK_BLUE;
            }
            case DarkGray: {
                return ChatFormatting.DARK_GRAY;
            }
            case DarkGreen: {
                return ChatFormatting.DARK_GREEN;
            }
            case DarkPurple: {
                return ChatFormatting.DARK_PURPLE;
            }
            case LightPurple: {
                return ChatFormatting.LIGHT_PURPLE;
            }
            default: {
                return null;
            }
        }
    }
    
    static {
        ModuleColor.prefix = new ValueBoolean("Prefix", "Prefix", "", false);
        ModuleColor.textColour = new ValueEnum("TextColour", "textcolour", "", colourModes.Blue);
        ModuleColor.bracketColour = new ValueEnum("BracketColour", "bracketcolour", "", bracketModes.Gray);
        ModuleColor.prefixMode = new ValueEnum("PrefixMode", "PrefixMode", "", prefixModes.Static);
        ModuleColor.prefixStart = new ValueColor("PrefixStart", "PrefixStart", "", new Color(255, 0, 0, 255));
        ModuleColor.prefixEnd = new ValueColor("PrefixEnd", "PrefixEnd", "", new Color(0, 0, 0, 255));
        ModuleColor.daColor = new ValueColor("Color", "Color", "", new Color(82, 1, 1, 255));
    }
    
    public enum prefixModes
    {
        Rainbow, 
        Gradient, 
        Static;
    }
    
    public enum colourModes
    {
        Black, 
        Blue, 
        DarkBlue, 
        Green, 
        DarkGreen, 
        Red, 
        DarkRed, 
        Gold, 
        Gray, 
        DarkGray, 
        Yellow, 
        DarkAqua, 
        DarkPurple, 
        Aqua, 
        LightPurple, 
        White;
    }
    
    public enum bracketModes
    {
        Black, 
        Blue, 
        DarkBlue, 
        Green, 
        DarkGreen, 
        Red, 
        DarkRed, 
        Gold, 
        Gray, 
        DarkGray, 
        Yellow, 
        DarkAqua, 
        DarkPurple, 
        Aqua, 
        LightPurple, 
        White;
    }
}
