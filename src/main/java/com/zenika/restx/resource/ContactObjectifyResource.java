package com.zenika.restx.resource;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.zenika.restx.domain.ZenUser;
import com.zenika.restx.persistence.objectify.ContactsDaoObjectify;
import restx.annotations.DELETE;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;

import java.util.List;

@Component
@RestxResource
public class ContactObjectifyResource {
    private static final String CONTACTS_CACHE_KEY = "com.zenika.training.zencontact.service.ContactService.getAll";
    private MemcacheService cache = MemcacheServiceFactory.getMemcacheService();

    @GET("/v2/users")
    @PermitAll
    /**
     * Get the users defined in a arraylist : only mock objects
     */
    public Iterable<ZenUser> getAllUsers() {
        List<ZenUser> contacts = (List<ZenUser>) cache.get(CONTACTS_CACHE_KEY);
        if (contacts == null) {

            contacts = ContactsDaoObjectify.getInstance().getAll();

            // quelques secondes pour ne pas attendre trop pendant le testing
            // on met en cache qu'un liste avec au moins un élément
            boolean isCached = contacts.size() > 0 && cache.put(CONTACTS_CACHE_KEY, contacts, Expiration.byDeltaSeconds(240), MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT);
        }
        return contacts;
    }

    @GET("/v2/users/{id}")
    @PermitAll
    public Optional<ZenUser> getUser(final Long id) {
        ZenUser user = ContactsDaoObjectify.getInstance().get(id);
        return Optional.fromNullable(user);
    }

    @DELETE("/v2/users/{id}")
    @PermitAll
    public void deleteUser(final Long id) {
        cache.delete(CONTACTS_CACHE_KEY);
        ContactsDaoObjectify.getInstance().delete(id);
    }

    @POST("/v2/users")
    @PermitAll
    public ZenUser storeUser(final ZenUser user) {
        cache.delete(CONTACTS_CACHE_KEY);
        if (Strings.isNullOrEmpty(user.password)) {
            user.password = "azerty";
        }
        if (user.id == null) {
            user.id(ContactsDaoObjectify.getInstance().save(user));
        }
        return user;
    }

}
