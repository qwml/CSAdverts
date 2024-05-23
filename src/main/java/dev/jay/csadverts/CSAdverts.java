package dev.jay.csadverts;

import dev.jay.csadverts.commands.advertise;
import dev.jay.csadverts.commands.managers.coolDown;
import dev.jay.csadverts.commands.managers.timeFormat;
import dev.jay.csadverts.commands.sub.set;
import dev.jay.csadverts.database.data;
import dev.jay.csadverts.database.dbq;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public final class CSAdverts extends JavaPlugin implements Listener {

    public data con;
    public dbq query;
    public String Datatype;
    public HashMap<UUID, Long> cooldown;
    public timeFormat tf;
    public coolDown cd;
    public set s;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info(ChatColor.GOLD + "[CSAdverts] Started.");
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Datatype = getConfig().getString("Plugin.Database.Type");
        con = new data(this);
        query = new dbq(this);
        cooldown = new HashMap<>();
        tf = new timeFormat();
        cd = new coolDown(this);
        s = new set(this);

        try {
            con.InitDb();
            if (con.GetDb() != null){
                query.createTable();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        getServer().getPluginManager().registerEvents(this, this);
        getCommand("advertise").setExecutor(new advertise(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        con.CloseDb();

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws SQLException {

        query.createPlayer(e.getPlayer());


    }
}
