package me.imbanana.projectilecamera;

import me.imbanana.projectilecamera.config.ModConfig;
import me.imbanana.projectilecamera.keymapping.ModKeyMapping;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectileCameraMod implements ClientModInitializer {
    public static final String MOD_ID = "projectile_camera";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static ModCameraController modCameraController;

    @Override
    public void onInitializeClient() {
        modCameraController = new ModCameraController();

        ModKeyMapping.registerKeyMapping();

        ModConfig.HANDLER.load();

        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (ModKeyMapping.toggleKey.consumeClick()) {
                ModConfig config = ModConfig.HANDLER.instance();
                config.setEnabled(!ModConfig.HANDLER.instance().isEnabled());
                ModConfig.HANDLER.save();

                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null) {
                    player.displayClientMessage(Component.translatable("msg." + MOD_ID + ".toggle_" + (config.isEnabled() ? "on" : "off")), true);
                }
            }

            modCameraController.tick();
        });
    }

    public static ModCameraController getCameraController() {
        return modCameraController;
    }

    public static Identifier idOf(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
