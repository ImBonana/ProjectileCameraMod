package me.imbanana.projectilecamera.neoforge;

import dev.isxander.yacl3.api.YetAnotherConfigLib;
import me.imbanana.projectilecamera.ProjectileCameraMod;
import me.imbanana.projectilecamera.config.ModConfigScreenFactory;
import me.imbanana.projectilecamera.keymapping.ModKeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.lifecycle.ClientStartedEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = ProjectileCameraMod.MOD_ID, dist = Dist.CLIENT)
public final class ProjectileCameraModNeoForge {
    public ProjectileCameraModNeoForge(IEventBus modBus) {
        // Run our common setup.
        NeoForge.EVENT_BUS.addListener(ProjectileCameraModNeoForge::onClientStart);

        NeoForge.EVENT_BUS.addListener(ProjectileCameraModNeoForge::onClientPostTick);

        modBus.addListener(ProjectileCameraModNeoForge::registerKeyMapping);

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (client, parent) -> ModConfigScreenFactory.buildConfigScreen(parent)
        );
    }

    private static void onClientStart(ClientStartedEvent event) {
        ProjectileCameraMod.init();
    }

    private static void onClientPostTick(ClientTickEvent.Post event) {
        ProjectileCameraMod.tickEvent();
    }

    private static void registerKeyMapping(RegisterKeyMappingsEvent event) {
        ModKeyMapping.registerModKeyMapping(event::register);
    }
}
