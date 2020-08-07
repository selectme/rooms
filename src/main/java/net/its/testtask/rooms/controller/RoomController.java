package net.its.testtask.rooms.controller;

import com.neovisionaries.i18n.CountryCode;
import lombok.AllArgsConstructor;
import net.its.testtask.rooms.model.Lamp;
import net.its.testtask.rooms.model.Room;
import net.its.testtask.rooms.model.User;
import net.its.testtask.rooms.service.IpResolverService;
import net.its.testtask.rooms.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@AllArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;
    private final IpResolverService ipResolverService;

    @GetMapping
    public String getAllRooms(Model model) {
        List<Room> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "room-list";
    }

    @GetMapping("/create")
    public String createRoomForm(Model model, Room room) {
//        Set<String> countries = getCountries();
        Map<String, String> countries = getCountries();
        model.addAttribute("countries", countries);
        return "room-create";
    }

    @PostMapping("/create")
    public String createRoom(Room room) {

        roomService.createRoom(room);
        return "redirect:/rooms";
    }

    @GetMapping("/{roomId}/join")
    public String joinRoomForm(@PathVariable Long roomId, Model model, User user) {
        Room room = roomService.getRoomById(roomId);
        model.addAttribute("room", room);
        return "room-join";
    }

    @PostMapping("/join")
    public String joinRoom(@RequestParam("roomId") Long roomId, User user, Model model, HttpServletRequest request) {

        String userIp = getIp(request);
        String userCountryCode = ipResolverService.getUserCountryCodeByIp(userIp);

        Room room = roomService.getRoomById(roomId);

        if (getCountryNameByCountryCode(userCountryCode).equals(room.getCountry())) {
            roomService.addUserToRoom(roomId, user);
            return "redirect:/rooms/" + roomId;
        } else {
            String error = "You are not allowed to join this room";
            model.addAttribute("error", error);
            return "room-access-denied";
        }
    }

    private String getIp(HttpServletRequest request) {
        String userIp = "";
        if (request != null) {
            userIp = request.getHeader("X-FORWARDED-FOR");
            if (userIp == null || "".equals(userIp)) {
                userIp = request.getRemoteAddr();
            }
        }
        return userIp;
    }

    @GetMapping("/{roomId}")
    public String getRoom(@PathVariable Long roomId, Model model, HttpServletRequest request) {


        String userIp = getIp(request);
        String userCountryCode = ipResolverService.getUserCountryCodeByIp(userIp);
        Room room = roomService.getRoomById(roomId);

        if (getCountryNameByCountryCode(userCountryCode).equals(room.getCountry())) {

            Set<User> users = room.getUsers();
            Lamp lamp = room.getLamp();
            model.addAttribute("room", room);
            model.addAttribute("lamp", lamp);
            model.addAttribute("users", users);
            return "room";
        } else {
            String error = "You are not allowed to join this room";
            model.addAttribute("error", error);
            return "room-access-denied";
        }
    }

    @PutMapping("/{roomId}/switch-lamp")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void switchLight(@PathVariable("roomId") Long roomId, @RequestParam("isOn") boolean isOn) {
        Room room = roomService.getRoomById(roomId);
        room.getLamp().setOn(isOn);
        roomService.switchLight(room);
    }

    private String getCountryNameByCountryCode(String countryCode) {
        return CountryCode.getByCode(countryCode).getName();
    }

    private Map<String, String> getCountries() {
        String[] countriesISO = Locale.getISOCountries();

        Map<String, String> isoCountry = new TreeMap<>();

        for (String countryIsoCode : countriesISO) {
            CountryCode countryCode = CountryCode.getByCode(countryIsoCode);
            isoCountry.put(countryCode.getName(), countryCode.getAlpha2());
        }

        return isoCountry;

    }
}



