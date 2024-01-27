package com.example.airportapp.controller;

import com.example.airportapp.domain.Airport;
import com.example.airportapp.domain.Flight;
import com.example.airportapp.dto.request.CreateFlightDTO;
import com.example.airportapp.dto.response.FlightDTO;
import com.example.airportapp.facade.FlightFacade;
import com.example.airportapp.objectmother.domain.AirportBuilderObjectMother;
import com.example.airportapp.objectmother.domain.FlightBuilderObjectMother;
import com.example.airportapp.objectmother.dto.CreateFlightDTOBuilderObjectMother;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlightController.class)
public class FlightControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private FlightFacade flightFacade;

    @Test
    void addFlight_createFlightDTO_flightDTO() throws Exception {
        // arrange
        CreateFlightDTO createFlightDTO = CreateFlightDTOBuilderObjectMother.getTestingBuilder()
                .build();
        Airport departureAirport = AirportBuilderObjectMother.getTestingBuilder(1L)
                .code(createFlightDTO.getDepartureAirportCode())
                .build();
        Airport destinationAirport = AirportBuilderObjectMother.getTestingBuilder(2L)
                .code(createFlightDTO.getDestinationAirportCode())
                .build();
        Flight flight = FlightBuilderObjectMother.getTestingBuilder(1L)
                .departureAirport(departureAirport)
                .destinationAirport(destinationAirport)
                .build();
        FlightDTO flightDTO = new FlightDTO(flight);

        when(flightFacade.createAndSaveFlight(createFlightDTO)).thenReturn(flightDTO);

        // act
        ResultActions response = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/flights/add-flight")
                .content(mapper.writeValueAsString(createFlightDTO))
                .contentType(MediaType.APPLICATION_JSON));

        // assert
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.departureTimeInMinutes", is(1074)))
                .andExpect(jsonPath("$.flightDurationInMinutes", is(745)));

        verify(flightFacade).createAndSaveFlight(createFlightDTO);
    }

    @Test
    void addFlight_invalidCreateFlightDTO_validationErrors() throws Exception {
        // arrange
        CreateFlightDTO createFlightDTO = CreateFlightDTOBuilderObjectMother.getTestingBuilderWithoutMandatoryFields()
                .build();

        // act
        ResultActions response = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/flights/add-flight")
                .content(mapper.writeValueAsString(createFlightDTO))
                .contentType(MediaType.APPLICATION_JSON));

        // assert
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.timestamp", matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+")))
                .andExpect(jsonPath("$.message", is("Validation error")))
                .andExpect(jsonPath("$.subErrors.*", hasSize(4)));

        verify(flightFacade, Mockito.times(0)).createAndSaveFlight(createFlightDTO);
    }
    @Test
    void findAllFlights_listOfFlightDTOs() throws Exception {
        // arrange
        Airport departureAirport = AirportBuilderObjectMother.getTestingBuilder(1L)
                .code("DepartureCode")
                .build();
        Airport destinationAirport = AirportBuilderObjectMother.getTestingBuilder(2L)
                .code("DestinationCode")
                .build();

        Flight flightOne = FlightBuilderObjectMother.getTestingBuilder(1L)
                .departureAirport(departureAirport)
                .destinationAirport(destinationAirport)
                .build();
        Flight flightTwo = FlightBuilderObjectMother.getTestingBuilder(2L)
                .departureAirport(departureAirport)
                .destinationAirport(destinationAirport)
                .build();

        FlightDTO flightDTOOne = new FlightDTO(flightOne);
        FlightDTO flightDTOTwo = new FlightDTO(flightTwo);
        List<FlightDTO> flightDTOS = List.of(flightDTOOne, flightDTOTwo);

        when(flightFacade.findAll()).thenReturn(flightDTOS);

        // act
        ResultActions response = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/flights"));

        // assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.*.id", hasItems(equalTo(1), equalTo(2))))
                .andExpect(jsonPath("$.*.departureTimeInMinutes", hasItems(equalTo(1074), equalTo(1074))))
                .andExpect(jsonPath("$.*.flightDurationInMinutes", hasItems(equalTo(745), equalTo(745))));

        verify(flightFacade).findAll();
    }

    @Test
    void findAllFlightsFromAnAirportWithSpecificCode_airportCode_listOfFlightDTOs() throws Exception {
        // arrange
        String airportCode = "CAN";
        Airport airport = AirportBuilderObjectMother.getTestingBuilder(1L)
                .code(airportCode)
                .build();
        Flight flightOne = FlightBuilderObjectMother.getTestingBuilder(1L)
                .departureAirport(airport)
                .build();
        Flight flightTwo = FlightBuilderObjectMother.getTestingBuilder(2L)
                .departureAirport(airport)
                .build();

        FlightDTO flightDTOOne = new FlightDTO(flightOne);
        FlightDTO flightDTOTwo = new FlightDTO(flightTwo);
        List<FlightDTO> flightDTOS = List.of(flightDTOOne, flightDTOTwo);

        when(flightFacade.findAllFlightsFromAnAirportWithSpecificCodeAndMapToFlightDTOs(airportCode)).thenReturn(flightDTOS);

        // act
        ResultActions response = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/flights/all").queryParam("airportCode", airportCode));

        // assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.*.id", hasItems(equalTo(1), equalTo(2))))
                .andExpect(jsonPath("$.*.departureTimeInMinutes", hasItems(equalTo(1074), equalTo(1074))))
                .andExpect(jsonPath("$.*.flightDurationInMinutes", hasItems(equalTo(745), equalTo(745))));

        verify(flightFacade).findAllFlightsFromAnAirportWithSpecificCodeAndMapToFlightDTOs(airportCode);
    }
}
