package de.TheHolyException.blockhider.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.TheHolyException.blockhider.utils.Utils;

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Utils.hidingplayers.keySet().forEach(hiders -> {
			event.getPlayer().hidePlayer(hiders);
		});
	}

}
