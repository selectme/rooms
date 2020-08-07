package net.its.testtask.rooms.service.impl;

import com.neovisionaries.i18n.CountryCode;
import lombok.AllArgsConstructor;
import net.its.testtask.rooms.model.Lamp;
import net.its.testtask.rooms.model.Room;
import net.its.testtask.rooms.model.User;
import net.its.testtask.rooms.repository.RoomRepository;
import net.its.testtask.rooms.repository.UserRepository;
import net.its.testtask.rooms.service.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Override
    public void createRoom(Room room) {
        Assert.notNull(room, "Room must not be null");

        CountryCode countryCode = CountryCode.getByCode(room.getCountry());
        if (countryCode == null) {
            throw new IllegalArgumentException("Wrong country");
        } else {
            room.setCountry(countryCode.getName());
        }
        room.setLamp(new Lamp());
        roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
        return (List<Room>) roomRepository.findAll();
    }

    @Override
    @Transactional
    public void addUserToRoom(Long roomId, User user) {
        Assert.notNull(roomId, "Id must not be null");
        Assert.notNull(user, "User must not be null");

        Room room = getRoomById(roomId);
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

        roomRepository.save(room);
    }
}
