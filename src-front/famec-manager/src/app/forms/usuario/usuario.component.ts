import { Component, OnInit, ViewChild } from '@angular/core';
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

  @ViewChild('searchField') searchField:any;

  ALERT: string = 'alert';
  ERROR: string = 'error';
  SUCCESS: string = 'success';

  loading: boolean = false;
  loadingMessage: string = "";

  showSearch: boolean = true;

  usuarioForm: FormGroup = new FormGroup({
    cdUsuario: new FormControl(0),
    nmUsuario: new FormControl(''),
    stUsuario: new FormControl(1),
    nmLogin: new FormControl(''),
    nmSenha: new FormControl(''),
    nmEmail: new FormControl(''),
    nmFuncao: new FormControl('')
  });

  usuarios:Usuario[];

  constructor(
    private usuarioService: UsuarioService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    window.dispatchEvent(new Event('resize'));
  }

  openSnackBar(message: string, action: string, type: string = this.SUCCESS) {
    this.snackBar.open(message, action, {
      duration: 3000,
      panelClass: [type + '-snackbar']
    });
  }

  onSubmit() {
    if (!this.usuarioForm.valid) {
      this.openSnackBar("Existem campos inválidos.", null, this.ALERT);
      return;
    }

    this.usuarioForm.value.stUsuario = this.usuarioForm.value.stUsuario ? 1 : 0;

    this.usuarioService.saveUsuario(this.usuarioForm.value as Usuario)
      .subscribe(result => {
        if (result.code <= 0) {
          this.openSnackBar(result.message, null, this.ERROR);
          return;
        }

        var usuario: Usuario = result.objects.USUARIO as Usuario;
        this.usuarioForm.setValue(usuario);
        this.openSnackBar(result.message, null);
      });
  }

  onSearch(term: string): void {
    this.usuarioService.searchUsuarios(term)
        .subscribe(list => {
          this.usuarios = list;
          
        });
  }

  onSelectItem(arg:Usuario):void {
    if(!arg)
      return;

    this.usuarioForm.setValue(arg);
    this.usuarios = [];
    this.searchField.value = "";
  }
}
