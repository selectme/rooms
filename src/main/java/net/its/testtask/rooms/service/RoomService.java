package net.its.testtask.rooms.service;

import net.its.testtask.rooms.model.Room;
import net.its.testtask.rooms.model.User;

import java.util.List;

public interface RoomService {

    void createRoom(Room room);

    List<Room> getAllRooms();

    void addUserToRoom(Long roomId, User user);

    Room getRoomById(Long id);

    void switchLight(Room room);

}
