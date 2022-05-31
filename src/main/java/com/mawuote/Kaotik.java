package com.mawuote;

import net.minecraftforge.fml.common.*;
import com.mawuote.client.modules.client.notifications.*;
import com.mawuote.api.manager.module.*;
import com.mawuote.api.manager.element.*;
import com.mawuote.api.manager.command.*;
import com.mawuote.api.manager.friend.*;
import com.mawuote.api.manager.event.*;
import com.mawuote.client.gui.font.*;
import com.mawuote.client.gui.click.*;
import com.mawuote.client.gui.hud.*;
import com.mawuote.client.gui.special.*;
import com.mawuote.api.manager.misc.*;
import java.util.concurrent.*;
import com.mawuote.api.utilities.math.*;
import net.minecraftforge.fml.common.event.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import java.nio.*;
import javax.imageio.*;
import java.util.*;
import java.awt.image.*;
import java.io.*;
import org.apache.logging.log4j.*;

@Mod(modid = "kaotik", name = "Kaotik", version = "1.3.5")
public class Kaotik
{
    public static final String NAME = "Kaotik";
    public static final String VERSION = "1.3.5";
    public static final String MODID = "kaotik";
    public final ExecutorService executorService;
    public static final Logger LOGGER;
    @Mod.Instance("kaotik")
    public static Kaotik INSTANCE;
    public static NotificationProcessor NOTIFICATION_PROCESSOR;
    public static ChatManager CHAT_MANAGER;
    public static ModuleManager MODULE_MANAGER;
    public static ElementManager ELEMENT_MANAGER;
    public static CommandManager COMMAND_MANAGER;
    public static FriendManager FRIEND_MANAGER;
    public static EventManager EVENT_MANAGER;
    public static FontManager FONT_MANAGER;
    public static ClickGuiScreen CLICK_GUI;
    public static HudEditorScreen HUD_EDITOR;
    public static EuropaMainMenu MAIN_MENU;
    public static ConfigManager CONFIG_MANAGER;
    
    public Kaotik() {
        this.executorService = Executors.newSingleThreadExecutor();
    }
    
    @Mod.EventHandler
    public void initialize(final FMLInitializationEvent event) {
        Kaotik.LOGGER.info("Started Initialization process for Kaotik v1.3.5!");
        Kaotik.CHAT_MANAGER = new ChatManager();
        Kaotik.MODULE_MANAGER = new ModuleManager();
        Kaotik.ELEMENT_MANAGER = new ElementManager();
        Kaotik.COMMAND_MANAGER = new CommandManager();
        Kaotik.FRIEND_MANAGER = new FriendManager();
        Kaotik.EVENT_MANAGER = new EventManager();
        (Kaotik.FONT_MANAGER = new FontManager()).load();
        Kaotik.CLICK_GUI = new ClickGuiScreen();
        Kaotik.HUD_EDITOR = new HudEditorScreen();
        Kaotik.MAIN_MENU = new EuropaMainMenu();
        Kaotik.NOTIFICATION_PROCESSOR = new NotificationProcessor();
        (Kaotik.CONFIG_MANAGER = new ConfigManager()).load();
        Kaotik.CONFIG_MANAGER.attach();
        new TPSUtils();
        Kaotik.LOGGER.info("Finished Initialization process for Kaotik v1.3.5!");
    }
    
    @Mod.EventHandler
    public void postInitialize(final FMLPostInitializationEvent event) {
        Kaotik.LOGGER.info("Started Post-Initialization process for Kaotik v1.3.5!");
        Display.setTitle("Kaotik 1.3.5 | kaotik got leaked by ssk");
        Kaotik.LOGGER.info("Finished Post-Initialization process for Kaotik v1.3.5!");
    }
    
    public static ModuleManager getModuleManager() {
        return Kaotik.MODULE_MANAGER;
    }
    
    public void setWindowIcon() {
        if (Util.getOSType() != Util.EnumOS.OSX) {
            try (final InputStream inputStream16x = Minecraft.class.getResourceAsStream("/assets/mawuote/img16.png");
                 final InputStream inputStream32x = Minecraft.class.getResourceAsStream("/assets/mawuote/img32.png")) {
                final ByteBuffer[] icons = { this.readImageToBuffer(inputStream16x), this.readImageToBuffer(inputStream32x) };
                Display.setIcon(icons);
            }
            catch (final Exception e) {
                Kaotik.LOGGER.error("couldnt display window icon", (Throwable)e);
            }
        }
    }
    
    public ByteBuffer readImageToBuffer(final InputStream inputStream) throws IOException {
        final BufferedImage bufferedimage = ImageIO.read(inputStream);
        final int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
        final ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
        Arrays.stream(aint).map(i -> i << 8 | (i >> 24 & 0xFF)).forEach(bytebuffer::putInt);
        bytebuffer.flip();
        return bytebuffer;
    }
    
    private void setEuropaIcon() {
        this.setWindowIcon();
    }
    
    public ExecutorService getExecutorService() {
        return this.executorService;
    }
    
    static {
        LOGGER = LogManager.getLogger("Kaotik");
    }
}
