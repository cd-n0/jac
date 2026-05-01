package xyz.soydev.jac.features.utility;

import xyz.soydev.jac.features.Feature;

public abstract class UtilityFeature extends Feature {
    UtilityFeature(String name, boolean enabled) {
        super(name, "utility", enabled);
    }
}
