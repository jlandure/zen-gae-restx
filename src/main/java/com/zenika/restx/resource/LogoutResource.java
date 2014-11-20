package com.zenika.restx.resource;

import com.zenika.restx.domain.Message;
import com.zenika.restx.login.AuthenticationService;
import org.joda.time.DateTime;
import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;

@Component
@RestxResource
public class LogoutResource {

    @GET("/logout")
    @PermitAll
    public Message sayHello() {
        Message msg = new Message();
        msg.message = String.format(
                "hello1 %s, it's %s",
                AuthenticationService.getInstance().getUsername(), DateTime.now().toString("HH:mm:ss"));

        msg.message += "<br/>You can " +
                "<a href=\"" + AuthenticationService.getInstance().getLogoutURL("/login") +
                "\">sign out</a>.</p>";
        return msg;
    }
}

