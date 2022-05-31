package com.mawuote.api.manager.value.impl;

import com.mawuote.api.manager.value.*;
import net.minecraft.entity.*;

public class ValuePreview extends Value
{
    private Entity entity;
    
    public ValuePreview(final String name, final String tag, final String description, final Entity entity) {
        super(name, tag, description);
        this.entity = entity;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
}
