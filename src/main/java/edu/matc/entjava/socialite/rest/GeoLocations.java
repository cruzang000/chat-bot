package edu.matc.entjava.socialite.rest;

import edu.matc.entjava.socialite.entity.Search;
import edu.matc.entjava.socialite.entity.User;
import edu.matc.entjava.socialite.persistence.GenericDao;
import edu.matc.entjava.socialite.persistence.GeoDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.json.JsonArray;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * GeoLocations service class serves as api class for requesting a search against
 * the GeoDao api class from front end
 */
@Path("/geoLocations")
public class GeoLocations {

    /**
     * when requested takes query param zipcode and max rows (default 1 row) and validates, if valid method uses the GeoDao class
     * to query for the zipcode and get a list of searches to return the latitude nad longitude of
     * the first result as a json object
     * @param zipcode zipcode to query api for
     * @param maxRows number of results to get
     * @returns number of lng and lat objects wanted
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/zipcode")
    public Response getGeoLocations(@QueryParam("zipcode") String zipcode, @DefaultValue("1") @QueryParam("maxRows") String maxRows) {
        //get only lat and lng of first object as json object
        JSONArray locations = new JSONArray();
        Boolean requestZipcode = this.validateZipcode(zipcode);

        // if valid zipcode add user and use geoDao to send a request for Search objects
        if (requestZipcode) {
            GeoDao geoDao = new GeoDao();

            User user = (User) new GenericDao(User.class).getById(608);

            Search[] searches = geoDao.getGeoLocationsByZipcode(Integer.parseInt(zipcode), Integer.parseInt(maxRows), user);

            // loop searches
            for (Search search : searches) {
                // create json object
                JSONObject geoObject = new JSONObject();

                // add longitude and latitude for json object
                geoObject.put("lat", search.getLatitude());
                geoObject.put("lng", search.getLongitude());

                // add json object to json array
                locations.put(geoObject);
            }
        }

        // return response with location json array
        return Response.ok().entity(locations).build();
    }

    /**
     * validates zipcode is 5 digits
     * @param zipcode zipcode entered
     * @return boolean response
     */
    protected Boolean validateZipcode(String zipcode) {
        return zipcode.matches("^[0-9]{5}$");
    }
}
