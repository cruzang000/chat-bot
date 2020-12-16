package edu.matc.entjava.socialite.controller.member;

import edu.matc.entjava.socialite.entity.User;
import edu.matc.entjava.socialite.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/home"})

public class MemberHome extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     *  Handles HTTP GET requests. accepts request for a user sign up
     *
     *@param  request    the HttpServletRequest object
     *@param  response   the HttpServletResponse object
     *@exception IOException   if there is an IO failure
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        session.setAttribute("user", null);

        if (request.getUserPrincipal() != null) {
            String username = request.getUserPrincipal().getName();

            User user = (User) new GenericDao(User.class).getByPropertyValue("username", username).get(0);

            // set session and req attributes
            request.setAttribute("currentUserPlans", user.getCurrentPlans());
            session.setAttribute("user", user);
        }

        // forward request
        request.getRequestDispatcher("/jsp/member/memberHome.jsp").forward(request, response);
    }
}

