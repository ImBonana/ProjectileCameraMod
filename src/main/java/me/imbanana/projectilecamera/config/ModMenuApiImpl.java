package me.imbanana.projectilecamera.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import me.imbanana.projectilecamera.config.controllers.EntityTypeControllerBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parentScreen -> YetAnotherConfigLib.createBuilder()
                .title(Component.literal("Projectile Camera"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.literal("Projectile Camera"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.literal("Stop On Movement"))
                                .description(OptionDescription.of(Component.literal("Should the player exit the projectile camera when moved?")))
                                .binding(true, () -> ModConfig.SHOULD_STOP_WHEN_MOVED, val -> ModConfig.SHOULD_STOP_WHEN_MOVED = val)
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.literal("Stop On Keypress"))
                                .description(OptionDescription.of(Component.literal("Should the player exit the projectile camera when key is pressed?")))
                                .binding(true, () -> ModConfig.SHOULD_STOP_WHEN_KEYPRESS, val -> ModConfig.SHOULD_STOP_WHEN_KEYPRESS = val)
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        .option(ListOption.<EntityType<?>>createBuilder()
                                .name(Component.literal("Tracked Projectiles"))
                                .description(OptionDescription.of(Component.literal("What projectiles should the mod track?")))
                                .binding(ModConfig.DEFAULT_TRACKABLE, () -> ModConfig.TRACKABLE, val -> ModConfig.TRACKABLE = val)
                                .initial(EntityType.SNOWBALL)
                                .controller(EntityTypeControllerBuilder::create)
                                .build()
                        )
                        .build()
                )
                .build()
                .generateScreen(parentScreen);
    }
}
