package com.example.airportapp.service.impl;

import com.example.airportapp.domain.Flight;
import com.example.airportapp.exception.EntityNotFoundException;
import com.example.airportapp.repository.FlightRepository;
import com.example.airportapp.service.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    public List<Flight> saveFlights(List<Flight> flights) {
        return flightRepository.saveAll(flights);
    }

    @Override
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Override
    public List<Flight> findAllFlightsFromAnAirportWithSpecificCode(String airportCode) {
        List<Flight> flights = flightRepository.findAndOrderAllFlightsFromAnAirportWithSpecificCode(airportCode);
        if (flights.isEmpty()){
            throw new EntityNotFoundException(Flight.class, airportCode);
        }
        return flights;
    }

    @Override
    public List<Flight> findAllDirectFlightsForSpecificAirportCodes(String departureCode, String destinationCode) {
        List<Flight> flights = flightRepository.findAllDirectFlightsForSpecificAirportCodes(departureCode, destinationCode);
        if (flights.isEmpty()){
            throw new EntityNotFoundException("You have inserted invalid arguments for either departureCode or destinationCode");
        }
        return flights;
    }

    @Override
    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }
}
