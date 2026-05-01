package xyz.soydev.jac.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @param paramType Void.TYPE if none
 */
record JacSubscriber(Object listener, Method method, Class<?> paramType) {
    void invoke(Object event) {
        try {
            if (paramType == Void.TYPE) {
                method.invoke(listener);
            } else {
                method.invoke(listener, event);
            }
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException("Error invoking subscriber " + method, ex);
        }
    }
}
