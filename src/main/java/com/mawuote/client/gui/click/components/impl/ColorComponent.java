package com.mawuote.client.gui.click.components.impl;

import com.mawuote.client.gui.click.components.*;
import com.mawuote.api.manager.value.impl.*;
import net.minecraft.util.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import com.mawuote.api.utilities.render.*;
import com.mawuote.*;
import org.lwjgl.opengl.*;
import com.mawuote.client.modules.client.*;
import net.minecraft.client.renderer.*;

public class ColorComponent extends Component
{
    ValueColor setting;
    public boolean open;
    ResourceLocation alphaBG;
    boolean hueDragging;
    float hueWidth;
    boolean saturationDragging;
    float satWidth;
    boolean brightnessDragging;
    float briWidth;
    boolean alphaDragging;
    float alphaWidth;
    
    public ColorComponent(final ValueColor setting, final ModuleComponent parent, final int offset) {
        super(parent.getParent().getX(), parent.getParent().getY() + offset, parent.getParent());
        this.open = false;
        this.alphaBG = new ResourceLocation("mawuote:alpha_texture.png");
        this.setting = setting;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY) {
        super.drawScreen(mouseX, mouseY);
        final float[] hsb = Color.RGBtoHSB(this.setting.getValue().getRed(), this.setting.getValue().getGreen(), this.setting.getValue().getBlue(), null);
        final Color color = Color.getHSBColor(hsb[0], 1.0f, 1.0f);
        Gui.func_73734_a(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + 14, new Color(10, 10, 10).getRGB());
        Gui.func_73734_a(this.getX() + this.getWidth() - 12, this.getY() + 2, this.getX() + this.getWidth() - 2, this.getY() + 12, this.setting.getValue().getRGB());
        RenderUtils.drawOutline((float)(this.getX() + this.getWidth() - 12), (float)(this.getY() + 2), (float)(this.getX() + this.getWidth() - 2), (float)(this.getY() + 12), 0.5f, new Color(99, 57, 8).getRGB());
        if (this.open) {
            Gui.func_73734_a(this.getX(), this.getY() + 14, this.getX() + this.getWidth(), this.getY() + 28, new Color(10, 10, 10).getRGB());
            for (float i = 0.0f; i + 1.0f < 96.0f; i += 0.45f) {
                RenderUtils.drawRecta(this.getX() + 2 + i, (float)(this.getY() + 16), 1.0f, 11.0f, Color.getHSBColor(i / 96.0f, 1.0f, 1.0f).getRGB());
            }
            RenderUtils.drawOutline((float)(this.getX() + 2), (float)(this.getY() + 16), (float)(this.getX() + 2 + this.getWidth() - 4), (float)(this.getY() + 27), 0.5f, new Color(99, 57, 8).getRGB());
            RenderUtils.drawRecta(this.getX() + 2 + this.hueWidth, (float)(this.getY() + 16), 1.0f, 11.0f, new Color(99, 57, 8).getRGB());
            Gui.func_73734_a(this.getX(), this.getY() + 28, this.getX() + this.getWidth(), this.getY() + 42, new Color(10, 10, 10).getRGB());
            RenderUtils.drawSidewaysGradient((float)(this.getX() + 2), (float)(this.getY() + 29), (float)(this.getWidth() - 4), 11.0f, new Color(99, 57, 8), color, 255, 255);
            RenderUtils.drawOutline((float)(this.getX() + 2), (float)(this.getY() + 29), (float)(this.getX() + 2 + this.getWidth() - 4), (float)(this.getY() + 40), 0.5f, new Color(99, 57, 8).getRGB());
            RenderUtils.drawRecta(this.getX() + 2 + this.satWidth, (float)(this.getY() + 29), 1.0f, 11.0f, new Color(99, 57, 8).getRGB());
            Gui.func_73734_a(this.getX(), this.getY() + 42, this.getX() + this.getWidth(), this.getY() + 56, new Color(10, 10, 10).getRGB());
            RenderUtils.drawSidewaysGradient((float)(this.getX() + 2), (float)(this.getY() + 42), (float)(this.getWidth() - 4), 11.0f, new Color(99, 57, 8), color, 255, 255);
            RenderUtils.drawOutline((float)(this.getX() + 2), (float)(this.getY() + 42), (float)(this.getX() + 2 + this.getWidth() - 4), (float)(this.getY() + 53), 0.5f, new Color(99, 57, 8).getRGB());
            RenderUtils.drawRecta(this.getX() + 2 + this.briWidth, (float)(this.getY() + 42), 1.0f, 11.0f, new Color(99, 57, 8).getRGB());
            Gui.func_73734_a(this.getX(), this.getY() + 56, this.getX() + this.getWidth(), this.getY() + 70, new Color(10, 10, 10).getRGB());
            this.renderAlphaBG(this.getX() + 2, this.getY() + 55, this.alphaBG);
            RenderUtils.drawSidewaysGradient((float)(this.getX() + 2), (float)(this.getY() + 55), (float)(this.getWidth() - 4), 11.0f, new Color(0, 0, 0), color, 0, 255);
            RenderUtils.drawOutline((float)(this.getX() + 2), (float)(this.getY() + 55), (float)(this.getX() + 2 + this.getWidth() - 4), (float)(this.getY() + 66), 0.5f, new Color(10, 10, 10).getRGB());
            RenderUtils.drawRecta(this.getX() + 2 + this.alphaWidth, (float)(this.getY() + 55), 1.0f, 11.0f, new Color(99, 57, 8).getRGB());
            Gui.func_73734_a(this.getX(), this.getY() + 70, this.getX() + this.getWidth(), this.getY() + 84, new Color(10, 10, 10).getRGB());
            Kaotik.FONT_MANAGER.drawString("Rainbow", (float)(this.getX() + 3), this.getY() + 78 - Kaotik.FONT_MANAGER.getHeight() / 2.0f, Color.WHITE);
            Gui.func_73734_a(this.getX() + this.getWidth() - 12, this.getY() + 72, this.getX() + this.getWidth() - 2, this.getY() + 82, new Color(10, 10, 10).getRGB());
            if (this.setting.getRainbow()) {
                RenderUtils.prepareGL();
                GL11.glShadeModel(7425);
                GL11.glEnable(2848);
                GL11.glLineWidth(2.5f);
                GL11.glBegin(1);
                GL11.glColor3f(ModuleColor.getActualColor().getRed() / 255.0f, ModuleColor.getActualColor().getGreen() / 255.0f, ModuleColor.getActualColor().getBlue() / 255.0f);
                GL11.glVertex2d((double)(this.getX() + this.getWidth() - 8), (double)(this.getY() + 80));
                GL11.glColor3f(ModuleColor.getActualColor().getRed() / 255.0f, ModuleColor.getActualColor().getGreen() / 255.0f, ModuleColor.getActualColor().getBlue() / 255.0f);
                GL11.glVertex2d((double)(this.getX() + this.getWidth() - 8 + 4), (double)(this.getY() + 74));
                GL11.glEnd();
                GL11.glBegin(1);
                GL11.glColor3f(ModuleColor.getActualColor().getRed() / 255.0f, ModuleColor.getActualColor().getGreen() / 255.0f, ModuleColor.getActualColor().getBlue() / 255.0f);
                GL11.glVertex2d((double)(this.getX() + this.getWidth() - 8), (double)(this.getY() + 80));
                GL11.glColor3f(ModuleColor.getActualColor().getRed() / 255.0f, ModuleColor.getActualColor().getGreen() / 255.0f, ModuleColor.getActualColor().getBlue() / 255.0f);
                GL11.glVertex2d((double)(this.getX() + this.getWidth() - 10), (double)(this.getY() + 77));
                GL11.glEnd();
                RenderUtils.releaseGL();
            }
        }
        Gui.func_73734_a(this.getX() - 1, this.getY(), this.getX(), this.getY() + 84, new Color(30, 30, 30).getRGB());
        Gui.func_73734_a(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth() + 1, this.getY() + 84, new Color(47, 8, 8).getRGB());
        Kaotik.FONT_MANAGER.drawString(this.setting.getName(), (float)(this.getX() + 3), (float)(this.getY() + 3), Color.WHITE);
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.getHeight() && mouseButton == 1) {
            this.open = !this.open;
        }
        if (this.isMouseOnHue(mouseX, mouseY) && mouseButton == 0 && this.open) {
            this.hueDragging = true;
        }
        else if (this.isMouseOnSat(mouseX, mouseY) && mouseButton == 0 && this.open) {
            this.saturationDragging = true;
        }
        else if (this.isMouseOnBri(mouseX, mouseY) && mouseButton == 0 && this.open) {
            this.brightnessDragging = true;
        }
        else if (this.isMouseOnAlpha(mouseX, mouseY) && mouseButton == 0 && this.open) {
            this.alphaDragging = true;
        }
        else if (this.isMouseOnRainbow(mouseX, mouseY) && mouseButton == 0 && this.open) {
            this.setting.setRainbow(!this.setting.getRainbow());
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.hueDragging = false;
        this.saturationDragging = false;
        this.brightnessDragging = false;
        this.alphaDragging = false;
    }
    
