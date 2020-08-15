package net.its.testtask.rooms.repository;

import net.its.testtask.rooms.model.Room;
import net.its.testtask.rooms.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for {@link UserRepository}.
 */
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    /**
     * Test method for {@link UserRepository#findByIp(String)}.
     */
    @Test
    void findByIp_ok() {
        User user = getUser();

        User foundUser = userRepository.findByIp(user.getIp());

        assertEquals(user.getIp(), foundUser.getIp());
    }

    /**
     * Test method for {@link UserRepository#findByIp(String)}.
     */
    @Test
    void findByIp_notFound() {
        User foundUser = userRepository.findByIp("11.11");

        assertNull(foundUser);
    }

    private User getUser() {
        User user = new User();
        user.setName("Test");
        user.setIp("127.0.0.1");
        userRepository.save(user);

        return user;
    }
}