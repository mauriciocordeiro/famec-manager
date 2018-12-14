import { Observable, BehaviorSubject } from 'rxjs';

import { map } from 'rxjs/operators';
import { merge } from 'rxjs/observable/merge';

import { Injectable, OnInit } from '@angular/core';
import { DataSource, SelectionModel } from '@angular/cdk/collections';

import { MatTableDataSource } from '@angular/material';

import { ArrayUtils } from './ArrayUtils';

export class ResultSetMap extends DataSource<any> {


    private _lines: any[] = [];
    private _selectedLines: any[] = [];
    private _renderedData: any[] = [];

    dataSource = new MatTableDataSource<any>(this.lines);

    public get lines() {
        return this._lines;
    }
    public set lines(value) {
        this._lines = value;
    }

    public selection = new SelectionModel<any>(true, []);
    public dataChange: BehaviorSubject<any[]> = new BehaviorSubject<any[]>([]);
    get data(): any[] {
        return this.dataChange.value
    }

    public _paginator: any;
    public _sort: any;
    public pointer: number = -1;

    /* @deprecated */
    public ORDER_ASC: number = 0;
    public ORDER_DESC: number = 1;
    public columns: any[];

    public hasMore: boolean;
    public offset: number;
    public limit: number;
    public total: number;


    constructor(data = null) {
        super();

        if (data && data instanceof Array)
            this.lines = data;
    }

    connect(): Observable<any[]> {
        const displayDataChanges = [];

        if (this.dataChange)
            displayDataChanges.push(this.dataChange);

        if (this._paginator && this._paginator.page)
            displayDataChanges.push(this._paginator.page);

        if (this._sort && this._sort.mdSortChange)
            displayDataChanges.push(this._sort.mdSortChange);

        this.refresh();

        return merge(...displayDataChanges).pipe(map(() => {
            this._renderedData = this.data;

            if (this._sort) {
                this._renderedData = this.getSortedData(this._renderedData);
            }

            if (this._paginator) {
                const data = this._renderedData.slice();
                const startIndex = this._paginator.pageIndex * this._paginator.pageSize;

                this._renderedData = data.splice(startIndex, this._paginator.pageSize);
            }

            return this._renderedData;
        }));
    }


    disconnect() {

    }

    public refresh(): void {
        const d = [];
        for (let key in this.lines) {
            d.push(this.lines[key]);
            this.dataChange.next(d);
        }
    }

    public sort(field: string, order: number = ArrayUtils.ASC): void {
        ArrayUtils.sort(this.lines, field, order);
    }

    getSortedData(data): any[] {
        if (!this._sort.active || this._sort.direction == '') { return data; }

        return data.sort((a, b) => {
            let propertyA: number | string = '';
            let propertyB: number | string = '';

            [propertyA, propertyB] = [a[this._sort.active], b[this._sort.active]]

            let valueA = isNaN(+propertyA) ? propertyA : +propertyA;
            let valueB = isNaN(+propertyB) ? propertyB : +propertyB;

            return (valueA < valueB ? -1 : 1) * (this._sort.direction == 'asc' ? 1 : -1);
        });
    }

    public revert(): void {
        this.lines.reverse();
    }

    /**
    *  Remove o item fornecido do rsm
    *  Retorna o indice do elemento removido em caso de sucesso
    *  ou -1 caso o item nÃ£o esteja no rsm
    **/
    public removeItem(item: any): number {
        let index: number = this.lines.find(item);

        if (index > -1)
            this.lines.splice(index, 1);

        return index;
    }

    /**
    *  Seleciona um objeto da propriedade {_lines}
    *  Retorna o indice do elemento removido em caso de sucesso
    *  ou -1 caso o item nÃ£o esteja no rsm
    *  @param {any} item
    **/
    public select(item: any): boolean {
        let indexSelected: number = this._selectedLines.find(item);
        let indexLines: number = this._lines.find(item);

        if (!indexSelected && indexLines) {
            this._selectedLines.push(item);
        } else {
            return false;
        }

        return true;
    }

    /**
    *  Remove o item fornecido dos registros selecionados do rsm
    *  Retorna o indice do elemento removido em caso de sucesso
    *  ou -1 caso o item nÃ£o esteja no rsm
    **/
    public unselect(item: any): number {
        let index: number = this._selectedLines.find(item);

        if (index > -1)
            this._selectedLines.splice(index, 1);

        return index;
    }

