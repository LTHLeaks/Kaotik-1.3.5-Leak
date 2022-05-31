package com.mawuote.client.mixins.impl;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.model.*;
import java.nio.*;
import java.util.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.entity.*;
import com.google.common.collect.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mawuote.api.manager.event.impl.render.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import java.awt.*;
import net.minecraftforge.client.event.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import com.mawuote.*;
import com.mawuote.client.modules.render.*;
import net.minecraft.client.*;
import com.mawuote.api.utilities.render.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import org.spongepowered.asm.mixin.*;
import org.apache.logging.log4j.*;

@Mixin({ RenderLivingBase.class })
public abstract class MixinRenderLivingBase<T extends EntityLivingBase> extends Render<T>
{
    @Shadow
    private static final Logger field_147923_a;
    @Shadow
    private static final DynamicTexture field_177096_e;
    @Shadow
    protected ModelBase field_77045_g;
    @Shadow
    protected FloatBuffer field_177095_g;
    @Shadow
    protected List<LayerRenderer<T>> field_177097_h;
    @Shadow
    protected boolean field_188323_j;
    @Shadow
    public static float NAME_TAG_RANGE;
    @Shadow
    public static float NAME_TAG_RANGE_SNEAK;
    
    public MixinRenderLivingBase() {
        super((RenderManager)null);
        this.field_177095_g = GLAllocation.func_74529_h(4);
        this.field_177097_h = (List<LayerRenderer<T>>)Lists.newArrayList();
    }
    
    @Shadow
    protected float func_77040_d(final T livingBase, final float partialTickTime) {
        return livingBase.func_70678_g(partialTickTime);
    }
    
    @Shadow
    protected float func_77034_a(final float prevYawOffset, final float yawOffset, final float partialTicks) {
        float f;
        for (f = yawOffset - prevYawOffset; f < -180.0f; f += 360.0f) {}
        while (f >= 180.0f) {
            f -= 360.0f;
        }
        return prevYawOffset + partialTicks * f;
    }
    
    @Shadow
    protected void func_77039_a(final T entityLivingBaseIn, final double x, final double y, final double z) {
        GlStateManager.func_179109_b((float)x, (float)y, (float)z);
    }
    
    @Shadow
    protected float func_77044_a(final T livingBase, final float partialTicks) {
        return livingBase.field_70173_aa + partialTicks;
    }
    
