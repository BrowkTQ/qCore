package me.Browk.qCore;

import com.intellectualcrafters.plot.object.PlotPlayer;
import com.plotsquared.bukkit.util.BukkitUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.*;
import me.Browk.qCore.MySQL.*;
import org.bukkit.event.*;
import org.bukkit.command.*;
import java.io.*;
import org.bukkit.configuration.file.*;
import me.Browk.qCore.Language.*;
import org.bukkit.plugin.*;
import me.Browk.qCore.Evenimente.*;
import me.Browk.qCore.Utile.*;
import me.Browk.qCore.Comenzi.*;
import org.apache.commons.lang.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import me.Browk.qCore.Custom.*;
import org.bukkit.*;

import java.util.*;

public class Main extends JavaPlugin implements Utile, Message {
    public static Main intstanta;
    public MySQL mysql;
    public Database database;
    private List<Listener> events;
    private HashMap<String, CommandExecutor> commands;
    public File userFile;
    public FileConfiguration user;
    public File groupFile;
    public FileConfiguration group;
    public File helpFile;
    public FileConfiguration helpc;
    public File jailsFile;
    public FileConfiguration jails;
    public String server;
    public List<String> help;
    public Economy economy;
    public long timeStartup;
    public int dropparty;
    public static int o;
    int i;
    Calendar calendar;
    int counter;

    static {
        Main.o = 0;
    }

    public Main() {
        this.events = new ArrayList<Listener>();
        this.commands = new HashMap<String, CommandExecutor>();
        this.jailsFile = new File(this.getDataFolder(), "jails.yml");
        this.helpFile = new File(this.getDataFolder(), "help.yml");
        this.helpc = (FileConfiguration) YamlConfiguration.loadConfiguration(this.helpFile);
        this.jails = (FileConfiguration) YamlConfiguration.loadConfiguration(this.jailsFile);
        this.help = new ArrayList<String>();
        this.timeStartup = 0L;
        this.dropparty = 0;
        this.i = 0;
    }

