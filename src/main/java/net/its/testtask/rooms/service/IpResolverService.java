package net.its.testtask.rooms.service;

/**
 * Provides methods for work ip address.
 */
public interface IpResolverService {

     /**
      * Allows to get country code using ip address.
      *
      * @param ip according to which will the country code will be determined.
      * @return country code in string representation if found, {@code null} - otherwise
      */
     String getUserCountryCodeByIp(String ip);
}
