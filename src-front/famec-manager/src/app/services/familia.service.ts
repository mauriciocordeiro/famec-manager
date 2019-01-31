import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { Services } from './services';
import { Result } from './result';

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
    return this.http.put(this.serviceUrl + "/save", JSON.stringify(familia, null, 2), httpOptions).pipe(
      tap((result: Result) => this.log(result.message)),
      catchError(this.handleError<Result>('Erro! saveFamilia'))
    );
  }
}