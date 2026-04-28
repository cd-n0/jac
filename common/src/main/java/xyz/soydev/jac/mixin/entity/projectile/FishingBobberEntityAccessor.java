package xyz.soydev.jac.mixin.entity.projectile;

import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FishingHook.class)
public interface FishingBobberEntityAccessor {
    @Accessor("biting")
    boolean jac$caughtFish();
}
