package com.mawuote.api.utilities.math;

public class AnimationUtils
{
    private float value;
    private long lastTime;
    private float changePerMillisecond;
    private float start;
    private float end;
    boolean increasing;
    
    public AnimationUtils(final float duration, final float start, final float end) {
        this((long)(duration * 1000.0f), start, end);
    }
    
    public AnimationUtils(final long duration, final float start, final float end) {
        this.value = start;
        this.end = end;
        this.start = start;
        this.increasing = (end > start);
        final float difference = Math.abs(start - end);
        this.changePerMillisecond = difference / duration;
        this.lastTime = System.currentTimeMillis();
    }
    
    public static AnimationUtils fromChangePerSecond(final float changePerSecond, final float start, final float end) {
        return new AnimationUtils(Math.abs(start - end) / changePerSecond, start, end);
    }
    
    public void reset() {
        this.value = this.start;
        this.lastTime = System.currentTimeMillis();
    }
    
    public float getValue() {
        if (this.value == this.end) {
            return this.value;
        }
        if (this.increasing) {
            if (this.value >= this.end) {
                return this.value = this.end;
            }
            this.value += this.changePerMillisecond * (System.currentTimeMillis() - this.lastTime);
            if (this.value > this.end) {
                this.value = this.end;
            }
            this.lastTime = System.currentTimeMillis();
            return this.value;
        }
        else {
            if (this.value <= this.end) {
                return this.value = this.end;
            }
            this.value -= this.changePerMillisecond * (System.currentTimeMillis() - this.lastTime);
            if (this.value < this.end) {
                this.value = this.end;
            }
            this.lastTime = System.currentTimeMillis();
            return this.value;
        }
    }
    
    public boolean isDone() {
        return this.value == this.end;
    }
}
