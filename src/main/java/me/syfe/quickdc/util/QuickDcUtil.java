package me.syfe.quickdc.util;

import me.syfe.quickdc.QuickDc;
import me.syfe.quickdc.QuickDcClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.SaveLevelScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.text.TranslatableText;

public class QuickDcUtil {
    public static void dc(MinecraftClient client) {
        boolean isSinglePlayer = client.isInSingleplayer();
        boolean isConnectedRealms = client.isConnectedToRealms();

        client.world.disconnect(); // Disconnect from the server / integrated server

        if (isSinglePlayer) {
            client.disconnect(new SaveLevelScreen(new TranslatableText("menu.savingLevel")));
        } else {
            client.disconnect();
        }

        TitleScreen titleScreen = new TitleScreen();

        // Resetting the displayed screen for the game
        if (isSinglePlayer) {
            client.setScreen(titleScreen);
        } else if (isConnectedRealms) {
            client.setScreen(new RealmsMainScreen(titleScreen));
        } else {
            client.setScreen(new MultiplayerScreen(titleScreen));
        }
    }

    public static void rc(MinecraftClient client) {
        QuickDc.LOGGER.info("Reconnecting to " + QuickDcClient.getRecentIp() + ":" + QuickDcClient.getRecentPort());
        ConnectScreen.connect(client.currentScreen, client, new ServerAddress(QuickDcClient.getRecentIp(), QuickDcClient.getRecentPort()), (ServerInfo)null);
    }
}
