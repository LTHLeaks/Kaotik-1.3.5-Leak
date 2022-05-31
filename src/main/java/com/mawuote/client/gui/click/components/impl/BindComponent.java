package com.mawuote.client.gui.click.components.impl;

import com.mawuote.client.gui.click.components.*;
import com.mawuote.api.manager.value.impl.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import com.mawuote.*;
import org.lwjgl.input.*;

public class BindComponent extends Component
{
    private boolean binding;
    ValueBind setting;
    
    public BindComponent(final ValueBind setting, final ModuleComponent parent, final int offset) {
        super(parent.getParent().getX(), parent.getParent().getY() + offset, parent.getParent());
        this.setting = setting;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY) {
        super.drawScreen(mouseX, mouseY);
        Gui.func_73734_a(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + 14, new Color(40, 40, 40).getRGB());
        Gui.func_73734_a(this.getX() - 1, this.getY(), this.getX(), this.getY() + 14, new Color(30, 30, 30).getRGB());
        Gui.func_73734_a(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth() + 1, this.getY() + 14, new Color(30, 30, 30).getRGB());
        Gui.func_73734_a(this.getX() + 1, this.getY() + 1, this.getX() + this.getWidth() - 1, this.getY() + 13, new Color(30, 30, 30).getRGB());
        Kaotik.FONT_MANAGER.drawString(this.setting.getName(), (float)(this.getX() + 3), (float)(this.getY() + 3), Color.WHITE);
        Kaotik.FONT_MANAGER.drawString(this.binding ? "[...]" : ("[" + Keyboard.getKeyName(this.setting.getValue()).toUpperCase() + "]"), this.getX() + this.getWidth() - 3 - Kaotik.FONT_MANAGER.getStringWidth(this.binding ? "[...]" : ("[" + Keyboard.getKeyName(this.setting.getValue()).toUpperCase() + "]")), (float)(this.getY() + 3), Color.LIGHT_GRAY);
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0 && mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.getHeight()) {
            this.binding = !this.binding;
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
        super.keyTyped(typedChar, keyCode);
        if (this.binding) {
            if (keyCode == 211) {
                this.setting.setValue(0);
            }
            else if (keyCode != 1) {
                this.setting.setValue(keyCode);
            }
            this.binding = false;
        }
    }
}
