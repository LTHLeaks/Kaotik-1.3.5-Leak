package com.mawuote.api.manager.value.impl;

import com.mawuote.api.manager.value.*;
import java.awt.*;
import net.minecraftforge.common.*;
import com.mawuote.api.manager.event.impl.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ValueColor extends Value
{
    private Color value;
    private Boolean rainbow;
    
    public ValueColor(final String name, final String tag, final String description, final Color value) {
        super(name, tag, description);
        this.value = value;
        this.rainbow = false;
    }
    
    public Color getValue() {
        this.doRainbow();
        return this.value;
    }
    
    public void setValue(final Color value) {
        MinecraftForge.EVENT_BUS.post((Event)new EventClient(this));
        this.value = value;
    }
    
    private void doRainbow() {
        if (this.rainbow) {
            final float[] hsb = Color.RGBtoHSB(this.value.getRed(), this.value.getGreen(), this.value.getBlue(), null);
            double rainbowState = Math.ceil(System.currentTimeMillis() / 20.0);
            rainbowState %= 360.0;
            final Color c = this.HSBAlpha(Color.getHSBColor((float)(rainbowState / 360.0), hsb[1], hsb[2]), this.value.getAlpha());
            this.setValue(new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()));
        }
    }
    
    public void setRainbow(final boolean rainbow) {
        this.rainbow = rainbow;
    }
    
    public Boolean getRainbow() {
        return this.rainbow;
    }
    
    public Color HSBAlpha(final Color color, final int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
}
