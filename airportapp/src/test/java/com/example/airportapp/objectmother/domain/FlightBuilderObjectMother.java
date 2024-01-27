package com.example.airportapp.objectmother.domain;

import com.example.airportapp.domain.Flight;

public class FlightBuilderObjectMother {
    public static Flight.FlightBuilder getTestingBuilder(Long id) {
        return Flight.builder()
                .id(id)
                .departureAirport(AirportBuilderObjectMother.getTestingBuilder(1L).build())
                .destinationAirport(AirportBuilderObjectMother.getTestingBuilder(2L).build())
                .departureTimeInMinutes(1074L)
                .flightDurationInMinutes(745L);
    }
}
