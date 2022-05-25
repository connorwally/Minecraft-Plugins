package me.wally.diamondcounter2;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class DiamondCounter2 extends JavaPlugin implements Listener {

    public DataManager data;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.data = new DataManager(this);

        this.getServer().getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if (event.getBlock().getType().equals(Material.DIAMOND_ORE)){
            Player player = event.getPlayer();
            int amount = 0;

            if (this.data.getConfig().contains("players." + player.getUniqueId().toString() + ".total")){
                amount = this.data.getConfig().getInt("players." + player.getUniqueId().toString() + ".total");
            }

            data.getConfig().set("players." + player.getUniqueId().toString() + ".total", (amount + 1));
            data.saveConfig();
        }
    }

}
