package me.jamin.projectiles.goal;

import lombok.Getter;
import me.jamin.projectiles.model.SimpleProjectile;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

/**
 * Attempts to follow entity found at cursor
 */
public class FollowCursorEntityGoal implements ProjectileGoal {

    /**
     * Our raytract result to see if an entity was found
     */
    private final RayTraceResult toFollow;

    public FollowCursorEntityGoal(Player shooter) {
        // Will attempt to find entity cursor is on, given it's a living entity. Complexity here is O(n+M^3), n being number of entities near it and m being a constant I think
        this.toFollow = shooter.getLocation().getWorld().rayTraceEntities(shooter.getEyeLocation(), shooter.getEyeLocation().getDirection(), 150, entity -> entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER);
    }

    @Override
    public void tick(Player shooter, SimpleProjectile projectile) {
        // Found entity
        if (toFollow != null && toFollow.getHitEntity() != null && toFollow.getHitEntity() instanceof LivingEntity) {
            projectile.getProjectile().setVelocity(approachEntity(projectile, toFollow.getHitEntity()));
        }
    }
}
