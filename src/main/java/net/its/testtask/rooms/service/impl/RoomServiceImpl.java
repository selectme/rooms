package net.its.testtask.rooms.service.impl;

import com.neovisionaries.i18n.CountryCode;
import lombok.AllArgsConstructor;
import net.its.testtask.rooms.model.Room;
import net.its.testtask.rooms.model.User;
import net.its.testtask.rooms.repository.RoomRepository;
import net.its.testtask.rooms.repository.UserRepository;
import net.its.testtask.rooms.service.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Implementation of {@link RoomService}.
 *
 * @see RoomService
 */
@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public void createRoom(Room room) {
        Assert.notNull(room, "Room must not be null");

        CountryCode countryCode = CountryCode.getByCode(room.getCountry());
        if (countryCode == null) {
            throw new IllegalArgumentException("Wrong country");
        } else {
            room.setCountry(countryCode.getName());
        }
        roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
        return (List<Room>) roomRepository.findAll();
    }

    @Override
    @Transactional
    public void addUserToRoom(Room room, User user) {
        Assert.notNull(room, "room must not be null");
        Assert.notNull(user, "User must not be null");

        room.addUser(user);
        roomRepository.save(room);
    }

    @Override
    public Room getRoomById(Long id) {
        Assert.notNull(id, "Id must not be null");

        return roomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Room with id " + id + " does not exist."));
    }

    @Override
    public void switchLight(Room room) {
        Assert.notNull(room, "room must not be null");

        roomRepository.save(room);
    }
}
