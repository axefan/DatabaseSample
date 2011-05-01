package us.axefan.demo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name="players")
public class DatabaseSamplePlayerRecord {
	
    @Id
    private int id;
    
    @NotNull
    private String playerName;
    
    @NotNull
    private Boolean enabled;
    
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
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}
	
}
