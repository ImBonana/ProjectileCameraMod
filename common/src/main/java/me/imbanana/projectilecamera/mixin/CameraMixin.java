package me.imbanana.projectilecamera.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.imbanana.projectilecamera.ModCameraController;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.waypoints.TrackedWaypoint;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin implements TrackedWaypoint.Camera {

    @Shadow
    protected abstract void setRotation(float yRot, float xRot);

    @Shadow
    protected abstract void setPosition(Vec3 position);

    @Shadow
    private @Nullable Entity entity;

    @Inject(
            method = "alignWithEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Camera;setPosition(DDD)V",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            )
    )
    public void fixProjectileRotation(float partialTicks, CallbackInfo ci) {
        if (this.entity instanceof Projectile) {
            float xRot = this.entity.getViewXRot(partialTicks);
            float yRot = this.entity.getViewYRot(partialTicks);

            boolean detached = !Minecraft.getInstance().options.getCameraType().isFirstPerson();
            boolean mirror = Minecraft.getInstance().options.getCameraType().isMirrored();

            float newYRot = detached && mirror ? 180.0f - yRot : -yRot;
            float newXRot = detached && mirror ? xRot : -xRot;

            Vec3 smoothedPosition = ModCameraController.ROTATION_SMOOTH.smooth(
                    new Vec3(newXRot, newYRot, 0),
                    vec3 -> new Vec3(vec3.x, Mth.wrapDegrees(vec3.y), vec3.z)
            );

            this.setRotation((float) smoothedPosition.y, (float) smoothedPosition.x);

            return;
        }

        ModCameraController.ROTATION_SMOOTH.reset();
    }

    @ModifyExpressionValue(
            method = "alignWithEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/CameraType;isMirrored()Z",
                    ordinal = 0
            )
    )
    private boolean disableForProjectile(boolean original) {
        if (this.entity instanceof Projectile) return false;

        return original;
    }

    @WrapWithCondition(
            method = "alignWithEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Camera;setPosition(DDD)V",
                    ordinal = 0
            )
    )
    public boolean fixProjectilePosition(Camera instance, double x, double y, double z) {
        if (instance.entity() instanceof Projectile) {
            this.setPosition(ModCameraController.MOVEMENT_SMOOTH.smooth(new Vec3(x, y, z)));
            return false;
        }

        ModCameraController.MOVEMENT_SMOOTH.reset();
        return true;
    }
}
