package com.zenika.restx.persistence.datastore;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.*;
import com.zenika.restx.domain.ZenUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactsDaoDatastore {

    private static ContactsDaoDatastore INSTANCE = new ContactsDaoDatastore();

    public static ContactsDaoDatastore getInstance() {
        return INSTANCE;
    }

    public DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    // Création d'un ancestor pour gérer la cohérence des requêtes
    private Key agenda = KeyFactory.createKey("Agenda", 1);

    public long save(ZenUser contact) {
        Entity e = new Entity("Contact", agenda);
        if (contact.id != null) {
            Key k = KeyFactory.createKey(agenda, "Contact", contact.id);
            try {
                e = datastore.get(k);
            } catch (EntityNotFoundException e1) {

            }
        } else {
            e = new Entity("Contact", agenda);
        }
        e.setProperty("firstname", contact.firstName);
        e.setProperty("lastname", contact.lastName);
        e.setProperty("email", contact.email);
        if (contact.birthdate != null) {
            e.setProperty("birthdate", contact.birthdate);
        }
        e.setProperty("notes", contact.notes);
        /*BlobKey cvKey = contact.getCvKey();
        if (cvKey != null) {
			e.setProperty("cvKey", cvKey);
		}*/

        Transaction txn = datastore.beginTransaction();
        Key key;
        try {
            key = datastore.put(e);
            txn.commit();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
                throw new RuntimeException();
            }
        }
        return key.getId();

    }

    public void delete(Long id) {
        Transaction txn = datastore.beginTransaction();
        try {
            Key k = KeyFactory.createKey(agenda, "Contact", id);
            datastore.delete(k);
            txn.commit();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
                throw new RuntimeException();
            }
        }
    }

    public ZenUser get(Long id) {
        Entity e;
        try {
            e = datastore.get(KeyFactory.createKey(agenda, "Contact", id));
        } catch (EntityNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        return ZenUser.create()
                .id(e.getKey().getId())
                .firstName((String) e.getProperty("firstname"))
                .lastName((String) e.getProperty("lastname"))
                .email((String) e.getProperty("email"))
                .birthdate((Date) e.getProperty("birthdate"))
                .notes((String) e.getProperty("notes"));
		/*contact.setCvKey((BlobKey) e.getProperty("cvKey"));
		*/
    }

    public List<ZenUser> getAll() {
        List<ZenUser> contacts = new ArrayList<>();

        Query q =
                new Query("Contact", agenda)
                        .addProjection(new PropertyProjection("firstname", String.class))
                        .addProjection(new PropertyProjection("lastname", String.class))
                        .addProjection(new PropertyProjection("email", String.class))
                        .addProjection(new PropertyProjection("notes", String.class));

        PreparedQuery pq = datastore.prepare(q);

        for (Entity e : pq.asIterable()) {
            contacts.add(ZenUser.create()
                    .id(e.getKey().getId())
                    .firstName((String) e.getProperty("firstname"))
                    .lastName((String) e.getProperty("lastname"))
                    .email((String) e.getProperty("email"))
                    .notes((String) e.getProperty("notes")));

        }

        return contacts;
    }

    public BlobKey fetchOldCvKey(Long id) {
        Key k = KeyFactory.createKey(agenda, "Contact", id);
        BlobKey key = null;
        try {
            Entity entity = datastore.get(k);
            key = (BlobKey) entity.getProperty("cvKey");
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
        return key;
    }
}
