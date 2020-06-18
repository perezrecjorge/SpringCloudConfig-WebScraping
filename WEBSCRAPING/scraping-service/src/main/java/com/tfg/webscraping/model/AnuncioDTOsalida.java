package com.tfg.webscraping.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AnuncioDTOsalida {

    @NotBlank
    @JsonProperty("title")
    private String titleCar;
    @NotBlank
    @JsonProperty("price")
    private String priceCar;
    @NotBlank
    @JsonProperty("ano")
    private String anoCar;
    @NotBlank
    @JsonProperty("cvs")
    private String cvsCar;
    @NotBlank
    @JsonProperty("kms")
    private String kmsCar;
    @NotBlank
    @JsonProperty("enlace")
    private String enlaceCar;
}
