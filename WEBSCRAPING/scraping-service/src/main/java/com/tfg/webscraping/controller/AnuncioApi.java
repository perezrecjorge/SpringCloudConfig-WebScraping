package com.tfg.webscraping.controller;

import com.tfg.webscraping.controller.constants.EndPointUri;
import com.tfg.webscraping.model.AnuncioDTOentrada;
import com.tfg.webscraping.model.AnuncioDTOsalida;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(EndPointUri.Api.Anuncios.ANUNCIO)
public interface AnuncioApi {

    @PostMapping
    ResponseEntity<List<AnuncioDTOsalida>> findAllCar(AnuncioDTOentrada carDTO);

    @PostMapping
    List<AnuncioDTOsalida> findAllCar2(AnuncioDTOentrada carDTO);

}
