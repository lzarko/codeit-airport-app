package com.example.airportapp.controller;

import com.example.airportapp.domain.Airport;
import com.example.airportapp.service.AirportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
@Slf4j
public class AirportController {

    private final AirportService airportService;

    @GetMapping
    public ResponseEntity<List<Airport>> findAllAirports() {
        List<Airport> airports = airportService.findAll();
        return new ResponseEntity<>(airports, HttpStatus.OK);
    }

    @GetMapping("/airport")
    public ResponseEntity<Airport> findAirportByCode(@RequestParam String code) {
        Airport airport = airportService.findAirportByCode(code);
        return new ResponseEntity<>(airport, HttpStatus.OK);
    }

    @PostMapping("/add-airport")
    public ResponseEntity<Airport> addAirport(@RequestBody Airport airport) {
        Airport addedAirport = airportService.saveAirport(airport);
        return new ResponseEntity<>(addedAirport, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        airportService.deleteAirport(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/airport/most-passengers")
    public ResponseEntity<Airport> getAirportWithMostAnnualPassengers(@RequestParam String country) {
        Airport airport = airportService.findAirportByCountryWithMostPassengers(country);
        return new ResponseEntity<>(airport, HttpStatus.OK);
    }
}
