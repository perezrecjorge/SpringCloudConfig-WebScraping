package com.tfg.webscraping.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AnuncioDTOentrada {
    @NotBlank
    @JsonProperty("marca_car")
    private String marcaCar;
    @NotBlank
    @JsonProperty("modelo_car")
    private String modeloCar;
}
