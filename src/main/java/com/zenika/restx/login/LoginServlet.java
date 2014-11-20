package com.zenika.restx.login;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {

        String thisURL = req.getRequestURI();
        resp.setContentType("text/html");
        if (AuthenticationService.getInstance().getUser() != null) {
            resp.getWriter().println("<p>Hello, " +
                    AuthenticationService.getInstance().getUser().getNickname() +
                    "!" + (AuthenticationService.getInstance().isAdmin() ? "<br/> You're an admin !" : "<br/>")
                    +"<br/> You can " +
                    "<a href=\"" + AuthenticationService.getInstance().getLogoutURL(thisURL) +
                    "\">sign out</a>.</p>");
        } else {
            resp.getWriter().println("<p>Please " +
                    "<a href=\"" + AuthenticationService.getInstance().getLoginURL(thisURL) +
                    "\">sign in</a>.</p>");
        }
    }
}
