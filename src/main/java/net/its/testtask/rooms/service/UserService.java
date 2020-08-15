package net.its.testtask.rooms.service;

import net.its.testtask.rooms.model.User;
import org.springframework.lang.Nullable;

/**
 * Provides method for work with {@link User}.
 */
public interface UserService  {

    /**
     * Allows to get {@link User} from a database using ip address.
     *
     * @param ip according to which {@link User} will be searched in a database
     * @return {@link User} if found, {@code null} - otherwise
     */
    @Nullable
    User findUserByIp(String ip);

    /**
     * Allows to delete {@link User} using its ip address.
     *
     * @param ip according to which {@link User} will be deleted from a database
     */
    void deleteUserByIp(String ip);

}
