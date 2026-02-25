package xyz.soydev;

import net.minecraft.client.MinecraftClient;
import net.fabricmc.api.ClientModInitializer;
import xyz.soydev.features.Features;

public class JacClient implements ClientModInitializer {
    public static MinecraftClient instance;
    public static Features features = new Features();
    // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    @Override
    public void onInitializeClient() {
        instance = MinecraftClient.getInstance();
        Jac.LOGGER.info("Client initialized");
    }
}
