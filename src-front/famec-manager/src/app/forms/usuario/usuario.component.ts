import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';

import { UsuarioService } from '../../services/usuario.services';
import { Usuario } from 'src/app/services/usuario';

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
    stUsuario: new FormControl(1),
    nmLogin: new FormControl(''),
    nmSenha: new FormControl(''),
    nmEmail: new FormControl(''),
    nmFuncao: new FormControl('')
  });

  constructor(private usuarioService:UsuarioService) { }

  ngOnInit() {
    window.dispatchEvent(new Event('resize'));

    console.log("usuario");
  }

  onSubmit() {
    this.loading = true;
    console.log(this.usuarioForm.value);
    if(!this.usuarioForm.valid) {
      this.loading = false;
      console.log("Is form valid? "+this.usuarioForm.valid)
      return;
    }

    var usuario:Usuario = this.usuarioForm.value as Usuario;
    usuario.cdUsuario = (this.usuarioForm.value.cdUsuario ? this.usuarioForm.value.cdUsuario : 0);
    
    this.usuarioService.addUsuario(usuario)
      .subscribe(usuario => {
        console.log(usuario);
      });
    
    this.loading = false;

  }

}
