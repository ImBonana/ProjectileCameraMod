package me.imbanana.projectilecamera;

import com.mojang.blaze3d.platform.InputConstants;
import me.imbanana.projectilecamera.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

public class ModCameraController {
    private Minecraft client;
    
    private Projectile trackedProjectile;
    private Vec3 anchorPos;
    private boolean hideGuiState = false;

    public ModCameraController() {
        this.client = Minecraft.getInstance();
    }

    public void startTracking(Projectile projectile) {
        if (projectile == null || this.client.player == null && !projectile.isAlive()) return;
        
        this.anchorPos = this.client.player.position();
        this.trackedProjectile = projectile;
        this.hideGuiState = this.client.options.hideGui;

        this.client.options.hideGui = true;
        this.client.setCameraEntity(projectile);
    }
    
    public void stopTracking() {
        this.trackedProjectile = null;
        this.client.options.hideGui = this.hideGuiState;
        this.client.setCameraEntity(this.client.player);
    }
    
    public void tick() {
        if (this.trackedProjectile == null) return;

        if (!this.trackedProjectile.isAlive()) {
            stopTracking();
            return;
        }
        
        if (this.anchorPos != null && this.client.player != null && this.anchorPos.distanceToSqr(client.player.position()) > 1.0E-6 && ModConfig.SHOULD_STOP_WHEN_MOVED) {
            stopTracking();
            return;
        }
        
        if ((
                this.client.options.keyUp.isDown()
                || this.client.options.keyDown.isDown()
                || this.client.options.keyLeft.isDown()
                || this.client.options.keyRight.isDown()
                || this.client.options.keyJump.isDown()
                || this.client.options.keyShift.isDown()
                || this.client.options.keyDrop.isDown()
        ) && ModConfig.SHOULD_STOP_WHEN_KEYPRESS) {
            stopTracking();
            return;
        }

        if (InputConstants.isKeyDown(this.client.getWindow(), GLFW.GLFW_KEY_ESCAPE)) {
            stopTracking();
            return;
        }
    }
    
    public boolean canTrack(Projectile projectile) {
        if (!projectile.isAlive()) return false;
        if (projectile.getOwner() != null && this.client.player != null && projectile.getOwner().getId() != this.client.player.getId()) return false;

        if (ModConfig.TRACKABLE.stream().noneMatch(entityType -> entityType == projectile.getType())) return false;

        return true;
    }

    public boolean isTrackingProjectile() {
        return trackedProjectile != null && this.trackedProjectile.isAlive();
    }

    public boolean isTrackingProjectile(Projectile projectile) {
        return projectile != null && this.trackedProjectile != null && projectile.getId() == this.trackedProjectile.getId();
    }
}
