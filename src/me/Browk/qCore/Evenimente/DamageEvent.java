package me.Browk.qCore.Evenimente;

import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Language.*;
import org.bukkit.entity.*;
import me.Browk.qCore.Custom.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

public class DamageEvent implements Listener, Utile, Message {
    @EventHandler
    public void onDamage(final EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player p = (Player) e.getEntity();
            final Jucator j = this.getJucator(p);
            if (j.isGod()) {
                e.setCancelled(true);
            }
            if (j.haveJail()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamageByPlayer(final EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player p = (Player) e.getEntity();
            final Jucator j = this.getJucator(p);
            if (j.isGod()) {
                e.setCancelled(true);
            }
            if (e.getDamager() instanceof Player) {
                final Player d = (Player) e.getDamager();
                final Jucator dj = this.getJucator(d);
                if (dj.isGod()) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
