package xyz.soydev.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import xyz.soydev.features.Feature;

import java.util.ArrayList;
import java.util.List;

public class JacModuleOptionsScreen extends GameOptionsScreen {

    private final Screen parent;
    private final GameOptions settings;
    private final Feature feature;

    public JacModuleOptionsScreen(Screen parent, GameOptions gameOptions, Feature feature) {
        super(parent, gameOptions, feature.getTitle());
        this.parent = parent;
        this.settings = gameOptions;
        this.feature = feature;
    }

    @Override
    protected void addOptions() {
        List<ClickableWidget> widgets = new ArrayList<>();
        for (SimpleOption option : feature.getOptions()) {
            widgets.add(option.createWidget(settings));
        }
        this.body.addAll(widgets);
    }

}
