package me.Browk.qCore.Utile;

import me.Browk.qCore.*;
import me.clip.placeholderapi.*;
import java.text.*;

import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scoreboard.*;
import me.Browk.qCore.Custom.*;
import java.net.*;
import org.bukkit.command.*;
import me.Browk.qCore.MySQL.*;
import org.bukkit.plugin.*;
import org.bukkit.permissions.*;
import java.sql.*;
import org.apache.commons.lang3.*;
import org.bukkit.configuration.file.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;
import java.util.*;
import org.bukkit.block.*;
import org.bukkit.util.*;
import org.bukkit.entity.*;
import java.lang.reflect.*;
import java.io.*;

public interface Utile {
    default String replace(final String mesaj) {
        return mesaj.replace("&", "§").replace(">>", "»").replace("<3", "\u2764").replace("*", "\u2022");
    }

    default Main getInstance() {
        return Main.intstanta;
    }

    default void sendLogger(final String mesaj) {
        this.getInstance().getServer().getConsoleSender().sendMessage(this.replace(mesaj));
    }

    default void sendLoggerConsole(final String mesaj) {
        this.getInstance().getServer().getConsoleSender().sendMessage(this.replace(mesaj));
    }

    default void teleportPlayer(Player p, Location loc) {
        p.teleport(loc, PlayerTeleportEvent.TeleportCause.COMMAND);
        getJucator(p).setTploc(null);
        getJucator(p).setTpcooldown(0);
    }

