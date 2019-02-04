import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material';
import { FamiliaService } from 'src/app/services/familia.service';
import { ObjectUtils } from 'src/app/services/ObjectUtils';
import { Familia } from 'src/app/services/familia';

@Component({
  selector: 'app-familia',
  templateUrl: './familia.component.html',
  styleUrls: ['./familia.component.scss']
})
export class FamiliaComponent implements OnInit {

  step = 0;

  lgHabitacaoAluguel: boolean = false;
  lgResponsavelEstudando: boolean = false;

  // snackbar message type
  ALERT: string = 'alert';
  ERROR: string = 'error';
  SUCCESS: string = 'success';

  parentesco = [
    { id: 1, label: 'Mãe/Pai' },
    { id: 2, label: 'Avó/Avô' },
    { id: 3, label: 'Irmã/Irmão' },
    { id: 4, label: 'Tia/Tio' },
    { id: 5, label: 'Outro' }
  ];

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

  formGroup: FormGroup;

  constructor(private snackBar: MatSnackBar, private familiaService: FamiliaService) { }

  ngOnInit() {

    // building formGroup
    this.formGroup = this.getFormGroup();
    this.formGroup.value.arrayAlunos.push(this.getAlunoFormGroup());

  }

  getFormGroup(): FormGroup {
    return new FormGroup({
      // familia
      cdFamilia: new FormControl(0),
      dtCadastro: new FormControl(''),
      cdUsuarioCadastro: new FormControl(0),

      // responsavel
      cdResponsavel: new FormControl(0),
      nmResponsavel: new FormControl(''),
      tpParentesco: new FormControl(0),
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

  getAlunoFormGroup(): FormGroup {
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

  // show messages
  openSnackBar(message: string, action: string, type: string = this.SUCCESS) {
    this.snackBar.open(message, action, {
      duration: 3000,
      panelClass: [type + '-snackbar']
    });
  }


  onSubmit() {
    //form validation
    if (!this.formGroup.valid) {
      this.openSnackBar("Existem campos inválidos.", null, this.ALERT);
      return;
    }

    this.familiaService.saveFamilia(this.mapRegister())
      .subscribe(result => {
        if (result.code <= 0) { // save failed 
          this.openSnackBar(result.message, null, this.ERROR);
          return;
        }

        this.openSnackBar(result.message, null);
        debugger;
      });

  }

  onDelete() {

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

  mapRegister(): any {
    var register: any = {};

    // FAMILIA
    register.cdFamilia         = this.formGroup.value.cdFamilia;
    register.cdUsuarioCadastro = this.formGroup.value.cdUsuarioCadastro;
    register.dtCadastro        = new Date();
    // RESPONSAVEL
    register.cdResponsavel      = this.formGroup.value.cdResponsavel;
    register.nmResponsavel      = this.formGroup.value.nmResponsavel;
    register.tpParentesco       = this.formGroup.value.tpParentesco;
    register.tpGenero           = this.formGroup.value.tpGenero ? 1 : 0;
    register.dtNascimento       = this.formGroup.value.dtNascimento;
    register.nmNaturalidade     = this.formGroup.value.nmNaturalidade;
    register.tpEstadoCivil      = this.formGroup.value.tpEstadoCivil;
    register.nrTelefone1        = this.formGroup.value.nrTelefone1;
    register.nrTelefone2        = this.formGroup.value.nrTelefone2;
    register.nrRg               = this.formGroup.value.nrRg;
    register.nmOrgaoExpedidorRg = this.formGroup.value.nmOrgaoExpedidorRg;
    register.sgUfRg             = this.formGroup.value.sgUfRg;
    register.nrCpf              = this.formGroup.value.nrCpf;
    register.dsEscolaridade     = this.formGroup.value.dsEscolaridade;
    register.lgEstudante        = this.formGroup.value.lgEstudante ? 1 : 0;
    register.tpNivelEscolar     = this.formGroup.value.tpNivelEscolar;
    register.tpTurno            = this.formGroup.value.tpTurno;
    register.nmOcupacao         = this.formGroup.value.nmOcupacao;
    register.vlRendaMensal      = this.formGroup.value.vlRendaMensal;
    register.nmLocalTrabalho    = this.formGroup.value.nmLocalTrabalho;
    register.nrTelefoneTrabalho = this.formGroup.value.nrTelefoneTrabalho;
    // ENDERECO
    register.cdEndereco    = this.formGroup.value.cdEndereco;
    register.nmRua         = this.formGroup.value.nmRua;
    register.nrCasa        = this.formGroup.value.nrCasa;
    register.nmComplemento = this.formGroup.value.nmComplemento;
    register.nmBairro      = this.formGroup.value.nmBairro;
    register.nmCidade      = this.formGroup.value.nmCidade;
    register.nmEstado      = this.formGroup.value.nmEstado;
    // HABITACAO
    register.cdHabitacao           = this.formGroup.value.cdHabitacao;
    register.tpSituacao            = this.formGroup.value.tpSituacao;
    register.vlAluguel             = this.formGroup.value.vlAluguel;
    register.nrComodos             = this.formGroup.value.nrComodos;
    register.tpAbastecimento       = this.formGroup.value.tpAbastecimento;
    register.tpTratamentoAgua      = this.formGroup.value.tpTratamentoAgua;
    register.tpIluminacao          = this.formGroup.value.tpIluminacao;
    register.tpEscoamentoSanitario = this.formGroup.value.tpEscoamentoSanitario;
    register.tpDestinoLixo         = this.formGroup.value.tpDestinoLixo;
    // PERFIL SOCIAL
    register.cdPerfilSocial = this.formGroup.value.cdPerfilSocial;
    register.lgNis          = this.formGroup.value.lgNis ? 1 : 0;
    register.nrNis          = this.formGroup.value.nrNis;
    register.lgBeneficio    = this.formGroup.value.lgBeneficio ? 1 : 0;
    register.nmBeneficio    = this.formGroup.value.nmBeneficio;
    register.vlBeneficio    = this.formGroup.value.vlBeneficio;
    // ALUNOS
    var arrayAlunos: Array<any> = [];
    this.formGroup.value.arrayAlunos.forEach(fgAluno => {
      if (!fgAluno.valid) {
        this.openSnackBar("Existem campos inválidos.", null, this.ALERT);
        return;
      }

      fgAluno.value.lgAlmocoInstituicao = fgAluno.value.lgAlmocoInstituicao ? 1 : 0;
      fgAluno.value.lgAcompanhanteSaida = fgAluno.value.lgAcompanhanteSaida ? 1 : 0;

      arrayAlunos.push(fgAluno.value);
    });
    register.arrayAlunos = arrayAlunos;
    debugger;
    return register;
  }

}
