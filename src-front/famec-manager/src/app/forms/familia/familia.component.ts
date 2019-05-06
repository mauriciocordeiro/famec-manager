import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material';
import { FamiliaService } from 'src/app/services/familia.service';
import { ObjectUtils } from 'src/app/services/ObjectUtils';
import { Familia } from 'src/app/services/familia';
import { Router } from '@angular/router';
import { Utils } from 'src/app/services/Utils';
import { UsuarioService } from 'src/app/services/usuario.services';
import { Usuario } from 'src/app/services/usuario';
import { LocalStorage } from 'src/app/services/LocalStorage';
import { AlunoService } from 'src/app/services/aluno.services';
import { Aluno } from 'src/app/services/aluno';
import { SessionStorage } from 'src/app/services/SessionStorage';

@Component({
  selector: 'app-familia',
  templateUrl: './familia.component.html',
  styleUrls: ['./familia.component.scss']
})
export class FamiliaComponent implements OnInit {

  loading = false;

  usuario:Usuario;

  alunos: Aluno[];
  @ViewChild('searchField') searchField: ElementRef;

  step = 0;

  lgHabitacaoAluguel: boolean = false;
  lgResponsavelEstudando: boolean = false;

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

  constructor(
    private router:Router,
    private snackBar: MatSnackBar, 
    private familiaService: FamiliaService,
    private alunoService: AlunoService
  ) { }

  ngOnInit() {
    UsuarioService.checkAuth(this.router);    
    this.buildFormGroup();
    this.usuario = ObjectUtils.getInstanceByString(Utils.decrypt(SessionStorage.get('famec.usuario')), Usuario);
  }

  buildFormGroup(register?) {
    console.log(register);

    this.formGroup = this.getFormGroup(register);

    if(register) {
      register.RSM_ALUNO.lines.forEach(regAluno => {
        this.formGroup.value.arrayAlunos.push(this.getAlunoFormGroup(regAluno));  
      });
    } else {
      this.formGroup.value.arrayAlunos.push(this.getAlunoFormGroup());
    }
  }

  getFormGroup(register?): FormGroup {
    return new FormGroup({
      // familia
      cdFamilia: new FormControl(register ? register.CD_FAMILIA: 0),
      dtCadastro: new FormControl(register ? new Date(register.DT_CADASTRO) : ''),
      cdUsuarioCadastro: new FormControl(register ? register.CD_USUARIO_CADASTRO : 0),
      nrProntuario: new FormControl(register ? register.NR_PRONTUARIO : ''),

      // responsavel
      cdResponsavel: new FormControl(register ? register.CD_RESPONSAVEL : 0),
      nmResponsavel: new FormControl(register ? register.NM_RESPONSAVEL : ''),
      tpParentesco: new FormControl(register ? register.TP_PARENTESCO : 0),
      tpGenero: new FormControl(register ? register.TP_GENERO : 0),
      dtNascimento: new FormControl(register ? new Date(register.DT_NASCIMENTO) : ''),
      nmNaturalidade: new FormControl(register ? register.NM_NATURALIDADE : ''),
      tpEstadoCivil: new FormControl(register ? register.TP_ESTADO_CIVIL : 0),
      nrTelefone1: new FormControl(register ? register.NR_TELEFONE_1 : ''),
      nrTelefone2: new FormControl(register ? register.NR_TELEFONE_2 : ''),
      nrRg: new FormControl(register ? register.NR_RG : ''),
      nmOrgaoExpedidorRg: new FormControl(register ? register.NM_ORGAO_EXPEDIDOR_RG : ''),
      sgUfRg: new FormControl(register ? register.SG_UF_RG : ''),
      nrCpf: new FormControl(register ? register.NR_CPF : ''),
      dsEscolaridade: new FormControl(register ? register.DS_ESCOLARIDADE : ''),
      lgEstudante: new FormControl(register ? register.LG_ESTUDANTE==1 : 0),
      tpNivelEscolar: new FormControl(register ? register.TP_NIVEL_ESCOLAR : 0),
      tpTurno: new FormControl(register ? register.TP_TURNO : 0),
      nmOcupacao: new FormControl(register ? register.NM_OCUPACAO : ''),
      vlRendaMensal: new FormControl(register ? register.VL_RENDA_MENSAL : 0),
      nmLocalTrabalho: new FormControl(register ? register.NM_LOCAL_TRABALHO : ''),
      nrTelefoneTrabalho: new FormControl(register ? register.NR_TELEFONE_TRABALHO : ''),

      // endereco
      cdEnderecoResponsavel: new FormControl(register ? register.CD_ENDERECO_RESPONSAVEL : 0),
      nmRua: new FormControl(register ? register.NM_RUA : ''),
      nrCasa: new FormControl(register ? register.NR_CASA : ''),
      nmComplemento: new FormControl(register ? register.NM_COMPLEMENTO : ''),
      nmBairro: new FormControl(register ? register.NM_BAIRRO : ''),
      nmCidade: new FormControl(register ? register.NM_CIDADE : ''),
      nmEstado: new FormControl(register ? register.NM_ESTADO : ''),

      // habitação
      cdHabitacao: new FormControl(register ? register.CD_HABITACAO : 0),
      tpSituacao: new FormControl(register ? register.TP_SITUACAO : 0),
      vlAluguel: new FormControl(register ? register.VL_ALUGUEL : 0),
      nrComodos: new FormControl(register ? register.NR_COMODOS : 0),
      tpAbastecimento: new FormControl(register ? register.TP_ABASTECIMENTO : 0),
      tpTratamentoAgua: new FormControl(register ? register.TP_TRATAMENTO_AGUA : 0),
      tpIluminacao: new FormControl(register ? register.TP_ILUMINACAO : 0),
      tpEscoamentoSanitario: new FormControl(register ? register.TP_ESCOAMENTO_SANITARIO : 0),
      tpDestinoLixo: new FormControl(register ? register.TP_DESTINO_LIXO : 0),

      // perfil social
      cdPerfilSocial: new FormControl(register ? register.CD_PERFIL_SOCIAL : 0),
      lgNis: new FormControl(register ? (register.LG_NIS==1) : 0),
      nrNis: new FormControl(register ? register.NR_NIS : ''),
      lgBeneficio: new FormControl(register ? register.LG_BENEFICIO==1 : 0),
      nmBeneficio: new FormControl(register ? register.NM_BENEFICIO : ''),
      vlBeneficio: new FormControl(register ? register.VL_BENEFICIO : 0),

      // alunos
      arrayAlunos: new FormControl([])
    });
  }

