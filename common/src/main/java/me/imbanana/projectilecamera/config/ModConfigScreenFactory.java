package me.imbanana.projectilecamera.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import me.imbanana.projectilecamera.ProjectileCameraMod;
import me.imbanana.projectilecamera.config.controllers.EntityTypeControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;

public class ModConfigScreenFactory {

    public static Screen buildConfigScreen(Screen parentScreen) {
        return YetAnotherConfigLib.create(ModConfig.HANDLER, (defaults, config, builder) ->
                    builder.title(createText("title"))
                    .category(ConfigCategory.createBuilder()
                            .name(createText("title"))
                            .option(Option.<Boolean>createBuilder()
                                    .name(createText("enable"))
                                    .description(OptionDescription.of(createText("enable.desc")))
                                    .binding(defaults.isEnabled(), config::isEnabled, config::setEnabled)
                                    .controller(TickBoxControllerBuilder::create)
                                    .build()
                            )
                            .option(Option.<Boolean>createBuilder()
                                    .name(createText("stop_on_move"))
                                    .description(OptionDescription.of(createText("stop_on_move.desc")))
                                    .binding(defaults.isShouldStopWhenMoved(), config::isShouldStopWhenMoved, config::setShouldStopWhenMoved)
                                    .controller(TickBoxControllerBuilder::create)
                                    .build()
                            )
                            .option(Option.<Boolean>createBuilder()
                                    .name(createText("stop_on_key"))
                                    .description(OptionDescription.of(createText("stop_on_key.desc")))
                                    .binding(defaults.isShouldStopWhenKeypress(), config::isShouldStopWhenKeypress, config::setShouldStopWhenKeypress)
                                    .controller(TickBoxControllerBuilder::create)
                                    .build()
                            )
                            .option(ListOption.<EntityType<?>>createBuilder()
                                    .name(createText("tracked_projectiles"))
                                    .description(OptionDescription.of(createText("tracked_projectiles.desc")))
                                    .binding(defaults.getTrackableEntities(), config::getTrackableEntities, config::setTrackableEntities)
                                    .initial(EntityType.SNOWBALL)
                                    .controller(EntityTypeControllerBuilder::create)
                                    .build()
                            )
                            .build()
                    )
                    .category(ConfigCategory.createBuilder()
                            .name(createText("camera_smooth"))
                            .group(OptionGroup.createBuilder()
                                    .name(createText("camera_smooth.rotation"))
                                    .option(Option.<Float>createBuilder()
                                            .name(createText("camera_smooth.rotation.stiffness"))
                                            .description(OptionDescription.of(createText("camera_smooth.rotation.stiffness.desc")))
                                            .binding(defaults.getRotationSmoothStiffness(), config::getRotationSmoothStiffness, config::setRotationSmoothStiffness)
                                            .controller(option -> FloatSliderControllerBuilder.create(option)
                                                    .range(0f, 1f)
                                                    .step(0.01f)
                                                    .formatValue(value -> Component.literal("%.2f".formatted(value)))
                                            )
                                            .build()
                                    )
                                    .option(Option.<Float>createBuilder()
                                            .name(createText("camera_smooth.rotation.damping"))
                                            .description(OptionDescription.of(createText("camera_smooth.rotation.damping.desc")))
                                            .binding(defaults.getMovementSmoothDamping(), config::getRotationSmoothDamping, config::setRotationSmoothDamping)
                                            .controller(option -> FloatSliderControllerBuilder.create(option)
                                                    .range(0f, 1f)
                                                    .step(0.01f)
                                                    .formatValue(value -> Component.literal("%.2f".formatted(value)))
                                            )
                                            .build()
                                    )
                                    .build()
                            )
                            .group(OptionGroup.createBuilder()
                                    .name(createText("camera_smooth.movement"))
                                    .option(Option.<Float>createBuilder()
                                            .name(createText("camera_smooth.movement.stiffness"))
                                            .description(OptionDescription.of(createText("camera_smooth.movement.stiffness.desc")))
                                            .binding(defaults.getMovementSmoothStiffness(), config::getMovementSmoothStiffness, config::setMovementSmoothStiffness)
                                            .controller(option -> FloatSliderControllerBuilder.create(option)
                                                    .range(0f, 1f)
                                                    .step(0.01f)
                                                    .formatValue(value -> Component.literal("%.2f".formatted(value)))
                                            )
                                            .build()
                                    )
                                    .option(Option.<Float>createBuilder()
                                            .name(createText("camera_smooth.movement.damping"))
                                            .description(OptionDescription.of(createText("camera_smooth.movement.damping.desc")))
                                            .binding(defaults.getMovementSmoothDamping(), config::getMovementSmoothDamping, config::setMovementSmoothDamping)
                                            .controller(option -> FloatSliderControllerBuilder.create(option)
                                                    .range(0f, 1f)
                                                    .step(0.01f)
                                                    .formatValue(value -> Component.literal("%.2f".formatted(value)))
                                            )
                                            .build()
                                    )
                                    .build()
                            )
                            .build()
                    )
                ).generateScreen(parentScreen);
    }

    private static Component createText(String path) {
        return Component.translatable("config." + ProjectileCameraMod.MOD_ID + "." + path);
    }
}
