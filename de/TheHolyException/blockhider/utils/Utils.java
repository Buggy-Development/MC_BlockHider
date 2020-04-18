package de.TheHolyException.blockhider.utils;

import java.lang.reflect.Method;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.function.Supplier;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Bat;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import de.TheHolyException.blockhider.BlockHider;
import de.TheHolyException.blockhider.listeners.PlayerInteractAndMove;

public class Utils {
	
	public static HashMap<Player, FallingBlock> hidingplayers = new HashMap<>();
	public static HashMap<FallingBlock, Bat> ridingBlock = new HashMap<>();
	
	public static void registerListener(Listener listener) {
		BlockHider.getInstance().getServer().getPluginManager().registerEvents(listener, BlockHider.getInstance());
	}
	
	public static void runtask() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				try {
					hidingplayers.keySet().forEach(player -> {
						FallingBlock block = hidingplayers.get(player);
						Bat bat = ridingBlock.get(block);
						Location loc = player.getLocation();
						if (block == null || block.isDead() || (block.getLocation() == null)) {
							PlayerInteractAndMove.spawnFallingBlock(player, block.getMaterial());
						}
						try {
							methods[1].invoke(methods[0].invoke(bat), loc.getX(), (loc.getY()-1D), loc.getZ(), loc.getYaw(), loc.getPitch());
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					});
				} catch (ConcurrentModificationException ex) {
					//IGNORE
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}.runTaskTimer(BlockHider.getInstance(), 10, 2);
	}
	
	private static final Method[] methods = ((Supplier<Method[]>) () -> {
	    try {
	        Method getHandle = Class.forName(Bukkit.getServer().getClass().getPackage().getName() + ".entity.CraftEntity").getDeclaredMethod("getHandle");
	        return new Method[] {
	                getHandle, getHandle.getReturnType().getDeclaredMethod("setPositionRotation", double.class, double.class, double.class, float.class, float.class)
	        };
	    } catch (Exception e) {
	        return null;
	    }
	}).get();

}
