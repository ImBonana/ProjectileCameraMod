package me.imbanana.projectilecamera.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Camera;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.waypoints.TrackedWaypoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Camera.class)
public abstract class CameraMixin implements TrackedWaypoint.Camera {
    @Shadow
    protected abstract void setRotation(float yRot, float xRot);


    @Shadow
    protected abstract void setPosition(Vec3 pos);

    private static class RotationSmooth {
        @Unique
        private static float smoothYRot = 0;
        @Unique
        private static float smoothXRot = 0;
        @Unique
        private static float yRotVelocity = 0;
        @Unique
        private static float xRotVelocity = 0;
        @Unique
        private static boolean smoothInitialized = false;

        @Unique
        private static final float STIFFNESS = 0.1f; // lower = longer delay
        @Unique
        private static final float DAMPING = 0.5f; // higher = more rigid

    }

    private static class MovementSmooth {
        @Unique
        private static Vec3 smooth = Vec3.ZERO;
        @Unique
        private static Vec3 velocity = Vec3.ZERO;
        @Unique
        private static boolean smoothInitialized = false;

        @Unique
        private static final float STIFFNESS = 0.1f; // lower = longer delay
        @Unique
        private static final float DAMPING = 0.5f; // higher = more rigid

    }

    @WrapWithCondition(
            method = "setup",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Camera;setRotation(FF)V",
                    ordinal = 1
            )
    )
    public boolean fixProjectileRotation(Camera instance, float yRot, float xRot, @Local(argsOnly = true, name = "detached") boolean detached, @Local(argsOnly = true, name = "mirror") boolean mirror) {
        if (instance.entity() instanceof Projectile) {
            float newYRot = detached && mirror ? 180.0f - yRot : -yRot;
            float newXRot = detached && mirror ? xRot : -xRot;

            if (!RotationSmooth.smoothInitialized) {
                RotationSmooth.smoothYRot = newYRot;
                RotationSmooth.smoothXRot = newXRot;
                RotationSmooth.smoothInitialized = true;
            }

            float yDiff = Mth.wrapDegrees(newYRot - RotationSmooth.smoothYRot);
            float xDiff = newXRot - RotationSmooth.smoothXRot;

            RotationSmooth.yRotVelocity += yDiff * RotationSmooth.STIFFNESS;
            RotationSmooth.xRotVelocity += xDiff * RotationSmooth.STIFFNESS;

            RotationSmooth.yRotVelocity *= RotationSmooth.DAMPING;
            RotationSmooth.xRotVelocity *= RotationSmooth.DAMPING;

            RotationSmooth.smoothYRot += RotationSmooth.yRotVelocity;
            RotationSmooth.smoothXRot += RotationSmooth.xRotVelocity;

            this.setRotation(RotationSmooth.smoothYRot, RotationSmooth.smoothXRot);

            return false;
        }

        RotationSmooth.smoothInitialized = false;
        RotationSmooth.yRotVelocity = 0;
        RotationSmooth.xRotVelocity = 0;

        return true;
    }

    @WrapWithCondition(
            method = "setup",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Camera;setRotation(FF)V",
                    ordinal = 2
            )
    )
    private boolean disableForProjectile(Camera instance, float yRot, float xRot) {
        return !(instance.entity() instanceof Projectile);
    }

    @WrapWithCondition(
            method = "setup",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Camera;setPosition(DDD)V",
                    ordinal = 0
            )
    )
    public boolean fixProjectilePosition(Camera instance, double x, double y, double z) {
        if (instance.entity() instanceof Projectile) {

            if (!MovementSmooth.smoothInitialized) {
                MovementSmooth.smoothInitialized = true;
                MovementSmooth.smooth = new Vec3(x, y, z);
            } else {
                Vec3 diff = new Vec3(x, y, z).subtract(MovementSmooth.smooth);

                MovementSmooth.velocity = MovementSmooth.velocity
                        .add(diff.scale(MovementSmooth.STIFFNESS))
                        .scale(MovementSmooth.DAMPING);

                MovementSmooth.smooth = MovementSmooth.smooth.add(MovementSmooth.velocity);
            }

            this.setPosition(MovementSmooth.smooth);

            return false;
        }

        MovementSmooth.smoothInitialized = false;
        MovementSmooth.velocity = Vec3.ZERO;

        return true;
    }
}
