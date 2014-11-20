package com.zenika.restx.persistence;

import com.zenika.restx.domain.ZenUser;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public static List<ZenUser> USERS = new ArrayList<ZenUser>(10);

    static {

        USERS.add(ZenUser.create()
                .id(123L)
                .firstName("Julien")
                .lastName("Landuré")
                .email("julien.landure@zenika.com")
                .password("julien")
                .lastConnectionDate(DateTime.parse("2014-02-19T14:34:20.000+02:00").toDate()));
        USERS.add(ZenUser.create()
                .id(1L)
                .firstName("Pierre")
                .lastName("Reliquet")
                .email("pierre.reliquet@zenika.com")
                .password("pierre")
                .lastConnectionDate(DateTime.parse("2013-03-12T09:33").toDate()));
    }
}
