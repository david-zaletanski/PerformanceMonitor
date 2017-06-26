package net.dzale.treeseeder.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Torrent {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String type;
	
	protected Torrent() { }
	
	public Torrent(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return String.format("Torrent[id=%d, name='%s', type='%s']", 
				id, name, type);
	}

}
