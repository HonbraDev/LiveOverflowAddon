package com.patsore.liveoverflowaddon.modules;

import com.patsore.liveoverflowaddon.Addon;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.CustomPlayerInput;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.injection.Inject;

public class WorldGuardBypass extends Module {
    public static boolean isWorldGuardBypassEnabled = false;
    public static ClientPlayNetworkHandler networkHandler;
    public static double MAX_DELTA = 0.05;

    public WorldGuardBypass() {
        super(Addon.CATEGORY, "WorldGuard Bypass", "A module to bypass worldguard protection");
    }

    @Override
    public void onActivate() {
        isWorldGuardBypassEnabled = true;
    }
    @EventHandler
    public void tick(TickEvent.Pre event) {
        networkHandler = mc.getNetworkHandler();
        assert mc.player != null;
        mc.player.setVelocity(0, 0, 0);

        Vec3d vec = new Vec3d(0, 0, 0);
        // Key presses changing position
        if (mc.player.input.jumping) {  // Move up
            vec = vec.add(new Vec3d(0, 1, 0));
        } else if (mc.player.input.sneaking) {  // Move down
            vec = vec.add(new Vec3d(0, -1, 0));
        } else {
            // Horizontal movement (not at the same time as vertical)
            if (mc.player.input.pressingForward) {
                vec = vec.add(new Vec3d(0, 0, 1));
            }
            if (mc.player.input.pressingRight) {
                vec = vec.add(new Vec3d(1, 0, 0));
            }
            if (mc.player.input.pressingBack) {
                vec = vec.add(new Vec3d(0, 0, -1));
            }
            if (mc.player.input.pressingLeft) {
                vec = vec.add(new Vec3d(-1, 0, 0));
            }
        }

        if (vec.length() > 0) {
            vec = vec.normalize();  // Normalize to length 1

            if (!(vec.x == 0 && vec.z == 0)) {  // Rotate by looking yaw (won't change length)
                double moveAngle = Math.atan2(vec.x, vec.z) + Math.toRadians(mc.player.getYaw() + 90);
                double x = Math.cos(moveAngle);
                double z = Math.sin(moveAngle);
                vec = new Vec3d(x, vec.y, z);
            }

            vec = vec.multiply(MAX_DELTA);  // Scale to maxDelta

            Vec3d newPos = new Vec3d(mc.player.getX() + vec.x, mc.player.getY() + vec.y, mc.player.getZ() + vec.z);

            // If able to add more without going over a block boundary, add more
            boolean extra = false;
            if (mc.options.sprintKey.isPressed()) {  // Trigger by sprinting
                while (inSameBlock(newPos.add(vec.multiply(1.5)), new Vec3d(mc.player.prevX, mc.player.prevY, mc.player.prevZ))) {
                    newPos = newPos.add(vec);
                    extra = true;
                }
            }

            mc.player.setPosition(newPos);
            sendMovementPackets(extra);
            // Send tiny movement so delta is small enough

        }
    }

    public void sendMovementPackets(boolean extra) {
        PlayerMoveC2SPacket.Full smallMovePacket = new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(),
            mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), mc.player.isOnGround());
        networkHandler.getConnection().send(smallMovePacket);

        // Send far away packet for "moving too quickly!" to reset position
        if (!extra) {
            PlayerMoveC2SPacket.Full farPacket = new PlayerMoveC2SPacket.Full(mc.player.getX() + 1337.0, mc.player.getY() + 1337.0,
                mc.player.getZ() + 1337.0, mc.player.getYaw(), mc.player.getPitch(), mc.player.isOnGround());
            networkHandler.getConnection().send(farPacket);
        }
    }


    public static boolean inSameBlock(Vec3d vector, Vec3d other) {
        return other.x >= Math.floor(vector.x) && other.x <= Math.ceil(vector.x) &&
            other.y >= Math.floor(vector.y) && other.y <= Math.ceil(vector.y) &&
            other.z >= Math.floor(vector.z) && other.z <= Math.ceil(vector.z);
    }


    @Override
    public void onDeactivate() {
        isWorldGuardBypassEnabled = false;
    }
}
