package xyz.soydev.features.utility;

import xyz.soydev.features.Feature;

public abstract class UtilityFeature extends Feature {
    UtilityFeature(String name) {
        super(name, "utility");
    }

    @Override
    protected void onFrame() {
    }
}
