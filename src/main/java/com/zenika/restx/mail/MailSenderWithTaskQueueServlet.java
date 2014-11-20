package com.zenika.restx.mail;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MailSenderWithTaskQueueServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {


        Queue mailQueue = QueueFactory.getQueue("mail-queue");

        mailQueue.add(TaskOptions.Builder.withUrl("/sendMail")
                .param("destinataire", "julien.landure@zenika.com"));

    }
}
