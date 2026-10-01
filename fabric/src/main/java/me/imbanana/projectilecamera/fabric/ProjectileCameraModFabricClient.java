package me.imbanana.projectilecamera.fabric;

import me.imbanana.projectilecamera.ProjectileCameraMod;
import me.imbanana.projectilecamera.keymapping.ModKeyMapping;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

public final class ProjectileCameraModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModKeyMapping.registerModKeyMapping(KeyMappingHelper::registerKeyMapping);

        ProjectileCameraMod.init();

        ClientTickEvents.END_CLIENT_TICK.register((minecraft) -> ProjectileCameraMod.tickEvent());
        ClientEntityEvents.ENTITY_LOAD.register(ProjectileCameraMod::entityLoadEvent);
        UseItemCallback.EVENT.register(ProjectileCameraMod::playerItemUseEvent);
    }
}
