package xyz.soydev.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import xyz.soydev.JacClient;
import xyz.soydev.features.Feature;

public class JacOptionsScreen extends OptionsScreen {

    private final Screen parent;
    private final GameOptions settings;


    public JacOptionsScreen(Screen parent, GameOptions gameOptions) {
        super(parent, gameOptions);
        this.parent = parent;
        this.settings = gameOptions;
    }

    @Override
    protected void init() {
        int i = 0;
        for (Feature feature : JacClient.features.getFeatures()) {
            addDrawableChild(
                    ButtonWidget.builder(
                            feature.getTitle(),
                            buttonWidget -> {
                                client.setScreen(new JacModuleOptionsScreen(this, settings, feature));
                            }
                    ).dimensions(0, 20 * i++, 150, 20).build()
            );
        }
    }

}
