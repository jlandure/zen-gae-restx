package com.zenika.restx.resource;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.zenika.restx.domain.ZenUser;
import com.zenika.restx.persistence.UserRepository;
import com.zenika.restx.persistence.datastore.ContactsDaoDatastore;
import restx.annotations.*;
import restx.factory.Component;
import restx.security.PermitAll;

@Component
@RestxResource
public class UserResource {

    @GET("/v0/users")
    @PermitAll
    /**
     * Get the users defined in a arraylist : only mock objects
     */
    public Iterable<ZenUser> getAllUsers() {
        return UserRepository.USERS;
    }

    @GET("/v0/users/{id}")
    @PermitAll
    public Optional<ZenUser> getUser(final Long id) {
        Iterable<ZenUser> users = UserRepository.USERS;
        Predicate<ZenUser> getUserById = new Predicate<ZenUser>() {
            public boolean apply(ZenUser user) {
                return user.id == id;
            }
        };
        ZenUser user = Iterables.find(users, getUserById, null);
        return Optional.fromNullable(user);
    }

    @PUT("/v0/users/{id}")
    @PermitAll
    public Optional<ZenUser> getUser(final Long id, final ZenUser user) {
        Iterable<ZenUser> users = UserRepository.USERS;
        Predicate<ZenUser> getUserById = new Predicate<ZenUser>() {
            public boolean apply(ZenUser user) {
                return user.id == id;
            }
        };
        UserRepository.USERS.set(Iterables.indexOf(users, getUserById), user);
        return Optional.fromNullable(user);
    }

    @DELETE("/v0/users/{id}")
    @PermitAll
    public void deleteUser(final Long id) {
        Iterable<ZenUser> users = UserRepository.USERS;
        Predicate<ZenUser> getUserById = new Predicate<ZenUser>() {
            public boolean apply(ZenUser user) {
                return user.id == id;
            }
        };
        UserRepository.USERS.remove(Iterables.indexOf(users, getUserById));
    }

    @POST("/v0/users")
    @PermitAll
    public ZenUser storeUser(final ZenUser user) {
        if(user.id == null) {
            user.id = Long.valueOf(UserRepository.USERS.size());
        }
        if(Strings.isNullOrEmpty(user.password)) {
            user.password = "azerty";
        }
        UserRepository.USERS.add(user);
        return user;
    }

}
