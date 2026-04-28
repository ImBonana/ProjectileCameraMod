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
        return parentScreen -> YetAnotherConfigLib.create(ModConfig.HANDLER, (defaults, config, builder) ->
                builder.title(Component.literal("Projectile Camera"))
                    .category(ConfigCategory.createBuilder()
                            .name(Component.literal("Projectile Camera"))
                            .option(Option.<Boolean>createBuilder()
                                    .name(Component.literal("Enabled"))
                                    .description(OptionDescription.of(Component.literal("Enable Mod?")))
                                    .binding(defaults.isEnabled(), config::isEnabled, config::setEnabled)
                                    .controller(TickBoxControllerBuilder::create)
                                    .build()
                            )
                            .option(Option.<Boolean>createBuilder()
                                    .name(Component.literal("Stop On Movement"))
                                    .description(OptionDescription.of(Component.literal("Should the player exit the projectile camera when moved?")))
                                        .binding(defaults.isShouldStopWhenMoved(), config::isShouldStopWhenMoved, config::setShouldStopWhenMoved)
                                    .controller(TickBoxControllerBuilder::create)
                                    .build()
                            )
                            .option(Option.<Boolean>createBuilder()
                                    .name(Component.literal("Stop On Keypress"))
                                    .description(OptionDescription.of(Component.literal("Should the player exit the projectile camera when key is pressed?")))
                                    .binding(defaults.isShouldStopWhenKeypress(), config::isShouldStopWhenKeypress, config::setShouldStopWhenKeypress)
                                    .controller(TickBoxControllerBuilder::create)
                                    .build()
                            )
                            .option(ListOption.<EntityType<?>>createBuilder()
                                    .name(Component.literal("Tracked Projectiles"))
                                    .description(OptionDescription.of(Component.literal("What projectiles should the mod track?")))
                                    .binding(defaults.getTrackableEntities(), config::getTrackableEntities, config::setTrackableEntities)
                                    .initial(EntityType.SNOWBALL)
                                    .controller(EntityTypeControllerBuilder::create)
                                    .build()
                            )
                            .build()
                    )
                ).generateScreen(parentScreen);
    }
}
