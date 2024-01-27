package com.example.airportapp.repository;

import com.example.airportapp.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f " +
            "JOIN FETCH f.departureAirport depAirport " +
            "JOIN FETCH f.destinationAirport desAirport " +
            "WHERE depAirport.code = :airportCode " +
            "ORDER BY depAirport.name, desAirport.code, f.departureTimeInMinutes")
    List<Flight> findAndOrderAllFlightsFromAnAirportWithSpecificCode(String airportCode);

    @Query("SELECT f FROM Flight f " +
            "JOIN FETCH f.departureAirport " +
            "JOIN FETCH f.destinationAirport " +
            "WHERE f.departureAirport.code = :departureCode " +
            "AND f.destinationAirport.code = :destinationCode")
    List<Flight> findAllDirectFlightsForSpecificAirportCodes(String departureCode, String destinationCode);

    @Query("SELECT f FROM Flight f " +
            "JOIN FETCH f.departureAirport " +
            "JOIN FETCH f.destinationAirport ")
    List<Flight> findAll();
}
