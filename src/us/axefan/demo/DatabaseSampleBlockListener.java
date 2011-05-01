package us.axefan.demo;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;

public class DatabaseSampleBlockListener extends BlockListener {
	
	public static DatabaseSample m_plugin;
	
	public DatabaseSampleBlockListener(DatabaseSample instance) {
		m_plugin = instance;
	}
	
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		m_plugin.addBlock(player, event.getBlock());
		m_plugin.printBlocksDestroyed(player);
	}
	
}