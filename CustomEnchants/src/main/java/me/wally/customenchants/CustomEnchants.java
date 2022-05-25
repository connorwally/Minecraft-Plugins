package me.wally.customenchants;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CustomEnchants extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        CustomEnchant2.register();

        this.getServer().getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("telepathy")){
            if(!(sender instanceof Player)){
                return true;
            }
            Player player = (Player) sender;

            ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
            item.addUnsafeEnchantment(CustomEnchant2.TELEPATHY, 1);
            item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);

            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList<String>();
            lore.add(ChatColor.GRAY + "Telepathy I");
            if (meta.hasLore())
                for (String l : meta.getLore())
                    lore.add(l);
            meta.setLore(lore);
            item.setItemMeta(meta);

            player.getInventory().addItem(item);
            return true;
        }
        return true;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if (event.getPlayer().getInventory().getItemInMainHand() == null){
            return;
        }
        if (!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()){
            return;
        }
        if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchant2.TELEPATHY)){
            return;
        }
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE || event.getPlayer().getGameMode() == GameMode.SPECTATOR){
            return;
        }
        if (event.getPlayer().getInventory().firstEmpty() == -1){
            return;
        }
        if (event.getBlock().getState() instanceof Container){
            return;
        }
        event.setDropItems(false);
        Player player = event.getPlayer();
        Block block = event.getBlock();

        Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());
        if(drops.isEmpty()){
            return;
        }

        player.getInventory().addItem(drops.iterator().next());
    }
}
