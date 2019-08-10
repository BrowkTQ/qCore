package me.Browk.qCore.Evenimente;

import me.Browk.qCore.Custom.Jucator;
import me.Browk.qCore.Utile.*;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.entity.*;
import me.Browk.qCore.MySQL.*;
import org.bukkit.plugin.*;
import org.bukkit.event.player.*;
import org.bukkit.event.*;

public class JoinEvent implements Listener, Utile {
    int o;

    public JoinEvent() {
        this.o = 0;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if (this.getInstance().getConfig().getString("Default Kit") != "false" && !p.hasPlayedBefore() && getKits().contains(this.getInstance().getConfig().getString("Default Kit"))) {
            this.giveKit(p, this.getInstance().getConfig().getString("Default Kit"));
        }
        ++this.o;
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) this.getInstance(), (Runnable) new Runnable() {
            @Override
            public void run() {
                final JoinEvent this$0 = JoinEvent.this;
                --this$0.o;
                Database.getData().setString(JoinEvent.this.getInstance().server, "Ip", "Player", p.getName(), p.getAddress().toString().replace("/", ""));
            }
        }, this.o * 20L);
        if (this.getInstance().getConfig().getBoolean("Spawn Tp") && getSpawn() != null) {
            p.teleport(this.getSpawn(), PlayerTeleportEvent.TeleportCause.COMMAND);
        }
        e.setJoinMessage((String) null);
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) Bukkit.getPluginManager().getPlugin("qCore"), (Runnable) new Runnable() {
            @Override
            public void run() {
                if (getInstance().getConfig().getBoolean("Welcome.Motd.enable")) {
                    String motd = "";
                    for (String s : getInstance().getConfig().getStringList("Welcome.Motd.message")) {
                        if (!motd.equalsIgnoreCase("")) {
                            motd += "\n";
                        }
                        if (s.startsWith("<center>")) {
                            s = s.replace("<center>", "");
                            motd += StringUtils.center(replace(s), 68);
                        } else {
                            motd += replace(s.replace("%blank%", "    &f"));
                        }
                    }
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, motd));
                }
                if (getInstance().getConfig().getBoolean("Welcome.Title.enable")) {
                    sendTitle(p, getInstance().getConfig().getInt("Welcome.Title.fadeIn"), getInstance().getConfig().getInt("Welcome.Title.stay"), getInstance().getConfig().getInt("Welcome.Title.fadeOut"), PlaceholderAPI.setPlaceholders(p, getInstance().getConfig().getString("Welcome.Title.title-message")), PlaceholderAPI.setPlaceholders(p, getInstance().getConfig().getString("Welcome.Title.subtitle-message")));
                }
            }}, 20L*1L);
        if(!p.hasPermission("essentials.vanish.bypass")) {
            for (Player o : Bukkit.getOnlinePlayers()) {
                if (getJucator(o).isVanish()) {
                    p.hidePlayer(o);
                }
            }
        }
        getJucator(p).setMoney(getJucator(p).getMoney());
    }
}
