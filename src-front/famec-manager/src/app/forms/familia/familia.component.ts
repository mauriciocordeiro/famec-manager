import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-familia',
  templateUrl: './familia.component.html',
  styleUrls: ['./familia.component.scss']
})
export class FamiliaComponent implements OnInit {

  step = -1;
  
  lgHabitacaoAluguel:boolean = false;
  lgResponsavelEstudando:boolean = false;

  estadoCivil = [
    {id:1, label:'Solteiro'},
    {id:1, label:'Casado'},
    {id:1, label:'Separado'},
    {id:1, label:'Divorciado'},
    {id:1, label:'Viúvo'}
  ];

  nivelEscolaridade = [
    {id:1, label:'Fundamental'},
    {id:2, label:'Médio'},
    {id:3, label:'Superior'},
    {id:4, label:'Outro'}
  ];

  horarioEscolar = [
    {id: 1, label:'Matutino'},
    {id: 2, label:'Vespertino'},
    {id: 3, label:'Noturno'},
    {id: 4, label:'Diurno'}
  ];

  situacaoHabitacional = [
    {id: 1, label:'Própria'},
    {id: 2, label:'Alugada'},
    {id: 3, label:'Cedida'},
    {id: 4, label:'Invasão'},
    {id: 5, label:'De favor'}
  ];

  abastecimentoAgua = [
    {id: 1, label:'Rede pública'}
  ];

  tratamentoAgua = [
    {id: 1, label:'Filtração'},
    {id: 2, label:'Fervura'},
    {id: 3, label:'S/ Tratamento'},
    {id: 4, label:'Outro'}
  ];

  iluminacao = [
    {id: 1, label:'Medidor próprio'},
    {id: 2, label:'S/ Medidor'},
    {id: 3, label:'Outros'}
  ];

  escoamentoSanitario = [
    {id: 1, label:'Esgoto'},
    {id: 2, label:'Fossa'},
    {id: 3, label:'Céu aberto'},
    {id: 4, label:'Outros'}
  ];

  destinoLixo = [
    {id: 1, label:'Coletado'},
    {id: 2, label:'Queimado'},
    {id: 3, label:'Enterrado'},
    {id: 4, label:'Céu aberto'},
    {id: 5, label:'Outros'}
  ];
  

  alunos:any = [
    {id:1, nmAluno:'Alfa Centauro'}
  ];

  constructor() { }

  ngOnInit() {
  }

  onChangeEstudando(event) {
    this.lgResponsavelEstudando = event.checked;
  }

  onChangeSituacaoHabitacional(event) {
    this.lgHabitacaoAluguel = event.value === 2; //Alugada
  }

  onClickAddAluno() {
    this.alunos.push({id:2, nmAluno:'Sirius'});
  }

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }

}
