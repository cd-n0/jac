package xyz.soydev.jac.mixin.entity;

//import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin {
    //    https://old.reddit.com/r/fabricmc/comments/1ivk42f/issue_with_falldistance_always_is_zero/mq3ih6s/
//    @ModifyExpressionValue(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isControlledByPlayer()Z"))
//    private boolean fixFallDistanceCalculation(boolean original) {
//        if ((Object) this == MinecraftClient.getInstance().player) {
//            return false;
//        }
//        return original;
//    }
}
