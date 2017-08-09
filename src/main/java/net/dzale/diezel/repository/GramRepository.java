package net.dzale.diezel.repository;

import net.dzale.diezel.model.entity.GramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Database access layer for interacting with Grams.
 *
 * @drzale
 */
public interface GramRepository extends JpaRepository<GramEntity, Long> {

    List<GramEntity> findFirst5ByOrderByCreatedDesc();

    List<GramEntity> findAllByOrderByCreatedDesc();

    //List<GramEntity> findAll(Pageable pageable);

}
