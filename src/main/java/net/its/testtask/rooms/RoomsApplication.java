package net.its.testtask.rooms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("net.its.testtask.rooms.model")
@EnableJpaRepositories("net.its.testtask.rooms.repository")
public class RoomsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomsApplication.class, args);
    }

}
