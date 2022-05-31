package com.mawuote.client.gui.click.components.impl;

import com.mawuote.client.gui.click.components.*;
import com.mawuote.api.manager.value.impl.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import com.mawuote.api.utilities.render.*;
import org.lwjgl.opengl.*;
import com.mawuote.client.modules.client.*;
import com.mawuote.*;

public class BooleanComponent extends Component
{
    ValueBoolean setting;
    
    public BooleanComponent(final ValueBoolean setting, final ModuleComponent parent, final int offset) {
        super(parent.getParent().getX(), parent.getParent().getY() + offset, parent.getParent());
        this.setting = setting;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY) {
        super.drawScreen(mouseX, mouseY);
        Gui.func_73734_a(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + 14, new Color(40, 40, 40).getRGB());
        Gui.func_73734_a(this.getX() + this.getWidth() - 12, this.getY() + 2, this.getX() + this.getWidth() - 2, this.getY() + 12, new Color(30, 30, 30).getRGB());
        if (this.setting.getValue()) {
            RenderUtils.prepareGL();
            GL11.glShadeModel(7425);
            GL11.glEnable(2848);
            GL11.glLineWidth(2.5f);
            GL11.glBegin(1);
            GL11.glColor3f(ModuleColor.getActualColor().getRed() / 255.0f, ModuleColor.getActualColor().getGreen() / 255.0f, ModuleColor.getActualColor().getBlue() / 255.0f);
            GL11.glVertex2d((double)(this.getX() + this.getWidth() - 8), (double)(this.getY() + 10));
            GL11.glColor3f(ModuleColor.getActualColor().getRed() / 255.0f, ModuleColor.getActualColor().getGreen() / 255.0f, ModuleColor.getActualColor().getBlue() / 255.0f);
            GL11.glVertex2d((double)(this.getX() + this.getWidth() - 8 + 4), (double)(this.getY() + 4));
            GL11.glEnd();
            GL11.glBegin(1);
            GL11.glColor3f(ModuleColor.getActualColor().getRed() / 255.0f, ModuleColor.getActualColor().getGreen() / 255.0f, ModuleColor.getActualColor().getBlue() / 255.0f);
            GL11.glVertex2d((double)(this.getX() + this.getWidth() - 8), (double)(this.getY() + 10));
            GL11.glColor3f(ModuleColor.getActualColor().getRed() / 255.0f, ModuleColor.getActualColor().getGreen() / 255.0f, ModuleColor.getActualColor().getBlue() / 255.0f);
            GL11.glVertex2d((double)(this.getX() + this.getWidth() - 10), (double)(this.getY() + 7));
            GL11.glEnd();
            RenderUtils.releaseGL();
        }
        Gui.func_73734_a(this.getX() - 1, this.getY(), this.getX(), this.getY() + 14, new Color(30, 30, 30).getRGB());
        Gui.func_73734_a(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth() + 1, this.getY() + 14, new Color(30, 30, 30).getRGB());
        Kaotik.FONT_MANAGER.drawString(this.setting.getName(), (float)(this.getX() + 3), (float)(this.getY() + 3), Color.WHITE);
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0 && mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.getHeight()) {
            this.setting.setValue(!this.setting.getValue());
        }
    }
}
