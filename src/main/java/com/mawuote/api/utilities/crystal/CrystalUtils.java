package com.mawuote.api.utilities.crystal;

import com.mawuote.api.utilities.*;
import net.minecraft.entity.player.*;
import com.mawuote.api.utilities.math.*;
import com.mawuote.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.*;
import com.mawuote.api.utilities.render.*;
import java.awt.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class CrystalUtils implements IMinecraft
{
    public static EntityPlayer getTarget(final float range) {
        EntityPlayer targetPlayer = null;
        for (final EntityPlayer player : new ArrayList(CrystalUtils.mc.field_71441_e.field_73010_i)) {
            if (CrystalUtils.mc.field_71439_g.func_70068_e((Entity)player) > MathUtils.square(range)) {
                continue;
            }
            if (player == CrystalUtils.mc.field_71439_g) {
                continue;
            }
            if (Kaotik.FRIEND_MANAGER.isFriend(player.func_70005_c_())) {
                continue;
            }
            if (player.field_70128_L) {
                continue;
            }
            if (player.func_110143_aJ() <= 0.0f) {
                continue;
            }
            if (targetPlayer == null) {
                targetPlayer = player;
            }
            else {
                if (CrystalUtils.mc.field_71439_g.func_70068_e((Entity)player) >= CrystalUtils.mc.field_71439_g.func_70068_e((Entity)targetPlayer)) {
                    continue;
                }
                targetPlayer = player;
            }
        }
        return targetPlayer;
    }
    
    public static List<BlockPos> getSphere(final float range, final boolean sphere, final boolean hollow) {
        final List<BlockPos> blocks = new ArrayList<BlockPos>();
        for (int x = CrystalUtils.mc.field_71439_g.func_180425_c().func_177958_n() - (int)range; x <= CrystalUtils.mc.field_71439_g.func_180425_c().func_177958_n() + range; ++x) {
            for (int z = CrystalUtils.mc.field_71439_g.func_180425_c().func_177952_p() - (int)range; z <= CrystalUtils.mc.field_71439_g.func_180425_c().func_177952_p() + range; ++z) {
                for (int y = sphere ? (CrystalUtils.mc.field_71439_g.func_180425_c().func_177956_o() - (int)range) : CrystalUtils.mc.field_71439_g.func_180425_c().func_177956_o(); y < CrystalUtils.mc.field_71439_g.func_180425_c().func_177956_o() + range; ++y) {
                    final double distance = (CrystalUtils.mc.field_71439_g.func_180425_c().func_177958_n() - x) * (CrystalUtils.mc.field_71439_g.func_180425_c().func_177958_n() - x) + (CrystalUtils.mc.field_71439_g.func_180425_c().func_177952_p() - z) * (CrystalUtils.mc.field_71439_g.func_180425_c().func_177952_p() - z) + (sphere ? ((CrystalUtils.mc.field_71439_g.func_180425_c().func_177956_o() - y) * (CrystalUtils.mc.field_71439_g.func_180425_c().func_177956_o() - y)) : 0);
                    if (distance < range * range && (!hollow || distance >= (range - 1.0) * (range - 1.0))) {
                        blocks.add(new BlockPos(x, y, z));
                    }
                }
            }
        }
        return blocks;
    }
    
    public static boolean canPlaceCrystal(final BlockPos position, final boolean placeUnderBlock, final boolean multiPlace, final boolean holePlace) {
        if (CrystalUtils.mc.field_71441_e.func_180495_p(position).func_177230_c() != Blocks.field_150357_h && CrystalUtils.mc.field_71441_e.func_180495_p(position).func_177230_c() != Blocks.field_150343_Z) {
            return false;
        }
        if (CrystalUtils.mc.field_71441_e.func_180495_p(position.func_177982_a(0, 1, 0)).func_177230_c() != Blocks.field_150350_a || (!placeUnderBlock && CrystalUtils.mc.field_71441_e.func_180495_p(position.func_177982_a(0, 2, 0)).func_177230_c() != Blocks.field_150350_a)) {
            return false;
        }
        if (multiPlace) {
            return CrystalUtils.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(position.func_177982_a(0, 1, 0))).isEmpty() && !placeUnderBlock && CrystalUtils.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(position.func_177982_a(0, 2, 0))).isEmpty();
        }
        for (final Entity entity : CrystalUtils.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(position.func_177982_a(0, 1, 0)))) {
            if (entity instanceof EntityEnderCrystal) {
                continue;
            }
            return false;
        }
        if (!placeUnderBlock) {
            for (final Entity entity : CrystalUtils.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(position.func_177982_a(0, 2, 0)))) {
                if (!(entity instanceof EntityEnderCrystal)) {
                    if (holePlace && entity instanceof EntityPlayer) {
                        continue;
                    }
                    return false;
                }
            }
        }
        return true;
    }
    
    public static void drawText(final BlockPos pos, final String text) {
        GlStateManager.func_179094_E();
        RenderUtils.glBillboardDistanceScaled(pos.func_177958_n() + 0.5f, pos.func_177956_o() + 0.5f, pos.func_177952_p() + 0.5f, (EntityPlayer)CrystalUtils.mc.field_71439_g, 1.0f);
        GlStateManager.func_179097_i();
        GlStateManager.func_179137_b(-(Kaotik.FONT_MANAGER.getStringWidth(text) / 2.0), 0.0, 0.0);
        Kaotik.FONT_MANAGER.drawString(text, 0.0f, 0.0f, Color.WHITE);
        GlStateManager.func_179121_F();
    }
    
    public static boolean isEntityMoving(final EntityLivingBase entity) {
        return entity.field_70159_w > 2.0 || entity.field_70181_x > 2.0 || entity.field_70179_y > 2.0;
    }
    
    public static boolean canSeePos(final BlockPos pos) {
        return CrystalUtils.mc.field_71441_e.func_147447_a(new Vec3d(CrystalUtils.mc.field_71439_g.field_70165_t, CrystalUtils.mc.field_71439_g.field_70163_u + CrystalUtils.mc.field_71439_g.func_70047_e(), CrystalUtils.mc.field_71439_g.field_70161_v), new Vec3d((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p()), false, true, false) == null;
    }
}
