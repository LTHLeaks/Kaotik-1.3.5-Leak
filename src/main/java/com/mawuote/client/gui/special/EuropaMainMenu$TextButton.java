package com.mawuote.client.gui.special;

import net.minecraft.client.gui.*;
import com.mawuote.*;
import net.minecraft.client.*;
import java.awt.*;
import com.mawuote.api.utilities.render.*;

private static class TextButton extends GuiButton
{
    public TextButton(final int buttonId, final int x, final int y, final String buttonText) {
        super(buttonId, x, y, (int)Kaotik.FONT_MANAGER.getStringWidth(buttonText), (int)Kaotik.FONT_MANAGER.getHeight(), buttonText);
    }
    
    public void func_191745_a(final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks) {
        if (this.field_146125_m) {
            this.field_146124_l = true;
            this.field_146123_n = (mouseX >= this.field_146128_h - Kaotik.FONT_MANAGER.getStringWidth(this.field_146126_j) / 2.0f && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g);
            this.func_73733_a(this.field_146128_h - 40 + (this.field_146123_n ? -2 : 0), this.field_146129_i - 5 + (this.field_146123_n ? -2 : 0), this.field_146128_h + 40 + (this.field_146123_n ? 2 : 0), (int)(this.field_146129_i + Kaotik.FONT_MANAGER.getHeight() + 5.0f + (this.field_146123_n ? 2 : 0)), new Color(227, 126, 95).getRGB(), new Color(48, 150, 196).getRGB());
            RenderUtils.drawOutlineLine(this.field_146128_h - 40 + (this.field_146123_n ? -2 : 0), this.field_146129_i - 5 + (this.field_146123_n ? -2 : 0), this.field_146128_h + 40 + (this.field_146123_n ? 2 : 0), this.field_146129_i + Kaotik.FONT_MANAGER.getHeight() + 5.0f + (this.field_146123_n ? 2 : 0), 2.0f, new Color(0, 0, 0).getRGB());
            Kaotik.FONT_MANAGER.drawString(this.field_146126_j, (float)(this.field_146128_h - (int)Kaotik.FONT_MANAGER.getStringWidth(this.field_146126_j) / 2), (float)this.field_146129_i, Color.WHITE);
        }
    }
    
    public boolean func_146116_c(final Minecraft mc, final int mouseX, final int mouseY) {
        return this.field_146124_l && this.field_146125_m && mouseX >= this.field_146128_h - Kaotik.FONT_MANAGER.getStringWidth(this.field_146126_j) / 2.0f && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g;
    }
}
