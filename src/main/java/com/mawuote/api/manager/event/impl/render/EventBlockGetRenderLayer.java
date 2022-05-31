package com.mawuote.api.manager.event.impl.render;

import com.mawuote.api.manager.event.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class EventBlockGetRenderLayer extends Event
{
    private BlockRenderLayer _layer;
    private Block _block;
    
    public EventBlockGetRenderLayer(final Block block) {
        this._block = block;
    }
    
    public Block getBlock() {
        return this._block;
    }
    
    public void setLayer(final BlockRenderLayer layer) {
        this._layer = layer;
    }
    
    public BlockRenderLayer getBlockRenderLayer() {
        return this._layer;
    }
}
