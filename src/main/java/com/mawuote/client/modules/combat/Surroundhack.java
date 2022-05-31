package com.mawuote.client.modules.combat;

import net.minecraft.util.math.*;
import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.module.*;
import com.mawuote.api.utilities.entity.*;
import com.mawuote.api.utilities.math.*;
import com.mawuote.api.manager.misc.*;
import com.mawuote.api.utilities.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class Surroundhack extends Module
{
    private int placements;
    private BlockPos startPosition;
    private int tries;
    ValueEnum mode;
    ValueEnum item;
    ValueEnum switchMode;
    ValueNumber blocks;
    ValueEnum supportBlocks;
    ValueNumber retries;
    ValueBoolean dynamic;
    ValueBoolean center;
    ValueBoolean rotate;
    ValueBoolean floor;
    
    public Surroundhack() {
        super("SurroundHack", "Surround", "Places blocks around your feet to protect you from crystals.", ModuleCategory.COMBAT);
        this.mode = new ValueEnum("Mode", "Mode", "The mode for the Surround.", Modes.Normal);
        this.item = new ValueEnum("Item", "Item", "The item for block placing.", InventoryUtils.Items.Obsidian);
        this.switchMode = new ValueEnum("Switch", "Switch", "The mode for switching.", InventoryUtils.SwitchModes.Normal);
        this.blocks = new ValueNumber("Blocks", "Blocks", "The amount of blocks that can be placed per tick.", 8, 1, 40);
        this.supportBlocks = new ValueEnum("SupportBlocks", "SupportBlocks", "The support blocks for placing.", Supports.Dynamic);
        this.retries = new ValueNumber("Retries", "Retries", "The amount of retries that can happen before stopping the crystal ignore.", 5, 0, 20);
        this.dynamic = new ValueBoolean("Dynamic", "Dynamic", "Makes the surround place dynamically.", true);
        this.center = new ValueBoolean("Center", "Center", "Positions the player to the center.", false);
        this.rotate = new ValueBoolean("Rotate", "Rotate", "Rotates to the block when placing.", false);
        this.floor = new ValueBoolean("Floor", "Floor", "Places blocks at the floor.", false);
    }
    
    @Override
    public void onMotionUpdate() {
        if (this.startPosition.func_177956_o() != MathUtils.roundToPlaces(Surroundhack.mc.field_71439_g.field_70163_u, 0) && this.mode.getValue().equals(Modes.Normal)) {
            this.disable();
            return;
        }
        final int slot = InventoryUtils.getCombatBlock(this.item.getValue().toString());
        final int lastSlot = Surroundhack.mc.field_71439_g.field_71071_by.field_70461_c;
        if (slot == -1) {
            ChatManager.sendClientMessage("No blocks could be found.", 256);
            this.disable();
            return;
        }
        if (!this.getUnsafeBlocks().isEmpty()) {
            InventoryUtils.switchSlot(slot, this.switchMode.getValue().equals(InventoryUtils.SwitchModes.Silent));
            for (final BlockPos position : this.getUnsafeBlocks()) {
                if (!this.supportBlocks.getValue().equals(Supports.None) && (BlockUtils.getPlaceableSide(position) == null || this.supportBlocks.getValue().equals(Supports.Static)) && BlockUtils.isPositionPlaceable(position.func_177977_b(), true, true)) {
                    this.placeBlock(position.func_177977_b());
                }
                if (BlockUtils.isPositionPlaceable(position, true, true, this.tries <= this.retries.getValue().intValue())) {
                    this.placeBlock(position);
                    ++this.tries;
                }
            }
            if (!this.switchMode.getValue().equals(InventoryUtils.SwitchModes.Strict)) {
                InventoryUtils.switchSlot(lastSlot, this.switchMode.getValue().equals(InventoryUtils.SwitchModes.Silent));
            }
        }
        this.placements = 0;
        if (this.getUnsafeBlocks().isEmpty()) {
            this.tries = 0;
            if (this.mode.getValue().equals(Modes.Toggle)) {
                this.disable();
            }
        }
    }
    
    private List<BlockPos> getOffsets() {
        final List<BlockPos> offsets = new ArrayList<BlockPos>();
        if (this.dynamic.getValue()) {
            final double decimalX = Math.abs(Surroundhack.mc.field_71439_g.field_70165_t) - Math.floor(Math.abs(Surroundhack.mc.field_71439_g.field_70165_t));
            final double decimalZ = Math.abs(Surroundhack.mc.field_71439_g.field_70161_v) - Math.floor(Math.abs(Surroundhack.mc.field_71439_g.field_70161_v));
            final int lengthX = this.calculateLength(decimalX, false);
            final int negativeLengthX = this.calculateLength(decimalX, true);
            final int lengthZ = this.calculateLength(decimalZ, false);
            final int negativeLengthZ = this.calculateLength(decimalZ, true);
            final List<BlockPos> tempOffsets = new ArrayList<BlockPos>();
            offsets.addAll(this.getOverlapPositions());
            for (int x = 1; x < lengthX + 1; ++x) {
                tempOffsets.add(this.addToPosition(this.getPlayerPosition(), x, 1 + lengthZ));
                tempOffsets.add(this.addToPosition(this.getPlayerPosition(), x, -(1 + negativeLengthZ)));
            }
            for (int x = 0; x <= negativeLengthX; ++x) {
                tempOffsets.add(this.addToPosition(this.getPlayerPosition(), -x, 1 + lengthZ));
                tempOffsets.add(this.addToPosition(this.getPlayerPosition(), -x, -(1 + negativeLengthZ)));
            }
            for (int z = 1; z < lengthZ + 1; ++z) {
                tempOffsets.add(this.addToPosition(this.getPlayerPosition(), 1 + lengthX, z));
                tempOffsets.add(this.addToPosition(this.getPlayerPosition(), -(1 + negativeLengthX), z));
            }
            for (int z = 0; z <= negativeLengthZ; ++z) {
                tempOffsets.add(this.addToPosition(this.getPlayerPosition(), 1 + lengthX, -z));
                tempOffsets.add(this.addToPosition(this.getPlayerPosition(), -(1 + negativeLengthX), -z));
            }
            offsets.addAll(tempOffsets);
        }
        else {
            for (final EnumFacing side : EnumFacing.field_176754_o) {
                offsets.add(this.getPlayerPosition().func_177982_a(side.func_82601_c(), 0, side.func_82599_e()));
            }
        }
        return offsets;
    }
    
    private BlockPos getPlayerPosition() {
        return new BlockPos(Surroundhack.mc.field_71439_g.field_70165_t, (Surroundhack.mc.field_71439_g.field_70163_u - Math.floor(Surroundhack.mc.field_71439_g.field_70163_u) > 0.8) ? (Math.floor(Surroundhack.mc.field_71439_g.field_70163_u) + 1.0) : Math.floor(Surroundhack.mc.field_71439_g.field_70163_u), Surroundhack.mc.field_71439_g.field_70161_v);
    }
    
    private List<BlockPos> getOverlapPositions() {
        final List<BlockPos> positions = new ArrayList<BlockPos>();
        final int offsetX = this.calculateOffset(Surroundhack.mc.field_71439_g.field_70165_t - Math.floor(Surroundhack.mc.field_71439_g.field_70165_t));
        final int offsetZ = this.calculateOffset(Surroundhack.mc.field_71439_g.field_70161_v - Math.floor(Surroundhack.mc.field_71439_g.field_70161_v));
        positions.add(this.getPlayerPosition());
        for (int x = 0; x <= Math.abs(offsetX); ++x) {
            for (int z = 0; z <= Math.abs(offsetZ); ++z) {
                final int properX = x * offsetX;
                final int properZ = z * offsetZ;
                positions.add(this.getPlayerPosition().func_177982_a(properX, -1, properZ));
            }
        }
        return positions;
    }
    
    private BlockPos addToPosition(final BlockPos position, double x, double z) {
        if (position.func_177958_n() < 0) {
            x = -x;
        }
        if (position.func_177952_p() < 0) {
            z = -z;
        }
        return position.func_177963_a(x, 0.0, z);
    }
    
    private int calculateOffset(final double dec) {
        return (dec >= 0.7) ? 1 : ((dec <= 0.3) ? -1 : 0);
    }
    
    private int calculateLength(final double decimal, final boolean negative) {
        if (negative) {
            return (decimal <= 0.3) ? 1 : 0;
        }
        return (decimal >= 0.7) ? 1 : 0;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (Surroundhack.mc.field_71439_g == null || Surroundhack.mc.field_71441_e == null) {
            this.disable();
            return;
        }
        this.startPosition = new BlockPos(MathUtils.roundVector(Surroundhack.mc.field_71439_g.func_174791_d(), 0));
    }
    
    public void placeBlock(final BlockPos position) {
        if (this.placements < this.blocks.getValue().intValue()) {
            BlockUtils.placeBlock(position, EnumHand.MAIN_HAND, true);
            ++this.placements;
        }
    }
    
    public List<BlockPos> getUnsafeBlocks() {
        final List<BlockPos> positions = new ArrayList<BlockPos>();
        for (final BlockPos position : this.getOffsets()) {
            if (!this.isSafe(position)) {
                positions.add(position);
            }
        }
        return positions;
    }
    
    public boolean isSafe(final BlockPos position) {
        return !Surroundhack.mc.field_71441_e.func_180495_p(position).func_177230_c().func_176200_f((IBlockAccess)Surroundhack.mc.field_71441_e, position);
    }
    
    @Override
    public String getHudInfo() {
        return " " + this.tries + "/" + this.retries.getValue().intValue();
    }
    
    public enum Modes
    {
        Normal, 
        Persistent, 
        Toggle, 
        Shift;
    }
    
    public enum Supports
    {
        None, 
        Dynamic, 
        Static;
    }
}
