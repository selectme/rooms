package net.its.testtask.rooms.service;

import net.its.testtask.rooms.repository.RoomRepository;
import net.its.testtask.rooms.repository.UserRepository;
import net.its.testtask.rooms.service.impl.IpResolverServiceImpl;
import net.its.testtask.rooms.service.impl.RoomServiceImpl;
import net.its.testtask.rooms.service.impl.UserServiceImpl;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

public class ServiceTestConfiguration {

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private UserRepository userRepository;

    @Bean
    public UserService employeeService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Bean
    public RoomService roomService(RoomRepository roomRepository){
        return new RoomServiceImpl(roomRepository);
    }

    @Bean
    public IpResolverService ipResolverService(){
        return new IpResolverServiceImpl();
    }
}