    public void renderAlphaBG(final int x, final int y, final ResourceLocation texture) {
        ColorComponent.mc.func_110434_K().func_110577_a(texture);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.func_152125_a(x, y, 0.0f, 0.0f, 104, 16, this.getWidth() - 4, 11, 104.0f, 16.0f);
        GL11.glPopMatrix();
        GlStateManager.func_179086_m(256);
    }
    
    public boolean isMouseOnHue(final int x, final int y) {
        return x > this.getX() + 2 && x < this.getX() + 2 + this.getWidth() - 4 && y > this.getY() + 16 && y < this.getY() + 27;
    }
    
    public boolean isMouseOnSat(final int x, final int y) {
        return x > this.getX() + 2 && x < this.getX() + 2 + this.getWidth() - 4 && y > this.getY() + 29 && y < this.getY() + 40;
    }
    
    public boolean isMouseOnBri(final int x, final int y) {
        return x > this.getX() + 2 && x < this.getX() + 2 + this.getWidth() - 4 && y > this.getY() + 42 && y < this.getY() + 53;
    }
    
    public boolean isMouseOnAlpha(final int x, final int y) {
        return x > this.getX() + 2 && x < this.getX() + 2 + this.getWidth() - 4 && y > this.getY() + 55 && y < this.getY() + 66;
    }
    
