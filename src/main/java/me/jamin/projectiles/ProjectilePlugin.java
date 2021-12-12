package me.jamin.projectiles;

import me.jamin.projectiles.model.SimpleProjectile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class ProjectilePlugin extends JavaPlugin implements Listener {

    /**
     * Singleton instance of our plugin
     */
    private static volatile ProjectilePlugin INSTANCE;

    /**
     * Return our instance of the plugin
     *
     * @return This instance of the plugin
     */
    public static ProjectilePlugin getInstance() {
        // Assign if null
        if (INSTANCE == null) {
            INSTANCE = JavaPlugin.getPlugin(ProjectilePlugin.class);
        }

        return INSTANCE;
    }

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onProjectileLaunch(final ProjectileLaunchEvent event) {
        // Create our instance on this projectile
        new SimpleProjectile((Player) event.getEntity().getShooter(), event.getEntity());
    }

}
