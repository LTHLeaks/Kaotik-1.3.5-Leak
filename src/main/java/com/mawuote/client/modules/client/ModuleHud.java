package com.mawuote.client.modules.client;

import com.mawuote.api.manager.value.impl.*;
import java.awt.*;
import com.mawuote.api.manager.module.*;
import com.mawuote.api.manager.event.impl.network.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mawuote.api.manager.event.impl.render.*;
import com.mawuote.api.utilities.render.*;
import net.minecraft.client.gui.*;
import com.mawuote.*;
import com.mojang.realmsclient.gui.*;
import java.text.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import net.minecraft.util.math.*;
import com.mawuote.api.utilities.math.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.potion.*;

public class ModuleHud extends Module
{
    public static ValueEnum colorMode;
    public static ValueColor daColor;
    public static ValueColor gradient1;
    public static ValueColor gradient2;
    public static ValueNumber rainbowOffset;
    public static ValueNumber rainbowSat;
    public static ValueNumber rainbowBri;
    public static ValueEnum infoColor;
    public static ValueEnum waterMode;
    public static ValueString waterString;
    public static ValueBoolean watermark;
    public static ValueBoolean watermarkVersion;
    public static ValueBoolean welcomer;
    public static ValueBoolean fps;
    public static ValueBoolean tps;
    public static ValueBoolean ping;
    public static ValueBoolean packetPS;
    public static ValueBoolean speed;
    public static ValueBoolean brand;
    public static ValueEnum effectHud;
    public static ValueBoolean potionEffects;
    public static ValueBoolean potionSync;
    public static ValueBoolean coords;
    public static ValueBoolean netherCoords;
    public static ValueBoolean direction;
    public static ValueBoolean lagNotifier;
    public static ValueBoolean rubberNotifier;
    public static ValueBoolean armor;
    public static ValueBoolean arrayList;
    public static ValueEnum arrayRendering;
    public static ValueEnum ordering;
    private int components;
    private int leftComponents;
    private int packets;
    AnimationUtils anim;
    TimerUtils packetTimer;
    private Color colorHud;
    private boolean rubberbanded;
    TimerUtils serverTimer;
    TimerUtils rubberTimer;
    private static final RenderItem itemRender;
    
    public ModuleHud() {
        super("Hud", "Hud", "", ModuleCategory.CLIENT);
        this.anim = new AnimationUtils(500L, 1.0f, 100.0f);
        this.packetTimer = new TimerUtils();
        this.serverTimer = new TimerUtils();
        this.rubberTimer = new TimerUtils();
    }
    
    public void renderGif() {
        final GifLocation gif = new GifLocation("clientname/ban", 44, 1);
        gif.update();
        ModuleHud.mc.func_110434_K().func_110577_a(gif.getTexture());
        Gui.func_146110_a(0, 0, 0.0f, 0.0f, 498, 494, 498.0f, 494.0f);
    }
    
