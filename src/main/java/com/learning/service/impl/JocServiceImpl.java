package com.learning.service.impl;

import com.learning.client.JocClient;
import com.learning.model.request.FlightRequest;
import com.learning.model.response.FlightResponse;
import com.learning.service.AuthTokenService;
import com.learning.service.JocService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JocServiceImpl implements JocService {

    private final JocClient jocClient;
    private final AuthTokenService authTokenService;

    @Value("${joc.client.api-key}")
    private String apiKey;

    @Override
    public Optional<FlightResponse> getFlightResponse(FlightRequest flightRequest) {
        log.info("[JocService] fetching flight data for flightRequest : {}", flightRequest);
        return Optional.ofNullable(jocClient.searchFlight(apiKey, authTokenService.getToken(), flightRequest));
    }
}
