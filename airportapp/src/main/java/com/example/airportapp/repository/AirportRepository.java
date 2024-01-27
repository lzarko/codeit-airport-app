package com.example.airportapp.repository;

import com.example.airportapp.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    @Query(value = "SELECT a.* FROM Airport a " +
            "WHERE a.country = :country " +
            "ORDER BY a.num_of_passengers DESC " +
            "LIMIT 1", nativeQuery = true)
    Airport findAirportByCountryWithMostPassengers(String country);


    @Query("SELECT ap from Airport ap " +
            "WHERE ap.code = :code")
    Airport findAirportByCode(String code);
}
