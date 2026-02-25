package xyz.soydev.events;

import java.util.ArrayList;
import java.util.List;

public class FrameEvent {
    static final List<JacEvent> onFrameEvents = new ArrayList<>();

    public static void addEvent(JacEvent event) {
        onFrameEvents.add(event);
    }

    public static void onFrame() {
        for (JacEvent event : onFrameEvents) {
            event.event();
        }
    }
}
