package com.mawuote.api.utilities.render;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;

public class ScissorUtil
{
    public static void scissor(double x, double y, double width, double height) {
        final ScaledResolution sr = new ScaledResolution(Minecraft.func_71410_x());
        final double scale = sr.func_78325_e();
        y = sr.func_78328_b() - y;
        x *= scale;
        y *= scale;
        width *= scale;
        height *= scale;
        GL11.glScissor((int)x, (int)(y - height), (int)width, (int)height);
    }
}
