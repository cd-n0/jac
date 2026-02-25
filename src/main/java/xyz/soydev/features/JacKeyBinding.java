package xyz.soydev.features;

import net.minecraft.client.option.KeyBinding;
import xyz.soydev.Jac;
import xyz.soydev.events.JacEvent;

public class JacKeyBinding extends KeyBinding implements JacEvent {
    private boolean wasPressed = false;
    /* Ran at every tick while key is held */
    private Runnable callback = () -> {
        Jac.LOGGER.warn("No callback set for for key binding " + this.getBoundKeyTranslationKey());
    };
    public JacKeyBinding(String string, int glfwKey, String string2) {
        super(string, glfwKey, string2);
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

    public boolean repeat() {
        return wasPressed;
    }

    @Override
    public void event() {
        if (isPressed()) {
            callback.run();
        }
        if (isPressed() && !wasPressed)  wasPressed = true;
        if (!isPressed()) wasPressed = false;
    }
}
