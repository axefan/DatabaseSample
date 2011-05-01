package us.axefan.demo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrackCommandExecutor implements CommandExecutor{
	
	private DatabaseSample m_plugin;
	
	public TrackCommandExecutor(DatabaseSample instance){
		m_plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String cmd = label.toLowerCase();
		if(!cmd.equals("track")) return false;
		Player player = (Player) sender;
		DatabaseSamplePlayerRecord record = m_plugin.getPlayerRecord(player);
		record.setEnabled(!record.getEnabled());
		m_plugin.savePlayerRecord(record);
		m_plugin.printPlayerStatus(player, record);
		m_plugin.printBlocksDestroyed(player);
		return true;
	}
	
}