    public void onEnable() {
        if(!checkWhitelisted()) {
            Bukkit.shutdown();
            return;
        }
        if (!getDataFolder().exists()) {
            try {
                getDataFolder().mkdir();
            } catch(SecurityException se){
            }
        }
        setupEconomy();
        this.saveDefaultConfig();
        this.mysql = new MySQL();
        Main.intstanta = this;
        this.database = new Database();
        this.server = this.getConfig().getString("Server");
        this.timeStartup = System.currentTimeMillis();
        this.mysql.connect(getConfig().getString("Host"), getConfig().getString("Database"), getConfig().getInt("Port"), getConfig().getString("User"), getConfig().getString("Password"));
        this.startMySQL();
        Language.l.load();
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin) this, (Runnable) new Lag(), 100L, 1L);
        try {
            helpc.options().header("qCore by Browk_\n");
            helpc.addDefault("prefix", "&eqStaff &8» &f");
            helpc.addDefault("Header", "&8&m--+------------------------------------+--&r");
            helpc.addDefault("Footer", "&8&m--+------------------------------------+--&r");
            ArrayList<String> listHelp = new ArrayList<String>();
            listHelp.add("&e >> &f&l/money &8- &fAfiseaza balanta ta.");
            listHelp.add("&e >> &f&l/hub &8- &fTeleporteaza-te in hub.");
            helpc.addDefault("Help-Pages.1", listHelp);
            helpc.options().copyDefaults(true);
            helpc.options().copyHeader(true);
            helpc.save(helpFile);
        } catch (IOException ex) {

        }
        if(Bukkit.getPluginManager().isPluginEnabled("PlotSquared")) {
            this.events.add((Listener) new ChatEventPS());
        } else if(Bukkit.getPluginManager().isPluginEnabled("ASkyBlock")) {
            this.events.add((Listener) new ChatEventSB());
        } else if(Bukkit.getPluginManager().isPluginEnabled("Factions")) {
            this.events.add((Listener) new ChatEventF());
        } else {
            this.events.add((Listener) new ChatEvent());
        }
        this.events.add((Listener) new JoinEvent());
        this.events.add((Listener) new QuitEvent());
        this.events.add((Listener) new KickEvent());
        this.events.add((Listener) new LoginEvent());
        this.events.add((Listener) new DeathEvent());
        this.events.add((Listener) new TeleportEvent());
        this.events.add((Listener) new CommandEvent());
        this.events.add((Listener) new InventoryEvent());
        this.events.add((Listener) new DamageEvent());
        this.events.add((Listener) new FoodEvent());
        this.events.add((Listener) new InteractEvent());
        this.events.add((Listener) new SignEvent());
        this.events.add((Listener) new PreChatEvent());
        if (this.getServer().getPluginManager().isPluginEnabled("Votifier")) {
            this.events.add((Listener) new VoteEvent());
        }
        if (this.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderImport().hook();
        }
        this.commands.put("qcore", (CommandExecutor) new Core());
        this.commands.put("adminchat", (CommandExecutor) new AdminChat());
        this.commands.put("back", (CommandExecutor) new Back());
        this.commands.put("balance", (CommandExecutor) new Balance());
        this.commands.put("balancetop", (CommandExecutor) new BalanceTop());
        this.commands.put("broadcast", (CommandExecutor) new Broadcast());
        this.commands.put("clearinventory", (CommandExecutor) new Clear());
        this.commands.put("delhome", (CommandExecutor) new Delhome());
        this.commands.put("delwarp", (CommandExecutor) new Delwarp());
        this.commands.put("deljail", (CommandExecutor) new Deljail());
        this.commands.put("eco", (CommandExecutor) new Eco());
        this.commands.put("enchant", (CommandExecutor) new Enchant());
        this.commands.put("enderchest", (CommandExecutor) new Enderchest());
        this.commands.put("exp", (CommandExecutor) new Exp());
        this.commands.put("fly", (CommandExecutor) new Fly());
        this.commands.put("feed", (CommandExecutor) new Feed());
        this.commands.put("getpos", (CommandExecutor) new Getpos());
        this.commands.put("give", (CommandExecutor) new Give());
        this.commands.put("giveall", (CommandExecutor) new Giveall());
        this.commands.put("gamemode", (CommandExecutor) new Gamemode());
        this.commands.put("god", (CommandExecutor) new God());
        this.commands.put("hat", (CommandExecutor) new Hat());
        this.commands.put("heal", (CommandExecutor) new Heal());
        this.commands.put("help", (CommandExecutor) new Help());
        this.commands.put("helpop", (CommandExecutor) new Helpop());
        this.commands.put("home", (CommandExecutor) new Home());
        this.commands.put("ignore", (CommandExecutor) new Ignore());
        this.commands.put("invsee", (CommandExecutor) new Invsee());
        this.commands.put("item", (CommandExecutor) new me.Browk.qCore.Comenzi.Item());
        this.commands.put("itemdb", (CommandExecutor) new Itemdb());
        this.commands.put("jail", (CommandExecutor) new Jail());
        this.commands.put("kickall", (CommandExecutor) new Kickall());
        this.commands.put("kill", (CommandExecutor) new Kill());
        this.commands.put("killall", (CommandExecutor) new Killall());
        this.commands.put("kit", (CommandExecutor) new Kit());
        this.commands.put("kitpreview", (CommandExecutor) new KitPreview());
        this.commands.put("previewkit", (CommandExecutor) new KitPreview());
        this.commands.put("list", (CommandExecutor) new ListA());
        this.commands.put("more", (CommandExecutor) new More());
        this.commands.put("motd", (CommandExecutor) new Motd());
        this.commands.put("msg", (CommandExecutor) new Msg());
        this.commands.put("msgtoggle", (CommandExecutor) new Msgtoggle());
        this.commands.put("near", (CommandExecutor) new Near());
        this.commands.put("nick", (CommandExecutor) new Nick());
        this.commands.put("pay", (CommandExecutor) new Pay());
        this.commands.put("ptime", (CommandExecutor) new Ptime());
        this.commands.put("realname", (CommandExecutor) new Realname());
        this.commands.put("repair", (CommandExecutor) new Repair());
        this.commands.put("reply", (CommandExecutor) new Reply());
        this.commands.put("seen", (CommandExecutor) new Seen());
        this.commands.put("sethome", (CommandExecutor) new Sethome());
        this.commands.put("setjail", (CommandExecutor) new Setjail());
        this.commands.put("setwarp", (CommandExecutor) new Setwarp());
        this.commands.put("sudo", (CommandExecutor) new Sudo());
        this.commands.put("setspawn", (CommandExecutor) new Setspawn());
        this.commands.put("spawn", (CommandExecutor) new Spawn());
        this.commands.put("spawner", (CommandExecutor) new Spawner());
        this.commands.put("spawnmob", (CommandExecutor) new Spawnmob());
        this.commands.put("skull", (CommandExecutor) new Skull());
        this.commands.put("socialspy", (CommandExecutor) new Socialspy());
        this.commands.put("speed", (CommandExecutor) new Speed());
        this.commands.put("suicide", (CommandExecutor) new Suicide());
        this.commands.put("thunder", (CommandExecutor) new Thunder());
        this.commands.put("time", (CommandExecutor) new Time());
        this.commands.put("top", (CommandExecutor) new Top());
        this.commands.put("tp", (CommandExecutor) new Tp());
        this.commands.put("tpall", (CommandExecutor) new Tpall());
        this.commands.put("tphere", (CommandExecutor) new Tphere());
        this.commands.put("tpa", (CommandExecutor) new Tpa());
        this.commands.put("tpahere", (CommandExecutor) new Tpahere());
        this.commands.put("tpdeny", (CommandExecutor) new Tpdeny());
        this.commands.put("tpaccept", (CommandExecutor) new Tpaccept());
        this.commands.put("tpo", (CommandExecutor) new Tpo());
        this.commands.put("tpohere", (CommandExecutor) new Tpohere());
        this.commands.put("tppos", (CommandExecutor) new Tppos());
        this.commands.put("tptoggle", (CommandExecutor) new Tptoggle());
        this.commands.put("vanish", (CommandExecutor) new Vanish());
        this.commands.put("warp", (CommandExecutor) new Warp());
        this.commands.put("workbench", (CommandExecutor) new Workbench());
        this.commands.put("whois", (CommandExecutor) new Whois());
        this.commands.put("weather", (CommandExecutor) new me.Browk.qCore.Comenzi.Weather());
        this.commands.put("lag", (CommandExecutor) new Mem());
        this.commands.put("vote", (CommandExecutor) new Vote());
        this.commands.put("stats", (CommandExecutor) new Stats());
        this.commands.put("tokens", (CommandExecutor) new Tokens());
        for (final Listener c : this.events) {
            this.getServer().getPluginManager().registerEvents(c, (Plugin) this);
        }
        if(!this.commands.isEmpty()) {
            for (final Map.Entry<String, CommandExecutor> k : this.commands.entrySet()) {
                if (k != null) {
                    this.getCommand((String) k.getKey()).setExecutor((CommandExecutor) k.getValue());
                    this.help.add(k.getKey());
                }
            }
        }
        Collections.sort(this.help);
        this.sendLogger("&6&m======================================");
        this.sendLogger("&e           _____");
        this.sendLogger("&e          / ____|");
        this.sendLogger("&e   __ _  | |        ___    _ __    ___ ");
        this.sendLogger("&e  / _` | | |       / _ \\  | '__|  / _ \\");
        this.sendLogger("&e | (_| | | |____  | (_) | | |    |  __/");
        this.sendLogger("&e  \\__, |  \\_____|  \\___/  |_|     \\___|");
        this.sendLogger("&e     | |");
        this.sendLogger("&e     |_|");
        this.sendLogger("");
        if (this.events.size() == 1) {
            this.sendLogger(StringUtils.center("&fS-a incarcat &6" + this.events.size() + " eveniment&f.", 34));
        } else {
            this.sendLogger(StringUtils.center("&fS-au incarcat &6" + this.events.size() + " evenimente&f.", 34));
        }
        if (this.commands.size() == 1) {
            this.sendLogger(StringUtils.center("&fS-a incarcat &6" + this.commands.size() + " comanda&f.", 34));
        } else {
            this.sendLogger(StringUtils.center("&fS-au incarcat &6" + this.commands.size() + " comenzi&f.", 34));
        }
        this.sendLogger("&6&m======================================");
        this.startRunnable();
        if (this.getConfig().getBoolean("Auto Restart")) {
            this.init();
        }
        this.getConfig().options().copyDefaults(true);
        this.getConfig().options().copyHeader(true);
        this.saveConfig();
    }

    public boolean checkWhitelisted() {
        try {
            MySQL mysql1 = new MySQL();
            mysql1.connect("88.99.231.213", "qbrowk_lp200", 3306, "qbrowk_lp200", "Dqj^3_u(Wzbo");
            System.out.println("Se verifica IP-ul:" + InetAddress.getLocalHost().getHostAddress());
            PreparedStatement preparedStatement = mysql1.getConnection().prepareStatement("SELECT IP FROM whitelist WHERE IP='" + InetAddress.getLocalHost().getHostAddress() + "';");
            ResultSet localResultSet = preparedStatement.executeQuery();
            if(localResultSet.next()) {
                localResultSet.close();
                preparedStatement.close();
                mysql1.disconnect();
                mysql1 = null;
                return true;
            }
            localResultSet.close();
            preparedStatement.close();
            mysql1.disconnect();
            mysql1 = null;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nu s-a putut efectua verificarea plugin-ului. Contacteaza-l pe Browk_!");
            getServer().shutdown();
            return false;
        } catch (UnknownHostException e2) {
            e2.printStackTrace();
            System.out.println("Nu s-a putut efectua verificarea plugin-ului. Contacteaza-l pe Browk_!");
            getServer().shutdown();
            return false;
        }
        System.out.println("Nu s-a putut efectua verificarea plugin-ului. Contacteaza-l pe Browk_!");
        getServer().shutdown();
        return false;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    public void startRunnable() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) this, (Runnable) new Runnable() {
            @Override
            public void run() {
                Main.this.mysql.disconnect();
                Main.this.mysql.connect(getConfig().getString("Host"), getConfig().getString("Database"), getConfig().getInt("Port"), getConfig().getString("User"), getConfig().getString("Password"));
                Database.getData().setInt(Main.this.server, "Money", "Player", "NuFolosiAcestCont", Main.this.randInt(0, 100));
            }
        }, 20L, 20L * 180L);
    }

    public void onDisable() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            getJucator(p).setMoney(getJucator(p).getMoney());
        }
        if(this.mysql != null) {
            this.mysql.disconnect();
        }
    }

    public void init() {

        Bukkit.getScheduler().runTaskAsynchronously((Plugin) this, (Runnable)new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Main.this.sleep(60000);
                    Main.this.calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Bucharest"));
                    final int hour = Main.this.calendar.get(11);
                    final int minute = Main.this.calendar.get(12);
                    if (hour == 4 && minute <= 5) {
                        System.out.println(ChatColor.RED + "Restart in 10 minute!");
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)Main.intstanta, (Runnable)new Runnable() {
                            @Override
                            public void run() {
                                for (final Player p : Bukkit.getOnlinePlayers()) {
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6► &fServerul se va restarta in &e10 minute&f!"));
                                }
                            }
                        }, 0L);
                        Main.this.sleep(300000);
                        System.out.println(ChatColor.RED + "Restart in 5 minute!");
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)Main.intstanta, (Runnable)new Runnable() {
                            @Override
                            public void run() {
                                for (final Player p : Bukkit.getOnlinePlayers()) {
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6► &fServerul se va restarta in &e5 minute&f!"));
                                }
                            }
                        }, 0L);
                        Main.this.sleep(240000);
                        System.out.println(ChatColor.RED + "Restart intr-un minut!");
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)Main.intstanta, (Runnable)new Runnable() {
                            @Override
                            public void run() {
                                for (final Player p : Bukkit.getOnlinePlayers()) {
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6► &fServerul se va restarta &eintr-un minut&f!"));
                                }
                            }
                        }, 0L);
                        Main.this.sleep(55000);
                        System.out.println(ChatColor.RED + "Restart in 5 secunde!");
                        Main.this.counter = 5;
                        while (Main.this.counter >= 1) {
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)Main.intstanta, (Runnable)new Runnable() {
                                @Override
                                public void run() {
                                    for (final Player p : Bukkit.getOnlinePlayers()) {
                                        if(counter > 1) {
                                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6► &fServerul se va restarta in &e<X> secunde&f!").replace("<X>", String.valueOf(counter)));
                                        } else if(counter == 1) {
                                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6► &fServerul se va restarta &eintr-o secunda&f!"));
                                        }
                                    }
                                }
                            }, 0L);
                            Main.this.sleep(1000);
                            final Main this$0 = Main.this;
                            --this$0.counter;
                        }
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)Main.intstanta, (Runnable)new Runnable() {
                            @Override
                            public void run() {
                                for (final Player p : Bukkit.getOnlinePlayers()) {
                                    p.kickPlayer("§cRestart! Revenim imediat.");
                                }
                                System.out.println(ChatColor.RED + "Se restarteaza server-ul! (PlayAutoRestart)");
                                Bukkit.shutdown();
                            }
                        }, 0L);
                    }
                }
            }
        });
    }

    public void sleep(final long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
        }
    }
}
