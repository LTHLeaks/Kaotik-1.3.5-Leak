package com.mawuote.api.utilities.world;

import com.mawuote.api.utilities.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;

public class HoleUtils implements IMinecraft
{
    public static boolean isBedrockHole(final BlockPos pos) {
        boolean retVal = false;
        if (HoleUtils.mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(Blocks.field_150350_a) && HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177984_a()).func_177230_c().equals(Blocks.field_150350_a) && HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177984_a().func_177984_a()).func_177230_c().equals(Blocks.field_150350_a) && HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c().equals(Blocks.field_150357_h) && HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c().equals(Blocks.field_150357_h) && HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c().equals(Blocks.field_150357_h) && HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c().equals(Blocks.field_150357_h) && HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c().equals(Blocks.field_150357_h)) {
            retVal = true;
        }
        return retVal;
    }
    
    public static boolean isObiHole(final BlockPos pos) {
        boolean retVal = false;
        int obiCount = 0;
        if (HoleUtils.mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(Blocks.field_150350_a) && HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177984_a()).func_177230_c().equals(Blocks.field_150350_a) && HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177984_a().func_177984_a()).func_177230_c().equals(Blocks.field_150350_a) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c().equals(Blocks.field_150343_Z))) {
            if (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c().equals(Blocks.field_150343_Z)) {
                ++obiCount;
            }
            if (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c().equals(Blocks.field_150343_Z)) {
                if (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c().equals(Blocks.field_150343_Z)) {
                    ++obiCount;
                }
                if (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c().equals(Blocks.field_150343_Z)) {
                    if (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c().equals(Blocks.field_150343_Z)) {
                        ++obiCount;
                    }
                    if (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c().equals(Blocks.field_150343_Z)) {
                        if (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c().equals(Blocks.field_150343_Z)) {
                            ++obiCount;
                        }
                        if (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c().equals(Blocks.field_150343_Z)) {
                            if (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c().equals(Blocks.field_150343_Z)) {
                                ++obiCount;
                            }
                            if (obiCount >= 1) {
                                retVal = true;
                            }
                        }
                    }
                }
            }
        }
        return retVal;
    }
    
    public static boolean isDoubleHole(final BlockPos pos) {
        for (final EnumFacing f : EnumFacing.field_176754_o) {
            final int offX = f.func_82601_c();
            final int offZ = f.func_82599_e();
            if ((HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177982_a(offX, 0, offZ)).func_177230_c() == Blocks.field_150343_Z || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177982_a(offX, 0, offZ)).func_177230_c() == Blocks.field_150357_h) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177982_a(offX * -2, 0, offZ * -2)).func_177230_c() == Blocks.field_150343_Z || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177982_a(offX * -2, 0, offZ * -2)).func_177230_c() == Blocks.field_150357_h) && HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177982_a(offX * -1, 0, offZ * -1)).func_177230_c() == Blocks.field_150350_a && isSafeBlock(pos.func_177982_a(0, -1, 0)) && isSafeBlock(pos.func_177982_a(offX * -1, -1, offZ * -1))) {
                if (offZ == 0 && isSafeBlock(pos.func_177982_a(0, 0, 1)) && isSafeBlock(pos.func_177982_a(0, 0, -1)) && isSafeBlock(pos.func_177982_a(offX * -1, 0, 1)) && isSafeBlock(pos.func_177982_a(offX * -1, 0, -1))) {
                    return true;
                }
                if (offX == 0 && isSafeBlock(pos.func_177982_a(1, 0, 0)) && isSafeBlock(pos.func_177982_a(-1, 0, 0)) && isSafeBlock(pos.func_177982_a(1, 0, offZ * -1)) && isSafeBlock(pos.func_177982_a(-1, 0, offZ * -1))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean isHole(final BlockPos pos) {
        boolean retVal = false;
        if (HoleUtils.mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(Blocks.field_150350_a) && HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177984_a()).func_177230_c().equals(Blocks.field_150350_a) && HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177984_a().func_177984_a()).func_177230_c().equals(Blocks.field_150350_a) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c().equals(Blocks.field_150343_Z))) {
            retVal = true;
        }
        return retVal;
    }
    
    public static boolean isPlayerInHole() {
        boolean retVal = false;
        final BlockPos pos = new BlockPos(HoleUtils.mc.field_71439_g.field_70165_t, HoleUtils.mc.field_71439_g.field_70163_u, HoleUtils.mc.field_71439_g.field_70161_v);
        if (HoleUtils.mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(Blocks.field_150350_a) && HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177984_a()).func_177230_c().equals(Blocks.field_150350_a) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c().equals(Blocks.field_150343_Z))) {
            retVal = true;
        }
        return retVal;
    }
    
    public static boolean isEntityInHole(final Entity entity) {
        boolean retVal = false;
        final BlockPos pos = new BlockPos(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
        if (HoleUtils.mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(Blocks.field_150350_a) && HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177984_a()).func_177230_c().equals(Blocks.field_150350_a) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c().equals(Blocks.field_150343_Z))) {
            retVal = true;
        }
        return retVal;
    }
    
    public static boolean isPlayerTrapped() {
        boolean retVal = false;
        final BlockPos pos = new BlockPos(HoleUtils.mc.field_71439_g.field_70165_t, HoleUtils.mc.field_71439_g.field_70163_u + 1.0, HoleUtils.mc.field_71439_g.field_70161_v);
        if (!HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177984_a()).func_177230_c().equals(Blocks.field_150350_a)) {
            retVal = true;
        }
        return retVal;
    }
    
    public static boolean isEntityTrapped(final Entity entity) {
        boolean retVal = false;
        final BlockPos pos = new BlockPos(entity.field_70165_t, entity.field_70163_u + 1.0, entity.field_70161_v);
        if (!HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177984_a()).func_177230_c().equals(Blocks.field_150350_a)) {
            retVal = true;
        }
        return retVal;
    }
    
    public static boolean isInHole(final EntityPlayer player) {
        boolean retVal = false;
        final BlockPos pos = new BlockPos(player.field_70165_t, player.field_70163_u, player.field_70161_v);
        if (HoleUtils.mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(Blocks.field_150350_a) && HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177984_a()).func_177230_c().equals(Blocks.field_150350_a) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c().equals(Blocks.field_150357_h) || HoleUtils.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c().equals(Blocks.field_150343_Z))) {
            retVal = true;
        }
        return retVal;
    }
    
    static boolean isSafeBlock(final BlockPos pos) {
        return HoleUtils.mc.field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150343_Z || HoleUtils.mc.field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150357_h;
    }
    
    public static Vec3d centerPos(final double posX, final double posY, final double posZ) {
        return new Vec3d(Math.floor(posX) + 0.5, Math.floor(posY), Math.floor(posZ) + 0.5);
    }
    
    public static boolean isDoubleBedrockHoleX(final BlockPos blockPos) {
        if (!HoleUtils.mc.field_71441_e.func_180495_p(blockPos).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 0, 0)).func_177230_c().equals(Blocks.field_150350_a) || (!HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 1, 0)).func_177230_c().equals(Blocks.field_150350_a)) || (!HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 2, 0)).func_177230_c().equals(Blocks.field_150350_a))) {
            return false;
        }
        for (final BlockPos blockPos2 : new BlockPos[] { blockPos.func_177982_a(2, 0, 0), blockPos.func_177982_a(1, 0, 1), blockPos.func_177982_a(1, 0, -1), blockPos.func_177982_a(-1, 0, 0), blockPos.func_177982_a(0, 0, 1), blockPos.func_177982_a(0, 0, -1), blockPos.func_177982_a(0, -1, 0), blockPos.func_177982_a(1, -1, 0) }) {
            final IBlockState iBlockState = HoleUtils.mc.field_71441_e.func_180495_p(blockPos2);
            if (iBlockState.func_177230_c() == Blocks.field_150350_a || iBlockState.func_177230_c() != Blocks.field_150357_h) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isDoubleBedrockHoleZ(final BlockPos blockPos) {
        if (!HoleUtils.mc.field_71441_e.func_180495_p(blockPos).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 0, 1)).func_177230_c().equals(Blocks.field_150350_a) || (!HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 1)).func_177230_c().equals(Blocks.field_150350_a)) || (!HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 1)).func_177230_c().equals(Blocks.field_150350_a))) {
            return false;
        }
        for (final BlockPos blockPos2 : new BlockPos[] { blockPos.func_177982_a(0, 0, 2), blockPos.func_177982_a(1, 0, 1), blockPos.func_177982_a(-1, 0, 1), blockPos.func_177982_a(0, 0, -1), blockPos.func_177982_a(1, 0, 0), blockPos.func_177982_a(-1, 0, 0), blockPos.func_177982_a(0, -1, 0), blockPos.func_177982_a(0, -1, 1) }) {
            final IBlockState iBlockState = HoleUtils.mc.field_71441_e.func_180495_p(blockPos2);
            if (iBlockState.func_177230_c() == Blocks.field_150350_a || iBlockState.func_177230_c() != Blocks.field_150357_h) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isDoubleObsidianHoleX(final BlockPos blockPos) {
        if (!HoleUtils.mc.field_71441_e.func_180495_p(blockPos).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 0, 0)).func_177230_c().equals(Blocks.field_150350_a) || (!HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 1, 0)).func_177230_c().equals(Blocks.field_150350_a)) || (!HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 2, 0)).func_177230_c().equals(Blocks.field_150350_a))) {
            return false;
        }
        for (final BlockPos blockPos2 : new BlockPos[] { blockPos.func_177982_a(2, 0, 0), blockPos.func_177982_a(1, 0, 1), blockPos.func_177982_a(1, 0, -1), blockPos.func_177982_a(-1, 0, 0), blockPos.func_177982_a(0, 0, 1), blockPos.func_177982_a(0, 0, -1), blockPos.func_177982_a(0, -1, 0), blockPos.func_177982_a(1, -1, 0) }) {
            if (BlockUtils.getBlockResistance(blockPos2) != BlockUtils.BlockResistance.Resistant && BlockUtils.getBlockResistance(blockPos2) != BlockUtils.BlockResistance.Unbreakable) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isDoubleObsidianHoleZ(final BlockPos blockPos) {
        if (!HoleUtils.mc.field_71441_e.func_180495_p(blockPos).func_177230_c().equals(Blocks.field_150350_a) || !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 0, 1)).func_177230_c().equals(Blocks.field_150350_a) || (!HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 1, 1)).func_177230_c().equals(Blocks.field_150350_a)) || (!HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a) && !HoleUtils.mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 2, 1)).func_177230_c().equals(Blocks.field_150350_a))) {
            return false;
        }
        for (final BlockPos blockPos2 : new BlockPos[] { blockPos.func_177982_a(0, 0, 2), blockPos.func_177982_a(1, 0, 1), blockPos.func_177982_a(-1, 0, 1), blockPos.func_177982_a(0, 0, -1), blockPos.func_177982_a(1, 0, 0), blockPos.func_177982_a(-1, 0, 0), blockPos.func_177982_a(0, -1, 0), blockPos.func_177982_a(0, -1, 1) }) {
            if (BlockUtils.getBlockResistance(blockPos2) != BlockUtils.BlockResistance.Resistant && BlockUtils.getBlockResistance(blockPos2) != BlockUtils.BlockResistance.Unbreakable) {
                return false;
            }
        }
        return true;
    }
}
