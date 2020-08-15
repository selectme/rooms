package net.its.testtask.rooms.service.impl;

import net.its.testtask.rooms.model.Lamp;
import net.its.testtask.rooms.model.Room;
import net.its.testtask.rooms.repository.RoomRepository;
import net.its.testtask.rooms.service.AbstractServiceTest;
import net.its.testtask.rooms.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link RoomService}.
 */
class RoomServiceImplTest extends AbstractServiceTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    /**
     * Test method for {@link RoomService#getAllRooms()}.
     */
    @Test
    void getAllRooms() {
        List<Room> expectedRooms = new ArrayList<>();

        Room room = new Room();
        Room room1 = new Room();
        Room room2 = new Room();
        expectedRooms.add(room);
        expectedRooms.add(room1);
        expectedRooms.add(room2);

        when(roomRepository.findAll()).thenReturn(expectedRooms);

        List<Room> actualRooms = roomService.getAllRooms();

        assertEquals(expectedRooms, actualRooms);
    }

    /**
     * Test method for {@link RoomService#getRoomById(Long)}.
     */
    @Test
    void getRoomById_ok() {
        Room expectedRoom = getRoom();

        when(roomRepository.findById(expectedRoom.getRoomId())).thenReturn(Optional.of(expectedRoom));

        Room actualRoom = roomService.getRoomById(expectedRoom.getRoomId());

        assertEquals(expectedRoom.getRoomId(), actualRoom.getRoomId());
    }

    /**
     * Test method for {@link RoomService#getRoomById(Long)}.
     */
    @Test
    void getRoomById_notFound() {
        when(roomRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->{
           roomService.getRoomById(2L);
        });

        String expectedMessage = "Room with id 2 does not exist.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    private Room getRoom() {
        Room expectedRoom = new Room();
        expectedRoom.setRoomId(1L);
        expectedRoom.setName("Test room");
        expectedRoom.setCountry("Belarus");
        expectedRoom.setLamp(new Lamp());

        return expectedRoom;
    }
}