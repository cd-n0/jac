package xyz.soydev.jac.mixin.client.util;

import com.mojang.blaze3d.platform.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.soydev.jac.events.FrameEvent;
import xyz.soydev.jac.events.JacEventBus;

@Mixin(Window.class)
public class WindowMixin {
    @Inject(at = @At("TAIL"), method = "updateDisplay")
    void onFrame(CallbackInfo ci) {
        JacEventBus.get().post(new FrameEvent());
    }
}
