package me.imbanana.projectilecamera.config;

import me.imbanana.projectilecamera.ModCameraController;
import net.minecraft.world.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class ModConfig {
    public static final List<EntityType<?>> DEFAULT_TRACKABLE = new ArrayList<>(){{
        add(EntityType.SNOWBALL);
        add(EntityType.EGG);
        add(EntityType.ENDER_PEARL);
        add(EntityType.SPECTRAL_ARROW);
        add(EntityType.ARROW);
        add(EntityType.TRIDENT);
    }};

    private List<EntityType<?>> trackableEntities;
    private boolean shouldStopWhenMoved;
    private boolean shouldStopWhenKeypress;
    private float rotationSmoothStiffness;
    private float rotationSmoothDamping;
    private float movementSmoothStiffness;
    private float movementSmoothDamping;

    public ModConfig(List<EntityType<?>> trackableEntities,
                     boolean shouldStopWhenMoved,
                     boolean shouldStopWhenKeypress,
                     float rotationSmoothStiffness,
                     float rotationSmoothDamping,
                     float movementSmoothStiffness,
                     float movementSmoothDamping) {
        this.trackableEntities = trackableEntities;
        this.shouldStopWhenMoved = shouldStopWhenMoved;
        this.shouldStopWhenKeypress = shouldStopWhenKeypress;
        this.rotationSmoothStiffness = rotationSmoothStiffness;
        this.rotationSmoothDamping = rotationSmoothDamping;
        this.movementSmoothStiffness = movementSmoothStiffness;
        this.movementSmoothDamping = movementSmoothDamping;
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

    public float getRotationSmoothStiffness() {
        return rotationSmoothStiffness;
    }

    public void setRotationSmoothStiffness(float rotationSmoothStiffness) {
        this.rotationSmoothStiffness = rotationSmoothStiffness;
        ModCameraController.updateCameraSmoothing(this);
    }

    public float getRotationSmoothDamping() {
        return rotationSmoothDamping;
    }

    public void setRotationSmoothDamping(float rotationSmoothDamping) {
        this.rotationSmoothDamping = rotationSmoothDamping;
        ModCameraController.updateCameraSmoothing(this);
    }

    public float getMovementSmoothStiffness() {
        return movementSmoothStiffness;
    }

    public void setMovementSmoothStiffness(float movementSmoothStiffness) {
        this.movementSmoothStiffness = movementSmoothStiffness;
        ModCameraController.updateCameraSmoothing(this);
    }

    public float getMovementSmoothDamping() {
        return movementSmoothDamping;
    }

    public void setMovementSmoothDamping(float movementSmoothDamping) {
        this.movementSmoothDamping = movementSmoothDamping;
        ModCameraController.updateCameraSmoothing(this);
    }
}
