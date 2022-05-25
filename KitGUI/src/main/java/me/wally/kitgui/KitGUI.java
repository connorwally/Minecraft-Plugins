package me.wally.kitgui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class KitGUI extends JavaPlugin implements Listener {

    public static Inventory kits;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this,this);

        createInventory();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void createInventory(){
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Kits");
        ItemStack item = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Noob Kit");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.RED + "Click here to get the kit");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        inv.setItem(3, item);

        item.setType(Material.IRON_BLOCK);
        meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Iron Kit");
        item.setItemMeta(meta);
        inv.setItem(4, item);

        item.setType(Material.DIAMOND_BLOCK);
        meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Diamond Kit");
        item.setItemMeta(meta);
        inv.setItem(5, item);

        kits = inv;
    }



    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (label.equalsIgnoreCase("kits")){
            if (!(sender instanceof Player)){
                sender.sendMessage("The console cannot run this command");
                return true;
            }
            Player player = (Player) sender;
            player.openInventory(kits);
            return true;
        }
        return false;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if (!event.getView().getTitle().contains("Kits")){
            return;
        }

        if (event.getCurrentItem()== null){
            return;
        }

        if (event.getCurrentItem().getItemMeta()== null){
            return;
        }

        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        if (event.getClickedInventory().getType() == InventoryType.PLAYER){
            return;
        }

        if (event.getSlot() == 3){
            if (!player.hasPermission("kits.noob")){
                player.sendMessage("You dont have permission to use this kit");
                return;
            }
            this.dropChest(player, this.getNoobKit());
            player.closeInventory();
            player.updateInventory();
            return;


        }

        if (event.getSlot() == 4){
            if (!player.hasPermission("kits.iron")){
                player.sendMessage("You dont have permission to use this kit");
                return;
            }
            this.dropChest(player, this.getIronKit());
            player.closeInventory();
            player.updateInventory();
            return;


        }

        if (event.getSlot() == 5){
            if (!player.hasPermission("kits.diamond")){
                player.sendMessage("You dont have permission to use this kit");
                return;
            }
            this.dropChest(player, this.getDiamondKit());
            player.closeInventory();
            player.updateInventory();
            return;


        }

    }

    private void dropChest(Player player, ItemStack[] items){
        Location loc = null;
        if (player.getFacing()== BlockFace.NORTH){
            loc = player.getLocation().add(0,0,-1);
        }
        if (player.getFacing()== BlockFace.SOUTH){
            loc = player.getLocation().add(0,0,1);
        }
        if (player.getFacing()== BlockFace.EAST){
            loc = player.getLocation().add(1,0,0);
        }
        if (player.getFacing()== BlockFace.WEST){
            loc = player.getLocation().add(-1,0,0);
        }

        if (loc.getBlock().getType() != Material.AIR) {
            player.sendMessage(ChatColor.RED + "Cannot spawn a kit chest here");
            return;
        }

        Block block = loc.getBlock();
        block.setType(Material.CHEST);
        Chest chest = (Chest) block.getState();
        chest.getInventory().addItem(items);
    }

    private ItemStack[] getNoobKit(){
        ItemStack[] items = {new ItemStack(Material.COAL, 16), new ItemStack(Material.LEATHER_HELMET, 1)};
        return items;
    }

    private ItemStack[] getIronKit(){
        ItemStack[] items = {new ItemStack(Material.COAL, 16), new ItemStack(Material.LEATHER_HELMET, 1)};
        return items;
    }

    private ItemStack[] getDiamondKit(){
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DIG_SPEED, 5, true);
        item.setItemMeta(meta);

        ItemStack item2 = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta2 = item2.getItemMeta();
        meta2.addEnchant(Enchantment.LOOT_BONUS_MOBS, 5, true);
        item2.setItemMeta(meta2);

        ItemStack[] items = {item, item2};
        return items;
    }
}
