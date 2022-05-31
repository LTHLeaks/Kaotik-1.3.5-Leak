package com.mawuote.api.manager.sound;

import javax.annotation.*;
import net.minecraft.client.audio.*;
import net.minecraft.util.*;

static final class SoundUtil$1 implements ISound {
    private final int pitch = 1;
    private final int volume = 1;
    
    public ResourceLocation func_147650_b() {
        return SoundUtil.access$000();
    }
    
    @Nullable
    public SoundEventAccessor func_184366_a(final SoundHandler soundHandler) {
        return new SoundEventAccessor(SoundUtil.access$000(), "f");
    }
    
    public Sound func_184364_b() {
        return new Sound("mike", 1.0f, 1.0f, 1, Sound.Type.SOUND_EVENT, false);
    }
    
    public SoundCategory func_184365_d() {
        return SoundCategory.VOICE;
    }
    
    public boolean func_147657_c() {
        return true;
    }
    
    public int func_147652_d() {
        return 2;
    }
    
    public float func_147653_e() {
        return 1.0f;
    }
    
    public float func_147655_f() {
        return 1.0f;
    }
    
    public float func_147649_g() {
        return 1.0f;
    }
    
    public float func_147654_h() {
        return 0.0f;
    }
    
    public float func_147651_i() {
        return 0.0f;
    }
    
    public ISound.AttenuationType func_147656_j() {
        return ISound.AttenuationType.LINEAR;
    }
}