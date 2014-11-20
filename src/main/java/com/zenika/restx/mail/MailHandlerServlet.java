package com.zenika.restx.mail;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class MailHandlerServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            //MimeMessage encapsule toutes les informations
            MimeMessage message = new MimeMessage(session,
                    request.getInputStream());

            log(message.getSubject());            //Sujet du mail

            Multipart multipart = (Multipart) message.getContent();
            BodyPart part = multipart.getBodyPart(0);
            log((String) part.getContent());        //Contenu du mail

            for (Address sender : message.getFrom())
                log(sender.toString());                //Origine du mail
        } catch (MessagingException e) {
        }
    }
}
