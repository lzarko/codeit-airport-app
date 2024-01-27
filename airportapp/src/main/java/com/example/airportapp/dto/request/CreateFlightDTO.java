package com.example.airportapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CreateFlightDTO {

    @NotBlank
    String departureAirportCode;
    @NotBlank
    String destinationAirportCode;
    @NotNull
    Long departureTimeInMinutes;
    @NotNull
    Long flightDurationInMinutes;
}
