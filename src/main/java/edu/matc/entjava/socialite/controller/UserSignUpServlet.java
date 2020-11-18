package edu.matc.entjava.socialite.controller;

import edu.matc.entjava.socialite.entity.User;
import edu.matc.entjava.socialite.persistence.GenericDao;
import edu.matc.entjava.socialite.service.FieldValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet to handle user sign up
 */
@WebServlet(urlPatterns = {"/userSignUp"})

public class UserSignUpServlet extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     *  Handles HTTP GET requests. accepts request for a user sign up
     *
     *@param  request    the HttpServletRequest object
     *@param  response   the HttpServletResponse object
     *@exception IOException   if there is an IO failure
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //create map from request data
        Map<String, String> signUpDetails = parseUserDetails(request);

        //get errors or empty string from validation method
        String errorsFound = String.join("", validateInsertData(signUpDetails));

        // if error was found set error else try to add user and set response
        if (errorsFound.length() > 0) {
            request.setAttribute("error", errorsFound);
            request.setAttribute("formValues", signUpDetails);
        } else {
            String message = "User could not be added, try again";
            String signUserUpResults = signUserUp(signUpDetails);

            if (signUserUpResults.equals("success")) {
                message = "You're all signed up, Log in to access member options.";
            } else {
                // set user details in request
                request.setAttribute("formValues", signUpDetails);
            }

            // sets error or success message depending on return value of signUserUp()
            request.setAttribute(signUserUpResults, message);
        }

        // forward request
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    /**
     * gets request object and parses user details returning hash map
     * @param request request object from servlet containing user sing up details
     * @return map of user details
     */
    private Map<String, String> parseUserDetails(HttpServletRequest request) {
         return new HashMap<>() {
            {
                put("firstName", request.getParameter("firstName"));
                put("lastName", request.getParameter("lastName"));
                put("dateOfBirth", request.getParameter("dateOfBirth"));
                put("email", request.getParameter("email"));
                put("username", request.getParameter("username"));
                put("password", request.getParameter("password"));
            }
        };
    }


    /**
     * validate user data from map passed in as param
     * @param signUpDetails map of user details
     *                    
     * @return string array of validation results
     */
    private String[] validateInsertData(Map<String, String> signUpDetails){
        FieldValidator fieldValidator = new FieldValidator();

        //validate params adds error or empty message to position in string array
        return new String[]{
            fieldValidator.validateOnlyLettersString(signUpDetails.get("firstName"), 0,
                    "<li>First name is invalid, must be only letters.</li>"),
            fieldValidator.validateOnlyLettersString(signUpDetails.get("lastName"), 0,
                    "<li>Last name is invalid, must be only letters.</li>"),
            fieldValidator.validateDate(signUpDetails.get("dateOfBirth"),"<li>Date of birth is invalid, must be valid date of birth [MM/DD/YYYY].</li>"),
            fieldValidator.validateMinAge(signUpDetails.get("dateOfBirth"), 18, "<li>Age must be over 18.</li>"),
            fieldValidator.validateEmail(signUpDetails.get("email"), "<li>Email is invalid is invalid.</li>"),
            fieldValidator.validateDuplicateProperty(User.class, "email", signUpDetails.get("email"),
                    "<li>email is already being used.</li>"),
            fieldValidator.validateByRegex(signUpDetails.get("username"), "[a-zA-z][A-za-z0-9]+",
                    "<li>Username is invalid, must start with a letter and include letters or numbers.</li>"),
            fieldValidator.validateDuplicateProperty(User.class, "username", signUpDetails.get("username"),
                    "<li>Username is already being used.</li>"),
        };
    }

    /**
     * uses generic dao and passed in params to insert new user, returns 0 on non insert otherwise id
     * @param signUpDetails map of user details to sign up
     * @return success or error string
     */
    private String signUserUp(Map<String, String> signUpDetails) {
        // instantiate generic dao
        GenericDao dao = new GenericDao(User.class);
        int id = 0;

        try {
            id = dao.insert(new User(
                signUpDetails.get("username"),
                signUpDetails.get("password"),
                signUpDetails.get("lastName"),
                signUpDetails.get("firstName"),
                signUpDetails.get("email"),
                LocalDate.parse(signUpDetails.get("dateOfBirth")),
                    false)
            );
            logger.info("id: " + id);
        } catch (ConstraintViolationException e) {
            logger.error("error: Email or username is already used", e);
        }

        // create and insert user
        return id > 0 ? "success" : "error";
    }
}

