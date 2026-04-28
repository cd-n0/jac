package xyz.soydev.jac;

import net.minecraft.client.Minecraft;
import xyz.soydev.jac.features.Features;

public class JacClient {
    public static Minecraft instance;
    public static Features features = new Features();
    // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    public static void onInitializeClient() {
        instance = Minecraft.getInstance();
        Jac.LOGGER.info("Client initialized");
    }
}
