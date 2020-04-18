package de.TheHolyException.blockhider;

import org.bukkit.entity.FallingBlock;
import org.bukkit.plugin.java.JavaPlugin;

import de.TheHolyException.blockhider.commands.BlockHide;
import de.TheHolyException.blockhider.listeners.PlayerInteractAndMove;
import de.TheHolyException.blockhider.listeners.PlayerJoin;
import de.TheHolyException.blockhider.utils.Utils;

public class BlockHider extends JavaPlugin {
	
	private static BlockHider instance;
	
	public void onEnable() {
		BlockHider.instance = this;
		
		Utils.runtask();
		
		registerCommands();
		registerListeners();
	}
	
	public void onDisable() {
		Utils.hidingplayers.keySet().forEach(player -> {
			FallingBlock block = Utils.hidingplayers.get(player);
			Utils.ridingBlock.get(block).remove();
			block.remove();
		});
	}
	
	private void registerCommands() {
		getCommand("blockhide").setExecutor(new BlockHide());
	}
	
	private void registerListeners() {
		Utils.registerListener(new PlayerInteractAndMove());
		Utils.registerListener(new PlayerJoin());
	}
	
	public static BlockHider getInstance() {
		return instance;
	}

}
