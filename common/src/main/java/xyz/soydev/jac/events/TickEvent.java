package xyz.soydev.jac.events;

import java.util.ArrayList;
import java.util.List;

public class TickEvent {
    static final List<JacEvent> onTickEvents = new ArrayList<>();

    public static void addEvent(JacEvent event) {
        onTickEvents.add(event);
    }

    public static void onTick() {
        for (JacEvent event : onTickEvents) {
            event.event();
        }
    }
}
