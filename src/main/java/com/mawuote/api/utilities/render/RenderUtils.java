package com.mawuote.api.utilities.render;

import com.mawuote.api.utilities.*;
import net.minecraft.client.renderer.culling.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import java.awt.*;
import net.minecraft.util.*;
import com.mawuote.api.utilities.entity.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.util.math.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;

public class RenderUtils extends Tessellator implements IMinecraft
{
    public static ICamera camera;
    private static final Frustum frustrum;
    private static final AxisAlignedBB DEFAULT_AABB;
    private final Map<Integer, Boolean> glCapMap;
    public static int deltaTime;
    public static RenderUtils INSTANCE;
    private static boolean depth;
    private static boolean texture;
    private static boolean clean;
    private static boolean bind;
    private static boolean override;
    
    public RenderUtils() {
        super(2097152);
        this.glCapMap = new HashMap<Integer, Boolean>();
    }
    
    public static void prepare(final int mode) {
        prepareGL();
        begin(mode);
    }
    
    public static void prepareGL() {
        GL11.glBlendFunc(770, 771);
        GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.func_187441_d(1.0f);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a(false);
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179140_f();
        GlStateManager.func_179129_p();
        GlStateManager.func_179141_d();
        GlStateManager.func_179124_c(1.0f, 1.0f, 1.0f);
    }
    
    public static void begin(final int mode) {
        RenderUtils.INSTANCE.func_178180_c().func_181668_a(mode, DefaultVertexFormats.field_181706_f);
    }
    
    public static void release() {
        render();
        releaseGL();
    }
    
    public static void render() {
        RenderUtils.INSTANCE.func_78381_a();
    }
    
    public static void releaseGL() {
        GlStateManager.func_179089_o();
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179098_w();
        GlStateManager.func_179147_l();
        GlStateManager.func_179126_j();
        GlStateManager.func_179124_c(1.0f, 1.0f, 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void drawOpenGradientBox(final BlockPos pos, final Color startColor, final Color endColor, final double height) {
        for (final EnumFacing face : EnumFacing.values()) {
            if (face != EnumFacing.UP) {
                drawGradientPlane(pos, face, startColor, endColor, height);
            }
        }
    }
    
    public static AxisAlignedBB interpolateAxis(final AxisAlignedBB bb) {
        return new AxisAlignedBB(bb.field_72340_a - RenderUtils.mc.func_175598_ae().field_78730_l, bb.field_72338_b - RenderUtils.mc.func_175598_ae().field_78731_m, bb.field_72339_c - RenderUtils.mc.func_175598_ae().field_78728_n, bb.field_72336_d - RenderUtils.mc.func_175598_ae().field_78730_l, bb.field_72337_e - RenderUtils.mc.func_175598_ae().field_78731_m, bb.field_72334_f - RenderUtils.mc.func_175598_ae().field_78728_n);
    }
    
    public static void drawGradientPlane(final BlockPos pos, final EnumFacing face, final Color startColor, final Color endColor, final boolean half, final boolean top) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder builder = tessellator.func_178180_c();
        final IBlockState iblockstate = RenderUtils.mc.field_71441_e.func_180495_p(pos);
        final Vec3d interp = EntityUtils.interpolateEntity((Entity)RenderUtils.mc.field_71439_g, RenderUtils.mc.func_184121_ak());
        final AxisAlignedBB bb = iblockstate.func_185918_c((World)RenderUtils.mc.field_71441_e, pos).func_186662_g(0.0020000000949949026).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c);
        final float red = startColor.getRed() / 255.0f;
        final float green = startColor.getGreen() / 255.0f;
        final float blue = startColor.getBlue() / 255.0f;
        final float alpha = startColor.getAlpha() / 255.0f;
        final float red2 = endColor.getRed() / 255.0f;
        final float green2 = endColor.getGreen() / 255.0f;
        final float blue2 = endColor.getBlue() / 255.0f;
        final float alpha2 = endColor.getAlpha() / 255.0f;
        double x1 = 0.0;
        double y1 = 0.0;
        double z1 = 0.0;
        double x2 = 0.0;
        double y2 = 0.0;
        double z2 = 0.0;
        if (face == EnumFacing.DOWN) {
            x1 = bb.field_72340_a;
            x2 = bb.field_72336_d;
            y1 = bb.field_72338_b + (top ? 0.5 : 0.0);
            y2 = bb.field_72338_b + (top ? 0.5 : 0.0);
            z1 = bb.field_72339_c;
            z2 = bb.field_72334_f;
        }
        else if (face == EnumFacing.UP) {
            x1 = bb.field_72340_a;
            x2 = bb.field_72336_d;
            y1 = bb.field_72337_e / (half ? 2 : 1);
            y2 = bb.field_72337_e / (half ? 2 : 1);
            z1 = bb.field_72339_c;
            z2 = bb.field_72334_f;
        }
        else if (face == EnumFacing.EAST) {
            x1 = bb.field_72336_d;
            x2 = bb.field_72336_d;
            y1 = bb.field_72338_b + (top ? 0.5 : 0.0);
            y2 = bb.field_72337_e / (half ? 2 : 1);
            z1 = bb.field_72339_c;
            z2 = bb.field_72334_f;
        }
        else if (face == EnumFacing.WEST) {
            x1 = bb.field_72340_a;
            x2 = bb.field_72340_a;
            y1 = bb.field_72338_b + (top ? 0.5 : 0.0);
            y2 = bb.field_72337_e / (half ? 2 : 1);
            z1 = bb.field_72339_c;
            z2 = bb.field_72334_f;
        }
        else if (face == EnumFacing.SOUTH) {
            x1 = bb.field_72340_a;
            x2 = bb.field_72336_d;
            y1 = bb.field_72338_b + (top ? 0.5 : 0.0);
            y2 = bb.field_72337_e / (half ? 2 : 1);
            z1 = bb.field_72334_f;
            z2 = bb.field_72334_f;
        }
        else if (face == EnumFacing.NORTH) {
            x1 = bb.field_72340_a;
            x2 = bb.field_72336_d;
            y1 = bb.field_72338_b + (top ? 0.5 : 0.0);
            y2 = bb.field_72337_e / (half ? 2 : 1);
            z1 = bb.field_72339_c;
            z2 = bb.field_72339_c;
        }
        GlStateManager.func_179094_E();
        GlStateManager.func_179097_i();
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_179132_a(false);
        builder.func_181668_a(5, DefaultVertexFormats.field_181706_f);
        if (face == EnumFacing.EAST || face == EnumFacing.WEST || face == EnumFacing.NORTH || face == EnumFacing.SOUTH) {
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
        }
        else if (face == EnumFacing.UP) {
            builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
        }
        else if (face == EnumFacing.DOWN) {
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
        }
        tessellator.func_78381_a();
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
        GlStateManager.func_179126_j();
        GlStateManager.func_179121_F();
    }
    
