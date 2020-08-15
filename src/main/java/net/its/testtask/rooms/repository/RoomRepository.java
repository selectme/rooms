package net.its.testtask.rooms.repository;

import net.its.testtask.rooms.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for work with {@link Room}.
 */
@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

}