  getAlunoFormGroup(register?): FormGroup {
    return new FormGroup({
      cdAluno: new FormControl(register ? register.CD_ALUNO : 0),
      cdFamilia: new FormControl(register ? register.CD_FAMILIA : 0),
      nmAluno: new FormControl(register ? register.NM_ALUNO : ''),
      dtNascimento: new FormControl(register ? new Date(register.DT_NASCIMENTO) : ''),
      tpSexo: new FormControl(register ? register.TP_SEXO : 0),
      nmNaturalidade: new FormControl(register ? register.NM_NATURALIDADE : ''),
      nmEscola: new FormControl(register ? register.NM_ESCOLA : ''),
      nrNivelEscolar: new FormControl(register ? register.NR_NIVEL_ESCOLAR : 0),
      tpModalidadeEscolar: new FormControl(register ? register.TP_MODALIDADE_ESCOLAR : 0),
      tpHorarioEscolar: new FormControl(register ? register.TP_HORARIO_ESCOLAR : 0),
      tpTurnoFamec: new FormControl(register ? register.TP_TURNO_FAMEC : 0),
      // stAluno: new FormControl(0),
      hrSaida: new FormControl(register ? register.HR_SAIDA : ''),
      lgAcompanhanteSaida: new FormControl(register ? register.LG_ACOMPANHANTE_SAIDA==1 : 0),
      nmAcompanhanteSaida: new FormControl(register ? register.NM_ACOMPANHANTE_SAIDA : ''),
      lgAlmocoInstituicao: new FormControl(register ? register.LG_ALMOCO_INSTITUICAO==1 : 0),
    });
  }

  // show messages
  openSnackBar(message: string, action: string, type: string = Utils.SNACK_SUCCESS) {
    this.snackBar.open(message, action, {
      duration: 3000,
      panelClass: [type + '-snackbar']
    });
  }


  onSubmit() {
    //form validation
    if (!this.formGroup.valid) {
      this.openSnackBar("Existem campos inválidos.", null, Utils.SNACK_ALERT);
      return;
    }

    this.familiaService.saveFamilia(this.mapRegister())
      .subscribe(result => {
        if (result.code <= 0) { // save failed 
          this.openSnackBar(result.message, null, Utils.SNACK_ERROR);
          return;
        }

        this.openSnackBar(result.message, null);
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
    register.cdFamilia         = this.formGroup.get('cdFamilia').value;
    register.cdUsuarioCadastro = this.usuario.cdUsuario;
    register.dtCadastro        = new Date();
    register.nrProntuario      = this.formGroup.get('nrProntuario').value;
    // RESPONSAVEL
    register.cdResponsavel      = this.formGroup.value.cdResponsavel;
    register.nmResponsavel      = this.formGroup.get('nmResponsavel').value;
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
    register.cdEnderecoResponsavel = this.formGroup.value.cdEnderecoResponsavel;
    register.nmRua                 = this.formGroup.value.nmRua;
    register.nrCasa                = this.formGroup.value.nrCasa;
    register.nmComplemento         = this.formGroup.value.nmComplemento;
    register.nmBairro              = this.formGroup.value.nmBairro;
    register.nmCidade              = this.formGroup.value.nmCidade;
    register.nmEstado              = this.formGroup.value.nmEstado;
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
        this.openSnackBar("Existem campos inválidos.", null, Utils.SNACK_ALERT);
        return;
      }

      fgAluno.value.lgAlmocoInstituicao = fgAluno.value.lgAlmocoInstituicao ? 1 : 0;
      fgAluno.value.lgAcompanhanteSaida = fgAluno.value.lgAcompanhanteSaida ? 1 : 0;

      arrayAlunos.push(fgAluno.value);
    });
    register.arrayAlunos = arrayAlunos;
    // debugger;
    console.log("mapped: ", register);
    return register;
  }

  // search
  onSearch(term: string): void {
    if (!term.trim())
			return;
		if (term.length < 3)
      return;
    this.alunoService.quickSearch(term)
      .subscribe(list => {
        this.alunos = list;
      });
  }
  // fill formControl with a selected item (from search)
  onSelectItem(arg: Aluno): void {
    if (!arg)
      return;

    this.familiaService.getFamilia(arg.cdFamilia)
      .subscribe(item => {
        let reg = (<Array<any>>item.lines).pop();
        this.buildFormGroup(reg);
        // this.formGroup.setValue((<Array<any>>item.lines).pop());
        this.alunos = [];      
        this.searchField.nativeElement.value = "";
      });
  }
}
