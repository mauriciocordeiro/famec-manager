import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-contato',
  templateUrl: './contato.component.html',
  styleUrls: ['./contato.component.scss']
})
export class ContatoComponent implements OnInit {

  contatos = [
    {nome:"Maurício Cordeiro", telefone:"+55 77 99106-1142", email:"mauriciocordeiro@live.com"},
  ];

  constructor() { }

  ngOnInit() {
    // console.log("contato");
  }


}
