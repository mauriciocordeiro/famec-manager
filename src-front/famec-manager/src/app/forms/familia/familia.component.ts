import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-familia',
  templateUrl: './familia.component.html',
  styleUrls: ['./familia.component.scss']
})
export class FamiliaComponent implements OnInit {

  step = 0;

  lgHabitacaoAluguel: boolean = false;
  lgResponsavelEstudando: boolean = false;

  estadoCivil = [
    { id: 1, label: 'Solteiro' },
    { id: 1, label: 'Casado' },
    { id: 1, label: 'Separado' },
    { id: 1, label: 'Divorciado' },
    { id: 1, label: 'Viúvo' }
  ];

  nivelEscolaridade = [
    { id: 1, label: 'Fundamental' },
    { id: 2, label: 'Médio' },
    { id: 3, label: 'Superior' },
    { id: 4, label: 'Outro' }
  ];

  horarioEscolar = [
    { id: 1, label: 'Matutino' },
    { id: 2, label: 'Vespertino' },
    { id: 3, label: 'Noturno' },
    { id: 4, label: 'Diurno' }
  ];

  situacaoHabitacional = [
    { id: 1, label: 'Própria' },
    { id: 2, label: 'Alugada' },
    { id: 3, label: 'Cedida' },
    { id: 4, label: 'Invasão' },
    { id: 5, label: 'De favor' }
  ];

  abastecimentoAgua = [
    { id: 1, label: 'Rede pública' }
  ];

  tratamentoAgua = [
    { id: 1, label: 'Filtração' },
    { id: 2, label: 'Fervura' },
    { id: 3, label: 'S/ Tratamento' },
    { id: 4, label: 'Outro' }
  ];

  iluminacao = [
    { id: 1, label: 'Medidor próprio' },
    { id: 2, label: 'S/ Medidor' },
    { id: 3, label: 'Outros' }
  ];

  escoamentoSanitario = [
    { id: 1, label: 'Esgoto' },
    { id: 2, label: 'Fossa' },
    { id: 3, label: 'Céu aberto' },
    { id: 4, label: 'Outros' }
  ];

  destinoLixo = [
    { id: 1, label: 'Coletado' },
    { id: 2, label: 'Queimado' },
    { id: 3, label: 'Enterrado' },
    { id: 4, label: 'Céu aberto' },
    { id: 5, label: 'Outros' }
  ];

  sgUf = ['AC', 'AL', 'AM', 'AP', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA', 'MG', 'MS', 'MT', 'PA', 'PB', 'PE', 'PI', 'PR', 'RJ', 'RN', 'RO', 'RR', 'RS', 'SC', 'SE', 'SP', 'TO'];

  alunos: any = [
    { id: 1, nmAluno: 'Alfa Centauro' }
  ];

  formGroup: FormGroup;

  
  constructor() { }

  ngOnInit() {

    // building formGroup
    this.formGroup = this.getFormGroup();
    this.formGroup.value.arrayAlunos.push(this.getAlunoFormGroup());

  }

  onSubmit() {
    console.log(this.formGroup.value);
    // debugger;
  }

  onDelete() {

  }

  getFormGroup():FormGroup {
    return new FormGroup({
      // familia
      cdFamilia: new FormControl(0),
      dtCadastro: new FormControl(''),
      cdUsuarioCadastro: new FormControl(0),
  
      // responsavel
      cdResponsavel: new FormControl(0),
      nmResponsavel: new FormControl(''),
      nmParentesco: new FormControl(''),
      tpGenero: new FormControl(0),
      dtNascimento: new FormControl(''),
      nmNaturalidade: new FormControl(''),
      tpEstadoCivil: new FormControl(0),
      nrTelefone1: new FormControl(''),
      nrTelefone2: new FormControl(''),
      nrRg: new FormControl(''),
      nmOrgaoExpedidorRg: new FormControl(''),
      sgUfRg: new FormControl(''),
      nrCpf: new FormControl(''),
      dsEscolaridade: new FormControl(''),
      lgEstudante: new FormControl(0),
      tpNivelEscolar: new FormControl(0),
      tpTurno: new FormControl(0),
      nmOcupacao: new FormControl(''),
      vlRendaMensal: new FormControl(0),
      nmLocalTrabalho: new FormControl(''),
      nrTelefoneTrabalho: new FormControl(''),
  
      // endereco
      cdEndereco: new FormControl(0),
      nmRua: new FormControl(''),
      nrCasa: new FormControl(''),
      nmComplemento: new FormControl(''),
      nmBairro: new FormControl(''),
      nmCidade: new FormControl(''),
      nmEstado: new FormControl(''),
  
      // habitação
      cdHabitacao: new FormControl(0),
      tpSituacao: new FormControl(0),
      vlAluguel: new FormControl(0),
      nrComodos: new FormControl(0),
      tpAbastecimento: new FormControl(0),
      tpTratamentoAgua: new FormControl(0),
      tpIluminacao: new FormControl(0),
      tpEscoamentoSanitario: new FormControl(0),
      tpDestinoLixo: new FormControl(0),
  
      // perfil social
      cdPerfilSocial: new FormControl(0),
      lgNis: new FormControl(0),
      nrNis: new FormControl(''),
      lgBeneficio: new FormControl(0),
      nmBeneficio: new FormControl(''),
      vlBeneficio: new FormControl(0),
  
      // alunos
      arrayAlunos: new FormControl([])
    });
  }

  getAlunoFormGroup():FormGroup {
    return new FormGroup({
      cdAluno: new FormControl(0),
      cdFamilia: new FormControl(0),
      nmAluno: new FormControl(''),
      dtNascimento: new FormControl(''),
      tpSexo: new FormControl(0),
      nmNaturalidade: new FormControl(''),
      nmEscola: new FormControl(''),
      nrNivelEscolar: new FormControl(0),
      tpModalidadeEscolar: new FormControl(0),
      tpHorarioEscolar: new FormControl(0),
      tpTurnoFamec: new FormControl(0),
      // stAluno: new FormControl(0),
      hrSaida: new FormControl(''),
      lgAcompanhanteSaida: new FormControl(0),
      nmAcompanhanteSaida: new FormControl(''),
      lgAlmocoInstituicao: new FormControl(0),
    });
  }

  onChangeEstudando(event) {
    this.lgResponsavelEstudando = event.checked;
  }

  onChangeSituacaoHabitacional(event) {
    this.lgHabitacaoAluguel = event.value === 2; //Alugada
  }

  onClickAddAluno() {
    this.formGroup.value.arrayAlunos.push(this.getAlunoFormGroup());
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
