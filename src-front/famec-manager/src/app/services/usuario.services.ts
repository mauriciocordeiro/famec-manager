import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Usuario } from './usuario';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Services } from './services';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({ providedIn: 'root' })
export class UsuarioService extends Services {

	private serviceUrl = this.FULL_CONTEXT+"/rws/usuario";//'http://localhost:8080/famec/rws/usuario';  // URL to web api

	constructor(private http: HttpClient) { 
		super();
	}

	saveUsuario(usuario: Usuario): Observable<Usuario> {
		debugger;
		return this.http.put<Usuario>(this.serviceUrl+"/save", JSON.stringify(usuario, null, 2), httpOptions).pipe(
			tap((usuario: Usuario) => this.log(`added user w/ id=${usuario.cdUsuario}`)),
			catchError(this.handleError<Usuario>('addUsuario'))
		);
	}










	getUsuarios(): Observable<Usuario[]> {
		return this.http.get<Usuario[]>(this.serviceUrl)
			.pipe(
				tap(_ => this.log('fetched users')),
				catchError(this.handleError('getUsuarios', []))
			);
	}

	getUsuario(id: number): Observable<Usuario> {
		const url = `${this.serviceUrl}/${id}`;
		return this.http.get<Usuario>(url).pipe(
			tap(_ => this.log(`fetched user id=${id}`)),
			catchError(this.handleError<Usuario>(`getUsuario id=${id}`))
		);
	}

	updateHero(hero: Usuario): Observable<any> {
		return this.http.put(this.serviceUrl, hero, httpOptions).pipe(
			tap(_ => this.log(`updated hero id=${hero.cdUsuario}`)),
			catchError(this.handleError<any>('updateHero'))
		);
	}

	

	deleteHero(hero: Usuario | number): Observable<Usuario> {
		const id = typeof hero === 'number' ? hero : hero.cdUsuario;
		const url = `${this.serviceUrl}/${id}`;

		return this.http.delete<Usuario>(url, httpOptions).pipe(
			tap(_ => this.log(`deleted hero id=${id}`)),
			catchError(this.handleError<Usuario>('deleteHero'))
		); 	
	}

	searchHeroes(term: string): Observable<Usuario[]> {
		if (!term.trim()) {
			// if not search term, return empty hero array.
			return of([]);
		}
		return this.http.get<Usuario[]>(`${this.serviceUrl}/?name=${term}`).pipe(
			tap(_ => this.log(`found heroes matching "${term}"`)),
			catchError(this.handleError<Usuario[]>('searchHeroes', []))
		);
	}
}