package me.wally.playerhead;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class PlayerHead extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("skull")){
            if(!(sender instanceof Player)){
                sender.sendMessage("You cannot do this!");
                return true;
            }
            Player player = (Player) sender;
            if (args.length==0){
                // /skull (give the donny his head)
                player.sendMessage(ChatColor.GREEN + "You have been given the skull of " + player.getName());
                player.getInventory().addItem(getPlayerHead(player.getName()));
                return true;
            }

            // /skull (playername) (give the donny his head)
            player.sendMessage(ChatColor.GREEN + "You have been given the skull of " + args[0]);
            player.getInventory().addItem(getPlayerHead(args[0]));
            return true;
        }
        return false;
    }

    public ItemStack getPlayerHead(String player){

        boolean isNewVersion = Arrays.stream(Material.values()).map(Material::name).collect(Collectors.toList()).contains("PLAYER_HEAD");

        Material type = Material.matchMaterial(isNewVersion ? "PLAYER_HEAD" : "SKULL_ITEM");
        ItemStack item = new ItemStack(type, 1);

        if(!isNewVersion)
            item.setDurability((short) 3);

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(player);

        item.setItemMeta(meta);

        return item;
    }
}
