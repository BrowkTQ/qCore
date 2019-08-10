package me.Browk.qCore.Evenimente;

import me.Browk.qCore.Language.Message;
import me.Browk.qCore.Utile.Utile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PreChatEvent implements Listener, Utile, Message {

    @EventHandler
    public void onCommandSend(PlayerCommandPreprocessEvent e) {
        if(!(e.getPlayer() instanceof Player)) {
            return;
        }
        String[] cmd = e.getMessage().split(" ");
        if(cmd.length > 0) {
            if (this.getInstance().getConfig().getStringList("Inavabile Commands").contains(cmd[0])) {
                this.noPermission(e.getPlayer());
                e.setCancelled(true);
            }
        }
    }

}
