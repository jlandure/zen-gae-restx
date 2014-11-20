package com.zenika.restx.hello;

import org.junit.ClassRule;
import org.junit.Test;
import restx.tests.RestxSpecRule;

public class HelloResourceSpecTest {
    @ClassRule
    public static RestxSpecRule rule = new RestxSpecRule();

    @Test
    public void should_say_hello() throws Exception {
        rule.runTest("com/zenika/restx/hello/should_say_hello.spec.yaml");
    }
}
