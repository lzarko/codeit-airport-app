package com.example.airportapp.objectmother.dto;

import com.example.airportapp.dto.request.CreateFlightDTO;

public class CreateFlightDTOBuilderObjectMother {
    public static CreateFlightDTO.CreateFlightDTOBuilder getTestingBuilder() {
        return CreateFlightDTO.builder()
                .departureAirportCode("DepartureAirportCode")
                .destinationAirportCode("DestinationAirportCode")
                .departureTimeInMinutes((long) (Math.random() * 1000))
                .flightDurationInMinutes((long) (Math.random() * 1000));
    }

    public static CreateFlightDTO.CreateFlightDTOBuilder getTestingBuilderWithoutMandatoryFields() {
        return CreateFlightDTO.builder();
    }
}
