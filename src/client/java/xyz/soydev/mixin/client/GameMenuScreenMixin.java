package xyz.soydev.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.client.gui.screen.Screen;
import xyz.soydev.JacOptionsScreen;


@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "initWidgets()V")
    private void additionalButtons(CallbackInfo ci) {
        this.addDrawableChild(
                new ButtonWidget(
                    0,
                    0,
                    98,
                    20,
                    new TranslatableText("menu." + xyz.soydev.Jac.MOD_ID),
                    button -> this.client.setScreen(new JacOptionsScreen(this, this.client.options))
                    )
                );

    }
}
