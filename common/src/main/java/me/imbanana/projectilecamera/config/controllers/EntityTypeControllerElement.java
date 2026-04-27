package me.imbanana.projectilecamera.config.controllers;

import dev.isxander.yacl3.api.utils.Dimension;
import dev.isxander.yacl3.gui.YACLScreen;
import dev.isxander.yacl3.gui.controllers.dropdown.AbstractDropdownControllerElement;
import dev.isxander.yacl3.gui.utils.ItemRegistryHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityTypeControllerElement extends AbstractDropdownControllerElement<EntityType<?>, Identifier> {
    private final EntityTypeController entityTypeController;
    protected Map<Identifier, EntityType<?>> matchingEntityTypes = new HashMap<>();

    public EntityTypeControllerElement(EntityTypeController control, YACLScreen screen, Dimension<Integer> dim) {
        super(control, screen, dim);
        this.entityTypeController = control;
    }

    @Override
    protected void extractValueText(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        var oldDimension = getDimension();
        setDimension(getDimension().withWidth(getDimension().width() - getDecorationPadding()));
        super.extractValueText(graphics, mouseX, mouseY, a);
        setDimension(oldDimension);
    }

    @Override
    public List<Identifier> computeMatchingValues() {
        List<Identifier> identifiers = EntityTypeController.getMatchingItemIdentifiers(inputField).toList();
        for (Identifier identifier : identifiers) {
            matchingEntityTypes.put(identifier, ItemRegistryHelper.getFromRegistry(BuiltInRegistries.ENTITY_TYPE, identifier));
        }
        return identifiers;
    }

    @Override
    public String getString(Identifier identifier) {
        return identifier.toString();
    }

    @Override
    protected int getDecorationPadding() {
        return 4;
    }

    @Override
    protected int getDropdownEntryPadding() {
        return 4;
    }

    @Override
    protected int getControlWidth() {
        return super.getControlWidth() + getDecorationPadding();
    }

    @Override
    protected Component getValueText() {
        if (inputField.isEmpty() || entityTypeController == null) {
            return super.getValueText();
        }

        if (inputFieldFocused) return Component.literal(inputField);

        return entityTypeController.option().pendingValue().getDescription();
    }
}