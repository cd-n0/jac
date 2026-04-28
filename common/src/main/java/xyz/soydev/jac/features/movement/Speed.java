package xyz.soydev.jac.features.movement;

import static xyz.soydev.jac.JacClient.instance;

import net.minecraft.client.player.LocalPlayer;

public class Speed extends MovementFeature {

    public Speed() {
        super(Speed.class.getSimpleName().toLowerCase());
    }

    @Override
    protected void onTick() {
        LocalPlayer player = instance.player;
    }
}
