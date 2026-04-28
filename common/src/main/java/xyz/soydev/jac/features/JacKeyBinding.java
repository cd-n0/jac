package xyz.soydev.jac.features;

import net.minecraft.client.KeyMapping;
import xyz.soydev.jac.Jac;
import xyz.soydev.jac.events.JacEvent;

public class JacKeyBinding extends KeyMapping implements JacEvent {
    private boolean wasPressed = false;
    /* Ran at every tick while key is held */
    private Runnable callback = () -> {
        Jac.LOGGER.warn("No callback set for for key binding " + this.saveString());
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
        if (isDown()) {
            callback.run();
        }
        if (isDown() && !wasPressed)  wasPressed = true;
        if (!isDown()) wasPressed = false;
    }
}
