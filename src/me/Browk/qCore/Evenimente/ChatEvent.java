package me.Browk.qCore.Evenimente;

import com.intellectualcrafters.plot.object.PlotPlayer;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.ChatMode;
import com.massivecraft.factions.struct.Relation;
import com.plotsquared.bukkit.util.BukkitUtil;
import litebans.api.Database;
import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Language.*;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.*;
import org.bukkit.event.player.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import me.Browk.qCore.Custom.*;
import java.util.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;

public class ChatEvent implements Listener, Utile, Message {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e) {
        final Player p = e.getPlayer();
        final Jucator j = this.getJucator(p);
        if(Bukkit.getPluginManager().isPluginEnabled("LiteBans")) {
            if (Database.get().isPlayerMuted(p.getUniqueId(), p.getName())) {
                return;
            }
        }
        if(e.getMessage().contains("[event cancelled by LiteBans]")) {
            e.setCancelled(true);
            return;
        }
        e.setCancelled(true);
        if (!p.hasPermission("essentials.spam.bypass")) {
            if ((j.getSpam().contains(e.getMessage()) || e.getMessage().startsWith(j.getSpam())) && e.getMessage().length() <= j.getSpam().length() + 6) {
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO SPAM"));
                return;
            }
            j.setSpam(e.getMessage());
        }
        for(String str : this.getInstance().getConfig().getStringList("Cuvinte Blocate")) {
            if (e.getMessage().replaceAll(" ", "").replaceAll(".", "").toLowerCase().contains(str.toLowerCase())) {
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO SWEAR"));
                return;
            }
        }
        if (!p.hasPermission("essentials.chat.cooldown.bypass")) {
            if(j.getChatcooldown() > System.currentTimeMillis()) {
                this.sendMessage(p, this.getMessage(this.getLanguage(p), "NO SPAM2").replace("<TIME>", String.valueOf(this.getInstance().getConfig().getInt("Chat Cooldown"))));
                return;
            }
            j.setChatcooldown(System.currentTimeMillis() + this.getInstance().getConfig().getInt("Chat Cooldown")*1000);
        }
        for(Player q : Bukkit.getOnlinePlayers()) {
            String rel_pref = "";
            if(!getJucator(q).getIgnore().contains(p.getName())) {
                String premsg = this.replace(this.getInstance().getConfig().getString("Chat-Format.Player-prefix").replace("<PREFIX>", "%powerfulperms_firstprefix_Staff_Others_Donator_default%").replace("<FACTIONS_REL>", rel_pref) + this.getInstance().getConfig().getString("Chat-Format.Player-name").replace("<PLAYER>", j.getNick()) + this.getInstance().getConfig().getString("Chat-Format.Player-suffix") + this.getInstance().getConfig().getString("Chat-Format.Separator-Nume-Mesaj"));
                premsg = PlaceholderAPI.setPlaceholders(p, premsg);
                String msg = "";
                if(p.hasPermission("essentials.chat.color")) {
                    msg = ChatColor.translateAlternateColorCodes('&', e.getMessage());
                } else {
                    msg = e.getMessage();
                }
                String upper = msg.replaceAll("[^A-Z]", "");
                if(upper.length() >= 8) {
                    msg = msg.toLowerCase();
                }
                String hoverText = "";
                for(String s : this.getInstance().getConfig().getStringList("Chat-Format.Hover-text")) {
                    if(!hoverText.equalsIgnoreCase("")) {
                        hoverText += "\n";
                    }
                    hoverText += s;
                }
                ComponentBuilder message = new ComponentBuilder(premsg).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(PlaceholderAPI.setPlaceholders(p, hoverText.replace("<MONEY>", String.valueOf(j.getMoney())))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + p.getName())).append(msg).reset().append("").reset();
                ((Player) q).spigot().sendMessage(message.create());
            } else if(p.hasPermission("essentials.ignore.bypass")) {
                String premsg = this.replace(this.getInstance().getConfig().getString("Chat-Format.Player-prefix").replace("<PREFIX>", "%powerfulperms_firstprefix_Staff_Others_Donator_default%").replace("<FACTIONS_REL>", "%powerfulperms_firstprefix_Staff_Others_Donator_default%") + this.getInstance().getConfig().getString("Chat-Format.Player-name").replace("<PLAYER>", j.getNick()) + this.getInstance().getConfig().getString("Chat-Format.Player-suffix") + this.getInstance().getConfig().getString("Chat-Format.Separator-Nume-Mesaj"));
                premsg = PlaceholderAPI.setPlaceholders(p, premsg);
                String msg = "";
                if(p.hasPermission("essentials.chat.color")) {
                    msg = ChatColor.translateAlternateColorCodes('&', e.getMessage());
                } else {
                    msg = e.getMessage();
                }
                String upper = msg.replaceAll("[^A-Z]", "");
                if(upper.length() >= 8) {
                    msg = msg.toLowerCase();
                }
                String hoverText = "";
                for(String s : this.getInstance().getConfig().getStringList("Chat-Format.Hover-text")) {
                    if(!hoverText.equalsIgnoreCase("")) {
                        hoverText += "\n";
                    }
                    hoverText += s;
                }
                ComponentBuilder message = new ComponentBuilder(premsg).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(PlaceholderAPI.setPlaceholders(p, hoverText.replace("<MONEY>", String.valueOf(j.getMoney())))).create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + p.getName())).append(msg).reset().append("").reset();
                ((Player) q).spigot().sendMessage(message.create());
            }
        }
        Bukkit.getLogger().info(p.getName() + ": " + e.getMessage());
    }
}
