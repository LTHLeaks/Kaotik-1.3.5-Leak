package com.mawuote.client.gui.click.components;

import net.minecraft.client.*;

public class Component
{
    protected static final Minecraft mc;
    int x;
    int y;
    int width;
    int height;
    Frame parent;
    
    public Component(final int x, final int y, final Frame parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.width = parent.width;
        this.height = 14;
    }
    
    public void drawScreen(final int mouseX, final int mouseY) {
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
    }
    
    public void update(final int mouseX, final int mouseY) {
    }
    
    public Frame getParent() {
        return this.parent;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    static {
        mc = Minecraft.func_71410_x();
    }
}
