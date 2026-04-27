package me.imbanana.projectilecamera.mixin;

import me.imbanana.projectilecamera.ProjectileCameraMod;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public abstract class ProjectileMixin extends Entity implements TraceableEntity {
    public ProjectileMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "onHitEntity",
            at = @At("HEAD")
    )
    private void injectOnHitEntity(EntityHitResult hitResult, CallbackInfo ci) {
        if (!ProjectileCameraMod.getCameraController().isTrackingProjectile((Projectile) (Object) this)) return;
        ProjectileCameraMod.getCameraController().stopTracking();
    }

    @Inject(
            method = "onHitBlock",
            at = @At("HEAD")
    )
    private void injectOnHitBlock(BlockHitResult hitResult, CallbackInfo ci) {
        if (!ProjectileCameraMod.getCameraController().isTrackingProjectile((Projectile) (Object) this)) return;
        ProjectileCameraMod.getCameraController().stopTracking();
    }

    @Inject(
            method = "applyOnProjectileSpawned",
            at = @At("TAIL")
    )
    private void applyOnProjectileSpawned(ServerLevel level, ItemStack spawnedFrom, CallbackInfo ci) {
        Projectile thiz = (Projectile) (Object) this;

        if (!ProjectileCameraMod.getCameraController().canTrack(thiz)) return;

        ProjectileCameraMod.getCameraController().startTracking(thiz);
    }
}
