package us.axefan.demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.ExpressionList;

public class DatabaseSample extends JavaPlugin{
	
    private EbeanServer m_db = null;
    private final DatabaseSamplePlayerListener m_playerListener = new DatabaseSamplePlayerListener(this);
    private final DatabaseSampleBlockListener m_blockListener = new DatabaseSampleBlockListener(this);
    
    public void onEnable(){
        PluginDescriptionFile desc = this.getDescription();
        System.out.println("[" + desc.getName() + "] v" + desc.getVersion() + " starting...");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_JOIN, m_playerListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, m_playerListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, m_blockListener, Event.Priority.Normal, this);
		this.getCommand("track").setExecutor(new TrackCommandExecutor (this));
		this.setupDatabase();
        System.out.println("[" + desc.getName() + "] v" + desc.getVersion() + " enabled");
	}
	
    public void onDisable(){
	}
    
    /*
     * Ensures that the database for this plugin is installed and available.
     */
    private void setupDatabase(){
		try {
			// Verify or create ebean.properties file.
			File ebeans = new File("ebean.properties");
			if (!ebeans.exists()) {
				try {
					ebeans.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// Check for existing database.
			this.getDatabase().find(DatabaseSamplePlayerRecord.class).findRowCount();
		} catch (PersistenceException ex) {
			// Create new database.
			System.out.println(this.getDescription().getName() + " installing database");
			this.installDDL();
		}
		m_db = this.getDatabase();
	}
	
    /*
     * List the classes to store in the database.
     */
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(DatabaseSamplePlayerRecord.class);
		list.add(DatabaseSampleBlockRecord.class);
		return list;
	}
    
	/*
	 * Get or create a player record in the "players" table.
	 */
	public DatabaseSamplePlayerRecord getPlayerRecord(Player player){
		DatabaseSamplePlayerRecord record = m_db.find(DatabaseSamplePlayerRecord.class).where().ieq("playerName", player.getName()).findUnique();
		if (record == null){
			record = new DatabaseSamplePlayerRecord();
			record.setPlayerName(player.getName());
			record.setEnabled(false);
			this.savePlayerRecord(record);
		}
		return record;
    }
    
	/*
	 * Saves a player record to the "players" table.
	 * @param record - The record to save.
	 */
    public void savePlayerRecord(DatabaseSamplePlayerRecord record){
    	if (record.getId() == 0){
    		System.out.print("Creating player record " + record.getPlayerName() + "...");
        	m_db.save(record);
    	}else{
    		System.out.print("Updating player record " + record.getPlayerName() + " (" + record.getId() + ")...");
    		m_db.update(record);
    	}
		System.out.print("Saved");
    }
    
    /*
     * Adds a block record to the player's "blocks" table.
     * @param player - The player.
     * @param block - The block to add.
     */
    public void addBlock(Player player, Block block){
    	System.out.print("Creating block record " + block.getTypeId() + "...");
    	DatabaseSampleBlockRecord record = new DatabaseSampleBlockRecord();
    	record.setPlayerName(player.getName());
    	record.setBlockType(block.getTypeId());
    	m_db.save(record);
		System.out.print("Saved");
    }
    
    /*
     * Notifies player of current tracking status.
     * @param player - The player.
     * @param record - The player's record.
     */
	public void printPlayerStatus(Player player, DatabaseSamplePlayerRecord record){
		if (record.getEnabled()){
			player.sendMessage("Tracking is enabled");
		}else{
			player.sendMessage("Tracking is disabled");
		}
	}
	
	/*
	 * Notifies player when a block has been destroyed.
     * @param player - The player.
	 */
	public void printBlocksDestroyed(Player player) {
		ExpressionList<DatabaseSampleBlockRecord> blocks = m_db.find(DatabaseSampleBlockRecord.class).where().ieq("playerName", player.getName());
		player.sendMessage(Integer.toString(blocks.findRowCount()) + " blocks destroyed!");
	}
 		
}
