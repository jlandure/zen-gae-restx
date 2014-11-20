package com.zenika.restx.persistence.objectify;

import com.google.appengine.api.blobstore.BlobKey;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.zenika.restx.domain.ZenUser;

import java.util.List;

public class ContactsDaoObjectify {

    private static ContactsDaoObjectify INSTANCE = new ContactsDaoObjectify();

    public static ContactsDaoObjectify getInstance() {
        ObjectifyService.factory().register(ZenUser.class);
        return INSTANCE;
    }

    public Long save(ZenUser contact) {
        return ObjectifyService.ofy().save().entity(contact).now().getId();
    }

    public void delete(Long id) {
        ObjectifyService.ofy().delete().key(Key.create(ZenUser.class, id)).now();
    }

    public ZenUser get(Long id) {
        return ObjectifyService.ofy().load().key(Key.create(ZenUser.class, id)).now();
    }

    public List<ZenUser> getAll() {
        return ObjectifyService.ofy().load().type(ZenUser.class).list();
    }

    public BlobKey fetchOldCvKey(Long id) {
        return null;
    }
}
