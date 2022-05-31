package com.mawuote.api.utilities.math;

import java.math.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;

public class MathUtils
{
    public static double roundToPlaces(final double number, final int places) {
        BigDecimal decimal = new BigDecimal(number);
        decimal = decimal.setScale(places, RoundingMode.HALF_UP);
        return decimal.doubleValue();
    }
    
    public static Vec3d roundVector(final Vec3d vec3d, final int places) {
        return new Vec3d(roundToPlaces(vec3d.field_72450_a, places), roundToPlaces(vec3d.field_72448_b, places), roundToPlaces(vec3d.field_72449_c, places));
    }
    
    public static double square(final double input) {
        return input * input;
    }
    
    public static float square(final float input) {
        return input * input;
    }
    
    public static double distance(final float x, final float y, final float x1, final float y1) {
        return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
    }
    
    public static Vec3d getInterpolatedRenderPos(final Entity entity, final float ticks) {
        return interpolateEntity(entity, ticks).func_178786_a(Minecraft.func_71410_x().func_175598_ae().field_78725_b, Minecraft.func_71410_x().func_175598_ae().field_78726_c, Minecraft.func_71410_x().func_175598_ae().field_78723_d);
    }
    
    public static Vec3d interpolateEntity(final Entity entity, final float time) {
        return new Vec3d(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * time, entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * time, entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * time);
    }
    
    public static double[] directionSpeed(final double speed) {
        final Minecraft mc = Minecraft.func_71410_x();
        float forward = mc.field_71439_g.field_71158_b.field_192832_b;
        float side = mc.field_71439_g.field_71158_b.field_78902_a;
        float yaw = mc.field_71439_g.field_70126_B + (mc.field_71439_g.field_70177_z - mc.field_71439_g.field_70126_B) * mc.func_184121_ak();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[] { posX, posZ };
    }
    
    public static Vec3d mult(final Vec3d factor, final float multiplier) {
        return new Vec3d(factor.field_72450_a * multiplier, factor.field_72448_b * multiplier, factor.field_72449_c * multiplier);
    }
    
    public static Vec3d div(final Vec3d factor, final float divisor) {
        return new Vec3d(factor.field_72450_a / divisor, factor.field_72448_b / divisor, factor.field_72449_c / divisor);
    }
}
