import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UsuarioService } from 'src/app/services/usuario.services';
import { SessionStorage } from 'src/app/services/SessionStorage';

@Component({
  selector: 'app-menubar',
  templateUrl: './menubar.component.html',
  styleUrls: ['./menubar.component.scss']
})
export class MenubarComponent implements OnInit {
  /**
   * Application's top bar
   */

  constructor(private router:Router) { }

  ngOnInit() {
    UsuarioService.checkAuth(this.router);
  }

  logout() {
    SessionStorage.remove('famec.usuario');
    UsuarioService.checkAuth(this.router);
  }

}
