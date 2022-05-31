package com.mawuote.client.modules.client.notifications;

import com.mawuote.api.utilities.math.*;
import com.mawuote.*;
import com.mawuote.api.manager.module.*;
import com.mawuote.api.utilities.render.*;
import java.awt.*;
import com.mojang.realmsclient.gui.*;

public class Notification
{
    private final String text;
    public final long disableTime;
    private final float width;
    public final TimerUtils timer;
    public AnimationUtils animationUtils;
    public AnimationUtils animationUtils2;
    public AnimationUtils reverse;
    public AnimationUtils reverse2;
    boolean didThing;
    boolean isReversing;
    boolean didFirstReverse;
    
    public Notification(final String text, final long disableTime, final long inOutTime) {
        this.timer = new TimerUtils();
        this.didThing = false;
        this.isReversing = false;
        this.didFirstReverse = false;
        this.text = text;
        this.disableTime = disableTime;
        this.width = Kaotik.FONT_MANAGER.getStringWidth(text);
        this.animationUtils = new AnimationUtils(inOutTime, 0.0f, this.width + 2.0f);
        this.animationUtils2 = new AnimationUtils(inOutTime, 0.0f, this.width + 4.0f);
        this.reverse = new AnimationUtils(inOutTime, 0.0f, this.width + 2.0f);
        this.reverse2 = new AnimationUtils(inOutTime, 0.0f, this.width + 4.0f);
        this.timer.reset();
        this.animationUtils.reset();
        this.animationUtils2.reset();
        this.reverse.reset();
        this.reverse2.reset();
    }
    
    public void onDraw(final int y) {
        if (this.timer.hasReached(this.disableTime)) {
            Kaotik.NOTIFICATION_PROCESSOR.getNotifications().remove(this);
        }
        RenderUtils.drawRecta(-(this.width + 4.0f) + this.animationUtils2.getValue() - ((this.isReversing && this.didFirstReverse) ? this.reverse2.getValue() : 0.0f), (float)y, this.width + 4.0f, 20.0f, Module.globalColor(255).getRGB());
        if (this.animationUtils2.isDone()) {
            RenderUtils.drawRecta(-(this.width + 2.0f) + this.animationUtils.getValue() - (this.isReversing ? this.reverse.getValue() : 0.0f), (float)y, this.width + 2.0f, 20.0f, new Color(28, 28, 28).getRGB());
            Kaotik.FONT_MANAGER.drawString(ChatFormatting.stripFormatting(this.text), -(this.width + 2.0f) + this.animationUtils.getValue() - (this.isReversing ? this.reverse.getValue() : 0.0f) + 2.0f, y + 10 - Kaotik.FONT_MANAGER.getHeight() / 2.0f, Color.WHITE);
        }
    }
}
