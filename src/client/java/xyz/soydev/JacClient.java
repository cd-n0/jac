package xyz.soydev;

import net.fabricmc.api.ClientModInitializer;

public class JacClient implements ClientModInitializer {
    // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    @Override
    public void onInitializeClient() {
        Jac.LOGGER.info("Client initialized");
    }
}
