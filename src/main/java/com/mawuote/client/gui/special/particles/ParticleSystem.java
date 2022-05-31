package com.mawuote.client.gui.special.particles;

import java.util.*;
import com.mawuote.client.modules.client.*;
import java.awt.*;
import com.mawuote.api.utilities.render.*;
import org.lwjgl.opengl.*;

public class ParticleSystem
{
    public static float SPEED;
    private List<Particle> particleList;
    public int dist;
    
    public ParticleSystem(final int initAmount, final int dist) {
        this.particleList = new ArrayList<Particle>();
        this.addParticles(initAmount);
        this.dist = dist;
    }
    
    public void addParticles(final int amount) {
        for (int i = 0; i < amount; ++i) {
            this.particleList.add(Particle.generateParticle());
        }
    }
    
    public void changeParticles(final int amount) {
        this.particleList.clear();
        for (int i = 0; i < amount; ++i) {
            this.particleList.add(Particle.generateParticle());
        }
    }
    
    public void tick(final int delta) {
        for (final Particle particle : this.particleList) {
            particle.tick(delta, ParticleSystem.SPEED);
        }
    }
    
    public void render() {
        for (final Particle particle : this.particleList) {
            final Color color = new Color(ModuleParticles.daColor.getValue().getRed(), ModuleParticles.daColor.getValue().getGreen(), ModuleParticles.daColor.getValue().getBlue(), 255);
            for (final Particle particle2 : this.particleList) {
                final float distance = particle.getDistanceTo(particle2);
                if (particle.getDistanceTo(particle2) < this.dist) {
                    final float alpha = Math.min(1.0f, Math.min(1.0f, 1.0f - distance / this.dist));
                    RenderUtils.prepareGL();
                    GL11.glEnable(2848);
                    GL11.glDisable(3553);
                    GL11.glLineWidth(ModuleParticles.lineWidth.getValue().floatValue());
                    GL11.glBegin(1);
                    GL11.glColor4f(ModuleParticles.daColor.getValue().getRed() / 255.0f, ModuleParticles.daColor.getValue().getGreen() / 255.0f, ModuleParticles.daColor.getValue().getBlue() / 255.0f, alpha);
                    GL11.glVertex2d((double)particle.getX(), (double)particle.getY());
                    GL11.glVertex2d((double)particle2.getX(), (double)particle2.getY());
                    GL11.glEnd();
                    GL11.glEnable(3553);
                    RenderUtils.releaseGL();
                }
            }
            RenderUtils.drawCircle(particle.getX(), particle.getY(), particle.getSize(), color.getRGB());
        }
    }
    
    private void drawLine(final double x, final double y, final double x1, final double y1, final float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
    }
    
    static {
        ParticleSystem.SPEED = 0.1f;
    }
}
