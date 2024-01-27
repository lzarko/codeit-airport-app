package com.example.airportapp.config;

import com.example.airportapp.domain.Airport;
import com.example.airportapp.dto.FlightData;
import com.example.airportapp.facade.FlightFacade;
import com.example.airportapp.service.AirportService;
import com.example.airportapp.util.CsvReader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CsvDataLoader {

    private final AirportService airportService;

    private final FlightFacade flightFacade;

    @Value("${storageFile.airports}")
    private String airportsFileLocation;

    @Value("${storageFile.flights}")
    private String flightsFileLocation;

    @PostConstruct
    public void loadCsvData() {
        if (airportService.findAll().isEmpty()) {
            List<Airport> airports = CsvReader.readAirportsFromCSV(airportsFileLocation);
            log.info("The CSV file has been successfully imported");
            airportService.saveAirports(airports);
        }

        if (flightFacade.findAll().isEmpty()) {
            List<FlightData> flights = CsvReader.readFlightDataFromCSV(flightsFileLocation);
            log.info("The CSV file has been successfully imported");
            flightFacade.saveFlightsFromFlightData(flights);
        }
    }
}
