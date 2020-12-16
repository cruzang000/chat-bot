package edu.matc.entjava.socialite.rest;

import edu.matc.entjava.socialite.entity.User;
import edu.matc.entjava.socialite.entity.UserUpdateRequest;
import edu.matc.entjava.socialite.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * GeoLocations service class serves as api class for requesting a search against
 * the GeoDao api class from front end
 */
@Path("/user")
public class UserApi {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Updates user from params
     *
     * @param req the req
     * @return the user plan location ids
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("update")
    public Response updateUser(@Context HttpServletRequest req, UserUpdateRequest userUpdateRequest) {
        boolean wasUpdated = false;

        logger.info(userUpdateRequest.toString());

        // update user
        if (req.getRemoteUser() != null) {
            GenericDao userDao = new GenericDao(User.class);

            // get current user
            User user = (User) userDao.getByPropertyValue("username", req.getRemoteUser()).get(0);

            user.setFirstName(userUpdateRequest.getFirstName());
            user.setLastName(userUpdateRequest.getLastName());

            if (userUpdateRequest.getPassword().length() > 0) {
                user.setPassword(userUpdateRequest.getPassword());
            }

            userDao.saveOrUpdate(user);
            //TODO confirm update
            user = (User) userDao.getByPropertyValue("username", req.getRemoteUser()).get(0);

            wasUpdated = user.getFirstName().equals(userUpdateRequest.getFirstName()) &&
                    user.getLastName().equals(userUpdateRequest.getLastName());
        }

        // return response with user update response
        return Response.ok().entity(wasUpdated).build();
    }
}
