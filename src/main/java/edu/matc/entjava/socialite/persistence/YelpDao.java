package edu.matc.entjava.socialite.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.matc.entjava.socialite.entity.Location;
import edu.matc.entjava.socialite.entity.Search;
import edu.matc.entjava.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import org.json.*;

public class YelpDao implements PropertiesLoader {

    private final Logger logger = LogManager.getLogger(this.getClass());
    protected Properties properties;
    GenericDao genericDao;
    private Object business;

    /**
     * make request to yelp api using geo locations, returns arraylist of locations
     * @param lat latitude of location
     * @param lng longitude of location
     * @param locationType category type for location
     * @return array list of locations
     */
    public Location[] getLocationByGeoAndType(double lat, double lng, String locationType) {

        properties = this.loadProperties("/api.properties");
        String url = properties.getProperty("yelp.url.base");

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url)
                .queryParam("term", locationType)
                .queryParam("latitude", lat)
                .queryParam("longitude", lng);

        String response = target.request(MediaType.APPLICATION_JSON)
                .header("Authorization", properties.getProperty("yelp.api.key"))
                .get(String.class);

        //json object
        JSONArray businesses = new JSONObject(response).getJSONArray("businesses");

        //change price "$" char to rating out of 5 using length of value
        //businesses.forEach(business -> { logger.info(business); }); // loop out each different business object

        logger.info(businesses);

        genericDao = new GenericDao(Location.class);

        //instantiate and configure object mapper to use java array
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Location[] locations = null;

        //try to convert response to GeoSearch array catch json processing exception
        try {
            locations = mapper.readValue(businesses.toString(), Location[].class);

            //add searches to db
            Arrays.stream(locations).forEach(entity -> { 
                logger.info(entity);
                genericDao.insert(entity);
            });
        } catch (JsonProcessingException e) {
            logger.error("GeoSearch request error:", e);
        }

        return locations;
    }
}

