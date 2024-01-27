package com.example.airportapp.service;

import com.example.airportapp.domain.Flight;

import java.util.List;

public interface FlightService {

    List<Flight> saveFlights(List<Flight> flights);

    Flight saveFlight(Flight flight);

    List<Flight> findAll();

    List<Flight> findAllFlightsFromAnAirportWithSpecificCode(String airportCode);

    List<Flight> findAllDirectFlightsForSpecificAirportCodes(String departureCode, String destinationCode);
}
