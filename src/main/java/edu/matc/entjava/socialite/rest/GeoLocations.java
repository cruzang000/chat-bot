package edu.matc.entjava.socialite.rest;

import edu.matc.entjava.socialite.entity.Search;
import edu.matc.entjava.socialite.entity.User;
import edu.matc.entjava.socialite.persistence.GenericDao;
import edu.matc.entjava.socialite.persistence.GeoDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

import javax.json.JsonArray;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

@Path("/geoLocations")
public class GeoLocations {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGeoLocation() {
        GeoDao geoDao = new GeoDao();

        User user = (User) new GenericDao(User.class).getById(608);

        Search[] searches = geoDao.getGeoLocationsByZipcode(53511, 1, user);

        return Response.status(200).entity(searches).build();
    }
}
