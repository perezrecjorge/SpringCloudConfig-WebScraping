package com.tfg.webscraping.model.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Anuncio {
    @Id
    @GeneratedValue
    private int id;
    private String marcaCar;
    private String modeloCar;
    private String titleCar;
    private String priceCar;
    private String anoCar;
    private String cvsCar;
    private String kmsCar;
    private String enlaceCar;
}