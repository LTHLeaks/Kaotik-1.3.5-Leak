package com.mawuote.client.gui.hud;

import net.minecraft.client.gui.*;
import com.mawuote.client.gui.click.components.*;
import com.mawuote.*;
import com.mawuote.api.manager.element.*;
import java.util.*;
import java.io.*;
import org.lwjgl.input.*;
import com.mawuote.client.modules.client.*;

public class HudEditorScreen extends GuiScreen
{
    private final ArrayList<ElementFrame> elementFrames;
    private final Frame frame;
    
    public HudEditorScreen() {
        this.elementFrames = new ArrayList<ElementFrame>();
        this.frame = new Frame(20, 20);
        for (final Element element : Kaotik.ELEMENT_MANAGER.getElements()) {
            this.addElement(element);
            element.setFrame(this.getFrame(element));
        }
    }
    
    public void addElement(final Element element) {
        this.elementFrames.add(new ElementFrame(element, 10.0f, 10.0f, 80.0f, 15.0f, this));
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        super.func_73863_a(mouseX, mouseY, partialTicks);
        this.frame.drawScreen(mouseX, mouseY);
        this.frame.updatePosition(mouseX, mouseY);
        this.frame.refreshPosition();
        this.mouseScroll();
        for (final ElementFrame frame : this.elementFrames) {
            frame.drawScreen(mouseX, mouseY, partialTicks);
        }
    }
    
    public void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        this.frame.mouseClicked(mouseX, mouseY, mouseButton);
        for (final ElementFrame frame : this.elementFrames) {
            frame.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
    
    public void func_146286_b(final int mouseX, final int mouseY, final int state) {
        super.func_146286_b(mouseX, mouseY, state);
        this.frame.mouseReleased(mouseX, mouseY, state);
        for (final ElementFrame frame : this.elementFrames) {
            frame.mouseReleased(mouseX, mouseY, state);
        }
    }
    
    public void func_73869_a(final char typedChar, final int keyCode) throws IOException {
        super.func_73869_a(typedChar, keyCode);
        this.frame.keyTyped(typedChar, keyCode);
    }
    
    public void func_146281_b() {
        super.func_146281_b();
        if (ModuleHUDEditor.INSTANCE != null) {
            ModuleHUDEditor.INSTANCE.disable();
        }
    }
    
    public boolean func_73868_f() {
        return false;
    }
    
    public void mouseScroll() {
        final int scroll = Mouse.getDWheel();
        if (scroll < 0) {
            this.frame.setY(this.frame.getY() - ModuleGUI.INSTANCE.scrollSpeed.getValue().intValue());
        }
        else if (scroll > 0) {
            this.frame.setY(this.frame.getY() + ModuleGUI.INSTANCE.scrollSpeed.getValue().intValue());
        }
    }
    
    public Frame getFrame() {
        return this.frame;
    }
    
    public ArrayList<ElementFrame> getElementFrames() {
        return this.elementFrames;
    }
    
    public ElementFrame getFrame(final Element element) {
        for (final ElementFrame frame : this.elementFrames) {
            if (!frame.getElement().equals(element)) {
                continue;
            }
            return frame;
        }
        return null;
    }
}
