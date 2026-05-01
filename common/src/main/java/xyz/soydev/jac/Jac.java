package xyz.soydev.jac;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;

public class Jac {
    public static final String MOD_ID = "jac";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // This code runs as soon as Minecraft is in a mod-load-ready state.
    // However, some things (like resources) may still be uninitialized.
    // Proceed with mild caution.
    public void onInitialize() {
        LOGGER.info("JAC initialized!");
        JacClient.onInitializeClient();
    }
}
