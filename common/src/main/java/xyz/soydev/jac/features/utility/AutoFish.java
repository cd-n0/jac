package xyz.soydev.jac.features.utility;

import static xyz.soydev.jac.JacClient.instance;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import xyz.soydev.jac.events.JacSubscribe;
import xyz.soydev.jac.events.TickEvent;
import xyz.soydev.jac.mixin.entity.projectile.FishingBobberEntityAccessor;

public class AutoFish extends UtilityFeature {
    private boolean wasCaught;
    public AutoFish() {
        super(AutoFish.class.getSimpleName().toLowerCase(), true);
    }

    @JacSubscribe
    protected void onTick(TickEvent e) {
        if (instance.player == null) return;
        if (wasCaught) {
            castRod();
            wasCaught = false;
        }
        if (instance.player.fishing == null) return;
        if (((FishingBobberEntityAccessor) instance.player.fishing).jac$caughtFish()) {
            castRod();
            wasCaught = true;
        }
    }

    private void castRod() {
        if (instance.gameMode == null || instance.player == null) return;
        // Get rod hand
        InteractionHand rodHand = instance.player.getMainHandItem().getItem() ==
                Items.FISHING_ROD ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;

        // Cast rod using the rod hand
        instance.gameMode.useItem(instance.player, rodHand);
    }
}
