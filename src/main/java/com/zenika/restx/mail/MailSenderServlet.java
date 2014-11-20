package com.zenika.restx.mail;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class MailSenderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        String to = req.getParameter("destinataire");

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("julien.landure@zenika.com",
                    "Administrateur AppEngine"));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to, "M. Client"));

            /*msg.addRecipient(Message.RecipientType.BCC,
                    new InternetAddress("support@zenika.com"));*/

            msg.setReplyTo(new Address[]{
                    new InternetAddress("team@zen-gae-restx.appspotmail.com",
                            "Application team")});
            msg.setSubject("Envoi d'email depuis AppEngine");
            msg.setText("Cool feature too !!");

            Transport.send(msg); //Envoi par HTTP
            log("mail envoyé!");
            resp.getWriter().write("Mail envoyé !");

        } catch (AddressException e) {}
        catch (MessagingException e) {}
    }
}
