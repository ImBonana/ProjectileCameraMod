package me.imbanana.projectilecamera.config.controllers;

import dev.isxander.yacl3.api.Controller;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.impl.controller.AbstractControllerBuilderImpl;
import net.minecraft.world.entity.EntityType;

public class EntityTypeControllerBuilderImp extends AbstractControllerBuilderImpl<EntityType<?>> implements EntityTypeControllerBuilder {
    protected EntityTypeControllerBuilderImp(Option<EntityType<?>> option) {
        super(option);
    }

    @Override
    public Controller<EntityType<?>> build() {
        return new EntityTypeController(this.option);
    }
}