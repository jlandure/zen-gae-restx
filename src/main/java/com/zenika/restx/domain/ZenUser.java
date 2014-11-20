package com.zenika.restx.domain;

import com.google.appengine.api.blobstore.BlobKey;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Unindex;

import java.io.Serializable;
import java.util.Date;

@Entity
public class ZenUser implements Serializable {

    @Id
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public Date lastConnectionDate;
    public Date birthdate;
    @Unindex
    public String notes;
    public BlobKey cvKey; // points to the blobstore entry if any

    @Ignore
    public String uploadURL;
    @Ignore
    public String downloadURL;

    public static ZenUser create() {
        return new ZenUser();
    }

    public ZenUser id(Long id) {
        this.id = id;
        return this;
    }

    public ZenUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ZenUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ZenUser email(String email) {
        this.email = email;
        return this;
    }

    public ZenUser password(String password) {
        this.password = password;
        return this;
    }

    public ZenUser lastConnectionDate(Date lastConnectionDate) {
        this.lastConnectionDate = lastConnectionDate;
        return this;
    }

    public ZenUser birthdate(Date birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public ZenUser notes(String notes) {
        this.notes = notes;
        return this;
    }

    public ZenUser cvKey(BlobKey cvKey) {
        this.cvKey = cvKey;
        return this;
    }

    public ZenUser uploadURL(String uploadURL) {
        this.uploadURL = uploadURL;
        return this;
    }

    public ZenUser downloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
        return this;
    }

}
