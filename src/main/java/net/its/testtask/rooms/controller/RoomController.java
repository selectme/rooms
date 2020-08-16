package net.its.testtask.rooms.controller;

import com.neovisionaries.i18n.CountryCode;
import lombok.AllArgsConstructor;
import net.its.testtask.rooms.model.Lamp;
import net.its.testtask.rooms.model.Room;
import net.its.testtask.rooms.model.User;
import net.its.testtask.rooms.service.IpResolverService;
import net.its.testtask.rooms.service.RoomService;
import net.its.testtask.rooms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * Controller for {@link Room}.
 */
@Controller
@AllArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    public static final String X_FORWARDED_FOR_HEADER_NAME = "X-FORWARDED-FOR";
    public static final String IN_A_ROOM_MESSAGE = "You are already in a room ";
    public static final String NOT_ALLOWED_TO_JOIN_MESSAGE = "You are not allowed to join this room";
    public static final String FIRST_JOIN_MESSAGE = "You are not in this room. First join.";
    private final RoomService roomService;
    private final IpResolverService ipResolverService;
    private final UserService userService;

    /**
     * Returns a page with list of rooms.
     *
     * @param model   {@link Model}
     * @param request {@link HttpServletRequest}
     * @return a page with list of rooms
     */
    @GetMapping
    public String getAllRooms(Model model, HttpServletRequest request) {

        List<Room> rooms = roomService.getAllRooms();
        String userIp = getIp(request);
        User user = userService.findUserByIp(userIp);
        model.addAttribute("rooms", rooms);
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "room-list";
    }

    /**
     * Returns a room creation form page.
     *
     * @param model {@link Model}
     * @param room  {@link Room}
     * @return a room creation form page
     */
    @GetMapping("/create")
    public String createRoomForm(Model model, Room room) {
        Assert.notNull(room, "room must not be null");

        Map<String, String> countries = getCountries();
        model.addAttribute("countries", countries);
        return "room-create";
    }

    /**
     * Controller for creating {@link Room}
     *
     * @param room {@link Room}
     */
    @PostMapping("/create")
    public String createRoom(Room room) {
        Assert.notNull(room, "room must not be null.");

        roomService.createRoom(room);
        return "redirect:/rooms";
    }

    /**
     * Returns join room page if all conditionals are successful, error page otherwise.
     *
     * @param roomId  of {@link Room}
     * @param model   {@link Model}
     * @param user    {@link User}
     * @param request {@link HttpServletRequest}
     * @return join room page if all conditionals are successful, error page otherwise
     */
    @GetMapping("/{roomId}/join")
    public String joinRoomForm(@PathVariable Long roomId, Model model, User user, HttpServletRequest request) {
        Assert.notNull(roomId, "roomId must not be null.");

        Room room = roomService.getRoomById(roomId);

        String userIp = getIp(request);
        String userCountryCode = ipResolverService.getUserCountryCodeByIp(userIp);
        String error;
        if (getCountryNameByCountryCode(userCountryCode).equals(room.getCountry())) {
            User userToCheck = userService.findUserByIp(userIp);
            if (userToCheck == null) {
                model.addAttribute("room", room);
                return "room-join";
            } else {
                error = IN_A_ROOM_MESSAGE + userToCheck.getRoom().getName();
                model.addAttribute("error", error);
                return "room-access-denied";
            }
        } else {
            error = NOT_ALLOWED_TO_JOIN_MESSAGE;
            model.addAttribute("error", error);
            return "room-access-denied";
        }
    }

    /**
     * Controller for joining into {@link Room}.
     *
     * @param roomId  id of {@link Room} in which one {@link User} tries to join
     * @param user    {@link User} which tries to join
     * @param model   {@link Model}
     * @param request {@link HttpServletRequest}
     * @return {@link Room} page if all conditionals are successful, error page otherwise
     */
    @PostMapping("/join")
    public String joinRoom(@RequestParam("roomId") Long roomId, User user, Model model, HttpServletRequest request) {
        Assert.notNull(roomId, "roomId must not be null.");
        Assert.notNull(user, "user must not be null.");

        String userIp = getIp(request);
        String userCountryCode = ipResolverService.getUserCountryCodeByIp(userIp);

        Room room = roomService.getRoomById(roomId);
        String error;
        if (getCountryNameByCountryCode(userCountryCode).equals(room.getCountry())) {
            if (userService.findUserByIp(userIp) == null) {
                user.setIp(userIp);
                roomService.addUserToRoom(room, user);
                return "redirect:/rooms/" + roomId;
            } else {
                error = IN_A_ROOM_MESSAGE + user.getRoom().getName();
            }
        } else {
            error = NOT_ALLOWED_TO_JOIN_MESSAGE;
        }
        model.addAttribute("error", error);
        return "room-access-denied";
    }

    /**
     * Allows to enter into the {@link Room} if {@link User} was joined into this one earlier.
     *
     * @param roomId  id of {@link Room}
     * @param model   {@link Model}
     * @param request {@link HttpServletRequest}
     * @return {@link Room} page if {@link User} has rights to join into this one, error page otherwise
     */
    @GetMapping("/{roomId}")
    public String getRoom(@PathVariable Long roomId, Model model, HttpServletRequest request) {
        Assert.notNull(roomId, "roomId must not be null.");

        String userIp = getIp(request);
        String userCountryCode = ipResolverService.getUserCountryCodeByIp(userIp);
        Room room = roomService.getRoomById(roomId);
        String error = NOT_ALLOWED_TO_JOIN_MESSAGE;
        if (getCountryNameByCountryCode(userCountryCode).equals(room.getCountry())) {
            User user = userService.findUserByIp(userIp);
            if (user == null) {
                error = FIRST_JOIN_MESSAGE;
            } else if (user.getRoom().getRoomId().equals(roomId)) {
                Lamp lamp = room.getLamp();
                model.addAttribute("room", room);
                model.addAttribute("lamp", lamp);
                return "room";
            } else {
                error = IN_A_ROOM_MESSAGE + user.getRoom().getName();
            }
        }
        model.addAttribute("error", error);
        return "room-access-denied";
    }

    /**
     * Controller for switching state of {@link Lamp}
     *
     * @param roomId id of {@link Room}
     * @param isOn   state of {@link Lamp}, {@code true} means that {@link Lamp} is on, {@code false} - {@link Lamp} is off
     */
    @PutMapping("/{roomId}/switch-lamp")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public synchronized void switchLight(@PathVariable("roomId") Long roomId, @RequestParam("isOn") boolean isOn) {
        Assert.notNull(roomId, "roomId must not be null.");

        Room room = roomService.getRoomById(roomId);
        room.getLamp().setOn(isOn);
        roomService.switchLight(room);
    }

    /**
     * Allows to current {@link User} to leave {@link Room} he is assigned to.
     *
     * @param request {@link HttpServletRequest}
     * @return a page with list of {@link Room}
     */
    @GetMapping("/leave")
    public String leaveRoom(HttpServletRequest request) {

        userService.deleteUserByIp(getIp(request));
        return "redirect:/rooms";
    }

    /**
     * Allows to get a state of {@link Lamp} of concrete {@link Room}.
     *
     * @param roomId id of {@link Room}
     * @return {@code true} if {@link Lamp} state is on, {@code false} otherwise
     */
    @GetMapping("/{roomId}/lamp")
    @ResponseBody
    public boolean getLampState(@PathVariable(name = "roomId") Long roomId) {
        Assert.notNull(roomId, "roomId must not be null.");

        Room room = roomService.getRoomById(roomId);

        return room.getLamp().isOn();
    }

    private String getIp(HttpServletRequest request) {

        String userIp = request.getHeader(X_FORWARDED_FOR_HEADER_NAME);
        if (StringUtils.isEmpty(userIp)) {
            userIp = request.getRemoteAddr();
        }
        return userIp;
    }

    private String getCountryNameByCountryCode(String countryCode) {
        Assert.hasText(countryCode, "countryCode must not be empty.");

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



