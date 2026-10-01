package me.imbanana.projectilecamera;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class ModProjectileManager {
    private int lastItemUseTime = -100;
    private boolean wasUsingItem = false;

    public InteractionResult onItemUse(Player player, Level level, InteractionHand interactionHand) {
        if (level.isClientSide()) lastItemUseTime = player.tickCount;

        return InteractionResult.PASS;
    }

    public void onEntityLoad(Entity entity, ClientLevel clientLevel) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        if (!(entity instanceof Projectile projectile)) return;
        if (projectile.position().distanceToSqr(player.getEyePosition()) > 4.0) return;
        if (player.tickCount - lastItemUseTime > 3) return;
        if (!ProjectileCameraMod.getCameraController().canTrack(projectile)) return;

        ProjectileCameraMod.getCameraController().startTracking(projectile);
    }

    public void tick() {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null) return;

        boolean isUsing = player.isUsingItem();
        if (wasUsingItem && !isUsing) {
            lastItemUseTime = player.tickCount;
        }

        wasUsingItem = isUsing;
    }
}
