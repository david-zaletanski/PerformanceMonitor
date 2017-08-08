package net.dzale.diezel.repository;

import net.dzale.diezel.model.database.GramEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Database access layer for interacting with Grams.
 *
 * @drzale
 */
public interface GramRepository extends CrudRepository<GramEntity, Long> {


}
