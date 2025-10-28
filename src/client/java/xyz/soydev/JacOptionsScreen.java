package xyz.soydev;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;

public class JacOptionsScreen extends Screen {

    private final Screen parent;
    private final GameOptions settings;


    public JacOptionsScreen(Screen parent, GameOptions gameOptions) {
        super(Text.translatable(Jac.MOD_ID + "title"));
        this.parent = parent;
        this.settings = gameOptions;
    }

    @Override
    protected void init() {
        this.addDrawableChild(
                ButtonWidget.builder(
                    Text.translatable(Jac.MOD_ID + ".test"),
                    buttonWidget -> {Jac.LOGGER.info("Button Clicked");}
                    ).dimensions(0, 0, 150, 20).build()
                );
    }

}
