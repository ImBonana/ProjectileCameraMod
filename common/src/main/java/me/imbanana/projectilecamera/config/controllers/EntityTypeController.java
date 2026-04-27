package me.imbanana.projectilecamera.config.controllers;

import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.utils.Dimension;
import dev.isxander.yacl3.gui.AbstractWidget;
import dev.isxander.yacl3.gui.YACLScreen;
import dev.isxander.yacl3.gui.controllers.dropdown.AbstractDropdownController;
import dev.isxander.yacl3.gui.utils.ItemRegistryHelper;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.IdentifierException;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class EntityTypeController extends AbstractDropdownController<EntityType<?>> {


    protected EntityTypeController(Option<EntityType<?>> option) {
        super(option);
    }

    @Override
    public String getString() {
        return BuiltInRegistries.ENTITY_TYPE.getKey(option.pendingValue()).toString();
    }

    @Override
    public void setFromString(String value) {
        option.requestSet(getEntityTypeFromName(value, option.pendingValue()));
    }

    @Override
    public Component formatValue() {
        return Component.literal(toString());
    }

    @Override
    public boolean isValueValid(String value) {
        return this.isRegisteredEntityType(value);
    }

    @Override
    protected String getValidValue(String value, int offset) {
        return getMatchingItemIdentifiers(value)
                .skip(offset)
                .findFirst()
                .map(Identifier::toString)
                .orElseGet(this::getString);
    }

    @Override
    public AbstractWidget provideWidget(YACLScreen screen, Dimension<Integer> widgetDimension) {
        return new EntityTypeControllerElement(this, screen, widgetDimension);
    }

    public static EntityType<?> getEntityTypeFromName(String identifier, EntityType<?> defaultEntityType) {
        try {
            Identifier entityIdentifier = YACLPlatform.parseRl(identifier.toLowerCase());
            if (BuiltInRegistries.ENTITY_TYPE.containsKey(entityIdentifier)) {
                return ItemRegistryHelper.getFromRegistry(BuiltInRegistries.ENTITY_TYPE, entityIdentifier);
            }
        } catch (IdentifierException ignored) { }

        return defaultEntityType;
    }

    public boolean isRegisteredEntityType(String identifier) {
        try {
            Identifier entityIdentifier = YACLPlatform.parseRl(identifier.toLowerCase());
            return BuiltInRegistries.ENTITY_TYPE.containsKey(entityIdentifier);
        } catch (IdentifierException e) {
            return false;
        }
    }

    public static Stream<Identifier> getMatchingItemIdentifiers(String value) {
        int sep = value.indexOf(Identifier.NAMESPACE_SEPARATOR);
        Predicate<Identifier> filterPredicate;
        if (sep == -1) {
            filterPredicate = identifier ->
                    identifier.getPath().contains(value)
                            || ItemRegistryHelper.getFromRegistry(BuiltInRegistries.ENTITY_TYPE, identifier).getDescription()
                            .getString().toLowerCase().contains(value.toLowerCase());
        } else {
            String namespace = value.substring(0, sep);
            String path = value.substring(sep + 1);
            filterPredicate = identifier -> identifier.getNamespace().equals(namespace) && identifier.getPath().startsWith(path);
        }
        return BuiltInRegistries.ITEM.keySet().stream()
                .filter(filterPredicate)
                .sorted((id1, id2) -> {
                    String path = (sep == -1 ? value : value.substring(sep + 1)).toLowerCase();
                    boolean id1StartsWith = id1.getPath().toLowerCase().startsWith(path);
                    boolean id2StartsWith = id2.getPath().toLowerCase().startsWith(path);
                    if (id1StartsWith) {
                        if (id2StartsWith) {
                            return id1.compareTo(id2);
                        }
                        return -1;
                    }
                    if (id2StartsWith) {
                        return 1;
                    }
                    return id1.compareTo(id2);
                });
    }
}
