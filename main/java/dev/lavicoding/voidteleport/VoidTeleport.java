package dev.lavicoding.voidteleport;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class VoidTeleport extends JavaPlugin implements Listener {

    private final Set<UUID> cooldown = new HashSet<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("VoidTeleport aktif!");
    }

    @EventHandler
    public void onVoidFall(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();

        if (loc.getY() <= -60 && player.getWorld().getName().equals("world") && !cooldown.contains(player.getUniqueId())) {
            World nether = Bukkit.getWorld("world_nether");
            if (nether != null) {
                Location target = new Location(nether, loc.getX(), 100, loc.getZ());
                player.teleport(target);
                player.sendMessage("§cÇok derine düştüğün için Nether'e ışınlandın!");
                cooldown.add(player.getUniqueId());

                // 5 saniye sonra cooldown sıfırlanır
                Bukkit.getScheduler().runTaskLater(this, () -> cooldown.remove(player.getUniqueId()), 100L);
            }
        }
    }
}
