package xyz.soydev.jac.keymap;

import net.minecraft.client.KeyMapping;
import xyz.soydev.jac.Jac;
import xyz.soydev.jac.events.*;

public class JacKeyMapping extends KeyMapping {

    private JacKeyMappingCallback callback = () -> {
        Jac.LOGGER.warn("No callback set for for key binding " + this.saveString());
    };
    public JacKeyMapping(String string, int glfwKey, String string2) {
        super(string, glfwKey, string2);
        JacEventBus.get().register(this);
    }

    /**
     * Runs once whenever the keymap is pressed
     * @param callback
     */
    public void setCallback(JacKeyMappingCallback callback) {
        this.callback = callback;
    }

    @JacSubscribe
    void onKeyPress(KeyPressEvent e) {
        if (!matches(e.pKey, e.pScanCode)) return;
        if (consumeClick()) callback.callback();
    }
}
