import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material';
import { UsuarioService } from '../../services/usuario.services';
import { Usuario } from 'src/app/services/usuario';
import { Router } from '@angular/router';
import { Utils } from 'src/app/services/Utils';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.scss']
})
export class UsuarioComponent implements OnInit {
  /**
   * Application users form
   */

  @ViewChild('searchField') searchField: ElementRef;
  @ViewChild('nmSenha2') nmSenha2: ElementRef;

  // loading flags
  loading: boolean = false;
  loadingMessage: string = "";

  // form group
  usuarioForm: FormGroup = new FormGroup({
    cdUsuario: new FormControl(0),
    nmUsuario: new FormControl(''),
    stUsuario: new FormControl(1),
    nmLogin: new FormControl(''),
    nmSenha: new FormControl(''),
    nmEmail: new FormControl(''),
    nmFuncao: new FormControl('')
  });

  // users list (filled by search result)
  usuarios: Usuario[];

  constructor(
    private router:Router,
    private usuarioService: UsuarioService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    UsuarioService.checkAuth(this.router);
    window.dispatchEvent(new Event('resize'));
  }

  newFormGroup():FormGroup {
    return new FormGroup({
      cdUsuario: new FormControl(0),
      nmUsuario: new FormControl(''),
      stUsuario: new FormControl(1),
      nmLogin: new FormControl(''),
      nmSenha: new FormControl(''),
      nmEmail: new FormControl(''),
      nmFuncao: new FormControl('')
    });
  }

  // show messages
  openSnackBar(message: string, action: string, type: string = Utils.SNACK_SUCCESS) {
    this.snackBar.open(message, action, {
      duration: 3000,
      panelClass: [type + '-snackbar']
    });
  }

  clearForm() {
    this.usuarioForm = this.newFormGroup();
    this.usuarios = [];
    this.searchField.nativeElement.value = "";
  }

  // form submit
  onSubmit() {
    //form validation
    if (!this.usuarioForm.valid || !this.nmSenha2.nativeElement.value) {
      this.openSnackBar("Existem campos inválidos.", null, Utils.SNACK_ALERT);
      return;
    }

    if(this.nmSenha2.nativeElement.value !== this.usuarioForm.value.nmSenha) {
      this.openSnackBar("As senhas não coincidem", null, Utils.SNACK_ALERT);

      this.usuarioForm.value.nmSenha = '';
      this.nmSenha2.nativeElement.value = '';

      return;
    }

    // cast SlideToggle to Integer
    this.usuarioForm.value.stUsuario = this.usuarioForm.value.stUsuario ? 1 : 0;

    // save request
    this.usuarioService.saveUsuario(this.usuarioForm.value as Usuario)
      .subscribe(result => {
        if (result.code <= 0) { // save failed 
          this.openSnackBar(result.message, null, Utils.SNACK_ERROR);
          return;
        }

        var usuario: Usuario = result.objects.USUARIO as Usuario;
        this.usuarioForm.setValue(usuario);
        this.openSnackBar(result.message, null);
      });
  }

  // search
  onSearch(term: string): void {
    if (!term.trim())
			return;

		if (term.length < 3)
      return;
      
    this.usuarioService.getUsuarios(term)
      .subscribe(list => {
        this.usuarios = list;
      });
  }

  // fill formControl with a selected item (from search)
  onSelectItem(arg: Usuario): void {
    if (!arg)
      return;

    this.usuarioForm.setValue(arg);
    this.usuarios = [];
    this.searchField.nativeElement.value = "";
  }

  // delete Usuario on FormGroup
  onDelete():void {
    if(!this.usuarioForm.value.cdUsuario || this.usuarioForm.value.cdUsuario==0) {
      this.openSnackBar('Nenhum usuário carregado.', null, Utils.SNACK_ALERT);
      return;
    }

    //TODO: confirmation dialog

    this.usuarioService.deleteUsuario(this.usuarioForm.value.cdUsuario)
      .subscribe(result => {
        if (result.code <= 0) { // delete failed 
          this.openSnackBar(result.message, null, Utils.SNACK_ERROR);
          return;
        }

        this.openSnackBar(result.message, null);
        this.clearForm();
      });
  }
}
