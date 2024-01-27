package com.example.airportapp.util;

import com.example.airportapp.dto.FlightData;
import com.example.airportapp.domain.Airport;
import com.example.airportapp.exception.InvalidFileDataException;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CsvReader {

    public static List<Airport> readAirportsFromCSV(String filePath) {
        List<Airport> airports = new ArrayList<>();
        try (Reader reader = new FileReader(filePath);
             CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                Airport airport = new Airport();
                airport.setName(nextRecord[0]);
                airport.setCountry(nextRecord[1]);
                airport.setCode(nextRecord[2]);
                airport.setNumOfPassengers(Long.parseLong(nextRecord[3]));
                airports.add(airport);
            }
        } catch (IOException | CsvException exception) {
            log.error("Error occurred while trying to read the file due to the following exception", exception);
            throw new InvalidFileDataException();
        }
        return airports;
    }

    public static List<FlightData> readFlightDataFromCSV(String filePath) {
        List<FlightData> flightDataList = new ArrayList<>();
        try (Reader reader = new FileReader(filePath);
             CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                FlightData flightData = FlightData.builder()
                        .departureAirportCode(nextRecord[0])
                        .destinationAirportCode(nextRecord[1])
                        .departureTimeInMinutes(Long.parseLong(nextRecord[2]))
                        .flightDurationInMinutes(Long.parseLong(nextRecord[3])).build();
                flightDataList.add(flightData);
            }
        } catch (IOException | CsvException exception) {
            log.error("Error occurred while trying to read the file due to the following exception", exception);
            throw new InvalidFileDataException();
        }
        return flightDataList;
    }
}
