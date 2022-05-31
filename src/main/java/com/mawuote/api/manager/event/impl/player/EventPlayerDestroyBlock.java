package com.mawuote.api.manager.event.impl.player;

import com.mawuote.api.manager.event.*;
import net.minecraft.util.math.*;

public class EventPlayerDestroyBlock extends Event
{
    BlockPos pos;
    
    public EventPlayerDestroyBlock(final BlockPos blockPos) {
        this.pos = blockPos;
    }
    
    public BlockPos getBlockPos() {
        return this.pos;
    }
    
    public void setPos(final BlockPos pos) {
        this.pos = pos;
    }
}
