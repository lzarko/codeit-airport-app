package com.example.airportapp.mapper;


import com.example.airportapp.domain.Airport;
import com.example.airportapp.domain.Flight;
import com.example.airportapp.dto.FlightData;
import com.example.airportapp.dto.request.CreateFlightDTO;
import com.example.airportapp.dto.response.FlightDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class FlightMapper {

    public Flight mapCreateFlightDTOToFlightEntity(CreateFlightDTO createFlightDTO, Airport departureAirport, Airport destinationAirport) {
        return Flight.builder()
                .departureAirport(departureAirport)
                .destinationAirport(destinationAirport)
                .departureTimeInMinutes(createFlightDTO.getDepartureTimeInMinutes())
                .flightDurationInMinutes(createFlightDTO.getFlightDurationInMinutes())
                .build();
    }

    public List<Flight> mapFlightDataToFlights(List<FlightData> flightData, Map<String, Airport> airportCodeToAirport) {
        log.info("Trying to map the following flight data: {} to entity", flightData);

        List<Flight> flights = flightData.stream()
                .map(fData -> mapFlightDataToEntity(fData, airportCodeToAirport))
                .toList();

        log.info("The flight data has been successfully mapped to entity");

        return flights;
    }

    private Flight mapFlightDataToEntity(FlightData flightData, Map<String, Airport> airportCodeToAirport){
        String departureAirportCode = flightData.getDepartureAirportCode();
        String destinationAirportCode = flightData.getDestinationAirportCode();
        Airport departureAirport = airportCodeToAirport.getOrDefault(departureAirportCode, null);
        Airport destinationAirport = airportCodeToAirport.getOrDefault(destinationAirportCode, null);

        return Flight.builder()
                .departureTimeInMinutes(flightData.getDepartureTimeInMinutes())
                .flightDurationInMinutes(flightData.getFlightDurationInMinutes())
                .departureAirport(departureAirport)
                .destinationAirport(destinationAirport)
                .build();
    }

    public List<FlightDTO> mapFlightsToFlightDTOs(List<Flight> flights) {
        log.info("Trying to map all flights to flight DTOs");

        List<FlightDTO> flightDTOS = flights.stream()
                .map(FlightDTO::new)
                .toList();

        log.info("Successfully mapped all flights to flight DTOs");

        return flightDTOS;
    }
}
