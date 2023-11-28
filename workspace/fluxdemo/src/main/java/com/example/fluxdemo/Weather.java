package com.example.fluxdemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Weather {
    private String temperature;
    private String humidity;
    private String windSpeed;
}