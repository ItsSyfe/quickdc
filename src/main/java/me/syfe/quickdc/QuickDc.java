package me.syfe.quickdc;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QuickDc implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("quickdc");

	@Override
	public void onInitialize() {
		LOGGER.info("QuickDC has been initialized, awaiting your speedy logout.");
	}

	public static MinecraftClient getClient() {
		return MinecraftClient.getInstance();
	}
}
