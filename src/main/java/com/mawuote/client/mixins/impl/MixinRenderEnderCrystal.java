package com.mawuote.client.mixins.impl;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import com.mawuote.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.*;
import com.mawuote.api.utilities.render.*;
import java.awt.*;
import com.mawuote.client.gui.click.components.impl.*;
import com.mawuote.client.modules.render.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderEnderCrystal.class })
public abstract class MixinRenderEnderCrystal
{
    @Shadow
    public ModelBase field_76995_b;
    @Shadow
    public ModelBase field_188316_g;
    @Final
    @Shadow
    private static ResourceLocation field_110787_a;
    private static final ResourceLocation RES_ITEM_GLINT;
    
    @Shadow
    public abstract void func_76986_a(final EntityEnderCrystal p0, final double p1, final double p2, final double p3, final float p4, final float p5);
    
    @Redirect(method = { "doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void render1(final ModelBase var1, final Entity var2, final float var3, final float var4, final float var5, final float var6, final float var7, final float var8) {
        if (!Kaotik.getModuleManager().isModuleEnabled("CrystalChams")) {
            var1.func_78088_a(var2, var3, var4, var5, var6, var7, var8);
        }
    }
    
    @Redirect(method = { "doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", ordinal = 1))
    private void render2(final ModelBase var1, final Entity var2, final float var3, final float var4, final float var5, final float var6, final float var7, final float var8) {
        if (!Kaotik.getModuleManager().isModuleEnabled("CrystalChams")) {
            var1.func_78088_a(var2, var3, var4, var5, var6, var7, var8);
        }
    }
    
    @Inject(method = { "doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V" }, at = { @At("RETURN") }, cancellable = true)
    public void IdoRender(final EntityEnderCrystal var1, final double var2, final double var4, final double var6, final float var8, final float var9, final CallbackInfo var10) {
        final Minecraft mc = Minecraft.func_71410_x();
        mc.field_71474_y.field_74347_j = false;
        if (Kaotik.getModuleManager().isModuleEnabled("ESP") && ModuleESP.others.getValue()) {
            final float var11 = var1.field_70261_a + var9;
            GlStateManager.func_179094_E();
            GlStateManager.func_179137_b(var2, var4, var6);
            Minecraft.func_71410_x().field_175616_W.field_78724_e.func_110577_a(MixinRenderEnderCrystal.field_110787_a);
            float var12 = MathHelper.func_76126_a(var11 * 0.2f) / 2.0f + 0.5f;
            var12 += var12 * var12;
            GL11.glLineWidth(5.0f);
            if (var1.func_184520_k()) {
                this.field_76995_b.func_78088_a((Entity)var1, 0.0f, var11 * 3.0f, var12 * 0.2f, 0.0f, 0.0f, 0.0625f);
            }
            else {
                this.field_188316_g.func_78088_a((Entity)var1, 0.0f, var11 * 3.0f, var12 * 0.2f, 0.0f, 0.0f, 0.0625f);
            }
            OutlineUtils.renderOne((float)ModuleESP.width.getValue().doubleValue());
            if (var1.func_184520_k()) {
                this.field_76995_b.func_78088_a((Entity)var1, 0.0f, var11 * 3.0f, var12 * 0.2f, 0.0f, 0.0f, 0.0625f);
            }
            else {
                this.field_188316_g.func_78088_a((Entity)var1, 0.0f, var11 * 3.0f, var12 * 0.2f, 0.0f, 0.0f, 0.0625f);
            }
            OutlineUtils.renderTwo();
            if (var1.func_184520_k()) {
                this.field_76995_b.func_78088_a((Entity)var1, 0.0f, var11 * 3.0f, var12 * 0.2f, 0.0f, 0.0f, 0.0625f);
            }
            else {
                this.field_188316_g.func_78088_a((Entity)var1, 0.0f, var11 * 3.0f, var12 * 0.2f, 0.0f, 0.0f, 0.0625f);
            }
            final Color rainbowColor1 = new Color(ModuleESP.color.getRed(), ModuleESP.color.getGreen(), ModuleESP.color.getBlue());
            final Color rainbowColor2 = new Color(rainbowColor1.getRed(), rainbowColor1.getGreen(), rainbowColor1.getBlue());
            final Color n = new Color(rainbowColor2.getRed(), rainbowColor2.getGreen(), rainbowColor2.getBlue());
            OutlineUtils.renderThree();
            OutlineUtils.renderFour();
            OutlineUtils.setColor(n);
            if (var1.func_184520_k()) {
                this.field_76995_b.func_78088_a((Entity)var1, 0.0f, var11 * 3.0f, var12 * 0.2f, 0.0f, 0.0f, 0.0625f);
            }
            else {
                this.field_188316_g.func_78088_a((Entity)var1, 0.0f, var11 * 3.0f, var12 * 0.2f, 0.0f, 0.0f, 0.0625f);
            }
            OutlineUtils.renderFive();
            GlStateManager.func_179121_F();
        }
        if (Kaotik.getModuleManager().isModuleEnabled("CrystalChams")) {
            GL11.glPushMatrix();
            final float var13 = var1.field_70261_a + var9;
            GlStateManager.func_179137_b(var2, var4, var6);
            if (var1 == PreviewComponent.entityEnderCrystal) {
                GlStateManager.func_179152_a(1.0f, 1.0f, 1.0f);
            }
            else {
                GlStateManager.func_179152_a(ModuleCrystalChams.size.getValue().floatValue(), ModuleCrystalChams.size.getValue().floatValue(), ModuleCrystalChams.size.getValue().floatValue());
            }
            GlStateManager.func_179152_a(ModuleCrystalChams.size.getValue().floatValue(), ModuleCrystalChams.size.getValue().floatValue(), ModuleCrystalChams.size.getValue().floatValue());
            Minecraft.func_71410_x().field_175616_W.field_78724_e.func_110577_a(MixinRenderEnderCrystal.field_110787_a);
            float var14 = MathHelper.func_76126_a(var13 * 0.2f) / 2.0f + 0.5f;
            var14 += var14 * var14;
            final float spinSpeed = ModuleCrystalChams.crystalSpeed.getValue().floatValue();
            final float bounceSpeed = ModuleCrystalChams.crystalBounce.getValue().floatValue();
            if (ModuleCrystalChams.texture.getValue()) {
                if (var1.func_184520_k()) {
                    this.field_76995_b.func_78088_a((Entity)var1, 0.0f, var13 * spinSpeed, var14 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                }
                else {
                    this.field_188316_g.func_78088_a((Entity)var1, 0.0f, var13 * spinSpeed, var14 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                }
            }
            GL11.glPushAttrib(1048575);
            if (ModuleCrystalChams.mode.getValue().equals(ModuleCrystalChams.modes.Wireframe)) {
                GL11.glPolygonMode(1032, 6913);
            }
            GL11.glDisable(3008);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glLineWidth(1.5f);
            GL11.glEnable(2960);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glEnable(10754);
            if (ModuleCrystalChams.lifetimeColor.getValue()) {
                if (ModuleCrystalChams.thingers.containsKey(var1.func_110124_au())) {
                    GL11.glColor4f(ModuleCrystalChams.thingers.get(var1.func_110124_au()).color.getRed() / 255.0f, ModuleCrystalChams.thingers.get(var1.func_110124_au()).color.getGreen() / 255.0f, ModuleCrystalChams.thingers.get(var1.func_110124_au()).color.getBlue() / 255.0f, ModuleCrystalChams.pulse.getValue() ? (ModuleCrystalChams.pulseAlpha / 255.0f) : (ModuleCrystalChams.thingers.get(var1.func_110124_au()).color.getAlpha() / 255.0f));
                }
                else {
                    GL11.glColor4f(ModuleCrystalChams.lifeStart.getValue().getRed() / 255.0f, ModuleCrystalChams.lifeStart.getValue().getGreen() / 255.0f, ModuleCrystalChams.lifeStart.getValue().getBlue() / 255.0f, ModuleCrystalChams.pulse.getValue() ? (ModuleCrystalChams.pulseAlpha / 255.0f) : (ModuleCrystalChams.lifeStart.getValue().getAlpha() / 255.0f));
                }
            }
            else if (ModuleCrystalChams.hiddenSync.getValue()) {
                GL11.glColor4f(ModuleCrystalChams.color.getRed() / 255.0f, ModuleCrystalChams.color.getGreen() / 255.0f, ModuleCrystalChams.color.getBlue() / 255.0f, ModuleCrystalChams.pulse.getValue() ? (ModuleCrystalChams.pulseAlpha / 255.0f) : (ModuleCrystalChams.daColor.getValue().getAlpha() / 255.0f));
            }
            else {
                GL11.glColor4f(ModuleCrystalChams.hideColor.getRed() / 255.0f, ModuleCrystalChams.hideColor.getGreen() / 255.0f, ModuleCrystalChams.hideColor.getBlue() / 255.0f, ModuleCrystalChams.pulse.getValue() ? (ModuleCrystalChams.pulseAlpha / 255.0f) : (ModuleCrystalChams.hiddenColor.getValue().getAlpha() / 255.0f));
            }
            if (var1.func_184520_k()) {
                this.field_76995_b.func_78088_a((Entity)var1, 0.0f, var13 * spinSpeed, var14 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
            }
            else {
                this.field_188316_g.func_78088_a((Entity)var1, 0.0f, var13 * spinSpeed, var14 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
            }
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            if (ModuleCrystalChams.lifetimeColor.getValue()) {
                if (ModuleCrystalChams.thingers.containsKey(var1.func_110124_au())) {
                    GL11.glColor4f(ModuleCrystalChams.thingers.get(var1.func_110124_au()).color.getRed() / 255.0f, ModuleCrystalChams.thingers.get(var1.func_110124_au()).color.getGreen() / 255.0f, ModuleCrystalChams.thingers.get(var1.func_110124_au()).color.getBlue() / 255.0f, ModuleCrystalChams.pulse.getValue() ? (ModuleCrystalChams.pulseAlpha / 255.0f) : (ModuleCrystalChams.thingers.get(var1.func_110124_au()).color.getAlpha() / 255.0f));
                }
                else {
                    GL11.glColor4f(ModuleCrystalChams.lifeStart.getValue().getRed() / 255.0f, ModuleCrystalChams.lifeStart.getValue().getGreen() / 255.0f, ModuleCrystalChams.lifeStart.getValue().getBlue() / 255.0f, ModuleCrystalChams.pulse.getValue() ? (ModuleCrystalChams.pulseAlpha / 255.0f) : (ModuleCrystalChams.lifeStart.getValue().getAlpha() / 255.0f));
                }
            }
            else {
                GL11.glColor4f(ModuleCrystalChams.color.getRed() / 255.0f, ModuleCrystalChams.color.getGreen() / 255.0f, ModuleCrystalChams.color.getBlue() / 255.0f, ModuleCrystalChams.pulse.getValue() ? (ModuleCrystalChams.pulseAlpha / 255.0f) : (ModuleCrystalChams.daColor.getValue().getAlpha() / 255.0f));
            }
            if (var1.func_184520_k()) {
                this.field_76995_b.func_78088_a((Entity)var1, 0.0f, var13 * spinSpeed, var14 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
            }
            else {
                this.field_188316_g.func_78088_a((Entity)var1, 0.0f, var13 * spinSpeed, var14 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
            }
            if (ModuleCrystalChams.enchanted.getValue()) {
                mc.func_110434_K().func_110577_a(MixinRenderEnderCrystal.RES_ITEM_GLINT);
                GL11.glTexCoord3d(1.0, 1.0, 1.0);
                GL11.glEnable(3553);
                GL11.glBlendFunc(768, 771);
                GL11.glColor4f(ModuleCrystalChams.enchantedColor.getValue().getRed() / 255.0f, ModuleCrystalChams.enchantedColor.getValue().getGreen() / 255.0f, ModuleCrystalChams.enchantedColor.getValue().getBlue() / 255.0f, ModuleCrystalChams.enchantedColor.getValue().getAlpha() / 255.0f);
                if (var1.func_184520_k()) {
                    this.field_76995_b.func_78088_a((Entity)var1, 0.0f, var13 * spinSpeed, var14 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                }
                else {
                    this.field_188316_g.func_78088_a((Entity)var1, 0.0f, var13 * spinSpeed, var14 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                }
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
            GL11.glEnable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            GL11.glPopAttrib();
            if (ModuleCrystalChams.outline.getValue()) {
                if (ModuleCrystalChams.outlineMode.getValue().equals(ModuleCrystalChams.outlineModes.Wire)) {
                    GL11.glPushAttrib(1048575);
                    GL11.glPolygonMode(1032, 6913);
                    GL11.glDisable(3008);
                    GL11.glDisable(3553);
                    GL11.glDisable(2896);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    GL11.glLineWidth(ModuleCrystalChams.width.getValue().floatValue());
                    GL11.glEnable(2960);
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                    GL11.glEnable(10754);
                    GL11.glColor4f(ModuleCrystalChams.outColor.getRed() / 255.0f, ModuleCrystalChams.outColor.getGreen() / 255.0f, ModuleCrystalChams.outColor.getBlue() / 255.0f, 1.0f);
                    if (var1.func_184520_k()) {
                        this.field_76995_b.func_78088_a((Entity)var1, 0.0f, var13 * spinSpeed, var14 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    else {
                        this.field_188316_g.func_78088_a((Entity)var1, 0.0f, var13 * spinSpeed, var14 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                    if (var1.func_184520_k()) {
                        this.field_76995_b.func_78088_a((Entity)var1, 0.0f, var13 * spinSpeed, var14 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    else {
                        this.field_188316_g.func_78088_a((Entity)var1, 0.0f, var13 * spinSpeed, var14 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    GL11.glEnable(3042);
                    GL11.glEnable(2896);
                    GL11.glEnable(3553);
                    GL11.glEnable(3008);
                    GL11.glPopAttrib();
                }
                else {
                    OutlineUtils.setColor(ModuleCrystalChams.outColor);
                    OutlineUtils.renderOne(ModuleCrystalChams.width.getValue().floatValue());
                    if (var1.func_184520_k()) {
                        this.field_76995_b.func_78088_a((Entity)var1, 0.0f, var13 * spinSpeed, var14 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    else {
                        this.field_188316_g.func_78088_a((Entity)var1, 0.0f, var13 * spinSpeed, var14 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    OutlineUtils.renderTwo();
                    if (var1.func_184520_k()) {
                        this.field_76995_b.func_78088_a((Entity)var1, 0.0f, var13 * spinSpeed, var14 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    else {
                        this.field_188316_g.func_78088_a((Entity)var1, 0.0f, var13 * spinSpeed, var14 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    OutlineUtils.renderThree();
                    OutlineUtils.renderFour();
                    OutlineUtils.setColor(ModuleCrystalChams.outColor);
                    if (var1.func_184520_k()) {
                        this.field_76995_b.func_78088_a((Entity)var1, 0.0f, var13 * spinSpeed, var14 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    else {
                        this.field_188316_g.func_78088_a((Entity)var1, 0.0f, var13 * spinSpeed, var14 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    OutlineUtils.renderFive();
                    OutlineUtils.setColor(Color.WHITE);
                }
            }
            GL11.glPopMatrix();
        }
    }
    
    static {
        RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    }
}
