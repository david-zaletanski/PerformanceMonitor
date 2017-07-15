package net.dzale.diezel.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import net.dzale.diezel.model.Torrent;

public interface TorrentRepository extends CrudRepository<Torrent, Long> {

	List<Torrent> findByName(String name);
	List<Torrent> findByType(String type);
	
}
