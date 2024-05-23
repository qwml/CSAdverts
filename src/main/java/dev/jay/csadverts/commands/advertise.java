package dev.jay.csadverts.commands;

import dev.jay.csadverts.CSAdverts;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class advertise implements CommandExecutor {

    private final CSAdverts plugin;
    public advertise(CSAdverts plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase("advertise")){
            Player player = (Player) commandSender;
            String message = null;
            if (args.length == 0){
                try {
                    PreparedStatement checkMessage = plugin.con.GetDb().prepareStatement("SELECT message FROM adverts WHERE playerUUID=?");
                    checkMessage.setString(1, player.getUniqueId().toString());
                    ResultSet checkMess = checkMessage.executeQuery();
                    if (checkMess.next()){
                        message = checkMess.getString("message");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                if (!(Objects.equals(message, "unset"))){

                    if (plugin.cooldown.containsKey(player.getUniqueId()) && (plugin.cooldown.get(player.getUniqueId())).longValue() > System.currentTimeMillis()) {

                        long timeleft = ((plugin.cooldown.get(player.getUniqueId())).longValue() - System.currentTimeMillis()) / 1000L;
                        String ocmessage= plugin.getConfig().getString("Messages.on-cooldown");
                        ocmessage = ocmessage.replace("%timeleft%", plugin.tf.formattedTime((int) timeleft));
                        player.sendMessage(Hex(Color(plugin.getConfig().getString("Plugin.Prefix") + ocmessage)));

                    }else{
                        List<String> announcement = plugin.getConfig().getStringList("onAnnounce.Message");

                        for (String i : announcement){
                            i = i.replace("%player%", player.getName()).replace("%message%", message);
                            Bukkit.broadcastMessage(Hex(Color(i)));
                        }
                        player.sendMessage(Hex(Color(plugin.getConfig().getString("Plugin.Prefix") + plugin.getConfig().getString("Messages.advert-sent"))));

                        plugin.cd.adduserCooldown(player);

                    }

                }else{

                    player.sendMessage(Hex(Color(plugin.getConfig().getString("Plugin.Prefix") + plugin.getConfig().getString("Messages.unset-message"))));

                }

            }else if (args[0].equalsIgnoreCase("set")){

                if (!(args.length < 2)){
                    String message1 = "";
                    for (int i = 1; i < args.length; i++){

                        message1 = message1 + args[i] + " ";

                    }


                    try {
                        plugin.s.onMessageSet(player, message1);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }else{

                    player.sendMessage(Hex(Color(plugin.getConfig().getString("Plugin.Prefix") + plugin.getConfig().getString("Messages.empty-message"))));

                }



            }

        }


        return true;
    }

    public String Color(String s){
        ChatColor.translateAlternateColorCodes('&',s);
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
