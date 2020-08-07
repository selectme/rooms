package net.its.testtask.rooms.service.impl;

import net.its.testtask.rooms.model.User;
import net.its.testtask.rooms.repository.UserRepository;
import net.its.testtask.rooms.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
        Assert.notNull(user, "user must not be null");

        userRepository.save(user);
    }
}
