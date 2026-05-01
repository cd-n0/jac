package xyz.soydev.jac.features.movement;

import static xyz.soydev.jac.JacClient.instance;

import net.minecraft.client.player.LocalPlayer;
import xyz.soydev.jac.events.JacSubscribe;
import xyz.soydev.jac.events.TickEvent;

public class Speed extends MovementFeature {

    public Speed() {
        super(Speed.class.getSimpleName().toLowerCase(), false);
    }

    @JacSubscribe
    protected void onTick(TickEvent e) {
        LocalPlayer player = instance.player;
    }
}
