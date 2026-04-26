package me.imbanana.projectilecamera.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.imbanana.projectilecamera.ProjectileCameraMod;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    @WrapWithCondition(
            method = "keyPress",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;pauseGame(Z)V"
            )
    )
    private boolean shouldPause(Minecraft instance, boolean pauseOnly) {
        return !ProjectileCameraMod.getCameraController().isTrackingProjectile();
    }
}
