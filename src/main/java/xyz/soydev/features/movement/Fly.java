package xyz.soydev.features.movement;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.lwjgl.glfw.GLFW;
import net.minecraft.util.math.Vec3d;

import static xyz.soydev.JacClient.instance;

public class Fly extends MovementFeature {
    private final SimpleOption<Double> flySpeedOption;
    private final SimpleOption<Integer> flyAntiKickTickFrequency;
    public Fly() {
        super(Fly.class.getSimpleName().toLowerCase());
        flySpeedOption = addDoubleOption("flyspeed", 1, 0, 10);
        flyAntiKickTickFrequency = addIntegerOption("flyantikicktickfrequency", 20, 1, 19);
        addToggleKeyBinding(GLFW.GLFW_KEY_PAGE_UP);
    }

    private boolean antiFlyKick(ClientPlayerEntity player) {
        float eps = .05f;
        int modulo = flyAntiKickTickFrequency.getValue() + 1;
        assert(modulo >= 2);
        switch (player.age % modulo) {
            case 0: {
                player.addVelocity(0, eps, 0);
            } return true;
            case 1: {
                player.addVelocity(0, -eps, 0);
            } // Fallthrough
            default: return false;
        }
    }

    @Override
    protected void onTick() {
        ClientPlayerEntity player = instance.player;
        if (player == null) return;
        GameOptions options = instance.options;
        double newX = 0;
        double newY = 0;
        double newZ = 0;

        double flySpeed = flySpeedOption.getValue();
        if (options.forwardKey.isPressed()) newX += flySpeed;
        if (options.backKey.isPressed()) newX -= flySpeed;
        if (options.jumpKey.isPressed()) newY += flySpeed;
        if (options.sneakKey.isPressed()) newY -= flySpeed;
        if (options.rightKey.isPressed()) newZ += flySpeed;
        if (options.leftKey.isPressed()) newZ -= flySpeed;


        final float yaw = (player.getYaw() + 90) % 360;
        final float pitch = player.getPitch() % 360;

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

        player.setVelocity(Vec3d.ZERO);
        if (antiFlyKick(player)) newY = 0;
        player.addVelocity(newX, newY, newZ);
    }
}
