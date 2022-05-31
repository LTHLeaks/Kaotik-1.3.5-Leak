package com.mawuote.api.manager.event.impl.world;

import com.mawuote.api.manager.event.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class EventClickBlock extends Event
{
    public BlockPos pos;
    public EnumFacing facing;
    
    public EventClickBlock(final BlockPos pos, final EnumFacing facing) {
        this.pos = pos;
        this.facing = facing;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
}
