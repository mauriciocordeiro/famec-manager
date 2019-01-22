import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material';
import { UsuarioService } from '../../services/usuario.services';
import { Usuario } from 'src/app/services/usuario';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.scss']
})
export class UsuarioComponent implements OnInit {

  loading:boolean = true;
  loadingMessage:string = "";

  usuarioForm:FormGroup = new FormGroup({
    cdUsuario: new FormControl(0),
    nmUsuario: new FormControl(''),
    stUsuario: new FormControl(1),
    nmLogin: new FormControl(''),
    nmSenha: new FormControl(''),
    nmEmail: new FormControl(''),
    nmFuncao: new FormControl('')
  });

  constructor(
    private usuarioService:UsuarioService, 
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    window.dispatchEvent(new Event('resize'));

    console.log("usuario");
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
    });
  }

  onSubmit() {
    if(!this.usuarioForm.valid) {
      this.openSnackBar("Existem campos inválidos.", null);
      return;
    }

    this.loading = true;
    this.usuarioService.saveUsuario(this.usuarioForm.value as Usuario)
      .subscribe(usuario => {
        this.usuarioForm.setValue(usuario);
        this.openSnackBar("Usuário registrado com sucesso", null);
      });
    
    this.loading = false;

  }

}
