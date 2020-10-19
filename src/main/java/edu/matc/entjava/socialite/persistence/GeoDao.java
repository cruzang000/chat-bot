package edu.matc.entjava.socialite.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.matc.entjava.socialite.entity.Search;
import edu.matc.entjava.socialite.entity.User;
import edu.matc.entjava.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Array;
import java.util.*;

/**
 * The type Geo dao.
 */
public class GeoDao implements PropertiesLoader {

    private final Logger logger = LogManager.getLogger(this.getClass());
    protected Properties properties;
    protected GenericDao genericDao;


    /**
     * make api request for geo locations using zipcode, adds each search to db and returns geo search object list
     * @param zipcode zipcode of area to search
     * @param maxRows the max rows
     * @return the geo locations by zipcode
     */
    public Search[] getGeoLocationsByZipcode(int zipcode, int maxRows, User user) {
        //load in properties
        properties = this.loadProperties("/api.properties");
        //build api url
        String url = properties.getProperty("geonames.url.zipcode");

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url).queryParam("postalcode", zipcode).queryParam("maxRows", maxRows);

        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);

        //json object
        JSONArray postalCodes = new JSONObject(response).getJSONArray("postalCodes");

        genericDao = new GenericDao(Search.class);

        //instantiate and configure object mapper to use java array
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Search[] searches = null;

        //try to convert response to GeoSearch array catch json processing exception
        try {
            searches = mapper.readValue(postalCodes.toString(), Search[].class);

            //add searches to db
            Arrays.stream(searches).forEach(search -> {
                search.setUser(user);
                genericDao.insert(search);
            });
        } catch (JsonProcessingException e) {
            logger.error("GeoSearch request error:", e);
        }

        return searches;
    }
}

