package com.tfg.webscraping.controller;

import com.tfg.webscraping.model.AnuncioDTOentrada;
import com.tfg.webscraping.model.AnuncioDTOsalida;
import com.tfg.webscraping.service.AnuncioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class AnuncioController implements AnuncioApi{

    @Autowired
    private AnuncioService anuncioService;

    @Override
    @PostMapping("/findallcar")
    public ResponseEntity<List<AnuncioDTOsalida>> findAllCar(@RequestBody AnuncioDTOentrada datosDTOentrada) {

        List<AnuncioDTOsalida> listaAnuncios = anuncioService.getAllAnuncios(datosDTOentrada);

        if (listaAnuncios.size() == 0 || listaAnuncios == null) {
            System.out.println("entra aqui");
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(HttpStatus.FOUND).body(listaAnuncios);
        }


    }@PostMapping("/findallcar2")
    public List<AnuncioDTOsalida> findAllCar2(@RequestBody AnuncioDTOentrada datosDTOentrada) {

        List<AnuncioDTOsalida> listaAnuncios = anuncioService.getAllAnuncios(datosDTOentrada);

        return listaAnuncios;
    }

}
