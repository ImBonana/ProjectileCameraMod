package me.imbanana.projectilecamera.fabric;

import me.imbanana.projectilecamera.ProjectileCameraMod;
import net.fabricmc.api.ClientModInitializer;

public final class ProjectileCameraModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ProjectileCameraMod.init();
    }
}
