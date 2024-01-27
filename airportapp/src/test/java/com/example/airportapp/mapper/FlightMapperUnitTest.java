package com.example.airportapp.mapper;

import com.example.airportapp.domain.Airport;
import com.example.airportapp.domain.Flight;
import com.example.airportapp.dto.request.CreateFlightDTO;
import com.example.airportapp.dto.response.FlightDTO;
import com.example.airportapp.objectmother.domain.AirportBuilderObjectMother;
import com.example.airportapp.objectmother.domain.FlightBuilderObjectMother;
import com.example.airportapp.objectmother.dto.CreateFlightDTOBuilderObjectMother;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FlightMapperUnitTest {

    private final FlightMapper flightMapper = new FlightMapper();

    @Test
    void mapCreateFlightDTOToFlightEntity_createFlightDTOWithDepartureAirportAndDestinationAirport_flight() {
        // arrange
        Airport departureAirport = AirportBuilderObjectMother.getTestingBuilder(1L)
                .build();
        Airport destinationAirport = AirportBuilderObjectMother.getTestingBuilder(2L)
                .build();

        CreateFlightDTO createFlightDTO = CreateFlightDTOBuilderObjectMother.getTestingBuilder()
                .build();

        // act
        Flight actualFlight = flightMapper.mapCreateFlightDTOToFlightEntity(createFlightDTO, departureAirport, destinationAirport);

        // assert
        assertThat(actualFlight.getDepartureAirport()).isEqualTo(departureAirport);
        assertThat(actualFlight.getDestinationAirport()).isEqualTo(destinationAirport);
        assertThat(actualFlight.getDepartureTimeInMinutes()).isEqualTo(createFlightDTO.getDepartureTimeInMinutes());
        assertThat(actualFlight.getFlightDurationInMinutes()).isEqualTo(createFlightDTO.getFlightDurationInMinutes());
    }

    @Test
    void mapFlightsToFlightDTOs_listOfFlights_listOfFlightDTOs() {
        // arrange
        Flight flightOne = FlightBuilderObjectMother.getTestingBuilder(1L).build();
        Flight flightTwo = FlightBuilderObjectMother.getTestingBuilder(1L).build();
        List<Flight> flightList = List.of(flightOne, flightTwo);

        // act
        List<FlightDTO> flightDTOs = flightMapper.mapFlightsToFlightDTOs(flightList);

        // assert
        assertThat(flightDTOs).hasSize(2);
        assertThat(flightDTOs.get(0).getDepartureAirportCode()).isEqualTo(flightOne.getDepartureAirport().getCode());
        assertThat(flightDTOs.get(1).getDepartureAirportCode()).isEqualTo(flightTwo.getDepartureAirport().getCode());
        assertThat(flightDTOs.get(0).getDestinationAirportCode()).isEqualTo(flightOne.getDestinationAirport().getCode());
        assertThat(flightDTOs.get(1).getDestinationAirportCode()).isEqualTo(flightTwo.getDestinationAirport().getCode());
        assertThat(flightDTOs.get(0).getDepartureTimeInMinutes()).isEqualTo(flightOne.getDepartureTimeInMinutes());
        assertThat(flightDTOs.get(1).getDepartureTimeInMinutes()).isEqualTo(flightTwo.getDepartureTimeInMinutes());
        assertThat(flightDTOs.get(0).getFlightDurationInMinutes()).isEqualTo(flightOne.getFlightDurationInMinutes());
        assertThat(flightDTOs.get(1).getFlightDurationInMinutes()).isEqualTo(flightTwo.getFlightDurationInMinutes());
    }
}
