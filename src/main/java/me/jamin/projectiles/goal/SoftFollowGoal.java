package me.jamin.projectiles.goal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.jamin.projectiles.model.SimpleProjectile;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Optional;

/**
 * Try to follow nearby entities for a few seconds
 */
@RequiredArgsConstructor
public class SoftFollowGoal implements ProjectileGoal {

    /**
     * Keep track of alive ticks before terminating task, max 60 ticks
     */
    private int elapsedTicks = 0;

    /**
     * Total amount the angle has changed
     */
    private double totalChangedAngle = 0;

    @Override
    public void tick(Player shooter, SimpleProjectile projectile) {
        if (elapsedTicks >= 60) return;

        // Try to find closest entity within 5 blocks
        Optional<Entity> closestEntity = projectile.getProjectile().getNearbyEntities(5, 5, 5).stream().filter(entity -> entity != shooter && entity != projectile.getProjectile() && entity instanceof LivingEntity).findFirst();

        if (closestEntity.isPresent()) {
            // Will try approach entity for up to 3 seconds
            Entity foundEntity = closestEntity.get();

            // Angle of change
            Vector dirVelocity = projectile.getProjectile().getVelocity().clone().normalize();
            Vector dirToTarget = foundEntity.getLocation().clone().add(new Vector(0.0D, 0.5D, 0.0D)).subtract(projectile.getProjectile().getLocation()).toVector().clone().normalize();
            totalChangedAngle += dirVelocity.angle(dirToTarget);

            // Only track change in 1 second
            if (elapsedTicks % 20 == 0) {
                totalChangedAngle = 0;
            }

            // Changed too much so just give up
            if (Math.toDegrees(totalChangedAngle - 0.12D) >= 45) {
                // Make plop on ground for dramatic effect :)
                projectile.getProjectile().setVelocity(new Vector(0, -1, 0));
                projectile.getProjectile().getWorld().spawnParticle(Particle.VILLAGER_ANGRY, projectile.getProjectile().getLocation(), 1);
                return;
            }

            projectile.getProjectile().setVelocity(approachEntity(projectile, foundEntity));
            elapsedTicks++;
        }
    }
}
