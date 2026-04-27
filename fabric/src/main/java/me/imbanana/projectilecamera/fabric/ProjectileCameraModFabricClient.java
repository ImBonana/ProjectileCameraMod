package me.imbanana.projectilecamera.fabric;

import me.imbanana.projectilecamera.ProjectileCameraMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public final class ProjectileCameraModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ProjectileCameraMod.init();

        ClientTickEvents.END_CLIENT_TICK.register((minecraft) -> ProjectileCameraMod.tickEvent());
    }
}
