import { Component, OnInit } from '@angular/core';
import { Utils } from 'src/app/services/Utils';
import { Router } from '@angular/router';
import { UsuarioService } from 'src/app/services/usuario.services';

@Component({
  selector: 'app-contato',
  templateUrl: './contato.component.html',
  styleUrls: ['./contato.component.scss']
})
export class ContatoComponent implements OnInit {
  /**
   * Application support info
   */

  contatos = [
    { nome: "FAMEC", telefone: "+55 77 3424-2301", email: "famec.vc@outlook.com" },
    { nome: "Maurício Cordeiro", telefone: "+55 77 99106-1142", email: "mauriciocordeiro@live.com" },
    { nome: "Natália Amorim", telefone: "+55 77 99175-4969", email: "natalia.amorim2@hotmail.com" }
  ];

  constructor(private router:Router) { }

  ngOnInit() {
    UsuarioService.checkAuth(this.router);
  }


}
