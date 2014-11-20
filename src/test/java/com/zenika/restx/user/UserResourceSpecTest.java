package com.zenika.restx.user;

import org.junit.ClassRule;
import org.junit.Test;
import restx.tests.RestxSpecRule;

public class UserResourceSpecTest {
    @ClassRule
    public static RestxSpecRule rule = new RestxSpecRule();

    @Test
    public void shouldTestUsers() throws Exception {
        rule.runTest("com/zenika/restx/user/should_get_all_user.spec.yaml");
        rule.runTest("com/zenika/restx/user/should_get_123_user.spec.yaml");
    }

    @Test
    public void shouldCreateUser() throws Exception {
        rule.runTest("com/zenika/restx/user/should_create_a_user.spec.yaml");
        rule.runTest("com/zenika/restx/user/should_create_a_user_without_password.spec.yaml");
    }
}
