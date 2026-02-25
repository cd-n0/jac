package xyz.soydev.mixin.client;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.soydev.events.TickEvent;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(at = @At("TAIL"), method = "tick")
    private void onTick(CallbackInfo info) {
        TickEvent.onTick();
    }
}
