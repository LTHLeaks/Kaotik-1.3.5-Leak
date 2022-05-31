package com.mawuote.client.mixins.impl;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import com.mawuote.*;
import com.mawuote.client.modules.render.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ ModelEnderCrystal.class })
public class MixinModelEnderCrystal
{
    @Shadow
    private ModelRenderer field_78230_a;
    @Shadow
    private ModelRenderer field_78228_b;
    @Shadow
    private ModelRenderer field_78229_c;
    
    @Overwrite
    public void func_78088_a(final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a(2.0f, 2.0f, 2.0f);
        GlStateManager.func_179109_b(0.0f, -0.5f, 0.0f);
        if (this.field_78229_c != null) {
            this.field_78229_c.func_78785_a(scale);
        }
        GlStateManager.func_179114_b(limbSwingAmount, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179109_b(0.0f, 0.8f + ageInTicks, 0.0f);
        GlStateManager.func_179114_b(60.0f, 0.7071f, 0.0f, 0.7071f);
        if (Kaotik.getModuleManager().isModuleEnabled("CrystalChams")) {
            if (ModuleCrystalChams.outsideCube.getValue()) {
                this.field_78228_b.func_78785_a(scale);
            }
        }
        else {
            this.field_78228_b.func_78785_a(scale);
        }
        final float f = 0.875f;
        GlStateManager.func_179152_a(0.875f, 0.875f, 0.875f);
        GlStateManager.func_179114_b(60.0f, 0.7071f, 0.0f, 0.7071f);
        GlStateManager.func_179114_b(limbSwingAmount, 0.0f, 1.0f, 0.0f);
        if (Kaotik.getModuleManager().isModuleEnabled("CrystalChams")) {
            if (ModuleCrystalChams.outsideCube2.getValue()) {
                this.field_78228_b.func_78785_a(scale);
            }
        }
        else {
            this.field_78228_b.func_78785_a(scale);
        }
        GlStateManager.func_179152_a(0.875f, 0.875f, 0.875f);
        GlStateManager.func_179114_b(60.0f, 0.7071f, 0.0f, 0.7071f);
        GlStateManager.func_179114_b(limbSwingAmount, 0.0f, 1.0f, 0.0f);
        if (Kaotik.getModuleManager().isModuleEnabled("CrystalChams")) {
            if (ModuleCrystalChams.insideCube.getValue()) {
                this.field_78230_a.func_78785_a(scale);
            }
        }
        else {
            this.field_78230_a.func_78785_a(scale);
        }
        GlStateManager.func_179121_F();
    }
}
