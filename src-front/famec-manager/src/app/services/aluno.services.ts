import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { Services } from './services';
import { Aluno } from './aluno';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({ providedIn: 'root' })
export class AlunoService extends Services {
  static find() {
    throw new Error("Method not implemented.");
  }

  /**
   * manage back-end request/response
   */

  // URL to web api
  private serviceUrl = this.FULL_CONTEXT + "/rws/aluno";

  constructor(private http: HttpClient) {
    super();
  }

  quickSearch(term: string): Observable<any> {
    return this.http.get(`${this.serviceUrl}/quick/search?nome=${term}`)
      .pipe(
        tap(_ => this.log(`found aluno matching "${term}"`)),
        catchError(this.handleError<Aluno[]>('quickSearch', []))
      );
  }

  find(): Observable<any> {
    return this.http.post(`${this.serviceUrl}/find`, {}, {})
      .pipe(
        tap(_ => this.log(`found aluno matching`)),
        catchError(this.handleError<JSON[]>('find', []))
      );
  }

  printList(): Observable<any> {
    
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    
    return this.http.get(`${this.serviceUrl}/report/list`, 
      {headers, responseType: 'blob'})
        .pipe(
          tap((result:any) => this.log("Erro ao gerar relatório")),
          catchError(this.handleError<any>('Erro! reportList'))
    );
  }
}