package com.mawuote.api.utilities.world;

import com.mawuote.api.utilities.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import com.mawuote.api.utilities.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;

public class BlockUtils implements IMinecraft
{
    public static final List<Block> blackList;
    public static final List<Block> shulkerList;
    public static BlockPos[] SURROUND;
    
    public static boolean canBeClicked(final BlockPos var0) {
        return BlockUtils.mc.field_71441_e.func_180495_p(var0).func_177230_c().func_176209_a(BlockUtils.mc.field_71441_e.func_180495_p(var0), false);
    }
    
    private static float[] getLegitRotations(final Vec3d var0) {
        final Vec3d var = new Vec3d(BlockUtils.mc.field_71439_g.field_70165_t, BlockUtils.mc.field_71439_g.field_70163_u + BlockUtils.mc.field_71439_g.func_70047_e(), BlockUtils.mc.field_71439_g.field_70161_v);
        final double var2 = var0.field_72450_a - var.field_72450_a;
        final double var3 = var0.field_72448_b - var.field_72448_b;
        final double var4 = var0.field_72449_c - var.field_72449_c;
        final double var5 = Math.sqrt(var2 * var2 + var4 * var4);
        final float var6 = (float)Math.toDegrees(Math.atan2(var4, var2)) - 90.0f;
        final float var7 = (float)(-Math.toDegrees(Math.atan2(var3, var5)));
        return new float[] { BlockUtils.mc.field_71439_g.field_70177_z + MathHelper.func_76142_g(var6 - BlockUtils.mc.field_71439_g.field_70177_z), BlockUtils.mc.field_71439_g.field_70125_A + MathHelper.func_76142_g(var7 - BlockUtils.mc.field_71439_g.field_70125_A) };
    }
    
