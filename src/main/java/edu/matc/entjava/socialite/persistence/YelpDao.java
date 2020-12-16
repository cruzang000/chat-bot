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
import java.util.List;
import java.util.Properties;
import org.json.*;

public class YelpDao implements PropertiesLoader {

    private final Logger logger = LogManager.getLogger(this.getClass());
    protected Properties properties;
    protected GenericDao genericDao;

    /**
     * make request to yelp api using geo locations, ands locations to db and
     * returns location object with categories and addresses
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

        genericDao = new GenericDao(Location.class);

        //instantiate and configure object mapper to use java array
        ObjectMapper mapper = new ObjectMapper();

        //configure mapper to use array and not fail on unused properties
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Location[] locations = null;

        //try to convert response to GeoSearch array catch json processing exception
        try {
            //map location, location.locationCategories, location.locationAddresses
            locations = mapper.readValue(businesses.toString(), Location[].class);

            //add locations, locationCategories, and locationAddresses to db if they dont exist already,
            // else get existing location and set to current spot
            for (int i = 0; i < locations.length; i++) {
                List<Location> matching = genericDao.getByPropertyValue("yelpID", locations[i].getYelpID());

                if (matching.size() > 0) {
                    locations[i] = matching.get(0);
                } else {
                    locations[i].setId(genericDao.insert(locations[i]));
                }
            }
        } catch (JsonProcessingException e) {
            logger.error("GeoSearch request error:", e);
        }

        return locations;
    }
}

