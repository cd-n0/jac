package xyz.soydev.mixin.client.util;

import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.soydev.events.FrameEvent;

@Mixin(Window.class)
public class WindowMixin {
    @Inject(at = @At("TAIL"), method = "swapBuffers")
    void onFrame(CallbackInfo ci) {
        FrameEvent.onFrame();
    }
}