    public static void faceVectorPacketInstant(final Vec3d var0) {
        final float[] var = getLegitRotations(var0);
        BlockUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(var[0], var[1], BlockUtils.mc.field_71439_g.field_70122_E));
    }
    
    public static boolean placeBlockOnHole(final BlockPos pos, final boolean swing) {
        final EnumFacing side = getFirstFacing(pos);
        if (side == null) {
            return false;
        }
        final BlockPos neighbour = pos.func_177972_a(side);
        final EnumFacing opposite = side.func_176734_d();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).func_72441_c(0.5, 0.5, 0.5).func_178787_e(new Vec3d(opposite.func_176730_m()).func_186678_a(0.5));
        if (!BlockUtils.mc.field_71439_g.func_70093_af()) {
            BlockUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)BlockUtils.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
            BlockUtils.mc.field_71439_g.func_70095_a(true);
        }
        BlockUtils.mc.field_71442_b.func_187099_a(BlockUtils.mc.field_71439_g, BlockUtils.mc.field_71441_e, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        if (swing) {
            BlockUtils.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
        }
        BlockUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)BlockUtils.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
        return true;
    }
    
    public static boolean canPlaceCrystal(final BlockPos blockPos) {
        final BlockPos boost = blockPos.func_177982_a(0, 1, 0);
        final BlockPos boost2 = blockPos.func_177982_a(0, 2, 0);
        try {
            return (BlockUtils.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150357_h || BlockUtils.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150343_Z) && BlockUtils.mc.field_71441_e.func_180495_p(boost).func_177230_c() == Blocks.field_150350_a && BlockUtils.mc.field_71441_e.func_180495_p(boost2).func_177230_c() == Blocks.field_150350_a && BlockUtils.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty() && BlockUtils.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(boost2)).isEmpty();
        }
        catch (final Exception e) {
            return false;
        }
    }
    
    public static boolean canPlaceCrystal(final BlockPos blockPos, final boolean specialEntityCheck, final boolean oneDot15) {
        final BlockPos boost = blockPos.func_177982_a(0, 1, 0);
        final BlockPos boost2 = blockPos.func_177982_a(0, 2, 0);
        try {
            if (BlockUtils.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150357_h && BlockUtils.mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150343_Z) {
                return false;
            }
            if ((!oneDot15 && BlockUtils.mc.field_71441_e.func_180495_p(boost2).func_177230_c() != Blocks.field_150350_a) || BlockUtils.mc.field_71441_e.func_180495_p(boost).func_177230_c() != Blocks.field_150350_a) {
                return false;
            }
            for (final Entity entity : BlockUtils.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(boost))) {
                if (!entity.field_70128_L) {
                    if (specialEntityCheck && entity instanceof EntityEnderCrystal) {
                        continue;
                    }
                    return false;
                }
            }
            if (!oneDot15) {
                for (final Entity entity : BlockUtils.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(boost2))) {
                    if (!entity.field_70128_L) {
                        if (specialEntityCheck && entity instanceof EntityEnderCrystal) {
                            continue;
                        }
                        return false;
                    }
                }
            }
        }
        catch (final Exception ignored) {
            return false;
        }
        return true;
    }
    
    public static boolean rayTracePlaceCheck(final BlockPos pos, final boolean shouldCheck, final float height) {
        return !shouldCheck || BlockUtils.mc.field_71441_e.func_147447_a(new Vec3d(BlockUtils.mc.field_71439_g.field_70165_t, BlockUtils.mc.field_71439_g.field_70163_u + BlockUtils.mc.field_71439_g.func_70047_e(), BlockUtils.mc.field_71439_g.field_70161_v), new Vec3d((double)pos.func_177958_n(), (double)(pos.func_177956_o() + height), (double)pos.func_177952_p()), false, true, false) == null;
    }
    
    public static boolean rayTracePlaceCheck(final BlockPos pos, final boolean shouldCheck) {
        return rayTracePlaceCheck(pos, shouldCheck, 1.0f);
    }
    
    public static boolean rayTracePlaceCheck(final BlockPos pos) {
        return rayTracePlaceCheck(pos, true);
    }
    
    public static EnumFacing getFirstFacing(final BlockPos pos) {
        final Iterator<EnumFacing> iterator = getPossibleSides(pos).iterator();
        if (iterator.hasNext()) {
            final EnumFacing facing = iterator.next();
            return facing;
        }
        return null;
    }
    
    public static List<EnumFacing> getPossibleSides(final BlockPos pos) {
        final List<EnumFacing> facings = new ArrayList<EnumFacing>();
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.func_177972_a(side);
            if (BlockUtils.mc.field_71441_e.func_180495_p(neighbour) == null) {
                return facings;
            }
            if (BlockUtils.mc.field_71441_e.func_180495_p(neighbour).func_177230_c() == null) {
                return facings;
            }
            if (BlockUtils.mc.field_71441_e.func_180495_p(neighbour).func_177230_c().func_176209_a(BlockUtils.mc.field_71441_e.func_180495_p(neighbour), false)) {
                final IBlockState blockState = BlockUtils.mc.field_71441_e.func_180495_p(neighbour);
                if (!blockState.func_185904_a().func_76222_j()) {
                    facings.add(side);
                }
            }
        }
        return facings;
    }
    
    public static List<BlockPos> getNearbyBlocks(final EntityPlayer player, final double blockRange, final boolean motion) {
        final List<BlockPos> nearbyBlocks = new ArrayList<BlockPos>();
        final int range = (int)MathUtils.roundToPlaces(blockRange, 0);
        if (motion) {
            player.func_180425_c().func_177971_a(new Vec3i(player.field_70159_w, player.field_70181_x, player.field_70179_y));
        }
        for (int x = -range; x <= range; ++x) {
            for (int y = -range; y <= range - range / 2; ++y) {
                for (int z = -range; z <= range; ++z) {
                    nearbyBlocks.add(player.func_180425_c().func_177982_a(x, y, z));
                }
            }
        }
        return nearbyBlocks;
    }
    
    public static void placeBlock(final BlockPos blockPos, final boolean swing, final boolean packet) {
        for (final EnumFacing enumFacing : EnumFacing.values()) {
            if (!BlockUtils.mc.field_71441_e.func_180495_p(blockPos.func_177972_a(enumFacing)).func_177230_c().equals(Blocks.field_150350_a) && !isIntercepted(blockPos)) {
                if (packet) {
                    BlockUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(blockPos.func_177972_a(enumFacing), enumFacing.func_176734_d(), EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
                }
                else {
                    BlockUtils.mc.field_71442_b.func_187099_a(BlockUtils.mc.field_71439_g, BlockUtils.mc.field_71441_e, blockPos.func_177972_a(enumFacing), enumFacing.func_176734_d(), new Vec3d((Vec3i)blockPos), EnumHand.MAIN_HAND);
                }
                if (swing) {
                    BlockUtils.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                }
                return;
            }
        }
    }
    
    public static void placeBlock(final BlockPos position, final EnumHand hand, final boolean packet) {
        if (!BlockUtils.mc.field_71441_e.func_180495_p(position).func_177230_c().func_176200_f((IBlockAccess)BlockUtils.mc.field_71441_e, position)) {
            return;
        }
        if (getPlaceableSide(position) == null) {
            return;
        }
        clickBlock(position, getPlaceableSide(position), hand, packet);
        BlockUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(hand));
    }
    
    public static void clickBlock(final BlockPos position, final EnumFacing side, final EnumHand hand, final boolean packet) {
        if (packet) {
            BlockUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(position.func_177972_a(side), side.func_176734_d(), hand, 0.5f, 0.5f, 0.5f));
        }
        else {
            BlockUtils.mc.field_71442_b.func_187099_a(BlockUtils.mc.field_71439_g, BlockUtils.mc.field_71441_e, position.func_177972_a(side), side.func_176734_d(), new Vec3d((Vec3i)position), hand);
        }
    }
    
    public static boolean isIntercepted(final BlockPos blockPos) {
        for (final Entity entity : BlockUtils.mc.field_71441_e.field_72996_f) {
            if (entity instanceof EntityItem) {
                continue;
            }
            if (entity instanceof EntityEnderCrystal) {
                continue;
            }
            if (new AxisAlignedBB(blockPos).func_72326_a(entity.func_174813_aQ())) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isPositionPlaceable(final BlockPos position, final boolean entityCheck, final boolean sideCheck) {
        if (!BlockUtils.mc.field_71441_e.func_180495_p(position).func_177230_c().func_176200_f((IBlockAccess)BlockUtils.mc.field_71441_e, position)) {
            return false;
        }
        if (entityCheck) {
            for (final Entity entity : BlockUtils.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(position))) {
                if (!(entity instanceof EntityItem)) {
                    if (entity instanceof EntityXPOrb) {
                        continue;
                    }
                    return false;
                }
            }
        }
        return !sideCheck || getPlaceableSide(position) != null;
    }
    
    public static List<BlockPos> getSphere(final BlockPos pos, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = pos.func_177958_n();
        final int cy = pos.func_177956_o();
        final int cz = pos.func_177952_p();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                int y = sphere ? (cy - (int)r) : cy;
                while (true) {
                    final float f = (float)y;
                    final float f2 = sphere ? (cy + r) : ((float)(cy + h));
                    if (f >= f2) {
                        break;
                    }
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                }
            }
        }
        return circleblocks;
    }
    
    public static boolean isPositionPlaceable(final BlockPos position, final boolean entityCheck, final boolean sideCheck, final boolean ignoreCrystals) {
        if (!BlockUtils.mc.field_71441_e.func_180495_p(position).func_177230_c().func_176200_f((IBlockAccess)BlockUtils.mc.field_71441_e, position)) {
            return false;
        }
        if (entityCheck) {
            for (final Entity entity : BlockUtils.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(position))) {
                if (!(entity instanceof EntityItem)) {
                    if (entity instanceof EntityXPOrb) {
                        continue;
                    }
                    if (entity instanceof EntityEnderCrystal && ignoreCrystals) {
                        continue;
                    }
                    return false;
                }
            }
        }
        return !sideCheck || getPlaceableSide(position) != null;
    }
    
    public static boolean isPositionPlaceable(final BlockPos pos, final boolean entityCheck, final double distance) {
        final Block block = BlockUtils.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid) && !(block instanceof BlockTallGrass) && !(block instanceof BlockFire) && !(block instanceof BlockDeadBush) && !(block instanceof BlockSnow)) {
            return false;
        }
        if (entityCheck) {
            for (final Entity entity : BlockUtils.mc.field_71441_e.func_72872_a((Class)Entity.class, new AxisAlignedBB(pos))) {
                if (BlockUtils.mc.field_71439_g.func_70032_d(entity) > distance) {
                    continue;
                }
                if (entity instanceof EntityItem) {
                    continue;
                }
                if (entity instanceof EntityXPOrb) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }
    
    public static EnumFacing getPlaceableSide(final BlockPos pos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.func_177972_a(side);
            if (BlockUtils.mc.field_71441_e.func_180495_p(neighbour).func_177230_c().func_176209_a(BlockUtils.mc.field_71441_e.func_180495_p(neighbour), false)) {
                final IBlockState blockState = BlockUtils.mc.field_71441_e.func_180495_p(neighbour);
                if (!blockState.func_185904_a().func_76222_j()) {
                    return side;
                }
            }
        }
        return null;
    }
    
    public static BlockResistance getBlockResistance(final BlockPos block) {
        if (BlockUtils.mc.field_71441_e.func_175623_d(block)) {
            return BlockResistance.Blank;
        }
        if (BlockUtils.mc.field_71441_e.func_180495_p(block).func_177230_c().func_176195_g(BlockUtils.mc.field_71441_e.func_180495_p(block), (World)BlockUtils.mc.field_71441_e, block) != -1.0f && !BlockUtils.mc.field_71441_e.func_180495_p(block).func_177230_c().equals(Blocks.field_150343_Z) && !BlockUtils.mc.field_71441_e.func_180495_p(block).func_177230_c().equals(Blocks.field_150467_bQ) && !BlockUtils.mc.field_71441_e.func_180495_p(block).func_177230_c().equals(Blocks.field_150381_bn) && !BlockUtils.mc.field_71441_e.func_180495_p(block).func_177230_c().equals(Blocks.field_150477_bB)) {
            return BlockResistance.Breakable;
        }
        if (BlockUtils.mc.field_71441_e.func_180495_p(block).func_177230_c().equals(Blocks.field_150343_Z) || BlockUtils.mc.field_71441_e.func_180495_p(block).func_177230_c().equals(Blocks.field_150467_bQ) || BlockUtils.mc.field_71441_e.func_180495_p(block).func_177230_c().equals(Blocks.field_150381_bn) || BlockUtils.mc.field_71441_e.func_180495_p(block).func_177230_c().equals(Blocks.field_150477_bB)) {
            return BlockResistance.Resistant;
        }
        if (BlockUtils.mc.field_71441_e.func_180495_p(block).func_177230_c().equals(Blocks.field_150357_h)) {
            return BlockResistance.Unbreakable;
        }
        return null;
    }
    
    static {
        blackList = Arrays.asList(Blocks.field_150477_bB, (Block)Blocks.field_150486_ae, Blocks.field_150447_bR, Blocks.field_150462_ai, Blocks.field_150467_bQ, Blocks.field_150382_bo, (Block)Blocks.field_150438_bZ, Blocks.field_150409_cd, Blocks.field_150367_z);
        shulkerList = Arrays.asList(Blocks.field_190977_dl, Blocks.field_190978_dm, Blocks.field_190979_dn, Blocks.field_190980_do, Blocks.field_190981_dp, Blocks.field_190982_dq, Blocks.field_190983_dr, Blocks.field_190984_ds, Blocks.field_190985_dt, Blocks.field_190986_du, Blocks.field_190987_dv, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190990_dy, Blocks.field_190991_dz, Blocks.field_190975_dA);
        BlockUtils.SURROUND = new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1) };
    }
    
    public enum BlockResistance
    {
        Blank, 
        Breakable, 
        Resistant, 
        Unbreakable;
    }
}
