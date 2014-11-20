package com.zenika.restx.service;

import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.zenika.restx.domain.ZenUser;
import com.zenika.restx.persistence.datastore.ContactsDaoDatastore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CVService {

    private static final Logger LOG = Logger.getLogger(CVService.class.getName());
    private static CVService INSTANCE = new CVService();
    private ContactsDaoDatastore contactsDao = ContactsDaoDatastore.getInstance();
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    public static CVService getInstance() {
        return INSTANCE;
    }

    public void deleteOldCV(Long id) {
        BlobKey cvKey = contactsDao.fetchOldCvKey(id);
        if (cvKey != null) {
            blobstoreService.delete(cvKey);
        }
    }

    public void prepareUploadURL(ZenUser contact) {
        String uploadURL = blobstoreService.createUploadUrl("/zencontact/uploadCV/" + contact.id);
        LOG.warning("upload URL  : " + uploadURL);
        contact.uploadURL(uploadURL);
    }

    public void prepareDownloadURL(ZenUser contact) {
        BlobKey cvKey = contact.cvKey;
        String url = cvKey == null ? null : "/zencontact/CV/" + cvKey.getKeyString();
        contact.downloadURL(url);
    }

    public void updateCV(ZenUser contact, HttpServletRequest req) {
        Map<String, List<BlobKey>> uploads = blobstoreService.getUploads(req);
        if (!uploads.keySet().isEmpty()) {
            // delete old CV from BlobStore to save disk space
            deleteOldCV(contact.id);
            // update CV BlobKey in Contact entity
            Iterator<String> names = uploads.keySet().iterator();
            String cvName = names.next();
            List<BlobKey> keys = uploads.get(cvName);
            contact.cvKey(keys.get(0));
        }
    }

    public void serve(BlobKey blobKey, HttpServletResponse resp) throws IOException {
        BlobInfoFactory blobInfoFactory = new BlobInfoFactory(DatastoreServiceFactory.getDatastoreService());
        BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);
        LOG.log(Level.INFO, "Serving " + blobInfo.getFilename());
        resp.setHeader("Content-Disposition", "attachment; filename=" + blobInfo.getFilename());
        blobstoreService.serve(blobKey, resp);
    }

}
