package edu.matc.entjava.socialite.rest;

import edu.matc.entjava.socialite.entity.Location;
import edu.matc.entjava.socialite.entity.User;
import edu.matc.entjava.socialite.entity.UserPlan;
import edu.matc.entjava.socialite.persistence.GenericDao;
import edu.matc.entjava.socialite.service.FieldValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * GeoLocations service class serves as api class for requesting a search against
 * the GeoDao api class from front end
 */
@Path("/userPlans")
public class UserPlans {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Gets user plan location ids.
     *
     * @param req the req
     * @return the user plan location ids
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("userPlanLocationIds")
    public Response getUserPlanLocationIds(@Context HttpServletRequest req) {
        // get only lat and lng of first object as json object
        JSONArray userPlansArray = new JSONArray();

        if (req.getRemoteUser() != null) {
            // get current user
            User user = (User) new GenericDao(User.class).getByPropertyValue("username", req.getRemoteUser()).get(0);
            Set<UserPlan> userPlans = user.getCurrentPlans();

            // if user and userPlans size is great than 0, put all location id's in json array and return
            if (userPlans.size() > 0) {
                for (UserPlan userPlan : userPlans) { userPlansArray.put(userPlan.getLocation().getId()); }
            }
        }

        // return response with location json array
        return Response.ok().entity(userPlansArray).build();
    }

    /**
     * Manage user plan by location id.
     *
     * @param req             the req
     * @param locationId      the location id
     * @param requestedAction the requested action
     * @return the response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("manageUserPlan")
    public Response manageUserPlanByYelpId(@Context HttpServletRequest req, @QueryParam("locationId") int locationId,
                                            @QueryParam("action") String requestedAction) {
        // get only lat and lng of first object as json object
        JSONObject response = new JSONObject();

        if (req.getRemoteUser() != null) {
            // get current user
            User user = (User) new GenericDao(User.class).getByPropertyValue("username", req.getRemoteUser()).get(0);

            GenericDao userPlanDao = new GenericDao(UserPlan.class);
            Location location = (Location) new GenericDao(Location.class).getById(locationId);

            switch(requestedAction) {
                case "add":
                    addPlan(user, location, response, userPlanDao);
                    break;
                case "remove":
                    removePlan(user, location, response, userPlanDao);
                    break;
            }
        }

        // return response with location json array
        return Response.ok().entity(response).build();
    }

    /**
     * removes user plan
     * @param user
     * @param location
     * @param response
     * @param userPlanDao
     */
    private void removePlan(User user, Location location, JSONObject response, GenericDao userPlanDao) {
        Set<UserPlan> userPlans = user.getCurrentPlans();

        // if user and userPlans size is great than 0, put all location id's in json array and return
        if (userPlans.size() > 0) {
            UserPlan userPlan = userPlans.stream()
                    .filter(plan -> plan.getLocation().getId() == location.getId())
                    .findFirst().get();

            userPlan.setRemoved(true);

            userPlanDao.saveOrUpdate(userPlan);

            UserPlan retrievedUserPlan = (UserPlan) userPlanDao.getById(userPlan.getId());

            response.put("wasRemoved", retrievedUserPlan.getRemoved());
        }
    }

    /**
     * add user plan
     * @param user
     * @param location
     * @param response
     * @param userPlanDao
     */
    private void addPlan(User user, Location location, JSONObject response, GenericDao userPlanDao) {

        UserPlan userPlan = new UserPlan(false, user, location);

        UserPlan retrievedUserPlan = (UserPlan) userPlanDao.getById(userPlanDao.insert(userPlan));

        response.put("wasAdded", retrievedUserPlan != null);
    }


//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/")
//    public Response getUserPlans(@Context HttpServletRequest req, @FormParam("locationId") String locationId) {
//        // get only lat and lng of first object as json object
//        JSONObject response = new JSONObject();
//        response.put("error", true);
//        response.put("response", "");
//
//        // validate zipcode
//        Boolean isValidId = validateLocationId(locationId);
//
//        // if valid locationID get current user and check if location exists for user
//        if (isValidId) {
//            // get current user
//            GenericDao userDao = new GenericDao(User.class);
//            int userId = ((User) userDao.getByPropertyValue("username", req.getRemoteUser()).get(0)).getId();
//
//            // get user plans that match locations
//            List<UserPlan> userPlans = (List<UserPlan>) new GenericDao(UserPlan.class).getByPropertyValue("location_id", locationId);
//
//            // TODO: find a better way to do this by making gen dao accept multiple field values
//            // get plans that match current user
//            response.put("response", userPlans);
//        }
//
//        // return response with location json array
//        return Response.ok().entity(response).build();
//    }

    /**
     * Validate location id boolean.
     *
     * @param locationId the location id
     * @return the boolean
     */
    protected Boolean validateLocationId(String locationId) {
        return new FieldValidator().validateByRegex(String.valueOf(locationId), "^\\d+$", "0").length() == 0;
    }
}
