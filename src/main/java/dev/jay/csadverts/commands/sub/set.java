package dev.jay.csadverts.commands.sub;

import dev.jay.csadverts.CSAdverts;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class set {

    private final CSAdverts plugin;
    public set(CSAdverts plugin){
        this.plugin = plugin;
    }

    public void onMessageSet(Player player, String message) throws SQLException {
        PreparedStatement setUserMessage = plugin.con.GetDb().prepareStatement("UPDATE adverts SET message=? WHERE playerUUID=?");
        setUserMessage.setString(2, player.getUniqueId().toString());
        setUserMessage.setString(1, message);
        setUserMessage.executeUpdate();
        player.sendMessage(Hex(Color(plugin.getConfig().getString("Plugin.Prefix") + plugin.getConfig().getString("Messages.advert-set"))));
    }

    public String Color(String s){
        s = ChatColor.translateAlternateColorCodes('&',s);
        return s;
    }

    private static final Pattern HEX_PATTERN = Pattern.compile("&(#\\w{6})");

    public static String Hex(String message) {
        Matcher matcher = HEX_PATTERN.matcher(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message));
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group(1)).toString());
        }

        return matcher.appendTail(buffer).toString();
    }
}
