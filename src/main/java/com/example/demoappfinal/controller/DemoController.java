package com.example.demoappfinal.controller;

import dev.cerbos.sdk.CerbosBlockingClient;
import dev.cerbos.sdk.CerbosClientBuilder;
import dev.cerbos.sdk.CheckResult;
import dev.cerbos.sdk.builders.Principal;
import dev.cerbos.sdk.builders.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static dev.cerbos.sdk.builders.AttributeValue.stringValue;

@RestController
public class DemoController {

    @GetMapping
    public String hello()  {
        try {
            CerbosBlockingClient client = new CerbosClientBuilder("localhost:3593").withPlaintext().buildBlockingClient();
            CheckResult result = client.check(
                    Principal.newInstance("john", "employee")
                            .withPolicyVersion("20210210")
                            .withAttribute("department", stringValue("marketing"))
                            .withAttribute("geography", stringValue("GB")),
                    Resource.newInstance("leave_request", "xx125")
                            .withPolicyVersion("20210210")
                            .withAttribute("department", stringValue("marketing"))
                            .withAttribute("geography", stringValue("GB"))
                            .withAttribute("owner", stringValue("john")),
                    "view:public", "approve");

            if (result.isAllowed("approve")) { // returns true if `approve` action is allowed
                return "Hello World!";
            }
        } catch (Exception e) {
            return e.toString();
        }
        return "unauthorized";
    }
}
