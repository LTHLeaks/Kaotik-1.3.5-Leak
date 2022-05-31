package com.mawuote.client.mixins.impl;

import com.mawuote.*;
import com.mawuote.api.manager.command.*;
import net.minecraft.client.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.client.gui.*;
import java.util.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ GuiTextField.class })
public class MixinGuiTextField extends Gui
{
    @Shadow
    public boolean field_146226_p;
    @Shadow
    public int field_146222_t;
    @Shadow
    public int field_146221_u;
    @Shadow
    public int field_146224_r;
    @Shadow
    private int field_146225_q;
    @Shadow
    private String field_146216_j;
    @Shadow
    private int field_146223_s;
    @Shadow
    private FontRenderer field_146211_a;
    @Shadow
    private boolean field_146215_m;
    @Shadow
    private boolean field_146213_o;
    @Shadow
    private int field_146214_l;
    @Shadow
    private int field_146209_f;
    @Shadow
    private int field_146210_g;
    @Shadow
    private int field_146219_i;
    @Shadow
    private int field_146218_h;
    
    @Shadow
    public boolean func_146176_q() {
        return false;
    }
    
    @Shadow
    public boolean func_146181_i() {
        return false;
    }
    
    @Shadow
    private int func_146200_o() {
        return 0;
    }
    
    @Overwrite
    public void func_146194_f() {
        if (this.func_146176_q()) {
            if (this.func_146181_i()) {
                func_73734_a(this.field_146209_f - 1, this.field_146210_g - 1, this.field_146209_f + this.field_146218_h + 1, this.field_146210_g + this.field_146219_i + 1, -6250336);
                func_73734_a(this.field_146209_f, this.field_146210_g, this.field_146209_f + this.field_146218_h, this.field_146210_g + this.field_146219_i, -16777216);
            }
            final int i = this.field_146226_p ? this.field_146222_t : this.field_146221_u;
            final int j = this.field_146224_r - this.field_146225_q;
            int k = this.field_146223_s - this.field_146225_q;
            final String s = this.field_146211_a.func_78269_a(this.field_146216_j.substring(this.field_146225_q), this.func_146200_o());
            final boolean flag = j >= 0 && j <= s.length();
            final boolean flag2 = this.field_146213_o && this.field_146214_l / 6 % 2 == 0 && flag;
            final int l = this.field_146215_m ? (this.field_146209_f + 4) : this.field_146209_f;
            final int i2 = this.field_146215_m ? (this.field_146210_g + (this.field_146219_i - 8) / 2) : this.field_146210_g;
            int j2 = l;
            if (k > s.length()) {
                k = s.length();
            }
            if (!s.isEmpty()) {
                final String s2 = flag ? s.substring(0, j) : s;
                String string = "";
                for (final Command c : Kaotik.COMMAND_MANAGER.getCommands()) {
                    final String cN = Kaotik.COMMAND_MANAGER.getPrefix() + c.getName();
                    if (cN.startsWith(s)) {
                        string = cN;
                    }
                }
                Minecraft.func_71410_x().field_71466_p.func_175063_a(ChatFormatting.GRAY + string, 4.0f, (float)(new ScaledResolution(Minecraft.func_71410_x()).func_78328_b() - Minecraft.func_71410_x().field_71466_p.field_78288_b - 3), -1);
                j2 = this.field_146211_a.func_175063_a(s2, (float)l, (float)i2, i);
            }
            final boolean flag3 = this.field_146224_r < this.field_146216_j.length() || this.field_146216_j.length() >= this.func_146208_g();
            int k2 = j2;
            if (!flag) {
                k2 = ((j > 0) ? (l + this.field_146218_h) : l);
            }
            else if (flag3) {
                k2 = j2 - 1;
                --j2;
            }
            if (!s.isEmpty() && flag && j < s.length()) {
                j2 = this.field_146211_a.func_175063_a(s.substring(j), (float)j2, (float)i2, i);
            }
            if (flag2) {
                if (flag3) {
                    Gui.func_73734_a(k2, i2 - 1, k2 + 1, i2 + 1 + this.field_146211_a.field_78288_b, -3092272);
                }
                else {
                    this.field_146211_a.func_175063_a("_", (float)k2, (float)i2, i);
                }
            }
            if (k != j) {
                final int l2 = l + this.field_146211_a.func_78256_a(s.substring(0, k));
                this.func_146188_c(k2, i2 - 1, l2 - 1, i2 + 1 + this.field_146211_a.field_78288_b);
            }
        }
    }
    
    @Shadow
    private int func_146208_g() {
        return 0;
    }
    
    @Shadow
    private void func_146188_c(final int startX, final int startY, final int endX, final int endY) {
    }
}
