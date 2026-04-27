package me.imbanana.projectilecamera;

import com.mojang.blaze3d.platform.InputConstants;
import me.imbanana.projectilecamera.config.ModConfig;
import me.imbanana.projectilecamera.util.CameraSmoothing;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

public class ModCameraController {
    private Minecraft client;
    private ModConfig config;

    private Projectile trackedProjectile;
    private Vec3 anchorPos;
    private boolean hideGuiState = false;

    public static final CameraSmoothing ROTATION_SMOOTH = new CameraSmoothing(0.1f, 0.5f);
    public static final CameraSmoothing MOVEMENT_SMOOTH = new CameraSmoothing(0.1f, 0.5f);

    public ModCameraController() {
        this.client = Minecraft.getInstance();
        this.config = ProjectileCameraMod.getConfig();
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

        if (this.anchorPos != null && this.client.player != null && this.anchorPos.distanceToSqr(client.player.position()) > 1.0E-6 && this.config.isShouldStopWhenMoved()) {
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
        ) && this.config.isShouldStopWhenKeypress()) {
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

        if (this.config.getTrackableEntities().stream().noneMatch(entityType -> entityType == projectile.getType())) return false;

        return true;
    }

    public boolean isTrackingProjectile() {
        return trackedProjectile != null && this.trackedProjectile.isAlive();
    }

    public boolean isTrackingProjectile(Projectile projectile) {
        return projectile != null && this.trackedProjectile != null && projectile.getId() == this.trackedProjectile.getId();
    }

    public static void updateCameraSmoothing(ModConfig config) {
        ROTATION_SMOOTH.setStiffness(config.getRotationSmoothStiffness());
        ROTATION_SMOOTH.setDamping(config.getRotationSmoothDamping());
        MOVEMENT_SMOOTH.setStiffness(config.getMovementSmoothStiffness());
        MOVEMENT_SMOOTH.setDamping(config.getMovementSmoothDamping());
    }
}
