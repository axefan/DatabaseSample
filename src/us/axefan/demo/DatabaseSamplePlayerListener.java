package us.axefan.demo;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Player;

public class DatabaseSamplePlayerListener extends PlayerListener {
	
	public static DatabaseSample m_plugin;
	
	public DatabaseSamplePlayerListener(DatabaseSample instance){
		m_plugin = instance;
	}
	
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		DatabaseSamplePlayerRecord record = m_plugin.getPlayerRecord(player);
		m_plugin.printPlayerStatus(player, record);
		m_plugin.printBlocksDestroyed(player);
	}
	
	public void onPlayerQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		DatabaseSamplePlayerRecord record = m_plugin.getPlayerRecord(player);
		m_plugin.printPlayerStatus(player, record);
	}
}
