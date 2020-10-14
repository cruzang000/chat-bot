package edu.matc.entjava.socialite.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.matc.entjava.socialite.entity.GeoSearch;
import edu.matc.entjava.util.PropertiesLoader;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Properties;

public class YelpDao implements PropertiesLoader {

    private final Logger logger = LogManager.getLogger(this.getClass());
    protected Properties properties;

    /**
     * make request to yelp api using geo locations, returns array of locations
     * @param lat
     * @param lng
     * @return
     */
    public YelpLocation[] getLocationByGeo(double lat, double lng) {

        //TODO code to get url from properties file
//        properties = this.loadProperties("main/resources/api.properties");
//        String url = properties.getProperty("geonames.url.base")
//                + properties.getProperty("geonames.url.addon.postalCodeSearch") + zipcode + "&"
//                + properties.getProperty("geonames.url.addon.maxRows") + "1&"
//                + properties.getProperty("geonames.url.addon.username");

        String url = "http://api.geonames.org/postalCodeSearchJSON?username=cruzang000&maxRows=1&postalcode="
                + zipcode;

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);

        //trim string to match collection format
        response = StringUtils.substringBetween(response, "[", "]");
        response = "[" + response + "]";

        //instantiate and configure object mapper to use java array
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);

        GeoSearch geoSearch = null;

        //try to convert response to GeoSearch array catch json processing exception
        try {
            geoSearch = mapper.readValue(response, GeoSearch[].class)[0];
        } catch (JsonProcessingException e) {
            logger.error("GeoSearch request error:", e);
        }

        return geoSearch;
    }
}

