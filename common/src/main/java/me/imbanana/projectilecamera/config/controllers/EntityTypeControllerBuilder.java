package me.imbanana.projectilecamera.config.controllers;

import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.controller.ControllerBuilder;
import net.minecraft.world.entity.EntityType;

public interface EntityTypeControllerBuilder extends ControllerBuilder<EntityType<?>> {
    static EntityTypeControllerBuilder create(Option<EntityType<?>> option) {
        return new EntityTypeControllerBuilderImp(option);
    }
}
