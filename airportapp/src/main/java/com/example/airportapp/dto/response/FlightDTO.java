package com.example.airportapp.dto.response;

import com.example.airportapp.domain.Flight;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class FlightDTO {

    private Long id;
    private String departureAirportCode;
    private String destinationAirportCode;
    private Long departureTimeInMinutes;
    private Long flightDurationInMinutes;

    public FlightDTO(Flight flight) {
        if (Objects.nonNull(flight)) {
            this.id = flight.getId();
            this.departureAirportCode = flight.getDepartureAirport().getCode();
            this.destinationAirportCode = flight.getDestinationAirport().getCode();
            this.departureTimeInMinutes = flight.getDepartureTimeInMinutes();
            this.flightDurationInMinutes = flight.getFlightDurationInMinutes();
        }
    }
}
