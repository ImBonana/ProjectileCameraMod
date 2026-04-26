package me.imbanana.projectilecamera;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectileCameraMod implements ClientModInitializer {
    public static final String MOD_ID = "projectile_camera";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static ModCameraController modCameraController;

    @Override
    public void onInitializeClient() {
        modCameraController = new ModCameraController();

        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> modCameraController.tick());
    }

    public static ModCameraController getCameraController() {
        return modCameraController;
    }
}
