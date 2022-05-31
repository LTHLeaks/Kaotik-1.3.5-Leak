package com.mawuote.api.utilities.render;

import net.minecraft.util.*;

public class GifLocation
{
    private final String folder;
    private final int frames;
    private final int fpt;
    private int currentTick;
    private int currentFrame;
    private ResourceLocation[] textures;
    
    public GifLocation(final String folder, final int frames, final int fpt) {
        this.currentTick = 0;
        this.currentFrame = 0;
        this.folder = folder;
        this.frames = frames;
        this.fpt = fpt;
        this.textures = new ResourceLocation[frames];
        for (int i = 0; i < frames; ++i) {
            this.textures[i] = new ResourceLocation(folder + "/" + i + ".png");
        }
    }
    
    public ResourceLocation getTexture() {
        return this.textures[this.currentFrame];
    }
    
    public void update() {
        if (this.currentTick > this.fpt) {
            this.currentTick = 0;
            ++this.currentFrame;
            if (this.currentFrame > this.textures.length - 1) {
                this.currentFrame = 0;
            }
        }
        ++this.currentTick;
    }
}
