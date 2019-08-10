package me.Browk.qCore.Evenimente;

import me.Browk.qCore.Language.*;
import me.Browk.qCore.Utile.*;
import org.bukkit.event.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class SignEvent implements Listener, Message, Utile {
    @EventHandler
    public void onClick(final SignChangeEvent e) {
        final Player p = e.getPlayer();
        if (p.hasPermission("essentials.sign")) {
            e.setLine(0, this.replace(e.getLine(0)));
            e.setLine(1, this.replace(e.getLine(1)));
            e.setLine(2, this.replace(e.getLine(2)));
            e.setLine(3, this.replace(e.getLine(3)));
        }
    }
}
