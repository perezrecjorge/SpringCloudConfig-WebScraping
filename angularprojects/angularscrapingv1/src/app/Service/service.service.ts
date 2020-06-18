import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Anuncio } from '../Modelo/Anuncio';

@Injectable({
  providedIn: 'root',
})
export class ServiceService {

  marcaCoche: String;
  modeloCoche: String;

  constructor(private http:HttpClient) { }

  Url='http://localhost:9090/api/scraping-service/v1/anuncios/findallcar2';

  setMarcaCoche(marca: String, modelo: String) {
    this.marcaCoche = marca;
    this.modeloCoche = modelo;
  }

  getAnuncios(){
    return this.http.post<Anuncio[]>(this.Url, {
      "marca_car": this.marcaCoche,
      "modelo_car": this.modeloCoche
  });
  }

  
  
}
