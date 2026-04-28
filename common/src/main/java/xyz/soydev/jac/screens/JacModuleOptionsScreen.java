package xyz.soydev.jac.screens;

import xyz.soydev.jac.features.Feature;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;

public class JacModuleOptionsScreen extends OptionsSubScreen {

    //private final Screen parent;
    private final Options settings;
    private final Feature feature;

    public JacModuleOptionsScreen(Screen parent, Options gameOptions, Feature feature) {
        super(parent, gameOptions, feature.getTitle());
        //this.lastScreen = parent;
        this.settings = gameOptions;
        this.feature = feature;
    }

    @Override
    protected void addOptions() {
        List<AbstractWidget> widgets = new ArrayList<>();
        for (OptionInstance option : feature.getOptions()) {
            widgets.add(option.createButton(settings));
        }
        this.list.addSmall(widgets);
    }

}