    public static void drawGradientPlane(final BlockPos pos, final EnumFacing face, final Color startColor, final Color endColor, final double height) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder builder = tessellator.func_178180_c();
        final IBlockState iblockstate = RenderUtils.mc.field_71441_e.func_180495_p(pos);
        final Vec3d interp = EntityUtils.interpolateEntity((Entity)RenderUtils.mc.field_71439_g, RenderUtils.mc.func_184121_ak());
        final AxisAlignedBB bb = iblockstate.func_185918_c((World)RenderUtils.mc.field_71441_e, pos).func_186662_g(0.0020000000949949026).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c).func_72321_a(0.0, height, 0.0);
        final float red = startColor.getRed() / 255.0f;
        final float green = startColor.getGreen() / 255.0f;
        final float blue = startColor.getBlue() / 255.0f;
        final float alpha = startColor.getAlpha() / 255.0f;
        final float red2 = endColor.getRed() / 255.0f;
        final float green2 = endColor.getGreen() / 255.0f;
        final float blue2 = endColor.getBlue() / 255.0f;
        final float alpha2 = endColor.getAlpha() / 255.0f;
        double x1 = 0.0;
        double y1 = 0.0;
        double z1 = 0.0;
        double x2 = 0.0;
        double y2 = 0.0;
        double z2 = 0.0;
        if (face == EnumFacing.DOWN) {
            x1 = bb.field_72340_a;
            x2 = bb.field_72336_d;
            y1 = bb.field_72338_b;
            y2 = bb.field_72338_b;
            z1 = bb.field_72339_c;
            z2 = bb.field_72334_f;
        }
        else if (face == EnumFacing.UP) {
            x1 = bb.field_72340_a;
            x2 = bb.field_72336_d;
            y1 = bb.field_72337_e;
            y2 = bb.field_72337_e;
            z1 = bb.field_72339_c;
            z2 = bb.field_72334_f;
        }
        else if (face == EnumFacing.EAST) {
            x1 = bb.field_72336_d;
            x2 = bb.field_72336_d;
            y1 = bb.field_72338_b;
            y2 = bb.field_72337_e;
            z1 = bb.field_72339_c;
            z2 = bb.field_72334_f;
        }
        else if (face == EnumFacing.WEST) {
            x1 = bb.field_72340_a;
            x2 = bb.field_72340_a;
            y1 = bb.field_72338_b;
            y2 = bb.field_72337_e;
            z1 = bb.field_72339_c;
            z2 = bb.field_72334_f;
        }
        else if (face == EnumFacing.SOUTH) {
            x1 = bb.field_72340_a;
            x2 = bb.field_72336_d;
            y1 = bb.field_72338_b;
            y2 = bb.field_72337_e;
            z1 = bb.field_72334_f;
            z2 = bb.field_72334_f;
        }
        else if (face == EnumFacing.NORTH) {
            x1 = bb.field_72340_a;
            x2 = bb.field_72336_d;
            y1 = bb.field_72338_b;
            y2 = bb.field_72337_e;
            z1 = bb.field_72339_c;
            z2 = bb.field_72339_c;
        }
        GlStateManager.func_179094_E();
        GlStateManager.func_179097_i();
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_179132_a(false);
        builder.func_181668_a(5, DefaultVertexFormats.field_181706_f);
        if (face == EnumFacing.EAST || face == EnumFacing.WEST || face == EnumFacing.NORTH || face == EnumFacing.SOUTH) {
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
        }
        else if (face == EnumFacing.UP) {
            builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
        }
        else if (face == EnumFacing.DOWN) {
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
        }
        tessellator.func_78381_a();
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
        GlStateManager.func_179126_j();
        GlStateManager.func_179121_F();
    }
    
    public static void glBillboard(final float x, final float y, final float z) {
        final float scale = 0.02666667f;
        GlStateManager.func_179137_b(x - Minecraft.func_71410_x().func_175598_ae().field_78725_b, y - Minecraft.func_71410_x().func_175598_ae().field_78726_c, z - Minecraft.func_71410_x().func_175598_ae().field_78723_d);
        GlStateManager.func_187432_a(0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(-Minecraft.func_71410_x().field_71439_g.field_70177_z, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(Minecraft.func_71410_x().field_71439_g.field_70125_A, (Minecraft.func_71410_x().field_71474_y.field_74320_O == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179152_a(-scale, -scale, scale);
    }
    
    public static void glBillboardDistanceScaled(final float x, final float y, final float z, final EntityPlayer player, final float scale) {
        glBillboard(x, y, z);
        final int distance = (int)player.func_70011_f((double)x, (double)y, (double)z);
        float scaleDistance = distance / 2.0f / (2.0f + (2.0f - scale));
        if (scaleDistance < 1.0f) {
            scaleDistance = 1.0f;
        }
        GlStateManager.func_179152_a(scaleDistance, scaleDistance, scaleDistance);
    }
    
    public static void drawBox(final BlockPos blockPos, final int argb, final int sides) {
        final int a = argb >>> 24 & 0xFF;
        final int r = argb >>> 16 & 0xFF;
        final int g = argb >>> 8 & 0xFF;
        final int b = argb & 0xFF;
        drawBox(blockPos, r, g, b, a, sides);
    }
    
    public static void drawBox(final BlockPos blockPos, final int r, final int g, final int b, final int a, final int sides) {
        drawBox(RenderUtils.INSTANCE.func_178180_c(), (float)blockPos.func_177958_n(), (float)blockPos.func_177956_o(), (float)blockPos.func_177952_p(), 1.0f, 1.0f, 1.0f, r, g, b, a, sides);
    }
    
    public static void drawBox(final BufferBuilder buffer, final float x, final float y, final float z, final float w, final float h, final float d, final int r, final int g, final int b, final int a, final int sides) {
        if ((sides & 0x1) != 0x0) {
            buffer.func_181662_b((double)(x + w), (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 0x2) != 0x0) {
            buffer.func_181662_b((double)(x + w), (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 0x4) != 0x0) {
            buffer.func_181662_b((double)(x + w), (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 0x8) != 0x0) {
            buffer.func_181662_b((double)x, (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 0x10) != 0x0) {
            buffer.func_181662_b((double)x, (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)x, (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
        }
        if ((sides & 0x20) != 0x0) {
            buffer.func_181662_b((double)(x + w), (double)y, (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)y, (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)(y + h), (double)z).func_181669_b(r, g, b, a).func_181675_d();
            buffer.func_181662_b((double)(x + w), (double)(y + h), (double)(z + d)).func_181669_b(r, g, b, a).func_181675_d();
        }
    }
    
    public static void drawBoundingBoxBlockPos(final BlockPos bp, final float width, final int r, final int g, final int b, final int alpha) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179120_a(770, 771, 0, 1);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(width);
        final Minecraft mc = Minecraft.func_71410_x();
        final double x = bp.func_177958_n() - mc.func_175598_ae().field_78730_l;
        final double y = bp.func_177956_o() - mc.func_175598_ae().field_78731_m;
        final double z = bp.func_177952_p() - mc.func_175598_ae().field_78728_n;
        final AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0);
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferbuilder = tessellator.func_178180_c();
        bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        tessellator.func_78381_a();
        bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        tessellator.func_78381_a();
        bufferbuilder.func_181668_a(1, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181669_b(r, g, b, alpha).func_181675_d();
        tessellator.func_78381_a();
        GL11.glDisable(2848);
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
    }
    
    public static void drawGradientBlockOutline(final BlockPos pos, final Color startColor, final Color endColor, final float linewidth) {
        final IBlockState iblockstate = RenderUtils.mc.field_71441_e.func_180495_p(pos);
        final Vec3d interp = EntityUtils.interpolateEntity((Entity)RenderUtils.mc.field_71439_g, RenderUtils.mc.func_184121_ak());
        drawGradientBlockOutline(iblockstate.func_185918_c((World)RenderUtils.mc.field_71441_e, pos).func_186662_g(0.0020000000949949026).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c), startColor, endColor, linewidth);
    }
    
    public static void drawGradientBlockOutline(final AxisAlignedBB bb, final Color startColor, final Color endColor, final float linewidth) {
        final float red = startColor.getRed() / 255.0f;
        final float green = startColor.getGreen() / 255.0f;
        final float blue = startColor.getBlue() / 255.0f;
        final float alpha = startColor.getAlpha() / 255.0f;
        final float red2 = endColor.getRed() / 255.0f;
        final float green2 = endColor.getGreen() / 255.0f;
        final float blue2 = endColor.getBlue() / 255.0f;
        final float alpha2 = endColor.getAlpha() / 255.0f;
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179120_a(770, 771, 0, 1);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(linewidth);
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferbuilder = tessellator.func_178180_c();
        bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        tessellator.func_78381_a();
        GL11.glDisable(2848);
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
    }
    
    public static void drawBoxESP(final BlockPos pos, final Color color, final int boxAlpha) {
        drawBox(pos, new Color(color.getRed(), color.getGreen(), color.getBlue(), boxAlpha));
    }
    
    public static void drawBox(final BlockPos pos, final Color color) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.func_177958_n() - RenderUtils.mc.func_175598_ae().field_78730_l, pos.func_177956_o() - RenderUtils.mc.func_175598_ae().field_78731_m, pos.func_177952_p() - RenderUtils.mc.func_175598_ae().field_78728_n, pos.func_177958_n() + 1 - RenderUtils.mc.func_175598_ae().field_78730_l, pos.func_177956_o() + 1 - RenderUtils.mc.func_175598_ae().field_78731_m, pos.func_177952_p() + 1 - RenderUtils.mc.func_175598_ae().field_78728_n);
        RenderUtils.camera.func_78547_a(Objects.requireNonNull(RenderUtils.mc.func_175606_aa()).field_70165_t, RenderUtils.mc.func_175606_aa().field_70163_u, RenderUtils.mc.func_175606_aa().field_70161_v);
        if (RenderUtils.camera.func_78546_a(new AxisAlignedBB(bb.field_72340_a + RenderUtils.mc.func_175598_ae().field_78730_l, bb.field_72338_b + RenderUtils.mc.func_175598_ae().field_78731_m, bb.field_72339_c + RenderUtils.mc.func_175598_ae().field_78728_n, bb.field_72336_d + RenderUtils.mc.func_175598_ae().field_78730_l, bb.field_72337_e + RenderUtils.mc.func_175598_ae().field_78731_m, bb.field_72334_f + RenderUtils.mc.func_175598_ae().field_78728_n))) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179147_l();
            GlStateManager.func_179097_i();
            GlStateManager.func_179120_a(770, 771, 0, 1);
            GlStateManager.func_179090_x();
            GlStateManager.func_179132_a(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            RenderGlobal.func_189696_b(bb, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            GL11.glDisable(2848);
            GlStateManager.func_179132_a(true);
            GlStateManager.func_179126_j();
            GlStateManager.func_179098_w();
            GlStateManager.func_179084_k();
            GlStateManager.func_179121_F();
        }
    }
    
    public static void drawBoxESP(final BlockPos pos, final Color color, final float lineWidth, final boolean outline, final boolean box, final int boxAlpha, final boolean air, final double height, final boolean gradientBox) {
        if (box) {
            drawBox(pos, new Color(color.getRed(), color.getGreen(), color.getBlue(), boxAlpha), height, gradientBox, boxAlpha);
        }
        if (outline) {
            drawBlockOutline(pos, color, lineWidth, air, height);
        }
    }
    
    public static void drawBlockOutline(final BlockPos pos, final Color color, final float linewidth, final boolean air, final double height) {
        final IBlockState iblockstate = RenderUtils.mc.field_71441_e.func_180495_p(pos);
        if ((air || iblockstate.func_185904_a() != Material.field_151579_a) && RenderUtils.mc.field_71441_e.func_175723_af().func_177746_a(pos)) {
            final AxisAlignedBB blockAxis = new AxisAlignedBB(pos.func_177958_n() - RenderUtils.mc.func_175598_ae().field_78730_l, pos.func_177956_o() - RenderUtils.mc.func_175598_ae().field_78731_m, pos.func_177952_p() - RenderUtils.mc.func_175598_ae().field_78728_n, pos.func_177958_n() + 1 - RenderUtils.mc.func_175598_ae().field_78730_l, pos.func_177956_o() + 1 - RenderUtils.mc.func_175598_ae().field_78731_m + height, pos.func_177952_p() + 1 - RenderUtils.mc.func_175598_ae().field_78728_n);
            drawBlockOutline(blockAxis.func_186662_g(0.0020000000949949026), color, linewidth);
        }
    }
    
    public static void drawBlockOutline(final AxisAlignedBB bb, final Color color, final float linewidth) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        final float alpha = 1.0f;
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179120_a(770, 771, 0, 1);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(linewidth);
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferbuilder = tessellator.func_178180_c();
        bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        tessellator.func_78381_a();
        GL11.glDisable(2848);
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
    }
    
    public static void drawColoredFlatBox(final BlockPos pos, final float linewidth, final boolean air, final double height) {
        final IBlockState iblockstate = RenderUtils.mc.field_71441_e.func_180495_p(pos);
        if ((air || iblockstate.func_185904_a() != Material.field_151579_a) && RenderUtils.mc.field_71441_e.func_175723_af().func_177746_a(pos)) {
            final AxisAlignedBB blockAxis = new AxisAlignedBB(pos.func_177958_n() - RenderUtils.mc.func_175598_ae().field_78730_l, pos.func_177956_o() - RenderUtils.mc.func_175598_ae().field_78731_m, pos.func_177952_p() - RenderUtils.mc.func_175598_ae().field_78728_n, pos.func_177958_n() + 1 - RenderUtils.mc.func_175598_ae().field_78730_l, pos.func_177956_o() + 1 - RenderUtils.mc.func_175598_ae().field_78731_m + height, pos.func_177952_p() + 1 - RenderUtils.mc.func_175598_ae().field_78728_n);
            drawColoredFlatBox(blockAxis.func_186662_g(0.0020000000949949026), linewidth);
        }
    }
    
    public static void drawColoredFlatBox(final AxisAlignedBB bb, final float linewidth) {
        final float alpha = 1.0f;
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179120_a(770, 771, 0, 1);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(linewidth);
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferbuilder = tessellator.func_178180_c();
        bufferbuilder.func_181668_a(5, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(1.0f, 0.0f, 0.0f, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(0.0f, 1.0f, 0.0f, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(0.0f, 0.0f, 1.0f, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(1.0f, 1.0f, 1.0f, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(1.0f, 0.0f, 0.0f, alpha).func_181675_d();
        tessellator.func_78381_a();
        GL11.glDisable(2848);
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
    }
    
    public static void drawBox(final BlockPos pos, final Color color, final double height, final boolean gradient, final int alpha) {
        if (gradient) {
            final Color endColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
            drawOpenGradientBox(pos, color, endColor, height);
            return;
        }
        final AxisAlignedBB bb = new AxisAlignedBB(pos.func_177958_n() - RenderUtils.mc.func_175598_ae().field_78730_l, pos.func_177956_o() - RenderUtils.mc.func_175598_ae().field_78731_m, pos.func_177952_p() - RenderUtils.mc.func_175598_ae().field_78728_n, pos.func_177958_n() + 1 - RenderUtils.mc.func_175598_ae().field_78730_l, pos.func_177956_o() + 1 - RenderUtils.mc.func_175598_ae().field_78731_m + height, pos.func_177952_p() + 1 - RenderUtils.mc.func_175598_ae().field_78728_n);
        RenderUtils.camera.func_78547_a(Objects.requireNonNull(RenderUtils.mc.func_175606_aa()).field_70165_t, RenderUtils.mc.func_175606_aa().field_70163_u, RenderUtils.mc.func_175606_aa().field_70161_v);
        if (RenderUtils.camera.func_78546_a(new AxisAlignedBB(bb.field_72340_a + RenderUtils.mc.func_175598_ae().field_78730_l, bb.field_72338_b + RenderUtils.mc.func_175598_ae().field_78731_m, bb.field_72339_c + RenderUtils.mc.func_175598_ae().field_78728_n, bb.field_72336_d + RenderUtils.mc.func_175598_ae().field_78730_l, bb.field_72337_e + RenderUtils.mc.func_175598_ae().field_78731_m, bb.field_72334_f + RenderUtils.mc.func_175598_ae().field_78728_n))) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179147_l();
            GlStateManager.func_179097_i();
            GlStateManager.func_179120_a(770, 771, 0, 1);
            GlStateManager.func_179090_x();
            GlStateManager.func_179132_a(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            RenderGlobal.func_189696_b(bb, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            GL11.glDisable(2848);
            GlStateManager.func_179132_a(true);
            GlStateManager.func_179126_j();
            GlStateManager.func_179098_w();
            GlStateManager.func_179084_k();
            GlStateManager.func_179121_F();
        }
    }
    
    public static void renderCrosses(final BlockPos pos, final Color color, final float lineWidth) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.func_177958_n() - RenderUtils.mc.func_175598_ae().field_78730_l, pos.func_177956_o() - RenderUtils.mc.func_175598_ae().field_78731_m, pos.func_177952_p() - RenderUtils.mc.func_175598_ae().field_78728_n, pos.func_177958_n() + 1 - RenderUtils.mc.func_175598_ae().field_78730_l, pos.func_177956_o() + 1 - RenderUtils.mc.func_175598_ae().field_78731_m, pos.func_177952_p() + 1 - RenderUtils.mc.func_175598_ae().field_78728_n);
        RenderUtils.camera.func_78547_a(Objects.requireNonNull(RenderUtils.mc.func_175606_aa()).field_70165_t, RenderUtils.mc.func_175606_aa().field_70163_u, RenderUtils.mc.func_175606_aa().field_70161_v);
        if (RenderUtils.camera.func_78546_a(new AxisAlignedBB(pos))) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179147_l();
            GlStateManager.func_179097_i();
            GlStateManager.func_179120_a(770, 771, 0, 1);
            GlStateManager.func_179090_x();
            GlStateManager.func_179132_a(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glLineWidth(lineWidth);
            renderCrosses(bb, color);
            GL11.glDisable(2848);
            GlStateManager.func_179132_a(true);
            GlStateManager.func_179126_j();
            GlStateManager.func_179098_w();
            GlStateManager.func_179084_k();
            GlStateManager.func_179121_F();
        }
    }
    
    public static void renderCrosses(final AxisAlignedBB bb, final Color color) {
        final int hex = color.getRGB();
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferbuilder = tessellator.func_178180_c();
        bufferbuilder.func_181668_a(1, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, 1.0f).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, 1.0f).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, 1.0f).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, 1.0f).func_181675_d();
        tessellator.func_78381_a();
    }
    
    public static void drawOutlineLine(double left, double top, double right, double bottom, final float width, final int color) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179120_a(770, 771, 0, 1);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(width);
        if (left < right) {
            final double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            final double j = top;
            top = bottom;
            bottom = j;
        }
        final float a = (color >> 24 & 0xFF) / 255.0f;
        final float r = (color >> 16 & 0xFF) / 255.0f;
        final float g = (color >> 8 & 0xFF) / 255.0f;
        final float b = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferbuilder = tessellator.func_178180_c();
        bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b(left, bottom, 0.0).func_181666_a(r, g, b, a).func_181675_d();
        bufferbuilder.func_181662_b(right, bottom, 0.0).func_181666_a(r, g, b, a).func_181675_d();
        bufferbuilder.func_181662_b(right, top, 0.0).func_181666_a(r, g, b, a).func_181675_d();
        bufferbuilder.func_181662_b(left, top, 0.0).func_181666_a(r, g, b, a).func_181675_d();
        bufferbuilder.func_181662_b(left, bottom, 0.0).func_181666_a(r, g, b, a).func_181675_d();
        tessellator.func_78381_a();
        GL11.glDisable(2848);
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
    }
    
    public static void drawRectrgb(final float x, final float y, final float w, final float h, final float r, final float g, final float b, final float a) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferbuilder = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b((double)x, (double)h, 0.0).func_181666_a(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f).func_181675_d();
        bufferbuilder.func_181662_b((double)w, (double)h, 0.0).func_181666_a(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f).func_181675_d();
        bufferbuilder.func_181662_b((double)w, (double)y, 0.0).func_181666_a(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f).func_181675_d();
        bufferbuilder.func_181662_b((double)x, (double)y, 0.0).func_181666_a(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }
    
    public static void drawRect(final float x, final float y, final float width, final float height, final int rgb) {
        final Color color = new Color(rgb);
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferBuilder = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        bufferBuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        bufferBuilder.func_181662_b((double)x, (double)height, 0.0).func_181669_b(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).func_181675_d();
        bufferBuilder.func_181662_b((double)width, (double)height, 0.0).func_181669_b(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).func_181675_d();
        bufferBuilder.func_181662_b((double)width, (double)y, 0.0).func_181669_b(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).func_181675_d();
        bufferBuilder.func_181662_b((double)x, (double)y, 0.0).func_181669_b(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }
    
    public static void drawOutline(final float x, final float y, final float width, final float height, final float lineWidth, final int color) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        drawRect(x + lineWidth, y, x - lineWidth, y + lineWidth, color);
        drawRect(x + lineWidth, y, width - lineWidth, y + lineWidth, color);
        drawRect(x, y, x + lineWidth, height, color);
        drawRect(width - lineWidth, y, width, height, color);
        drawRect(x + lineWidth, height - lineWidth, width - lineWidth, height, color);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
    
    public static void drawRecta(float x, float y, final float w, final float h, final int color) {
        float p_drawRect_2_ = x + w;
        float p_drawRect_3_ = y + h;
        if (x < p_drawRect_2_) {
            final float lvt_5_2_ = x;
            x = p_drawRect_2_;
            p_drawRect_2_ = lvt_5_2_;
        }
        if (y < p_drawRect_3_) {
            final float lvt_5_2_ = y;
            y = p_drawRect_3_;
            p_drawRect_3_ = lvt_5_2_;
        }
        final float lvt_5_3_ = (color >> 24 & 0xFF) / 255.0f;
        final float lvt_6_1_ = (color >> 16 & 0xFF) / 255.0f;
        final float lvt_7_1_ = (color >> 8 & 0xFF) / 255.0f;
        final float lvt_8_1_ = (color & 0xFF) / 255.0f;
        final Tessellator lvt_9_1_ = Tessellator.func_178181_a();
        final BufferBuilder lvt_10_1_ = lvt_9_1_.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.func_179131_c(lvt_6_1_, lvt_7_1_, lvt_8_1_, lvt_5_3_);
        lvt_10_1_.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        lvt_10_1_.func_181662_b((double)x, (double)p_drawRect_3_, 0.0).func_181675_d();
        lvt_10_1_.func_181662_b((double)p_drawRect_2_, (double)p_drawRect_3_, 0.0).func_181675_d();
        lvt_10_1_.func_181662_b((double)p_drawRect_2_, (double)y, 0.0).func_181675_d();
        lvt_10_1_.func_181662_b((double)x, (double)y, 0.0).func_181675_d();
        lvt_9_1_.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }
    
    public static void drawDoubleRect(double left, double top, double right, double bottom, final int color) {
        if (left < right) {
            final double j = left;
            left = right;
            right = j;
        }
        if (top < bottom) {
            final double j = top;
            top = bottom;
            bottom = j;
        }
        final float f3 = (color >> 24 & 0xFF) / 255.0f;
        final float f4 = (color >> 16 & 0xFF) / 255.0f;
        final float f5 = (color >> 8 & 0xFF) / 255.0f;
        final float f6 = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferbuilder = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.func_179131_c(f4, f5, f6, f3);
        bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        bufferbuilder.func_181662_b(left, bottom, 0.0).func_181675_d();
        bufferbuilder.func_181662_b(right, bottom, 0.0).func_181675_d();
        bufferbuilder.func_181662_b(right, top, 0.0).func_181675_d();
        bufferbuilder.func_181662_b(left, top, 0.0).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }
    
    public static void drawSelectionBoundingBox(final AxisAlignedBB boundingBox) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder vertexbuffer = tessellator.func_178180_c();
        vertexbuffer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        tessellator.func_78381_a();
        vertexbuffer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        tessellator.func_78381_a();
        vertexbuffer.func_181668_a(1, DefaultVertexFormats.field_181705_e);
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        vertexbuffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        tessellator.func_78381_a();
    }
    
    public static void drawCircle(final float x, final float y, final float radius, final int color) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        GlStateManager.func_179103_j(7425);
        GL11.glColor4d(getRedFromHex(color), getGreenFromHex(color), getBlueFromHex(color), getAlphaFromHex(color));
        GL11.glBegin(9);
        for (int i = 0; i <= 360; ++i) {
            GL11.glVertex2d((double)(x + MathHelper.func_76126_a(i * 3.1415927f / 180.0f) * radius), (double)(y + MathHelper.func_76134_b(i * 3.1415927f / 180.0f) * radius));
        }
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GL11.glEnd();
        GlStateManager.func_179103_j(7424);
        GL11.glDisable(2848);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
        GlStateManager.func_179121_F();
    }
    
    public static double getAlphaFromHex(final int color) {
        return (color >> 24 & 0xFF) / 255.0f;
    }
    
    public static double getRedFromHex(final int color) {
        return (color >> 16 & 0xFF) / 255.0f;
    }
    
    public static double getGreenFromHex(final int color) {
        return (color >> 8 & 0xFF) / 255.0f;
    }
    
    public static double getBlueFromHex(final int color) {
        return (color & 0xFF) / 255.0f;
    }
    
    public static void drawFilledBox(final AxisAlignedBB bb, final int color) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179120_a(770, 771, 0, 1);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a(false);
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder bufferbuilder = tessellator.func_178180_c();
        bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
    }
    
    public static void drawSolidBox() {
        drawSolidBox(RenderUtils.DEFAULT_AABB);
    }
    
    public static void drawSolidBox(final AxisAlignedBB bb) {
        GL11.glBegin(7);
        GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c);
        GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c);
        GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f);
        GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f);
        GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c);
        GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f);
        GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f);
        GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c);
        GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c);
        GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c);
        GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c);
        GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c);
        GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c);
        GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c);
        GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f);
        GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f);
        GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f);
        GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f);
        GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f);
        GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f);
        GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c);
        GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f);
        GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f);
        GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c);
        GL11.glEnd();
    }
    
    public static void drawBoundingBox(final AxisAlignedBB bb, final float width, final int argb) {
        final int a = argb >>> 24 & 0xFF;
        final int r = argb >>> 16 & 0xFF;
        final int g = argb >>> 8 & 0xFF;
        final int b = argb & 0xFF;
        drawBoundingBox(bb, width, r, g, b, a);
    }
    
    public static void drawBoundingBox(final AxisAlignedBB bb, final float width, final int red, final int green, final int blue, final int alpha) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179120_a(770, 771, 0, 1);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a(false);
        GlStateManager.func_187441_d(width);
        final BufferBuilder bufferbuilder = RenderUtils.INSTANCE.func_178180_c();
        bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181669_b(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181669_b(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181669_b(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181669_b(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181669_b(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181669_b(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181669_b(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181669_b(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181669_b(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181669_b(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181669_b(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181669_b(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181669_b(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181669_b(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181669_b(red, green, blue, alpha).func_181675_d();
        bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181669_b(red, green, blue, alpha).func_181675_d();
        render();
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
    }
    
    public static void GLPre(final float lineWidth) {
        RenderUtils.depth = GL11.glIsEnabled(2896);
        RenderUtils.texture = GL11.glIsEnabled(3042);
        RenderUtils.clean = GL11.glIsEnabled(3553);
        RenderUtils.bind = GL11.glIsEnabled(2929);
        RenderUtils.override = GL11.glIsEnabled(2848);
        GLPre(RenderUtils.depth, RenderUtils.texture, RenderUtils.clean, RenderUtils.bind, RenderUtils.override, lineWidth);
    }
    
    public static void GlPost() {
        GLPost(RenderUtils.depth, RenderUtils.texture, RenderUtils.clean, RenderUtils.bind, RenderUtils.override);
    }
    
    private static void GLPre(final boolean depth, final boolean texture, final boolean clean, final boolean bind, final boolean override, final float lineWidth) {
        if (depth) {
            GL11.glDisable(2896);
        }
        if (!texture) {
            GL11.glEnable(3042);
        }
        GL11.glLineWidth(lineWidth);
        if (clean) {
            GL11.glDisable(3553);
        }
        if (bind) {
            GL11.glDisable(2929);
        }
        if (!override) {
            GL11.glEnable(2848);
        }
        GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GL11.glHint(3154, 4354);
        GlStateManager.func_179132_a(false);
    }
    
    private static void GLPost(final boolean depth, final boolean texture, final boolean clean, final boolean bind, final boolean override) {
        GlStateManager.func_179132_a(true);
        if (!override) {
            GL11.glDisable(2848);
        }
        if (bind) {
            GL11.glEnable(2929);
        }
        if (clean) {
            GL11.glEnable(3553);
        }
        if (!texture) {
            GL11.glDisable(3042);
        }
        if (depth) {
            GL11.glEnable(2896);
        }
    }
    
    public static void rainbowOutline(final int x, final int y, final float width, final float height, final float lineThick, final int sat, final int bri) {
        final int[] counterChing = { 1 };
        for (int i = 0; i < width / 2.0f; ++i) {
            final Color rainbowCol = RainbowUtils.anyRainbowColor(counterChing[0] * 50, sat, bri);
            drawRecta((float)(x + i), (float)y, (float)(i + 2), lineThick, rainbowCol.getRGB());
            final int[] array = counterChing;
            final int n = 0;
            ++array[n];
        }
        for (int d = 0; d < height / 2.0f; ++d) {
            final Color rainbowCol = RainbowUtils.anyRainbowColor(counterChing[0] * 50, sat, 255);
            drawRecta(x + width, (float)(y + d), lineThick, (float)(d + 2), rainbowCol.getRGB());
            final int[] array2 = counterChing;
            final int n2 = 0;
            ++array2[n2];
        }
        for (int c = 0; c < width / 2.0f; ++c) {
            final Color rainbowCol = RainbowUtils.anyRainbowColor(counterChing[0] * 50, sat, bri);
            drawRecta((float)(x + c), y + height + lineThick / 2.0f, (float)(c + 2), lineThick, rainbowCol.getRGB());
            final int[] array3 = counterChing;
            final int n3 = 0;
            ++array3[n3];
        }
        for (int j = 0; j < height / 2.0f; ++j) {
            final Color rainbowCol = RainbowUtils.anyRainbowColor(counterChing[0] * 100, sat, bri);
            drawRecta((float)x, (float)(y + j), lineThick, (float)(j + 2), rainbowCol.getRGB());
            final int[] array4 = counterChing;
            final int n4 = 0;
            ++array4[n4];
        }
    }
    
    public static void renderEntity(final EntityPlayer entity, final ModelBase modelBase, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        if (RenderUtils.mc.func_175598_ae() == null) {
            return;
        }
        if (modelBase instanceof ModelPlayer) {
            final ModelPlayer modelPlayer = (ModelPlayer)modelBase;
            modelPlayer.field_178720_f.field_78806_j = false;
            modelPlayer.field_178730_v.field_78806_j = false;
            modelPlayer.field_178733_c.field_78806_j = false;
            modelPlayer.field_178731_d.field_78806_j = false;
            modelPlayer.field_178734_a.field_78806_j = false;
            modelPlayer.field_178732_b.field_78806_j = false;
        }
        final float partialTicks = RenderUtils.mc.func_184121_ak();
        final double x = entity.field_70165_t - RenderUtils.mc.func_175598_ae().field_78730_l;
        double y = entity.field_70163_u - RenderUtils.mc.func_175598_ae().field_78731_m;
        final double z = entity.field_70161_v - RenderUtils.mc.func_175598_ae().field_78728_n;
        GlStateManager.func_179094_E();
        if (entity.func_70093_af()) {
            y -= 0.125;
        }
        renderLivingAt(x, y, z);
        prepareRotations((EntityLivingBase)entity);
        final float f4 = prepareScale((EntityLivingBase)entity, scale);
        final float yaw = handleRotationFloat((EntityLivingBase)entity, partialTicks);
        GlStateManager.func_179141_d();
        modelBase.func_78086_a((EntityLivingBase)entity, limbSwing, limbSwingAmount, partialTicks);
        modelBase.func_78087_a(limbSwing, limbSwingAmount, 0.0f, yaw, entity.field_70125_A, f4, (Entity)entity);
        modelBase.func_78088_a((Entity)entity, limbSwing, limbSwingAmount, 0.0f, yaw, entity.field_70125_A, f4);
        GlStateManager.func_179121_F();
    }
    
    public static void renderLivingAt(final double x, final double y, final double z) {
        GlStateManager.func_179109_b((float)x, (float)y, (float)z);
    }
    
    public static float prepareScale(final EntityLivingBase entity, final float scale) {
        GlStateManager.func_179091_B();
        GlStateManager.func_179152_a(-1.0f, -1.0f, 1.0f);
        final double widthX = entity.func_184177_bl().field_72336_d - entity.func_184177_bl().field_72340_a;
        final double widthZ = entity.func_184177_bl().field_72334_f - entity.func_184177_bl().field_72339_c;
        GlStateManager.func_179139_a(scale + widthX, (double)(scale * entity.field_70131_O), scale + widthZ);
        final float f = 0.0625f;
        GlStateManager.func_179109_b(0.0f, -1.501f, 0.0f);
        return 0.0625f;
    }
    
    public static void prepareRotations(final EntityLivingBase entityLivingBase) {
        GlStateManager.func_179114_b(180.0f - entityLivingBase.field_70177_z, 0.0f, 1.0f, 0.0f);
    }
    
    public static float handleRotationFloat(final EntityLivingBase livingBase, final float partialTicks) {
        return livingBase.field_70759_as;
    }
    
    public static void drawSidewaysGradient(final float x, final float y, final float width, final float height, final Color color1, final Color color2, final int alpha1, final int alpha2) {
        prepareGL();
        GL11.glShadeModel(7425);
        GL11.glEnable(2848);
        GL11.glBegin(7);
        GL11.glColor4f(color1.getRed() / 255.0f, color1.getGreen() / 255.0f, color1.getBlue() / 255.0f, alpha1 / 255.0f);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glColor4f(color2.getRed() / 255.0f, color2.getGreen() / 255.0f, color2.getBlue() / 255.0f, alpha2 / 255.0f);
        GL11.glVertex2d((double)(x + width), (double)y);
        GL11.glColor4f(color2.getRed() / 255.0f, color2.getGreen() / 255.0f, color2.getBlue() / 255.0f, alpha2 / 255.0f);
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glColor4f(color1.getRed() / 255.0f, color1.getGreen() / 255.0f, color1.getBlue() / 255.0f, alpha1 / 255.0f);
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glEnd();
        releaseGL();
    }
    
    public static void drawCircle(final float x, final float y, final float z, final float radius, final Color color) {
        final BlockPos pos = new BlockPos((double)x, (double)y, (double)z);
        final AxisAlignedBB bb = new AxisAlignedBB(pos.func_177958_n() - RenderUtils.mc.func_175598_ae().field_78730_l, pos.func_177956_o() - RenderUtils.mc.func_175598_ae().field_78731_m, pos.func_177952_p() - RenderUtils.mc.func_175598_ae().field_78728_n, pos.func_177958_n() + 1 - RenderUtils.mc.func_175598_ae().field_78730_l, pos.func_177956_o() + 1 - RenderUtils.mc.func_175598_ae().field_78731_m, pos.func_177952_p() + 1 - RenderUtils.mc.func_175598_ae().field_78728_n);
        drawCircleVertices(bb, radius, color);
    }
    
    public static void drawCircleVertices(final AxisAlignedBB bb, final float radius, final Color color) {
        final float r = color.getRed() / 255.0f;
        final float g = color.getGreen() / 255.0f;
        final float b = color.getBlue() / 255.0f;
        final float a = color.getAlpha() / 255.0f;
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder buffer = tessellator.func_178180_c();
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179097_i();
        GlStateManager.func_179120_a(770, 771, 0, 1);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(1.0f);
        for (int i = 0; i < 360; ++i) {
            buffer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
            buffer.func_181662_b(bb.func_189972_c().field_72450_a + Math.sin(i * 3.1415926 / 180.0) * radius, bb.field_72338_b, bb.func_189972_c().field_72449_c + Math.cos(i * 3.1415926 / 180.0) * radius).func_181666_a(r, g, b, a).func_181675_d();
            buffer.func_181662_b(bb.func_189972_c().field_72450_a + Math.sin((i + 1) * 3.1415926 / 180.0) * radius, bb.field_72338_b, bb.func_189972_c().field_72449_c + Math.cos((i + 1) * 3.1415926 / 180.0) * radius).func_181666_a(r, g, b, a).func_181675_d();
            tessellator.func_78381_a();
        }
        GL11.glDisable(2848);
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179126_j();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
    }
    
    public static void drawEntityOnScreen(final Entity ent, final int posX, final int posY, final int scale, final float mouseX, final float mouseY) {
        GlStateManager.func_179142_g();
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b((float)posX, (float)posY, 50.0f);
        GlStateManager.func_179152_a((float)(-scale), (float)scale, (float)scale);
        GlStateManager.func_179114_b(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179114_b(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.func_74519_b();
        GlStateManager.func_179114_b(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(-(float)Math.atan(mouseY / 40.0f) * 20.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179109_b(0.0f, 0.0f, 0.0f);
        final RenderManager rendermanager = Minecraft.func_71410_x().func_175598_ae();
        rendermanager.func_178631_a(180.0f);
        rendermanager.func_178633_a(false);
        rendermanager.func_188391_a(ent, 0.0, 0.0, 0.0, 0.0f, 1.0f, false);
        rendermanager.func_178633_a(true);
        GlStateManager.func_179121_F();
        RenderHelper.func_74518_a();
        GlStateManager.func_179101_C();
        GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
        GlStateManager.func_179090_x();
        GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
    }
    
    public static AxisAlignedBB fixBB(final AxisAlignedBB bb) {
        return new AxisAlignedBB(bb.field_72340_a - RenderUtils.mc.func_175598_ae().field_78730_l, bb.field_72338_b - RenderUtils.mc.func_175598_ae().field_78731_m, bb.field_72339_c - RenderUtils.mc.func_175598_ae().field_78728_n, bb.field_72336_d - RenderUtils.mc.func_175598_ae().field_78730_l, bb.field_72337_e - RenderUtils.mc.func_175598_ae().field_78731_m, bb.field_72334_f - RenderUtils.mc.func_175598_ae().field_78728_n);
    }
    
    public static void drawOpenGradientBoxBB(final AxisAlignedBB bb, final Color startColor, final Color endColor, final boolean depth) {
        for (final EnumFacing face : EnumFacing.values()) {
            drawGradientPlaneBB(bb.func_186662_g(0.0020000000949949026), face, startColor, endColor, depth);
        }
    }
    
    public static void drawGradientPlaneBB(final AxisAlignedBB bb, final EnumFacing face, final Color startColor, final Color endColor, final boolean depth) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder builder = tessellator.func_178180_c();
        final float red = startColor.getRed() / 255.0f;
        final float green = startColor.getGreen() / 255.0f;
        final float blue = startColor.getBlue() / 255.0f;
        final float alpha = startColor.getAlpha() / 255.0f;
        final float red2 = endColor.getRed() / 255.0f;
        final float green2 = endColor.getGreen() / 255.0f;
        final float blue2 = endColor.getBlue() / 255.0f;
        final float alpha2 = endColor.getAlpha() / 255.0f;
        double x1 = 0.0;
        double y1 = 0.0;
        double z1 = 0.0;
        double x2 = 0.0;
        double y2 = 0.0;
        double z2 = 0.0;
        switch (face) {
            case DOWN: {
                x1 = bb.field_72340_a;
                x2 = bb.field_72336_d;
                y1 = bb.field_72338_b;
                y2 = bb.field_72338_b;
                z1 = bb.field_72339_c;
                z2 = bb.field_72334_f;
                break;
            }
            case UP: {
                x1 = bb.field_72340_a;
                x2 = bb.field_72336_d;
                y1 = bb.field_72337_e;
                y2 = bb.field_72337_e;
                z1 = bb.field_72339_c;
                z2 = bb.field_72334_f;
                break;
            }
            case EAST: {
                x1 = bb.field_72336_d;
                x2 = bb.field_72336_d;
                y1 = bb.field_72338_b;
                y2 = bb.field_72337_e;
                z1 = bb.field_72339_c;
                z2 = bb.field_72334_f;
                break;
            }
            case WEST: {
                x1 = bb.field_72340_a;
                x2 = bb.field_72340_a;
                y1 = bb.field_72338_b;
                y2 = bb.field_72337_e;
                z1 = bb.field_72339_c;
                z2 = bb.field_72334_f;
                break;
            }
            case SOUTH: {
                x1 = bb.field_72340_a;
                x2 = bb.field_72336_d;
                y1 = bb.field_72338_b;
                y2 = bb.field_72337_e;
                z1 = bb.field_72334_f;
                z2 = bb.field_72334_f;
                break;
            }
            case NORTH: {
                x1 = bb.field_72340_a;
                x2 = bb.field_72336_d;
                y1 = bb.field_72338_b;
                y2 = bb.field_72337_e;
                z1 = bb.field_72339_c;
                z2 = bb.field_72339_c;
                break;
            }
        }
        if (face != EnumFacing.DOWN) {
            if (face != EnumFacing.UP) {
                if (face != EnumFacing.EAST) {
                    if (face != EnumFacing.WEST) {
                        if (face != EnumFacing.SOUTH) {
                            if (face == EnumFacing.NORTH) {}
                        }
                    }
                }
            }
        }
        GlStateManager.func_179094_E();
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        if (!depth) {
            GlStateManager.func_179132_a(false);
            GlStateManager.func_179097_i();
        }
        builder.func_181668_a(5, DefaultVertexFormats.field_181706_f);
        if (face == EnumFacing.EAST || face == EnumFacing.WEST || face == EnumFacing.NORTH || face == EnumFacing.SOUTH) {
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
        }
        else if (face == EnumFacing.UP) {
            builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
        }
        else if (face == EnumFacing.DOWN) {
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x1, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
            builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
        }
        tessellator.func_78381_a();
        if (!depth) {
            GlStateManager.func_179132_a(true);
            GlStateManager.func_179126_j();
        }
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
        GlStateManager.func_179121_F();
    }
    
    public static void drawBoxBB(final AxisAlignedBB bb, final Color color) {
        RenderUtils.camera.func_78547_a(Objects.requireNonNull(RenderUtils.mc.func_175606_aa()).field_70165_t, RenderUtils.mc.func_175606_aa().field_70163_u, RenderUtils.mc.func_175606_aa().field_70161_v);
        if (RenderUtils.camera.func_78546_a(new AxisAlignedBB(bb.field_72340_a + RenderUtils.mc.func_175598_ae().field_78730_l, bb.field_72338_b + RenderUtils.mc.func_175598_ae().field_78731_m, bb.field_72339_c + RenderUtils.mc.func_175598_ae().field_78728_n, bb.field_72336_d + RenderUtils.mc.func_175598_ae().field_78730_l, bb.field_72337_e + RenderUtils.mc.func_175598_ae().field_78731_m, bb.field_72334_f + RenderUtils.mc.func_175598_ae().field_78728_n))) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179147_l();
            GlStateManager.func_179097_i();
            GlStateManager.func_179120_a(770, 771, 0, 1);
            GlStateManager.func_179090_x();
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            RenderGlobal.func_189696_b(bb, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            GL11.glDisable(2848);
            GlStateManager.func_179126_j();
            GlStateManager.func_179098_w();
            GlStateManager.func_179084_k();
            GlStateManager.func_179121_F();
        }
    }
    
    public static void drawLine(final double x, final double y, final double x1, final double y1, final float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
    }
    
    static {
        RenderUtils.camera = (ICamera)new Frustum();
        frustrum = new Frustum();
        RenderUtils.INSTANCE = new RenderUtils();
        DEFAULT_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.0);
    }
}
