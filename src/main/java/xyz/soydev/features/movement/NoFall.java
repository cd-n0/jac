package xyz.soydev.features.movement;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import xyz.soydev.Jac;

import static xyz.soydev.JacClient.instance;

public class NoFall extends MovementFeature {
    public NoFall() {
        super(NoFall.class.getSimpleName().toLowerCase());
        addToggleKeyBinding(GLFW.GLFW_KEY_PAGE_DOWN);
    }

    @Override
    protected void onTick() {
        final ClientPlayerEntity player = instance.player;
        if (player == null) return;

        float eps = Math.ulp(-0.f); // JVM epsilon
        Vec3d vel = player.getVelocity();
        float safeDist = -(player.getSafeFallDistance() - eps);

        if (player.fallDistance > safeDist) {
            if (vel.y < safeDist) {
                double scaling = safeDist / vel.y;
                player.setVelocity(vel.multiply(scaling));
            }
            //player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true, false));
            player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
        }
    }
}