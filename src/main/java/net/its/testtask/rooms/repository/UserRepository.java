package net.its.testtask.rooms.repository;

import net.its.testtask.rooms.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for work with {@link User}.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Allows to find {@link User} using ip.
     *
     * @param ip {@link User} ip address
     * @return {@link User} if found, {@code null} - otherwise
     */
    User findByIp(String ip);

    /**
     * Allows to delete {@link User} using its ip address.
     *
     * @param ip according to which {@link User} will be deleted from a database
     */
    void deleteByIp(String ip);
}
