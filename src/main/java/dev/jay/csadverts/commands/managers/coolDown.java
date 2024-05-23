package dev.jay.csadverts.commands.managers;

import dev.jay.csadverts.CSAdverts;
import org.bukkit.entity.Player;


public class coolDown {

    private final CSAdverts plugin;
    public coolDown(CSAdverts plugin){
        this.plugin = plugin;
    }

    public void adduserCooldown(Player player){

        if (player.hasPermission(plugin.getConfig().getString("onAnnounce.Cooldown.Donator.Permission")) && !player.isOp()) {

            plugin.cooldown.put(player.getUniqueId(), Long.valueOf(System.currentTimeMillis() + (this.plugin.getConfig().getInt("onAnnounce.Cooldown.Donator.Timer") * 1000)));

        }else if (!player.isOp()){

            plugin.cooldown.put(player.getUniqueId(), Long.valueOf(System.currentTimeMillis() + (this.plugin.getConfig().getInt("onAnnounce.Cooldown.Default.Timer") * 1000)));
        }
    }


}
