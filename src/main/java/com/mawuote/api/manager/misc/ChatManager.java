package com.mawuote.api.manager.misc;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.text.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.mawuote.client.modules.client.*;
import com.mojang.realmsclient.gui.*;
import com.mawuote.api.utilities.render.*;
import java.awt.*;
import net.minecraft.util.math.*;

public class ChatManager
{
    private Minecraft mc;
    private GuiNewChat gameChatGUI;
    public static String prefix;
    public static ChatManager INSTANCE;
    
    public ChatManager() {
        this.mc = Minecraft.func_71410_x();
        ChatManager.INSTANCE = this;
    }
    
    public void printChatMessage(final String message) {
        if (Minecraft.func_71410_x().field_71439_g == null) {
            return;
        }
        if (this.gameChatGUI == null) {
            this.gameChatGUI = Minecraft.func_71410_x().field_71456_v.func_146158_b();
        }
        this.gameChatGUI.func_146227_a((ITextComponent)new TextComponentString(message));
    }
    
    public void sendChatMessage(final String message) {
        if (Minecraft.func_71410_x().field_71439_g == null) {
            return;
        }
        Minecraft.func_71410_x().field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage(message));
    }
    
    public static void sendRawMessage(final String message) {
        Minecraft.func_71410_x().field_71439_g.func_145747_a((ITextComponent)new TextComponentString(message));
    }
    
    public static void printChatNotifyClient(final String message) {
        if (ModuleColor.prefixMode.getValue().equals(ModuleColor.prefixModes.Rainbow) || ModuleColor.prefixMode.getValue().equals(ModuleColor.prefixModes.Gradient)) {
            ChatManager.INSTANCE.printChatMessage((ModuleColor.prefix.getValue() ? ("§+" + ChatFormatting.GRAY + " [" + ModuleColor.getTextColor() + "Kaotik" + ChatFormatting.GRAY + "] ") : "") + "§r" + ChatFormatting.RESET + message);
        }
        else {
            ChatManager.INSTANCE.printChatMessage((ModuleColor.prefix.getValue() ? (ChatFormatting.GRAY + " [" + ModuleColor.getTextColor() + "Kaotik" + ChatFormatting.GRAY + "] ") : "") + ChatFormatting.RESET + message);
        }
    }
    
    public static void printTextComponentMessage(final TextComponentString message) {
        if (Minecraft.func_71410_x().field_71439_g == null) {
            return;
        }
        if (ChatManager.INSTANCE.gameChatGUI == null) {
            ChatManager.INSTANCE.gameChatGUI = Minecraft.func_71410_x().field_71456_v.func_146158_b();
        }
        ChatManager.INSTANCE.gameChatGUI.func_146227_a((ITextComponent)message);
    }
    
    public static void sendClientMessage(final String string, final int id) {
        if (Minecraft.func_71410_x().field_71439_g == null) {
            return;
        }
        ITextComponent component;
        if (ModuleColor.prefixMode.getValue().equals(ModuleColor.prefixModes.Rainbow) || ModuleColor.prefixMode.getValue().equals(ModuleColor.prefixModes.Gradient)) {
            component = (ITextComponent)new TextComponentString((ModuleColor.prefix.getValue() ? ("§+" + ChatFormatting.GRAY + " [" + ModuleColor.getTextColor() + "Kaotik" + ChatFormatting.GRAY + "] ") : "") + "§r" + ChatFormatting.RESET + string);
        }
        else {
            component = (ITextComponent)new TextComponentString((ModuleColor.prefix.getValue() ? (ModuleColor.getBracketColour() + " [" + ModuleColor.getTextColor() + "Kaotik" + ModuleColor.getBracketColour() + "] ") : "") + ChatFormatting.RESET + string);
        }
        Minecraft.func_71410_x().field_71456_v.func_146158_b().func_146234_a(component, id);
    }
    
    public void drawRainbowString(final String text, final float x, final float y, final boolean shadow) {
        int currentWidth = 0;
        boolean shouldRainbow = true;
        boolean shouldContinue = false;
        final int[] counterChing = { 1 };
        for (int i = 0; i < text.length(); ++i) {
            Color color;
            if (ModuleColor.prefixMode.getValue().equals(ModuleColor.prefixModes.Rainbow)) {
                color = RainbowUtils.anyRainbowColor(counterChing[0] * 150, 180, 255);
            }
            else {
                color = RainbowUtils.getGradientOffset(new Color(ModuleColor.prefixStart.getValue().getRed(), ModuleColor.prefixStart.getValue().getGreen(), ModuleColor.prefixStart.getValue().getBlue()), new Color(ModuleColor.prefixEnd.getValue().getRed(), ModuleColor.prefixEnd.getValue().getGreen(), ModuleColor.prefixEnd.getValue().getBlue()), Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (counterChing[0] * 2 + 10) * 2.0f) % 2.0f - 1.0f));
            }
            final char currentChar = text.charAt(i);
            final char nextChar = text.charAt(MathHelper.func_76125_a(i + 1, 0, text.length() - 1));
            if ((String.valueOf(currentChar) + nextChar).equals("§r")) {
                shouldRainbow = false;
            }
            else if ((String.valueOf(currentChar) + nextChar).equals("§+")) {
                shouldRainbow = true;
            }
            if (shouldContinue) {
                shouldContinue = false;
            }
            else {
                if ((String.valueOf(currentChar) + nextChar).equals("§r")) {
                    final String escapeString = text.substring(i);
                    this.drawString(escapeString, x + currentWidth, y, Color.WHITE.getRGB(), shadow);
                    break;
                }
                this.drawString(String.valueOf(currentChar).equals("§") ? "" : String.valueOf(currentChar), x + currentWidth, y, shouldRainbow ? color.getRGB() : Color.WHITE.getRGB(), shadow);
                if (String.valueOf(currentChar).equals("§")) {
                    shouldContinue = true;
                }
                currentWidth += this.getStringWidth(String.valueOf(currentChar));
                if (!String.valueOf(currentChar).equals(" ")) {
                    final int[] array = counterChing;
                    final int n = 0;
                    ++array[n];
                }
            }
        }
    }
    
    public int getStringWidth(final String text) {
        return this.mc.field_71466_p.func_78256_a(text);
    }
    
    public float drawString(final String text, final float x, final float y, final int color, final boolean shadow) {
        this.mc.field_71466_p.func_175065_a(text, x, y, color, shadow);
        return x;
    }
    
    static {
        ChatManager.prefix = ModuleColor.getBracketColour() + " [" + ModuleColor.getTextColor() + "Kaotik" + ModuleColor.getBracketColour() + "] " + ModuleColor.getTextColor();
    }
}
