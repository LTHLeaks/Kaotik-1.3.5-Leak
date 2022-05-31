package com.mawuote.client.modules.combat;

import com.mawuote.api.manager.value.impl.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import com.mawuote.api.manager.module.*;
import net.minecraft.init.*;
import com.mawuote.api.utilities.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.mawuote.api.utilities.world.*;
import net.minecraft.entity.*;
import com.mawuote.api.utilities.math.*;
import com.mawuote.api.utilities.crystal.*;
import java.util.*;
import net.minecraft.block.*;

public class FeetPlace extends Module
{
    public static ValueNumber enemyRange;
    public static ValueNumber palceReach;
    public static ValueNumber blockDistance;
    EntityPlayer target;
    boolean placed;
    int oldSlot;
    BlockPos currentPos;
    
    public FeetPlace() {
        super("FeetPlace", "FeetPlace", "Automatically places obsidian at the enemy's feet.", ModuleCategory.COMBAT);
        this.placed = false;
        this.oldSlot = -1;
        this.currentPos = null;
    }
    
    @Override
    public void onMotionUpdate() {
        if (FeetPlace.mc.field_71439_g == null || FeetPlace.mc.field_71441_e == null) {
            return;
        }
        this.oldSlot = FeetPlace.mc.field_71439_g.field_71071_by.field_70461_c;
        this.target = (EntityPlayer)this.getClosest();
        final int obiSlot = InventoryUtils.getHotbarBlockSlot(Blocks.field_150343_Z);
        if (this.target != null) {
            final BlockPos playerPos = new BlockPos(Math.floor(this.target.field_70165_t), this.target.field_70163_u, Math.floor(this.target.field_70161_v));
            if (!this.placed) {
                if (AutoCrystalHack.renderPosition != null) {
                    return;
                }
                final BlockPos pos = this.getPos(this.target);
                if (pos != null) {
                    if (obiSlot != -1) {
                        FeetPlace.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(obiSlot));
                    }
                    BlockUtils.placeBlock(pos, true, false);
                    FeetPlace.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.oldSlot));
                    this.placed = true;
                    this.currentPos = pos;
                }
            }
            if (this.currentPos != null && this.placed && (FeetPlace.mc.field_71439_g.func_70032_d((Entity)this.target) > FeetPlace.enemyRange.getValue().doubleValue() || FeetPlace.mc.field_71439_g.func_174818_b(this.currentPos) > MathUtils.square(FeetPlace.blockDistance.getValue().doubleValue()) || playerPos.field_177960_b <= this.currentPos.field_177960_b || BlockUtils.isIntercepted(this.currentPos.func_177984_a()) || FeetPlace.mc.field_71441_e.func_180495_p(this.currentPos.func_177984_a()).func_177230_c() != Blocks.field_150350_a)) {
                this.placed = false;
            }
        }
    }
    
    public BlockPos getPos(final EntityPlayer target) {
        BlockPos placePos = null;
        final BlockPos playerPos = new BlockPos(Math.floor(target.field_70165_t), target.field_70163_u, Math.floor(target.field_70161_v));
        double dist = MathUtils.square(FeetPlace.palceReach.getValue().doubleValue());
        for (final BlockPos pos : CrystalUtils.getSphere(FeetPlace.palceReach.getValue().floatValue(), true, false)) {
            if (pos.func_177956_o() >= playerPos.func_177956_o()) {
                continue;
            }
            if (pos == playerPos) {
                continue;
            }
            if (!this.canPlace(pos, true, true)) {
                continue;
            }
            if (FeetPlace.mc.field_71441_e.func_180495_p(pos.func_177984_a()).func_177230_c() != Blocks.field_150350_a) {
                continue;
            }
            if (BlockUtils.isIntercepted(pos.func_177984_a())) {
                continue;
            }
            if (BlockUtils.isIntercepted(pos.func_177984_a())) {
                continue;
            }
            final double pDist = target.func_174818_b(pos);
            if (pDist >= dist) {
                continue;
            }
            dist = pDist;
            placePos = pos;
        }
        return placePos;
    }
    
    public Entity getClosest() {
        Entity returnEntity = null;
        double dist = FeetPlace.enemyRange.getValue().doubleValue();
        for (final Entity entity : FeetPlace.mc.field_71441_e.field_72996_f) {
            if (entity instanceof EntityPlayer && entity != null) {
                if (FeetPlace.mc.field_71439_g.func_70032_d(entity) > dist) {
                    continue;
                }
                if (entity == FeetPlace.mc.field_71439_g) {
                    continue;
                }
                final double pDist = FeetPlace.mc.field_71439_g.func_70032_d(entity);
                if (pDist >= dist) {
                    continue;
                }
                dist = pDist;
                returnEntity = entity;
            }
        }
        return returnEntity;
    }
    
    public boolean canPlace(final BlockPos pos, final boolean obsidian, final boolean bedrock) {
        final Block block = BlockUtils.mc.field_71441_e.func_180495_p(pos).func_177230_c();
        return block instanceof BlockAir || block instanceof BlockLiquid || block instanceof BlockTallGrass || block instanceof BlockFire || block instanceof BlockDeadBush || block instanceof BlockSnow || (block instanceof BlockObsidian && obsidian) || (block == Blocks.field_150357_h && bedrock);
    }
    
    @Override
    public void onDisable() {
        this.placed = false;
    }
    
    static {
        FeetPlace.enemyRange = new ValueNumber("EnemyRange", "EnemyRange", "", 5, 2, 8);
        FeetPlace.palceReach = new ValueNumber("PlaceReach", "PlaceReach", "", 5.0, 2.0, 6.0);
        FeetPlace.blockDistance = new ValueNumber("BlockDistance", "BlockDistance", "BlockDistance", 4, 2, 6);
    }
}
