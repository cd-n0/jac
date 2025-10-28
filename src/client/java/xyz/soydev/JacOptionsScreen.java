package xyz.soydev;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;

public class JacOptionsScreen extends Screen {

    private final Screen parent;
    private final GameOptions settings;


    public JacOptionsScreen(Screen parent, GameOptions gameOptions) {
        super(new TranslatableText("jac.title"));
        this.parent = parent;
        this.settings = gameOptions;
    }

    @Override
    protected void init() {
        this.addDrawableChild(
                new ButtonWidget(
                    0,
                    0,
                    150,
                    20,
                    new TranslatableText("jac.test"),
                    buttonWidget -> {Jac.LOGGER.info("Button Clicked");}
                    )
                );
    }

}
