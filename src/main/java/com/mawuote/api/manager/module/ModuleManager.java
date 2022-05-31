package com.mawuote.api.manager.module;

import net.minecraft.client.*;
import net.minecraftforge.common.*;
import com.mawuote.client.modules.combat.*;
import com.mawuote.client.modules.player.*;
import com.mawuote.client.modules.misc.*;
import com.mawuote.client.modules.movement.*;
import com.mawuote.client.modules.render.*;
import com.mawuote.client.modules.client.*;
import java.util.function.*;
import com.mawuote.api.manager.value.*;
import java.lang.reflect.*;
import java.util.stream.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mawuote.api.manager.event.impl.player.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.client.event.*;
import com.mawuote.api.manager.event.impl.render.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.gameevent.*;
import org.lwjgl.input.*;

public class ModuleManager
{
    protected static final Minecraft mc;
    private final ArrayList<Module> modules;
    
    public ModuleManager() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.modules = new ArrayList<Module>();
        this.addModule(new AutoCrystalHack());
        this.addModule(new AutoTrap());
        this.addModule(new AutoFillHoleChing());
        this.addModule(new Surroundhack());
        this.addModule(new FeetPlace());
        this.addModule(new Criticals());
        this.addModule(new AutoTotem());
        this.addModule(new ChorusPospone());
        this.addModule(new Dummy());
        this.addModule(new ModuleDiscordPresence());
        this.addModule(new Announcer());
        this.addModule(new hihowareyou());
        this.addModule(new Killsay());
        this.addModule(new ChatMod());
        this.addModule(new Anchor());
        this.addModule(new Speedy());
        this.addModule(new Strafe());
        this.addModule(new Step());
        this.addModule(new NoSlowDown());
        this.addModule(new FastFall());
        this.addModule(new TP());
        this.addModule(new Sprint());
        this.addModule(new Velocity());
        this.addModule(new ModuleAnimations());
        this.addModule(new ModuleHoleESP());
        this.addModule(new ModuleShaderChams());
        this.addModule(new ModulePopChams());
        this.addModule(new ModuleCrosshair());
        this.addModule(new ModuleWallhack());
        this.addModule(new ModuleCrystalChams());
        this.addModule(new ModulePlayerChams());
        this.addModule(new ModuleESP());
        this.addModule(new ModuleNoRender());
        this.addModule(new ModuleSkeleton());
        this.addModule(new ModuleNametags());
        this.addModule(new ModuleLogoutSpots());
        this.addModule(new ModuleMiddleClick());
        this.addModule(new ModuleNotifications());
        this.addModule(new ModuleHud());
        this.addModule(new ModuleGUI());
        this.addModule(new ModuleStreamerMode());
        this.addModule(new ModuleColor());
        this.addModule(new ModuleFont());
        this.addModule(new ModuleParticles());
        this.addModule(new ModuleHUDEditor());
        this.modules.sort(Comparator.comparing((Function<? super Module, ? extends Comparable>)Module::getName));
    }
    
    public void addModule(final Module module) {
        try {
            for (final Field field : module.getClass().getDeclaredFields()) {
                if (Value.class.isAssignableFrom(field.getType())) {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    module.addValue((Value)field.get(module));
                }
            }
            module.addValue(module.tag);
            module.addValue(module.chatNotify);
            module.addValue(module.drawn);
            module.addValue(module.bind);
            this.modules.add(module);
        }
        catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Module> getModules() {
        return this.modules;
    }
    
    public ArrayList<Module> getModules(final ModuleCategory category) {
        return this.modules.stream().filter(m -> m.getCategory().equals(category)).collect((Collector<? super Object, ?, ArrayList<Module>>)Collectors.toList());
    }
    
    public Module getModule(final String name) {
        for (final Module module : this.modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }
    
    public boolean isModuleEnabled(final String name) {
        final Module module = this.modules.stream().filter(m -> m.getName().equals(name)).findFirst().orElse(null);
        return module != null && module.isToggled();
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (ModuleManager.mc.field_71439_g != null && ModuleManager.mc.field_71441_e != null) {
            this.modules.stream().filter(Module::isToggled).forEach(Module::onUpdate);
        }
    }
    
    @SubscribeEvent
    public void onUpdate(final EventMotionUpdate event) {
        if (ModuleManager.mc.field_71439_g != null && ModuleManager.mc.field_71441_e != null) {
            this.modules.stream().filter(Module::isToggled).forEach(Module::onMotionUpdate);
        }
    }
    
    @SubscribeEvent
    public void onRender2D(final RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            this.modules.stream().filter(Module::isToggled).forEach(m -> m.onRender2D(new EventRender2D(event.getPartialTicks())));
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @SubscribeEvent
    public void onRender3D(final RenderWorldLastEvent event) {
        ModuleManager.mc.field_71424_I.func_76320_a("mawuote");
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        GlStateManager.func_179103_j(7425);
        GlStateManager.func_179097_i();
        GlStateManager.func_187441_d(1.0f);
        final EventRender3D renderEvent = new EventRender3D(event.getPartialTicks());
        this.modules.stream().filter(Module::isToggled).forEach(mm -> mm.onRender3D(renderEvent));
        GlStateManager.func_187441_d(1.0f);
        GlStateManager.func_179103_j(7424);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
        GlStateManager.func_179126_j();
        GlStateManager.func_179089_o();
        GlStateManager.func_179089_o();
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179098_w();
        GlStateManager.func_179147_l();
        GlStateManager.func_179126_j();
        ModuleManager.mc.field_71424_I.func_76319_b();
    }
    
    @SubscribeEvent
    public void onLogin(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        this.modules.stream().filter(Module::isToggled).forEach(Module::onLogin);
    }
    
    @SubscribeEvent
    public void onLogout(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.modules.stream().filter(Module::isToggled).forEach(Module::onLogout);
    }
    
    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == 0) {
                return;
            }
            for (final Module module : this.modules) {
                if (module.getBind() == Keyboard.getEventKey()) {
                    module.toggle();
                }
            }
        }
    }
    
    static {
        mc = Minecraft.func_71410_x();
    }
}
