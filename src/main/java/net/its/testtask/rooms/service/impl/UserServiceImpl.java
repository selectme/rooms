package net.its.testtask.rooms.service.impl;

import net.its.testtask.rooms.model.User;
import net.its.testtask.rooms.repository.UserRepository;
import net.its.testtask.rooms.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

/**
 * Implementation of {@link UserService}
 *
 * @see UserService
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User findUserByIp(String ip) {
        Assert.hasText(ip, "ip must not be empty");

        return userRepository.findByIp(ip);
    }

    @Override
    @Transactional
    public void deleteUserByIp(String ip) {
        Assert.hasText(ip, "ip must not be empty");

        userRepository.deleteByIp(ip);
    }
}
