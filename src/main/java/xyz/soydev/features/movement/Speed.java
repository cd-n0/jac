package xyz.soydev.features.movement;

import net.minecraft.client.network.ClientPlayerEntity;
import static xyz.soydev.JacClient.instance;

public class Speed extends MovementFeature {

    public Speed() {
        super(Speed.class.getSimpleName().toLowerCase());
    }

    @Override
    protected void onTick() {
        ClientPlayerEntity player = instance.player;
    }
}
