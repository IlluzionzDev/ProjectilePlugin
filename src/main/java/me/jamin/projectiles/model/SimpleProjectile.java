package me.jamin.projectiles.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.jamin.projectiles.ProjectilePlugin;
import me.jamin.projectiles.goal.FollowCursorEntityGoal;
import me.jamin.projectiles.goal.FollowHostileGoal;
import me.jamin.projectiles.goal.ProjectileGoal;
import me.jamin.projectiles.goal.SoftFollowGoal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.jetbrains.annotations.Nullable;

/**
 * An instance of a projectile that can have a custom goal on it
 */
@Getter
public class SimpleProjectile {

    /**
     * The launched projectile instance
     */
    public final Projectile projectile;

    /**
     * The current goal of the projectile
     */
    @Setter
    @Nullable
    public ProjectileGoal goal;

    /**
     * Id of goal scheduler
     */
    private int goalScheduler = -1;

    public SimpleProjectile(Player shooter, Projectile projectile) {
        this.projectile = projectile;

        // Update goal based on type
        switch (projectile.getType()) {
            case ARROW -> this.goal = new SoftFollowGoal();
            case EGG -> this.goal = new FollowHostileGoal();
            case ENDER_PEARL -> this.goal = new FollowCursorEntityGoal(shooter);
            default -> {} // None
        }

        launchProjectile(shooter);
    }

    /**
     * Start launch goals for this projectile
     *
     * @param player Player to launch from
     */
    public void launchProjectile(final Player player) {
        // Only tick goal if exists
        if (goal != null) {
            // Start goal
            goalScheduler = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ProjectilePlugin.getInstance(), () -> {
                // Stop when projectile lands
                if (projectile == null || projectile.isDead() || projectile.isOnGround()) {
                    Bukkit.getServer().getScheduler().cancelTask(goalScheduler);
                    return;
                }

                goal.tick(player, this);
                // Every three ticks as to not be too demanding
            }, 0, 1);
        }
    }

}
