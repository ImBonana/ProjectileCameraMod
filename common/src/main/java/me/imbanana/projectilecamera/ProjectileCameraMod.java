package me.imbanana.projectilecamera;

import me.imbanana.projectilecamera.config.ModConfig;
import me.imbanana.projectilecamera.keymapping.ModKeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ProjectileCameraMod {
    public static final String MOD_ID = "projectile_camera";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static ModCameraController modCameraController;

    public static void init() {
        modCameraController = new ModCameraController();
        ModConfig.HANDLER.load();
    }

    public static void tickEvent() {
        modCameraController.tick();

        if (ModKeyMapping.toggleMod.consumeClick()) {
            ModConfig config = ModConfig.HANDLER.instance();
            config.setEnabled(!ModConfig.HANDLER.instance().isEnabled());
            ModConfig.HANDLER.save();

            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                player.sendOverlayMessage(Component.translatable("msg." + MOD_ID + ".toggle_" + (config.isEnabled() ? "on" : "off")));
            }
        }
    }

    public static ModCameraController getCameraController() {
        return modCameraController;
    }

    public static Identifier idOf(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static Path getConfigDir() {
        Path configDir = Minecraft.getInstance().gameDirectory.toPath().resolve("config");

        if (!Files.exists(configDir)) {
            try {
                Files.createDirectories(configDir);
            } catch (IOException e) {
                throw new RuntimeException("Creating config directory", e);
            }
        }

        return configDir;
    }
}
