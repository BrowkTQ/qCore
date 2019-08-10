package me.Browk.qCore.Language;

import me.Browk.qCore.Utile.*;
import java.io.*;
import org.bukkit.configuration.file.*;
import java.util.*;

public class Language implements Utile {
    public static Language l;

    static {
        Language.l = new Language();
    }

    public void load() {
        this.addMessage("RO", "NO PERMISSION", "&6► &cNu ai permisiunea sa utilizezi aceasta comanda.");
        this.addMessage("RO", "CONSOLE", "&6► &fTrebuie sa fi &cjucator&f.");
        this.addMessage("RO", "USAGE", "&6►&f Foloseste: &e/<COMMAND>");
        this.addMessage("RO", "NO PLAYER", "&6► &fJucatorul nu exista!");
        this.addMessage("RO", "STAFFCHAT", "&6[&eSTAFF&6] <PREFIX> &7<PLAYER>: &f<MESSAGE>");
        this.addMessage("RO", "NO BACK", "&6► &fNu ai unde sa te teleportezi!");
        this.addMessage("RO", "BACK AFTER DEATH", "&6► &fFoloseste comanda &6/back&f pentru a ajunge locatia decesului!");
        this.addMessage("RO", "BACK DEAD", "&6► &fAi fost teleporteaza la locatia unde ai murit!");
        this.addMessage("RO", "BACK TP", "&6► &fAi fost teleportat la locatia anterioara!");
        this.addMessage("RO", "NO AVAILABLE", "&6► &cAceasta comanda nu este disponibila!");
        this.addMessage("RO", "BALANCE", "&6► &fBanii tai:&e <MONEY> ⛁");
        this.addMessage("RO", "BALANCE PLAYER", "&6► &fBanii lui <PLAYER>:&e <MONEY>$");
        this.addMessage("RO", "BROADCAST", "&8[&ePlay&8] &c<MESSAGE>");
        this.addMessage("RO", "CLEAR", "&6► &fSe curata inventarul lui &e<PLAYER>&7.");
        this.addMessage("RO", "NO HOME", "&6► &fCasa nu a fost gasita.");
        this.addMessage("RO", "NO WARP", "&6► &fWarpul nu a fost gasit.");
        this.addMessage("RO", "NO JAIL", "&6► &fJailul nu a fost gasit.");
        this.addMessage("RO", "DELETE HOME", "&6► &fCasa a fost stearsa.");
        this.addMessage("RO", "DELETE WARP", "&6► &fWarpul a fost sters.");
        this.addMessage("RO", "DELETE JAIL", "&6► &fJailul a fost sters.");
        this.addMessage("RO", "ECO RESET", "&6► &fI-ai resetat bani lui &e<PLAYER>&f.");
        this.addMessage("RO", "ECO GIVE", "&6► &fI-ai dat lui &e<PLAYER> &6<MONEY> ⛁&f.");
        this.addMessage("RO", "ECO TAKE", "&6► &fI-ai luat lui &e<PLAYER> &6<MONEY> ⛁&f.");
        this.addMessage("RO", "NO ITEM", "&6► &fItemul nu a fost gasit.");
        this.addMessage("RO", "NO ENCHANT", "&6► &fEnchantul nu a fost gasit.");
        this.addMessage("RO", "ENCHANT SUCCES", "&6► &fAi adaugat enchantul &e<ENCHANT>&f de level&e <LEVEL> &fpe &e<ITEM>&f.");
        this.addMessage("RO", "EXP RESET", "&6► &fI-ai resetat xpul lui &e<PLAYER>&f.");
        this.addMessage("RO", "EXP GIVE", "&6► &fI-ai dat lui &e<PLAYER> &6<XP> xp&f.");
        this.addMessage("RO", "EXP TAKE", "&6► &fI-ai luat lui &e<PLAYER> &6<XP> xp&f.");
        this.addMessage("RO", "EXP", "&6► &fExperienta lui &e<PLAYER> &feste &6<XP> xp&f.");
        this.addMessage("RO", "FLY", "&6► &fModul de zburat a fost &e<FLY>&f pentru &e<PLAYER>&f.");
        this.addMessage("RO", "FEED", "&6► &fAi fost hranit!");
        this.addMessage("RO", "FEED PLAYER", "&6► &fL-ai hrait pe &e<PLAYER>&f.");
        this.addMessage("RO", "GETPOS", "&6► &fLocatia lui &e<PLAYER>&f este &7X: &6<X>&f, &7Y: &6<Y>&f, &7Z: &6<Z>");
        this.addMessage("RO", "ON", "&aactivat");
        this.addMessage("RO", "OFF", "&cdezactivat");
        this.addMessage("RO", "GIVE", "&6► &fI-ai dat &6<AMOUNT>&f bucata(ti) de &6<ITEM>&f lui &e<PLAYER>&f.");
        this.addMessage("RO", "GAMEMODE", "&6► &fModul de joc a fost schimbat in &6<GAMEMODE>&f.");
        this.addMessage("RO", "GAMEMODE PLAYER", "&6► &fAi actualizat modul de joc lui &e<PLAYER>&f.");
        this.addMessage("RO", "GOD", "&6► &fModul GOD a fost &e<GOD>&f pentru &e<PLAYER>&f.");
        this.addMessage("RO", "HEAL", "&6► &fAi fost vindecat!");
        this.addMessage("RO", "HEAL PLAYER", "&6► &fL-ai vindecat pe &e<PLAYER>&f.");
        this.addMessage("RO", "HAT", "&6► &fBucura-te de noua ta palarie!");
        this.addMessage("RO", "HELP", "&fScrie &6/help <PAGE>&f pentru a vedea pagina urmatoare.");
        this.addMessage("RO", "PAGE NOT VALID", "&6► &cPagina solicitata nu exista!");
        this.addMessage("RO", "HELPOP", "&e&l&k!!&r &4[&cHELP ME&4] &7<PLAYER>: &f<MESSAGE>");
        this.addMessage("RO", "HELPOP SENT", "&6► &fMesajul tau a fost trimis staffului!");
        this.addMessage("RO", "HELPOP WAIT", "&6► &fTrebuie sa astepti &c<TIME>&f secunde pentru a trimite un nou mesaj!");
        this.addMessage("RO", "ITEM", "&6► &fAi prmit &6<AMOUNT>&f bucata(ti) de &6<ITEM>&f.");
        this.addMessage("RO", "ITEMDB", "&fObiect: &e<TYPE> &fId: &6<ITEMID>:<DATA>");
        this.addMessage("RO", "IGNORE", "&6► &fDe acum il ignori pe &e<PLAYER>&f.");
        this.addMessage("RO", "NO IGNORE", "&6► &fNu il mai ignori pe &e<PLAYER>&f.");
        this.addMessage("RO", "JAIL", "&6► &fAi fost inchis de &e<ADMIN>&f pentru &6<TIME>&f, motiv: &c<REASON>&f!");
        this.addMessage("RO", "ADMIN JAIL", "&6► &fJucatorul &e<PLAYER> &fa fost inchis.");
        this.addMessage("RO", "JAIL CMD", "&6► &fNu poti face asta cat timp esti inchis! Jail ramas: &6<TIME>&f.");
        this.addMessage("RO", "NOJAIL", "&6► &fJucatorul nu are jail.");
        this.addMessage("RO", "CHECKJAIL", "Jucatorul &e<PLAYER> &f are jail!/nTimp ramas: &6<TIME>&f.");
        this.addMessage("RO", "UNJAIL", "&6► &fJucatorul &e<PLAYER>&f a fost eliberat.");
        this.addMessage("RO", "DAYS", "zile");
        this.addMessage("RO", "DAY", "zi");
        this.addMessage("RO", "HOURS", "ore");
        this.addMessage("RO", "HOUR", "ora");
        this.addMessage("RO", "MINUTES", "minute");
        this.addMessage("RO", "MINUTE", "minut");
        this.addMessage("RO", "SECONDS", "secunde");
        this.addMessage("RO", "SECOND", "secunda");
        this.addMessage("RO", "YOURSELF", "&6► &fNu poti face asta.");
        this.addMessage("RO", "KICKALL", "&6► &fAi dat afara toti jucatori.");
        this.addMessage("RO", "NO KIT", "&6► &fKitul nu exista.");
        this.addMessage("RO", "CREATE KIT", "&6► &fAi creat kit-ul &e<KIT>&f.");
        this.addMessage("RO", "KIT", "&6► &fAi primit kit-ul &e<KIT>&f.");
        this.addMessage("RO", "NEXT KIT", "&6► &fPoti lua kit-ul &e<KIT> &fin &6<TIME>&f.");
        this.addMessage("RO", "BYE KIT", "&6► &fNu mai poti lua acest kit&f.");
        this.addMessage("RO", "LIST", "&f&m--&8&m+&f&m-----------------------------&8&m+&f&m--\n&6 ► &fMomentan sunt &e<PLAYERS> &fjucatori conectati pe server.\n&6 ► &fStaff online: &7<STAFF>\n&6 ► &fDonatori online: &7<DONOR>\n&f&m--&8&m+&f&m-----------------------------&8&m+&f&m--");
        this.addMessage("RO", "MORE", "&6► &fAi primit un stack de &6<ITEM>&f.");
        this.addMessage("RO", "IGNORE MSG", "&6► &fAcest jucator te ignora.");
        this.addMessage("RO", "MSG SEND", "&6► &fMesajul a fost trimis lui &e<TARGET>&f.");
        this.addMessage("RO", "MSG TOGGLE", "&6► &fMesageria a fost &e<TOGGLE>&f pentru &e<PLAYER>&f.");
        this.addMessage("RO", "MSG OFF", "&6► &fJucatorul &e<TARGET> &fare mesageria dezactivata.");
        this.addMessage("RO", "NEAR", "&6► &fJucatori in apropiere: &e<TARGET>.");
        this.addMessage("RO", "NICK", "&6► &fNumele a fost setat in &e<NICK>&f pentru &e<PLAYER>&f.");
        this.addMessage("RO", "POSITIVE", "&6► &fTrebuie sa fie un numar pozitiv.");
        this.addMessage("RO", "NO MONEY", "&6► &fNu ai destui bani.");
        this.addMessage("RO", "PAY", "&6► &f<MONEY>$ au fost trimisi catre &6<PLAYER>&f.");
        this.addMessage("RO", "PAY RECIVE", "&6► &f<MONEY>$ au fost primiti de la &6<PLAYER>&f.");
        this.addMessage("RO", "PT CLEAR", "&6► &fComenzile au fost sterse de pe &e<MATERIAL>&f.");
        this.addMessage("RO", "PT SET", "&6► &fComanda &6<COMMAND>&fa fost pusa pe &e<MATERIAL>&f.");
        this.addMessage("RO", "PTIME", "Timpul jucatorului a fost setat &e<TIME>&f pentru &6<PLAYER>&f.");
        this.addMessage("RO", "REALNAME", "&e<NICK> &feste &6<NAME>&f.");
        this.addMessage("RO", "REPAIR", "&6► &fAi reparat cu succes: &e<REPAIR>");
        this.addMessage("RO", "SPY", "&6► &fModul SPY a fost &e<SPY>&f pentru &e<PLAYER>&f.");
        this.addMessage("RO", "SKULL", "&6► &fAi primit capul lui &e<PLAYER>&f.");
        this.addMessage("RO", "SET HOME", "&6► &fCasa a fost setata.");
        this.addMessage("RO", "SET WARP", "&6► &fWarpul a fost setat.");
        this.addMessage("RO", "SET JAIL", "&6► &fJailul a fost setat.");
        this.addMessage("RO", "SET SPAWN", "&6► &fSpawnul a fost setat.");
        this.addMessage("RO", "SPAWNER", "&6► &fSpawnerul a fost setat.");
        this.addMessage("RO", "NO ENTITY", "&6► &fEntitatea nu exista!");
        this.addMessage("RO", "SPAWNMOB", "&6► &fMobul a fost spawnat.");
        this.addMessage("RO", "SPEED", "&6► &fAi setat viteaza de &e<SPEED>&f in &6<I>&f.");
        this.addMessage("RO", "SUDO", "&6► &fL-ai fortat pe &6&f<PLAYER> &fsa scrie: &e<MSG>&f.");
        this.addMessage("RO", "MAX HOMES", "&6► &cAi atins numarul maxim de case.");
        this.addMessage("RO", "TIME", "Timpul a fost setat &e<TIME>&f in lumea &6<WORLD>&f.");
        this.addMessage("RO", "TELEPORT", "Te vei teleporta..");
        this.addMessage("RO", "TP OFF", "&6► &fJucatorul &e<TARGET> &fare teleportarea dezactivata.");
        this.addMessage("RO", "TPA", "&6<PLAYER> &fti-a cerut sa se teleporteze la tine.\n&fPentru a accepta cererea scrie &e/tpaccept&f.\n&fPentru a refuza cererea scrie &e/tpdeny&f.\nAceasta cerere va expira in &e30 secunde&f.");
        this.addMessage("RO", "TPAHERE", "&6<PLAYER> &fti-a cerut sa te teleportezi la el.\n&fPentru a accepta cererea scrie &e/tpaccept&f.\n&fPentru a refuza cererea scrie &e/tpdeny&f.\nAceasta cerere va expira in &e30 secunde&f.");
        this.addMessage("RO", "REQUEST SENT", "&6► &fCererea de teleportare a fost trimisa!");
        this.addMessage("RO", "REQUEST ACCEPT", "&6► &6<PLAYER> &fa acceptat cererea de teleportare!");
        this.addMessage("RO", "REQUEST DENY", "&6► &6<PLAYER> &fa respins cererea de teleportare!");
        this.addMessage("RO", "NO TP", "&6► &fNu ai nici o cerere in asteptare.");
        this.addMessage("RO", "TP TOGGLE", "&6► &fTeleportarea a fost &e<TOGGLE>&f pentru &e<PLAYER>&f.");
        this.addMessage("RO", "TPALL", "&6► &fAi teleportat &7[&6<PL>&7] &fjucatori la tine.");
        this.addMessage("RO", "VANISH", "&6► &fModul de vanish a fost &e<VANISH>&f pentru &e<PLAYER>&f.");
        this.addMessage("RO", "KILLALL", "&6► &fAi omorat &6<X> &f<TYPE>.");
        this.addMessage("RO", "HOME TP", "&6► &fTe vei teleporta acasa!");
        this.addMessage("RO", "SPAWN TP", "&6► &fTe vei teleporta la spawn!");
        this.addMessage("RO", "DONT MOVE", "Nu te misca!");
        this.addMessage("RO", "MOVE", "&cTeleportarea a fost anulata!");
        this.addMessage("RO", "TELEPORT NOW", "Te-ai teleportat");
        this.addMessage("RO", "NO SPAM", "&6► &cTe rugam sa nu faci spam in chat!");
        this.addMessage("RO", "NO SPAM2", "&6► &fTrebuie sa astepti &c<TIME> secunde &fpentru a scrie din nou!");
        this.addMessage("RO", "NO SWEAR", "&6► &cNu ai voie sa folosesti cuvinte interzise!");
        this.addMessage("RO", "SET LANGUAGE", "&6► &fA fost setata limba &e<LANG> &fpentru &6<PLAYER>&f.");
        this.addMessage("RO", "VOTE", "&6&l<PLAYER> &fne-a votat si a fost recompensat! &7(&e&l/vote&7)");
        this.addMessage("RO", "VOTE MSG", "Pana in prezent, ne-ai votat de &e&l<VOTE> &fori.\nDaca vrei sa votezi, aceseaza urmatorul link:\n&6https://minecraft-mp.com/server/197848/vote/");
        this.addMessage("RO", "RESTART", "&6► &fServerul se va restarta in &e<SECOND> &f<TYPE>.");
    }

