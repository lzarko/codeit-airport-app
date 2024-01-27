package com.example.airportapp.controller;

import com.example.airportapp.dto.request.CreateFlightDTO;
import com.example.airportapp.dto.response.FlightDTO;
import com.example.airportapp.facade.FlightFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
@Slf4j
public class FlightController {

    private final FlightFacade flightFacade;

    @PostMapping("/add-flight")
    public ResponseEntity<FlightDTO> addFlight(@RequestBody @Valid CreateFlightDTO createFlightDTO) {
        log.info("Got request to create a new flight");

        FlightDTO addedFlight = flightFacade.createAndSaveFlight(createFlightDTO);

        log.info("Flight with the following id has been created: {}", addedFlight.getId());

        return new ResponseEntity<>(addedFlight, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FlightDTO>> findAllFlights() {
        log.info("Got request to obtain all flights available");

        List<FlightDTO> flights = flightFacade.findAll();

        log.info("Successfully retrieved all flights");

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FlightDTO>> findAllFlightsFromAnAirportWithSpecificCode(@RequestParam String airportCode) {
        log.info("Got request to obtain all flights available with the following airportCode: {}", airportCode);

        List<FlightDTO> flights = flightFacade.findAllFlightsFromAnAirportWithSpecificCodeAndMapToFlightDTOs(airportCode);

        log.info("Successfully retrieved all flights");

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/direct")
    public ResponseEntity<List<FlightDTO>> findAllDirectFlightsForSpecificAirportCodes(@RequestParam String departureCode,
                                                                                       @RequestParam String destinationCode) {
        log.info("Got request to obtain all direct flights available with the following departureCode: {} and destinationCode: {}",
                departureCode, destinationCode);

        List<FlightDTO> flights = flightFacade.findAllDirectFlightsForSpecificAirportCodesAndMapToFlightDTOs(departureCode, destinationCode);

        log.info("Successfully retrieved all direct flights");

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
}
