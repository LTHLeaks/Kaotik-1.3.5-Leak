package com.mawuote.client.gui.click.components.impl;

import com.mawuote.client.gui.click.components.*;
import com.mawuote.api.manager.value.impl.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import com.mawuote.*;
import com.mawuote.client.modules.client.*;

public class ModeComponent extends Component
{
    ValueEnum setting;
    private int enumSize;
    
    public ModeComponent(final ValueEnum setting, final ModuleComponent parent, final int offset) {
        super(parent.getParent().getX(), parent.getParent().getY() + offset, parent.getParent());
        this.setting = setting;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY) {
        super.drawScreen(mouseX, mouseY);
        Gui.func_73734_a(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + 14, new Color(40, 40, 40).getRGB());
        Gui.func_73734_a(this.getX() - 1, this.getY(), this.getX(), this.getY() + 14, new Color(30, 30, 30).getRGB());
        Gui.func_73734_a(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth() + 1, this.getY() + 14, new Color(70, 10, 10).getRGB());
        Kaotik.FONT_MANAGER.drawString(this.setting.getName(), (float)(this.getX() + 3), (float)(this.getY() + 3), Color.WHITE);
        Kaotik.FONT_MANAGER.drawString(this.setting.getValue().toString(), this.getX() + this.getWidth() - 3 - Kaotik.FONT_MANAGER.getStringWidth(this.setting.getValue().toString()), (float)(this.getY() + 3), ModuleColor.getActualColor());
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.getHeight()) {
            if (mouseButton == 0) {
                final int maxIndex = this.setting.getValues().size() - 1;
                ++this.enumSize;
                if (this.enumSize > maxIndex) {
                    this.enumSize = 0;
                }
                this.setting.setValue(this.setting.getValues().get(this.enumSize));
            }
            else if (mouseButton == 1) {
                final int maxIndex = this.setting.getValues().size() - 1;
                --this.enumSize;
                if (this.enumSize < 0) {
                    this.enumSize = maxIndex;
                }
                this.setting.setValue(this.setting.getValues().get(this.enumSize));
            }
        }
    }
}
