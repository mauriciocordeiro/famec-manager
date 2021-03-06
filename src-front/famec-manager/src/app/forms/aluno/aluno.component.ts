import { Component, OnInit, Input, ViewChild, ElementRef } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Utils } from 'src/app/services/Utils';
import { UsuarioService } from 'src/app/services/usuario.services';

@Component({
  selector: 'app-aluno',
  templateUrl: './aluno.component.html',
  styleUrls: ['./aluno.component.scss']
})
export class AlunoComponent implements OnInit {

  @Input('formAluno') formAluno:FormGroup;  
  @ViewChild('nrIdade') nrIdade: ElementRef;

  nivelEscolar = [
    { id: 1, label: '1º' }, { id: 2, label: '2º' }, { id: 3, label: '3º' },
    { id: 4, label: '4º' }, { id: 5, label: '5º' }, { id: 6, label: '6º' },
    { id: 7, label: '7º' }, { id: 8, label: '8º' }, { id: 9, label: '9º' }
  ];

  modalidadeEscolar = [
    { id: 1, label: 'INFANTIL' },
    { id: 2, label: 'FUNDAMENTAL' },
    { id: 3, label: 'MÉDIO' }
  ];

  horarioEscolar = [
    { id: 1, label: 'MATUTINO' },
    { id: 2, label: 'VESPERTINO' },
    { id: 3, label: 'NOTURNO' },
    { id: 4, label: 'DIURNO' }
  ];

  horarioInstituicao = [
    { id: 1, label: 'MATUTINO' },
    { id: 2, label: 'VESPERTINO' },
    { id: 3, label: 'NOTURNO' },
    { id: 4, label: 'DIURNO' }
  ];

  constructor(private router:Router) { }

  ngOnInit() {
    UsuarioService.checkAuth(this.router);
  }
  
  onDtNascChange(event) {
    this.nrIdade.nativeElement.value = Utils.getAge(event.value);
  }
}
