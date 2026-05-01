package xyz.soydev.jac.features.movement;

import org.lwjgl.glfw.GLFW;

import static xyz.soydev.jac.JacClient.instance;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.world.phys.Vec3;
import xyz.soydev.jac.events.JacSubscribe;
import xyz.soydev.jac.events.TickEvent;

public class NoFall extends MovementFeature {
    public NoFall() {
        super(NoFall.class.getSimpleName().toLowerCase(), true);
        addToggleKeyBinding(GLFW.GLFW_KEY_PAGE_DOWN);
    }

    @JacSubscribe
    protected void onTick(TickEvent e) {
        final LocalPlayer player = instance.player;
        if (player == null) return;

        float eps = Math.ulp(-0.f); // JVM epsilon
        Vec3 vel = player.getDeltaMovement();
        float safeDist = -(player.getMaxFallDistance() - eps);

        if (player.fallDistance > safeDist) {
            if (vel.y < safeDist) {
                double scaling = safeDist / vel.y;
                player.setDeltaMovement(vel.scale(scaling));
            }

            player.connection.send(new ServerboundMovePlayerPacket.StatusOnly(true));
        }
    }
}