package dev.jay.csadverts.commands.sub;

import dev.jay.csadverts.CSAdverts;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class reload {

    private final CSAdverts plugin;
    public reload(CSAdverts plugin){
        this.plugin = plugin;
    }

    public void onReload(Player player){

        if (player.hasPermission("csadverts.reload")){


            plugin.reloadConfig();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6CSAdverts&8] &aConfig reloaded."));


        }

    }
}