    default void sendActionBar(final Player player, final String message) {
        boolean useOldMethods = false;
        String nmsver = Bukkit.getServer().getClass().getPackage().getName();
        nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
        if (nmsver.equalsIgnoreCase("v1_12")) {
            new PreAction(player, message);
            return;
        }
        if (nmsver.equalsIgnoreCase("v1_8_R1") || nmsver.equalsIgnoreCase("v1_7_")) {
            useOldMethods = true;
        }
        try {
            final Class<?> c1 = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
            final Object p = c1.cast(player);
            final Class<?> c2 = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
            final Class<?> c3 = Class.forName("net.minecraft.server." + nmsver + ".Packet");
            Object ppoc;
            if (useOldMethods) {
                final Class<?> c4 = Class.forName("net.minecraft.server." + nmsver + ".ChatSerializer");
                final Class<?> c5 = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
                final Method m3 = c4.getDeclaredMethod("a", String.class);
                final Object cbc = c5.cast(m3.invoke(c4, "{\"text\": \"" + message + "\"}"));
                ppoc = c2.getConstructor(c5, Byte.TYPE).newInstance(cbc, (byte) 2);
            } else {
                final Class<?> c4 = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
                final Class<?> c5 = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
                final Object o = c4.getConstructor(String.class).newInstance(message);
                ppoc = c2.getConstructor(c5, Byte.TYPE).newInstance(o, (byte) 2);
            }
            final Method m4 = c1.getDeclaredMethod("getHandle", (Class<?>[]) new Class[0]);
            final Object h = m4.invoke(p, new Object[0]);
            final Field f1 = h.getClass().getDeclaredField("playerConnection");
            final Object pc = f1.get(h);
            final Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c3);
            m5.invoke(pc, ppoc);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    default Boolean noPlayer(final String ent) {
        final Player t = Bukkit.getPlayer(ent);
        if (t == null) {
            return !Bukkit.getOnlinePlayers().contains(t);
        }
        return false;
    }

    default String replace(final Player p, final String mesaj) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(p, mesaj);
        }
        return mesaj;
    }

    default boolean allow(final String ip) {
        return this.getInstance().getConfig().getStringList("IP").contains(ip);
    }

    default boolean allow(final InetSocketAddress addr) {
        return this.allow(addr.getAddress().getHostAddress());
    }

    default boolean allow(final InetAddress addr) {
        return this.allow(addr.getHostAddress());
    }

    default int isNumber(final String s) {
        int amount = 0;
        try {
            amount = Integer.valueOf(s);
        } catch (Exception e) {
            return 0;
        }
        return amount;
    }

    default void saveConfig(final File file, final FileConfiguration config) {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    default Jucator getJucator(final Player p) {
        for (final Jucator j : Jucator.jucatorObjects) {
            if (j.getPlayer() == p) {
                return j;
            }
        }
        Jucator j = new Jucator(p);
        return j;
    }

    default Connection getConnection() {
        return this.getInstance().mysql.getConnection();
    }

    default Sound getSound() {
        try {
            return Sound.valueOf("NOTE_PLING");
        } catch (Exception e) {
            return Sound.valueOf("BLOCK_NOTE_PLING");
        }
    }

    default void startMySQL() {
        final List<String> integers = new ArrayList<String>();
        integers.add("Kills");
        integers.add("Deaths");
        integers.add("Money");
        final List<String> strings = new ArrayList<String>();
        strings.add("Player");
        strings.add("IgnoreList");
        strings.add("Rank");
        strings.add("Ip");
        strings.add("LastOnline");
        strings.add("LastLocation");
        strings.add("Time");
        Database.getData().createTable(this.getInstance().server, integers, strings);
        final List<String> integers2 = new ArrayList<String>();
        integers2.add("Tokens");
        integers2.add("Vote");
        final List<String> strings2 = new ArrayList<String>();
        strings2.add("Player");
        strings2.add("Nick");
        strings2.add("Language");
        Database.getData().addColum("Tokens", false);
        Database.getData().createTable("Global", integers2, strings2);
    }

    default int randInt(final int min, final int max) {
        final Random rand = new Random();
        final int randomNum = rand.nextInt(max - min + 1) + min;
        return randomNum;
    }

    default FileConfiguration getRankFile() {
        return this.getInstance().group;
    }

    default FileConfiguration getUserFile() {
        return this.getInstance().user;
    }

    default void saveUser() {
        this.saveConfig(this.getInstance().userFile, this.getInstance().user);
    }

    default void saveGroup() {
        this.saveConfig(this.getInstance().groupFile, this.getInstance().group);
    }

    default String shortList(final List<String> string, final String c) {
        if (string.isEmpty() || string == null) {
            return "NONE";
        }
        final StringBuilder l = new StringBuilder();
        for (final String a : string) {
            if (l.length() > 0) {
                l.append(c);
                l.append(String.valueOf(c) + ", " + c);
            }
            l.append(a);
        }
        return this.replace(new StringBuilder().append(l.toString()).toString());
    }

    default String getRank(final String p) {
        return Database.getData().getString(this.getInstance().server, "Rank", "Player", p);
    }

    default void balanceTop(final CommandSender s, final int page) {
        final List<String> z = new ArrayList<String>();
        s.sendMessage(this.replace("&e------------------------------------------"));
        s.sendMessage(this.replace("&e&l>> &fTopul balantelor &e-- &fPagina: " + (page + 1)));
        try {
            final Connection connection = this.getConnection();
            final PreparedStatement select = connection.prepareStatement("SELECT Money, Player FROM " + this.getInstance().server + " ORDER BY " + "Money" + " DESC LIMIT 100;");
            final ResultSet rs = select.executeQuery();
            int zahl = 1;
            boolean finish = false;
            while (rs.next() && !finish) {
                if (!z.contains(String.valueOf(rs.getInt(1)) + "," + rs.getString(2))) {
                    z.add(String.valueOf(rs.getInt(1)) + "," + rs.getString(2));
                    if (++zahl <= 100) {
                        continue;
                    }
                    finish = true;
                }
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        int g = page * 10;
        for (final String h : z) {
            ++g;
            if (z.size() == g - 1) {
                break;
            }
            final String[] r = z.get(g - 1).split(",");
            s.sendMessage(String.valueOf(g) + ". " + r[1] + ": $" + r[0]);
            if (g == 10 * (page + 1)) {
                break;
            }
        }
        s.sendMessage(this.replace("&e------------------------------------------"));
    }

    default void help(final CommandSender sender, final List<String> map, int page, final int pageLength) {
        sender.sendMessage(this.replace("&e------------------------------------------"));
        sender.sendMessage(StringUtils.center(this.replace("&e- &6Help &e-"), 68));
        int i = 0;
        int k = 0;
        --page;
        for (final String e : map) {
            if (sender.hasPermission("essentials." + e)) {
                ++k;
                if (page * pageLength + i + 1 != k || k == page * pageLength + pageLength + 1) {
                    continue;
                }
                ++i;
                sender.sendMessage(this.replace("&6>> &f/" + e + " &8- &f" + this.getInstance().getCommand(e).getDescription()));
            }
        }
    }

    default String seterilizeLocation(final Location l) {
        return String.valueOf(l.getWorld().getName()) + "," + l.getX() + "," + l.getY() + "," + l.getZ() + "," + l.getYaw() + "," + l.getPitch();
    }

    default Location unSeterilizeLocation(final String s) {
        final String[] st = s.split(",");
        return new Location(Bukkit.getWorld(st[0]), (double) Double.valueOf(st[1]), (double) Double.valueOf(st[2]), (double) Double.valueOf(st[3]), (float) Float.valueOf(st[4]), (float) Float.valueOf(st[5]));
    }

    default String frumos(final String mesaj) {
        final String b = String.valueOf(new StringBuilder().append(mesaj.trim().toLowerCase().charAt(0)).toString().toUpperCase()) + mesaj.trim().toLowerCase().substring(1);
        return b;
    }

    default List<String> getWarps() {
        final File warpsFile = new File(this.getInstance().getDataFolder(), "warps.yml");
        final FileConfiguration warps = (FileConfiguration) YamlConfiguration.loadConfiguration(warpsFile);
        final List<String> l = new ArrayList<String>();
        try {
            for (final String str : warps.getConfigurationSection("Warp").getKeys(false)) {
                l.add(this.frumos(str));
            }
        } catch (Exception ex) {
        }
        return l;
    }

    default void setWarp(final Player p, final String name) {
        final File warpsFile = new File(this.getInstance().getDataFolder(), "warps.yml");
        final FileConfiguration warps = (FileConfiguration) YamlConfiguration.loadConfiguration(warpsFile);
        warps.set("Warp." + this.frumos(name), (Object) this.seterilizeLocation(p.getLocation()));
        this.saveConfig(warpsFile, warps);
    }

    default void deleteWarp(final String name) {
        final File warpsFile = new File(this.getInstance().getDataFolder(), "warps.yml");
        final FileConfiguration warps = (FileConfiguration) YamlConfiguration.loadConfiguration(warpsFile);
        warps.set("Warp." + this.frumos(name), (Object) null);
        this.saveConfig(warpsFile, warps);
    }

    default String getWarp(final String name) {
        final File warpsFile = new File(this.getInstance().getDataFolder(), "warps.yml");
        final FileConfiguration warps = (FileConfiguration) YamlConfiguration.loadConfiguration(warpsFile);
        return warps.getString("Warp." + this.frumos(name));
    }

    default void setJail(final Player p) {
        final File jailsFile = new File(this.getInstance().getDataFolder(), "jails.yml");
        final FileConfiguration jails = (FileConfiguration) YamlConfiguration.loadConfiguration(jailsFile);
        jails.set("Location", (Object) this.seterilizeLocation(p.getLocation()));
        this.saveConfig(jailsFile, jails);
    }

    default void deleteJail() {
        final File jailsFile = new File(this.getInstance().getDataFolder(), "jails.yml");
        final FileConfiguration jails = (FileConfiguration) YamlConfiguration.loadConfiguration(jailsFile);
        jails.set("Location", (Object) null);
        this.saveConfig(jailsFile, jails);
    }

    default Location getJail() {
        final File jailsFile = new File(this.getInstance().getDataFolder(), "jails.yml");
        final FileConfiguration jails = (FileConfiguration) YamlConfiguration.loadConfiguration(jailsFile);
        return this.unSeterilizeLocation(jails.getString("Location"));
    }

    default void setSpawn(final Player p) {
        final File spawnFile = new File(this.getInstance().getDataFolder(), "spawn.yml");
        final FileConfiguration spawn = (FileConfiguration) YamlConfiguration.loadConfiguration(spawnFile);
        spawn.set("Location", (Object) this.seterilizeLocation(p.getLocation()));
        this.saveConfig(spawnFile, spawn);
    }

    default void deleteSpawn() {
        final File spawnFile = new File(this.getInstance().getDataFolder(), "spawn.yml");
        final FileConfiguration spawn = (FileConfiguration) YamlConfiguration.loadConfiguration(spawnFile);
        spawn.set("Location", (Object) null);
        this.saveConfig(spawnFile, spawn);
    }

    default Location getSpawn() {
        final File spawnFile = new File(this.getInstance().getDataFolder(), "spawn.yml");
        final FileConfiguration spawn = (FileConfiguration) YamlConfiguration.loadConfiguration(spawnFile);
        if(!spawn.isSet("Location")) {
            return null;
        }
        return this.unSeterilizeLocation(spawn.getString("Location"));
    }

    default boolean contains(final String msg, final String what) {
        return msg.equalsIgnoreCase(what);
    }

    default Enchantment getEnchantByString(final String e) {
        if (this.contains(e, "alldamage") || this.contains(e, "alldmg") || this.contains(e, "sharpness") || this.contains(e, "sharp")) {
            return Enchantment.DAMAGE_ALL;
        }
        if (this.contains(e, "arthropodsdamage") || this.contains(e, "ardmg") || this.contains(e, "baneofarthropods")) {
            return Enchantment.DAMAGE_ARTHROPODS;
        }
        if (this.contains(e, "undeaddamage") || this.contains(e, "smite")) {
            return Enchantment.DAMAGE_UNDEAD;
        }
        if (this.contains(e, "digspeed") || this.contains(e, "efficiency")) {
            return Enchantment.DIG_SPEED;
        }
        if (this.contains(e, "dura") || this.contains(e, "durability") || this.contains(e, "unbreaking")) {
            return Enchantment.DURABILITY;
        }
        if (this.contains(e, "fireaspect") || this.contains(e, "fire")) {
            return Enchantment.FIRE_ASPECT;
        }
        if (this.contains(e, "knockback")) {
            return Enchantment.KNOCKBACK;
        }
        if (this.contains(e, "blockslootbonus") || this.contains(e, "fortune")) {
            return Enchantment.LOOT_BONUS_BLOCKS;
        }
        if (this.contains(e, "mobslootbonus") || this.contains(e, "mobloot") || this.contains(e, "looting")) {
            return Enchantment.LOOT_BONUS_MOBS;
        }
        if (this.contains(e, "oxygen") || this.contains(e, "respiration")) {
            return Enchantment.OXYGEN;
        }
        if (this.contains(e, "protection") || this.contains(e, "prot")) {
            return Enchantment.PROTECTION_ENVIRONMENTAL;
        }
        if (this.contains(e, "explosionsprotection") || this.contains(e, "expprot") || this.contains(e, "blastprotection ")) {
            return Enchantment.PROTECTION_EXPLOSIONS;
        }
        if (this.contains(e, "fallprotection") || this.contains(e, "fallprot") || this.contains(e, "featherfall")) {
            return Enchantment.PROTECTION_FALL;
        }
        if (this.contains(e, "fireprotection") || this.contains(e, "fireprot")) {
            return Enchantment.PROTECTION_FIRE;
        }
        if (this.contains(e, "projectileprotection") || this.contains(e, "projprot")) {
            return Enchantment.PROTECTION_FALL;
        }
        if (this.contains(e, "silktouch")) {
            return Enchantment.SILK_TOUCH;
        }
        if (this.contains(e, "waterworker ") || this.contains(e, "aquaaffinity")) {
            return Enchantment.WATER_WORKER;
        }
        if (this.contains(e, "firearrow") || this.contains(e, "flame")) {
            return Enchantment.ARROW_FIRE;
        }
        if (this.contains(e, "arrowdamage") || this.contains(e, "power")) {
            return Enchantment.ARROW_DAMAGE;
        }
        if (this.contains(e, "arrowknockback") || this.contains(e, "punch") || this.contains(e, "infarrows")) {
            return Enchantment.ARROW_KNOCKBACK;
        }
        if (this.contains(e, "infinitearrows") || this.contains(e, "infarrows") || this.contains(e, "infinity")) {
            return Enchantment.ARROW_INFINITE;
        }
        return null;
    }

    default List<String> getEnchantmentName(final Enchantment e) {
        final List<String> l = new ArrayList<String>();
        if (e.equals((Object) Enchantment.DAMAGE_ALL)) {
            l.add("alldamage");
            l.add("alldmg");
            l.add("sharpness");
            l.add("sharp");
        }
        if (e.equals((Object) Enchantment.DAMAGE_ARTHROPODS)) {
            l.add("arthropodsdamage");
            l.add("ardmg");
            l.add("baneofarthropods");
        }
        if (e.equals((Object) Enchantment.DAMAGE_UNDEAD)) {
            l.add("undeaddamage");
            l.add("smite");
        }
        if (e.equals((Object) Enchantment.DIG_SPEED)) {
            l.add("digspeed");
            l.add("efficiency");
        }
        if (e.equals((Object) Enchantment.DURABILITY)) {
            l.add("dura");
            l.add("durability");
            l.add("unbreaking");
        }
        if (e.equals((Object) Enchantment.FIRE_ASPECT)) {
            l.add("fireaspect");
            l.add("fire");
        }
        if (e.equals((Object) Enchantment.KNOCKBACK)) {
            l.add("knockback");
        }
        if (e.equals((Object) Enchantment.LOOT_BONUS_BLOCKS)) {
            l.add("blockslootbonus");
            l.add("fortune");
        }
        if (e.equals((Object) Enchantment.LOOT_BONUS_MOBS)) {
            l.add("mobslootbonus");
            l.add("mobloot");
            l.add("looting");
        }
        if (e.equals((Object) Enchantment.OXYGEN)) {
            l.add("oxygen");
            l.add("respiration");
        }
        if (e.equals((Object) Enchantment.PROTECTION_ENVIRONMENTAL)) {
            l.add("protection");
            l.add("prot");
        }
        if (e.equals((Object) Enchantment.PROTECTION_EXPLOSIONS)) {
            l.add("explosionsprotection");
            l.add("expprot");
            l.add("blastprotection");
        }
        if (e.equals((Object) Enchantment.PROTECTION_FALL)) {
            l.add("fallprotection");
            l.add("fallprot");
            l.add("featherfall");
            l.add("featherfalling");
        }
        if (e.equals((Object) Enchantment.PROTECTION_FIRE)) {
            l.add("fireprot");
            l.add("fireprotection");
        }
        if (e.equals((Object) Enchantment.PROTECTION_PROJECTILE)) {
            l.add("projectileprotection");
            l.add("projprot");
        }
        if (e.equals((Object) Enchantment.SILK_TOUCH)) {
            l.add("silktouch");
        }
        if (e.equals((Object) Enchantment.WATER_WORKER)) {
            l.add("waterworker");
            l.add("aquaaffinity");
        }
        if (e.equals((Object) Enchantment.ARROW_FIRE)) {
            l.add("firearrow");
            l.add("flame");
        }
        if (e.equals((Object) Enchantment.ARROW_DAMAGE)) {
            l.add("arrowdamage");
            l.add("power");
        }
        if (e.equals((Object) Enchantment.ARROW_KNOCKBACK)) {
            l.add("arrowknockback");
            l.add("arrowkb");
            l.add("punch");
        }
        if (e.equals((Object) Enchantment.ARROW_INFINITE)) {
            l.add("infinitearrows");
            l.add("infarrows");
            l.add("infinity");
        }
        return l;
    }

    default Boolean isGoodEncahnt(final ItemStack i, final Enchantment e) {
        return e.canEnchantItem(i);
    }

    default boolean betweenExclusive(final int x, final int min, final int max) {
        return x > min && x < max;
    }

    default int aranjeazDouble(final double d) {
        final DecimalFormat sdf = new DecimalFormat("##");
        return Integer.valueOf(sdf.format(d));
    }

    default Location getHome(final String p, final String name) {
        final File homeFile = new File(this.getInstance().getDataFolder(), "home.yml");
        final FileConfiguration home = (FileConfiguration) YamlConfiguration.loadConfiguration(homeFile);
        return this.unSeterilizeLocation(home.getString("Home." + p + "." + name));
    }

    default Location getHome(final String p, final int i) {
        final List<String> newlist = this.getHomes(p);
        if (newlist.isEmpty() || newlist.size() < i) {
            return null;
        }
        return this.getHome(p, newlist.get(i));
    }

    default List<String> getHomes(final String p) {
        final File homeFile = new File(this.getInstance().getDataFolder(), "home.yml");
        final FileConfiguration home = (FileConfiguration) YamlConfiguration.loadConfiguration(homeFile);
        final List<String> newlist = new ArrayList<String>();
        try {
            for (final String str : home.getConfigurationSection("Home." + p).getKeys(false)) {
                newlist.add(str);
            }
        } catch (Exception ex) {
        }
        return newlist;
    }

    default String timeformat(final int totalSecs) {
        String s1 = " zile ";
        String s2 = " ore ";
        String s3 = " minute ";
        String s4 = " secunde";
        if(totalSecs / 86400 == 1) {
            s1 = " zi ";
        }
        if(totalSecs / 3600 == 1) {
            s2 = " ora ";
        }
        if(totalSecs / 86400 == 1) {
            s3 = " minut ";
        }
        if(totalSecs / 86400 == 1) {
            s4 = " secunda";
        }
        final String days = (totalSecs / 86400 == 0) ? "" : (String.valueOf(totalSecs / 86400) + s1);
        final String hours = (totalSecs % 86400 / 3600 == 0) ? "" : (String.valueOf(totalSecs % 86400 / 3600) + s2);
        final String minutes = (totalSecs % 3600 / 60 == 0) ? "" : (String.valueOf(totalSecs % 3600 / 60) + s3);
        final String seconds = String.valueOf(totalSecs % 60) + s4;
        final String timeString = String.valueOf(days) + hours + minutes + seconds;
        return timeString;
    }

    default String InventoryToString(final Inventory invInventory) {
        String serialization = String.valueOf(invInventory.getSize()) + ";";
        for (int i = 0; i < invInventory.getSize(); ++i) {
            final ItemStack is = invInventory.getItem(i);
            if (is != null) {
                String serializedItemStack = new String();
                final String isType = String.valueOf(is.getType().getId());
                serializedItemStack = String.valueOf(serializedItemStack) + "t@" + isType;
                if (is.getDurability() != 0) {
                    String isDurability = String.valueOf(is.getDurability());
                    serializedItemStack = String.valueOf(serializedItemStack) + ":d@" + isDurability;
                }
                if (is.getAmount() != 1) {
                    final String isAmount = String.valueOf(is.getAmount());
                    serializedItemStack = String.valueOf(serializedItemStack) + ":a@" + isAmount;
                }
                final Map<Enchantment, Integer> isEnch = (Map<Enchantment, Integer>) is.getEnchantments();
                if (isEnch.size() > 0) {
                    for (final Map.Entry<Enchantment, Integer> ench : isEnch.entrySet()) {
                        serializedItemStack = String.valueOf(serializedItemStack) + ":e@" + ench.getKey().getId() + "@" + ench.getValue();
                    }
                }
                if (is.getItemMeta().getDisplayName() != null) {
                    final String[] itemDisplayName = is.getItemMeta().getDisplayName().split(" ");
                    serializedItemStack = String.valueOf(serializedItemStack) + ":n@";
                    for (int m = 0; m < itemDisplayName.length; ++m) {
                        serializedItemStack = String.valueOf(serializedItemStack) + itemDisplayName[m] + "=";
                    }
                }
                serialization = String.valueOf(serialization) + i + "#" + serializedItemStack + ";";
            }
        }
        return serialization;
    }

    default Inventory StringToInventory(final String invString) {
        if(invString == null) {
            return null;
        }
        final String[] serializedBlocks = invString.split(";");
        final Inventory deserializedInventory = Bukkit.createInventory((InventoryHolder) null, 54, " ");
        for (int i = 1; i < serializedBlocks.length; ++i) {
            final String[] serializedBlock = serializedBlocks[i].split("#");
            final int stackPosition = Integer.valueOf(serializedBlock[0]);
            if (stackPosition < deserializedInventory.getSize()) {
                ItemStack is = null;
                Boolean createdItemStack = false;
                final String[] serializedItemStack = serializedBlock[1].split(":");
                String[] array;
                for (int length = (array = serializedItemStack).length, j = 0; j < length; ++j) {
                    final String itemInfo = array[j];
                    final String[] itemAttribute = itemInfo.split("@");
                    if (itemAttribute[0].equals("t")) {
                        is = new ItemStack(Material.getMaterial((int) Integer.valueOf(itemAttribute[1])));
                        createdItemStack = true;
                    } else if (itemAttribute[0].equals("d") && createdItemStack) {
                        is.setDurability((short) Short.valueOf(itemAttribute[1]));
                    } else if (itemAttribute[0].equals("a") && createdItemStack) {
                        is.setAmount((int) Integer.valueOf(itemAttribute[1]));
                    } else if (itemAttribute[0].equals("e") && createdItemStack) {
                        is.addEnchantment(Enchantment.getById((int) Integer.valueOf(itemAttribute[1])), (int) Integer.valueOf(itemAttribute[2]));
                    } else if (itemAttribute[0].equals("n") && createdItemStack) {
                        final ItemMeta meta = is.getItemMeta();
                        final String[] displayName = itemAttribute[1].split("=");
                        String finalName = "";
                        for (int m = 0; m < displayName.length; ++m) {
                            if (m == displayName.length - 1) {
                                finalName = String.valueOf(finalName) + displayName[m];
                            } else {
                                finalName = String.valueOf(finalName) + displayName[m] + " ";
                            }
                        }
                        meta.setDisplayName(finalName);
                        is.setItemMeta(meta);
                    }
                }
                deserializedInventory.setItem(stackPosition, is);
            }
        }
        return deserializedInventory;
    }

    default void createKit(final Player p, final String name, final int time) {
        if(!getKits().isEmpty() && getKits().contains(name)) {
            return;
        }
        final File kitsFile = new File(this.getInstance().getDataFolder(), "kits.yml");
        final FileConfiguration kits = (FileConfiguration) YamlConfiguration.loadConfiguration(kitsFile);
        kits.set("Kit." + name.toLowerCase() + ".Time", (Object) time);
        kits.set("Kit." + name.toLowerCase() + ".Items", (Object) this.InventoryToString((Inventory) p.getInventory()));
        this.saveConfig(kitsFile, kits);
    }

    default List<String> getKits() {
        final File kitsFile = new File(this.getInstance().getDataFolder(), "kits.yml");
        final FileConfiguration kits = (FileConfiguration) YamlConfiguration.loadConfiguration(kitsFile);
        final List<String> l = new ArrayList<String>();
        if(!kitsFile.exists()) {
            return l;
        }
        for (final String str : kits.getConfigurationSection("Kit").getKeys(false)) {
            l.add(str.toLowerCase());
        }
        return l;
    }

    default void giveKit(final Player p, final String kit) {
        final File kitsFile = new File(this.getInstance().getDataFolder(), "kits.yml");
        final FileConfiguration kits = (FileConfiguration) YamlConfiguration.loadConfiguration(kitsFile);
        final Inventory inv = this.StringToInventory(kits.getString("Kit." + kit.toLowerCase() + ".Items"));
        ItemStack[] contents;
        for (int length = (contents = inv.getContents()).length, k = 0; k < length; ++k) {
            final ItemStack i = contents[k];
            if (i != null && i.getType() != Material.AIR) {
                p.getInventory().addItem(new ItemStack[]{i});
            }
        }
        final Jucator j = this.getJucator(p);
        j.giveKit(kit.toLowerCase(), kits.getInt("Kit." + kit.toLowerCase() + ".Time"));
    }

    default Inventory previewKit(final String kit) {
        final File kitsFile = new File(this.getInstance().getDataFolder(), "kits.yml");
        final FileConfiguration kits = (FileConfiguration) YamlConfiguration.loadConfiguration(kitsFile);
        final Inventory inv = this.StringToInventory(kits.getString("Kit." + kit.toLowerCase() + ".Items"));
        ItemStack[] contents;
        final Inventory inv2 = Bukkit.createInventory((InventoryHolder) null, 36, this.replace("&8Vezi pachetul " + kit + ":"));
        for (int length = (contents = inv.getContents()).length, k = 0; k < length; ++k) {
            final ItemStack i = contents[k];
            if (i != null && i.getType() != Material.AIR) {
                inv2.addItem(new ItemStack[]{i});
            }
        }
        return inv2;
    }

    default String getStaff(final Player p) {
        final StringBuilder online = new StringBuilder();
        final Collection<? extends Player> players = (Collection<? extends Player>) Bukkit.getOnlinePlayers();
        final List<String> s = new ArrayList<String>();
        for (final Player player : players) {
            if (!player.hasPermission("essentials.staff")) {
                continue;
            }
            if (!p.canSee(player)) {
                continue;
            }
            if (online.length() > 0) {
                online.append(this.replace(", "));
            }
            online.append(this.replace(player.getName()));
            s.add(player.getName());
        }
        if (s.size() == 0) {
            return "PULA";
        }
        return online.toString();
    }

    default String getDonor(final Player p) {
        final StringBuilder online = new StringBuilder();
        final Collection<? extends Player> players = (Collection<? extends Player>) Bukkit.getOnlinePlayers();
        final List<String> s = new ArrayList<String>();
        for (final Player player : players) {
            if (!player.hasPermission("essentials.donator")) {
                continue;
            }
            if (!p.canSee(player)) {
                continue;
            }
            if (online.length() > 0) {
                online.append(this.replace(", "));
            }
            online.append(this.replace(player.getName()));
            s.add(player.getName());
        }
        if (s.size() == 0) {
            return "PULA";
        }
        return online.toString();
    }

    default String getStaff() {
        final StringBuilder online = new StringBuilder();
        final Collection<? extends Player> players = (Collection<? extends Player>) Bukkit.getOnlinePlayers();
        for (final Player player : players) {
            if (!player.hasPermission("essentials.staff")) {
                continue;
            }
            if (online.length() > 0) {
                online.append(this.replace(", "));
            }
            online.append(this.replace(player.getName()));
        }
        return online.toString();
    }

    default List<String> getNear(final Player p) {
        final List<String> found = new ArrayList<String>();
        for (final Entity entity : p.getLocation().getWorld().getEntities()) {
            if (entity instanceof Player) {
                final Player p2 = (Player) entity;
                if (p2.getName().equalsIgnoreCase(p.getName()) || !this.isInBorder(p.getLocation(), entity.getLocation(), 100)) {
                    continue;
                }
                found.add(String.valueOf(p2.getName()) + " (&3" + this.aranjeazDouble(p.getLocation().distance(p2.getLocation())) + "&b)");
            }
        }
        return found;
    }

    default boolean isInBorder(final Location center, final Location notCenter, final int range) {
        final int x = center.getBlockX();
        final int z = center.getBlockZ();
        final int x2 = notCenter.getBlockX();
        final int z2 = notCenter.getBlockZ();
        return x2 < x + range && z2 < z + range && x2 > x - range && z2 > z - range;
    }

    default boolean isRepairAble(final Material material) {
        return material != null && (material == Material.WOOD_AXE || material == Material.WOOD_HOE || material == Material.WOOD_SWORD || material == Material.WOOD_SPADE || material == Material.WOOD_PICKAXE || material == Material.STONE_AXE || material == Material.STONE_HOE || material == Material.STONE_SWORD || material == Material.STONE_SPADE || material == Material.STONE_PICKAXE || material == Material.GOLD_AXE || material == Material.GOLD_HOE || material == Material.GOLD_SWORD || material == Material.GOLD_SPADE || material == Material.GOLD_PICKAXE || material == Material.IRON_AXE || material == Material.IRON_HOE || material == Material.IRON_SWORD || material == Material.IRON_SPADE || material == Material.IRON_PICKAXE || material == Material.DIAMOND_AXE || material == Material.DIAMOND_HOE || material == Material.DIAMOND_SWORD || material == Material.DIAMOND_SPADE || material == Material.DIAMOND_PICKAXE || material == Material.DIAMOND_BOOTS || material == Material.DIAMOND_CHESTPLATE || material == Material.DIAMOND_HELMET || material == Material.DIAMOND_LEGGINGS || material == Material.IRON_BOOTS || material == Material.IRON_CHESTPLATE || material == Material.IRON_HELMET || material == Material.IRON_LEGGINGS || material == Material.GOLD_BOOTS || material == Material.GOLD_CHESTPLATE || material == Material.GOLD_HELMET || material == Material.GOLD_LEGGINGS || material == Material.DIAMOND_BOOTS || material == Material.DIAMOND_CHESTPLATE || material == Material.DIAMOND_HELMET || material == Material.DIAMOND_LEGGINGS || material == Material.CHAINMAIL_BOOTS || material == Material.CHAINMAIL_CHESTPLATE || material == Material.CHAINMAIL_HELMET || material == Material.CHAINMAIL_LEGGINGS || material.name().equals("ELYTRA") || material == Material.FISHING_ROD || material == Material.SHEARS || material == Material.FLINT_AND_STEEL || material == Material.BOW);
    }

    default Block getTargetBlock(final Player player, final int range) {
        final BlockIterator iter = new BlockIterator((LivingEntity) player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }

    default ItemStack creazaItem(final Material material, final String nume, final int numar) {
        final ItemStack item = new ItemStack(material, numar);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(this.replace(nume));
        meta.spigot().setUnbreakable(true);
        item.setItemMeta(meta);
        return item;
    }

    default boolean isInteger(final String s) {
        return this.isInteger(s, 10);
    }

    default boolean isInteger(final String s, final int radix) {
        if (s.isEmpty()) {
            return false;
        }
        for (int i = 0; i < s.length(); ++i) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) {
                    return false;
                }
            } else if (Character.digit(s.charAt(i), radix) < 0) {
                return false;
            }
        }
        return true;
    }

    default String getUptime() {
        final long timeCurrent = System.currentTimeMillis();
        final long timeElapsed = timeCurrent - this.getInstance().timeStartup;
        final long totalSeconds = timeElapsed / 1000L;
        final long totalMinutes = totalSeconds / 60L;
        final long totalHours = totalMinutes / 60L;
        final long totalDays = totalHours / 24L;
        final long fmtSeconds = totalSeconds - totalMinutes * 60L;
        final long fmtMinutes = totalMinutes - totalHours * 60L;
        final long fmtHours = totalHours - totalDays * 24L;
        String upSeconds = String.valueOf(fmtSeconds);
        String upMinutes = String.valueOf(fmtMinutes);
        String upHours = String.valueOf(fmtHours);
        if(fmtSeconds == 1) {
            upSeconds = " §e" + fmtSeconds + " §esecunda";
        } else {
            upSeconds = " §e" + fmtSeconds + " §esecunde";
        }
        if(fmtMinutes == 1) {
            upMinutes = " §e" + fmtMinutes + " §eminut";
        } else {
            upMinutes = " §e" + fmtMinutes + " §eminute";
        }
        if(fmtHours == 1) {
            upHours = " §e" + fmtHours + " §eora";
        } else {
            upHours = " §e" + fmtHours + " §eore";
        }
        final String msg = String.valueOf(upHours) + upMinutes + upSeconds;
        return msg;
    }

    default String getTime(final long l) {
        final long totalSeconds = l / 1000L;
        final long totalMinutes = totalSeconds / 60L;
        final long totalHours = totalMinutes / 60L;
        final long totalDays = totalHours / 24L;
        final long totalWeek = totalDays / 7L;
        final long fmtSeconds = totalSeconds - totalMinutes * 60L;
        final long fmtMinutes = totalMinutes - totalHours * 60L;
        final long fmtHours = totalHours - totalDays * 24L;
        final long fmtDays = totalDays - totalWeek * 7L;
        final String upSeconds = String.valueOf(fmtSeconds);
        String upMinutes = String.valueOf(fmtMinutes);
        String upHours = String.valueOf(fmtHours);
        String upDays = String.valueOf(fmtHours);
        if(fmtSeconds == 1) {
            upMinutes = " §e" + fmtSeconds + " §eminut";
        } else {
            upMinutes = " §e" + fmtSeconds + " §eminute";
        }
        if(fmtMinutes == 1) {
            upHours = " §e" + fmtMinutes + " §eora";
        } else {
            upHours = " §e" + fmtMinutes + " §eore";
        }
        if(fmtHours == 1) {
            upDays = " §e" + fmtHours + " §ezi";
        } else {
            upDays = " §e" + fmtHours + " §ezile";
        }
        final String msg = String.valueOf(upDays) + upHours + upMinutes;
        return msg;
    }

    default String getFormat(final long s) {
        if (s == 1L) {
            return "";
        }
        return "s";
    }

    default void sendTitle(final Player player, final Integer fadeIn, final Integer stay, final Integer fadeOut, String title, String subtitle) {
        try {
            if (title != null) {
                title = this.replace(title);
                title = title.replaceAll("%player%", player.getDisplayName());
                Object e = this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
                Object chatTitle = this.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
                Constructor subtitleConstructor = this.getNMSClass("PacketPlayOutTitle").getConstructor(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], this.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                Object titlePacket = subtitleConstructor.newInstance(e, chatTitle, fadeIn, stay, fadeOut);
                this.sendPacket(player, titlePacket);
                e = this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
                chatTitle = this.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
                subtitleConstructor = this.getNMSClass("PacketPlayOutTitle").getConstructor(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], this.getNMSClass("IChatBaseComponent"));
                titlePacket = subtitleConstructor.newInstance(e, chatTitle);
                this.sendPacket(player, titlePacket);
            }
            if (subtitle != null) {
                subtitle = this.replace(subtitle);
                subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
                Object e = this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
                Object chatSubtitle = this.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
                Constructor subtitleConstructor = this.getNMSClass("PacketPlayOutTitle").getConstructor(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], this.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                Object subtitlePacket = subtitleConstructor.newInstance(e, chatSubtitle, fadeIn, stay, fadeOut);
                this.sendPacket(player, subtitlePacket);
                e = this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
                chatSubtitle = this.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + subtitle + "\"}");
                subtitleConstructor = this.getNMSClass("PacketPlayOutTitle").getConstructor(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], this.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                subtitlePacket = subtitleConstructor.newInstance(e, chatSubtitle, fadeIn, stay, fadeOut);
                this.sendPacket(player, subtitlePacket);
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }
    }

    default void sendPacket(final Player player, final Object packet) {
        try {
            final Object handle = player.getClass().getMethod("getHandle", (Class<?>[]) new Class[0]).invoke(player, new Object[0]);
            final Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", this.getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    default Class<?> getNMSClass(final String name) {
        final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    default int getColorByString(final String s) {
        if (s.startsWith("&a")) {
            return 5;
        }
        if (s.startsWith("&2")) {
            return 13;
        }
        if (s.startsWith("&e")) {
            return 4;
        }
        if (s.startsWith("&6")) {
            return 1;
        }
        if (s.startsWith("&9")) {
            return 11;
        }
        if (s.startsWith("&5")) {
            return 10;
        }
        if (s.startsWith("&d")) {
            return 2;
        }
        if (s.startsWith("&3")) {
            return 9;
        }
        if (s.startsWith("&b")) {
            return 3;
        }
        if (s.startsWith("&f")) {
            return 0;
        }
        if (s.startsWith("&7")) {
            return 8;
        }
        if (s.startsWith("&8")) {
            return 7;
        }
        return 15;
    }

    default String getChatColorByString(final short s) {
        if (s == 5) {
            return "&a";
        }
        if (s == 13) {
            return "&2";
        }
        if (s == 4) {
            return "&e";
        }
        if (s == 1) {
            return "&6";
        }
        if (s == 11) {
            return "&9";
        }
        if (s == 10) {
            return "&5";
        }
        if (s == 2) {
            return "&d";
        }
        if (s == 9) {
            return "&3";
        }
        if (s == 3) {
            return "&b";
        }
        if (s == 0) {
            return "&f";
        }
        if (s == 8) {
            return "&7";
        }
        if (s == 7) {
            return "&8";
        }
        return "";
    }

    public static class PreAction {
        private final String packageVersion;

        public PreAction(final Player player, final String message) {
            this.packageVersion = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            try {
                final Object chatComponentText = this.getNMSClass("ChatComponentText").getConstructor(String.class).newInstance(message);
                final Object chatMessageType = this.getNMSClass("ChatMessageType").getField("GAME_INFO").get(null);
                final Object packetPlayOutChat = this.getNMSClass("PacketPlayOutChat").getConstructor(this.getNMSClass("IChatBaseComponent"), this.getNMSClass("ChatMessageType")).newInstance(chatComponentText, chatMessageType);
                final Object getHandle = player.getClass().getMethod("getHandle", (Class<?>[]) new Class[0]).invoke(player, new Object[0]);
                final Object playerConnection = getHandle.getClass().getField("playerConnection").get(getHandle);
                playerConnection.getClass().getMethod("sendPacket", this.getNMSClass("Packet")).invoke(playerConnection, packetPlayOutChat);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private Class<?> getNMSClass(final String nmsClassName) {
            try {
                return Class.forName("net.minecraft.server." + this.packageVersion + "." + nmsClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
