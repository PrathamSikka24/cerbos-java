package com.example.demoappfinal.controller;

import dev.cerbos.sdk.CerbosBlockingClient;
import dev.cerbos.sdk.CerbosClientBuilder;
import dev.cerbos.sdk.CheckResult;
import dev.cerbos.sdk.builders.Principal;
import dev.cerbos.sdk.builders.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/checkLeaveRequest")
    public String checkLeaveRequest() {
        try {
            // Initialize the Cerbos client
            CerbosBlockingClient client = new CerbosClientBuilder("localhost:3593").withPlaintext().buildBlockingClient();

            // Prepare a check for a hypothetical "approve" action on a "leave_request" resource
            CheckResult result = client.check(
                    Principal.newInstance("someUser", "employee"), // Example principal
                    Resource.newInstance("leave_request", "12345"), // Example resource
                    "approve"); // Example action

            // Check if the action is allowed based on the policy
            if (result.isAllowed("approve")) {
                // Action is allowed
                return "Action approved!";
            } else {
                // Action is not allowed (should not happen with the given policy)
                return "Action not approved.";
            }
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
            return "Error processing request: " + e.getMessage();
        }
    }
}
