package com.example.airportapp.objectmother.domain;

import com.example.airportapp.domain.Airport;

public class AirportBuilderObjectMother {
    public static Airport.AirportBuilder getTestingBuilder(Long id){
        return Airport.builder()
                .id(id)
                .name("Angola International Airport")
                .code("VMA")
                .country("Angola")
                .numOfPassengers(1750000L);
    }
}
