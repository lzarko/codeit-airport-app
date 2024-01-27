package com.example.airportapp.facade;

import com.example.airportapp.domain.Airport;
import com.example.airportapp.domain.Flight;
import com.example.airportapp.dto.request.CreateFlightDTO;
import com.example.airportapp.dto.response.FlightDTO;
import com.example.airportapp.mapper.FlightMapper;
import com.example.airportapp.objectmother.domain.AirportBuilderObjectMother;
import com.example.airportapp.objectmother.domain.FlightBuilderObjectMother;
import com.example.airportapp.objectmother.dto.CreateFlightDTOBuilderObjectMother;
import com.example.airportapp.service.AirportService;
import com.example.airportapp.service.FlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlightFacadeUnitTest {

    private final FlightMapper flightMapper = new FlightMapper();
    private final FlightService flightService = Mockito.mock(FlightService.class);
    private final AirportService airportService = Mockito.mock(AirportService.class);
    private final FlightFacade flightFacade = new FlightFacade(flightService, airportService, flightMapper);

    @Test
    void createAndSaveFlight_createFlightDTO_flightDTO() {
        // arange
        CreateFlightDTO createFlightDTO = CreateFlightDTOBuilderObjectMother.getTestingBuilder()
                .build();
        Airport departureAirport = AirportBuilderObjectMother.getTestingBuilder(1L)
                .build();
        Airport destinationAirport = AirportBuilderObjectMother.getTestingBuilder(2L)
                .code("MVA")
                .build();
        Flight flight = FlightBuilderObjectMother.getTestingBuilder(1L)
                .departureAirport(departureAirport)
                .destinationAirport(destinationAirport).build();
        FlightDTO flightDTO = new FlightDTO(flight);

        when(flightService.saveFlight(any(Flight.class))).thenReturn(flight);

        // act
        FlightDTO actualFlightDTO = flightFacade.createAndSaveFlight(createFlightDTO);

        // assert
        assertThat(actualFlightDTO.getId()).isEqualTo(flightDTO.getId());
        assertThat(actualFlightDTO.getDepartureAirportCode()).isEqualTo(flightDTO.getDepartureAirportCode());
        assertThat(actualFlightDTO.getDestinationAirportCode()).isEqualTo(flightDTO.getDestinationAirportCode());
        assertThat(actualFlightDTO.getDepartureTimeInMinutes()).isEqualTo(flightDTO.getDepartureTimeInMinutes());
        assertThat(actualFlightDTO.getFlightDurationInMinutes()).isEqualTo(flightDTO.getFlightDurationInMinutes());

        verify(flightService).saveFlight(any(Flight.class));
    }

    @Test
    void findAllDirectFlightsForSpecificAirportCodesAndMapToFlightDTOs_departureCodeAndDestinationCode_listOfFlightDTOs() {
        // arrange
        String departureCode = "MAV";
        String destinationCode = "VAM";
        Airport departureAirport = AirportBuilderObjectMother.getTestingBuilder(1L)
                .code(departureCode)
                .build();
        Airport destinationAirport = AirportBuilderObjectMother.getTestingBuilder(2L)
                .code(destinationCode)
                .build();
        Flight flightOne = FlightBuilderObjectMother.getTestingBuilder(1L)
                .departureAirport(departureAirport)
                .destinationAirport(destinationAirport)
                .build();
        Flight flightTwo = FlightBuilderObjectMother.getTestingBuilder(2L)
                .departureAirport(departureAirport)
                .destinationAirport(destinationAirport)
                .build();
        List<Flight> flights = List.of(flightOne, flightTwo);

        when(flightService.findAllDirectFlightsForSpecificAirportCodes(departureCode, destinationCode)).thenReturn(flights);

        // act
        List<FlightDTO> actualFlights = flightFacade.findAllDirectFlightsForSpecificAirportCodesAndMapToFlightDTOs(departureCode, destinationCode);

        // assert
        assertThat(actualFlights).hasSize(2);
        assertThat(actualFlights.get(0).getId()).isEqualTo(flights.get(0).getId());
        assertThat(actualFlights.get(1).getId()).isEqualTo(flights.get(1).getId());
        assertThat(actualFlights.get(0).getDepartureAirportCode()).isEqualTo(flights.get(0).getDepartureAirport().getCode());
        assertThat(actualFlights.get(1).getDepartureAirportCode()).isEqualTo(flights.get(1).getDepartureAirport().getCode());
        assertThat(actualFlights.get(0).getDestinationAirportCode()).isEqualTo(flights.get(0).getDestinationAirport().getCode());
        assertThat(actualFlights.get(1).getDestinationAirportCode()).isEqualTo(flights.get(1).getDestinationAirport().getCode());

        verify(flightService).findAllDirectFlightsForSpecificAirportCodes(departureCode, destinationCode);
    }
}
