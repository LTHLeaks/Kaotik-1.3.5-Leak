package com.mawuote.client.gui.special;

import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import com.mawuote.*;
import org.lwjgl.opengl.*;
import java.awt.image.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import java.awt.*;
import com.mawuote.api.utilities.render.*;

public class EuropaMainMenu extends GuiScreen
{
    private ResourceLocation resourceLocation;
    private int y;
    private int x;
    private int singleplayerWidth;
    private int multiplayerWidth;
    private int settingsWidth;
    private int exitWidth;
    private int textHeight;
    private float xOffset;
    private float yOffset;
    
    public EuropaMainMenu() {
        this.resourceLocation = new ResourceLocation("mawuote:mainmenu.png");
    }
    
    public void func_73866_w_() {
        this.x = this.field_146294_l / 2;
        this.y = this.field_146295_m / 4 + 48;
        this.field_146292_n.add(new TextButton(0, this.x, this.y + 22, "Solo"));
        this.field_146292_n.add(new TextButton(1, this.x, this.y + 44, "Hop in CC"));
        this.field_146292_n.add(new TextButton(2, this.x, this.y + 66, "Options"));
        this.field_146292_n.add(new TextButton(2, this.x, this.y + 88, "Close Kaotik"));
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_179103_j(7425);
        GlStateManager.func_179103_j(7424);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
    }
    
    public void func_73876_c() {
        super.func_73876_c();
    }
    
    public void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) {
        if (isHovered(this.x - (int)Kaotik.FONT_MANAGER.getStringWidth("Singleplayer") / 2, this.y + 20, (int)Kaotik.FONT_MANAGER.getStringWidth("Singleplayer"), (int)Kaotik.FONT_MANAGER.getHeight(), mouseX, mouseY)) {
            this.field_146297_k.func_147108_a((GuiScreen)new GuiWorldSelection((GuiScreen)this));
        }
        else if (isHovered(this.x - (int)Kaotik.FONT_MANAGER.getStringWidth("Multiplayer") / 2, this.y + 44, (int)Kaotik.FONT_MANAGER.getStringWidth("Multiplayer"), (int)Kaotik.FONT_MANAGER.getHeight(), mouseX, mouseY)) {
            this.field_146297_k.func_147108_a((GuiScreen)new GuiMultiplayer((GuiScreen)this));
        }
        else if (isHovered(this.x - (int)Kaotik.FONT_MANAGER.getStringWidth("Options") / 2, this.y + 66, (int)Kaotik.FONT_MANAGER.getStringWidth("Options"), (int)Kaotik.FONT_MANAGER.getHeight(), mouseX, mouseY)) {
            this.field_146297_k.func_147108_a((GuiScreen)new GuiOptions((GuiScreen)this, this.field_146297_k.field_71474_y));
        }
        else if (isHovered(this.x - (int)Kaotik.FONT_MANAGER.getStringWidth("Quit") / 2, this.y + 88, (int)Kaotik.FONT_MANAGER.getStringWidth("Quit"), (int)Kaotik.FONT_MANAGER.getHeight(), mouseX, mouseY)) {
            this.field_146297_k.func_71400_g();
        }
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.xOffset = -1.0f * ((mouseX - this.field_146294_l / 2.0f) / (this.field_146294_l / 32.0f));
        this.yOffset = -1.0f * ((mouseY - this.field_146295_m / 2.0f) / (this.field_146295_m / 18.0f));
        this.x = this.field_146294_l / 2;
        this.y = this.field_146295_m / 4 + 48;
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        this.field_146297_k.func_110434_K().func_110577_a(this.resourceLocation);
        drawCompleteImage(-16.0f + this.xOffset, -9.0f + this.yOffset, (float)(this.field_146294_l + 32), (float)(this.field_146295_m + 18));
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }
    
    public static void drawCompleteImage(final float posX, final float posY, final float width, final float height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(posX, posY, 0.0f);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(0.0f, height, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f(width, height, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f(width, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
    
    public BufferedImage parseBackground(final BufferedImage background) {
        int width;
        int srcWidth;
        int srcHeight;
        int height;
        for (width = 1920, srcWidth = background.getWidth(), srcHeight = background.getHeight(), height = 1080; width < srcWidth || height < srcHeight; width *= 2, height *= 2) {}
        final BufferedImage imgNew = new BufferedImage(width, height, 2);
        final Graphics g = imgNew.getGraphics();
        g.drawImage(background, 0, 0, null);
        g.dispose();
        return imgNew;
    }
    
    public static boolean isHovered(final int x, final int y, final int width, final int height, final int mouseX, final int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY < y + height;
    }
    
    public static boolean isCustomFont() {
        return Kaotik.getModuleManager().isModuleEnabled("Font");
    }
    
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
}
