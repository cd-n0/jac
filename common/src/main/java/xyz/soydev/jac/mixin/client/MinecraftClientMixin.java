package xyz.soydev.jac.mixin.client;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.soydev.jac.Jac;
import xyz.soydev.jac.events.JacEventBus;
import xyz.soydev.jac.events.TickEvent;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {
    @Inject(at = @At("TAIL"), method = "tick")
    private void onTick(CallbackInfo info) {
        JacEventBus.get().post(new TickEvent());
    }
}
