import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Usuario } from './usuario';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { Services } from './services';
import { Result } from './result';
import { Router } from '@angular/router';
import { SessionStorage } from './SessionStorage';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({ providedIn: 'root' })
export class UsuarioService extends Services {
	/**
	 * manage back-end request/response
	 */

  // URL to web api
  private serviceUrl = this.FULL_CONTEXT + "/rws/usuario";

  constructor(private http: HttpClient) {
    super();
  }

  saveUsuario(usuario: Usuario): Observable<Result> {
    return this.http.post(this.serviceUrl + "/", JSON.stringify(usuario, null, 2), httpOptions).pipe(
      tap((result: Result) => this.log(result.message)),
      catchError(this.handleError<Result>('Erro! saveUsuario.'))
    );
  }

  getUsuarios(term: string): Observable<any> {
    return this.http.get(`${this.serviceUrl}/pesquisar?nome=${term}`)
      .pipe(
        tap(_ => this.log(`found users matching "${term}"`)),
        catchError(this.handleError<Usuario[]>('searchUsuarios', [])
        )
      );
  }

  deleteUsuario(cdUsuario): Observable<Result> {
    const url = `${this.serviceUrl}/${cdUsuario}`;

    return this.http.delete<Result>(url, httpOptions).pipe(
      tap(_ => this.log(`deleted user cdUsuario=${cdUsuario}`)),
      catchError(this.handleError<Result>('deleteUsuario'))
    );
  }

  doLogin(nmLogin, nmSenha): Observable<Result> {
    return this.http.post(this.serviceUrl + "/login", JSON.stringify({ nmLogin: nmLogin, nmSenha: nmSenha }, null, 2), httpOptions).pipe(
      tap((result: Result) => this.log(result.message)),
      catchError(this.handleError<Result>('Erro! auth.'))
    );
  }

  static checkAuth(router:Router) {
    if(SessionStorage.get('famec.usuario') === null) {
      router.navigate(['login']);
    }
  }

}