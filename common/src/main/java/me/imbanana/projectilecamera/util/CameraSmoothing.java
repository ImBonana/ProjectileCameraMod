package me.imbanana.projectilecamera.util;

import net.minecraft.world.phys.Vec3;

import java.util.function.Function;

public class CameraSmoothing {
    private double stiffness;
    private double damping;

    private Vec3 position = Vec3.ZERO;
    private Vec3 velocity = Vec3.ZERO;
    private boolean initialized = false;

    public CameraSmoothing(double stiffness, double damping) {
        this.stiffness = stiffness;
        this.damping = damping;
    }

    public Vec3 smooth(Vec3 newPos) {
        return this.smooth(newPos, vec3 -> vec3);
    }

    public Vec3 smooth(Vec3 newPos, Function<Vec3, Vec3> diffModifier) {
        if (!this.initialized) {
            this.initialized = true;
            this.position = newPos;
        } else {
            Vec3 diff = diffModifier.apply(newPos.subtract(this.position));

            this.velocity = this.velocity
                    .add(diff.scale(this.stiffness))
                    .scale(this.damping);

            this.position = this.position.add(this.velocity);
        }

        return this.position;
    }

    public void reset() {
        this.initialized = false;
        this.velocity = Vec3.ZERO;
    }

    public void setStiffness(double stiffness) {
        this.stiffness = stiffness;
    }

    public void setDamping(double damping) {
        this.damping = damping;
    }

    public double getStiffness() {
        return stiffness;
    }

    public double getDamping() {
        return damping;
    }
}
