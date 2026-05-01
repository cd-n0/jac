package xyz.soydev.jac.events;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class JacEventBus {
    // Map: event parameter type -> list of subscriber method handles
    private final Map<Class<?>, List<JacSubscriber>> subscribers = new ConcurrentHashMap<>();

    private static final JacEventBus INSTANCE = new JacEventBus();
    public static JacEventBus get() { return INSTANCE; }

    private JacEventBus() {}

    public void register(Object listener) {
        if (listener == null) return;
        Class<?> cls = listener.getClass();
        for (Method m : cls.getDeclaredMethods()) {
            if (!m.isAnnotationPresent(JacSubscribe.class)) continue;

            m.setAccessible(true);
            Class<?>[] params = m.getParameterTypes();
            if (params.length > 1) {
                throw new IllegalArgumentException("Subscriber methods must have 0 or 1 parameter: " + m);
            }

            Class<?> key = params.length == 0 ? Void.TYPE : params[0];
            JacSubscriber sub = new JacSubscriber(listener, m, key);
            subscribers.computeIfAbsent(key, k -> new CopyOnWriteArrayList<>()).add(sub);
        }
    }

    public void unregister(Object listener) {
        if (listener == null) return;
        for (List<JacSubscriber> list : subscribers.values()) {
            list.removeIf(s -> s.listener() == listener);
        }
    }

    /**
     * Post an event object. Matches methods with parameter type assignable from event.getClass()
     * Also calls subscribers with zero-arg methods.
     */
    public void post(Object event) {
        // Call zero-arg subscribers (key == Void.TYPE)
        List<JacSubscriber> noArg = subscribers.get(Void.TYPE);
        if (noArg != null) {
            for (JacSubscriber s : noArg) s.invoke(event);
        }

        if (event == null) return;
        Class<?> evClass = event.getClass();

        // For all registered parameter types, call those where param.isAssignableFrom(evClass)
        for (Map.Entry<Class<?>, List<JacSubscriber>> e : subscribers.entrySet()) {
            Class<?> paramType = e.getKey();
            if (paramType == Void.TYPE) continue;
            if (paramType.isAssignableFrom(evClass)) {
                for (JacSubscriber s : e.getValue()) s.invoke(event);
            }
        }
    }
}