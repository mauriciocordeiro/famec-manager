import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorage } from './services/LocalStorage';
import { Utils } from './services/Utils';
import { UsuarioService } from './services/usuario.services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'FAMEC Manager';

  constructor(private router:Router) {
    
  }

  ngOnInit() {
    UsuarioService.checkAuth(this.router);
  }
  
}
