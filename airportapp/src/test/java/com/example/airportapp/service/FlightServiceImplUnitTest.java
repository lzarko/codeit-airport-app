package com.example.airportapp.service;

import com.example.airportapp.domain.Airport;
import com.example.airportapp.domain.Flight;
import com.example.airportapp.exception.EntityNotFoundException;
import com.example.airportapp.objectmother.domain.AirportBuilderObjectMother;
import com.example.airportapp.objectmother.domain.FlightBuilderObjectMother;
import com.example.airportapp.repository.FlightRepository;
import com.example.airportapp.service.impl.FlightServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlightServiceImplUnitTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightServiceImpl flightService;

    @Test
    void findAllFlightsFromAnAirportWithSpecificCode_airportCode_flight() {
        // arrange
        String airportCode = "MVA";
        Airport airport = AirportBuilderObjectMother.getTestingBuilder(1L)
                .code(airportCode)
                .build();
        Flight flightOne = FlightBuilderObjectMother.getTestingBuilder(1L)
                .departureAirport(airport)
                .build();
        Flight flightTwo = FlightBuilderObjectMother.getTestingBuilder(2L)
                .departureAirport(airport)
                .build();
        List<Flight> expectedFlights = List.of(flightOne, flightTwo);

        when(flightRepository.findAndOrderAllFlightsFromAnAirportWithSpecificCode(airportCode)).thenReturn(expectedFlights);

        // act
        List<Flight> actualFlights = flightService.findAllFlightsFromAnAirportWithSpecificCode(airportCode);

        // assert
        assertThat(actualFlights).hasSize(2);
        assertThat(actualFlights).isEqualTo(expectedFlights);
    }

    @Test
    void findAllFlightsFromAnAirportWithSpecificCode_invalidAirportCode_entityNotFoundException() {
        // arrange
        String airportCode = "MVA";

        when(flightRepository.findAndOrderAllFlightsFromAnAirportWithSpecificCode(airportCode)).thenReturn(Collections.emptyList());

        // act, assert
        assertThatThrownBy(() -> flightService.findAllFlightsFromAnAirportWithSpecificCode(airportCode))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Flight was not found for the following code MVA");
    }
}
