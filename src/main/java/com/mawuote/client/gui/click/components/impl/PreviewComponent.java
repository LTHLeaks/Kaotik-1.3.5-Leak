package com.mawuote.client.gui.click.components.impl;

import com.mawuote.client.gui.click.components.*;
import com.mawuote.api.manager.value.impl.*;
import net.minecraft.entity.item.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraft.world.*;
import org.lwjgl.opengl.*;
import com.mawuote.api.utilities.render.*;
import net.minecraft.entity.*;
import com.mawuote.*;

public class PreviewComponent extends Component
{
    ValuePreview setting;
    public static EntityEnderCrystal entityEnderCrystal;
    public boolean open;
    
    public PreviewComponent(final ValuePreview setting, final ModuleComponent parent, final int offset) {
        super(parent.getParent().getX(), parent.getParent().getY() + offset, parent.getParent());
        this.open = false;
        this.setting = setting;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY) {
        super.drawScreen(mouseX, mouseY);
        final Entity entity = this.setting.getEntity();
        Gui.func_73734_a(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + (this.open ? 100 : 14), new Color(40, 40, 40).getRGB());
        Gui.func_73734_a(this.getX() - 1, this.getY(), this.getX(), this.getY() + (this.open ? 100 : 14), new Color(30, 30, 30).getRGB());
        Gui.func_73734_a(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth() + 1, this.getY() + (this.open ? 100 : 14), new Color(30, 30, 30).getRGB());
        if (this.open && entity instanceof EntityEnderCrystal) {
            final EntityEnderCrystal ent = new EntityEnderCrystal((World)PreviewComponent.mc.field_71441_e, 0.0, 0.0, 0.0);
            (PreviewComponent.entityEnderCrystal = ent).func_184517_a(false);
            ent.field_70177_z = 0.0f;
            ent.field_70125_A = 0.0f;
            ent.field_70261_a = 0;
            ent.field_70126_B = 0.0f;
            ent.field_70127_C = 0.0f;
            if (ent != null) {
                GL11.glScalef(1.0f, 1.0f, 1.0f);
                RenderUtils.drawEntityOnScreen((Entity)ent, this.getX() + this.getWidth() / 2, this.getY() + 90, 40, 0.0f, 0.0f);
            }
        }
        Kaotik.FONT_MANAGER.drawString(this.setting.getName(), (float)(this.getX() + 3), (float)(this.getY() + 3), Color.WHITE);
        Kaotik.FONT_MANAGER.drawString(this.open ? "-" : "+", this.getX() + this.getWidth() - 3 - Kaotik.FONT_MANAGER.getStringWidth("+"), (float)(this.getY() + 3), Color.WHITE);
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 1 && mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.getHeight()) {
            this.open = !this.open;
        }
    }
}
