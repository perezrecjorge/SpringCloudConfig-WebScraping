package com.tfg.webscraping.service;

import com.tfg.webscraping.model.AnuncioDTOentrada;
import com.tfg.webscraping.model.AnuncioDTOsalida;

import java.util.List;

public interface AnuncioService {

    List<AnuncioDTOsalida> getAllAnuncios(AnuncioDTOentrada datospedidos);

}
