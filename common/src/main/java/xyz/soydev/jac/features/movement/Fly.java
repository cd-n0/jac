package xyz.soydev.jac.features.movement;

import org.lwjgl.glfw.GLFW;

import static xyz.soydev.jac.JacClient.instance;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;
import xyz.soydev.jac.events.JacSubscribe;
import xyz.soydev.jac.events.TickEvent;

public class Fly extends MovementFeature {
    private final OptionInstance<Double> flySpeedOption;
    private final OptionInstance<Integer> flyAntiKickTickFrequency;
    public Fly() {
        super(Fly.class.getSimpleName().toLowerCase(), false);
        flySpeedOption = addDoubleOption("flyspeed", 1, 0, 10);
        flyAntiKickTickFrequency = addIntegerOption("flyantikicktickfrequency", 20, 1, 19);
        addToggleKeyBinding(GLFW.GLFW_KEY_PAGE_UP);
    }

    private boolean antiFlyKick(LocalPlayer player) {
        float eps = .05f;
        int modulo = flyAntiKickTickFrequency.get() + 1;
        assert(modulo >= 2);
        switch (player.tickCount % modulo) {
            case 0: {
                player.push(0, eps, 0);
            } return true;
            case 1: {
                player.push(0, -eps, 0);
            } // Fallthrough
            default: {
                return false;
            }
        }
    }

    @JacSubscribe
    protected void onTick(TickEvent e) {
        LocalPlayer player = instance.player;
        if (player == null) return;
        Options options = instance.options;
        double newX = 0;
        double newY = 0;
        double newZ = 0;

        double flySpeed = flySpeedOption.get();
        if (options.keyUp.isDown()) newX += flySpeed;
        if (options.keyDown.isDown()) newX -= flySpeed;
        if (options.keyJump.isDown()) newY += flySpeed;
        if (options.keyShift.isDown()) newY -= flySpeed;
        if (options.keyRight.isDown()) newZ += flySpeed;
        if (options.keyLeft.isDown()) newZ -= flySpeed;


        final float yaw = (player.getYRot() + 90) % 360;
        final float pitch = player.getXRot() % 360;

        final double theta = -Math.toRadians(yaw);
        final double cosTheta = Math.cos(theta);
        final double sinTheta = Math.sin(theta);
        final double gamma = Math.toRadians(pitch);
        final double cosGamma = Math.cos(gamma);
        final double sinGamma = Math.sin(gamma);

        // Variable to assign the rotated value of x before using the original value for the second calculation
        double rotatedX;
        // Rotate around Z
        rotatedX = newX * cosGamma + newY * sinGamma;
        newY = newY * cosGamma - newX * sinGamma;
        newX = rotatedX;

        // Rotate around Y
        rotatedX = newX * cosTheta + newZ * sinTheta;
        newZ = newZ * cosTheta - newX * sinTheta;
        newX = rotatedX;

        player.setDeltaMovement(Vec3.ZERO);
        if (antiFlyKick(player)) newY = 0;
        player.push(newX, newY, newZ);
    }
}
