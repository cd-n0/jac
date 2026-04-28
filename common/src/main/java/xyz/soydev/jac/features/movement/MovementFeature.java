package xyz.soydev.jac.features.movement;

import xyz.soydev.jac.features.Feature;

public abstract class MovementFeature extends Feature {
    MovementFeature(String name) {
        super(name, "movement");
    }

    @Override
    protected void onFrame() {
    }
}
