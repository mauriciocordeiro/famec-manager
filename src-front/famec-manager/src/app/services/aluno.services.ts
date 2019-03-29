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
}