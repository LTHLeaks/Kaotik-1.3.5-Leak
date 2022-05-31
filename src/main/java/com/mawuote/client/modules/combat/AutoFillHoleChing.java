package com.mawuote.client.modules.combat;

import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.module.*;
import com.mawuote.api.utilities.crystal.*;
import com.mawuote.api.utilities.entity.*;
import com.mawuote.api.manager.misc.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import java.util.*;
import com.mawuote.api.utilities.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class AutoFillHoleChing extends Module
{
    private int placements;
    public static ValueEnum mode;
    public static ValueEnum item;
    public static ValueEnum switchMode;
    ValueNumber blocks;
    public static ValueNumber range;
    public static ValueNumber targetRange;
    public static ValueBoolean doubles;
    public static ValueBoolean targetDisable;
    public static ValueBoolean selfDisable;
    
    public AutoFillHoleChing() {
        super("AutoFillHoleChing", "Auto Fill Hole Ching", "Automatically fills holes with selected blocks.", ModuleCategory.COMBAT);
        this.placements = 0;
        this.blocks = new ValueNumber("Blocks", "Blocks", "The amount of blocks that can be placed per tick.", 8, 1, 40);
    }
    
    @Override
    public void onMotionUpdate() {
        super.onMotionUpdate();
        final EntityPlayer target = CrystalUtils.getTarget(AutoFillHoleChing.targetRange.getValue().floatValue());
        if (AutoFillHoleChing.mode.getValue().equals(Modes.Smart) && target == null) {
            if (AutoFillHoleChing.targetDisable.getValue()) {
                this.disable();
            }
            return;
        }
        this.placements = 0;
        final int slot = InventoryUtils.getCombatBlock(AutoFillHoleChing.item.getValue().toString());
        final int lastSlot = AutoFillHoleChing.mc.field_71439_g.field_71071_by.field_70461_c;
        if (slot == -1) {
            ChatManager.sendClientMessage("No blocks could be found.", 256);
            this.disable();
            return;
        }
        final ArrayList<BlockPos> holes = this.getHoles();
        if (!holes.isEmpty()) {
            InventoryUtils.switchSlot(slot, AutoFillHoleChing.switchMode.getValue().equals(InventoryUtils.SwitchModes.Silent));
            for (final BlockPos position : holes) {
                this.placeBlock(position);
            }
            if (!AutoFillHoleChing.switchMode.getValue().equals(InventoryUtils.SwitchModes.Strict)) {
                InventoryUtils.switchSlot(lastSlot, AutoFillHoleChing.switchMode.getValue().equals(InventoryUtils.SwitchModes.Silent));
            }
        }
        if (AutoFillHoleChing.selfDisable.getValue() && holes.isEmpty()) {
            this.disable();
        }
    }
    
    public ArrayList<BlockPos> getHoles() {
        final ArrayList<BlockPos> holes = new ArrayList<BlockPos>();
        for (final BlockPos position : CrystalUtils.getSphere(AutoFillHoleChing.range.getValue().floatValue(), true, false)) {
            if (HoleUtils.isHole(position)) {
                if (!BlockUtils.isPositionPlaceable(position, true, true, false)) {
                    continue;
                }
                holes.add(position);
            }
            else {
                if (!HoleUtils.isDoubleHole(position) || !AutoFillHoleChing.doubles.getValue()) {
                    continue;
                }
                if (AutoFillHoleChing.mc.field_71441_e.func_180495_p(position).func_177230_c() != Blocks.field_150350_a) {
                    continue;
                }
                if (AutoFillHoleChing.mc.field_71441_e.func_180495_p(position.func_177984_a()).func_177230_c() != Blocks.field_150350_a) {
                    continue;
                }
                if (AutoFillHoleChing.mc.field_71441_e.func_180495_p(position.func_177984_a().func_177984_a()).func_177230_c() != Blocks.field_150350_a) {
                    continue;
                }
                if (!BlockUtils.isPositionPlaceable(position, true, true, false)) {
                    continue;
                }
                holes.add(position);
            }
        }
        return holes;
    }
    
    public void placeBlock(final BlockPos position) {
        if (BlockUtils.isPositionPlaceable(position, true, true, false) && this.placements < this.blocks.getValue().intValue()) {
            BlockUtils.placeBlock(position, EnumHand.MAIN_HAND, true);
            ++this.placements;
        }
    }
    
    static {
        AutoFillHoleChing.mode = new ValueEnum("Mode", "Mode", "The mode for the HoleFill.", Modes.Normal);
        AutoFillHoleChing.item = new ValueEnum("Item", "Item", "The item for block placing.", InventoryUtils.Items.Obsidian);
        AutoFillHoleChing.switchMode = new ValueEnum("Switch", "Switch", "The mode for switching.", InventoryUtils.SwitchModes.Normal);
        AutoFillHoleChing.range = new ValueNumber("Range", "Range", "The maximum range that the block can be away.", 4.0f, 0.0f, 8.0f);
        AutoFillHoleChing.targetRange = new ValueNumber("TargetRange", "TargetRange", "The range for the targeting.", 6.0f, 0.0f, 8.0f);
        AutoFillHoleChing.doubles = new ValueBoolean("Doubles", "Doubles", "Fills in double holes too.", true);
        AutoFillHoleChing.targetDisable = new ValueBoolean("TargetDisable", "TargetDisable", "Automatically disables when there is no target.", true);
        AutoFillHoleChing.selfDisable = new ValueBoolean("SelfDisable", "SelfDisable", "Automatically disables when there are no more holes.", true);
    }
    
    public enum Modes
    {
        Normal, 
        Smart;
    }
}
