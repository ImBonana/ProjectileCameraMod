package me.imbanana.projectilecamera;

import me.imbanana.projectilecamera.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ProjectileCameraMod {
    public static final String MOD_ID = "projectile_camera";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static ModCameraController modCameraController;
    private static ModConfig modConfig;

    public static void init() {
        modCameraController = new ModCameraController();
    }

    public static void tickEvent() {
        modCameraController.tick();
    }

    public static ModCameraController getCameraController() {
        return modCameraController;
    }

    public static ModConfig getConfig() {
        if (modConfig == null) {
            modConfig = new ModConfig(
                    ModConfig.DEFAULT_TRACKABLE,
                    true,
                    true,
                    0.1f,
                    0.5f,
                    0.1f,
                    0.5f
            );
        }

        return modConfig;
    }
}
