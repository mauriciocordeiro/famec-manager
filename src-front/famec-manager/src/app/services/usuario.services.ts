import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';

import { Usuario } from './usuario';


import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({ providedIn: 'root' })
export class UsuarioService {

	private serviceUrl = 'http://localhost:8080/famec/rws/usuario';  // URL to web api

	constructor(private http: HttpClient) { }

	/** GET heroes from the server */
	getUsuarios(): Observable<Usuario[]> {
		return this.http.get<Usuario[]>(this.serviceUrl)
			.pipe(
				tap(_ => this.log('fetched users')),
				catchError(this.handleError('getUsuarios', []))
			);
	}

	/** GET hero by id. Will 404 if id not found */
	getUsuario(id: number): Observable<Usuario> {
		const url = `${this.serviceUrl}/${id}`;
		return this.http.get<Usuario>(url).pipe(
			tap(_ => this.log(`fetched user id=${id}`)),
			catchError(this.handleError<Usuario>(`getUsuario id=${id}`))
		);
	}

	/** Log a HeroService message with the MessageService */
	private log(message: string) {
        // this.messageService.add(`HeroService: ${message}`);
        console.log(message);
	}

	/**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
	private handleError<T>(operation = 'operation', result?: T) {
		return (error: any): Observable<T> => {

			// TODO: send the error to remote logging infrastructure
			console.error(error); // log to console instead

			// TODO: better job of transforming error for user consumption
			this.log(`${operation} failed: ${error.message}`);

			// Let the app keep running by returning an empty result.
			return of(result as T);
		};
	}

	/** PUT: update the hero on the server */
	updateHero(hero: Usuario): Observable<any> {
		return this.http.put(this.serviceUrl, hero, httpOptions).pipe(
			tap(_ => this.log(`updated hero id=${hero.cdUsuario}`)),
			catchError(this.handleError<any>('updateHero'))
		);
	}

	/** PUT: add a new hero to the server */
	addUsuario(usuario: Usuario): Observable<Usuario> {
		return this.http.put<Usuario>(this.serviceUrl+"/save", JSON.stringify(usuario, null, 2), httpOptions).pipe(
			tap((usuario: Usuario) => this.log(`added user w/ id=${usuario.cdUsuario}`)),
			catchError(this.handleError<Usuario>('addUsuario'))
		);
	}

	/** DELETE: delete the hero from the server */
	deleteHero(hero: Usuario | number): Observable<Usuario> {
		const id = typeof hero === 'number' ? hero : hero.cdUsuario;
		const url = `${this.serviceUrl}/${id}`;

		return this.http.delete<Usuario>(url, httpOptions).pipe(
			tap(_ => this.log(`deleted hero id=${id}`)),
			catchError(this.handleError<Usuario>('deleteHero'))
		); 	
	}

	/* GET heroes whose name contains search term */
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