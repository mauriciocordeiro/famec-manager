import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
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
  /**
   * Application users form
   */

  @ViewChild('searchField') searchField: ElementRef;
  @ViewChild('nmSenha2') nmSenha2: ElementRef;

  // snackbar message type
  ALERT: string = 'alert';
  ERROR: string = 'error';
  SUCCESS: string = 'success';

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
    private usuarioService: UsuarioService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
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
  openSnackBar(message: string, action: string, type: string = this.SUCCESS) {
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
      this.openSnackBar("Existem campos inválidos.", null, this.ALERT);
      return;
    }

    if(this.nmSenha2.nativeElement.value !== this.usuarioForm.value.nmSenha) {
      this.openSnackBar("As senhas não coincidem", null, this.ALERT);

      this.usuarioForm.value.nmSenha = '';
      this.nmSenha2.nativeElement.value = '';

      return;
    }

    // cast Slide Toggle to Integer
    this.usuarioForm.value.stUsuario = this.usuarioForm.value.stUsuario ? 1 : 0;

    // save request
    this.usuarioService.saveUsuario(this.usuarioForm.value as Usuario)
      .subscribe(result => {
        if (result.code <= 0) { // save failed 
          this.openSnackBar(result.message, null, this.ERROR);
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
      this.openSnackBar('Nenhum usuário carregado.', null, this.ALERT);
      return;
    }

    //TODO: confirmation dialog

    this.usuarioService.deleteUsuario(this.usuarioForm.value.cdUsuario)
      .subscribe(result => {
        if (result.code <= 0) { // delete failed 
          this.openSnackBar(result.message, null, this.ERROR);
          return;
        }

        this.openSnackBar(result.message, null);
        this.clearForm();
      });
  }
}
