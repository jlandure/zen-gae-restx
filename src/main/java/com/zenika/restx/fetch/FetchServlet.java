package com.zenika.restx.fetch;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

public class FetchServlet extends HttpServlet {

    private static URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();

    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        URL url = new URL("https://www.google.com/?q=appengine#q=appengine");
        HTTPResponse responseFetch = fetchService.fetch(url);

        byte[] content = responseFetch.getContent();

        if (responseFetch.getResponseCode() != 200) {
            //Erreur
        } else {
            resp.getWriter().write(new String(content));
        }

    }

}
