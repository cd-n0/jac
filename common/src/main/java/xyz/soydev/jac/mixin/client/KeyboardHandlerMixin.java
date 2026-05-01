package xyz.soydev.jac.mixin.client;

import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.soydev.jac.events.JacEventBus;
import xyz.soydev.jac.events.KeyDownEvent;
import xyz.soydev.jac.events.KeyPressEvent;


// TODO: Find release
@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    @Inject(at = @At("TAIL"), method = "keyPress")
    void onKeyPress(long pWindowPointer, int pKey, int pScanCode, int pAction, int pModifiers, CallbackInfo ci) {
        JacEventBus.get().post(new KeyDownEvent(pWindowPointer, pKey, pScanCode, pAction, pModifiers));
        if (pAction == 1) {
            JacEventBus.get().post(new KeyPressEvent(pWindowPointer, pKey, pScanCode, pAction, pModifiers));
        }
    }
}