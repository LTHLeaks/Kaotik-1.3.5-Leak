package com.mawuote.client.gui.click;

import com.mawuote.client.gui.click.components.*;
import com.mawuote.api.manager.module.*;
import net.minecraft.client.gui.*;
import java.util.*;
import java.io.*;
import com.mawuote.client.modules.client.*;
import org.lwjgl.input.*;

public class ClickGuiScreen extends GuiScreen
{
    public static ClickGuiScreen INSTANCE;
    public ArrayList<Frame> frames;
    
    public ClickGuiScreen() {
        this.frames = new ArrayList<Frame>();
        int offset = 0;
        for (final ModuleCategory category : ModuleCategory.values()) {
            if (category != ModuleCategory.HUD) {
                this.frames.add(new Frame(category, 10 + offset, 20));
                offset += 124;
            }
        }
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        super.func_73863_a(mouseX, mouseY, partialTicks);
        final ScaledResolution resolution = new ScaledResolution(this.field_146297_k);
        for (final Frame panel : this.frames) {
            panel.drawScreen(mouseX, mouseY);
            panel.updatePosition(mouseX, mouseY);
            panel.refreshPosition();
            this.mouseScroll();
        }
    }
    
    public void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        for (final Frame panel : this.frames) {
            panel.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
    
    public void func_146286_b(final int mouseX, final int mouseY, final int state) {
        super.func_146286_b(mouseX, mouseY, state);
        for (final Frame panel : this.frames) {
            panel.mouseReleased(mouseX, mouseY, state);
        }
    }
    
    public void func_73869_a(final char typedChar, final int key) throws IOException {
        super.func_73869_a(typedChar, key);
        for (final Frame panel : this.frames) {
            panel.keyTyped(typedChar, key);
        }
    }
    
    public void func_146281_b() {
        super.func_146281_b();
        ModuleGUI.INSTANCE.disable();
    }
    
    public boolean func_73868_f() {
        return false;
    }
    
    public void mouseScroll() {
        final int scroll = Mouse.getDWheel();
        for (final Frame panel : this.frames) {
            if (scroll < 0) {
                panel.setY(panel.getY() - ModuleGUI.INSTANCE.scrollSpeed.getValue().intValue());
            }
            else {
                if (scroll <= 0) {
                    continue;
                }
                panel.setY(panel.getY() + ModuleGUI.INSTANCE.scrollSpeed.getValue().intValue());
            }
        }
    }
    
    public static String capitalize(final String input) {
        return input;
    }
    
    static {
        ClickGuiScreen.INSTANCE = new ClickGuiScreen();
    }
}