    public void addMessage(final String l, final String path, final String mesaj) {
        final File messageFile = new File(this.getInstance().getDataFolder(), "customization.yml");
        final FileConfiguration c;
        final FileConfiguration message = c = (FileConfiguration) YamlConfiguration.loadConfiguration(messageFile);
        c.addDefault(String.valueOf(l) + ".Message." + path, (Object) mesaj);
        c.options().copyDefaults(true);
        this.saveConfig(messageFile, message);
    }

    public void addListMessage(final String l, final String path, final String mesaj) {
        final File messageFile = new File(this.getInstance().getDataFolder(), "customization.yml");
        final FileConfiguration c;
        final FileConfiguration message = c = (FileConfiguration) YamlConfiguration.loadConfiguration(messageFile);
        final String[] st = mesaj.split(",");
        final List<String> list = new ArrayList<String>();
        try {
            for (int i = 0; i < 15; ++i) {
                if (st[i] != " ") {
                    list.add(st[i]);
                }
            }
        } catch (Exception ex) {
        }
        c.addDefault(String.valueOf(l) + ".Message." + path, (Object) list);
        c.options().copyDefaults(true);
        this.saveConfig(messageFile, message);
    }

    public String getMessage(final String l, final String path) {
        final File messageFile = new File(this.getInstance().getDataFolder(), "customization.yml");
        final FileConfiguration message = (FileConfiguration) YamlConfiguration.loadConfiguration(messageFile);
        if (!message.contains(String.valueOf(l) + ".Message." + path)) {
            return "";
        }
        return this.replace(message.getString(String.valueOf(l) + ".Message." + path));
    }

    public List<String> getListMessage(final String l, final String path) {
        final File messageFile = new File(this.getInstance().getDataFolder(), "customization.yml");
        final FileConfiguration message = (FileConfiguration) YamlConfiguration.loadConfiguration(messageFile);
        return (List<String>) message.getStringList(String.valueOf(l) + ".Message." + path);
    }
}
