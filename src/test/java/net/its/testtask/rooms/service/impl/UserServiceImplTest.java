package net.its.testtask.rooms.service.impl;

import net.its.testtask.rooms.model.Room;
import net.its.testtask.rooms.model.User;
import net.its.testtask.rooms.repository.UserRepository;
import net.its.testtask.rooms.service.AbstractServiceTest;
import net.its.testtask.rooms.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link UserService}.
 */
class UserServiceImplTest extends AbstractServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Test method for {@link UserService#findUserByIp(String)}
     */
    @Test
    void findUserByIp_ok() {
        User expectedUser = getUser();

        when(userRepository.findByIp(expectedUser.getIp())).thenReturn(expectedUser);

        User foundUser = userService.findUserByIp(expectedUser.getIp());

        assertEquals(expectedUser.getIp(), foundUser.getIp());
    }

    /**
     * Test method for {@link UserService#findUserByIp(String)}
     */
    @Test
    void findUSerByIp_notFound() {
        User expectedUser = getUser();

        when(userRepository.findByIp(expectedUser.getIp())).thenReturn(expectedUser);

        User foundUser = userService.findUserByIp("11.111.11.1");

        assertNull(foundUser);
    }

    private User getUser() {
        User user = new User();
        user.setName("Test");
        user.setRoom(new Room());
        user.setIp("127.0.0.1");

        return user;
    }
}