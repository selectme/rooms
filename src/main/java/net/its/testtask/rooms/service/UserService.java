package net.its.testtask.rooms.service;

import net.its.testtask.rooms.model.User;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

public interface UserService  {

    void createUser(User user);

}
