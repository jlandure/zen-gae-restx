package com.zenika.restx.resource;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.zenika.restx.domain.ZenUser;
import com.zenika.restx.persistence.UserRepository;
import com.zenika.restx.persistence.datastore.ContactsDaoDatastore;
import com.zenika.restx.service.CVService;
import restx.annotations.*;
import restx.factory.Component;
import restx.security.PermitAll;

@Component
@RestxResource
public class ContactDatastoreResource {

    CVService cvService = CVService.getInstance();

    @GET("/v1/users")
    @PermitAll
    /**
     * Get the users defined in a arraylist : only mock objects
     */
    public Iterable<ZenUser> getAllUsers() {
        return ContactsDaoDatastore.getInstance().getAll();
    }

    @GET("/v1/users/{id}")
    @PermitAll
    public Optional<ZenUser> getUser(final Long id) {
        ZenUser user = ContactsDaoDatastore.getInstance().get(id);
        cvService.prepareUploadURL(user);
        cvService.prepareDownloadURL(user);

        return Optional.fromNullable(user);
    }

    /*@PUT("/v1/users/{id}")
    @PermitAll
    public Optional<ZenUser> getUser(final Long id, final ZenUser user) {
*/
    @DELETE("/v1/users/{id}")
    @PermitAll
    public void deleteUser(final Long id) {
        ContactsDaoDatastore.getInstance().delete(id);
    }

    @POST("/v1/users")
    @PermitAll
    public ZenUser storeUser(final ZenUser user) {

        if(Strings.isNullOrEmpty(user.password)) {
            user.password = "azerty";
        }
        if(user.id == null) {
            user.id(ContactsDaoDatastore.getInstance().save(user));
        }
        return user;
    }

}
