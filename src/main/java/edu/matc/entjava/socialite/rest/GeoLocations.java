package edu.matc.entjava.socialite.rest;

import edu.matc.entjava.socialite.entity.Search;
import edu.matc.entjava.socialite.entity.User;
import edu.matc.entjava.socialite.persistence.GenericDao;
import edu.matc.entjava.socialite.persistence.GeoDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * GeoLocations service class serves as api class for requesting a search against
 * the GeoDao api class from front end
 */
@Path("/geoLocations")
public class GeoLocations {

    private final Logger logger = LogManager.getLogger(this.getClass());

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
    public Response getGeoLocations(@Context HttpServletRequest req, @QueryParam("zipcode") String zipcode,
                                    @DefaultValue("1") @QueryParam("maxRows") String maxRows) {
        //get only lat and lng of first object as json object
        JSONArray locations = new JSONArray();
        Boolean requestZipcode = this.validateZipcode(zipcode);

        // if valid zipcode add user and use geoDao to send a request for Search objects
        if (requestZipcode) {
            GeoDao geoDao = new GeoDao();
            String username = "guest";

            // try and get current user from request, catch null request and set to guest
            try { username = !req.getRemoteUser().isEmpty() ? req.getRemoteUser() : username; } catch (NullPointerException ignored) {}

            User user = (User) new GenericDao(User.class).getByPropertyValue("username", username).get(0);

            Search[] searches = geoDao.getGeoLocationsByZipcode(Integer.parseInt(zipcode), Integer.parseInt(maxRows), user);
            // loop searches
            for (Search search : searches) { locations.put(searchToJson(search)); }
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

    /**
     * take search object and return in format needed for json
     * @param search
     * @return
     */
    protected JSONObject searchToJson(Search search) {
        // create json object and setting properties manually to avoid unwanted fields
        JSONObject geoObject = new JSONObject();

        // add longitude and latitude for json object
        geoObject.put("lat", search.getLatitude());
        geoObject.put("lng", search.getLongitude());
        geoObject.put("city", search.getCity());
        geoObject.put("state", search.getState());
        geoObject.put("zipcode", search.getZipcode());

        return geoObject;
    }
}
