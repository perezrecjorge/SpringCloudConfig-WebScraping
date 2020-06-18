import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ServiceService } from '../../src/app/Service/service.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'angularscrapingv1';

  currentLesson: String = "0";
  private isButtonVisible = true;
  constructor(private router:Router, private service:ServiceService){}

  miFormulario = new FormGroup({
    marcaCoche: new FormControl('', Validators.required),
    modeloCoche: new FormControl('', Validators.required)
  });

  Listar(){
    this.service.setMarcaCoche(this.miFormulario.value.marcaCoche, this.miFormulario.value.modeloCoche);
    this.miFormulario.controls['marcaCoche'].disable();
    this.miFormulario.controls['modeloCoche'].disable();
    this.router.navigate(["listar"]);
  }

}