    public selectAll() {
        if (this.isAllSelected()) {
            this.selection.clear();
        } else {
            this._renderedData.forEach(data => this.selection.select(data));
        }
    }

    public isAllSelected(): boolean {
        if (!this) { return false; }
        if (this.selection.isEmpty()) { return false; }
        return this.selection.selected.length == this._renderedData.length;
    }

    /**
    * Obtem uma string com os valores da propriedade especificada separadas
    * por vÃ­rgula, ou o separador especificado.
    */
    public joinBy(property: string, separator: string = ","): string {
        let result: any[] = [];
        for (let reg in this.lines) {
            if (reg.hasOwnProperty(property))
                result.push(reg[property]);
        }
        return result.join(separator);
    }

    public toString(): string {
        let out: string = "";

        for (let i: number = 0; i < this.lines.length; i++) {
            let register: Object = this.lines[i];
            out += "line " + (i + 1) + ": [";
            for (let key in register) {
                out += key + ": " + register[key] + ", ";
            }
            out += "]\n";
        }
        return out == "" ? "nenhuma coluna" : out;
    }

    public locate(campo: string, valor: string): boolean {
        let position: number = this.pointer;

        this.beforeFirst();
        while (this.next()) {
            let valorCampo: string = this.lines[this.pointer][campo];
            if ((valorCampo != null) && (valorCampo == valor)) {
                return true;
            }
        }
        this.pointer = position;
        return false;
    }

    public beforeFirst(): void {
        this.pointer = -1;
    }

    public next(): boolean {
        if (this.pointer + 1 >= this.size()) {
            return false;
        } else {
            this.pointer++;
            return true;
        }
    }

    public size(): number {
        return this.lines.length;
    }

    public getRegister(): object {
        return this.lines[this.pointer];
    }

}

export class SimpleResultSetMap {

    public lines: any[] = [];
    public pointer: number = -1;

    /* @deprecated */
    public static ORDER_ASC: number = 0;
    public static ORDER_DESC: number = 1;
    public columns: any[];

    public hasMore: boolean;
    public offset: number;
    public limit: number;
    public total: number;

    constructor(data = null) {
        if (data && data instanceof Array)
            this.lines = data;
    }

    public sort(field: string, order: number = ArrayUtils.ASC): void {
        ArrayUtils.sort(this.lines, field, order);
    }

    public revert(): void {
        this.lines.reverse();
    }

    /**
    *  Remove o item fornecido do rsm
    *  Retorna o indice do elemento removido em caso de sucesso
    *  ou -1 caso o item nÃ£o esteja no rsm
    **/
    public removeItem(item: any): number {
        let index: number = this.lines.find(item);

        if (index > -1)
            this.lines.splice(index, 1);

        return index;
    }

    /**
    * Obtem uma string com os valores da propriedade especificada separadas
    * por vÃ­rgula, ou o separador especificado.
    */
    public joinBy(property: string, separator: string = ","): string {
        let result: any[] = [];
        for (let reg in this.lines) {
            if (reg.hasOwnProperty(property))
                result.push(reg[property]);
        }
        return result.join(separator);
    }

    public toString(): string {
        let out: string = "";

        for (let i: number = 0; i < this.lines.length; i++) {
            let register: Object = this.lines[i];
            out += "line " + (i + 1) + ": [";
            for (let key in register) {
                out += key + ": " + register[key] + ", ";
            }
            out += "]\n";
        }
        return out == "" ? "nenhuma coluna" : out;
    }

    public locate(campo: string, valor: string): boolean {
        let position: number = this.pointer;

        this.beforeFirst();
        while (this.next()) {
            let valorCampo: string = this.lines[this.pointer][campo];
            if ((valorCampo != null) && (valorCampo == valor)) {
                return true;
            }
        }
        this.pointer = position;
        return false;
    }

    public beforeFirst(): void {
        this.pointer = -1;
    }

    public next(): boolean {
        if (this.pointer + 1 >= this.size()) {
            return false;
        } else {
            this.pointer++;
            return true;
        }
    }

    public size(): number {
        return this.lines.length;
    }
}
