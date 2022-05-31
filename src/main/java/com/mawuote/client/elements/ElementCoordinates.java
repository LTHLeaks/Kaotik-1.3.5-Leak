package com.mawuote.client.elements;

import com.mawuote.api.manager.element.*;
import java.text.*;
import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.event.impl.render.*;
import com.mawuote.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.client.gui.*;
import com.mawuote.client.modules.client.*;

public class ElementCoordinates extends Element
{
    DecimalFormat format;
    public static ValueEnum color;
    public static ValueEnum direction;
    public static ValueBoolean nether;
    public static ValueString firstSymbol;
    public static ValueString secondSymbol;
    public static ValueBoolean chatMove;
    
    public ElementCoordinates() {
        super("Coordinates", "Renders your coordinates and direction on screen.");
        this.format = new DecimalFormat("#.#");
    }
    
    @Override
    public void onRender2D(final EventRender2D event) {
        super.onRender2D(event);
        this.frame.setWidth(Kaotik.FONT_MANAGER.getStringWidth(this.getCoordinatesText()));
        this.frame.setHeight(ElementCoordinates.direction.getValue().equals(Directions.Normal) ? (Kaotik.FONT_MANAGER.getHeight() * 2.0f + 1.0f) : Kaotik.FONT_MANAGER.getHeight());
        if (ElementCoordinates.direction.getValue().equals(Directions.Normal)) {
            Kaotik.FONT_MANAGER.drawString(this.getDirectionName() + " " + ElementCoordinates.firstSymbol.getValue() + this.getColor() + this.getFacing(ElementCoordinates.mc.field_71439_g.func_174811_aO().func_176610_l()) + ChatFormatting.RESET + ElementCoordinates.secondSymbol.getValue(), this.frame.getX(), this.frame.getY() - ((ElementCoordinates.chatMove.getValue() && ElementCoordinates.mc.field_71462_r instanceof GuiChat) ? 11 : 0), ModuleColor.getActualColor());
        }
        Kaotik.FONT_MANAGER.drawString(this.getCoordinatesText(), this.frame.getX(), ElementCoordinates.direction.getValue().equals(Directions.Normal) ? (this.frame.getY() + Kaotik.FONT_MANAGER.getHeight() + 1.0f - ((ElementCoordinates.chatMove.getValue() && ElementCoordinates.mc.field_71462_r instanceof GuiChat) ? 11 : 0)) : (this.frame.getY() - ((ElementCoordinates.chatMove.getValue() && ElementCoordinates.mc.field_71462_r instanceof GuiChat) ? 12 : 0)), ModuleColor.getActualColor());
    }
    
    public String getCoordinatesText() {
        return (ElementCoordinates.direction.getValue().equals(Directions.Merged) ? (this.getDirectionName() + " " + ElementCoordinates.firstSymbol.getValue() + this.getColor() + this.getFacing(ElementCoordinates.mc.field_71439_g.func_174811_aO().func_176610_l()) + ChatFormatting.RESET + ElementCoordinates.secondSymbol.getValue() + " ") : "") + "X: " + this.getColor() + this.format.format(ElementCoordinates.mc.field_71439_g.field_70165_t) + ChatFormatting.RESET + " Y: " + this.getColor() + this.format.format(ElementCoordinates.mc.field_71439_g.field_70163_u) + ChatFormatting.RESET + " Z: " + this.getColor() + this.format.format(ElementCoordinates.mc.field_71439_g.field_70161_v) + ChatFormatting.RESET + (ElementCoordinates.nether.getValue() ? (" " + ElementCoordinates.firstSymbol.getValue() + this.getColor() + this.format.format((ElementCoordinates.mc.field_71439_g.field_71093_bK == -1) ? (ElementCoordinates.mc.field_71439_g.field_70165_t * 8.0) : (ElementCoordinates.mc.field_71439_g.field_70165_t / 8.0)) + ChatFormatting.RESET + ", " + this.getColor() + this.format.format((ElementCoordinates.mc.field_71439_g.field_71093_bK == -1) ? (ElementCoordinates.mc.field_71439_g.field_70165_t * 8.0) : (ElementCoordinates.mc.field_71439_g.field_70165_t / 8.0)) + ChatFormatting.RESET + ElementCoordinates.secondSymbol.getValue()) : "");
    }
    
    private String getDirectionName() {
        return ElementCoordinates.mc.field_71439_g.func_174811_aO().func_176610_l().substring(0, 1).toUpperCase() + ElementCoordinates.mc.field_71439_g.func_174811_aO().func_176610_l().substring(1).toLowerCase();
    }
    
    public ChatFormatting getColor() {
        if (ElementCoordinates.color.getValue().equals(Colors.White)) {
            return ChatFormatting.WHITE;
        }
        if (ElementCoordinates.color.getValue().equals(Colors.Gray)) {
            return ChatFormatting.GRAY;
        }
        return ChatFormatting.RESET;
    }
    
    private String getFacing(final String input) {
        final String lowerCase = input.toLowerCase();
        switch (lowerCase) {
            case "north": {
                return "-Z";
            }
            case "east": {
                return "+X";
            }
            case "south": {
                return "+Z";
            }
            default: {
                return "-X";
            }
        }
    }
    
    static {
        ElementCoordinates.color = new ValueEnum("Color", "Color", "The color of the Coordinates.", Colors.Gray);
        ElementCoordinates.direction = new ValueEnum("Direction", "Direction", "Renders the direction you are facing on screen.", Directions.Normal);
        ElementCoordinates.nether = new ValueBoolean("Opposite", "Opposite", "Renders the coordinates of the opposite dimension that you are currently in.", true);
        ElementCoordinates.firstSymbol = new ValueString("FirstSymbol", "FirstSymbol", "The first character to be rendered on the coordinates.", "[");
        ElementCoordinates.secondSymbol = new ValueString("SecondSymbol", "SecondSymbol", "The first character to be rendered on the coordinates.", "]");
        ElementCoordinates.chatMove = new ValueBoolean("ChatMove", "ChatMove", "Moves the coordinates above the chat textbox when it is opened.", true);
    }
    
    public enum Colors
    {
        Normal, 
        White, 
        Gray;
    }
    
    public enum Directions
    {
        None, 
        Normal, 
        Merged;
    }
}