    @Shadow
    protected void func_77043_a(final T entityLiving, final float ageInTicks, final float rotationYaw, final float partialTicks) {
        GlStateManager.func_179114_b(180.0f - rotationYaw, 0.0f, 1.0f, 0.0f);
        if (entityLiving.field_70725_aQ > 0) {
            float f = (entityLiving.field_70725_aQ + partialTicks - 1.0f) / 20.0f * 1.6f;
            if ((f = MathHelper.func_76129_c(f)) > 1.0f) {
                f = 1.0f;
            }
        }
        else {
            final String s = TextFormatting.func_110646_a(entityLiving.func_70005_c_());
            if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s)) && (!(entityLiving instanceof EntityPlayer) || ((EntityPlayer)entityLiving).func_175148_a(EnumPlayerModelParts.CAPE))) {
                GlStateManager.func_179109_b(0.0f, entityLiving.field_70131_O + 0.1f, 0.0f);
                GlStateManager.func_179114_b(180.0f, 0.0f, 0.0f, 1.0f);
            }
        }
    }
    
    @Shadow
    public float func_188322_c(final T entitylivingbaseIn, final float partialTicks) {
        GlStateManager.func_179091_B();
        GlStateManager.func_179152_a(-1.0f, -1.0f, 1.0f);
        final float f = 0.0625f;
        GlStateManager.func_179109_b(0.0f, -1.501f, 0.0f);
        return 0.0625f;
    }
    
    @Shadow
    protected boolean func_177088_c(final T entityLivingBaseIn) {
        GlStateManager.func_179140_f();
        GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
        GlStateManager.func_179090_x();
        GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
        return true;
    }
    
    @Shadow
    protected void func_77036_a(final T entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor) {
    }
    
    @Shadow
    protected void func_177093_a(final T entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleIn) {
    }
    
    @Inject(method = { "renderName" }, at = { @At("HEAD") }, cancellable = true)
    public void obRenderNamePre(final T entity, final double x, final double y, final double z, final CallbackInfo ci) {
        if (!ModuleShaderChams.renderNameTags) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderLayers" }, at = { @At("HEAD") }, cancellable = true)
    public void onInjectRenderLayers(final CallbackInfo ci) {
        if (!ModuleShaderChams.renderNameTags) {
            ci.cancel();
        }
    }
    
    @Redirect(method = { "renderLayers" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/LayerRenderer;doRenderLayer(Lnet/minecraft/entity/EntityLivingBase;FFFFFFF)V"))
    public void onRenderLayersDoLayers(final LayerRenderer<EntityLivingBase> layer, final EntityLivingBase entity, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleIn) {
        final EventRenderEntityLayer event = new EventRenderEntityLayer(entity, layer);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) {
            layer.func_177141_a(entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scaleIn);
        }
    }
    
    @Shadow
    protected void func_180565_e() {
        GlStateManager.func_179145_e();
        GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
        GlStateManager.func_179098_w();
        GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
    }
    
    @Shadow
    protected boolean func_177090_c(final T entityLivingBaseIn, final float partialTicks) {
        return false;
    }
    
    @Shadow
    protected void func_177091_f() {
    }
    
    @Overwrite
    public void func_76986_a(final T entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        final Color color = new Color(ModuleESP.color.getRed(), ModuleESP.color.getGreen(), ModuleESP.color.getBlue());
        final float lineWidth = (float)ModuleESP.width.getValue().doubleValue();
        if (MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Pre((EntityLivingBase)entity, (RenderLivingBase)RenderLivingBase.class.cast(this), partialTicks, x, y, z))) {
            return;
        }
        GlStateManager.func_179094_E();
        GlStateManager.func_179129_p();
        GL11.glEnable(2848);
        this.field_77045_g.field_78095_p = this.func_77040_d(entity, partialTicks);
        final boolean shouldSit = this.field_77045_g.field_78093_q = (entity.func_184218_aH() && entity.func_184187_bx() != null && entity.func_184187_bx().shouldRiderSit());
        this.field_77045_g.field_78091_s = entity.func_70631_g_();
        try {
            float f = this.func_77034_a(entity.field_70760_ar, entity.field_70761_aq, partialTicks);
            final float f2 = this.func_77034_a(entity.field_70758_at, entity.field_70759_as, partialTicks);
            float f3 = f2 - f;
            if (shouldSit && entity.func_184187_bx() instanceof EntityLivingBase) {
                final EntityLivingBase entitylivingbase = (EntityLivingBase)entity.func_184187_bx();
                f = this.func_77034_a(entitylivingbase.field_70760_ar, entitylivingbase.field_70761_aq, partialTicks);
                f3 = f2 - f;
                float f4 = MathHelper.func_76142_g(f3);
                if (f4 < -85.0f) {
                    f4 = -85.0f;
                }
                if (f4 >= 85.0f) {
                    f4 = 85.0f;
                }
                f = f2 - f4;
                if (f4 * f4 > 2500.0f) {
                    f += f4 * 0.2f;
                }
                f3 = f2 - f;
            }
            final float f5 = entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * partialTicks;
            this.func_77039_a(entity, x, y, z);
            final float f6 = this.func_77044_a(entity, partialTicks);
            this.func_77043_a(entity, f6, f, partialTicks);
            final float f7 = this.func_188322_c(entity, partialTicks);
            float f8 = 0.0f;
            float f9 = 0.0f;
            if (!entity.func_184218_aH()) {
                f8 = entity.field_184618_aE + (entity.field_70721_aZ - entity.field_184618_aE) * partialTicks;
                f9 = entity.field_184619_aG - entity.field_70721_aZ * (1.0f - partialTicks);
                if (entity.func_70631_g_()) {
                    f9 *= 3.0f;
                }
                if (f8 > 1.0f) {
                    f8 = 1.0f;
                }
                f3 = f2 - f;
            }
            GlStateManager.func_179141_d();
            this.field_77045_g.func_78086_a((EntityLivingBase)entity, f9, f8, partialTicks);
            this.field_77045_g.func_78087_a(f9, f8, f6, f3, f5, f7, (Entity)entity);
            if (Kaotik.getModuleManager().isModuleEnabled("ESP")) {
                GlStateManager.func_179132_a(true);
                if (ModuleESP.players.getValue()) {
                    if (entity instanceof EntityPlayer && entity != ModulePopChams.player) {
                        if (entity != Minecraft.func_71410_x().field_71439_g) {
                            OutlineUtils.setColor(color);
                            this.func_77036_a(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderOne(lineWidth);
                            this.func_77036_a(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderTwo();
                            this.func_77036_a(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderThree();
                            OutlineUtils.renderFour();
                            OutlineUtils.setColor(color);
                            this.func_77036_a(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderFive();
                            OutlineUtils.setColor(Color.WHITE);
                        }
                    }
                    else {
                        this.func_77036_a(entity, f9, f8, f6, f3, f5, f7);
                    }
                }
                if (ModuleESP.mobs.getValue()) {
                    if (entity instanceof EntityMob || entity instanceof EntitySlime || entity instanceof EntityMagmaCube || entity instanceof EntityGhast) {
                        if (entity != Minecraft.func_71410_x().field_71439_g && !(entity instanceof EntityPigZombie)) {
                            OutlineUtils.setColor(Color.RED);
                            this.func_77036_a(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderOne(lineWidth);
                            this.func_77036_a(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderTwo();
                            this.func_77036_a(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderThree();
                            OutlineUtils.renderFour();
                            OutlineUtils.setColor(Color.RED);
                            this.func_77036_a(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderFive();
                            OutlineUtils.setColor(Color.WHITE);
                        }
                    }
                    else {
                        this.func_77036_a(entity, f9, f8, f6, f3, f5, f7);
                    }
                }
                if (ModuleESP.animals.getValue()) {
                    if (entity instanceof EntityAnimal || entity instanceof EntityIronGolem || entity instanceof EntityGolem || entity instanceof EntitySquid || entity instanceof EntityPigZombie) {
                        if (entity != Minecraft.func_71410_x().field_71439_g) {
                            OutlineUtils.setColor(Color.GREEN);
                            this.func_77036_a(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderOne(lineWidth);
                            this.func_77036_a(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderTwo();
                            this.func_77036_a(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderThree();
                            OutlineUtils.renderFour();
                            OutlineUtils.setColor(Color.GREEN);
                            this.func_77036_a(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderFive();
                            OutlineUtils.setColor(Color.WHITE);
                        }
                    }
                    else {
                        this.func_77036_a(entity, f9, f8, f6, f3, f5, f7);
                    }
                }
            }
            else {
                this.func_77036_a(entity, f9, f8, f6, f3, f5, f7);
            }
            if (this.field_188301_f) {
                final boolean flag1 = this.func_177088_c(entity);
                GlStateManager.func_179142_g();
                GlStateManager.func_187431_e(ModuleESP.color.getRGB());
                if (!this.field_188323_j) {
                    this.func_77036_a(entity, f9, f8, f6, f3, f5, f7);
                }
                if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).func_175149_v()) {
                    this.func_177093_a(entity, f9, f8, partialTicks, f6, f3, f5, f7);
                }
                GlStateManager.func_187417_n();
                GlStateManager.func_179119_h();
                if (flag1) {
                    this.func_180565_e();
                }
            }
            else {
                final boolean flag2 = this.func_177090_c(entity, partialTicks);
                this.func_77036_a(entity, f9, f8, f6, f3, f5, f7);
                if (flag2) {
                    this.func_177091_f();
                }
                GlStateManager.func_179132_a(true);
                if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).func_175149_v()) {
                    this.func_177093_a(entity, f9, f8, partialTicks, f6, f3, f5, f7);
                }
            }
            GlStateManager.func_179101_C();
        }
        catch (final Exception exception) {
            MixinRenderLivingBase.field_147923_a.error("Couldn't render entity", (Throwable)exception);
        }
        GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
        GlStateManager.func_179098_w();
        GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
        GlStateManager.func_179089_o();
        GL11.glDisable(2848);
        GlStateManager.func_179121_F();
        super.func_76986_a((Entity)entity, x, y, z, entityYaw, partialTicks);
        MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Post((EntityLivingBase)entity, (RenderLivingBase)RenderLivingBase.class.cast(this), partialTicks, x, y, z));
    }
    
    static {
        field_147923_a = LogManager.getLogger();
        field_177096_e = new DynamicTexture(16, 16);
        MixinRenderLivingBase.NAME_TAG_RANGE = 64.0f;
        MixinRenderLivingBase.NAME_TAG_RANGE_SNEAK = 32.0f;
    }
}
