package com.example.airportapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
public class FlightData {

    String destinationAirportCode;
    String departureAirportCode;
    Long departureTimeInMinutes;
    Long flightDurationInMinutes;

}
