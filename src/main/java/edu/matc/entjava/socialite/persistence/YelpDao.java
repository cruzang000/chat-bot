package edu.matc.entjava.socialite.persistence;

import edu.matc.entjava.socialite.entity.Location;
import edu.matc.entjava.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Properties;
import org.json.*;

public class YelpDao implements PropertiesLoader {

    private final Logger logger = LogManager.getLogger(this.getClass());
    protected Properties properties;
    GenericDao genericDao;

    /**
     * make request to yelp api using geo locations, returns arraylist of locations
     * @param lat latitude of location
     * @param lng longitude of location
     * @param locationType category type for location
     * @return array list of locations
     */
    public ArrayList<Location> getLocationByGeoAndType(double lat, double lng, String locationType) {

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

        ArrayList<Location> locations = new ArrayList<>();

        //loops through json object and creates new location then adds to array list
        for (int x = 0; x < businesses.length(); x++) {
            logger.info(businesses.getJSONObject(x));
            Location location = new Location(
                businesses.getJSONObject(x).has("name") ? businesses.getJSONObject(x).get("name").toString() : null,
                businesses.getJSONObject(x).has("url") ? businesses.getJSONObject(x).get("url").toString() : null,
                businesses.getJSONObject(x).has("id") ? businesses.getJSONObject(x).get("id").toString() : null,
                businesses.getJSONObject(x).has("price") ? businesses.getJSONObject(x).get("price").toString().length() : 0,
                businesses.getJSONObject(x).has("rating") ? (double) businesses.getJSONObject(x).get("rating") : 0,
                businesses.getJSONObject(x).has("image_url") ? businesses.getJSONObject(x).get("image_url").toString() : null,
                businesses.getJSONObject(x).has("display_phone") ? businesses.getJSONObject(x).get("display_phone").toString() : null,
                businesses.getJSONObject(x).has("review_count") ? (int) businesses.getJSONObject(x).get("review_count") : 0
            );



            //TODO: implement location address, and category entity creation and inserts as well


            //add location to db
            genericDao.insert(location);

            locations.add(location);
        }

        return locations;
    }
}

