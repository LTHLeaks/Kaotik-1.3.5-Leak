package com.mawuote.client.modules.combat;

import net.minecraft.entity.player.*;
import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.module.*;
import com.mawuote.api.utilities.crystal.*;
import com.mawuote.api.utilities.entity.*;
import com.mawuote.api.manager.misc.*;
import net.minecraft.util.math.*;
import com.mawuote.api.utilities.world.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;

public class AutoTrap extends Module
{
    public static EntityPlayer target;
    private int placements;
    private final Vec3d[] offsets;
    public static ValueEnum item;
    public static ValueEnum switchMode;
    public static ValueNumber blocks;
    public static ValueNumber range;
    public static ValueNumber targetRange;
    public static ValueBoolean selfDisable;
    
    public AutoTrap() {
        super("AutoTrap", "Auto Trap", "traps run ez", ModuleCategory.COMBAT);
        this.offsets = new Vec3d[] { new Vec3d(-1.0, 0.0, 0.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(0.0, 2.0, 0.0) };
    }
    
    @Override
    public void onMotionUpdate() {
        AutoTrap.target = CrystalUtils.getTarget(AutoTrap.targetRange.getValue().floatValue());
        if (AutoTrap.target == null) {
            return;
        }
        final int slot = InventoryUtils.getCombatBlock(AutoTrap.item.getValue().toString());
        final int lastSlot = AutoTrap.mc.field_71439_g.field_71071_by.field_70461_c;
        if (slot == -1) {
            ChatManager.sendClientMessage("No blocks could be found.", 256);
            this.disable();
            return;
        }
        this.placements = 0;
        if (!this.getPlaceableBlocks(AutoTrap.target).isEmpty()) {
            InventoryUtils.switchSlot(slot, AutoTrap.switchMode.getValue().equals(InventoryUtils.SwitchModes.Silent));
            for (final BlockPos position : this.getPlaceableBlocks(AutoTrap.target)) {
                this.placeBlock(position);
            }
            if (!AutoTrap.switchMode.getValue().equals(InventoryUtils.SwitchModes.Strict)) {
                InventoryUtils.switchSlot(lastSlot, AutoTrap.switchMode.getValue().equals(InventoryUtils.SwitchModes.Silent));
            }
        }
        if (AutoTrap.selfDisable.getValue() && this.getPlaceableBlocks(AutoTrap.target).isEmpty()) {
            this.disable();
        }
    }
    
    public void placeBlock(final BlockPos position) {
        if (BlockUtils.isPositionPlaceable(position, true, true, false) && this.placements < AutoTrap.blocks.getValue().intValue()) {
            BlockUtils.placeBlock(position, EnumHand.MAIN_HAND, true);
            ++this.placements;
        }
    }
    
    public List<BlockPos> getPlaceableBlocks(final EntityPlayer player) {
        final List<BlockPos> positions = new ArrayList<BlockPos>();
        for (final Vec3d vec3d : this.offsets) {
            final BlockPos position = new BlockPos(vec3d.func_178787_e(player.func_174791_d()));
            if (AutoTrap.mc.field_71441_e.func_180495_p(position).func_177230_c().func_176200_f((IBlockAccess)AutoTrap.mc.field_71441_e, position)) {
                positions.add(position);
            }
        }
        return positions;
    }
    
    static {
        AutoTrap.target = null;
        AutoTrap.item = new ValueEnum("Item", "Item", "The item for block placing.", InventoryUtils.Items.Obsidian);
        AutoTrap.switchMode = new ValueEnum("Switch", "Switch", "The mode for switching.", InventoryUtils.SwitchModes.Normal);
        AutoTrap.blocks = new ValueNumber("Blocks", "Blocks", "The amount of blocks that can be placed per tick.", 8, 1, 40);
        AutoTrap.range = new ValueNumber("Range", "Range", "The maximum range that the block can be away.", 4.0f, 0.0f, 8.0f);
        AutoTrap.targetRange = new ValueNumber("TargetRange", "TargetRange", "The range for the targeting.", 6.0f, 0.0f, 8.0f);
        AutoTrap.selfDisable = new ValueBoolean("SelfDisable", "SelfDisable", "Automatically disables when there are no more holes.", true);
    }
}
