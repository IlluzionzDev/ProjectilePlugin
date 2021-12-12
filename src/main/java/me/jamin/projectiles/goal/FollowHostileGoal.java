package me.jamin.projectiles.goal;

import me.jamin.projectiles.model.SimpleProjectile;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * Will attempt to follow any hostile mob in a range
 */
public class FollowHostileGoal implements ProjectileGoal {

    @Override
    public void tick(Player shooter, SimpleProjectile projectile) {
        // Try to find closest entity within 5 blocks
        Optional<Entity> closestEntity = projectile.getProjectile().getNearbyEntities(20, 20, 20).stream().filter(entity -> entity != shooter && entity != projectile.getProjectile() && entity instanceof Monster).findFirst();

        if (closestEntity.isPresent()) {
            Entity foundEntity = closestEntity.get();
            projectile.getProjectile().setVelocity(approachEntity(projectile, foundEntity));
        }
    }

}
