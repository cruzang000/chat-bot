package edu.matc.entjava.socialite.persistence;

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
    public ArrayList<Search> getGeoLocationsByZipcode(int zipcode, int maxRows, User user) {
        //load in properties
        properties = this.loadProperties("/api.properties");
        //build api url
        String url = properties.getProperty("geonames.url.zipcode");

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url).queryParam("postalcode", zipcode).queryParam("maxRows", maxRows);

        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);

        //json object
        JSONArray postalCodes = new JSONObject(response).getJSONArray("postalCodes");

        ArrayList<Search> searches = new ArrayList<>();

        genericDao = new GenericDao(Search.class);

        //loops through json object and creates new location then adds to array list
        for (int x = 0; x < postalCodes.length(); x++) {
            Search search = new Search(
                postalCodes.getJSONObject(x).has("postalCode") ? zipcode : null,
                postalCodes.getJSONObject(x).has("placeName") ? postalCodes.getJSONObject(x).get("placeName").toString() : null,
                postalCodes.getJSONObject(x).has("ISO3166-2") ? postalCodes.getJSONObject(x).get("ISO3166-2").toString() : null,
                postalCodes.getJSONObject(x).has("lat") ? (double) postalCodes.getJSONObject(x).get("lat") : 0,
                postalCodes.getJSONObject(x).has("lng") ? (double) postalCodes.getJSONObject(x).get("lng") : 0,
                user
            );

            //add search to db
            genericDao.insert(search);

            searches.add(search);
        }

        return searches;
    }
}

