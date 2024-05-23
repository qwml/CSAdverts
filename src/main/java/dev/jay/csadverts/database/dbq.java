package dev.jay.csadverts.database;

import dev.jay.csadverts.CSAdverts;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class dbq {

    private final CSAdverts plugin;
    public dbq(CSAdverts plugin){
        this.plugin = plugin;
    }

    public void createTable() throws SQLException {
        PreparedStatement table = plugin.con.GetDb().prepareStatement("CREATE TABLE IF NOT EXISTS adverts(playerUUID varchar, message varchar, PRIMARY KEY(playerUUID))");
        table.executeUpdate();
        System.out.println("Table created.");
    }

    public void createPlayer(Player player) throws SQLException {
        UUID uuid = player.getUniqueId();
        if (!doesPlayerExist(player)){
            PreparedStatement createPlayer = plugin.con.GetDb().prepareStatement("INSERT INTO adverts(playerUUID, message) VALUES (?,?)");
            createPlayer.setString(1, uuid.toString());
            createPlayer.setString(2, "unset");
            createPlayer.executeUpdate();
        }
    }


    public boolean doesPlayerExist(Player player) throws SQLException{
        UUID uuid = player.getUniqueId();
        PreparedStatement ps1 = plugin.con.GetDb().prepareStatement("SELECT * FROM adverts WHERE playerUUID=?");
        ps1.setString(1, String.valueOf(uuid));
        ResultSet rs1 = ps1.executeQuery();
        if (rs1.next()){
            return true;
        }else{
            return false;
        }
    }
}
