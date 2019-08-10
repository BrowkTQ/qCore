package me.Browk.qCore.Evenimente;

import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Language.*;
import java.io.*;
import org.bukkit.configuration.file.*;
import org.bukkit.*;
import com.vexsoftware.votifier.model.*;
import me.Browk.qCore.MySQL.*;
import org.bukkit.entity.*;
import me.Browk.qCore.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;
import org.bukkit.command.*;
import java.util.*;
import org.bukkit.event.player.*;

public class VoteEvent implements Utile, Listener, Message {
    public File voteFile;
    public FileConfiguration vote;
    int o;

    public VoteEvent() {
        this.voteFile = new File(this.getInstance().getDataFolder(), "vote.yml");
        this.vote = (FileConfiguration) YamlConfiguration.loadConfiguration(this.voteFile);
        this.o = 0;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onVotifierEvent(final VotifierEvent event) {
        final Vote vote = event.getVote();
        ++this.o;
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask((Plugin) this.getInstance(), (Runnable) new Runnable() {
            @Override
            public void run() {
                final VoteEvent this$0 = VoteEvent.this;
                --this$0.o;
                if (vote.getUsername() != null && vote.getUsername().length() > 0) {
                    if (!vote.getUsername().trim().matches("[A-Za-z0-9-_]+")) {
                        VoteEvent.this.sendLoggerConsole("&cO persoana a votat si a introdus un nume invalid: &4" + vote.getUsername());
                        return;
                    }
                    String user = vote.getUsername().replaceAll("[^a-zA-Z0-9_\\-]", "");
                    user = user.substring(0, Math.min(user.length(), 16));
                    vote.setUsername(user);
                    if (!vote.getAddress().equals("fakeVote.local")) {
                        final String service = vote.getServiceName().replaceAll("[^a-zA-Z0-9_\\.\\-]", "");
                        vote.setServiceName(service);
                    }
                    if (Database.getData().isRegistered("Global", "Player", vote.getUsername())) {
                        Database.getData().setInt("Global", "Vote", "Player", vote.getUsername(), Database.getData().getInt("Global", "Vote", "Player", vote.getUsername()) + 1);
                    }
                    VoteEvent.this.processVote(vote);
                    if (VoteEvent.this.getInstance().getConfig().getBoolean("Vote Party On")) {
                        final Main instance = VoteEvent.this.getInstance();
                        ++instance.dropparty;
                        if (VoteEvent.this.getInstance().dropparty >= VoteEvent.this.getInstance().getConfig().getInt("Vote Party")) {
                            for (final Player p : Bukkit.getOnlinePlayers()) {
                                VoteEvent.this.giveRewardDrop(p);
                            }
                            VoteEvent.this.getInstance().dropparty = 0;
                        }
                    }
                } else {
                    VoteEvent.this.sendLoggerConsole("&cVote primit pe &4" + vote.getServiceName() + "&c fara a fi specificat un nume...");
                }
            }
        }, this.o * 20L);
    }

    public void processVote(final Vote vote) {
        final String playerName = vote.getUsername();
        final Player p = Bukkit.getPlayer(playerName);
        if (p != null) {
            final List<String> cmds = (List<String>) this.getInstance().getConfig().getStringList("Vote Command");
            for (final String cmd : cmds) {
                Bukkit.dispatchCommand((CommandSender) Bukkit.getServer().getConsoleSender(), cmd.replace("<PLAYER>", p.getName()));
            }
            for (final Player o : Bukkit.getOnlinePlayers()) {
                this.sendMessage(o, this.getMessage("RO", "VOTE").replace("<PLAYER>", p.getName()).replace("<I>", "1"));
            }
        } else {
            this.vote.set("Vote." + playerName, (Object) (this.vote.getInt("Vote." + playerName) + 1));
            this.saveConfig(this.voteFile, this.vote);
        }
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if (this.vote.contains("Vote." + p.getName())) {
            final int vote = this.vote.getInt("Vote." + p.getName());
            for (int i = 1; i <= vote; ++i) {
                for (final String cmd : this.getInstance().getConfig().getStringList("Vote Command")) {
                    Bukkit.dispatchCommand((CommandSender) Bukkit.getServer().getConsoleSender(), cmd.replace("<PLAYER>", p.getName()));
                }
            }
            for (final Player o : Bukkit.getOnlinePlayers()) {
                this.sendMessage(o, this.getMessage("RO", "VOTE").replace("<PLAYER>", p.getName()).replace("<I>", new StringBuilder(String.valueOf(vote)).toString()));
            }
            this.vote.set("Vote." + p.getName(), (Object) null);
            this.saveConfig(this.voteFile, this.vote);
        }
    }

    public void giveReward(final Player p) {
        final List<String> cmds = (List<String>) this.getInstance().getConfig().getStringList("Vote Command");
        for (final String cmd : cmds) {
            Bukkit.dispatchCommand((CommandSender) Bukkit.getServer().getConsoleSender(), cmd.replace("<PLAYER>", p.getName()));
        }
    }

    public void giveRewardDrop(final Player p) {
        final List<String> cmds = (List<String>) this.getInstance().getConfig().getStringList("DropParty Command");
        for (final String cmd : cmds) {
            Bukkit.dispatchCommand((CommandSender) Bukkit.getServer().getConsoleSender(), cmd.replace("<PLAYER>", p.getName()));
        }
    }
}
