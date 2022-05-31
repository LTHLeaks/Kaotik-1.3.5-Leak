package com.mawuote.api.manager.event.impl.render;

import com.mawuote.api.manager.event.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.entity.layers.*;

public class EventRenderEntityLayer extends Event
{
    private EntityLivingBase entity;
    private LayerRenderer<?> layer;
    
    public EventRenderEntityLayer(final EntityLivingBase entity, final LayerRenderer<?> layer) {
        this.entity = entity;
        this.layer = layer;
    }
    
    public final EntityLivingBase getEntity() {
        return this.entity;
    }
    
    public final void setEntity(final EntityLivingBase entityLivingBase) {
        this.entity = entityLivingBase;
    }
    
    public final LayerRenderer<?> getLayer() {
        return this.layer;
    }
    
    public final void setLayer(final LayerRenderer<?> layerRenderer) {
        this.layer = layerRenderer;
    }
    
    public final EntityLivingBase component1() {
        return this.entity;
    }
    
    public final LayerRenderer<?> component2() {
        return this.layer;
    }
    
    public final EventRenderEntityLayer copy(final EntityLivingBase entity, final LayerRenderer<?> layer) {
        return new EventRenderEntityLayer(entity, layer);
    }
    
    public static EventRenderEntityLayer copy$default(final EventRenderEntityLayer renderEntityLayerEvent, EntityLivingBase entityLivingBase, LayerRenderer layerRenderer, final int n, final Object object) {
        if ((n & 0x1) != 0x0) {
            entityLivingBase = renderEntityLayerEvent.entity;
        }
        if ((n & 0x2) != 0x0) {
            layerRenderer = renderEntityLayerEvent.layer;
        }
        return renderEntityLayerEvent.copy(entityLivingBase, (LayerRenderer<?>)layerRenderer);
    }
    
    public String toString() {
        return "RenderEntityLayerEvent(entity=" + this.entity + ", layer=" + this.layer + ')';
    }
    
    public int hashCode() {
        int result2 = this.entity.hashCode();
        result2 = result2 * 31 + this.layer.hashCode();
        return result2;
    }
    
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EventRenderEntityLayer)) {
            return false;
        }
        final EventRenderEntityLayer renderEntityLayerEvent = (EventRenderEntityLayer)other;
        return this.entity == renderEntityLayerEvent.entity && this.layer == renderEntityLayerEvent.layer;
    }
}
