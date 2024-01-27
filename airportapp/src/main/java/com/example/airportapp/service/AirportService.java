package com.example.airportapp.service;

import com.example.airportapp.domain.Airport;

import java.util.List;
import java.util.Map;

public interface AirportService {

    List<Airport> saveAirports(List<Airport> airports);

    Airport saveAirport(Airport airport);

    void deleteAirport(Long airportId);

    Map<String, Airport> findAllAndGroupByCodes();

    List<Airport> findAll();

    Airport findAirportByCountryWithMostPassengers(String country);

    Airport findAirportByCode(String code);
}
