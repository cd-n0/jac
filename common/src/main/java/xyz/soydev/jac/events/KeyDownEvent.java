package xyz.soydev.jac.events;

public class KeyDownEvent extends JacEvent {
    public long pWindowPointer;
    public int pKey;
    public int pScanCode;
    public int pAction;
    public int pModifiers;

    public KeyDownEvent(long pWindowPointer, int pKey, int pScanCode, int pAction, int pModifiers) {
        this.pWindowPointer = pWindowPointer;
        this.pKey = pKey;
        this.pScanCode = pScanCode;
        this.pAction = pAction;
        this.pModifiers = pModifiers;
    }
}