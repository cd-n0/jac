package xyz.soydev.jac.screens;

import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import xyz.soydev.jac.JacClient;
import xyz.soydev.jac.features.Feature;

public class JacOptionsScreen extends OptionsScreen {

    private final Screen parent;
    private final Options settings;


    public JacOptionsScreen(Screen parent, Options gameOptions) {
        super(parent, gameOptions);
        this.parent = parent;
        this.settings = gameOptions;
    }

    @Override
    protected void init() {
        int i = 0;
        for (Feature feature : JacClient.features) {
            addRenderableWidget(
                    Button.builder(
                            feature.getTitle(),
                            buttonWidget -> {
                                minecraft.setScreen(new JacModuleOptionsScreen(this, settings, feature));
                            }
                    ).bounds(0, 20 * i++, 150, 20).build()
            );
        }
    }

}
