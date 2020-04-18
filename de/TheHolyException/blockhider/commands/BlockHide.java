package de.TheHolyException.blockhider.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.TheHolyException.blockhider.utils.ItemManager;

public class BlockHide implements CommandExecutor {
	

	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		
		if (sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if (args.length == 0) {
				
				Inventory inv = Bukkit.createInventory(player, 27, "§bWähle einen Block");
				inv.addItem(new ItemManager(Material.STONE).addLoreArray(new String[] {" ", "§7Tarne dich als Stein", "", "§b► Klicke zum Auswählen"}).setDisplayName("§7§lStein Block").build());
				inv.addItem(new ItemManager(Material.LOG).addLoreArray(new String[] {" ", "§7Tarne dich als Eichenholz", "", "§b► Klicke zum Auswählen"}).setDisplayName("§6§lEichenholz").build());
				inv.addItem(new ItemManager(Material.CHEST).addLoreArray(new String[] {" ", "§7Tarne dich als Kiste", "", "§b► Klicke zum Auswählen"}).setDisplayName("§6§lKiste").build());
				inv.addItem(new ItemManager(Material.ENCHANTMENT_TABLE).addLoreArray(new String[] {" ", "§7Tarne dich als Zaubertisch", "", "§b► Klicke zum Auswählen"}).setDisplayName("§5§lZaubertisch").build());
				inv.setItem(26, new ItemManager(Material.BARRIER).addLoreArray(new String[] {" ", "§7Entferne deine Tarnung", "", "§b► Klicke zum Auswählen"}).setDisplayName("§c§lEntferne die Tarnung").build());
				
				player.openInventory(inv);
				
			}
			
			
		}
		
		
		return true;
	}
	
}
