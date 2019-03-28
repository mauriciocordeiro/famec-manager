import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { UsuarioService } from 'src/app/services/usuario.services';
import { MatSnackBar } from '@angular/material';
import { Usuario } from 'src/app/services/usuario';
import { LocalStorage } from 'src/app/services/LocalStorage';
import { Utils } from 'src/app/services/Utils';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  // snackbar message type
  ALERT: string = 'alert';
  ERROR: string = 'error';
  SUCCESS: string = 'success';

  @ViewChild('nmLogin') nmLogin: ElementRef;
  @ViewChild('nmSenha') nmSenha: ElementRef;

  loading: boolean = false;

  loginResult: any = { success: true, message: '' };

  constructor(
    private _router: Router,
    private usuarioService: UsuarioService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    
  }

  // show messages
  openSnackBar(message: string, action: string, type: string = this.SUCCESS) {
    this.snackBar.open(message, action, {
      duration: 3000,
      panelClass: [type + '-snackbar']
    });
  }

  async onAutenticar() {
    this.loading = true;
    
    this.usuarioService.doLogin(this.nmLogin.nativeElement.value, this.nmSenha.nativeElement.value)
    .subscribe(result => {
      if (result.code <= 0) { // auth failed 
        this.openSnackBar(result.message, null, this.ERROR);
        return;
      } else {
        var usuario: Usuario = result.objects.USUARIO as Usuario;
        this.openSnackBar(result.message, null);
        LocalStorage.put('famec.usuario', Utils.encrypt(JSON.stringify(usuario)));
        this._router.navigate(["/"]);
      }      
    });

    this.loading = false;
  }

}
