package me.imbanana.projectilecamera.neoforge;

import me.imbanana.projectilecamera.ProjectileCameraMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = ProjectileCameraMod.MOD_ID, dist = Dist.CLIENT)
public final class ProjectileCameraModNeoForge {
    public ProjectileCameraModNeoForge() {
        // Run our common setup.
        ProjectileCameraMod.init();
    }
}
