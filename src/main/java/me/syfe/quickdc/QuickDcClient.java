package me.syfe.quickdc;

import me.syfe.quickdc.util.QuickDcUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.TimeUnit;

public class QuickDcClient implements ClientModInitializer {
    KeyBinding quickDc = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.quickdc.dc",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_J,
            "category.quickdc.quickdc"
    ));

    KeyBinding quickRc = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.quickdc.rc",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_K,
            "category.quickdc.quickdc"
    ));

    public static String recentIp;
    public static int recentPort;

    public static String getRecentIp() {
        return recentIp;
    }

    public static int getRecentPort() {
        return recentPort;
    }

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (quickDc.wasPressed()) {
                quickDc.setPressed(true);
                QuickDcUtil.dc(client);
            }

            if (quickRc.wasPressed()) {
                quickRc.setPressed(true);
                QuickDcUtil.dc(client);
                try {
                    TimeUnit.MILLISECONDS.sleep(250); // need to set this to over 4000 to prevent connection throttling on vanilla servers
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                QuickDcUtil.rc(client);
            }
        });


        ClientPlayConnectionEvents.DISCONNECT.register((disconnect, client) -> {
            String serverAddress = disconnect.getConnection().getAddress().toString().split("/")[1];
            QuickDc.LOGGER.info(serverAddress);
            recentIp = serverAddress.split(":")[0];
            recentPort = Integer.parseInt(serverAddress.split(":")[1]);
            QuickDc.LOGGER.info(recentIp + ":" + recentPort);
        });
    }
}
