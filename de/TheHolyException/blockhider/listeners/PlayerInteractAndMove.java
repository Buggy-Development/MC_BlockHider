package de.TheHolyException.blockhider.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.TheHolyException.blockhider.utils.Utils;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class PlayerInteractAndMove implements Listener {
	
	@EventHandler
	public void onPlayerClickInventory(InventoryClickEvent event) {
		
		if (event.getCurrentItem() != null) {
			Player player = (Player) event.getWhoClicked();
			ItemStack item = event.getCurrentItem();
			if (event.getInventory().getTitle().equalsIgnoreCase("§bWähle einen Block")) {
				Material type = item.getType();
				if (type == Material.BARRIER) {
					FallingBlock fblock = Utils.hidingplayers.get(player);
					try {fblock.getVehicle().remove();}catch(RuntimeException ex) {}
					fblock.remove();
					Bukkit.getOnlinePlayers().forEach(onlines -> {
						onlines.showPlayer(player);
					});
					return;
				}
				try {
					spawnFallingBlock(player, type);
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
				Bukkit.getOnlinePlayers().forEach(all -> {
					all.hidePlayer(player);
				});
				player.closeInventory();
				player.playSound(player.getEyeLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void spawnFallingBlock(Player player, Material type) {
		FallingBlock block = player.getWorld().spawnFallingBlock(player.getLocation(), type, (byte) 0);
		block.setDropItem(false);
		freezeEntity(mount(block));
		if (Utils.hidingplayers.containsKey(player)) {
			FallingBlock fblock2 = Utils.hidingplayers.get(player);
			try {fblock2.getVehicle().remove();}catch(RuntimeException ex) {}
			fblock2.remove();
			Utils.hidingplayers.remove(player);
		}
		Utils.hidingplayers.put(player, block);
	}
	
	@EventHandler
	public void onBlock(EntityChangeBlockEvent event) {
		for (FallingBlock blocks : Utils.hidingplayers.values()) {
			if (blocks.getUniqueId().equals(event.getEntity().getUniqueId())) {
				event.setCancelled(true);
				System.out.println("can");
			}
		}
	}
	
	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent event) {
		if (event.getEntity().getType().equals(EntityType.BAT)) {
			if (Utils.ridingBlock.containsValue(event.getEntity())) {
				event.setCancelled(true);
			}
		}
	}
 
	public static void freezeEntity(Entity entity) {
		net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity)entity).getHandle();
		NBTTagCompound compund = new NBTTagCompound();
		nmsEntity.c(compund);
		compund.setByte("NoAI", (byte)1);
		compund.setByte("Silent", (byte)1);
		nmsEntity.f(compund);
	}
	
	public static Bat mount(FallingBlock block) {
 
		Bat bat = block.getWorld().spawn(block.getLocation(), Bat.class);
		bat.setPassenger(block);
		((CraftEntity)bat).getHandle().setInvisible(true);
		Utils.ridingBlock.put(block, bat);
		bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, true));
		return bat;
	}
}
