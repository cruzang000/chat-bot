package edu.matc.entjava.socialite.rest;

import edu.matc.entjava.socialite.entity.Location;
import edu.matc.entjava.socialite.entity.LocationAddress;
import edu.matc.entjava.socialite.entity.Search;
import edu.matc.entjava.socialite.entity.User;
import edu.matc.entjava.socialite.persistence.GenericDao;
import edu.matc.entjava.socialite.persistence.GeoDao;
import edu.matc.entjava.socialite.persistence.YelpDao;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.text.html.Option;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/yelpLocations")
public class YelpLocations {

    /**
     * takes in longitude nad latitude with an optional category and queries yelp dao for locations, returns
     * json array of location objects found
     * @param lat latitude of location
     * @param lng longitude of location
     * @param category category to query for
     * @return json array of location objects
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/byGeo")
    public Response getGeoLocations(@QueryParam("lat") String lat, @QueryParam("lng") String lng,
                                    @DefaultValue("drinks") @QueryParam("category") String category) {

        //get only lat and lng of first object as json object
        JSONArray locations = new JSONArray();
        Boolean validGeo = this.validateGeo(lat, lng);

        // if valid zipcode add user and use geoDao to send a request for Search objects
        if (validGeo) {
            // instantiate yelp dao object and query api for locations with latitude, longitude and category
            YelpDao yelpDao = new YelpDao();
            Location[] locationResult = yelpDao.getLocationByGeoAndType(Double.parseDouble(lat), Double.parseDouble(lng), category);

            // loop locations found
            for (Location location : locationResult) {
                // get first address
                Optional<LocationAddress> locationAddressOpt = location.getAddresses().stream().findFirst();

                // check address is present and create json object
                locationAddressOpt.ifPresent(locationAddress -> locations.put(locationToJson(location, locationAddress)));
            }
        }

        // return response with location json array
        return Response.ok().entity(locations).build();
    }

    /**
     * validates longitude and latitude
     * @param lat latitude
     * @param lng longitude
     * @return boolean result if valid or not
     */
    protected Boolean validateGeo(String lat, String lng) {
        return true;//lat.matches("^[0-9]{5}$") && lng.matches("^[0-9]{5}$");
    }

    /**
     * takes location object and location address optional object and parses to json object format needed
     * @param locationAddress
     * @param location
     * @return locationObject
     */
    protected JSONObject locationToJson(Location location, LocationAddress locationAddress) {
        // create json object and setting properties manually to avoid unwanted fields
        JSONObject locationObject = new JSONObject();

        // add properties needed
        locationObject.put("id", location.getId()); // id
        locationObject.put("yelpID", location.getYelpID());
        locationObject.put("name", location.getName()); // name
        locationObject.put("imgURL", location.getImageUrl()); // image url
        locationObject.put("address-street", locationAddress.getStreet()); // street address
        locationObject.put("address-city-state", locationAddress.getCity() + ", "
                + locationAddress.getState() + " " + locationAddress.getZipcode()); // city state zip
        locationObject.put("phone", location.getPhone());
        locationObject.put("rating", location.getRating());

        return locationObject;
    }
}
