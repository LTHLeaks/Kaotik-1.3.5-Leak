package com.mawuote.client.mixins.impl;

import net.minecraft.client.*;
import org.lwjgl.*;
import javax.annotation.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.gui.*;
import com.mawuote.client.modules.client.*;
import com.mawuote.client.gui.special.*;
import com.mawuote.api.utilities.render.*;
import net.minecraft.client.entity.*;
import com.mawuote.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.multiplayer.*;
import org.lwjgl.opengl.*;

@Mixin(value = { Minecraft.class }, priority = 999)
public abstract class MixinMinecraft
{
    private long lastFrame;
    
    public MixinMinecraft() {
        this.lastFrame = this.getTime();
    }
    
    public long getTime() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }
    
    @Shadow
    public abstract void func_147108_a(@Nullable final GuiScreen p0);
    
    @Inject(method = { "runTick()V" }, at = { @At("RETURN") })
    private void runTick(final CallbackInfo callbackInfo) {
        if (Minecraft.func_71410_x().field_71462_r instanceof GuiMainMenu && ModuleGUI.customMenu.getValue()) {
            Minecraft.func_71410_x().func_147108_a((GuiScreen)new EuropaMainMenu());
        }
    }
    
    @Inject(method = { "runGameLoop" }, at = { @At("HEAD") })
    private void onRunGameLoopPre(final CallbackInfo ci) {
        final long currentTime = this.getTime();
        final int deltaTime = (int)(currentTime - this.lastFrame);
        this.lastFrame = currentTime;
        RenderUtils.deltaTime = deltaTime;
    }
    
    @Inject(method = { "displayGuiScreen" }, at = { @At("HEAD") })
    private void displayGuiScreen(final GuiScreen screen, final CallbackInfo ci) {
        if (screen instanceof GuiMainMenu) {
            this.func_147108_a(new EuropaMainMenu());
        }
    }
    
    @Redirect(method = { "sendClickBlockToController" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
    private boolean isHandActiveWrapper(final EntityPlayerSP playerSP) {
        return !Kaotik.getModuleManager().isModuleEnabled("MultiTask") && playerSP.func_184587_cr();
    }
    
    @Redirect(method = { "rightClickMouse" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z", ordinal = 0))
    private boolean isHittingBlockHook(final PlayerControllerMP playerControllerMP) {
        return !Kaotik.getModuleManager().isModuleEnabled("MultiTask") && playerControllerMP.func_181040_m();
    }
    
    @Redirect(method = { "createDisplay" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V"))
    private void createDisplay(final String title) {
        Display.setTitle("Kaotik 1.3.5 | evolving to kaotik");
    }
}
