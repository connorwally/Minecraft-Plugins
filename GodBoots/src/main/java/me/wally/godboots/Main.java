package me.wally.godboots;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Plugin command logic
        if (label.equalsIgnoreCase("GodBoots")){
            if(!(sender instanceof Player)){
                sender.sendMessage("Sorry console you're not allowed to fly.");
                return true;
            }
            Player player = (Player) sender;
            if(player.getInventory().firstEmpty() == -1){
                Location loc = player.getLocation();
                World world = player.getWorld();

                world.dropItemNaturally(loc, getItem());
                player.sendMessage(ChatColor.GOLD + "The minecraft legends dropped a gift nearby.");
                return true;
            }
            player.getInventory().addItem(getItem());
            player.sendMessage(ChatColor.GOLD + "The Minecraft gods gave you a gift.");

        }
        return false;
    }

    public ItemStack getItem(){
        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta meta = boots.getItemMeta();

        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Boots of Leaping");
        List<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add(ChatColor.GOLD + "" + ChatColor.ITALIC + "Boots made for the minecraft gods.");
        meta.setLore(lore);

        meta.addEnchant(Enchantment.PROTECTION_FALL, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setUnbreakable(true);

        boots.setItemMeta(meta);

        return boots;

    }

    @EventHandler
    public void onJump(PlayerMoveEvent event){
        Player player = (Player) event.getPlayer();
        if (player.getInventory().getBoots() != null)
            if (player.getInventory().getBoots().getItemMeta().getDisplayName().contains("Boots of Leaping"))
                if (player.getInventory().getBoots().getItemMeta().hasLore())
                    if(event.getFrom().getY() < event.getTo().getY() && player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
                        player.setVelocity(player.getLocation().getDirection().multiply(2).setY(2));
                    }
    }

    @EventHandler
    public void onFall(EntityDamageEvent event){
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (player.getInventory().getBoots() != null)
                    if (player.getInventory().getBoots().getItemMeta().getDisplayName().contains("Boots of Leaping"))
                        if (player.getInventory().getBoots().getItemMeta().hasLore()) {
                            event.setCancelled(true);
                        }
            }
        }

    }


}

