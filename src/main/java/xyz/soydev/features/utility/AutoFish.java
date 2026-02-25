package xyz.soydev.features.utility;

import static xyz.soydev.JacClient.instance;

import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import xyz.soydev.mixin.entity.projectile.FishingBobberEntityAccessor;

public class AutoFish extends UtilityFeature {
    private boolean wasCaught;
    public AutoFish() {
        super(AutoFish.class.getSimpleName().toLowerCase());
    }

    @Override
    protected void onTick() {
        if (instance.player == null) return;
        if (wasCaught) {
            castRod();
            wasCaught = false;
        };
        if (instance.player.fishHook == null) return;
        if (((FishingBobberEntityAccessor) instance.player.fishHook).jac$caughtFish()) {
            castRod();
            wasCaught = true;
        }
    }

    private void castRod() {
        instance.interactionManager.interactItem(instance.player, getRodHand());
    }

    private Hand getRodHand(){
        return instance.player.getMainHandStack().getItem() == Items.FISHING_ROD ? Hand.MAIN_HAND : Hand.OFF_HAND;
    }
}
