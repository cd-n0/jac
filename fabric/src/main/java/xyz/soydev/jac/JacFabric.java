package xyz.soydev.jac;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import xyz.soydev.jac.features.JacKeyBinding;

import java.util.List;

public class JacFabric implements ModInitializer {
    Jac jac = new Jac();

    @Override
    public void onInitialize() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();
        jac.onInitialize();
        registerBindings();
    }


    public static void registerBindings() {
        JacClient.features.getFeatures().forEach(feature ->
            feature.getKeyBindings().forEach(KeyBindingHelper::registerKeyBinding)
        );
    }
}
