package com.example.airportapp.facade;

import com.example.airportapp.domain.Airport;
import com.example.airportapp.domain.Flight;
import com.example.airportapp.dto.FlightData;
import com.example.airportapp.dto.request.CreateFlightDTO;
import com.example.airportapp.dto.response.FlightDTO;
import com.example.airportapp.mapper.FlightMapper;
import com.example.airportapp.service.AirportService;
import com.example.airportapp.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FlightFacade {

    private final FlightService flightService;
    private final AirportService airportService;
    private final FlightMapper flightMapper;

    public FlightDTO createAndSaveFlight(CreateFlightDTO createFlightDTO) {
        Airport departureAirport = airportService.findAirportByCode(createFlightDTO.getDepartureAirportCode());
        Airport destinationAirport = airportService.findAirportByCode(createFlightDTO.getDestinationAirportCode());

        Flight flight = flightMapper.mapCreateFlightDTOToFlightEntity(createFlightDTO, departureAirport, destinationAirport);
        Flight savedFlight = flightService.saveFlight(flight);

        return new FlightDTO(savedFlight);
    }

    public List<FlightDTO> findAll() {
        List<Flight> flights = flightService.findAll();
        return flightMapper.mapFlightsToFlightDTOs(flights);
    }

    public List<Flight> saveFlightsFromFlightData(List<FlightData> flightData) {
        Map<String, Airport> airportCodeToAirport = airportService.findAllAndGroupByCodes();
        List<Flight> flights = flightMapper.mapFlightDataToFlights(flightData, airportCodeToAirport);

        return flightService.saveFlights(flights);
    }

    public List<FlightDTO> findAllFlightsFromAnAirportWithSpecificCodeAndMapToFlightDTOs(String airportCode) {
        List<Flight> flights = flightService.findAllFlightsFromAnAirportWithSpecificCode(airportCode);

        return flightMapper.mapFlightsToFlightDTOs(flights);
    }

    public List<FlightDTO> findAllDirectFlightsForSpecificAirportCodesAndMapToFlightDTOs(String departureCode, String destinationCode) {
        List<Flight> flights = flightService.findAllDirectFlightsForSpecificAirportCodes(departureCode, destinationCode);

        return flightMapper.mapFlightsToFlightDTOs(flights);
    }
}
