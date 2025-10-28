package xyz.soydev.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    // This code is injected into the start of MinecraftServer.loadWorld()V
    @Inject(at = @At("HEAD"), method = "loadWorld")
    private void init(CallbackInfo info) {
        xyz.soydev.Jac.LOGGER.info("MinecraftServer.loadWorld() mixin head");
    }
    @Inject(at = @At("TAIL"), method = "loadWorld")
    private void deinit(CallbackInfo info) {
        xyz.soydev.Jac.LOGGER.info("MinecraftServer.loadWorld() mixin tail");
    }
}
