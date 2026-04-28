package me.imbanana.projectilecamera.keymapping;

import com.mojang.blaze3d.platform.InputConstants;
import me.imbanana.projectilecamera.ProjectileCameraMod;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class ModKeyMapping {
    public static KeyMapping toggleKey = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "key." + ProjectileCameraMod.MOD_ID + ".toggle_mod",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_Z,
                    KeyMapping.Category.MISC
            )
    );

    public static void registerKeyMapping() {
        ProjectileCameraMod.LOGGER.info("Registered mod key mapping.");
    }
}
