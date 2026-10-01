package me.imbanana.projectilecamera.neoforge;

import me.imbanana.projectilecamera.ProjectileCameraMod;
import me.imbanana.projectilecamera.config.ModConfigScreenFactory;
import me.imbanana.projectilecamera.keymapping.ModKeyMapping;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.lifecycle.ClientStartedEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@Mod(value = ProjectileCameraMod.MOD_ID, dist = Dist.CLIENT)
public final class ProjectileCameraModNeoForge {
    public ProjectileCameraModNeoForge(IEventBus modBus) {
        // Run our common setup.
        NeoForge.EVENT_BUS.addListener(ProjectileCameraModNeoForge::onClientStart);
        NeoForge.EVENT_BUS.addListener(ProjectileCameraModNeoForge::onClientPostTick);
        NeoForge.EVENT_BUS.addListener(ProjectileCameraModNeoForge::onEntityJointLevel);
        NeoForge.EVENT_BUS.addListener(ProjectileCameraModNeoForge::onItemUse);

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

    private static void onEntityJointLevel(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) return;
        ProjectileCameraMod.entityLoadEvent(event.getEntity(), (ClientLevel) event.getLevel());
    }

    private static void onItemUse(PlayerInteractEvent.RightClickItem event) {
        ProjectileCameraMod.playerItemUseEvent(event.getEntity(), event.getLevel(), event.getHand());
    }
}
