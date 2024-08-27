package com.learning.service;

import com.learning.model.request.FlightRequest;
import com.learning.model.response.FlightResponse;

import java.util.Optional;

public interface JocService {
    Optional<FlightResponse> getFlightResponse(FlightRequest flightRequest);
}
