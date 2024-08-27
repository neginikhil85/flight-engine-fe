package com.learning.client;

import com.learning.model.request.FlightRequest;
import com.learning.model.response.FlightResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "joc-client", url = "${joc.client.base-url}")
public interface JocClient {

    @PostMapping("/api/flight/search")
    FlightResponse searchFlight(@RequestHeader("User_key") String apiKey,
                                @RequestHeader("Authorization") String auth,
                                @RequestBody FlightRequest request);
}