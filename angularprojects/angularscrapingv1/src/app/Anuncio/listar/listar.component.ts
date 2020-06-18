import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ServiceService } from '../../Service/service.service';
import { Anuncio } from 'src/app/Modelo/Anuncio';

@Component({
  selector: 'app-listar',
  templateUrl: './listar.component.html',
  styleUrls: ['./listar.component.css']
})
export class ListarComponent implements OnInit {

  currentLessonListar: String = "0";
  anuncios:Anuncio[];
  constructor(private service:ServiceService, private router:Router) { }

  ngOnInit(){

    this.currentLessonListar = "0";

    this.service.getAnuncios()
    .subscribe(data=>{
      this.anuncios=data;
    })

    this.currentLessonListar = "1";

  }



}
