package me.shockpast.roflan.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDismountEvent;

public class EntityListener implements Listener {
    public EntityListener() {}

    @EventHandler
    public void onEntityDismount(EntityDismountEvent event) {
        Entity player = event.getEntity();
        Entity entity = event.getDismounted();

        if (entity.getType() != EntityType.ARMOR_STAND)
            return;
        if (entity.getMetadata("sit").isEmpty())
            return;

        Location location = player.getLocation();
        location.setY(location.getY() + 1.7);

        player.teleport(location);
        entity.remove();
    }
}