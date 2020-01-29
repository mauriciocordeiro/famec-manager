import { Component, OnInit, ViewChild } from '@angular/core';
import { AlunoService } from 'src/app/services/aluno.services';
import { Utils } from 'src/app/services/Utils';
import { Router } from '@angular/router';
import { MatSnackBar, MatSort, MatTableDataSource } from '@angular/material';

import * as jsPDF from 'jspdf';

export interface Register {
  nr_prontuario: number;
  nm_aluno: string;
  dt_nascimento: string;
  nr_idade: number;
  nm_bairro: string;
  ds_endereco: string;
  nm_escola: string;
  nm_turno_famec: string;
  nm_responsavel: string;
  nr_telefone_1: string;
}

@Component({
  selector: 'app-relatorio-aluno',
  templateUrl: './relatorio-aluno.component.html',
  styleUrls: ['./relatorio-aluno.component.scss']
})
export class RelatorioAlunoComponent implements OnInit {

  printMode = false;

  dataSource = new MatTableDataSource([]);

  displayedColumns: string[] = [
    'nr_prontuario', 'nm_aluno', 'dt_nascimento',
    'nr_idade', 'nm_bairro', 'ds_endereco',
    'nm_escola', 'nm_turno_famec', 'nm_responsavel',
    'nr_telefone_1'
  ];

  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private router: Router,
    private snackBar: MatSnackBar,
    private alunoService: AlunoService) { }

  ngOnInit() {
  }

  ngAfterViewInit() {
    this.onSearch();
  }

  // show messages
  openSnackBar(message: string, action: string, type: string = Utils.SNACK_SUCCESS) {
    this.snackBar.open(message, action, {
      duration: 3000,
      panelClass: [type + '-snackbar']
    });
  }

  onSearch() {
    this.alunoService.find()
      .subscribe(result => {
        let data: Array<Register> = new Array();
        result.forEach(element => {
          data.push({
            nr_prontuario: <number>element.NR_PRONTUARIO,
            nm_aluno: <string>element.NM_ALUNO,
            dt_nascimento: <string>element.DS_DT_NASCIMENTO,
            nr_idade: <number>element.NR_IDADE,
            nm_bairro: <string>element.NM_BAIRRO,
            ds_endereco: <string>element.DS_ENDERECO,
            nm_escola: <string>element.NM_ESCOLA,
            nm_turno_famec: <string>element.NM_TURNO_FAMEC,
            nm_responsavel: <string>element.NM_RESPONSAVEL,
            nr_telefone_1: <string>element.NR_TELEFONE_1,
          });
        });
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.sort = this.sort;
      });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  onPrint() {
    this.alunoService.printList()
      .subscribe(item => {
        console.log(item);
        let file = new Blob([item], { type: 'application/pdf' });    
        let url = URL.createObjectURL(file);
        window.open(url);
      });
  }

}
