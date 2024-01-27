package com.example.airportapp.service.impl;

import com.example.airportapp.domain.Airport;
import com.example.airportapp.exception.InvalidFileDataException;
import com.example.airportapp.repository.AirportRepository;
import com.example.airportapp.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    @Override
    public List<Airport> saveAirports(List<Airport> airports) {
        return airportRepository.saveAll(airports);
    }

    @Override
    public Airport saveAirport(Airport airport) {
        return airportRepository.save(airport);
    }

    @Override
    public void deleteAirport(Long airportId) {
        airportRepository.deleteById(airportId);
    }

    @Override
    public Map<String, Airport> findAllAndGroupByCodes() {
        return airportRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Airport::getCode, Function.identity()));
    }

    @Override
    public List<Airport> findAll() {
        return airportRepository.findAll();
    }

    @Override
    public Airport findAirportByCountryWithMostPassengers(String country) {
        return airportRepository.findAirportByCountryWithMostPassengers(country);
    }

    @Override
    public Airport findAirportByCode(String code) {
        return airportRepository.findAirportByCode(code);
    }
}
