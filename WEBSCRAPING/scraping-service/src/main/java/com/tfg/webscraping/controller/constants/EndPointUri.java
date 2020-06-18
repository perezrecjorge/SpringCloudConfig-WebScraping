package com.tfg.webscraping.controller.constants;

public interface EndPointUri {
    interface Api {
        String BASE_PATH = "/v1";

        interface Anuncios {
            String ANUNCIO = BASE_PATH + "/anuncios";
        }
    }
}
