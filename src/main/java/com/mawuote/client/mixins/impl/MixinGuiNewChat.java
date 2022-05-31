package com.mawuote.client.mixins.impl;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.gui.*;
import com.mawuote.*;
import net.minecraft.client.*;

@Mixin({ GuiNewChat.class })
public abstract class MixinGuiNewChat
{
    @Redirect(method = { "drawChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"))
    public void drawBackground(final int left, final int top, final int right, final int bottom, final int colour) {
        Gui.func_73734_a(left, top, right, bottom, colour);
    }
    
    @Redirect(method = { "drawChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    private int drawStringWithShadow(final FontRenderer fontRenderer, final String text, final float x, final float y, final int color) {
        if (text.contains("§+")) {
            Kaotik.CHAT_MANAGER.drawRainbowString(text, x, y, true);
        }
        else {
            Minecraft.func_71410_x().field_71466_p.func_175063_a(text, x, y, color);
        }
        return 0;
    }
}
