import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-aluno',
  templateUrl: './aluno.component.html',
  styleUrls: ['./aluno.component.scss']
})
export class AlunoComponent implements OnInit {

  @Input('aluno') aluno;

  nivelEscolar = [
    {id:1, label:'1º'},{id:2, label:'2º'},{id:3, label:'3º'},
    {id:4, label:'4º'},{id:5, label:'5º'},{id:6, label:'6º'},
    {id:7, label:'7º'},{id:8, label:'8º'},{id:9, label:'9º'}
  ];

  modalidadeEscolar = [
    {id:1, label:'Infantil'},
    {id:2, label:'Fundamental'},
    {id:3, label:'Médio'}
  ];

  horarioEscolar = [
    {id: 1, label:'Matutino'},
    {id: 2, label:'Vespertino'},
    {id: 3, label:'Noturno'},
    {id: 4, label:'Diurno'}
  ];

  horarioInstituicao = [
    {id: 1, label:'Matutino'},
    {id: 2, label:'Vespertino'},
    {id: 3, label:'Noturno'},
    {id: 4, label:'Diurno'}
  ];


  constructor() { }

  ngOnInit() {
  }

}
