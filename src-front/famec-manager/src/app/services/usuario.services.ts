import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';

import { Usuario } from './usuario';


import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({ providedIn: 'root' })
export class HeroService {



	private heroesUrl = '/famec/rws/usuario';  // URL to web api

	constructor(
		private http: HttpClient) { }

	/** GET heroes from the server */
	/** GET heroes from the server */
	getUsuarios(): Observable<Usuario[]> {
		return this.http.get<Usuario[]>(this.heroesUrl)
			.pipe(
				tap(_ => this.log('fetched heroes')),
				catchError(this.handleError('getHeroes', []))
			);
	}

	/** GET hero by id. Will 404 if id not found */
	getUsuario(id: number): Observable<Usuario> {
		const url = `${this.heroesUrl}/${id}`;
		return this.http.get<Usuario>(url).pipe(
			tap(_ => this.log(`fetched hero id=${id}`)),
			catchError(this.handleError<Usuario>(`getHero id=${id}`))
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
		return this.http.put(this.heroesUrl, hero, httpOptions).pipe(
			tap(_ => this.log(`updated hero id=${hero.cdUsuario}`)),
			catchError(this.handleError<any>('updateHero'))
		);
	}

	/** POST: add a new hero to the server */
	addHero(hero: Usuario): Observable<Usuario> {
		return this.http.post<Usuario>(this.heroesUrl, hero, httpOptions).pipe(
			tap((hero: Usuario) => this.log(`added hero w/ id=${hero.cdUsuario}`)),
			catchError(this.handleError<Usuario>('addHero'))
		);
	}

	/** DELETE: delete the hero from the server */
	deleteHero(hero: Usuario | number): Observable<Usuario> {
		const id = typeof hero === 'number' ? hero : hero.cdUsuario;
		const url = `${this.heroesUrl}/${id}`;

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
		return this.http.get<Usuario[]>(`${this.heroesUrl}/?name=${term}`).pipe(
			tap(_ => this.log(`found heroes matching "${term}"`)),
			catchError(this.handleError<Usuario[]>('searchHeroes', []))
		);
	}
}