package me.Browk.qCore.Custom;

import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Language.*;
import net.milkbowl.vault.VaultEco;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.*;
import me.Browk.qCore.*;
import org.bukkit.*;
import java.sql.*;
import org.bukkit.plugin.*;
import org.bukkit.scheduler.*;
import me.Browk.qCore.MySQL.*;
import java.io.*;
import org.bukkit.configuration.file.*;
import java.util.*;

public class Jucator implements Utile, Message {
    public static List<Jucator> jucatorObjects;
    private Player p;
    private String language;
    private String ignore;
    private String reply;
    private String nick;
    private String pt_command;
    private String tp;
    private String spam;
    private Location back;
    private Location tploc;
    private Integer money;
    private long helpop_time;
    private Integer tp_time;
    private int tpcooldown;
    private long chatcooldown;
    private Integer tokens;
    private Integer vote;
    private boolean giveall;
    private boolean god;
    private boolean helpop;
    private boolean spy;
    private boolean msgtoggle;
    private boolean tptoggle;
    private boolean tpa;
    private boolean vanish;
    private Material pt_item;

    static {
        Jucator.jucatorObjects = new ArrayList<Jucator>();
    }

    public Jucator(final Player p) {
        this.setPlayer(p);
        this.registerDefault();
        this.money = 0;
        this.ignore = " ";
        this.tokens = 0;
        this.vote = 0;
        this.language = "RO";
        this.nick = p.getName();
        this.getInstance();
        ++Main.o;
        final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        final Main instance = this.getInstance();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Jucator.this.getInstance();
                --Main.o;
                try {
                    final Connection connection = Jucator.this.getInstance().mysql.getConnection();
                    final PreparedStatement prepared = connection.prepareStatement("SELECT * FROM " + Jucator.this.getInstance().server + " WHERE Player LIKE '" + p.getName() + "';");
                    final ResultSet rs = prepared.executeQuery();
                    if (rs.next()) {
                        Jucator.access$1(Jucator.this, rs.getInt("Money"));
                        Jucator.access$2(Jucator.this, rs.getString("IgnoreList"));
                    } else {
                        Jucator.this.registerDefault();
                        final ResultSet rs2 = prepared.executeQuery();
                        if(rs2.next()) {
                            Jucator.access$1(Jucator.this, rs2.getInt("Money"));
                            Jucator.access$2(Jucator.this, rs2.getString("IgnoreList"));
                        }
                        rs2.close();
                    }
                    rs.close();
                    prepared.close();
                    final PreparedStatement prepared2 = connection.prepareStatement("SELECT * FROM Global WHERE Player LIKE '" + p.getName() + "';");
                    final ResultSet rs2 = prepared2.executeQuery();
                    if (rs2.next()) {
                        Jucator.access$3(Jucator.this, rs2.getInt("Tokens"));
                        Jucator.access$4(Jucator.this, rs2.getInt("Vote"));
                        Jucator.access$5(Jucator.this, rs2.getString("Language"));
                        Jucator.access$6(Jucator.this, rs2.getString("Nick"));
                    } else {
                        Jucator.this.registerDefault();
                        final ResultSet rs3 = prepared2.executeQuery();
                        if(rs3.next()) {
                            Jucator.access$3(Jucator.this, rs2.getInt("Tokens"));
                            Jucator.access$4(Jucator.this, rs2.getInt("Vote"));
                            Jucator.access$5(Jucator.this, rs2.getString("Language"));
                            Jucator.access$6(Jucator.this, rs2.getString("Nick"));
                        }
                        rs3.close();
                    }
                    rs2.close();
                    prepared2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        this.getInstance();
        scheduler.scheduleSyncDelayedTask((Plugin) instance, (Runnable) runnable, Main.o * 20L);
        this.setGiveall(false);
        this.setHelpop(false);
        this.setSpy(false);
        this.setMsgToggle(false);
        this.setReply("NU");
        this.setPt_command("NU");
        this.setTp_time(0);
        this.setTpcooldown(0);
        this.setChatcooldown(0);
        this.setTp("NU");
        this.setSpam("NU");
        Jucator.jucatorObjects.add(this);
    }

    public Player getPlayer() {
        return this.p;
    }

    public void setPlayer(final Player p) {
        this.p = p;
    }

    public String getLanguage() {
        return "RO";
    }

    public void setLanguage(final String language) {
        this.language = language;
        Database.getData().setString("Global", "Language", "Player", this.p.getName(), language);
    }

    public void registerDefault() {
        final String playername = this.p.getName();
        if (!Database.getData().isRegistered(this.getInstance().server, "Player", this.p.getName())) {
            final List<String> values = new ArrayList<String>();
            values.add("Player-" + playername);
            values.add("Kills-0");
            values.add("Deaths-0");
            values.add("Money-" + this.getInstance().getConfig().getInt("Start Money"));
            values.add("Ip- ");
            values.add("IgnoreList- ");
            values.add("LastOnline- ");
            values.add("LastLocation- ");
            Database.getData().register(this.getInstance().server, values);
        }
        if (!Database.getData().isRegistered("Global", "Player", this.p.getName())) {
            final List<String> values = new ArrayList<String>();
            values.add("Player-" + playername);
            values.add("Tokens-0");
            values.add("Nick-" + playername);
            values.add("Vote-0");
            values.add("Language-RO");
            Database.getData().register("Global", values);
        }
    }

    public Location getBack() {
        return this.back;
    }

    public void setBack(final Location back) {
        this.back = back;
    }

    public Integer getMoney() {
        this.money = (int) this.getInstance().economy.getBalance(p.getName());
        return this.money;
    }

    public void setMoney(final Integer money) {
        this.money = money;
        EconomyResponse r = this.getInstance().economy.withdrawPlayer(p.getName(), this.getInstance().economy.getBalance(p.getName()));
        if (r.transactionSuccess()) { }
        r = this.getInstance().economy.depositPlayer(p.getName(), money);
        if (r.transactionSuccess()) { }
        Database.getData().setInt(this.getInstance().server, "Money", "Player", this.p.getName(), money);
    }

    public void setHome(final String name) {
        final File homeFile = new File(this.getInstance().getDataFolder(), "home.yml");
        final FileConfiguration home = (FileConfiguration) YamlConfiguration.loadConfiguration(homeFile);
        final List<String> newlist = new ArrayList<String>();
        for (final String s : home.getStringList("Home." + this.p.getName())) {
            newlist.add(s);
        }
        newlist.add(String.valueOf(name) + ":" + this.seterilizeLocation(this.p.getLocation()));
        home.set("Home." + this.p.getName() + "." + name, (Object) this.seterilizeLocation(this.p.getLocation()));
        this.saveConfig(homeFile, home);
    }

    public Location getHome(final String name) {
        final File homeFile = new File(this.getInstance().getDataFolder(), "home.yml");
        final FileConfiguration home = (FileConfiguration) YamlConfiguration.loadConfiguration(homeFile);
        return this.unSeterilizeLocation(home.getString("Home." + this.p.getName() + "." + name));
    }

    public Location getHome(final int i) {
        final List<String> newlist = this.getHomes();
        if (newlist.isEmpty() || newlist.size() < i + 1) {
            return null;
        }
        return this.getHome(newlist.get(i));
    }

    public void deleteHome(final String name) {
        final File homeFile = new File(this.getInstance().getDataFolder(), "home.yml");
        final FileConfiguration home = (FileConfiguration) YamlConfiguration.loadConfiguration(homeFile);
        home.set("Home." + this.p.getName() + "." + name, (Object) null);
        this.saveConfig(homeFile, home);
    }

    public List<String> getHomes() {
        final File homeFile = new File(this.getInstance().getDataFolder(), "home.yml");
        final FileConfiguration home = (FileConfiguration) YamlConfiguration.loadConfiguration(homeFile);
        final List<String> newlist = new ArrayList<String>();
        try {
            for (final String str : home.getConfigurationSection("Home." + this.p.getName()).getKeys(false)) {
                newlist.add(str);
            }
        } catch (Exception ex) {
        }
        return newlist;
    }

    public boolean isGiveall() {
        return this.giveall;
    }

    public void setGiveall(final boolean giveall) {
        this.giveall = giveall;
    }

    public boolean isGod() {
        return this.god;
    }

    public void setGod(final boolean god) {
        this.god = god;
    }

    public boolean isHelpop() {
        return this.helpop;
    }

    public void setHelpop(final boolean helpop) {
        this.helpop = helpop;
    }

    public long getHelpopCounter() {
        return this.helpop_time;
    }

    public void setHelpopCounter(final long helpop_time) {
        this.helpop_time = helpop_time;
    }

    public String getIgnore() {
        return this.ignore;
    }

    public void addIgnore(final String ignore) {
        this.ignore = String.valueOf(this.ignore) + " " + ignore;
        Database.getData().setString(this.getInstance().server, "IgnoreList", "Player", this.p.getName(), this.ignore);
    }

    public void setIgnore(final String ignore) {
        this.ignore = ignore;
    }

    public void removeIgnore(final String d) {
        this.ignore = this.ignore.replaceAll(" " + d, "");
        Database.getData().setString(this.getInstance().server, "IgnoreList", "Player", this.p.getName(), this.ignore);
    }

    public void giveJail(final Integer totalSecs, final String motiv, final String admin) {
        this.giveJail(totalSecs);
        final int days = totalSecs / 86400;
        final int hours = totalSecs / 3600;
        final int minutes = totalSecs % 3600 / 60;
        final int seconds = totalSecs % 60;
        if (days == 1) {
            this.sendMessage(this.p, this.getMessage(this.getLanguage(this.p), "JAIL").replace("<REASON>", motiv).replace("<ADMIN>", admin).replace("<TIME>", String.valueOf(days) + this.getMessage(this.getLanguage(this.p), "DAY")));
        } else if (days > 1) {
            this.sendMessage(this.p, this.getMessage(this.getLanguage(this.p), "JAIL").replace("<REASON>", motiv).replace("<ADMIN>", admin).replace("<TIME>", String.valueOf(days) + this.getMessage(this.getLanguage(this.p), "DAYS")));
        } else if (hours == 1) {
            this.sendMessage(this.p, this.getMessage(this.getLanguage(this.p), "JAIL").replace("<REASON>", motiv).replace("<ADMIN>", admin).replace("<TIME>", String.valueOf(hours) + this.getMessage(this.getLanguage(this.p), "HOUR")));
        } else if (hours > 1) {
            this.sendMessage(this.p, this.getMessage(this.getLanguage(this.p), "JAIL").replace("<REASON>", motiv).replace("<ADMIN>", admin).replace("<TIME>", String.valueOf(hours) + this.getMessage(this.getLanguage(this.p), "HOURS")));
        } else if (minutes == 1) {
            this.sendMessage(this.p, this.getMessage(this.getLanguage(this.p), "JAIL").replace("<REASON>", motiv).replace("<ADMIN>", admin).replace("<TIME>", String.valueOf(minutes) + this.getMessage(this.getLanguage(this.p), "MINUTE")));
        } else if (minutes > 1) {
            this.sendMessage(this.p, this.getMessage(this.getLanguage(this.p), "JAIL").replace("<REASON>", motiv).replace("<ADMIN>", admin).replace("<TIME>", String.valueOf(minutes) + this.getMessage(this.getLanguage(this.p), "MINUTES")));
        } else if (seconds == 1) {
            this.sendMessage(this.p, this.getMessage(this.getLanguage(this.p), "JAIL").replace("<REASON>", motiv).replace("<ADMIN>", admin).replace("<TIME>", String.valueOf(seconds) + this.getMessage(this.getLanguage(this.p), "SECOND")));
        } else if (seconds > 1) {
            this.sendMessage(this.p, this.getMessage(this.getLanguage(this.p), "JAIL").replace("<REASON>", motiv).replace("<ADMIN>", admin).replace("<TIME>", String.valueOf(seconds) + this.getMessage(this.getLanguage(this.p), "SECONDS")));
        }
    }

    public void giveJail(final int time) {
        this.getInstance().jails.set("Jail." + this.p.getName(), System.currentTimeMillis() + time * 1000);
        this.saveConfig(this.getInstance().jailsFile, this.getInstance().jails);
    }

    public boolean haveJail() {
        return this.getInstance().jails.contains("Jail." + this.p.getName()) && this.getInstance().jails.getLong("Jail." + this.p.getName()) > System.currentTimeMillis();
    }

    public int getJailTime() {
        final long i = this.getInstance().jails.getLong("Jail." + this.p.getName());
        return (int) (i - System.currentTimeMillis())/1000;
    }

    public void giveKit(final String kit, final int time) {
        final File kitsFile = new File(this.getInstance().getDataFolder(), "kits.yml");
        final FileConfiguration kits = (FileConfiguration) YamlConfiguration.loadConfiguration(kitsFile);
        kits.set("Kit Time." + kit.toLowerCase() + "." + this.p.getName(), System.currentTimeMillis() + time*1000);
        this.saveConfig(kitsFile, kits);
    }

    public boolean haveKit(final String kit) {
        final File kitsFile = new File(this.getInstance().getDataFolder(), "kits.yml");
        final FileConfiguration kits = (FileConfiguration) YamlConfiguration.loadConfiguration(kitsFile);
        return kits.contains("Kit Time." + kit.toLowerCase() + "." + this.p.getName()) && kits.getInt("Kit Time." + kit.toLowerCase() + "." + this.p.getName()) != 0;
    }

    public int getKitTime(final String kit) {
        final File kitsFile = new File(this.getInstance().getDataFolder(), "kits.yml");
        final FileConfiguration kits = (FileConfiguration) YamlConfiguration.loadConfiguration(kitsFile);
        final long i = kits.getLong("Kit Time." + kit.toLowerCase() + "." + this.p.getName());
        return (int) (i - System.currentTimeMillis())/1000;
    }

    public long getKitTimeL(final String kit) {
        final File kitsFile = new File(this.getInstance().getDataFolder(), "kits.yml");
        final FileConfiguration kits = (FileConfiguration) YamlConfiguration.loadConfiguration(kitsFile);
        final long i = kits.getLong("Kit Time." + kit.toLowerCase() + "." + this.p.getName());
        return i;
    }

    public String getReply() {
        return this.reply;
    }

    public void setReply(final String reply) {
        this.reply = reply;
    }

    public boolean isSpy() {
        return this.spy;
    }

    public void setSpy(final boolean spy) {
        this.spy = spy;
    }

    public boolean isMsgToggle() {
        return this.msgtoggle;
    }

    public void setMsgToggle(final boolean msgtoggle) {
        this.msgtoggle = msgtoggle;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(final String nick) {
        this.nick = nick;
        Database.getData().setString("Global", "Nick", "Player", this.p.getName(), nick);
    }

    public Material getPt_item() {
        return this.pt_item;
    }

    public void setPt_item(final Material pt_item) {
        this.pt_item = pt_item;
    }

    public String getPt_command() {
        return this.pt_command;
    }

    public void setPt_command(final String pt_command) {
        this.pt_command = pt_command;
    }

    public boolean isTpToggle() {
        return this.tptoggle;
    }

    public void setTpToggle(final boolean tptoggle) {
        this.tptoggle = tptoggle;
    }

    public Integer getTp_time() {
        return this.tp_time;
    }

    public void setTp_time(final Integer tp_time) {
        this.tp_time = tp_time;
    }

    public String getTp() {
        return this.tp;
    }

    public void setTp(final String tp) {
        this.tp = tp;
    }

    public boolean isTpa() {
        return this.tpa;
    }

    public void setTpa(final boolean tpa) {
        this.tpa = tpa;
    }

    public boolean isVanish() {
        return this.vanish;
    }

    public void setVanish(final boolean vanish) {
        this.vanish = vanish;
    }

    public Location getTploc() {
        return this.tploc;
    }

    public void setTploc(final Location tploc) {
        this.tploc = tploc;
    }

    public String getSpam() {
        return this.spam;
    }

    public void setSpam(final String spam) {
        this.spam = spam;
    }

    public Integer getTokens() {
        return this.tokens;
    }

    public void setTokens(final Integer tokens) {
        this.tokens = tokens;
        Database.getData().setInt("Global", "Tokens", "Player", this.p.getName(), tokens);
    }

    public Integer getVote() {
        return this.vote;
    }

    public void setVote(final Integer vote) {
        this.vote = vote;
    }

    public int getTpcooldown() {
        return this.tpcooldown;
    }

    public void setTpcooldown(final int tpcooldown) {
        this.tpcooldown = tpcooldown;
    }

    public long getChatcooldown() {
        return this.chatcooldown;
    }

    public void setChatcooldown(final long chattcooldown) {
        this.chatcooldown = chattcooldown;
    }

    static /* synthetic */ void access$1(final Jucator jucator, final Integer money) {
        jucator.money = money;
    }

    static /* synthetic */ void access$2(final Jucator jucator, final String ignore) {
        jucator.ignore = ignore;
    }

    static /* synthetic */ void access$3(final Jucator jucator, final Integer tokens) {
        jucator.tokens = tokens;
    }

    static /* synthetic */ void access$4(final Jucator jucator, final Integer vote) {
        jucator.vote = vote;
    }

    static /* synthetic */ void access$5(final Jucator jucator, final String language) {
        jucator.language = language;
    }

    static /* synthetic */ void access$6(final Jucator jucator, final String nick) {
        jucator.nick = nick;
    }

}
