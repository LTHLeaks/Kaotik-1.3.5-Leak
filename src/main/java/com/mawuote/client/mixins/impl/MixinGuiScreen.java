package com.mawuote.client.mixins.impl;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mawuote.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import net.minecraft.nbt.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiScreen.class })
public class MixinGuiScreen
{
    RenderItem itemRender;
    ResourceLocation resource;
    FontRenderer fontRenderer;
    
    public MixinGuiScreen() {
        this.itemRender = Minecraft.func_71410_x().func_175599_af();
        this.fontRenderer = Minecraft.func_71410_x().field_71466_p;
    }
    
    @Inject(method = { "renderToolTip" }, at = { @At("HEAD") }, cancellable = true)
    public void renderToolTip(final ItemStack stack, final int x, final int y, final CallbackInfo info) {
        this.resource = new ResourceLocation("textures/gui/container/shulker_box.png");
        if (Kaotik.getModuleManager().isModuleEnabled("ShulkerViewer") && stack.func_77973_b() instanceof ItemShulkerBox) {
            final NBTTagCompound tagCompound = stack.func_77978_p();
            if (tagCompound != null && tagCompound.func_150297_b("BlockEntityTag", 10)) {
                final NBTTagCompound blockEntityTag = tagCompound.func_74775_l("BlockEntityTag");
                if (blockEntityTag.func_150297_b("Items", 9)) {
                    info.cancel();
                    final NonNullList<ItemStack> nonnulllist = (NonNullList<ItemStack>)NonNullList.func_191197_a(27, (Object)ItemStack.field_190927_a);
                    ItemStackHelper.func_191283_b(blockEntityTag, (NonNullList)nonnulllist);
                    GlStateManager.func_179147_l();
                    GlStateManager.func_179101_C();
                    RenderHelper.func_74518_a();
                    GlStateManager.func_179140_f();
                    GlStateManager.func_179097_i();
                    final int x2 = x + 4;
                    final int y2 = y - 30;
                    this.itemRender.field_77023_b = 300.0f;
                    Minecraft.func_71410_x().field_71446_o.func_110577_a(this.resource);
                    GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                    Minecraft.func_71410_x().field_71456_v.func_73729_b(x2, y2, 7, 5, 162, 66);
                    this.fontRenderer.func_78276_b(stack.func_82833_r(), x + 6, y - 28, Color.DARK_GRAY.getRGB());
                    GlStateManager.func_179147_l();
                    GlStateManager.func_179141_d();
                    GlStateManager.func_179098_w();
                    GlStateManager.func_179145_e();
                    GlStateManager.func_179126_j();
                    RenderHelper.func_74520_c();
                    for (int i = 0; i < nonnulllist.size(); ++i) {
                        final int iX = x + 5 + i % 9 * 18;
                        final int iY = y + 1 + (i / 9 - 1) * 18;
                        final ItemStack itemStack = (ItemStack)nonnulllist.get(i);
                        this.itemRender.func_180450_b(itemStack, iX, iY);
                        this.itemRender.func_180453_a(this.fontRenderer, itemStack, iX, iY, (String)null);
                    }
                    RenderHelper.func_74518_a();
                    this.itemRender.field_77023_b = 0.0f;
                    GlStateManager.func_179145_e();
                    GlStateManager.func_179126_j();
                    RenderHelper.func_74519_b();
                    GlStateManager.func_179091_B();
                }
            }
        }
    }
}
