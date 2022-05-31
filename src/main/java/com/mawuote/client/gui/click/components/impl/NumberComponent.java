package com.mawuote.client.gui.click.components.impl;

import com.mawuote.client.gui.click.components.*;
import com.mawuote.api.manager.value.impl.*;
import com.mawuote.client.modules.client.*;
import com.mawuote.*;
import org.lwjgl.opengl.*;
import com.mawuote.api.utilities.math.*;
import org.lwjgl.input.*;
import java.awt.*;
import java.awt.datatransfer.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class NumberComponent extends Component
{
    ValueNumber setting;
    private double sliderWidth;
    private boolean dragging;
    private boolean typing;
    private String currentString;
    private boolean selecting;
    private final TimerUtils backTimer;
    private boolean undoing;
    private final TimerUtils timer;
    AnimationUtils animationUtils;
    boolean animated;
    
    public NumberComponent(final ValueNumber setting, final ModuleComponent parent, final int offset) {
        super(parent.getParent().getX(), parent.getParent().getY() + offset, parent.getParent());
        this.currentString = "";
        this.selecting = false;
        this.backTimer = new TimerUtils();
        this.undoing = false;
        this.timer = new TimerUtils();
        this.animated = false;
        this.setting = setting;
        this.dragging = false;
        this.typing = false;
        this.animationUtils = new AnimationUtils(500L, 0.0f, (float)(this.getWidth() - 1));
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY) {
        super.drawScreen(mouseX, mouseY);
        if (this.timer.hasReached(400L)) {
            this.undoing = !this.undoing;
            this.timer.reset();
        }
        Gui.func_73734_a(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + 14, new Color(40, 40, 40).getRGB());
        Gui.func_73734_a(this.getX() + 1, this.getY() + 1, this.getX() + this.getWidth() - 1, this.getY() + 13, new Color(50, 50, 50).getRGB());
        if (!this.typing) {
            Gui.func_73734_a(this.getX() + 1, this.getY() + 1, (int)(this.getX() + 1 + this.sliderWidth), this.getY() + 13, ModuleColor.getColor());
        }
        Gui.func_73734_a(this.getX() - 1, this.getY(), this.getX(), this.getY() + 14, new Color(30, 30, 30).getRGB());
        Gui.func_73734_a(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth() + 1, this.getY() + 14, new Color(30, 30, 30).getRGB());
        if (!this.typing) {
            Kaotik.FONT_MANAGER.drawString(this.setting.getName(), (float)(this.getX() + 3), (float)(this.getY() + 3), Color.WHITE);
            Kaotik.FONT_MANAGER.drawString(this.setting.getValue() + ((this.setting.getType() == 1) ? ".0" : ""), this.getX() + this.getWidth() - 3 - Kaotik.FONT_MANAGER.getStringWidth(this.setting.getValue() + ((this.setting.getType() == 1) ? ".0" : "")), (float)(this.getY() + 3), Color.WHITE);
            this.animated = false;
        }
        else {
            if (!this.animated) {
                this.animationUtils.reset();
            }
            if (!this.animationUtils.isDone()) {
                GL11.glEnable(3089);
                scissor(this.getX() + 1, this.getY() + 1, this.getWidth(), 13.0);
                drawRect((float)(this.getX() + 1), (float)(this.getY() + 1), this.getX() + 1 + (float)this.sliderWidth - this.animationUtils.getValue(), (float)(this.getY() + 13), ModuleColor.getColor());
                GL11.glDisable(3089);
            }
            this.animated = true;
            Kaotik.FONT_MANAGER.drawString(this.currentString + (this.selecting ? "" : (this.undoing ? (Kaotik.MODULE_MANAGER.isModuleEnabled("Font") ? "|" : "\u23d0") : "")), this.getX() + (this.getWidth() - 1) / 2.0f - Kaotik.FONT_MANAGER.getStringWidth(this.currentString) / 2.0f, (float)(this.getY() + 3), Color.WHITE);
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0 && mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() + 2 && mouseY <= this.getY() + this.getHeight() - 2) {
            this.dragging = true;
        }
        else if (mouseButton == 1 && mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() + 2 && mouseY <= this.getY() + this.getHeight() - 2) {
            this.typing = !this.typing;
            this.currentString = this.setting.getValue().toString();
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.dragging = false;
    }
    
    @Override
    public void update(final int mouseX, final int mouseY) {
        final double difference = Math.min(98, Math.max(0, mouseX - this.getX()));
        if (this.setting.getType() == 1) {
            this.sliderWidth = 98.0f * (this.setting.getValue().intValue() - this.setting.getMinimum().intValue()) / (this.setting.getMaximum().intValue() - this.setting.getMinimum().intValue());
            if (this.dragging) {
                if (difference == 0.0) {
                    this.setting.setValue(this.setting.getMinimum());
                }
                else {
                    final int value = (int)MathUtils.roundToPlaces(difference / 98.0 * (this.setting.getMaximum().intValue() - this.setting.getMinimum().intValue()) + this.setting.getMinimum().intValue(), 0);
                    this.setting.setValue(value);
                }
            }
        }
        else if (this.setting.getType() == 2) {
            this.sliderWidth = 98.0 * (this.setting.getValue().doubleValue() - this.setting.getMinimum().doubleValue()) / (this.setting.getMaximum().doubleValue() - this.setting.getMinimum().doubleValue());
            if (this.dragging) {
                if (difference == 0.0) {
                    this.setting.setValue(this.setting.getMinimum());
                }
                else {
                    final double value2 = MathUtils.roundToPlaces(difference / 98.0 * (this.setting.getMaximum().doubleValue() - this.setting.getMinimum().doubleValue()) + this.setting.getMinimum().doubleValue(), 2);
                    this.setting.setValue(value2);
                }
            }
        }
        else if (this.setting.getType() == 3) {
            this.sliderWidth = 98.0f * (this.setting.getValue().floatValue() - this.setting.getMinimum().floatValue()) / (this.setting.getMaximum().floatValue() - this.setting.getMinimum().floatValue());
            if (this.dragging) {
                if (difference == 0.0) {
                    this.setting.setValue(this.setting.getMinimum());
                }
                else {
                    final float value3 = (float)MathUtils.roundToPlaces(difference / 98.0 * (this.setting.getMaximum().floatValue() - this.setting.getMinimum().floatValue()) + this.setting.getMinimum().floatValue(), 2);
                    this.setting.setValue(value3);
                }
            }
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
        super.keyTyped(typedChar, keyCode);
        this.backTimer.reset();
        if (this.typing) {
            if (keyCode == 1) {
                this.selecting = false;
                return;
            }
            Label_0228: {
                if (keyCode == 28) {
                    this.updateString();
                    this.selecting = false;
                    this.typing = false;
                }
                else if (keyCode == 14) {
                    this.currentString = (this.selecting ? "" : this.removeLastCharacter(this.currentString));
                    this.selecting = false;
                }
                else {
                    Label_0162: {
                        if (keyCode == 47) {
                            if (!Keyboard.isKeyDown(157)) {
                                if (!Keyboard.isKeyDown(29)) {
                                    break Label_0162;
                                }
                            }
                            try {
                                this.currentString += Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                            }
                            catch (final Exception exception) {
                                exception.printStackTrace();
                            }
                            break Label_0228;
                        }
                    }
                    if (ChatAllowedCharacters.func_71566_a(typedChar)) {
                        this.currentString = (this.selecting ? ("" + typedChar) : (this.currentString + typedChar));
                        this.selecting = false;
                    }
                }
            }
            if (keyCode == 30 && Keyboard.isKeyDown(29)) {
                this.selecting = true;
            }
        }
    }
    
    private void updateString() {
        if (this.currentString.length() > 0) {
            if (this.setting.getType() == 1) {
                try {
                    if (Integer.parseInt(this.currentString) <= this.setting.getMaximum().intValue() && Integer.parseInt(this.currentString) >= this.setting.getMinimum().intValue()) {
                        this.setting.setValue(Integer.parseInt(this.currentString));
                    }
                    else {
                        this.setting.setValue(this.setting.getValue());
                    }
                }
                catch (final NumberFormatException e) {
                    this.setting.setValue(this.setting.getValue());
                }
            }
            else if (this.setting.getType() == 3) {
                try {
                    if (Float.parseFloat(this.currentString) <= this.setting.getMaximum().floatValue() && Float.parseFloat(this.currentString) >= this.setting.getMinimum().floatValue()) {
                        this.setting.setValue(Float.parseFloat(this.currentString));
                    }
                    else {
                        this.setting.setValue(this.setting.getValue());
                    }
                }
                catch (final NumberFormatException e) {
                    this.setting.setValue(this.setting.getValue());
                }
            }
            else if (this.setting.getType() == 2) {
                try {
                    if (Double.parseDouble(this.currentString) <= this.setting.getMaximum().doubleValue() && Double.parseDouble(this.currentString) >= this.setting.getMinimum().doubleValue()) {
                        this.setting.setValue(Double.parseDouble(this.currentString));
                    }
                    else {
                        this.setting.setValue(this.setting.getValue());
                    }
                }
                catch (final NumberFormatException e) {
                    this.setting.setValue(this.setting.getValue());
                }
            }
        }
        this.currentString = "";
    }
    
    private String removeLastCharacter(final String input) {
        if (input.length() > 0) {
            return input.substring(0, input.length() - 1);
        }
        return input;
    }
    
    public static void drawRect(float left, float top, float right, float bottom, final int color) {
        if (left < right) {
            final float j = left;
            left = right;
            right = j;
        }
        if (top < bottom) {
            final float j = top;
            top = bottom;
            bottom = j;
        }
        final float f3 = (color >> 24 & 0xFF) / 255.0f;
        final float f4 = (color >> 16 & 0xFF) / 255.0f;
        final float f5 = (color >> 8 & 0xFF) / 255.0f;
        final float f6 = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferbuilder = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.func_179131_c(f4, f5, f6, f3);
        bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        bufferbuilder.func_181662_b((double)left, (double)bottom, 0.0).func_181675_d();
        bufferbuilder.func_181662_b((double)right, (double)bottom, 0.0).func_181675_d();
        bufferbuilder.func_181662_b((double)right, (double)top, 0.0).func_181675_d();
        bufferbuilder.func_181662_b((double)left, (double)top, 0.0).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }
    
    public static void scissor(double x, double y, double width, double height) {
        final ScaledResolution sr = new ScaledResolution(Minecraft.func_71410_x());
        final double scale = sr.func_78325_e();
        y = sr.func_78328_b() - y;
        x *= scale;
        y *= scale;
        width *= scale;
        height *= scale;
        GL11.glScissor((int)x, (int)(y - height), (int)width, (int)height);
    }
}
