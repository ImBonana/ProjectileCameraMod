package me.imbanana.projectilecamera.mixin;

import me.imbanana.projectilecamera.ProjectileCameraMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {
    @Inject(
            method = "shouldRender",
            at = @At("HEAD"),
            cancellable = true
    )
    private void hideTrackedProjectile(T entity, Frustum culler, double camX, double camY, double camZ, CallbackInfoReturnable<Boolean> cir) {
        if (!(entity instanceof Projectile projectile)) return;
        if (!ProjectileCameraMod.getCameraController().isTrackingProjectile(projectile)) return;
        if (!Minecraft.getInstance().options.getCameraType().isFirstPerson()) return;

        cir.setReturnValue(true);
    }
}