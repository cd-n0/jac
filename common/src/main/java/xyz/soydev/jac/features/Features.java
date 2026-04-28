package xyz.soydev.jac.features;

import xyz.soydev.jac.events.FrameEvent;
import xyz.soydev.jac.events.TickEvent;
import xyz.soydev.jac.features.movement.Fly;
import xyz.soydev.jac.features.movement.NoFall;
import xyz.soydev.jac.features.movement.Speed;
import xyz.soydev.jac.features.utility.AutoFish;

import java.util.ArrayList;
import java.util.List;

public class Features {
    private final List<Feature> features = new ArrayList<>();
    public void addFeature(Feature feature) {
        features.add(feature);
    }

    public void init() {
        Fly fly = new Fly();
        Speed speed = new Speed();
        NoFall noFall = new NoFall();
        AutoFish autoFish = new AutoFish();
        addFeature(fly);
        addFeature(speed);
        addFeature(noFall);
        addFeature(autoFish);

        for (Feature feature : features) {
            List<JacKeyBinding> keyBindings = feature.getKeyBindings();
            for (JacKeyBinding keyBinding: keyBindings) {
                //TODO: For both platforms
                // KeyBindingHelper.registerKeyBinding(keyBinding);
                FrameEvent.addEvent(keyBinding);
            }
        }
        TickEvent.addEvent(() -> {
            onTick();
        });
    }

    public void onTick() {
        for (Feature feature : features) {
            if (feature.isEnabled()) feature.onTick();
        }
    }

    public void onFrame() {
        for (Feature feature : features) {
            if (feature.isEnabled()) feature.onFrame();
        }
    }

    public List<Feature> getFeatures() {
        return features;
    }
}
