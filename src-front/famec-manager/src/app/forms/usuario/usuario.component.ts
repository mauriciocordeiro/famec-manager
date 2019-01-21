import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.scss']
})
export class UsuarioComponent implements OnInit {

  loading:Boolean = false;
  showForm:boolean = true;

  usuarioForm:FormGroup = new FormGroup({
    nmUsuario: new FormControl(''),
    stUsuario: new FormControl(true),
    nmLogin: new FormControl(''),
    nmSenha: new FormControl(''),
    nmEmail: new FormControl(''),
    nmFuncao: new FormControl('')
  });

  constructor() { }

  ngOnInit() {
    window.dispatchEvent(new Event('resize'));

    console.log("usuario");
  }

  onSubmit() {
    console.log(this.usuarioForm.value);
    if(!this.usuarioForm.valid)
      return;

    
  }

}
