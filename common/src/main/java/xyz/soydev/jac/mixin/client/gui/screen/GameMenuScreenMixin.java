package xyz.soydev.jac.mixin.client.gui.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.soydev.jac.screens.JacOptionsScreen;


@Mixin(PauseScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Component title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "createPauseMenu()V")
    private void additionalButtons(CallbackInfo ci) {
        this.addRenderableWidget(
                Button.builder(
                    Component.translatable("jac.menu"),
                    button -> {
                        minecraft.setScreen(new JacOptionsScreen(this, minecraft.options));
                    }
                    ).bounds(0, 0, 98, 20).build()
                );
    }
}
