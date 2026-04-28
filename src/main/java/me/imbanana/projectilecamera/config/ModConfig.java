package me.imbanana.projectilecamera.config;

import com.google.gson.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import me.imbanana.projectilecamera.ProjectileCameraMod;
import me.imbanana.projectilecamera.config.controllers.EntityTypeController;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ModConfig {
    public static ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
            .id(ProjectileCameraMod.idOf("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("projectile_camera.json5"))
                    .appendGsonBuilder(gsonBuilder -> gsonBuilder
                            .setPrettyPrinting()
                            .registerTypeHierarchyAdapter(EntityType.class, new EntityTypeAdapter())
                    )
                    .setJson5(true)
                    .build()
            )
            .build();

    @SerialEntry
    private boolean enabled = true;

    @SerialEntry
    private List<EntityType<?>> trackableEntities = new ArrayList<>(){{
        add(EntityType.SNOWBALL);
        add(EntityType.EGG);
        add(EntityType.ENDER_PEARL);
        add(EntityType.SPECTRAL_ARROW);
        add(EntityType.ARROW);
        add(EntityType.TRIDENT);
    }};

    @SerialEntry
    private boolean shouldStopWhenMoved = true;

    @SerialEntry
    private boolean shouldStopWhenKeypress = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<EntityType<?>> getTrackableEntities() {
        return trackableEntities;
    }

    public void setTrackableEntities(List<EntityType<?>> trackableEntities) {
        this.trackableEntities = trackableEntities;
    }

    public boolean isShouldStopWhenMoved() {
        return shouldStopWhenMoved;
    }

    public void setShouldStopWhenMoved(boolean shouldStopWhenMoved) {
        this.shouldStopWhenMoved = shouldStopWhenMoved;
    }

    public boolean isShouldStopWhenKeypress() {
        return shouldStopWhenKeypress;
    }

    public void setShouldStopWhenKeypress(boolean shouldStopWhenKeypress) {
        this.shouldStopWhenKeypress = shouldStopWhenKeypress;
    }

    public static class EntityTypeAdapter implements JsonSerializer<EntityType<?>>, JsonDeserializer<EntityType<?>> {
        @Override
        public EntityType<?> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return EntityTypeController.getEntityTypeFromName(jsonElement.getAsString(), EntityType.SNOWBALL);
        }

        @Override
        public JsonElement serialize(EntityType<?> entityType, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
        }
    }
}
