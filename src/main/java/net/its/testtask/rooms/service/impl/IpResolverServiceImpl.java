package net.its.testtask.rooms.service.impl;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import net.its.testtask.rooms.service.IpResolverService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Arrays;

/**
 * Implementation of {@link IpResolverService}.
 *
 * @see IpResolverService
 */
@Service
public class IpResolverServiceImpl implements IpResolverService {


    @Value("${ip.db.path}")
    private String ipDatabasePath;

    @Value("${localhost.ip}")
    private String[] localhostIpValues;

    @Value("${local.country.code}")
    private String localCountryCode;

    @Override
    public String getUserCountryCodeByIp(String userIp) {
        Assert.hasText(userIp, "userIp must not be null");

        String countryCode;
        try {
            InputStream dbFile = new ClassPathResource(
                    ipDatabasePath).getInputStream();
            DatabaseReader databaseReader = new DatabaseReader.Builder(dbFile).build();
            InetAddress ipAddress = InetAddress.getByName(userIp);
            CountryResponse response = databaseReader.country(ipAddress);
            countryCode = response.getCountry().getIsoCode();
        } catch (GeoIp2Exception e) {
            if (Arrays.asList(localhostIpValues).contains(userIp)) {
                countryCode = localCountryCode;
            } else {
                throw new IllegalArgumentException("IP " + userIp + " was not found in the database.");
            }
        } catch (IOException e) {
            throw new IllegalStateException("IO exception has occurred.", e);
        }
        return countryCode;
    }
}
