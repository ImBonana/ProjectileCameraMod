package me.imbanana.projectilecamera.config;

import com.jcraft.jorbis.Block;
import net.minecraft.world.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class ModConfig {
    public static final List<EntityType<?>> TRACKABLE = new ArrayList<>(){{
        add(EntityType.SNOWBALL);
        add(EntityType.EGG);
        add(EntityType.ENDER_PEARL);
        add(EntityType.SPECTRAL_ARROW);
        add(EntityType.ARROW);
        add(EntityType.TRIDENT);
    }};

    public static final boolean SHOULD_STOP_WHEN_MOVED = true;
    public static final boolean SHOULD_STOP_WHEN_KEYPRESS = true;
}
