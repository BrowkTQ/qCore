package me.Browk.qCore.Evenimente;

import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Language.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import me.Browk.qCore.Custom.*;
import org.bukkit.event.*;

public class FoodEvent implements Listener, Utile, Message {
    @EventHandler
    public void onFood(final FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player p = (Player) e.getEntity();
            final Jucator j = this.getJucator(p);
            if (j.isGod()) {
                e.setCancelled(true);
            }
        }
    }
}
