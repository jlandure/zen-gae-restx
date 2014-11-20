package com.zenika.restx;

import com.google.common.base.Charsets;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import restx.factory.Module;
import restx.factory.Provides;
import restx.security.CORSAuthorizer;
import restx.security.SignatureKey;
import restx.security.StdCORSAuthorizer;

import javax.inject.Named;
import java.util.regex.Pattern;

@Module
public class AppModule {
    @Provides
    public SignatureKey signatureKey() {
        return new SignatureKey("5f768f23-703e-4268-9e9e-51d2e052b6a1 4082747839477764571 MyApp myapp".getBytes(Charsets.UTF_8));
    }

    /*@Provides
    public CORSAuthorizer getApiDocsAuthorizer() {
        return new StdCORSAuthorizer(
                Predicates.<CharSequence>alwaysTrue(), // predicate filtering the origin you want to accept, here accept all
                Predicates.contains(Pattern.compile("/")),
                //Predicates.contains(Pattern.compile("^/@/api-docs")), // predicate filtering the URI restx path to apply this authorizer to
                ImmutableList.of("GET","POST")); // HTTP verbs to accept
    }*/

    @Provides
      @Named("restx.admin.password")
      public String restxAdminPassword() {
        return "admin";
    }

    @Provides
    @Named("app.name")
    public String appName() {
        return "MyApp";
    }
}