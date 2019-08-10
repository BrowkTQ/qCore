package me.Browk.qCore.Utile;

import me.clip.placeholderapi.external.*;
import me.Browk.qCore.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import me.Browk.qCore.Custom.*;
import org.bukkit.*;
import me.clip.placeholderapi.*;

public class PlaceholderImport extends EZPlaceholderHook implements Utile {
    public PlaceholderImport() {
        super((Plugin) Main.intstanta, "qcore");
    }

    public String onPlaceholderRequest(final Player p, final String identifier) {
        final Jucator d = this.getJucator(p);
        if (identifier.equals("money")) {
            return String.valueOf(d.getMoney());
        }
        if (identifier.equals("nume")) {
            return String.valueOf(d.getNick());
        }
        if (identifier.equals("tokens")) {
            return String.valueOf(d.getTokens());
        }
        if (identifier.equals("dropparty")) {
            return String.valueOf(this.getInstance().dropparty);
        }
        if (identifier.startsWith("kitcooldown_")) {
            String kit = identifier.replace("kitcooldown_", "");
            if(!identifier.equalsIgnoreCase("") && getKits().contains(kit)) {
                return this.timeformat(d.getKitTime(kit));
            }
            return "Poate fi luat.";
        }
        return null;
    }

    public String replace(final Player p, final String mesaj) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(p, mesaj);
        }
        return mesaj;
    }
}
