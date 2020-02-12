import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { Services } from './services';
import { Result } from './result';
import { Aluno } from './aluno';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({ providedIn: 'root' })
export class FamiliaService extends Services {

  /**
   * manage back-end request/response
   */

  // URL to web api
  private endpoint = this.FULL_CONTEXT + "/rws/familia";

  constructor(private http: HttpClient) {
    super();
  }

  saveFamilia(familia: any): Observable<Result> {
    return this.http.post(this.endpoint, JSON.stringify(familia, null, 2), httpOptions).pipe(
      tap((result: Result) => this.log(result.message)),
      catchError(this.handleError<Result>('Erro! saveFamilia'))
    );
  }

  getFamilia(cdFamilia: number): Observable<any> {
    return this.http.get(`${this.endpoint}/${cdFamilia}`)
      .pipe(
        tap(_ => this.log(`found familias matching "${cdFamilia}"`)),
        catchError(this.handleError<any[]>('getFamilias', [])
        )
      );
  }

  quickSearch(term: string): Observable<any> {
    return this.http.get(`${this.endpoint}/quick/search?term=${term}`)
      .pipe(
        tap(_ => this.log(`found aluno matching "${term}"`)),
        catchError(this.handleError<Aluno[]>('quickSearch', []))
      );
  }

  deleteFamilia(cdFamilia: any): Observable<Result> {
    return this.http.delete(`${this.endpoint}/${cdFamilia}`, httpOptions).pipe(
      tap((result: Result) => this.log(result.message)),
      catchError(this.handleError<Result>('Erro! deleteFamilia'))
    );
  }

  printComprovante(cdFamilia, cdAluno): Observable<any> {
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    
    return this.http.get(`${this.endpoint}/report/comprovante?cdFamilia=${cdFamilia}&cdAluno=${cdAluno}`, 
      {headers, responseType: 'blob'})
        .pipe(
          tap((result:any) => this.log("Erro ao gerar relatório")),
          catchError(this.handleError<any>('Erro! reportComprovante'))
    );
  }
}