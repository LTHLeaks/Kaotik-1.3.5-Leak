package com.mawuote.client.gui.click.components.impl;

import com.mawuote.client.gui.click.components.*;
import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.utilities.math.*;
import net.minecraftforge.common.*;
import net.minecraft.client.gui.*;
import com.mawuote.*;
import com.mawuote.client.modules.client.*;
import org.lwjgl.input.*;
import java.awt.*;
import java.awt.datatransfer.*;
import net.minecraft.util.*;

public class StringComponent extends Component
{
    ValueString setting;
    private boolean listening;
    private String currentString;
    private final TimerUtils timer;
    private final TimerUtils backTimer;
    private final TimerUtils deleteTimer;
    private boolean selecting;
    private boolean undoing;
    
    public StringComponent(final ValueString setting, final ModuleComponent parent, final int offset) {
        super(parent.getParent().getX(), parent.getParent().getY() + offset, parent.getParent());
        this.currentString = "";
        this.timer = new TimerUtils();
        this.backTimer = new TimerUtils();
        this.deleteTimer = new TimerUtils();
        this.selecting = false;
        this.undoing = false;
        this.setting = setting;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY) {
        super.drawScreen(mouseX, mouseY);
        if (this.timer.hasReached(400L)) {
            this.undoing = !this.undoing;
            this.timer.reset();
        }
        Gui.func_73734_a(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + 14, new Color(40, 40, 40).getRGB());
        Gui.func_73734_a(this.getX() - 1, this.getY(), this.getX(), this.getY() + 14, new Color(30, 30, 30).getRGB());
        Gui.func_73734_a(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth() + 1, this.getY() + 14, new Color(30, 30, 30).getRGB());
        Gui.func_73734_a(this.getX() + 1, this.getY() + 1, this.getX() + this.getWidth() - 1, this.getY() + 13, new Color(30, 30, 30).getRGB());
        if (this.selecting) {
            Gui.func_73734_a(this.getX() + 3, this.getY() + 3, (int)(this.getX() + 3 + Kaotik.FONT_MANAGER.getStringWidth(this.currentString)), (int)(this.getY() + Kaotik.FONT_MANAGER.getHeight() + 3.0f), new Color(ModuleColor.getActualColor().getRed(), ModuleColor.getActualColor().getGreen(), ModuleColor.getActualColor().getBlue(), 100).getRGB());
        }
        if (this.listening) {
            Kaotik.FONT_MANAGER.drawString(this.currentString + (this.selecting ? "" : (this.undoing ? (Kaotik.MODULE_MANAGER.isModuleEnabled("Font") ? "|" : "\u23d0") : "")), (float)(this.getX() + 3), (float)(this.getY() + 3), Color.LIGHT_GRAY);
        }
        else {
            Kaotik.FONT_MANAGER.drawString(this.setting.getValue(), (float)(this.getX() + 3), (float)(this.getY() + 3), Color.LIGHT_GRAY);
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0 && mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.getHeight()) {
            this.listening = !this.listening;
            this.currentString = this.setting.getValue();
        }
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
        super.keyTyped(typedChar, keyCode);
        this.backTimer.reset();
        if (this.listening) {
            if (keyCode == 1) {
                this.selecting = false;
                return;
            }
            Label_0228: {
                if (keyCode == 28) {
                    this.updateString();
                    this.selecting = false;
                    this.listening = false;
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
            this.setting.setValue(this.currentString);
        }
        this.currentString = "";
    }
    
    private String removeLastCharacter(final String input) {
        if (input.length() > 0) {
            return input.substring(0, input.length() - 1);
        }
        return input;
    }
}
