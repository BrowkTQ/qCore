package me.Browk.qCore.Evenimente;

import me.Browk.qCore.Utile.*;
import org.bukkit.event.player.*;
import org.bukkit.entity.*;
import me.Browk.qCore.Custom.*;
import org.bukkit.event.*;

public class InteractEvent implements Listener, Utile {
    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        final Jucator j = this.getJucator(p);
        if (!j.getPt_command().equals("NU") && p.getItemInHand().getType() == j.getPt_item()) {
            p.performCommand(j.getPt_command());
        }
    }
}
