package xyz.soydev.jac;

import net.minecraft.client.Minecraft;
import xyz.soydev.jac.features.Feature;
import xyz.soydev.jac.features.movement.Fly;
import xyz.soydev.jac.features.movement.NoFall;
import xyz.soydev.jac.features.movement.Speed;
import xyz.soydev.jac.features.utility.AutoFish;

import java.util.ArrayList;
import java.util.List;

public class JacClient {
    public static Minecraft instance;
    public static final List<Feature> features = new ArrayList<>();
    // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    public static void onInitializeClient() {
        instance = Minecraft.getInstance();
        features.add(new Fly());
        features.add(new NoFall());
        features.add(new AutoFish());
        features.add(new Speed());

        Jac.LOGGER.info("Client initialized");
    }
}
