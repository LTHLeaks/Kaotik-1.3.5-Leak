package com.mawuote.client.elements;

import com.mawuote.api.manager.element.*;
import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.event.impl.render.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import com.mawuote.*;
import java.awt.*;
import com.mawuote.client.modules.client.*;
import java.util.*;
import com.mojang.realmsclient.gui.*;

public class ElementArmor extends Element
{
    public static ValueBoolean percentage;
    public static ValueEnum percentageColor;
    
    public ElementArmor() {
        super("Armor", "Renders the status of your armor on screen.");
    }
    
    @Override
    public void onRender2D(final EventRender2D event) {
        super.onRender2D(event);
        GlStateManager.func_179098_w();
        this.frame.setWidth(90.0f);
        this.frame.setHeight(15.0f);
        int index = 0;
        for (final ItemStack stack : ElementArmor.mc.field_71439_g.field_71071_by.field_70460_b) {
            ++index;
            if (stack.func_190926_b()) {
                continue;
            }
            GlStateManager.func_179126_j();
            ElementArmor.mc.func_175599_af().field_77023_b = 200.0f;
            ElementArmor.mc.func_175599_af().func_180450_b(stack, (int)(this.frame.getX() - 90.0f + (9 - index) * 20 + 2.0f), (int)this.frame.getY());
            ElementArmor.mc.func_175599_af().func_180453_a(ElementArmor.mc.field_71466_p, stack, (int)(this.frame.getX() - 90.0f + (9 - index) * 20 + 2.0f), (int)this.frame.getY(), "");
            ElementArmor.mc.func_175599_af().field_77023_b = 0.0f;
            GlStateManager.func_179098_w();
            GlStateManager.func_179140_f();
            GlStateManager.func_179097_i();
            final String s = (stack.func_190916_E() > 1) ? (stack.func_190916_E() + "") : "";
            ElementArmor.mc.field_71466_p.func_175063_a(s, this.frame.getX() - 90.0f + (9 - index) * 20 + 2.0f + 19.0f - 2.0f - ElementArmor.mc.field_71466_p.func_78256_a(s), this.frame.getY() + 9.0f, 16777215);
            if (!ElementArmor.percentage.getValue()) {
                continue;
            }
            final float green = (stack.func_77958_k() - (float)stack.func_77952_i()) / stack.func_77958_k();
            final float red = 1.0f - green;
            final int dmg = 100 - (int)(red * 100.0f);
            Kaotik.FONT_MANAGER.drawString(this.getPercentageColor() + "" + dmg + "", this.frame.getX() - 90.0f + (9 - index) * 20 + 2.0f + 8.0f - Kaotik.FONT_MANAGER.getStringWidth(dmg + "") / 2.0f, this.frame.getY() - 11.0f, ElementArmor.percentageColor.getValue().equals(PercentageColors.Damage) ? new Color(red, green, 0.0f) : ModuleColor.getActualColor());
        }
        GlStateManager.func_179126_j();
        GlStateManager.func_179140_f();
    }
    
    private ChatFormatting getPercentageColor() {
        if (ElementArmor.percentageColor.getValue().equals(PercentageColors.White)) {
            return ChatFormatting.WHITE;
        }
        if (ElementArmor.percentageColor.getValue().equals(PercentageColors.Gray)) {
            return ChatFormatting.GRAY;
        }
        return ChatFormatting.RESET;
    }
    
    static {
        ElementArmor.percentage = new ValueBoolean("Percentage", "Percentage", "Renders the percentage that the armor's durability is at.", true);
        ElementArmor.percentageColor = new ValueEnum("PercentageColor", "PercentageColor", "The color for the percentage.", PercentageColors.Damage);
    }
    
    public enum PercentageColors
    {
        Normal, 
        Gray, 
        White, 
        Damage;
    }
}
