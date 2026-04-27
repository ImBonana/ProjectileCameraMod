package me.imbanana.projectilecamera.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.imbanana.projectilecamera.ProjectileCameraMod;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Projectile {
    public AbstractArrowMixin(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    @WrapWithCondition(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"
            )
    )
    private boolean shouldRenderParticle(Level instance, ParticleOptions particle, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        if (!ProjectileCameraMod.getCameraController().isTrackingProjectile(this)) return true;
        if (!Minecraft.getInstance().options.getCameraType().isFirstPerson()) return true;

        return false;
    }
}