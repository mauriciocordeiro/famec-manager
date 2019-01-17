import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.scss']
})
export class UsuarioComponent implements OnInit {

  showForm:boolean = true;

  constructor() { }

  ngOnInit() {
    window.dispatchEvent(new Event('resize'));

    console.log("usuario");
  }

}
