package xyz.soydev.features.movement;

import xyz.soydev.features.Feature;

public abstract class MovementFeature extends Feature {
    MovementFeature(String name) {
        super(name, "movement");
    }

    @Override
    protected void onFrame() {
    }
}
