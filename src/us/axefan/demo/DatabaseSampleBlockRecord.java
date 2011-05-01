package us.axefan.demo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name="blocks")
public class DatabaseSampleBlockRecord {

    @Id
    private int id;
    
    @NotNull
    private String playerName;
    
    @NotNull
    private int blockType;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setBlockType(int blockType) {
		this.blockType = blockType;
	}

	public int getBlockType() {
		return blockType;
	}
}