    @SubscribeEvent
    public void onReceive(final EventPacket.Receive event) {
        this.serverTimer.reset();
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            this.rubberbanded = true;
            this.rubberTimer.reset();
        }
    }
    
    @SubscribeEvent
    public void onSend(final EventPacket.Send event) {
        ++this.packets;
    }
    
    @Override
    public void onUpdate() {
        if (this.packetTimer.hasReached(1000L)) {
            this.packets = 0;
            this.packetTimer.reset();
        }
    }
    
    @Override
    public void onRender2D(final EventRender2D event) {
        if (ModuleHud.mc.field_71439_g == null || ModuleHud.mc.field_71441_e == null) {
            return;
        }
        final Color color = new Color(ModuleHud.daColor.getValue().getRed(), ModuleHud.daColor.getValue().getGreen(), ModuleHud.daColor.getValue().getBlue());
        final Color color2 = new Color(ModuleHud.gradient1.getValue().getRed(), ModuleHud.gradient1.getValue().getGreen(), ModuleHud.gradient1.getValue().getBlue());
        final Color color3 = new Color(ModuleHud.gradient2.getValue().getRed(), ModuleHud.gradient2.getValue().getGreen(), ModuleHud.gradient2.getValue().getBlue());
        this.colorHud = color;
        if (ModuleHud.welcomer.getValue()) {
            final Color gradientColor = RainbowUtils.getGradientOffset(color2, color3, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (this.leftComponents * 2 + 10) * 2.0f) % 2.0f - 1.0f));
            final Color waveColor = RainbowUtils.astolfoRainbow(color, 50, this.leftComponents * 2 + 10);
            final String stringW = "Hello " + ModuleHud.mc.field_71439_g.func_70005_c_() + ", Welcome To Kaotik";
            if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                this.drawRainbowString(stringW, new ScaledResolution(ModuleHud.mc).func_78326_a() / 2 - Kaotik.FONT_MANAGER.getStringWidth(stringW) / 2.0f, 2.0f, ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor.getRGB() : gradientColor.getRGB()));
            }
            else {
                this.drawStringWithShadow(stringW, new ScaledResolution(ModuleHud.mc).func_78326_a() / 2 - Kaotik.FONT_MANAGER.getStringWidth(stringW) / 2.0f, 2.0f, ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor.getRGB() : gradientColor.getRGB()));
            }
        }
        if (ModuleHud.lagNotifier.getValue() && this.serverTimer.hasReached(1000L)) {
            this.drawStringWithShadow(ChatFormatting.RED + "Server has not responded for §r" + new DecimalFormat("0.0").format(this.serverTimer.time() / 1000.0) + "s", new ScaledResolution(ModuleHud.mc).func_78326_a() / 2 - Kaotik.FONT_MANAGER.getStringWidth("Server has not responded for " + new DecimalFormat("0.0").format(this.serverTimer.time() / 1000L) + "s") / 2.0f, ModuleHud.welcomer.getValue() ? 12.0f : 2.0f, -1);
        }
        if (ModuleHud.rubberNotifier.getValue() && this.rubberbanded) {
            this.drawStringWithShadow(ChatFormatting.RED + "Rubberband detected §r" + new DecimalFormat("0.0").format(this.rubberTimer.time() / 1000.0) + "s", new ScaledResolution(ModuleHud.mc).func_78326_a() / 2 - Kaotik.FONT_MANAGER.getStringWidth(ChatFormatting.RED + "Rubberband detected §r" + new DecimalFormat("0.0").format(this.rubberTimer.time() / 1000.0) + "s") / 2.0f, (float)(0 + (ModuleHud.welcomer.getValue() ? 12 : 0) + (ModuleHud.lagNotifier.getValue() ? 12 : 0)), -1);
            if (this.rubberTimer.hasReached(3500L)) {
                this.rubberbanded = false;
            }
        }
        if (ModuleHud.armor.getValue()) {
            GlStateManager.func_179098_w();
            final ScaledResolution resolution = new ScaledResolution(ModuleHud.mc);
            final int i = resolution.func_78326_a() / 2;
            int iteration = 0;
            final int y = resolution.func_78328_b() - 55 - (ModuleHud.mc.field_71439_g.func_70090_H() ? 10 : 0);
            for (final ItemStack is : ModuleHud.mc.field_71439_g.field_71071_by.field_70460_b) {
                ++iteration;
                if (is.func_190926_b()) {
                    continue;
                }
                final int x = i - 90 + (9 - iteration) * 20 + 2;
                GlStateManager.func_179126_j();
                ModuleHud.itemRender.field_77023_b = 200.0f;
                ModuleHud.itemRender.func_180450_b(is, x, y);
                ModuleHud.itemRender.func_180453_a(ModuleHud.mc.field_71466_p, is, x, y, "");
                ModuleHud.itemRender.field_77023_b = 0.0f;
                GlStateManager.func_179098_w();
                GlStateManager.func_179140_f();
                GlStateManager.func_179097_i();
                final String s = (is.func_190916_E() > 1) ? (is.func_190916_E() + "") : "";
                ModuleHud.mc.field_71466_p.func_175063_a(s, (float)(x + 19 - 2 - ModuleHud.mc.field_71466_p.func_78256_a(s)), (float)(y + 9), 16777215);
                final float green = (is.func_77958_k() - (float)is.func_77952_i()) / is.func_77958_k();
                final float red = 1.0f - green;
                final int dmg = 100 - (int)(red * 100.0f);
                this.drawStringWithShadow(dmg + "", (float)(x + 8 - ModuleHud.mc.field_71466_p.func_78256_a(dmg + "") / 2), (float)(y - 11), new Color(red, green, 0.0f).getRGB());
            }
            GlStateManager.func_179126_j();
            GlStateManager.func_179140_f();
        }
        if (ModuleHud.arrayRendering.getValue().equals(renderingModes.Up)) {
            this.components = 1;
        }
        else {
            this.components = 0;
        }
        this.leftComponents = 0;
        final ScaledResolution scaledRes = new ScaledResolution(ModuleHud.mc);
        if (ModuleHud.watermark.getValue()) {
            final Color gradientColor2 = RainbowUtils.getGradientOffset(color2, color3, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (this.leftComponents * 2 + 10) * 2.0f) % 2.0f - 1.0f));
            final Color waveColor2 = RainbowUtils.astolfoRainbow(color, 50, this.leftComponents * 2 + 10);
            String stringW2;
            if (ModuleHud.waterMode.getValue().equals(waterModes.Default)) {
                stringW2 = "Kaotik" + (ModuleHud.watermarkVersion.getValue() ? " 1.3.5" : "");
            }
            else {
                stringW2 = ModuleHud.waterString.getValue() + (ModuleHud.watermarkVersion.getValue() ? " 1.3.5" : "");
            }
            if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                this.drawRainbowString(stringW2, 2.0f, 2.0f, ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
            }
            else {
                this.drawStringWithShadow(stringW2, 2.0f, 2.0f, ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
            }
        }
        if (ModuleHud.coords.getValue()) {
            final Color gradientColor2 = RainbowUtils.getGradientOffset(color2, color3, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (this.leftComponents * 2 + 10) * 2.0f) % 2.0f - 1.0f));
            final Color waveColor2 = RainbowUtils.astolfoRainbow(color, 50, this.leftComponents * 2 + 10);
            final boolean inHell = ModuleHud.mc.field_71441_e.func_180494_b(ModuleHud.mc.field_71439_g.func_180425_c()).func_185359_l().equals("Hell");
            final String posX = String.format("%.1f", ModuleHud.mc.field_71439_g.field_70165_t);
            final String posY = String.format("%.1f", ModuleHud.mc.field_71439_g.field_70163_u);
            final String posZ = String.format("%.1f", ModuleHud.mc.field_71439_g.field_70161_v);
            final float nether = inHell ? 8.0f : 0.125f;
            final String hposX = String.format("%.1f", ModuleHud.mc.field_71439_g.field_70165_t * nether);
            final String hposZ = String.format("%.1f", ModuleHud.mc.field_71439_g.field_70161_v * nether);
            if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                this.drawRainbowString("§+XYZ §r" + this.getInfoColor() + posX + ", " + posY + ", " + posZ + " " + (ModuleHud.netherCoords.getValue() ? ("[" + hposX + ", " + hposZ + "]") : ""), 2.0f, scaledRes.func_78328_b() - 2 - (ModuleHud.mc.field_71456_v.func_146158_b().func_146241_e() ? (Kaotik.FONT_MANAGER.getHeight() + 14.0f) : Kaotik.FONT_MANAGER.getHeight()), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
            }
            else {
                this.drawStringWithShadow("XYZ " + this.getInfoColor() + posX + ", " + posY + ", " + posZ + " " + (ModuleHud.netherCoords.getValue() ? ("[" + hposX + ", " + hposZ + "]") : ""), 2.0f, scaledRes.func_78328_b() - 2 - (ModuleHud.mc.field_71456_v.func_146158_b().func_146241_e() ? (Kaotik.FONT_MANAGER.getHeight() + 14.0f) : Kaotik.FONT_MANAGER.getHeight()), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
            }
            ++this.leftComponents;
        }
        if (ModuleHud.direction.getValue()) {
            final Color gradientColor2 = RainbowUtils.getGradientOffset(color2, color3, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (this.leftComponents * 2 + 10) * 2.0f) % 2.0f - 1.0f));
            final Color waveColor2 = RainbowUtils.astolfoRainbow(color, 50, this.leftComponents * 2 + 10);
            if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                this.drawRainbowString("§+" + getFacing() + "§r" + this.getInfoColor() + " [" + getTowards() + "]", 2.0f, scaledRes.func_78328_b() - 2 - (ModuleHud.mc.field_71456_v.func_146158_b().func_146241_e() ? (Kaotik.FONT_MANAGER.getHeight() + 14.0f) : (Kaotik.FONT_MANAGER.getHeight() + 2.0f)) - this.leftComponents * 10, ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
            }
            else {
                this.drawStringWithShadow(getFacing() + this.getInfoColor() + " [" + getTowards() + "]", 2.0f, scaledRes.func_78328_b() - 2 - (ModuleHud.mc.field_71456_v.func_146158_b().func_146241_e() ? (Kaotik.FONT_MANAGER.getHeight() + 14.0f) : (Kaotik.FONT_MANAGER.getHeight() + 2.0f)) - this.leftComponents * 10, ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
            }
        }
        if (ModuleHud.arrayRendering.getValue().equals(renderingModes.Up)) {
            if (ModuleHud.potionEffects.getValue()) {
                final int[] potCount = { 0 };
                try {
                    ModuleHud.mc.field_71439_g.func_70651_bq().forEach(effect -> {
                        final Color gradientColor3 = RainbowUtils.getGradientOffset(color1, color2, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (this.components * 2 + 10) * 2.0f) % 2.0f - 1.0f));
                        final Color waveColor3 = RainbowUtils.astolfoRainbow(color, 50, this.components * 2 + 10);
                        final String name = I18n.func_135052_a(effect.func_188419_a().func_76393_a(), new Object[0]);
                        final double duration = effect.func_76459_b() / 19.99f;
                        final int amplifier = effect.func_76458_c() + 1;
                        final int potionColor = effect.func_188419_a().func_76401_j();
                        final double p = duration % 60.0;
                        final DecimalFormat format2 = new DecimalFormat("00");
                        final String seconds = format2.format(p);
                        final String s2 = name + " " + amplifier + this.getInfoColor() + " " + (int)duration / 60 + ":" + seconds;
                        final String sR = "§+" + name + " " + amplifier + "§r" + this.getInfoColor() + " " + (int)duration / 60 + ":" + seconds;
                        if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow) && ModuleHud.potionSync.getValue()) {
                            this.drawRainbowString(sR, scaledRes.func_78326_a() - 2 - this.getStringWidth(s2), (float)(scaledRes.func_78328_b() + potCount[0] * -10 - 10 - 2), ModuleHud.potionSync.getValue() ? (ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor3.getRGB() : gradientColor3.getRGB())) : potionColor);
                        }
                        else {
                            this.drawStringWithShadow(s2, scaledRes.func_78326_a() - 2 - this.getStringWidth(s2), (float)(scaledRes.func_78328_b() + potCount[0] * -10 - 10 - 2), ModuleHud.potionSync.getValue() ? (ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor3.getRGB() : gradientColor3.getRGB())) : potionColor);
                        }
                        final int n;
                        ++potCount[n];
                        ++this.components;
                        return;
                    });
                }
                catch (final NullPointerException e) {
                    e.printStackTrace();
                }
            }
            if (ModuleHud.brand.getValue()) {
                final Color gradientColor2 = RainbowUtils.getGradientOffset(color2, color3, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (this.components * 2 + 10) * 2.0f) % 2.0f - 1.0f));
                final Color waveColor2 = RainbowUtils.astolfoRainbow(color, 50, this.components * 2 + 10);
                final String string = getServerBrand();
                if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                    this.drawRainbowString("§+" + string, scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(scaledRes.func_78328_b() - 2 - this.components * 10), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                else {
                    this.drawStringWithShadow(string, scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(scaledRes.func_78328_b() - 2 - this.components * 10), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                ++this.components;
            }
            if (ModuleHud.speed.getValue()) {
                final Color gradientColor2 = RainbowUtils.getGradientOffset(color2, color3, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (this.components * 2 + 10) * 2.0f) % 2.0f - 1.0f));
                final Color waveColor2 = RainbowUtils.astolfoRainbow(color, 50, this.components * 2 + 10);
                final DecimalFormat df = new DecimalFormat("#.#");
                final double deltaX = Minecraft.func_71410_x().field_71439_g.field_70165_t - Minecraft.func_71410_x().field_71439_g.field_70169_q;
                final double deltaZ = Minecraft.func_71410_x().field_71439_g.field_70161_v - Minecraft.func_71410_x().field_71439_g.field_70166_s;
                final float tickRate = Minecraft.func_71410_x().field_71428_T.field_194149_e / 1000.0f;
                final String BPSText = df.format(MathHelper.func_76133_a(deltaX * deltaX + deltaZ * deltaZ) / tickRate * 3.6);
                final String string2 = "Speed " + this.getInfoColor() + BPSText + "km/h";
                if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                    this.drawRainbowString("§+Speed §r" + this.getInfoColor() + BPSText + "km/h", scaledRes.func_78326_a() - 2 - this.getStringWidth(string2), (float)(scaledRes.func_78328_b() - 2 - this.components * 10), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                else {
                    this.drawStringWithShadow(string2, scaledRes.func_78326_a() - 2 - this.getStringWidth(string2), (float)(scaledRes.func_78328_b() - 2 - this.components * 10), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                ++this.components;
            }
            if (ModuleHud.tps.getValue()) {
                final Color gradientColor2 = RainbowUtils.getGradientOffset(color2, color3, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (this.components * 2 + 10) * 2.0f) % 2.0f - 1.0f));
                final Color waveColor2 = RainbowUtils.astolfoRainbow(color, 50, this.components * 2 + 10);
                final String string = "TPS " + this.getInfoColor() + String.format("%.2f", TPSUtils.getTickRate());
                if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                    this.drawRainbowString("§+TPS §r" + this.getInfoColor() + String.format("%.2f", TPSUtils.getTickRate()), scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(scaledRes.func_78328_b() - 2 - this.components * 10), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                else {
                    this.drawStringWithShadow(string, scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(scaledRes.func_78328_b() - 2 - this.components * 10), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                ++this.components;
            }
            if (ModuleHud.fps.getValue()) {
                final Color gradientColor2 = RainbowUtils.getGradientOffset(color2, color3, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (this.components * 2 + 10) * 2.0f) % 2.0f - 1.0f));
                final Color waveColor2 = RainbowUtils.astolfoRainbow(color, 50, this.components * 2 + 10);
                final String string = "FPS " + this.getInfoColor() + Minecraft.field_71470_ab;
                if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                    this.drawRainbowString("§+FPS §r" + this.getInfoColor() + Minecraft.field_71470_ab, scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(scaledRes.func_78328_b() - 2 - this.components * 10), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                else {
                    this.drawStringWithShadow(string, scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(scaledRes.func_78328_b() - 2 - this.components * 10), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                ++this.components;
            }
            if (ModuleHud.ping.getValue()) {
                final Color gradientColor2 = RainbowUtils.getGradientOffset(color2, color3, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (this.components * 2 + 10) * 2.0f) % 2.0f - 1.0f));
                final Color waveColor2 = RainbowUtils.astolfoRainbow(color, 50, this.components * 2 + 10);
                final String string = "Ping " + this.getInfoColor() + getPing();
                if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                    this.drawRainbowString("§+Ping §r" + this.getInfoColor() + getPing(), scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(scaledRes.func_78328_b() - 2 - this.components * 10), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                else {
                    this.drawStringWithShadow(string, scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(scaledRes.func_78328_b() - 2 - this.components * 10), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                ++this.components;
            }
            if (ModuleHud.packetPS.getValue()) {
                final Color gradientColor2 = RainbowUtils.getGradientOffset(color2, color3, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (this.components * 2 + 10) * 2.0f) % 2.0f - 1.0f));
                final Color waveColor2 = RainbowUtils.astolfoRainbow(color, 50, this.components * 2 + 10);
                final String string = "Packets " + this.getInfoColor() + this.packets;
                if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                    this.drawRainbowString("§+Packets §r" + this.getInfoColor() + this.packets, scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(scaledRes.func_78328_b() - 2 - this.components * 10), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                else {
                    this.drawStringWithShadow(string, scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(scaledRes.func_78328_b() - 2 - this.components * 10), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
            }
            if (ModuleHud.arrayList.getValue()) {
                final int[] mods = { 0 };
                if (ModuleHud.ordering.getValue().equals(orderModes.Length)) {
                    Kaotik.getModuleManager().getModules().stream().filter(Module::isToggled).filter(Module::isDrawn).sorted(Comparator.comparing(module -> this.getStringWidth(module.getTag() + module.getHudInfo()) * -1.0f)).forEach(m -> {
                        final String string3 = m.getTag() + ChatFormatting.GRAY + m.getHudInfo();
                        final Color gradientColor4 = RainbowUtils.getGradientOffset(color1, color2, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (mods[0] * 2 + 10) * 2.0f) % 2.0f - 1.0f));
                        final Color waveColor4 = RainbowUtils.astolfoRainbow(color, 50, mods[0] * 2 + 10);
                        if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                            this.drawRainbowString("§+" + m.getTag() + "§r" + ChatFormatting.GRAY + m.getHudInfo(), scaledRes.func_78326_a() - 2 - this.getStringWidth(string3), (float)(2 + mods[0] * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.field_71439_g.func_70651_bq().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor4.getRGB() : gradientColor4.getRGB()));
                        }
                        else {
                            this.drawStringWithShadow(string3, scaledRes.func_78326_a() - 2 - this.getStringWidth(string3), (float)(2 + mods[0] * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.field_71439_g.func_70651_bq().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor4.getRGB() : gradientColor4.getRGB()));
                        }
                        final int n2;
                        ++mods[n2];
                    });
                }
                else {
                    Kaotik.getModuleManager().getModules().stream().filter(Module::isToggled).filter(Module::isDrawn).sorted(Comparator.comparing(module -> module.getTag())).forEach(m -> {
                        final String string4 = m.getTag() + ChatFormatting.GRAY + m.getHudInfo();
                        final Color gradientColor5 = RainbowUtils.getGradientOffset(color1, color2, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (mods[0] * 2 + 10) * 2.0f) % 2.0f - 1.0f));
                        final Color waveColor5 = RainbowUtils.astolfoRainbow(color, 50, mods[0] * 2 + 10);
                        if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                            this.drawRainbowString("§+" + m.getTag() + "§r" + ChatFormatting.GRAY + m.getHudInfo(), scaledRes.func_78326_a() - 2 - this.getStringWidth(string4), (float)(2 + mods[0] * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.field_71439_g.func_70651_bq().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor5.getRGB() : gradientColor5.getRGB()));
                        }
                        else {
                            this.drawStringWithShadow(string4, scaledRes.func_78326_a() - 2 - this.getStringWidth(string4), (float)(2 + mods[0] * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.field_71439_g.func_70651_bq().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor5.getRGB() : gradientColor5.getRGB()));
                        }
                        final int n3;
                        ++mods[n3];
                    });
                }
            }
        }
        else {
            if (ModuleHud.potionEffects.getValue()) {
                final int[] potCount = { 0 };
                try {
                    ModuleHud.mc.field_71439_g.func_70651_bq().forEach(effect -> {
                        final Color gradientColor6 = RainbowUtils.getGradientOffset(color1, color2, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (this.components * 2 + 10) * 2.0f) % 2.0f - 1.0f));
                        final Color waveColor6 = RainbowUtils.astolfoRainbow(color, 50, this.components * 2 + 10);
                        final String name2 = I18n.func_135052_a(effect.func_188419_a().func_76393_a(), new Object[0]);
                        final double duration2 = effect.func_76459_b() / 19.99f;
                        final int amplifier2 = effect.func_76458_c() + 1;
                        final int potionColor2 = effect.func_188419_a().func_76401_j();
                        final double p2 = duration2 % 60.0;
                        final DecimalFormat format3 = new DecimalFormat("00");
                        final String seconds2 = format3.format(p2);
                        final String s3 = name2 + " " + amplifier2 + this.getInfoColor() + " " + (int)duration2 / 60 + ":" + seconds2;
                        final String sR2 = "§+" + name2 + " " + amplifier2 + "§r" + this.getInfoColor() + " " + (int)duration2 / 60 + ":" + seconds2;
                        if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                            this.drawStringWithShadow(sR2, scaledRes.func_78326_a() - 2 - this.getStringWidth(s3), (float)(2 + potCount[0] * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.field_71439_g.func_70651_bq().isEmpty()) ? 25 : 0)), ModuleHud.potionSync.getValue() ? (ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor6.getRGB() : gradientColor6.getRGB())) : potionColor2);
                        }
                        else {
                            this.drawStringWithShadow(s3, scaledRes.func_78326_a() - 2 - this.getStringWidth(s3), (float)(2 + potCount[0] * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.field_71439_g.func_70651_bq().isEmpty()) ? 25 : 0)), ModuleHud.potionSync.getValue() ? (ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor6.getRGB() : gradientColor6.getRGB())) : potionColor2);
                        }
                        final int n4;
                        ++potCount[n4];
                        ++this.components;
                        return;
                    });
                }
                catch (final NullPointerException e) {
                    e.printStackTrace();
                }
            }
            if (ModuleHud.brand.getValue()) {
                final Color gradientColor2 = RainbowUtils.getGradientOffset(color2, color3, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (this.components * 2 + 10) * 2.0f) % 2.0f - 1.0f));
                final Color waveColor2 = RainbowUtils.astolfoRainbow(color, 50, this.components * 2 + 10);
                final String string = getServerBrand();
                if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                    this.drawRainbowString("§+" + string, scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(2 + this.components * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.field_71439_g.func_70651_bq().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                else {
                    this.drawStringWithShadow(string, scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(2 + this.components * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.field_71439_g.func_70651_bq().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                ++this.components;
            }
            if (ModuleHud.speed.getValue()) {
                final Color gradientColor2 = RainbowUtils.getGradientOffset(color2, color3, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (this.components * 2 + 10) * 2.0f) % 2.0f - 1.0f));
                final Color waveColor2 = RainbowUtils.astolfoRainbow(color, 50, this.components * 2 + 10);
                final DecimalFormat df = new DecimalFormat("#.#");
                final double deltaX = Minecraft.func_71410_x().field_71439_g.field_70165_t - Minecraft.func_71410_x().field_71439_g.field_70169_q;
                final double deltaZ = Minecraft.func_71410_x().field_71439_g.field_70161_v - Minecraft.func_71410_x().field_71439_g.field_70166_s;
                final float tickRate = Minecraft.func_71410_x().field_71428_T.field_194149_e / 1000.0f;
                final String BPSText = df.format(MathHelper.func_76133_a(deltaX * deltaX + deltaZ * deltaZ) / tickRate * 3.6);
                final String string2 = "Speed " + this.getInfoColor() + BPSText + "km/h";
                if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                    this.drawRainbowString("§+Speed §r" + this.getInfoColor() + BPSText + "km/h", scaledRes.func_78326_a() - 2 - this.getStringWidth(string2), (float)(2 + this.components * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.field_71439_g.func_70651_bq().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                else {
                    this.drawStringWithShadow(string2, scaledRes.func_78326_a() - 2 - this.getStringWidth(string2), (float)(2 + this.components * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.field_71439_g.func_70651_bq().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                ++this.components;
            }
            if (ModuleHud.tps.getValue()) {
                final Color gradientColor2 = RainbowUtils.getGradientOffset(color2, color3, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (this.components * 2 + 10) * 2.0f) % 2.0f - 1.0f));
                final Color waveColor2 = RainbowUtils.astolfoRainbow(color, 50, this.components * 2 + 10);
                final String string = "TPS " + this.getInfoColor() + String.format("%.2f", TPSUtils.getTickRate());
                if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                    this.drawRainbowString("§+TPS §r" + this.getInfoColor() + String.format("%.2f", TPSUtils.getTickRate()), scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(2 + this.components * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.field_71439_g.func_70651_bq().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                else {
                    this.drawStringWithShadow(string, scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(2 + this.components * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.field_71439_g.func_70651_bq().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                ++this.components;
            }
            if (ModuleHud.fps.getValue()) {
                final Color gradientColor2 = RainbowUtils.getGradientOffset(color2, color3, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (this.components * 2 + 10) * 2.0f) % 2.0f - 1.0f));
                final Color waveColor2 = RainbowUtils.astolfoRainbow(color, 50, this.components * 2 + 10);
                final String string = "FPS " + this.getInfoColor() + Minecraft.field_71470_ab;
                if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                    this.drawRainbowString("§+FPS §r" + this.getInfoColor() + Minecraft.field_71470_ab, scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(2 + this.components * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.field_71439_g.func_70651_bq().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                else {
                    this.drawStringWithShadow(string, scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(2 + this.components * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.field_71439_g.func_70651_bq().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                ++this.components;
            }
            if (ModuleHud.ping.getValue()) {
                final Color gradientColor2 = RainbowUtils.getGradientOffset(color2, color3, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (this.components * 2 + 10) * 2.0f) % 2.0f - 1.0f));
                final Color waveColor2 = RainbowUtils.astolfoRainbow(color, 50, this.components * 2 + 10);
                final String string = "Ping " + this.getInfoColor() + getPing();
                if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                    this.drawRainbowString("§+Ping §r" + this.getInfoColor() + getPing(), scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(2 + this.components * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.field_71439_g.func_70651_bq().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                else {
                    this.drawStringWithShadow(string, scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(2 + this.components * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.field_71439_g.func_70651_bq().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                ++this.components;
            }
            if (ModuleHud.packetPS.getValue()) {
                final Color gradientColor2 = RainbowUtils.getGradientOffset(color2, color3, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (this.components * 2 + 10) * 2.0f) % 2.0f - 1.0f));
                final Color waveColor2 = RainbowUtils.astolfoRainbow(color, 50, this.components * 2 + 10);
                final String string = "Packets " + this.getInfoColor() + this.packets;
                if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                    this.drawRainbowString("§+Packets §r" + this.getInfoColor() + this.packets, scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(2 + this.components * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.field_71439_g.func_70651_bq().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
                else {
                    this.drawStringWithShadow(string, scaledRes.func_78326_a() - 2 - this.getStringWidth(string), (float)(2 + this.components * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.field_71439_g.func_70651_bq().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor2.getRGB() : gradientColor2.getRGB()));
                }
            }
            if (ModuleHud.arrayList.getValue()) {
                final int[] mods = { 0 };
                if (ModuleHud.ordering.getValue().equals(orderModes.Length)) {
                    Kaotik.getModuleManager().getModules().stream().filter(Module::isToggled).filter(Module::isDrawn).sorted(Comparator.comparing(module -> this.getStringWidth(module.getTag() + module.getHudInfo()) * -1.0f)).forEach(m -> {
                        final String string5 = m.getTag() + ChatFormatting.GRAY + m.getHudInfo();
                        final Color gradientColor7 = RainbowUtils.getGradientOffset(color1, color2, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (mods[0] * 2 + 10) * 2.0f) % 2.0f - 1.0f));
                        final Color waveColor7 = RainbowUtils.astolfoRainbow(color, 50, mods[0] * 2 + 10);
                        if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                            this.drawStringWithShadow("§+" + m.getTag() + "§r" + ChatFormatting.GRAY + m.getHudInfo(), scaledRes.func_78326_a() - 2 - this.getStringWidth(string5), (float)(scaledRes.func_78328_b() + mods[0] * -10 - 12), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor7.getRGB() : gradientColor7.getRGB()));
                        }
                        else {
                            this.drawStringWithShadow(string5, scaledRes.func_78326_a() - 2 - this.getStringWidth(string5), (float)(scaledRes.func_78328_b() + mods[0] * -10 - 12), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor7.getRGB() : gradientColor7.getRGB()));
                        }
                        final int n5;
                        ++mods[n5];
                    });
                }
                else {
                    Kaotik.getModuleManager().getModules().stream().filter(Module::isToggled).filter(Module::isDrawn).sorted(Comparator.comparing(module -> module.getTag())).forEach(m -> {
                        final String string6 = m.getTag() + ChatFormatting.GRAY + m.getHudInfo();
                        final Color gradientColor8 = RainbowUtils.getGradientOffset(color1, color2, Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + 20.0f / (mods[0] * 2 + 10) * 2.0f) % 2.0f - 1.0f));
                        final Color waveColor8 = RainbowUtils.astolfoRainbow(color, 50, mods[0] * 2 + 10);
                        if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
                            this.drawStringWithShadow("§+" + m.getTag() + "§r" + ChatFormatting.GRAY + m.getHudInfo(), scaledRes.func_78326_a() - 2 - this.getStringWidth(string6), (float)(scaledRes.func_78328_b() + mods[0] * -10 - 12), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor8.getRGB() : gradientColor8.getRGB()));
                        }
                        else {
                            this.drawStringWithShadow(string6, scaledRes.func_78326_a() - 2 - this.getStringWidth(string6), (float)(scaledRes.func_78328_b() + mods[0] * -10 - 12), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor8.getRGB() : gradientColor8.getRGB()));
                        }
                        final int n6;
                        ++mods[n6];
                    });
                }
            }
        }
    }
    
    public ChatFormatting getInfoColor() {
        if (ModuleHud.infoColor.getValue().equals(infoColors.Gray)) {
            return ChatFormatting.GRAY;
        }
        return ChatFormatting.WHITE;
    }
    
    private void drawStringWithShadow(final String text, final float x, final float y, final int color) {
        Kaotik.FONT_MANAGER.drawString(text, x, y, new Color(color));
    }
    
    public float getStringWidth(final String text) {
        return Kaotik.FONT_MANAGER.getStringWidth(text);
    }
    
    public void drawRainbowString(final String text, final float x, final float y, final int coloring) {
        int currentWidth = 0;
        boolean shouldRainbow = true;
        boolean shouldContinue = false;
        final int[] counterChing = { 1 };
        for (int i = 0; i < text.length(); ++i) {
            final Color color = RainbowUtils.anyRainbowColor(counterChing[0] * ModuleHud.rainbowOffset.getValue().intValue(), ModuleHud.rainbowSat.getValue().intValue(), ModuleHud.rainbowBri.getValue().intValue());
            final char currentChar = text.charAt(i);
            final char nextChar = text.charAt(MathHelper.func_76125_a(i + 1, 0, text.length() - 1));
            if ((String.valueOf(currentChar) + nextChar).equals("§r")) {
                shouldRainbow = false;
            }
            else if ((String.valueOf(currentChar) + nextChar).equals("§+")) {
                shouldRainbow = true;
            }
            if (shouldContinue) {
                shouldContinue = false;
            }
            else {
                if ((String.valueOf(currentChar) + nextChar).equals("§r")) {
                    final String escapeString = text.substring(i);
                    Kaotik.FONT_MANAGER.drawString(escapeString, x + currentWidth, y, Color.WHITE);
                    break;
                }
                Kaotik.FONT_MANAGER.drawString(String.valueOf(currentChar).equals("§") ? "" : String.valueOf(currentChar), x + currentWidth, y, shouldRainbow ? color : Color.WHITE);
                if (String.valueOf(currentChar).equals("§")) {
                    shouldContinue = true;
                }
                currentWidth += (int)this.getStringWidth(String.valueOf(currentChar));
                if (!String.valueOf(currentChar).equals(" ")) {
                    final int[] array = counterChing;
                    final int n = 0;
                    ++array[n];
                }
            }
        }
    }
    
    public static String getFacing() {
        switch (MathHelper.func_76128_c(ModuleHud.mc.field_71439_g.field_70177_z * 8.0f / 360.0f + 0.5) & 0x7) {
            case 0: {
                return "South";
            }
            case 1: {
                return "South";
            }
            case 2: {
                return "West";
            }
            case 3: {
                return "West";
            }
            case 4: {
                return "North";
            }
            case 5: {
                return "North";
            }
            case 6: {
                return "East";
            }
            case 7: {
                return "East";
            }
            default: {
                return "Invalid";
            }
        }
    }
    
    public static String getTowards() {
        switch (MathHelper.func_76128_c(ModuleHud.mc.field_71439_g.field_70177_z * 8.0f / 360.0f + 0.5) & 0x7) {
            case 0: {
                return "+Z";
            }
            case 1: {
                return "+Z";
            }
            case 2: {
                return "-X";
            }
            case 3: {
                return "-X";
            }
            case 4: {
                return "-Z";
            }
            case 5: {
                return "-Z";
            }
            case 6: {
                return "+X";
            }
            case 7: {
                return "+X";
            }
            default: {
                return "Invalid";
            }
        }
    }
    
    public static int getPing() {
        int p;
        if (ModuleHud.mc.field_71439_g == null || ModuleHud.mc.func_147114_u() == null || ModuleHud.mc.func_147114_u().func_175104_a(ModuleHud.mc.field_71439_g.func_70005_c_()) == null) {
            p = -1;
        }
        else {
            ModuleHud.mc.field_71439_g.func_70005_c_();
            p = Objects.requireNonNull(ModuleHud.mc.func_147114_u().func_175104_a(ModuleHud.mc.field_71439_g.func_70005_c_())).func_178853_c();
        }
        return p;
    }
    
    public static final String getServerBrand() {
        String s;
        if (ModuleHud.mc.func_147104_D() == null) {
            s = "Vanilla";
        }
        else {
            final EntityPlayerSP it = ModuleHud.mc.field_71439_g;
            final int n = 0;
            final String getServerBrand = ModuleHud.mc.field_71439_g.func_142021_k();
            s = ((getServerBrand == null) ? "Vanilla" : getServerBrand);
        }
        return s;
    }
    
    static {
        ModuleHud.colorMode = new ValueEnum("ColorMode", "ColorMode", "", colorModes.Wave);
        ModuleHud.daColor = new ValueColor("Color", "Color", "", new Color(70, 10, 10));
        ModuleHud.gradient1 = new ValueColor("Gradient1", "Gradient1", "", new Color(255, 0, 0));
        ModuleHud.gradient2 = new ValueColor("Gradient2", "Gradient2", "", new Color(0, 0, 0));
        ModuleHud.rainbowOffset = new ValueNumber("RainbowOffset", "RainbowOffset", "", 255, 0, 255);
        ModuleHud.rainbowSat = new ValueNumber("RainbowSaturation", "RainbowSat", "", 255, 0, 255);
        ModuleHud.rainbowBri = new ValueNumber("RainbowBrightness", "RainbowSat", "", 255, 0, 255);
        ModuleHud.infoColor = new ValueEnum("InfoColor", "InfoColor", "", infoColors.Gray);
        ModuleHud.waterMode = new ValueEnum("WaterMode", "WaterMode", "", waterModes.Default);
        ModuleHud.waterString = new ValueString("WaterString", "WaterStrig", "", "Kaotik");
        ModuleHud.watermark = new ValueBoolean("Watermark", "Watermark", "", true);
        ModuleHud.watermarkVersion = new ValueBoolean("WatermarkVersion", "WatermarkVersion", "", true);
        ModuleHud.welcomer = new ValueBoolean("Welcomer", "Welcomer", "", false);
        ModuleHud.fps = new ValueBoolean("FPS", "FPS", "", true);
        ModuleHud.tps = new ValueBoolean("TPS", "TPS", "", true);
        ModuleHud.ping = new ValueBoolean("Ping", "Ping", "", true);
        ModuleHud.packetPS = new ValueBoolean("Packets", "Packets", "", false);
        ModuleHud.speed = new ValueBoolean("Speed", "Speed", "", true);
        ModuleHud.brand = new ValueBoolean("ServerBrand", "ServerBrand", "", true);
        ModuleHud.effectHud = new ValueEnum("EffectHud", "EffectHud", "", effectHudModes.Move);
        ModuleHud.potionEffects = new ValueBoolean("Effects", "Effect", "", true);
        ModuleHud.potionSync = new ValueBoolean("EffectSync", "EffectSync", "", false);
        ModuleHud.coords = new ValueBoolean("Coords", "Coords", "", true);
        ModuleHud.netherCoords = new ValueBoolean("NetherCoords", "NetherCoords", "", true);
        ModuleHud.direction = new ValueBoolean("Direction", "Direction", "", true);
        ModuleHud.lagNotifier = new ValueBoolean("LagNotifier", "LagNotifier", "", false);
        ModuleHud.rubberNotifier = new ValueBoolean("RubberNotifier", "RubberNotifier", "", false);
        ModuleHud.armor = new ValueBoolean("Armor", "Armor", "", false);
        ModuleHud.arrayList = new ValueBoolean("ArrayList", "ArrayList", "", true);
        ModuleHud.arrayRendering = new ValueEnum("Rendering", "Rendering", "", renderingModes.Up);
        ModuleHud.ordering = new ValueEnum("Ordering", "Ordering", "", orderModes.Length);
        itemRender = Minecraft.func_71410_x().func_175599_af();
    }
    
    public enum effectHudModes
    {
        Hide, 
        Keep, 
        Move;
    }
    
    public enum infoColors
    {
        Gray, 
        White;
    }
    
    public enum waterModes
    {
        Default, 
        Custom;
    }
    
    public enum colorModes
    {
        Static, 
        Wave, 
        Gradient, 
        Rainbow;
    }
    
    public enum renderingModes
    {
        Up, 
        Down;
    }
    
    public enum orderModes
    {
        ABC, 
        Length;
    }
}
