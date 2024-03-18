package com.example.demoappfinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cerbos.sdk.CerbosBlockingClient;
import dev.cerbos.sdk.CerbosClientBuilder;
import dev.cerbos.sdk.CheckResult;
import dev.cerbos.sdk.builders.Principal;
import dev.cerbos.sdk.builders.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/checkLeaveRequest")
    public String checkLeaveRequest() {
        try {
            // Initialize the Cerbos client
            CerbosBlockingClient client = new CerbosClientBuilder("localhost:3593").withPlaintext().buildBlockingClient();

            // Prepare a check for a hypothetical "approve" action on a "leave_request" resource
            CheckResult result = client.check(
                    Principal.newInstance("someUser", "employee"),
                    Resource.newInstance("leave_request", "12345"),
                    "approve");

            // Serialize the CheckResult to JSON and return
            return objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
            return "{\"error\":\"Error processing request: " + e.getMessage() + "\"}";
        }
    }
}
