import { Component, OnInit } from '@angular/core';

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

  constructor() { }

  ngOnInit() {
    // console.log("contato");
  }


}
