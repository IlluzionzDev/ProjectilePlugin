package me.jamin.projectiles.goal;

import me.jamin.projectiles.model.SimpleProjectile;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

/**
 * Indicates the current goal a projectile is trying to fulfill
 */
public interface ProjectileGoal {

    /**
     * Tick the current goal. This is where we change velocity etc
     */
    void tick(Player shooter, SimpleProjectile projectile);

    /**
     * Returns a vector that will approach entity. This will attempt to slowly turn
     */
    default Vector approachEntity(SimpleProjectile projectile, Entity entity) {
        // Add 0.5 so hits middle of entity, or around about
        Vector toTarget = entity.getLocation().clone().add(new Vector(0.0D, 0.5D, 0.0D)).subtract(projectile.getProjectile().getLocation()).toVector();

        // Velocity of projectile
        Vector dirVelocity = projectile.getProjectile().getVelocity().clone().normalize();
        Vector dirToTarget = toTarget.clone().normalize();

        // Angle changed in radians
        double angle = dirVelocity.angle(dirToTarget);

        // Speed to change to, extra speed is so it keeps going
        double speed = projectile.getProjectile().getVelocity().length();
        double newSpeed = 0.9D * speed + 0.14D;

        // The 0.12D here is an attempt to slowly change angle and not just abruptly change it
        Vector newVelocity;
        if (angle < 0.12D) {
            // Small angle just update speed
            newVelocity = dirVelocity.clone().multiply(newSpeed);
        } else {
            // Maths to find velocity to entity
            Vector newDir = dirVelocity.clone().multiply((angle - 0.12D) / angle).add(dirToTarget.clone().multiply(0.12D / angle));
            newDir.normalize();
            newVelocity = newDir.clone().multiply(newSpeed);
        }

        return newVelocity.add(new Vector(0.0D, 0.03D, 0.0D));
    }

}
