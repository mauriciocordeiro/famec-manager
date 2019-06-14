import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
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
  private serviceUrl = this.FULL_CONTEXT + "/rws/familia";

  constructor(private http: HttpClient) {
    super();
  }

  saveFamilia(familia: any): Observable<Result> {
    console.log('familia: ', familia)
    return this.http.put(this.serviceUrl + "/save", JSON.stringify(familia, null, 2), httpOptions).pipe(
      tap((result: Result) => this.log(result.message)),
      catchError(this.handleError<Result>('Erro! saveFamilia'))
    );
  }

  getFamilia(cdFamilia: number): Observable<any> {
    return this.http.get(`${this.serviceUrl}/get?cod=${cdFamilia}`)
      .pipe(
        tap(_ => this.log(`found familias matching "${cdFamilia}"`)),
        catchError(this.handleError<any[]>('getFamilias', [])
        )
      );
  }

  quickSearch(term: string): Observable<any> {
    return this.http.get(`${this.serviceUrl}/quick/search?term=${term}`)
      .pipe(
        tap(_ => this.log(`found aluno matching "${term}"`)),
        catchError(this.handleError<Aluno[]>('quickSearch', []))
      );
  }
}