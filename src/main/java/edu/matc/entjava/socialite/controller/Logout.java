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
@WebServlet(urlPatterns = {"/logout"})

public class Logout extends HttpServlet {

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
        HttpSession session = request.getSession(false);

        if(session != null) session.invalidate();

        // forward request
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

}

