package xyz.soydev.features;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import xyz.soydev.events.FrameEvent;
import xyz.soydev.events.TickEvent;
import xyz.soydev.features.movement.Fly;
import xyz.soydev.features.movement.NoFall;
import xyz.soydev.features.movement.Speed;
import xyz.soydev.features.utility.AutoFish;

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
                KeyBindingHelper.registerKeyBinding(keyBinding);
                FrameEvent.addEvent(keyBinding);
            }
        }
        TickEvent.addEvent(()->{onTick();});
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
