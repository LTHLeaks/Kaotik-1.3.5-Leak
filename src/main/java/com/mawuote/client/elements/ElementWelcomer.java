package com.mawuote.client.elements;

import com.mawuote.api.manager.element.*;
import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.event.impl.render.*;
import com.mawuote.*;
import net.minecraft.client.gui.*;
import com.mojang.realmsclient.gui.*;
import com.mawuote.client.modules.client.*;
import java.text.*;
import java.util.*;
import java.time.temporal.*;
import java.time.*;

public class ElementWelcomer extends Element
{
    public static ValueEnum mode;
    public static ValueBoolean center;
    public static ValueString customValue;
    public static ValueEnum nameColor;
    public static ValueBoolean emoji;
    public static ValueString emojiValue;
    
    public ElementWelcomer() {
        super("Welcomer", "Renders a nice greeting message.");
    }
    
    @Override
    public void onRender2D(final EventRender2D event) {
        this.frame.setWidth(Kaotik.FONT_MANAGER.getStringWidth(this.getText()));
        this.frame.setHeight(Kaotik.FONT_MANAGER.getHeight());
        final ScaledResolution resolution = new ScaledResolution(ElementWelcomer.mc);
        Kaotik.FONT_MANAGER.drawString(this.getText(), ElementWelcomer.center.getValue() ? (resolution.func_78326_a() / 2.0f - Kaotik.FONT_MANAGER.getStringWidth(this.getText()) / 2.0f) : this.frame.getX(), this.frame.getY(), ModuleColor.getActualColor());
    }
    
    public String getText() {
        return this.getWelcomeMessage() + (ElementWelcomer.emoji.getValue() ? (" " + ElementWelcomer.emojiValue.getValue()) : "");
    }
    
    public String getWelcomeMessage() {
        switch (ElementWelcomer.mode.getValue()) {
            case Short: {
                return "Greetings, " + this.getNameColor() + this.getPlayerName() + ChatFormatting.RESET + "!";
            }
            case Time: {
                return this.getTimeOfDay() + ", " + this.getNameColor() + this.getPlayerName() + ChatFormatting.RESET + "!";
            }
            case Holiday: {
                return this.getHoliday() + ", " + this.getNameColor() + this.getPlayerName() + ChatFormatting.RESET + "!";
            }
            case Hebrew: {
                return "Shalom, " + this.getNameColor() + this.getPlayerName() + ChatFormatting.RESET + "!";
            }
            case Long: {
                return "Welcome to Kaotik, " + this.getNameColor() + this.getPlayerName() + ChatFormatting.RESET + "!";
            }
            case Custom: {
                return ElementWelcomer.customValue.getValue().replaceAll("<player>", this.getNameColor() + this.getPlayerName() + ChatFormatting.RESET);
            }
            default: {
                return "Hello, " + this.getNameColor() + this.getPlayerName() + ChatFormatting.RESET + "!";
            }
        }
    }
    
    private ChatFormatting getNameColor() {
        if (ElementWelcomer.nameColor.getValue().equals(NameColors.White)) {
            return ChatFormatting.WHITE;
        }
        if (ElementWelcomer.nameColor.getValue().equals(NameColors.Gray)) {
            return ChatFormatting.GRAY;
        }
        return ChatFormatting.RESET;
    }
    
    public String getPlayerName() {
        if (Kaotik.MODULE_MANAGER.isModuleEnabled("StreamerMode") && ModuleStreamerMode.hideYou.getValue()) {
            return ModuleStreamerMode.yourName.getValue();
        }
        return ElementWelcomer.mc.field_71439_g.func_70005_c_();
    }
    
    public String getTimeOfDay() {
        final Calendar calendar = Calendar.getInstance();
        final int timeOfDay = calendar.get(11);
        if (timeOfDay < 12) {
            return "Good morning";
        }
        if (timeOfDay < 16) {
            return "Good afternoon";
        }
        if (timeOfDay < 21) {
            return "Good evening";
        }
        return "Good night";
    }
    
    public String getHoliday() {
        final int month = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        final int day = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
        switch (month) {
            case 1: {
                if (day == 1) {
                    return "Happy New Years";
                }
            }
            case 2: {
                if (day == 14) {
                    return "Happy Valentines Day";
                }
                break;
            }
            case 10: {
                if (day == 31) {
                    return "Happy Halloween";
                }
                break;
            }
            case 11: {
                final LocalDate thanksGiving = Year.of(Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()))).atMonth(Month.NOVEMBER).atDay(1).with(TemporalAdjusters.lastInMonth(DayOfWeek.WEDNESDAY));
                if (thanksGiving.getDayOfMonth() == day) {
                    return "Happy Thanksgiving";
                }
            }
            case 12: {
                if (day == 25) {
                    return "Merry Christmas";
                }
                break;
            }
        }
        return "No holiday is currently going on";
    }
    
    static {
        ElementWelcomer.mode = new ValueEnum("Mode", "Mode", "The mode for the Welcomer.", Modes.Shorter);
        ElementWelcomer.center = new ValueBoolean("Center", "Center", "Makes the Welcomer be positioned to the center.", true);
        ElementWelcomer.customValue = new ValueString("CustomValue", "CustomValue", "The value for the Custom mode.", "Hello, <player>!");
        ElementWelcomer.nameColor = new ValueEnum("NameColor", "NameColor", "The color for thet player's name.", NameColors.Normal);
        ElementWelcomer.emoji = new ValueBoolean("Emoji", "Emoji", "Renders a nice face after the welcomer text.", true);
        ElementWelcomer.emojiValue = new ValueString("EmojiValue", "EmojiValue", "The value for the Emoji.", ">:)");
    }
    
    public enum Modes
    {
        Shorter, 
        Short, 
        Holiday, 
        Time, 
        Hebrew, 
        Long, 
        Custom;
    }
    
    public enum NameColors
    {
        Normal, 
        White, 
        Gray;
    }
}
