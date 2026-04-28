package me.imbanana.projectilecamera.keymapping;

import com.mojang.blaze3d.platform.InputConstants;
import me.imbanana.projectilecamera.ProjectileCameraMod;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

public class ModKeyMapping {
    public static final KeyMapping toggleMod = new KeyMapping(
            "key." + ProjectileCameraMod.MOD_ID + ".toggle",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Z,
            KeyMapping.Category.MISC
    );

    public static void registerModKeyMapping(Consumer<KeyMapping> registry) {
        registry.accept(toggleMod);
    }
}