    public boolean isMouseOnRainbow(final int x, final int y) {
        return x > this.getX() + this.getWidth() - 12 && x < this.getX() + this.getWidth() - 2 && y > this.getY() + 72 && y < this.getY() + 82;
    }
    
    @Override
    public void update(final int mouseX, final int mouseY) {
        super.update(mouseX, mouseY);
        final float[] hsb = Color.RGBtoHSB(this.setting.getValue().getRed(), this.setting.getValue().getGreen(), this.setting.getValue().getBlue(), null);
        final double difference = Math.min(95, Math.max(0, mouseX - this.getX()));
        this.hueWidth = 95.5f * (hsb[0] * 360.0f / 360.0f);
        this.satWidth = 94.5f * (hsb[1] * 360.0f / 360.0f);
        this.briWidth = 94.5f * (hsb[2] * 360.0f / 360.0f);
        this.alphaWidth = 94.5f * (this.setting.getValue().getAlpha() / 255.0f);
        this.changeColor(difference, new Color(Color.HSBtoRGB((float)(difference / 95.0 * 360.0 / 360.0), hsb[1], hsb[2])), new Color(Color.HSBtoRGB(0.0f, hsb[1], hsb[2])), this.hueDragging);
        this.changeColor(difference, new Color(Color.HSBtoRGB(hsb[0], (float)(difference / 95.0 * 360.0 / 360.0), hsb[2])), new Color(Color.HSBtoRGB(hsb[0], 0.0f, hsb[2])), this.saturationDragging);
        this.changeColor(difference, new Color(Color.HSBtoRGB(hsb[0], hsb[1], (float)(difference / 95.0 * 360.0 / 360.0))), new Color(Color.HSBtoRGB(hsb[0], hsb[1], 0.0f)), this.brightnessDragging);
        this.changeAlpha(difference, (float)(difference / 95.0 * 255.0 / 255.0), this.alphaDragging);
    }
    
    private void changeColor(final double difference, final Color color, final Color zeroColor, final boolean dragging) {
        if (dragging) {
            if (difference == 0.0) {
                this.setting.setValue(new Color(zeroColor.getRed(), zeroColor.getGreen(), zeroColor.getBlue(), this.setting.getValue().getAlpha()));
            }
            else {
                this.setting.setValue(new Color(color.getRed(), color.getGreen(), color.getBlue(), this.setting.getValue().getAlpha()));
            }
        }
    }
    
    private void changeAlpha(final double difference, final float alpha, final boolean dragging) {
        if (dragging) {
            if (difference == 0.0) {
                this.setting.setValue(new Color(this.setting.getValue().getRed(), this.setting.getValue().getGreen(), this.setting.getValue().getBlue(), 0));
            }
            else {
                this.setting.setValue(new Color(this.setting.getValue().getRed(), this.setting.getValue().getGreen(), this.setting.getValue().getBlue(), (int)(alpha * 255.0f)));
            }
        }
    }
}
