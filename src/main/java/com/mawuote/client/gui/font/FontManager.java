package com.mawuote.client.gui.font;

import com.mawuote.api.utilities.*;
import com.mawuote.*;
import java.awt.*;
import java.io.*;

public class FontManager implements IMinecraft
{
    public FontRenderer FONT_RENDERER;
    
    public void load() {
        this.FONT_RENDERER = new FontRenderer(getFont("Lato-Medium.ttf", 40.0f));
    }
    
    public float drawString(final String text, final float x, final float y, final Color color) {
        if (Kaotik.MODULE_MANAGER.isModuleEnabled("Font")) {
            return (float)this.FONT_RENDERER.drawStringWithShadow(text, x, y, color.getRGB());
        }
        return (float)FontManager.mc.field_71466_p.func_175063_a(text, x, y, color.getRGB());
    }
    
    public float getStringWidth(final String text) {
        if (Kaotik.MODULE_MANAGER.isModuleEnabled("Font")) {
            return (float)this.FONT_RENDERER.getStringWidth(text);
        }
        return (float)FontManager.mc.field_71466_p.func_78256_a(text);
    }
    
    public float getHeight() {
        if (Kaotik.MODULE_MANAGER.isModuleEnabled("Font")) {
            return (float)this.FONT_RENDERER.getHeight();
        }
        return (float)FontManager.mc.field_71466_p.field_78288_b;
    }
    
    private static Font getFont(final String fontName, final float size) {
        try {
            final InputStream inputStream = FontManager.class.getResourceAsStream("/assets/mawuote/fonts/" + fontName);
            Font awtClientFont = Font.createFont(0, inputStream);
            awtClientFont = awtClientFont.deriveFont(0, size);
            inputStream.close();
            return awtClientFont;
        }
        catch (final Exception e) {
            e.printStackTrace();
            return new Font("default", 0, (int)size);
        }
    }
}
