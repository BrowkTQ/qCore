package me.Browk.qCore.Evenimente;

import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Language.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import me.Browk.qCore.Custom.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class DeathEvent implements Listener, Utile, Message {
    @EventHandler
    public void onDeath(final PlayerDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player p = e.getEntity();
            final Jucator j = this.getJucator(p);
            j.setBack(p.getLocation());
            this.sendMessage(p, this.getMessage(this.getLanguage(p), "BACK AFTER DEATH"));
            e.setDeathMessage((String) null);
        }
    }

    @EventHandler
    public void onRespawn(final PlayerRespawnEvent e) {
        final Player p = e.getPlayer();
        final Jucator j = this.getJucator(p);
        if (j.haveJail()) {
            if (this.getJail() != null) {
                e.setRespawnLocation(this.getJail());
            }
        } else {
            if(this.getSpawn() != null) {
                e.setRespawnLocation(this.getSpawn());
            }
        }
    }
}
