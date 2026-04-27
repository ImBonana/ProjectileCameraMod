package me.imbanana.projectilecamera.fabric.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.imbanana.projectilecamera.config.ModConfigScreenFactory;

public class ModMenuEntry implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModConfigScreenFactory::buildConfigScreen;
    }
}
