package net.its.testtask.rooms.service;

import net.its.testtask.rooms.model.Room;
import net.its.testtask.rooms.model.User;

import java.util.List;

/**
 * Provides methods for work with {@link Room}.
 */
public interface RoomService {

    /**
     * Allows to create {@link Room}.
     *
     * @param room {@link Room} which should be created.
     */
    void createRoom(Room room);

    /**
     * Allows to get all rooms {@link Room} stored in a database in advance.
     *
     * @return list of {@link Room}.
     */
    List<Room> getAllRooms();

    /**
     * Allows to add {@link User} into {@link Room}.
     *
     * @param room {@link Room} in which should be added {@link User}.
     * @param user {@link User} which will be added into {@link Room}.
     */
    void addUserToRoom(Room room, User user);

    /**
     * Allows to get {@link Room} using its id.
     *
     * @param id for getting {@link Room}.
     * @return {@link Room}.
     */
    Room getRoomById(Long id);

    /**
     * Allows to switch {@link net.its.testtask.rooms.model.Lamp} state of a specific {@link Room}.
     *
     * @param room {@link Room} in which will be changed state of {@link net.its.testtask.rooms.model.Lamp}
     */
    void switchLight(Room room);

}
