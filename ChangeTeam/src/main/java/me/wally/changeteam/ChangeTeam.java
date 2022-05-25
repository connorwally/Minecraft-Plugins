package me.wally.changeteam;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class ChangeTeam extends JavaPlugin implements Listener {

    public Inventory inv;

    @Override
    public void onEnable() {
        // Plugin startup logic
        createInv();
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("changeteam")){
            if (!(sender instanceof Player)) {
                sender.sendMessage("You cannot do this!");
                return true;
            }
            Player player = (Player) sender;

            player.openInventory(inv);
            return true;
        }
        return false;
    }

    @EventHandler()
    public void onClick(InventoryClickEvent event){
        if(!event.getInventory().equals(inv)) return;
        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getItemMeta() == null) return;
        if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();

        if(event.getSlot() == 0){
            ItemStack[] armor = player.getEquipment().getArmorContents();
            armor = changeColor(armor, Color.BLUE);
            player.getEquipment().setArmorContents(armor);
            player.sendMessage(ChatColor.GOLD + "You changed your team!");
        }

        if(event.getSlot() == 1){
            ItemStack[] armor = player.getEquipment().getArmorContents();
            armor = changeColor(armor, Color.RED);
            player.getEquipment().setArmorContents(armor);
            player.sendMessage(ChatColor.GOLD + "You changed your team!");
        }
        if(event.getSlot() == 2){
            ItemStack[] armor = player.getEquipment().getArmorContents();
            armor = changeColor(armor, Color.LIME);
            player.getEquipment().setArmorContents(armor);
            player.sendMessage(ChatColor.GOLD + "You changed your team!");
        }
        if(event.getSlot() == 3){
            ItemStack[] armor = player.getEquipment().getArmorContents();
            armor = changeColor(armor, Color.ORANGE);
            player.getEquipment().setArmorContents(armor);
            player.sendMessage(ChatColor.GOLD + "You changed your team!");
        }
        if(event.getSlot() == 4){
            ItemStack[] armor = player.getEquipment().getArmorContents();
            armor = changeColor(armor, Color.PURPLE);
            player.getEquipment().setArmorContents(armor);
            player.sendMessage(ChatColor.GOLD + "You changed your team!");
        }
        if(event.getSlot() == 5){
            ItemStack[] armor = player.getEquipment().getArmorContents();
            armor = changeColor(armor, Color.AQUA);
            player.getEquipment().setArmorContents(armor);
            player.sendMessage(ChatColor.GOLD + "You changed your team!");
        }
        if(event.getSlot() == 6){
            ItemStack[] armor = player.getEquipment().getArmorContents();
            armor = changeColor(armor, Color.BLACK);
            player.getEquipment().setArmorContents(armor);
            player.sendMessage(ChatColor.GOLD + "You changed your team!");
        }
        if(event.getSlot() == 8){
            player.closeInventory();
        }

        return;

    }

    public ItemStack[] changeColor(ItemStack[] a, Color color) {
        for (ItemStack item : a) {
            try {
                if (item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.LEATHER_HELMET || item.getType() == Material.LEATHER_LEGGINGS) {
                    LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                    meta.setColor(color);
                    item.setItemMeta(meta);
                }
            } catch (Exception e){
            }
        }
        return a;
    }

    public void createInv(){

        inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "" + ChatColor.BOLD + "Select Team");

        ItemStack item = new ItemStack(Material.BLUE_CONCRETE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_BLUE + "BLUE TEAM");
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "Click to join team!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(0, item);

        item.setType(Material.RED_CONCRETE);
        meta.setDisplayName(ChatColor.DARK_RED + "RED TEAM");
        item.setItemMeta(meta);
        inv.setItem(1, item);

        item.setType(Material.LIME_CONCRETE);
        meta.setDisplayName(ChatColor.GREEN+ "GREEN TEAM");
        item.setItemMeta(meta);
        inv.setItem(2, item);

        item.setType(Material.ORANGE_CONCRETE);
        meta.setDisplayName(ChatColor.GOLD+ "GOLD TEAM");
        item.setItemMeta(meta);
        inv.setItem(3, item);

        item.setType(Material.PURPLE_CONCRETE);
        meta.setDisplayName(ChatColor.DARK_PURPLE+ "DARK PURPLE TEAM");
        item.setItemMeta(meta);
        inv.setItem(4, item);

        item.setType(Material.CYAN_CONCRETE);
        meta.setDisplayName(ChatColor.BLUE+ "BLUE TEAM");
        item.setItemMeta(meta);
        inv.setItem(5, item);

        item.setType(Material.BLACK_CONCRETE);
        meta.setDisplayName(ChatColor.DARK_GRAY + "BLACK TEAM");
        item.setItemMeta(meta);
        inv.setItem(6, item);

        item.setType(Material.BARRIER);
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD +  "CLOSE MENU");
        lore.clear();
        meta.setLore(lore);
        inv.setItem(8, item);
    }
}
