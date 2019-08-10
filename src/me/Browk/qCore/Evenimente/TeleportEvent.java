package me.Browk.qCore.Evenimente;

import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Language.*;
import org.bukkit.entity.*;
import me.Browk.qCore.Custom.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.*;
import org.bukkit.plugin.Plugin;

public class TeleportEvent implements Listener, Utile, Message {
    @EventHandler
    public void onTeleport(final PlayerTeleportEvent e) {
        final Player p = e.getPlayer();
        final Jucator j = this.getJucator(p);
        if (j.getTploc() != null) {
            e.setCancelled(true);
        }
        if (j.haveJail()) {
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "JAIL CMD").replace("<TIME>", this.timeformat(j.getJailTime())));
            e.setCancelled(true);
            return;
        }
        j.setBack(e.getFrom());
        j.setTpcooldown(0);
        if (!p.hasPermission("essentials.admin") && this.getInstance().getConfig().getInt("Tp Time") != 0 && e.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN) {
            e.setCancelled(true);
            j.setTploc(e.getTo());
            j.setTpcooldown(this.getInstance().getConfig().getInt("Tp Time"));
            j.setTploc(e.getTo());
            this.sendTitle(j.getPlayer(), 0, 25, 20, "&a", "&fTe vei teleporta...");
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) Bukkit.getPluginManager().getPlugin("qCore"), (Runnable) new Runnable() {
                @Override
                public void run() {
                    if(j.getTploc() != null) {
                        teleportPlayer(p, e.getTo());
                    }
                }
            }, 20L*this.getInstance().getConfig().getInt("Tp Time"));
            return;
        }
        e.setCancelled(false);
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        final Jucator j = this.getJucator(p);
        if(e.getTo().getY() < 0 && p.getWorld().getName().equalsIgnoreCase("world") && this.getInstance().getConfig().getString("Server").contains("SkyBlock")) {
            j.setTploc(getSpawn());
            teleportPlayer(p, j.getTploc());
        }
        if (j.getTpcooldown() > 0 && e.getFrom().getX() != e.getTo().getX() && e.getFrom().getZ() != e.getTo().getZ()) {
            this.sendTitle(j.getPlayer(), 0, 20, 0, "&a", "&c" + this.getMessage(j.getLanguage(), "MOVE"));
            j.setTpcooldown(0);
            j.setTploc(null);
        }
    }
}
