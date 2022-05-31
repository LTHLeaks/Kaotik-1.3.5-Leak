package com.mawuote.client.gui.click.components;

import net.minecraft.client.*;
import com.mawuote.*;
import com.mawuote.api.manager.module.*;
import java.util.*;
import com.mawuote.api.manager.element.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import com.mawuote.client.gui.click.*;
import com.mawuote.client.gui.click.components.impl.*;

public class Frame
{
    protected static final Minecraft mc;
    ArrayList<Component> buttons;
    private String tab;
    int x;
    int y;
    int dragX;
    int dragY;
    int width;
    int height;
    boolean open;
    boolean dragging;
    
    public Frame(final ModuleCategory category, final int x, final int y) {
        this.open = true;
        this.tab = category.getName();
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 0;
        this.dragX = 0;
        this.dragY = 0;
        this.dragging = false;
        this.buttons = new ArrayList<Component>();
        int offset = 14;
        for (final Module module : Kaotik.MODULE_MANAGER.getModules(category)) {
            this.buttons.add(new ModuleComponent(module, x, y + offset, this));
            offset += 14;
        }
        this.height = offset;
        this.refreshPosition();
    }
    
    public Frame(final int x, final int y) {
        this.open = true;
        this.tab = "HUD";
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 0;
        this.dragX = 0;
        this.dragY = 0;
        this.dragging = false;
        this.buttons = new ArrayList<Component>();
        int offset = 14;
        for (final Element element : Kaotik.ELEMENT_MANAGER.getElements()) {
            this.buttons.add(new ModuleComponent(element, x, y + offset, this));
            offset += 14;
        }
        this.height = offset;
        this.refreshPosition();
    }
    
    public void drawScreen(final int mouseX, final int mouseY) {
        Gui.func_73734_a(this.x, this.y, this.x + this.width, this.y + 14, new Color(47, 8, 8).getRGB());
        Gui.func_73734_a(this.x - 1, this.y, this.x, this.y + 14, new Color(47, 8, 8).getRGB());
        Gui.func_73734_a(this.x + this.width, this.y, this.x + this.width + 1, this.y + 14, new Color(47, 8, 8).getRGB());
        if (this.open) {
            Gui.func_73734_a(this.x - 1, this.y + this.height, this.x + this.width + 1, this.y + this.height + 1, new Color(30, 29, 29).getRGB());
        }
        Kaotik.FONT_MANAGER.drawString(ClickGuiScreen.capitalize(this.tab), (float)(this.x + 3), (float)(this.y + 3), Color.WHITE);
        if (this.open) {
            for (final Component button : this.buttons) {
                button.drawScreen(mouseX, mouseY);
                button.update(mouseX, mouseY);
                if (button instanceof ModuleComponent) {
                    final ModuleComponent moduleButton = (ModuleComponent)button;
                    if (!moduleButton.isOpen()) {
                        continue;
                    }
                    for (final Component settingButton : moduleButton.getSubButtons()) {
                        settingButton.drawScreen(mouseX, mouseY);
                        settingButton.update(mouseX, mouseY);
                    }
                }
            }
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isMouseOnHeader(mouseX, mouseY) && mouseButton == 0) {
            this.setDragging(true);
            this.dragX = mouseX - this.getX();
            this.dragY = mouseY - this.getY();
        }
        if (mouseButton == 1 && this.isMouseOnHeader(mouseX, mouseY)) {
            this.open = !this.open;
            if (this.open) {
                int offset = 14;
                for (final Component ignored : this.buttons) {
                    offset += 14;
                }
                this.height = offset;
            }
            else {
                this.height = 14;
            }
        }
        for (final Component button : this.buttons) {
            button.mouseClicked(mouseX, mouseY, mouseButton);
            if (button instanceof ModuleComponent) {
                final ModuleComponent moduleButton = (ModuleComponent)button;
                if (!moduleButton.isOpen()) {
                    continue;
                }
                for (final Component settingButton : moduleButton.getSubButtons()) {
                    settingButton.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.setDragging(false);
        for (final Component button : this.buttons) {
            button.mouseReleased(mouseX, mouseY, state);
            if (button instanceof ModuleComponent) {
                final ModuleComponent moduleButton = (ModuleComponent)button;
                if (!moduleButton.isOpen()) {
                    continue;
                }
                for (final Component settingButton : moduleButton.getSubButtons()) {
                    settingButton.mouseReleased(mouseX, mouseY, state);
                }
            }
        }
    }
    
    public void keyTyped(final char typedChar, final int key) {
        for (final Component button : this.buttons) {
            button.keyTyped(typedChar, key);
            if (button instanceof ModuleComponent) {
                final ModuleComponent moduleButton = (ModuleComponent)button;
                if (!moduleButton.isOpen()) {
                    continue;
                }
                for (final Component settingButton : moduleButton.getSubButtons()) {
                    settingButton.keyTyped(typedChar, key);
                }
            }
        }
    }
    
    public void updatePosition(final int mouseX, final int mouseY) {
        if (this.dragging) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }
    }
    
    public void refreshPosition() {
        int offset = 14;
        for (final Component button : this.buttons) {
            button.setX(this.x);
            button.setY(this.y + offset);
            offset += 14;
            if (button instanceof ModuleComponent) {
                final ModuleComponent moduleButton = (ModuleComponent)button;
                if (!moduleButton.isOpen()) {
                    continue;
                }
                for (final Component settingButton : moduleButton.getSubButtons()) {
                    settingButton.setX(this.x);
                    settingButton.setY(this.y + offset);
                    if (settingButton instanceof ColorComponent) {
                        if (((ColorComponent)settingButton).open) {
                            offset += 84;
                        }
                        else {
                            offset += 14;
                        }
                    }
                    else if (settingButton instanceof PreviewComponent) {
                        if (((PreviewComponent)settingButton).open) {
                            offset += 100;
                        }
                        else {
                            offset += 14;
                        }
                    }
                    else {
                        offset += 14;
                    }
                }
            }
        }
        this.height = offset;
    }
    
    public boolean isMouseOnHeader(final int mouseX, final int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + 14;
    }
    
    public void setDragging(final boolean dragging) {
        this.dragging = dragging;
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
    
    static {
        mc = Minecraft.func_71410_x();
    }
}
